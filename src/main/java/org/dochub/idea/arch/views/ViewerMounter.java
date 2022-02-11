package org.dochub.idea.arch.views;

import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import org.jetbrains.annotations.NotNull;

public class ViewerMounter implements StartupActivity {

    EditorPanelInjector injector;

    @Override
    public void runActivity(@NotNull Project project) {
        injector = new EditorPanelInjector(project);
        project.getMessageBus().connect().subscribe(
                FileEditorManagerListener.FILE_EDITOR_MANAGER,
                injector
        );
    }
}
