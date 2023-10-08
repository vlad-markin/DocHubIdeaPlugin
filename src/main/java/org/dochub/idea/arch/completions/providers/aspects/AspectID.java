package org.dochub.idea.arch.completions.providers.aspects;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import org.dochub.idea.arch.completions.providers.suggets.IDSuggestAspects;
import org.dochub.idea.arch.completions.providers.CustomProvider;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLMapping;

public class AspectID extends IDSuggestAspects {
    private static String keyword = "aspects";

    @Override
    protected ElementPattern<? extends PsiElement> getPattern() {
        return PlatformPatterns.or(
                        PlatformPatterns.psiElement()
                                .withSuperParent(2, CustomProvider.psi(YAMLMapping.class))
                                .withSuperParent(3,
                                        CustomProvider.psi(YAMLKeyValue.class)
                                                .withName(PlatformPatterns.string().equalTo(keyword))
                                                .withSuperParent(2, CustomProvider.psi(YAMLDocument.class))
                                ),
                        PlatformPatterns.psiElement()
                                .withSuperParent(2,
                                        CustomProvider.psi(YAMLKeyValue.class)
                                                .withName(PlatformPatterns.string().equalTo(keyword))
                                                .withSuperParent(2, CustomProvider.psi(YAMLDocument.class))
                                )
                );
    }

}
