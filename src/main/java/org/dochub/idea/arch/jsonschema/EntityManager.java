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
import com.intellij.util.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class EntityManager {
    private static Map<String, VirtualFile> schemas = new HashMap<>();

    // Применяет JSONSchema для проекта
    public static VirtualFile applySchema(Project project, String schema) {

        String projectHash = project.getLocationHash();
        VirtualFile currentSchema = schemas.get(projectHash);

        if (currentSchema != null) {
            (new File(currentSchema.getPath())).delete();
            schemas.remove(projectHash);
        }

        try {
            String tempDir = System.getProperty("java.io.tmpdir");

            File file = new File(String.format("%s%s%s",tempDir,File.separator,"EntityDocHubJSONSchema.json"));

            FileUtil.writeToFile(file, String.valueOf(schema));
            currentSchema = VfsUtil.findFileByIoFile(file, true);
            schemas.put(projectHash, currentSchema);

            ModalityUiUtil.invokeLaterIfNeeded(ModalityState.defaultModalityState(), () -> {
                PsiManager.getInstance(project).  dropResolveCaches();
                PsiManager.getInstance(project).dropPsiCaches();
            });

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
