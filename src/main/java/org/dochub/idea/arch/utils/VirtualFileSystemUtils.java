package org.dochub.idea.arch.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;

public class VirtualFileSystemUtils {
    public static VirtualFile findFile(String path, Project project) {
        for (VirtualFile root : ProjectRootManager.getInstance(project).getContentSourceRoots()) {
            VirtualFile rel = root.findFileByRelativePath(path);
            if (rel != null) {
                return rel;
            }
        }
        for (VirtualFile root : ProjectRootManager.getInstance(project).getContentRoots()) {
            VirtualFile rel = root.findFileByRelativePath(path);
            if (rel != null) {
                return rel;
            }
        }
        return null;
    }

}
