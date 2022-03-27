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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Timer;
import java.util.regex.Matcher;

public class DocHubToolWindow extends JBCefBrowser {
  private JBCefJSQuery sourceQuery;
  private Project project;
  private MessageBusConnection eventBus;
  private Integer changeCounter = 0;
  private static List<String> sourceChanged;
  private Boolean doRepair = false;
  private Timer timer;
  private TimerTask observer = null;

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
        if (url.equals("plugin:/idea/source/$root")) {
          Map<String, Object> response = new HashMap<>();
          String rootManifest = CacheBuilder.getRootManifestName(project);
          if (rootManifest == null || rootManifest.length() < 5) {
            return new JBCefJSQuery.Response("", 404, "No found: " + url);
          }
          response.put("contentType", rootManifest.substring(rootManifest.length() - 4).toLowerCase(Locale.ROOT));
          response.put("data", Files.readString(Path.of(project.getBasePath() + "/" + rootManifest)));
          result.append(mapper.writeValueAsString(response));
        } else if (url.equals("plugin:/idea/plantuml/svg")) {
          JsonNode jsonSource = jsonObj.get("source");
          String source = jsonSource != null ? jsonSource.asText() : "@startuml\n@enduml";
          Map<String, Object> response = new HashMap<>();
          response.put("data", PlantUMLDriver.makeSVG(source));
          result.append(mapper.writeValueAsString(response));
        } else if ((url.length() > 20) && url.substring(0, 20).equals("plugin:/idea/source/")) {
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
        } else if (url.equals("plugin:/idea/change/index")) {
          Map<String, Object> response = new HashMap<>();
          response.put("data", changeCounter);
          response.put("changed", new ArrayList<>(sourceChanged));
          result.append(mapper.writeValueAsString(response));
          sourceChanged.clear();
        } else if (url.equals("plugin:/idea/debugger/show")){
          openDevtools();
        } else if (url.equals("plugin:/idea/reload")){
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

    sourceChanged = new ArrayList<>();
    eventBus = project.getMessageBus().connect();

    eventBus.subscribe(VirtualFileManager.VFS_CHANGES, new BulkFileListener() {
      @Override
      public void after(@NotNull List<? extends VFileEvent> events) {
        String rootManifest = CacheBuilder.getRootManifestName(project);
        events.forEach(event -> {
          if (event instanceof VFileContentChangeEvent &&
                  event.getFile() != null) {
            String source = event.getFile().getPath().substring(project.getBasePath().length() + 1);
            if (source.equals(rootManifest)) source = "$root";
            sourceChanged.add("plugin:/idea/source/" + source);
            changeCounter++;
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
