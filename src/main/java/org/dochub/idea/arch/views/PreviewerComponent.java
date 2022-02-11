package org.dochub.idea.arch.views;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.components.*;

public class PreviewerComponent implements ProjectComponent {

    private EditorPanelInjector injector = null;

    public PreviewerComponent(Project project) {
        super();
        injector = new EditorPanelInjector(project);
    }

}
