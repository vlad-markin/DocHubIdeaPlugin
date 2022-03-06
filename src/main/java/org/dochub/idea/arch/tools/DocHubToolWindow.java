package org.dochub.idea.arch.tools;
// JBCefBrowser (https://intellij-support.jetbrains.com/hc/en-us/community/posts/4403677440146-how-to-add-a-JBCefBrowser-in-toolWindow-)
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.codeInsight.TargetElementUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.ui.jcef.JBCefBrowserBase;
import com.intellij.ui.jcef.JBCefJSQuery;
import com.intellij.util.messages.MessageBusConnection;
import org.dochub.idea.arch.indexing.CacheBuilder;
import org.dochub.idea.arch.manifests.PlantUMLDriver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.ide.BuiltInServerManager;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;

public class DocHubToolWindow extends JBCefBrowser {
  private JBCefJSQuery sourceQuery;
  private Project project;
  private MessageBusConnection eventBus;
  private Integer changeCounter = 0;

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
        } else if (url.substring(0, 20).equals("plugin:/idea/source/")) {
          String path = project.getBasePath() + "/"
                          + (new File(CacheBuilder.getRootManifestName(project))).getParent()
                          + "/" + url.substring(20);
          File file = new File(path);
          if (!file.exists() || file.isDirectory()) {
            return new JBCefJSQuery.Response("", 404, "No found: " + url);
          }
          Map<String, Object> response = new HashMap<>();
          response.put("contentType", path.substring(path.length() - 4).toLowerCase(Locale.ROOT));
          response.put("data", Files.readString(Path.of(path)));
          result.append(mapper.writeValueAsString(response));
        } else if (url.equals("plugin:/idea/change/index")) {
          Map<String, Object> response = new HashMap<>();
          response.put("data", changeCounter);
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

  public DocHubToolWindow(ToolWindow toolWindow, Project project) {
    super("about:blank");

    eventBus = project.getMessageBus().connect();

    eventBus.subscribe(VirtualFileManager.VFS_CHANGES, new BulkFileListener() {
      @Override
      public void after(@NotNull List<? extends VFileEvent> events) {
        changeCounter++;
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
