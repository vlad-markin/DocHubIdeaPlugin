package org.dochub.idea.arch.tools;
// JBCefBrowser (https://intellij-support.jetbrains.com/hc/en-us/community/posts/4403677440146-how-to-add-a-JBCefBrowser-in-toolWindow-)
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileContentChangeEvent;
import com.intellij.openapi.vfs.newvfs.events.VFileDeleteEvent;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.ui.jcef.JBCefBrowserBase;
import com.intellij.ui.jcef.JBCefJSQuery;
import com.intellij.util.messages.MessageBusConnection;
import org.apache.commons.io.FilenameUtils;
import org.dochub.idea.arch.indexing.CacheBuilder;
import org.dochub.idea.arch.manifests.PlantUMLDriver;
import org.dochub.idea.arch.settings.SettingComponent;
import org.dochub.idea.arch.settings.SettingsState;
import org.dochub.idea.arch.wizard.RootManifest;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;

import static org.dochub.idea.arch.tools.Consts.*;

public class DocHubToolWindow extends JBCefBrowser {
  private final JBCefJSQuery sourceQuery;
  private final Project project;

  private final Navigation navigation;
  private final JSGateway jsGateway;

  private String getInjectionSettings() {
    SettingsState settingsState = SettingsState.getInstance();
    Map<String, Object> settings = new HashMap<>();
    Map<String, Object> render = new HashMap<>();
    render.put("mode", settingsState.renderMode);
    render.put("external", settingsState.renderIsExternal);
    render.put("server", settingsState.serverRendering);
    settings.put("render", render);
    try {
      ObjectMapper mapper = new ObjectMapper();
      return Matcher.quoteReplacement(
              mapper.writeValueAsString(settings)
      );
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public void reloadHtml() {
    InputStream input = getClass().getClassLoader().getResourceAsStream("html/plugin.html");
    String html;
    try {
      assert input != null;
      html = new String(input.readAllBytes(), StandardCharsets.UTF_8);
      String injectionCode = sourceQuery.inject("data","resolve","reject");
      html =
              html.replaceAll("\"API_INJECTION\"", Matcher.quoteReplacement(injectionCode))
              .replaceAll("\"SETTING_INJECTION\"", getInjectionSettings());
    } catch (IOException e) {
      html = e.toString();
    }
    loadHTML(html);
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
          reloadHtml();
        } else if ((url.length() > 20) && url.startsWith(ROOT_SOURCE_PATH)) {
          String basePath = project.getBasePath() + "/";
          String parentPath = (new File(CacheBuilder.getRootManifestName(project))).getParent();
          String sourcePath = basePath + (parentPath != null ? parentPath + "/" : "") + url.substring(20);
          File file = new File(sourcePath);
          if (!file.exists() || file.isDirectory()) {
            return new JBCefJSQuery.Response("", 404, "No found: " + url);
          }
          Map<String, Object> response = new HashMap<>();
          response.put("contentType", FilenameUtils.getExtension(sourcePath).toLowerCase(Locale.ROOT));
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
          reloadHtml();
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

    reloadHtml();
  }
}
