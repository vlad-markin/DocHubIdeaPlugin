package org.dochub.idea.arch.completions.providers;

import com.intellij.patterns.ElementPattern;
import com.intellij.psi.PsiElement;
import org.dochub.idea.arch.completions.CompletionKey;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class Dataset extends FilteredCustomProvider {
    private static final String KEYWORD = "datasets";

    private static final Collection<CompletionKey> COMPLETION_KEYS = List.of(
            new CompletionKey("origin"),
            new CompletionKey("source")
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
