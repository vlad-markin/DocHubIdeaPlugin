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

public class Technologies extends CustomProvider {
    private static final String keyword = "technologies";
    private static final String[] keys = {
            "sections", "items"
    };

    public static final ElementPattern<? extends PsiElement> rootPattern = PlatformPatterns.or(
            PlatformPatterns.psiElement()
                    .withSuperParent(2,
                            psi(YAMLKeyValue.class)
                                    .withName(PlatformPatterns.string().equalTo(keyword))
                    )
                    .withSuperParent(4, psi(YAMLDocument.class)),
            PlatformPatterns.psiElement()
                    .withSuperParent(3,
                            psi(YAMLKeyValue.class)
                                    .withName(PlatformPatterns.string().equalTo(keyword))
                    )
                    .withSuperParent(5, psi(YAMLDocument.class))
    );

    @Override
    public void appendToCompletion(CompletionContributor completion) {
        completion.extend(
                CompletionType.BASIC,
                rootPattern,
                new CompletionProvider<>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               @NotNull ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        for (final String key : keys) {
                            resultSet.addElement(LookupElementBuilder.create(key));
                        }
                    }
                }
        );
    }
}
