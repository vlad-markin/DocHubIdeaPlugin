package info.dochub.idea.arch.jsonata.psi;

import com.intellij.psi.tree.IElementType;
import info.dochub.idea.arch.jsonata.JSONataLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class JSONataTokenType extends IElementType {
    public JSONataTokenType(@NotNull @NonNls String debugName) {
        super(debugName, JSONataLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "JSONataTokenType." + super.toString();
    }
}
