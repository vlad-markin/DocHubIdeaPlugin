package org.dochub.idea.arch.views;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBSplitter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class EditorPanelInjector implements FileEditorManagerListener {
    Project project = null;
    HashMap<FileEditor, ViewerPanel> panels = new HashMap<>();

    public EditorPanelInjector(Project project) {
        super();
        this.project = project;
    }

    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        FileEditor editors[] = source.getEditors(file);
        for (FileEditor editor : editors) {
            inject(editor);
        }
    }

    @Override
    public void fileClosed(FileEditorManager fem, VirtualFile virtualFile) {
        freeUnusedPanels(fem);
    }

    private JPanel getPanel(FileEditor editor) {
        if (!(editor instanceof TextEditor)) {
            return null;
        }

        try {
            JPanel outerPanel = (JPanel) editor.getComponent();
            BorderLayout outerLayout = (BorderLayout) outerPanel.getLayout();
            Component layoutComponent = outerLayout.getLayoutComponent(BorderLayout.CENTER);

            if (layoutComponent instanceof JBSplitter) {
                // editor is inside firstComponent of a JBSplitter
                JPanel editorComp = (JPanel) ((JBSplitter) layoutComponent).getFirstComponent();
                layoutComponent = ((BorderLayout) editorComp.getLayout()).getLayoutComponent(BorderLayout.CENTER);
            }

            JLayeredPane pane = (JLayeredPane) layoutComponent;
            JPanel panel = pane.getComponentCount() > 1
                    ? (JPanel)pane.getComponent(1)
                    : (JPanel) pane.getComponent(0);

            return panel;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void inject(FileEditor editor) {
        JPanel panel = getPanel(editor);
        if (panel == null) return;

        BorderLayout innerLayout = (BorderLayout) panel.getLayout();

        String where = BorderLayout.LINE_END;

        if (innerLayout.getLayoutComponent(where) == null) {
            ViewerPanel viewer = new ViewerPanel(project, editor, panel);
            viewer.setVisible(true);
            panel.add(viewer, where);
            panels.put(editor, viewer);
        }
    }

    private void uninject(FileEditor editor) {
        JPanel panel = getPanel(editor);
        BorderLayout innerLayout = (BorderLayout) panel.getLayout();
        Component rightPanel = innerLayout.getLayoutComponent(BorderLayout.LINE_END);
        panel.remove(rightPanel);
    }

    private void freeUnusedPanels(FileEditorManager fem) {
        HashSet unseen = new HashSet<>(panels.keySet());
        for (FileEditor editor : fem.getAllEditors()) {
            if (unseen.contains(editor)) {
                unseen.remove(editor);
            }
        }

        for (FileEditor editor : panels.keySet()) {
            panels.get(editor).onClose();
            uninject(editor);
            panels.remove(editor);
        }
    }
}
