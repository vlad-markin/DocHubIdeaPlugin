package org.dochub.idea.arch.completions.providers.suggets;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Key;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiModificationTracker;
import org.dochub.idea.arch.completions.providers.CustomProvider;
import org.dochub.idea.arch.indexing.CacheBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class BaseSuggest extends CustomProvider {
    protected ElementPattern<? extends PsiElement> getPattern() {
        return PlatformPatterns.psiElement();
    }

    private static Key cacheProjectKey = Key.create("dochub-global");

    private class GlobalCacheProvider implements CachedValueProvider {
        private Project project;

        public GlobalCacheProvider(Project project) {
            this.project = project;
        }

        @Override
        public @Nullable Result compute() {
            return Result.create(
                    CacheBuilder.buildForProject(project),
                    PsiModificationTracker.MODIFICATION_COUNT,
                    ProjectRootManager.getInstance(project));
        }
    }

    private static GlobalCacheProvider globalCacheProvider = null;

    protected Map<String, Object> getProjectCache(Project project) {
        if (globalCacheProvider == null) {
            globalCacheProvider = new GlobalCacheProvider(project);
        }
        CachedValuesManager cacheManager = CachedValuesManager.getManager(project);
        return (Map<String, Object>) cacheManager.getCachedValue(
                PsiManager.getInstance(project).findFile(project.getProjectFile()),
                cacheProjectKey,
                globalCacheProvider
        );
    }
}
