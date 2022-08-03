package org.dochub.idea.arch.tools;

import com.intellij.execution.actions.CreateAction;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.components.panels.HorizontalBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class DocHubToolWindowFactory implements ToolWindowFactory {
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    DocHubToolWindow myToolWindow = new DocHubToolWindow(project);
    ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
    // Content content = contentFactory.createContent(myToolWindow.getContent(), "DocHub", false);
    DefaultActionGroup group = new DefaultActionGroup();
    group.add(new AnAction("Reset", "Перезагрузить", AllIcons.Actions.Refresh) {
      @Override
      public void actionPerformed(AnActionEvent e) {
        myToolWindow.reloadHtml();
      }
    });

    group.add(new AnAction("Debug", "Отладка", AllIcons.Actions.StartDebugger) {
      @Override
      public void actionPerformed(AnActionEvent e) {
        myToolWindow.openDevtools();
      }
    });

    final ActionToolbar actionBar = ActionManager.getInstance().createActionToolbar("DH Tools", group, true);
    actionBar.setTargetComponent(toolWindow.getComponent());
    toolWindow.getComponent().add(actionBar.getComponent(), BorderLayout.PAGE_START);
    toolWindow.getComponent().add(myToolWindow.getComponent());
    // toolWindow.getContentManager().addContent(content);
  }

}
