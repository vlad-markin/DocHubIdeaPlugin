package org.dochub.idea.arch.tools;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileChooser.*;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFileWrapper;
import com.intellij.util.messages.Topic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Download {
    Project project;

    public interface DownloadMessage {
        void download(String content, String title, String description);
    }
    public static Topic<DownloadMessage> ON_DOWNLOAD_MESSAGE = Topic.create("Navigate to", DownloadMessage.class);

    public Download(Project project) {
        this.project = project;
        project.getMessageBus().connect().subscribe(ON_DOWNLOAD_MESSAGE,
                new DownloadMessage() {
                    @Override
                    public void download(String content, String title, String description) {
                        ApplicationManager.getApplication().invokeAndWait(new Runnable() {
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
                });
    }

    public void download(String content, String title, String description) {
        DownloadMessage publisher = project.getMessageBus().syncPublisher(ON_DOWNLOAD_MESSAGE);
        publisher.download(content, title, description);
    }
}
