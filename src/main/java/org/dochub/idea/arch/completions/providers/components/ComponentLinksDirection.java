package org.dochub.idea.arch.completions.providers.components;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import org.dochub.idea.arch.completions.providers.Components;
import org.dochub.idea.arch.completions.providers.CustomProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;

public class ComponentLinksDirection extends CustomProvider {
    private static final String[] keys = {
            "<--", "-->", "--"
    };

    @Override
    public void appendToCompletion(CompletionContributor completion) {
        completion.extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement()
                        .withSuperParent(2,
                                psi(YAMLKeyValue.class)
                                        .withName("direction")
                                        .withSuperParent(4,
                                                psi(YAMLKeyValue.class)
                                                        .withName("links")
                                                        .and(Components.rootPattern)
                                        )
                        )
                ,
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
