package org.dochub.idea.arch.tools;

import com.fasterxml.jackson.databind.JsonNode;
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
import com.intellij.util.messages.MessageBusConnection;
import org.dochub.idea.arch.indexing.CacheBuilder;
import org.dochub.idea.arch.references.providers.RefBaseID;
import org.dochub.idea.arch.utils.VirtualFileSystemUtils;

import java.io.File;
import java.util.Map;

import static org.dochub.idea.arch.tools.Consts.ROOT_SOURCE_URI;

public class Navigation {
    private Project project;
    private MessageBusConnection connBus;

    private static String entityToSection(String entity) {
        String section = null;
        switch (entity) {
            case "component": section = "components"; break;
            case "document": section = "docs"; break;
            case "context": section = "contexts"; break;
            case "aspect": section = "aspects"; break;
        }
        return section;
    }

    public void gotoPsiElement(PsiElement element) {
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

    private void gotoByID(String source, String entity, String id) {
        VirtualFile vFile = VirtualFileSystemUtils.findFile(
                source.substring(project.getBasePath().length()),
                project
        );

        if (vFile != null) {
            String section = entityToSection(entity);
            PsiFile targetFile = PsiManager.getInstance(project).findFile(vFile);
            PsiTreeUtil.processElements(targetFile, element -> {
                if (RefBaseID.makeSourcePattern(section, id).accepts(element)) {
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
                gotoByID(source, entity, id);
            }
        }, ModalityState.NON_MODAL);
    }

    public void go(JsonNode location) {
        JsonNode jsonID = location.get("id");
        JsonNode jsonEntity = location.get("entity");
        JsonNode jsonSource = location.get("source");
        if (jsonID == null || jsonEntity == null) return;
        String id = jsonID.asText();
        String entity = jsonEntity.asText();
        String source = jsonSource.asText();
        if (source.equals("null")) {
            Map<String, Object> cache = CacheBuilder.getProjectCache(project);
            String section = entityToSection(entity);
            if (section == null) return;
            Map<String, Object> components = cache == null ? null : (Map<String, Object>) cache.get(section);
            if (components == null) return;;
            Map<String, Object> files = (Map<String, Object>) components.get(id);
            if (files == null) return;
            for (String file : files.keySet()) {
                source = file;
                break;
            }
        } else {
            source = jsonSource.asText();
            String basePath = project.getBasePath() + "/";
            if (source.equals(ROOT_SOURCE_URI)) {
                source = basePath + CacheBuilder.getRootManifestName(project);
            } else {
                String parentPath = (new File(CacheBuilder.getRootManifestName(project))).getParent();
                source = basePath + (parentPath != null ? parentPath + "/" : "") + source.substring(20);
            }
        }

        File file;
        file = new File(source);
        if (file.exists() || !file.isDirectory()) {
            go(source, entity, id);
        }

    }

}
