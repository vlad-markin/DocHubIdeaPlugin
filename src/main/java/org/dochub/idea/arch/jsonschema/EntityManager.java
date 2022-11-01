package org.dochub.idea.arch.jsonschema;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.ui.GuiUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class EntityManager {
    private static HashMap<String, VirtualFile> schemas = new HashMap<>();

    // Применяет JSONSchema для проекта
    public static VirtualFile applySchema(Project project, String schema) {
        String projectHash = project.getLocationHash();
        VirtualFile currentSchema = schemas.get(projectHash);
        if (currentSchema != null) {
            (new File(currentSchema.getPath())).delete();
        }
        try {
            File file = File.createTempFile("EntityDocHubJSONSchema", ".json");
            FileUtil.writeToFile(file, String.valueOf(schema));
            currentSchema = VfsUtil.findFileByIoFile(file, true);
            schemas.put(projectHash, currentSchema);

            ApplicationManager.getApplication().invokeLater(new Runnable() {
                @Override
                public void run() {
                    PsiManager.getInstance(project).dropResolveCaches();
                    PsiManager.getInstance(project).dropPsiCaches();
                }
            }, ModalityState.defaultModalityState());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return currentSchema;
    }

    // Возвращает JSONSchema для проекта
    public static VirtualFile getSchema(Project project) {
        return schemas.get(project.getLocationHash());
    }
}
