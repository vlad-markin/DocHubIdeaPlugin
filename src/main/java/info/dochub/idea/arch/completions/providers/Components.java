package info.dochub.idea.arch.completions.providers;

import com.intellij.patterns.ElementPattern;
import com.intellij.psi.PsiElement;
import info.dochub.idea.arch.completions.CompletionKey;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class Components extends FilteredCustomProvider {
    private static final String KEYWORD = "components";
    private static final int KEY_DOCUMENT_LEVEL = 2;

    private static final Collection<CompletionKey> COMPLETION_KEYS = List.of(
            new CompletionKey("title", CompletionKey.ValueType.TEXT),
            new CompletionKey("entity", CompletionKey.ValueType.TEXT),
            new CompletionKey("aspects", CompletionKey.ValueType.LIST),
            new CompletionKey("links", CompletionKey.ValueType.LIST),
            new CompletionKey("technologies", CompletionKey.ValueType.LIST)
    );

    public static final ElementPattern<? extends PsiElement> rootPattern = Root.makeRootPattern(KEYWORD);

    @Override
    protected @NotNull Collection<CompletionKey> getKeys() {
        return COMPLETION_KEYS;
    }

    @Override
    protected int getKeyDocumentLevel() {
        return KEY_DOCUMENT_LEVEL;
    }

    @Override
    protected @NotNull ElementPattern<? extends PsiElement> getRootPattern() {
        return rootPattern;
    }
}
