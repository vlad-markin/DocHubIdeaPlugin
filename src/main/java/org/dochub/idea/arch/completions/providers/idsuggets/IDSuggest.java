package org.dochub.idea.arch.completions.providers.idsuggets;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Key;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiModificationTracker;
import com.intellij.util.ProcessingContext;
import org.dochub.idea.arch.completions.providers.CustomProvider;
import org.dochub.idea.arch.indexing.CacheBuilder;
import org.dochub.idea.arch.utils.PsiUtils;
import org.dochub.idea.arch.utils.SuggestUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class IDSuggest extends CustomProvider {
    protected ElementPattern<? extends PsiElement> getPattern() {
        return PlatformPatterns.psiElement();
    }

    private static Key cacheProjectKey = Key.create("dochub-global");
    private Key cacheSectionKey = null;

    protected String getSection() {
        return "undefined";
    }

    private class GlobalCacheProvider implements CachedValueProvider {
        private Project project;

        public GlobalCacheProvider(Project project) {
            this.project = project;
        }

        @Override
        public @Nullable Result compute() {
            return CachedValueProvider.Result.create(
                    CacheBuilder.buildForProject(project),
                    PsiModificationTracker.MODIFICATION_COUNT,
                    ProjectRootManager.getInstance(project));
        }
    }

    private static GlobalCacheProvider globalCacheProvider = null;

    public IDSuggest() {
        cacheSectionKey = Key.create(getSection());
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

                        List<String> ids =  cacheManager.getCachedValue(
                                parameters.getOriginalFile(),
                                cacheSectionKey,
                                () -> {
                                    List<String> suggest = SuggestUtils.scanYamlPsiTreeToID(document, getSection());
                                    if (globalCacheProvider == null) {
                                        globalCacheProvider = new GlobalCacheProvider(project);
                                    }

                                    Map<String, Object> globalCache = (Map<String, Object>) cacheManager.getCachedValue(
                                            PsiManager.getInstance(project).findFile(project.getProjectFile()),
                                            cacheProjectKey,
                                            globalCacheProvider
                                    );

                                    Map<String, Object> section = (Map<String, Object>) globalCache.get(getSection());
                                    if (section != null) {
                                        for (String id : section.keySet()) {
                                            if(suggest.indexOf(id) < 0)
                                                suggest.add(id);
                                        }
                                    }

                                    return CachedValueProvider.Result.create(
                                            suggest,
                                            PsiModificationTracker.MODIFICATION_COUNT,
                                            ProjectRootManager.getInstance(project)
                                    );
                                }
                        );

                        for (String id : ids) {
                            resultSet.addElement(LookupElementBuilder.create(id));
                        }
                    }
                }
        );
    }
}
