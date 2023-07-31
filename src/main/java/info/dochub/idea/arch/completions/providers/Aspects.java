package info.dochub.idea.arch.completions.providers;

import com.intellij.patterns.ElementPattern;
import com.intellij.psi.PsiElement;
import info.dochub.idea.arch.completions.CompletionKey;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class Aspects extends FilteredCustomProvider {
    private static final String KEYWORD = "aspects";
    private static final Collection<CompletionKey> COMPLETION_KEYS = List.of(
            new CompletionKey("title"),
            new CompletionKey("location")
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
