package org.dochub.idea.arch.references.providers;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.*;
import org.dochub.idea.arch.references.Consts;
import org.dochub.idea.arch.completions.providers.Components;
import org.dochub.idea.arch.completions.providers.Contexts;
import org.jetbrains.yaml.psi.*;

public class RefComponentID extends RefBaseID {
    static private final String keyword = "components";

    static public ElementPattern<? extends PsiElement> pattern() {
        return PlatformPatterns.or(
                // Ссылки в идентификаторах компонентов
                PlatformPatterns.psiElement(YAMLKeyValue.class)
                        .withParent(psi(YAMLMapping.class))
                        .withSuperParent(2,
                                psi(YAMLKeyValue.class)
                                        .withName(PlatformPatterns.string().equalTo(keyword))
                                        .withSuperParent(2, psi(YAMLDocument.class))
                        )
                ,
                // Ссылки в линках компонентов
                PlatformPatterns.psiElement()
                        .notEmpty()
                        .afterLeaf(":")
                        .withText(StandardPatterns.string().matches(Consts.ID_PATTERN))
                        .withParent(
                                psi(YAMLKeyValue.class)
                                        .withName("id")
                                        .withSuperParent(4,
                                                psi(YAMLKeyValue.class)
                                                        .withName("links")
                                                        .and(Components.rootPattern)
                                        )
                        )
                ,
                // Ссылки в контекстах
                PlatformPatterns.psiElement()
                        .withText(StandardPatterns.string().matches(Consts.ID_PATTERN))
                        .withParent(psi(YAMLSequenceItem.class)
                                .withSuperParent(2,
                                        psi(YAMLKeyValue.class)
                                                .withName(PlatformPatterns.string().equalTo(keyword))
                                                .and(Contexts.rootPattern)
                                )
                        )
        );
    }

    @Override
    protected String getKeyword() {
        return keyword;
    }

    @Override
    public ElementPattern<? extends PsiElement> getRefPattern() {
        return pattern();
    }
}
