package org.dochub.idea.arch.views;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.*;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import org.dochub.idea.arch.manifests.PlantUMLDriver;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ViewerPanel extends JPanel implements VisibleAreaListener {
    Editor editor;
    private ComponentListener componentListener;
    private DocumentListener documentListener;
    private SelectionListener selectionListener;
    private JPanel container;

    public ViewerPanel(Project project, FileEditor fileEditor, JPanel container) {
        PlantUMLDriver.test();

        this.container = container;

        componentListener = new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                updateSize();
                revalidate();
                repaint();
            }
        };
        container.addComponentListener(componentListener);

        documentListener = new DocumentListener() {
            @Override
            public void documentChanged(@NotNull DocumentEvent event) {
                updateSize();
                revalidate();
                repaint();
            }
        };

        editor = ((TextEditor) fileEditor).getEditor();
        editor.getDocument().addDocumentListener(documentListener);

        editor.getScrollingModel().addVisibleAreaListener(this);

        selectionListener = new SelectionListener() {
            @Override
            public void selectionChanged(@NotNull SelectionEvent e) {
                SelectionListener.super.selectionChanged(e);
                repaint();
            }
        };

        editor.getSelectionModel().addSelectionListener(selectionListener);

        JButton button = new JButton("I am a button");
        add(button);
        updateSize();
    }

    @Override
    public void visibleAreaChanged(@NotNull VisibleAreaEvent e) {
        updateSize();
    }

    public void onClose() {
        container.removeComponentListener(componentListener);
        editor.getDocument().removeDocumentListener(documentListener);
        editor.getSelectionModel().removeSelectionListener(selectionListener);
    }

    private void updateSize() {
        this.setPreferredSize(new Dimension(110, 0));
    }

    @Override
    public void paint(Graphics gfx) {
        gfx.setColor(Color.CYAN);
        gfx.fillRect(0, 0, 100, 100);
    }
}
