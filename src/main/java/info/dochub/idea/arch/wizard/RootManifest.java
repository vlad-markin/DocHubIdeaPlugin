package info.dochub.idea.arch.wizard;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import info.dochub.idea.arch.tools.Navigation;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class RootManifest {
    private void executeAction(@NotNull Runnable action) {
        Application app = ApplicationManager.getApplication();
        app.invokeLater(new Runnable() {
            @Override
            public void run() {
                ApplicationManager.getApplication().runWriteAction(action);
            }
        });
    }

    public VirtualFile createExampleManifest(Project project) {
        executeAction(new Runnable() {
            @Override
            public void run() {
                VirtualFile vProject =  ProjectRootManager.getInstance(project).getContentRoots()[0];
                try(InputStream input = getClass().getClassLoader().getResourceAsStream("wizard/example.yaml")) {
                    VirtualFile root = vProject.createChildData(this, "dochub.yaml");
                    root.setBinaryContent(input.readAllBytes());
                    (new Navigation(project)).gotoPsiElement(PsiManager.getInstance(project).findFile(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return null;
    }

    public VirtualFile createRootManifest(Project project) {
        executeAction(new Runnable() {
            @Override
            public void run() {
                VirtualFile vProject =  ProjectRootManager.getInstance(project).getContentRoots()[0];
                try(InputStream input = getClass().getClassLoader().getResourceAsStream("wizard/dochub.yaml")) {
                    VirtualFile root = vProject.createChildData(this, "dochub.yaml");
                    root.setBinaryContent(input.readAllBytes());
                    (new Navigation(project)).gotoPsiElement(PsiManager.getInstance(project).findFile(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return null;
    }
}
