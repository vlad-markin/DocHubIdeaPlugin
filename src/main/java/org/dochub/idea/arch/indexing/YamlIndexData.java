package org.dochub.idea.arch.indexing;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.gist.GistManager;
import com.intellij.util.gist.VirtualFileGist;
import com.intellij.util.io.DataExternalizer;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class YamlIndexData  {

    public static class IndexData {

    }

    public static IndexData getIndexInfo(@NotNull VirtualFile file, @NotNull Project project) {
        return ourGist.getFileData(project, file);
    }

    private static final DataExternalizer<IndexData> ourValueExternalizer = new DataExternalizer<>() {
        @Override
        public void save(@NotNull DataOutput out, IndexData value) throws IOException {
        }

        @Override
        public IndexData read(@NotNull DataInput in) throws IOException {
            return null;
        }

    };

    private static final VirtualFileGist<IndexData> ourGist =
            GistManager.getInstance().newVirtualFileGist("DocHubIDs", 1, ourValueExternalizer, (project, file) -> {
                if (!file.isInLocalFileSystem()) {
                    return null;
                }

                FileType fileType = file.getFileType();

                byte[] content;
                try {
                    content = file.contentsToByteArray();
                }
                catch (IOException e) {
                    Logger.getInstance(IndexData.class).error(e);
                    return null;
                }
                return fileType == null ? null : new IndexData();
            });
}
