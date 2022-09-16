package org.dochub.idea.arch.references.providers;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;
import org.dochub.idea.arch.completions.providers.Components;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLMapping;

import static org.dochub.idea.arch.references.Consts.*;

public class RefDocsID extends RefBaseID {
    private final static String keyword = "docs";

    @Override
    protected String getKeyword() {
        return keyword;
    }

    static public ElementPattern<? extends PsiElement> pattern() {
        return PlatformPatterns.or(
                // Ссылки в идентификаторах документов
                PlatformPatterns.psiElement(YAMLKeyValue.class)
                        .withParent(psi(YAMLMapping.class))
                        .withSuperParent(2,
                                psi(YAMLKeyValue.class)
                                        .withName(PlatformPatterns.string().equalTo(keyword))
                                        .withSuperParent(2, psi(YAMLDocument.class))
                        )
                ,
                PlatformPatterns.psiElement()
                        .notEmpty()
                        .afterLeaf(":")
                        .withText(StandardPatterns.string().matches(ID_PATTERN))
                        .withParent(psi(YAMLKeyValue.class)
                                .withName("contract")
                                .withSuperParent(4,
                                        psi(YAMLKeyValue.class)
                                                .withName("links")
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
