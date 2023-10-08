package org.dochub.idea.arch.completions.providers;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class Namespaces extends CustomProvider {
    private static final String keyword = "namespaces";
    private static final String[] keys = {
            "title"
    };

    @Override
    public void appendToCompletion(CompletionContributor completion) {
        completion.extend(
                CompletionType.BASIC,
                Root.makeRootPattern(keyword),
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
