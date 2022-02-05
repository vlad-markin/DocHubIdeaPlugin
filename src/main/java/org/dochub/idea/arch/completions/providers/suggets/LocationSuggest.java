package org.dochub.idea.arch.completions.providers.suggets;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiModificationTracker;
import com.intellij.util.ProcessingContext;
import org.dochub.idea.arch.indexing.CacheFileData;
import org.dochub.idea.arch.utils.PsiUtils;
import org.dochub.idea.arch.utils.SuggestUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class LocationSuggest extends BaseSuggest {
    private Key cacheSectionKey = null;

    protected String getSection() {
        return "undefined";
    }

    public LocationSuggest() {
        cacheSectionKey = Key.create(getSection() + "-loc");
    }

    @Override
    public void appendToCompletion(CompletionContributor completion) {
        completion.extend(
                CompletionType.BASIC,
                getPattern(),
                new CompletionProvider<>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               @NotNull ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        PsiElement psiPosition = parameters.getPosition();
                        Project project = parameters.getPosition().getProject();
                        PsiElement document = PsiUtils.getYamlDocumentByPsiElement(psiPosition);

                        CachedValuesManager cacheManager = CachedValuesManager.getManager(project);

                        List<String> locations =  cacheManager.getCachedValue(
                                parameters.getOriginalFile(),
                                cacheSectionKey,
                                () -> {
                                    List<String> suggest = SuggestUtils.scanYamlPsiTreeToLocation(document, getSection());

                                    Map<String, Object> globalCache = getProjectCache(project);

                                    Map<String, Object> section = (Map<String, Object>) globalCache.get(getSection());
                                    if (section != null) {
                                        for (String id : section.keySet()) {
                                            Map<String, Object> files = (Map<String, Object>) section.get(id);
                                            for (String file : files.keySet()) {
                                                CacheFileData data = (CacheFileData)files.get(file);
                                                if (data.location != null && suggest.indexOf(data.location) < 0) {
                                                    suggest.add(data.location);
                                                }
                                            }
                                        }
                                    }

                                    return CachedValueProvider.Result.create(
                                            suggest,
                                            PsiModificationTracker.MODIFICATION_COUNT,
                                            ProjectRootManager.getInstance(project)
                                    );
                                }
                        );

                        for (String location : locations) {
                            resultSet.addElement(LookupElementBuilder.create(location));
                        }
                    }
                }
        );
    }
}
