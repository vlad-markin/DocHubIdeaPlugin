package org.dochub.idea.arch.completions.providers.contexts;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import org.dochub.idea.arch.completions.providers.Contexts;
import org.dochub.idea.arch.completions.providers.CustomProvider;
import org.dochub.idea.arch.utils.SuggestUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLSequenceItem;
import java.util.List;

public class ContextComponents extends CustomProvider {
    private static String keyword = "components";

    @Override
    public void appendToCompletion(CompletionContributor completion) {
        completion.extend(
                CompletionType.BASIC,
                PlatformPatterns.or(
                        PlatformPatterns.psiElement()
                                .withSuperParent(2, psi(YAMLSequenceItem.class))
                                .withSuperParent(4,
                                        psi(YAMLKeyValue.class)
                                                .withName(PlatformPatterns.string().equalTo(keyword))
                                                .and(Contexts.rootPattern)
                                )
                ),
                new CompletionProvider<>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               @NotNull ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {

                        List<String> ids = SuggestUtils.scanYamlStringToID(
                                parameters.getEditor().getDocument().getText()
                                , keyword
                        );
                        for (String id : ids) {
                            resultSet.addElement(LookupElementBuilder.create(id));
                        }
                    }
                }
        );
    }

}
