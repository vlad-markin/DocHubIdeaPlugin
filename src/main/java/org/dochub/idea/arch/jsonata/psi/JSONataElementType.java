package org.dochub.idea.arch.jsonata.psi;

import com.intellij.psi.tree.IElementType;
import org.dochub.idea.arch.jsonata.JSONataLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class JSONataElementType  extends IElementType {
    public JSONataElementType(@NotNull @NonNls String debugName) {
        super(debugName, JSONataLanguage.INSTANCE);
    }
}
