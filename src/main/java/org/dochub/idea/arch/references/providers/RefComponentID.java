package org.dochub.idea.arch.references.providers;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.patterns.StringPattern;
import com.intellij.psi.*;
import org.dochub.idea.arch.completions.providers.Components;
import org.dochub.idea.arch.completions.providers.Contexts;
import org.dochub.idea.arch.utils.PsiUtils;
import org.jetbrains.yaml.psi.*;

public class RefComponentID extends RefBaseID {
    static private final String keyword = "components";
    static private final String idPattern = "(([a-zA-Z0-9\\\\_]*)(\\..[a-zA-Z0-9\\\\_]*|)*)";

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
                        .withText(StandardPatterns.string().matches(idPattern))
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
                        .withText(StandardPatterns.string().matches(idPattern))
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
