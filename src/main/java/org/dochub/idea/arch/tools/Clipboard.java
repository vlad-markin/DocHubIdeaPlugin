package org.dochub.idea.arch.tools;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.ide.CopyPasteManager;

import java.awt.datatransfer.StringSelection;

public class Clipboard {
    public static void copy(String data) {
        Application app = ApplicationManager.getApplication();
        app.invokeLater(new Runnable() {
            @Override
            public void run() {
                CopyPasteManager copyPasteManager = CopyPasteManager.getInstance();
                copyPasteManager.setContents(new StringSelection(data));
            }
        }, ModalityState.NON_MODAL);
    }

}
