package org.dochub.idea.arch.references.providers;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import org.dochub.idea.arch.completions.providers.Docs;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLSequenceItem;

public class RefDocSource extends RefBaseSource {
    @Override
    public ElementPattern<? extends PsiElement> getRefPattern() {
        return PlatformPatterns.psiElement()
                .withParent(psi(YAMLKeyValue.class)
                                .withName(PlatformPatterns.string().equalTo("source"))
                                .and(Docs.rootPattern)
                );
    }
}
