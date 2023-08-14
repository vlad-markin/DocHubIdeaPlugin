package org.dochub.idea.arch.completions.providers.components;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import org.dochub.idea.arch.completions.providers.suggets.IDSuggestDocuments;
import org.dochub.idea.arch.completions.providers.Components;
import org.dochub.idea.arch.completions.providers.CustomProvider;
import org.jetbrains.yaml.psi.YAMLKeyValue;

public class ComponentContractID extends IDSuggestDocuments {
    @Override
    protected ElementPattern<? extends PsiElement> getPattern() {
        return PlatformPatterns.psiElement()
                .withSuperParent(2,
                        CustomProvider.psi(YAMLKeyValue.class)
                                .withName("contract")
                                .withSuperParent(4,
                                        CustomProvider.psi(YAMLKeyValue.class)
                                                .withName("links")
                                                .and(Components.rootPattern)
                                )
                );
    }
}
