package info.dochub.idea.arch.completions.providers;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import info.dochub.idea.arch.completions.CompletionKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLKeyValue;

import java.util.Collection;
import java.util.List;

public class Technologies extends FilteredCustomProvider {
    private static final String KEYWORD = "technologies";
    private static final String[] keys = {
            "sections", "items"
    };

    private static final Collection<CompletionKey> COMPLETION_KEYS = List.of(
            new CompletionKey("sections", CompletionKey.ValueType.MAP),
            new CompletionKey("items", CompletionKey.ValueType.MAP)
    );


    public static final ElementPattern<? extends PsiElement> rootPattern = PlatformPatterns.or(
            PlatformPatterns.psiElement()
                    .withSuperParent(2,
                            psi(YAMLKeyValue.class)
                                    .withName(PlatformPatterns.string().equalTo(KEYWORD))
                    )
                    .withSuperParent(4, psi(YAMLDocument.class)),
            PlatformPatterns.psiElement()
                    .withSuperParent(3,
                            psi(YAMLKeyValue.class)
                                    .withName(PlatformPatterns.string().equalTo(KEYWORD))
                    )
                    .withSuperParent(5, psi(YAMLDocument.class))
    );

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
        return 1;
    }
}
