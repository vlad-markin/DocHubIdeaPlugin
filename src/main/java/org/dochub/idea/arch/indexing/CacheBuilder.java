package org.dochub.idea.arch.indexing;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiModificationTracker;
import com.intellij.psi.util.PsiTreeUtil;
import org.dochub.idea.arch.references.providers.RefBaseID;
import org.dochub.idea.arch.utils.VirtualFileSystemUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

import static net.sourceforge.plantuml.style.SName.entity;
import static org.bouncycastle.asn1.x500.style.RFC4519Style.o;

public class CacheBuilder {
    public static Key cacheGlobalKey = Key.create("dochub-global-cache");
    public static Key cacheFileKey = Key.create("dochub-file-cache");

    private static boolean isFileExists(Project project, String filename) {
        return (new File(project.getBasePath() + "/" + filename)).exists();
    }

    private static Map<String, String> parseEnvFile(String filename) {
        Map<String, String> result = new HashMap<>();
        try( BufferedReader br = new BufferedReader( new FileReader( filename))) {
            String line;
            while(( line = br.readLine()) != null ) {
                String[] lineStruct = line.split("\\=");
                result.put(lineStruct[0], lineStruct.length > 1 ? lineStruct[1] : null);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Map<String, Object> getCacheSection(String section, Map<String, Object> context) {
        try {
            Map<String, Object> mapSection = (Map<String, Object>) context.get(section);
            if (mapSection == null) {
                mapSection = new HashMap<String, Object>();
                context.put(section, mapSection);
            }
            return mapSection;
        } catch (ClassCastException e) {
            return null;
        }
    }

    private static void parseYamlManifestIDs(Map<String, Object> yaml,
                                                 String section,
                                                 String path,
                                                 Map<String, Object> context) {

        try {
            Map<String, Object> yamlSection = (Map<String, Object>) yaml.get(section);
            Map<String, Object> cacheIDs = getCacheSection(section, context);
            if (yamlSection != null && cacheIDs != null) {
                for ( String id : yamlSection.keySet()) {
                    Map<String, Object> files = getCacheSection(id, cacheIDs);
                    String location = null;
                    try {
                        Map<String, Object> fields = (Map<String, Object>) yamlSection.get(id);
                        if (fields != null)
                            location = (String) fields.get("location");
                    } catch (ClassCastException e) {}
                    files.put(path, new CacheFileData(location));
                }
            }
        } catch (ClassCastException e) {}
    }

    private static void parseYamlManifestImports(Project project,
                                                 Map<String, Object> yaml,
                                                 String path,
                                                 Map<String, Object> context) {
        ArrayList<String> imports = (ArrayList) yaml.get("imports");
        if (imports != null) {
            for (String importManifest : imports) {
                parseYamlManifest(project, importManifest, context);
            }
        }
    }

    public static void makeCacheDataManifest(PsiFile file) {
        Map<String, Object> data = new HashMap<>();
        String path = file.getVirtualFile().getPath();
        try {
            InputStream inputStream = new FileInputStream(path);
            Yaml yaml = new Yaml();
            Map<String, Object> sections = yaml.load(inputStream);
            if (sections != null) {
                parseYamlManifestIDs(sections, "components", path, data);
                parseYamlManifestIDs(sections, "aspects", path, data);
                parseYamlManifestIDs(sections, "contexts", path, data);
                parseYamlManifestIDs(sections, "docs", path, data);
                parseYamlManifestIDs(sections, "datasets", path, data);
            }
            file.putUserData(cacheFileKey, data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void manifestMerge(Map<String, Object> from, Map<String, Object> to) {
        for (String keyName : from.keySet()) {
            if (!to.containsKey(keyName)) {
                to.put(keyName, from.get(keyName));
            } else {
                Object destination = to.get(keyName);
                Object source = from.get(keyName);
                if ((destination instanceof Map) && (source instanceof Map)) {
                    manifestMerge((Map<String, Object>) source, (Map<String, Object>) destination);
                } else if ((destination instanceof ArrayList) && (source instanceof ArrayList)) {
                    ((ArrayList<?>) destination).addAll((ArrayList)source);
                } else {
                    to.put(keyName, from.get(keyName));
                }
            }
        }
    }

    private static class FileCacheProvider implements CachedValueProvider {
        private Project project;
        private PsiFile file;

        public FileCacheProvider(Project project, PsiFile file) {
            this.project = project;
            this.file = file;
        }

        @Override
        public @Nullable Result compute() {
            Map<String, Object> result = new HashMap<>();
            InputStream inputStream = null;
            try {
                String path = file.getVirtualFile().getPath();
                inputStream = new FileInputStream(path);
                Yaml yaml = new Yaml();
                Map<String, Object> sections = yaml.load(inputStream);
                if (sections != null) {
                    parseYamlManifestImports(project, sections, path, result);
                    parseYamlManifestIDs(sections, "components", path, result);
                    parseYamlManifestIDs(sections, "aspects", path, result);
                    parseYamlManifestIDs(sections, "contexts", path, result);
                    parseYamlManifestIDs(sections, "docs", path, result);
                    parseYamlManifestIDs(sections, "datasets", path, result);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return Result.create(
                    result,
                    PsiModificationTracker.MODIFICATION_COUNT,
                    file);
        }
    }

    private static void parseYamlManifest(Project project, String path, Map<String, Object> context) {
        Map<String, Object> manifest = (Map<String, Object>) context.get("$manifest");
        if (manifest == null) {
            manifest = new HashMap<String, Object>();
            context.put("$manifest", manifest);
        }

        VirtualFile vFile = VirtualFileSystemUtils.findFile(path, project);

        if (vFile != null) {
            PsiFile targetFile = PsiManager.getInstance(project).findFile(vFile);
            if (targetFile != null) {
                Map<String, Object> sections = (Map<String, Object>) targetFile.getUserData(cacheGlobalKey);
                if (sections == null) {
                    sections = (Map<String, Object>) CachedValuesManager.getManager(project)
                            .getCachedValue(
                                    targetFile,
                                    new FileCacheProvider(project, targetFile)
                            );
                }
                targetFile.putUserData(cacheGlobalKey, sections);
                manifestMerge(sections, manifest);
            }
        }
    }

    private static String getFromEnv(Project project) {
        String[] names = new String[]{".env.local", ".env"};
        for (String name : names) {
            if (isFileExists(project, name)) {
                Map<String, String> env = parseEnvFile(project.getBasePath() + "/" + name);
                return  "public/" + env.get("VUE_APP_DOCHUB_ROOT_MANIFEST");
            };
        }
        return null;
    }

    public static String getRootManifestName(Project project) {
        String rootManifest = null;
        if (isFileExists(project, "dochub.yaml"))  // Если это проект DocHub
            rootManifest = "dochub.yaml";
        else
            rootManifest = getFromEnv(project);
        return rootManifest != null ? rootManifest : "dochub.yaml";
    }

    private static Key cacheProjectKey = Key.create("dochub-global");

    private static class GlobalCacheProvider implements CachedValueProvider {
        private Project project;

        public GlobalCacheProvider(Project project) {
            this.project = project;
        }

        @Override
        public @Nullable Result compute() {
            Map<String, Object> manifest = new HashMap<>();
            String rootManifest = getRootManifestName(project);

            if (rootManifest != null)
                parseYamlManifest(project, rootManifest, manifest);

            return Result.create(
                    manifest,
                    PsiModificationTracker.MODIFICATION_COUNT,
                    ProjectRootManager.getInstance(project));
        }
    }

    private static Map<Project, GlobalCacheProvider> globalCacheProviders = new HashMap<>();

    public static Map<String, Object> getProjectCache(Project project) {
        GlobalCacheProvider globalCacheProvider = globalCacheProviders.get(project);
        if (globalCacheProvider == null) {
            globalCacheProvider = new GlobalCacheProvider(project);
            globalCacheProviders.put(project, globalCacheProvider);
        }
        CachedValuesManager cacheManager = CachedValuesManager.getManager(project);
        return (Map<String, Object>) cacheManager.getCachedValue(
                // PsiManager.getInstance(project).findFile(projectFile),
                project,
                cacheProjectKey,
                globalCacheProvider,
                false
        );
    }
}
