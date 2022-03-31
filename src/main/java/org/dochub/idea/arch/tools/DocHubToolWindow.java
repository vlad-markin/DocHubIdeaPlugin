package org.dochub.idea.arch.tools;
// JBCefBrowser (https://intellij-support.jetbrains.com/hc/en-us/community/posts/4403677440146-how-to-add-a-JBCefBrowser-in-toolWindow-)
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.codeInsight.TargetElementUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileContentChangeEvent;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.ui.jcef.JBCefBrowserBase;
import com.intellij.ui.jcef.JBCefJSQuery;
import com.intellij.util.messages.MessageBusConnection;
import org.apache.commons.io.FilenameUtils;
import org.dochub.idea.arch.indexing.CacheBuilder;
import org.dochub.idea.arch.manifests.PlantUMLDriver;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Timer;
import java.util.regex.Matcher;

import static org.dochub.idea.arch.tools.Consts.*;

public class DocHubToolWindow extends JBCefBrowser {
  private JBCefJSQuery sourceQuery;
  private Project project;
  private MessageBusConnection eventBus;
  private Boolean doRepair = false;
  private Timer timer;
  private TimerTask observer = null;
  private Navigation navigation;
  private JSGateway jsGateway = null;

  private void startObserver() {
    doRepair = false;
    if (observer == null) {
      observer = new TimerTask() {
        public void run() {
          if (doRepair) reloadHtml();
          doRepair = true;
        }
      };
      timer = new Timer("DocHub observer");
      timer.scheduleAtFixedRate(observer, 5000L, 5000L);
    }
  }

  private void reloadHtml() {
    InputStream input = getClass().getClassLoader().getResourceAsStream("html/plugin.html");
    String html = "";
    try {
      html = new String(input.readAllBytes(), StandardCharsets.UTF_8);
      String injectionCode = sourceQuery.inject("data","resolve","reject");
      html = html.replaceAll("\"API_INJECTION\"", Matcher.quoteReplacement(injectionCode));
    } catch (IOException e) {
      html = e.toString();
    }

    loadHTML(html);
  }

  public static VirtualFile getGotoFile(final Project project, final VirtualFile file) {
    PsiElement element = PsiManager.getInstance(project).findFile(file);
    if (element != null) {
      PsiElement navElement = element.getNavigationElement();
      navElement = TargetElementUtil.getInstance().getGotoDeclarationTarget(element, navElement);
      if (navElement != null && navElement.getContainingFile() != null) {
        return navElement.getContainingFile().getVirtualFile();
      }
    }
    return file;
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
          String rootManifest = CacheBuilder.getRootManifestName(project);
          if (rootManifest == null || rootManifest.length() < 5) {
            return new JBCefJSQuery.Response("", 404, "No found: " + url);
          }
          response.put("contentType", rootManifest.substring(rootManifest.length() - 4).toLowerCase(Locale.ROOT));
          response.put("data", Files.readString(Path.of(project.getBasePath() + "/" + rootManifest)));
          result.append(mapper.writeValueAsString(response));
        } else if (url.equals(PLANTUML_RENDER_SVG_URI)) {
          JsonNode jsonSource = jsonObj.get("source");
          String source = jsonSource != null ? jsonSource.asText() : "@startuml\n@enduml";
          Map<String, Object> response = new HashMap<>();
          response.put("data", PlantUMLDriver.makeSVG(source));
          result.append(mapper.writeValueAsString(response));
        } else if (url.equals(NAVI_GOTO_SOURCE_URI)) {
          JsonNode jsonSource = jsonObj.get("source");
          JsonNode jsonID = jsonObj.get("id");
          if ((jsonSource != null) && (jsonID != null)) {
            String id = jsonID.asText();
            String source = jsonSource.asText();
            File file;
            String basePath = project.getBasePath() + "/";
            if (source.equals(ROOT_SOURCE_URI)) {
              source = basePath + CacheBuilder.getRootManifestName(project);
            } else {
              String parentPath = (new File(CacheBuilder.getRootManifestName(project))).getParent();
              source = basePath + (parentPath != null ? parentPath + "/" : "") + source.substring(20);
            }
            file = new File(source);
            if (file.exists() || !file.isDirectory()) {
              navigation.go(source, "component", id);
            }
          }
        } else if ((url.length() > 20) && url.substring(0, 20).equals(ROOT_SOURCE_PATH)) {
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
    startObserver();
    return new JBCefJSQuery.Response(result.toString());
  }

  public DocHubToolWindow(ToolWindow toolWindow, Project project) {
    super("/");

    eventBus = project.getMessageBus().connect();
    navigation = new Navigation(project);
    jsGateway = new JSGateway(project);

    eventBus.subscribe(VirtualFileManager.VFS_CHANGES, new BulkFileListener() {
      @Override
      public void after(@NotNull List<? extends VFileEvent> events) {
        String rootManifest = CacheBuilder.getRootManifestName(project);
        events.forEach(event -> {
          if (event instanceof VFileContentChangeEvent &&
                  event.getFile() != null) {
            String source = event.getFile().getPath().substring(project.getBasePath().length() + 1);
            if (source.equals(rootManifest)) source = ROOT_SOURCE;
            jsGateway.appendMessage(ACTION_SOURCE_CHANGED, ROOT_SOURCE_PATH + source, null);
          }
        });
      }
    });

    this.openDevtools();
    sourceQuery = JBCefJSQuery.create((JBCefBrowserBase)this);
    sourceQuery.addHandler((params) -> requestProcessing(params));

    reloadHtml();

    this.project = project;
  }

  public JComponent getContent() {
    return getComponent();
  }
}
