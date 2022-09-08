package org.dochub.idea.arch.completions.providers;

import com.intellij.patterns.ElementPattern;
import com.intellij.psi.PsiElement;
import org.dochub.idea.arch.completions.CompletionKey;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class Docs extends FilteredCustomProvider {
    private static final String KEYWORD = "docs";

    private static final Collection<CompletionKey> COMPLETION_KEYS = List.of(
            new CompletionKey("icon"),
            new CompletionKey("location"),
            new CompletionKey("description"),
            new CompletionKey("type"),
            new CompletionKey("subjects", CompletionKey.ValueType.LIST),
            new CompletionKey("source"),
            new CompletionKey("origin")
    );

    public static final ElementPattern<? extends PsiElement> rootPattern = Root.makeRootPattern(KEYWORD);

    @Override
    protected @NotNull ElementPattern<? extends PsiElement> getRootPattern() {
        return rootPattern;
    }

    @Override
    protected @NotNull Collection<CompletionKey> getKeys() {
        return COMPLETION_KEYS;
    }

    @Override
    protected int getKeyDocumentLevel() {
        return 2;
    }
}
