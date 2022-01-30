package org.dochub.idea.arch.completions.providers;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLTokenTypes;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLMapping;
import org.jetbrains.yaml.psi.YAMLScalar;

public class Root extends CustomProvider {
    private static final String[] rootKeys = {
            "aspects", "components", "contexts", "docs", "forms", "import", "namespaces", "technologies"
    };

    public static ElementPattern<? extends PsiElement> makeRootPattern(String keyword) {
        return PlatformPatterns.or(
                PlatformPatterns.psiElement()
                        .withSuperParent(5,
                                psi(YAMLKeyValue.class)
                                        .withName(PlatformPatterns.string().equalTo(keyword))
                        )
                        .withSuperParent(7, psi(YAMLDocument.class))
                ,
                PlatformPatterns.psiElement()
                        .withSuperParent(4,
                                psi(YAMLKeyValue.class)
                                        .withName(PlatformPatterns.string().equalTo(keyword))
                        )
                        .withSuperParent(6, psi(YAMLDocument.class))
        );
    };

    @Override
    public void appendToCompletion(CompletionContributor completion) {
        completion.extend(CompletionType.BASIC,
                psi(YAMLTokenTypes.TEXT)
                        .withParents(YAMLScalar.class, YAMLMapping.class, YAMLDocument.class),
                new CompletionProvider<>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               @NotNull ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        for (final String key : rootKeys) {
                            resultSet.addElement(LookupElementBuilder.create(key));
                        }
                    }
                }
        );
    }
}
