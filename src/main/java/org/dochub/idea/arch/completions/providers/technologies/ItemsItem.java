package org.dochub.idea.arch.completions.providers.technologies;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.dochub.idea.arch.completions.providers.CustomProvider;
import org.dochub.idea.arch.completions.providers.Technologies;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;

public class ItemsItem extends CustomProvider {
    private static String keyword = "items";
    private static final String[] keys = {
            "title"
    };

    public static final ElementPattern<? extends PsiElement> rootPattern = PlatformPatterns.or(
            PlatformPatterns.psiElement()
                    .withSuperParent(4,
                            psi(YAMLKeyValue.class)
                                    .withName(PlatformPatterns.string().equalTo(keyword))
                                    .and(Technologies.rootPattern)
                    ),
            PlatformPatterns.psiElement()
                    .withSuperParent(5,
                            psi(YAMLKeyValue.class)
                                    .withName(PlatformPatterns.string().equalTo(keyword))
                                    .and(Technologies.rootPattern)
                    )
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
