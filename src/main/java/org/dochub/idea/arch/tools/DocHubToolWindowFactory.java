package org.dochub.idea.arch.tools;

import com.intellij.icons.*;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.*;
import com.intellij.openapi.wm.*;
import org.jetbrains.annotations.*;

import java.awt.*;

public class DocHubToolWindowFactory implements ToolWindowFactory {
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    DocHubToolWindow myToolWindow = new DocHubToolWindow(project);
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

    group.add(new AnAction("Back", "Назад", AllIcons.Actions.Back) {
      @Override
      public void actionPerformed(AnActionEvent e) {
        myToolWindow.getCefBrowser().goBack();
      }
    });

    final ActionToolbar actionBar = ActionManager.getInstance().createActionToolbar("DH Tools", group, true);
    actionBar.setTargetComponent(toolWindow.getComponent());
    toolWindow.getComponent().add(actionBar.getComponent(), BorderLayout.PAGE_START);
    toolWindow.getComponent().add(myToolWindow.getComponent());
    toolWindow.getComponent().setVisible(true);
  }


}
