package org.dochub.idea.arch.references.providers;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import org.dochub.idea.arch.completions.providers.Components;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLSequenceItem;

public class RefAspectID extends RefBaseID {
    private final static String keyword = "aspects";

    @Override
    protected String getKeyword() {
        return keyword;
    }

    @Override
    public ElementPattern<? extends PsiElement> getRefPattern() {
        return PlatformPatterns.psiElement()
                .notEmpty()
                .withParent(psi(YAMLSequenceItem.class)
                        .withSuperParent(2,
                                psi(YAMLKeyValue.class)
                                        .withName(PlatformPatterns.string().equalTo(keyword))
                                        .and(Components.rootPattern)
                        )
                );
    }
}
