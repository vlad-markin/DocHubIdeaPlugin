package org.dochub.idea.arch.completions.providers.docs;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import org.dochub.idea.arch.completions.providers.suggets.IDSuggestDocuments;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLMapping;

public class DocID extends IDSuggestDocuments {
    private static String keyword = "docs";

    @Override
    protected ElementPattern<? extends PsiElement> getPattern() {
        return PlatformPatterns.or(
                        PlatformPatterns.psiElement()
                                .withSuperParent(2, psi(YAMLMapping.class))
                                .withSuperParent(3,
                                        psi(YAMLKeyValue.class)
                                                .withName(PlatformPatterns.string().equalTo(keyword))
                                                .withSuperParent(2, psi(YAMLDocument.class))
                                ),
                        PlatformPatterns.psiElement()
                                .withSuperParent(2,
                                        psi(YAMLKeyValue.class)
                                                .withName(PlatformPatterns.string().equalTo(keyword))
                                                .withSuperParent(2, psi(YAMLDocument.class))
                                )
                );
    }

}
