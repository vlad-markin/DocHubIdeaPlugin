package org.dochub.idea.arch.references.providers;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;
import org.dochub.idea.arch.completions.providers.Components;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLMapping;
import org.jetbrains.yaml.psi.YAMLSequenceItem;

import static org.dochub.idea.arch.references.Consts.ID_PATTERN;

public class RefContextID extends RefBaseID {
    private final static String keyword = "contexts";

    @Override
    protected String getKeyword() {
        return keyword;
    }

    static public ElementPattern<? extends PsiElement> pattern() {
        return PlatformPatterns.psiElement(YAMLKeyValue.class)
                        .withParent(psi(YAMLMapping.class))
                        .withSuperParent(2,
                                psi(YAMLKeyValue.class)
                                        .withName(PlatformPatterns.string().equalTo(keyword))
                                        .withSuperParent(2, psi(YAMLDocument.class))
                        );
    }


    @Override
    public ElementPattern<? extends PsiElement> getRefPattern() {
        return pattern();
    }
}
