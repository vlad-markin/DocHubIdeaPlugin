package org.dochub.idea.arch.wizard;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import org.dochub.idea.arch.tools.Navigation;

import java.io.*;

public class RootManifest {
    public VirtualFile createExampleManifest(Project project) {
        Application app = ApplicationManager.getApplication();
        app.invokeLater(new Runnable() {
            @Override
            public void run() {
                VirtualFile vProject =  ProjectRootManager.getInstance(project).getContentRoots()[0];
                try {
                    InputStream input = getClass().getClassLoader().getResourceAsStream("wizard/example.yaml");
                    VirtualFile root = vProject.createChildData(this, "dochub.yaml");
                    root.setBinaryContent(input.readAllBytes());
                    (new Navigation(project)).gotoPsiElement(PsiManager.getInstance(project).findFile(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, ModalityState.NON_MODAL);

        return null;
    }
    public VirtualFile createRootManifest(Project project) {
        Application app = ApplicationManager.getApplication();
        app.invokeLater(new Runnable() {
            @Override
            public void run() {
                VirtualFile vProject =  ProjectRootManager.getInstance(project).getContentRoots()[0];
                try {
                    InputStream input = getClass().getClassLoader().getResourceAsStream("wizard/dochub.yaml");
                    VirtualFile root = vProject.createChildData(this, "dochub.yaml");
                    root.setBinaryContent(input.readAllBytes());
                    (new Navigation(project)).gotoPsiElement(PsiManager.getInstance(project).findFile(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, ModalityState.NON_MODAL);

        return null;
    }
}
