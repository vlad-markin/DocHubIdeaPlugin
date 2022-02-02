package org.dochub.idea.arch.completions.providers.contexts;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import org.dochub.idea.arch.completions.providers.Contexts;
import org.dochub.idea.arch.completions.providers.idsuggets.LocationSuggestContexts;
import org.jetbrains.yaml.psi.YAMLKeyValue;

public class ContextLocation extends LocationSuggestContexts {
    private static String keyword = "location";

    @Override
    protected ElementPattern<? extends PsiElement> getPattern() {
        return PlatformPatterns.or(
                PlatformPatterns.psiElement()
                        .withSuperParent(2,
                                psi(YAMLKeyValue.class)
                                        .withName(PlatformPatterns.string().equalTo(keyword))
                                        .and(Contexts.rootPattern)
                        ),
                PlatformPatterns.psiElement()
                        .withSuperParent(3,
                                psi(YAMLKeyValue.class)
                                        .withName(PlatformPatterns.string().equalTo(keyword))
                                        .and(Contexts.rootPattern)
                        )
        );
    }
}
