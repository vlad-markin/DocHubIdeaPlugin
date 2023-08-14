package org.dochub.idea.arch.jsonata;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.NlsSafe;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class JSONataFileType extends LanguageFileType {
    public static final JSONataFileType INSTANCE = new JSONataFileType();
    private JSONataFileType() {
        super(JSONataLanguage.INSTANCE);
    }

    @Override
    public @NonNls @NotNull String getName() {
        return "JSONata";
    }

    @Override
    public @NlsContexts.Label @NotNull String getDescription() {
        return "JSON query and transformation language";
    }

    @Override
    public @NlsSafe @NotNull String getDefaultExtension() {
        return "jsonata";
    }

    @Override
    public @Nullable Icon getIcon() {
        return JSONataLanguage.ICON;
    }
}
