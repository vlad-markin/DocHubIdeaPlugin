package org.dochub.idea.arch.jsonata;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class JSONataFile extends PsiFileBase {
    public JSONataFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, JSONataLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return JSONataFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "JSONata File";
    }
}
