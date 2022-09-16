package org.dochub.idea.arch.tools;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileChooser.*;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFileWrapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Download {
    static public void download(String content, String title, String description) {
        Timer timer = new Timer("Download diagram");
        timer.schedule(new TimerTask() {
            public void run() {
                ApplicationManager.getApplication().invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        FileSaverDescriptor descriptor = new FileSaverDescriptor(title, description, new String[]{"svg"});
                        FileSaverDialog dialog = FileChooserFactory.getInstance().createSaveFileDialog(descriptor, (Project) null);
                        VirtualFileWrapper vf = dialog.save((VirtualFile) null, "diagram");

                        if (vf == null) {
                            return;
                        }
                        File file = vf.getFile();
                        try {
                            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
                            writer.append(content);
                            writer.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        }, 100L);

    }
}
