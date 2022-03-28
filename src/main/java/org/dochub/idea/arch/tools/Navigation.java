package org.dochub.idea.arch.tools;

import com.intellij.codeInsight.TargetElementUtil;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;
import com.intellij.util.messages.Topic;
import org.dochub.idea.arch.references.providers.RefBaseID;
import org.dochub.idea.arch.utils.VirtualFileSystemUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class Navigation {
    private Project project;
    private MessageBusConnection connBus;

    private void gotoPsiElement(PsiElement element) {
        AnAction action = ActionManager.getInstance().getAction(IdeActions.ACTION_COPY_REFERENCE);
        AnActionEvent event = new AnActionEvent(null, DataManager.getInstance().getDataContext(),
                ActionPlaces.UNKNOWN, new Presentation(),
                ActionManager.getInstance(), 0);
        action.actionPerformed(event);

        PsiElement navElement = element.getNavigationElement();
        navElement = TargetElementUtil.getInstance().getGotoDeclarationTarget(element, navElement);
        if (navElement instanceof Navigatable) {
            if (((Navigatable) navElement).canNavigate()) {
                ((Navigatable) navElement).navigate(true);
            }
        } else if (navElement != null) {
            int navOffset = navElement.getTextOffset();
            VirtualFile virtualFile = PsiUtilCore.getVirtualFile(navElement);
            if (virtualFile != null) {
                new OpenFileDescriptor(project, virtualFile, navOffset).navigate(true);
            }
        }
    }

    private void gotoComponentByID(String source, String id) {
        VirtualFile vFile = VirtualFileSystemUtils.findFile(
                source.substring(project.getBasePath().length()),
                project
        );

        if (vFile != null) {
            PsiFile targetFile = PsiManager.getInstance(project).findFile(vFile);
            PsiTreeUtil.processElements(targetFile, element -> {
                if (RefBaseID.makeSourcePattern("components", id).accepts(element)) {
                    gotoPsiElement(element);
                    return false;
                }
                return true;
            });
        }
    }


    public Navigation(Project project) {
        this.project = project;
    }

    public void go(String source, String entity, String id) {
        Application app = ApplicationManager.getApplication();
        app.invokeLater(new Runnable() {
            @Override
            public void run() {
                gotoComponentByID(source, id);
            }
        }, ModalityState.NON_MODAL);
    }
}
