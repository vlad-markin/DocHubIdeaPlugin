package info.dochub.idea.arch.completions.providers.components;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import info.dochub.idea.arch.completions.providers.Components;
import info.dochub.idea.arch.completions.providers.suggets.IDSuggestComponents;
import org.jetbrains.yaml.psi.YAMLKeyValue;

public class ComponentLinksID extends IDSuggestComponents {
    @Override
    protected ElementPattern<? extends PsiElement> getPattern() {
        return PlatformPatterns.psiElement()
                .withSuperParent(2,
                        psi(YAMLKeyValue.class)
                                .withName("id")
                                .withSuperParent(4,
                                        psi(YAMLKeyValue.class)
                                                .withName("links")
                                                .and(Components.rootPattern)
                                )
                );
    }
}
