package org.dochub.idea.arch.references.providers;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.*;
import org.dochub.idea.arch.references.Consts;
import org.dochub.idea.arch.completions.providers.Components;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLMapping;
import org.jetbrains.yaml.psi.YAMLSequenceItem;

public class RefAspectID extends RefBaseID {
    private final static String keyword = "aspects";

    @Override
    protected String getKeyword() {
        return keyword;
    }

    static public ElementPattern<? extends PsiElement> pattern() {
        return PlatformPatterns.or(
                // Ссылки в идентификаторах аспектов
                PlatformPatterns.psiElement(YAMLKeyValue.class)
                        .withParent(psi(YAMLMapping.class))
                        .withSuperParent(2,
                                psi(YAMLKeyValue.class)
                                        .withName(PlatformPatterns.string().equalTo(keyword))
                                        .withSuperParent(2, psi(YAMLDocument.class))
                        )
                ,
                // Ссылки в идентификаторах компонентов
                PlatformPatterns.psiElement()
                        .notEmpty()
                        .withText(StandardPatterns.string().matches(Consts.ID_PATTERN))
                        .withParent(psi(YAMLSequenceItem.class)
                                .withSuperParent(2,
                                        psi(YAMLKeyValue.class)
                                                .withName(PlatformPatterns.string().equalTo(keyword))
                                                .and(Components.rootPattern)
                                )
                        )
        );
    }


    @Override
    public ElementPattern<? extends PsiElement> getRefPattern() {
        return pattern();
    }
}
