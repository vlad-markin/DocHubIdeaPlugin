package org.dochub.idea.arch.references.providers;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import org.dochub.idea.arch.completions.providers.Components;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLSequenceItem;

public class RefDocsID extends RefBaseID {
    private final static String keyword = "docs";

    @Override
    protected String getKeyword() {
        return keyword;
    }

    @Override
    public ElementPattern<? extends PsiElement> getRefPattern() {
        return PlatformPatterns.psiElement()
                .notEmpty()
                .withParent(psi(YAMLKeyValue.class)
                                .withName("contract")
                                .withSuperParent(4,
                                        psi(YAMLKeyValue.class)
                                                .withName("links")
                                                .and(Components.rootPattern)
                                )
                );
    }
}
