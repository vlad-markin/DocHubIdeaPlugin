package org.dochub.idea.arch.completions.providers;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.ElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class Dataset extends CustomProvider {
    private static final String keyword = "datasets";
    private static final String[] keys = {
            "origin", "source"
    };

    public static final ElementPattern<? extends PsiElement> rootPattern = Root.makeRootPattern(keyword);

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
