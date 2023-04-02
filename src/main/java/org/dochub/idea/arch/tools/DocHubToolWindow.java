package org.dochub.idea.arch.tools;
// JBCefBrowser (https://intellij-support.jetbrains.com/hc/en-us/community/posts/4403677440146-how-to-add-a-JBCefBrowser-in-toolWindow-)

import com.fasterxml.jackson.databind.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.*;
import com.intellij.openapi.util.SystemInfoRt;
import com.intellij.openapi.vfs.*;
import com.intellij.openapi.vfs.newvfs.*;
import com.intellij.openapi.vfs.newvfs.events.*;
import com.intellij.ui.jcef.*;
import com.intellij.util.messages.*;
import org.apache.commons.io.*;
import org.apache.http.client.utils.URIBuilder;
import org.dochub.idea.arch.indexing.*;
import org.dochub.idea.arch.jsonschema.*;
import org.dochub.idea.arch.manifests.*;
import org.dochub.idea.arch.settings.*;
import org.dochub.idea.arch.wizard.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

import static org.dochub.idea.arch.tools.Consts.*;
public class DocHubToolWindow extends JBCefBrowser {
  private final JBCefJSQuery sourceQuery;
  private final Project project;
  private final Navigation navigation;
  private final JSGateway jsGateway;
  public void reloadHtml(Boolean root) {
    SettingsState settingsState = SettingsState.getInstance();
    String currentURL = root ? "" : getCefBrowser().getURL();
    // Если используем корпоративный портал или портал в режиме DEV mode
    if (settingsState.isEnterprise()) {
      String url = currentURL.length() > 0 ? currentURL : settingsState.enterprisePortal;
      try {
        URIBuilder urlBuilder = new URIBuilder(url);
        urlBuilder.addParameter("$dochub-api-interface-func", sourceQuery.getFuncName());
        url = urlBuilder.toString();
      } catch (URISyntaxException e) {
        throw new RuntimeException(e);
      }
      loadURL(url);
    } else {
      // Если НЕ используем, то грузим из собственных ресурсов
      InputStream input = getClass().getClassLoader().getResourceAsStream("html/plugin.html");
      String html;
      try {
        assert input != null;
        html = new String(input.readAllBytes(), StandardCharsets.UTF_8);
      } catch (IOException e) {
        html = e.toString();
      }

      html = html.replaceAll("\\%\\$dochub-api-interface-func\\%", sourceQuery.getFuncName());

      if (currentURL.length() > 0) {
        loadHTML(html, currentURL);
      } else {
        loadHTML(html);
      }
    }
    if (!SystemInfoRt.isWindows)
      getCefBrowser().getUIComponent().setFocusable(false);
  }
  private JBCefJSQuery.Response requestProcessing(String json) {
    // openDevtools();
    StringBuilder result = new StringBuilder();
    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode jsonObj = mapper.readTree(json);
      JsonNode jsonURL = jsonObj.get("url");
      if (jsonURL != null) {
        String url = jsonURL.asText();
        if (url.equals(ROOT_SOURCE_URI)) {
          Map<String, Object> response = new HashMap<>();
          String rootName = project.getBasePath() + "/" + CacheBuilder.getRootManifestName(project);
          File rootFile = new File(rootName);
          if (!rootFile.exists()) {
            return new JBCefJSQuery.Response("", 404, "No found: " + url);
          }
          response.put("contentType", rootName.substring(rootName.length() - 4).toLowerCase(Locale.ROOT));
          response.put("data", Files.readString(Path.of(rootName)));
          result.append(mapper.writeValueAsString(response));
        } else if (url.equals(PLANTUML_RENDER_SVG_URI)) {
          JsonNode jsonSource = jsonObj.get("source");
          String source = jsonSource != null ? jsonSource.asText() : "@startuml\n@enduml";
          Map<String, Object> response = new HashMap<>();
          response.put("data", PlantUMLDriver.makeSVG(source));
          result.append(mapper.writeValueAsString(response));
        } else if (url.equals(NAVI_GOTO_SOURCE_URI)) {
          navigation.go(jsonObj);
        } else if (url.equals(WIZARD_INIT_URI)) {
          JsonNode jsonMode = jsonObj.get("mode");
          String mode = jsonMode != null ? jsonMode.asText() : "production";
          if (mode.equals("example")) {
            (new RootManifest()).createExampleManifest(project);
          } else {
            (new RootManifest()).createRootManifest(project);
          }
          reloadHtml(false);
        } else if ((url.length() > 20) && url.startsWith(ROOT_SOURCE_PATH)) {
          String basePath = project.getBasePath() + "/";
          String parentPath = (new File(CacheBuilder.getRootManifestName(project))).getParent();
          String sourcePath =
                  basePath
                          + (parentPath != null ? parentPath + "/" : "")
                          + url.substring(20).split("\\?")[0];
          File file = new File(sourcePath);
          if (!file.exists() || file.isDirectory()) {
            return new JBCefJSQuery.Response("", 404, "No found: " + url);
          }
          Map<String, Object> response = new HashMap<>();
          String contentType = FilenameUtils.getExtension(sourcePath).toLowerCase(Locale.ROOT);
          response.put("contentType", contentType);
          if (contentType.equals("jpg") || contentType.equals("jpeg") || contentType.equals("svg")
                  || contentType.equals("png")) {
            response.put("data", Files.readAllBytes(Path.of(sourcePath)));
          } else
            response.put("data", Files.readString(Path.of(sourcePath)));
          result.append(mapper.writeValueAsString(response));
        } else if (url.equals(ACTION_PULL_URI)) {
          Map<String, Object> response = new HashMap<>();
          response.put("contentType", "json");
          response.put("data", jsGateway.pullJSONMessage());
          result.append(mapper.writeValueAsString(response));
        } else if (url.equals(ACTION_DOWNLOAD_URI)) { // Сохранение файлов из WEB морды
          JsonNode jsonContent = jsonObj.get("content");
          JsonNode jsonTitle = jsonObj.get("title");
          JsonNode jsonDescription = jsonObj.get("description");
          if (jsonContent != null) {
            Download.download(
                    jsonContent.asText(),
                    jsonTitle != null ? jsonTitle.asText() : "",
                    jsonDescription != null ? jsonDescription.asText() : ""
            );
          }
        } else if (url.equals(DEVTOOL_SHOW_URI)){
          openDevtools();
          getCefBrowser().executeJavaScript("console.info('GO!!');", "events.js", 0);
        } else if (url.equals(HTML_RELOAD_URI)){
          reloadHtml(false);
        } else if (url.equals(ENTITIES_APPLY_SCHEMA)) {
          JsonNode schema = jsonObj.get("schema");
          EntityManager.applySchema(project, schema.asText());
        } else if (url.equals(CLIPBOARD_COPY)) {
          Clipboard.copy(jsonObj.get("data").asText());
        } else if (url.equals(GET_SETTINGS)) {
          SettingsState settingsState = SettingsState.getInstance();
          Map<String, Object> response = new HashMap<>();
          response.put("isEnterprise", settingsState.isEnterprise());
          // todo Нужно перенести в какой-то конфиг
          response.put("api", "1.0.0");

          Map<String, Object> render = new HashMap<>();
          render.put("external", settingsState.renderIsExternal);
          render.put("mode", settingsState.renderMode);
          render.put("server", settingsState.serverRendering);
          render.put("request_type", "GET");
          response.put("render", render);

          result.append(mapper.writeValueAsString(response));
        } else {
          return new JBCefJSQuery.Response("", 404, "No found: " + url);
        }
      }
    } catch (IOException e1) {
      return new JBCefJSQuery.Response("", 500, e1.toString());
    }
    return new JBCefJSQuery.Response(result.toString());
  }
  public DocHubToolWindow(Project project) {
    super("/");

    PlantUMLDriver.init();

    ApplicationManager.getApplication().getMessageBus().connect().subscribe(SettingsState.ON_SETTING_CHANGED, new SettingsState.DocHubSettingsMessage() {
      @Override
      public void on() {
        reloadHtml(true);
      }
    });

    this.project = project;

    MessageBusConnection eventBus = project.getMessageBus().connect();
    navigation = new Navigation(project);
    jsGateway = new JSGateway(project);
    eventBus.subscribe(VirtualFileManager.VFS_CHANGES, new BulkFileListener() {
      @Override
      public void after(@NotNull List<? extends VFileEvent> events) {
        String rootManifest = CacheBuilder.getRootManifestName(project);
        events.forEach(event -> {
          if (event.getFile() != null &&
                  (
                          event instanceof VFileContentChangeEvent
                          ||  event instanceof VFileDeleteEvent
                  )
          ) {
            String path = event.getFile().getPath();
            int bpLength = Objects.requireNonNull(project.getBasePath()).length();
            if (path.length() > bpLength) {
              String source = path.substring(bpLength + 1);
              if (source.equals(rootManifest)) source = ROOT_SOURCE;
              jsGateway.appendMessage(ACTION_SOURCE_CHANGED, ROOT_SOURCE_PATH + source, null);
            }
          }
        });
      }
    });
    sourceQuery = JBCefJSQuery.create((JBCefBrowserBase)this);
    sourceQuery.addHandler(this::requestProcessing);
    reloadHtml(true);
  }
}
