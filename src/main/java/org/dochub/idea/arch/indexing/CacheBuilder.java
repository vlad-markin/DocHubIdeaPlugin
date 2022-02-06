package org.dochub.idea.arch.indexing;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Key;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CacheBuilder {

//    project.getMessageBus().connect().subscribe(ProjectTopics.PROJECT_ROOTS, new ModuleRootListener() {
//        @Override
//        public void rootsChanged(ModuleRootEvent event) {
//        }
//    });

    private static Key cacheGlobalKey = Key.create("dochub-global-cache");

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
        Map<String, Object> mapSection = (Map<String, Object>) context.get(section);
        if (mapSection == null) {
            mapSection = new HashMap<String, Object>();
            context.put(section, mapSection);
        }
        return mapSection;
    }

    private static void parseYamlManifestIDs(Map<String, Object> yaml,
                                                 String section,
                                                 String path,
                                                 Map<String, Object> context) {
        Map<String, Object> yamlSection = (Map<String, Object>) yaml.get(section);
        Map<String, Object> cacheIDs = getCacheSection(section, context);
        if (yamlSection != null) {
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
    }

    private static void parseYamlManifestImports(Map<String, Object> yaml,
                                                 String path,
                                                 Map<String, Object> context) {
        ArrayList<String> imports = (ArrayList) yaml.get("imports");
        if (imports != null) {
            for (String importManifest : imports) {
                parseYamlManifest((new File(path)).getParent() + "/" + importManifest, context);
            }
        }
    }

    private static void parseYamlManifest(String path, Map<String, Object> context) {
        Yaml yaml = new Yaml();
        try {
            InputStream inputStream = new FileInputStream(path);
            Map<String, Object> sections = yaml.load(inputStream);
            parseYamlManifestImports(sections, path, context);
            parseYamlManifestIDs(sections, "components", path, context);
            parseYamlManifestIDs(sections, "aspects", path, context);
            parseYamlManifestIDs(sections, "contexts", path, context);
            parseYamlManifestIDs(sections, "docs", path, context);
        } catch (FileNotFoundException e) {
            // todo тут нужно сделать предупреждение
        }
    }

//    private static void appendMessageToSuggest(String message, Map<String, Object> context) {
//        Map<String, CacheFileData> files = new HashMap<>();
//        files.put("file", new CacheFileData(null));
//        Map<String, Object> components = (Map<String, Object>) context.get("components");
//        if (components == null) components = new HashMap<>();
//        components.put("message:"  + message, files);
//        context.put("components", components);
//    }

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

    public static Map<String, Object> buildForProject(Project project)  {
        String rootManifest = null;
        if (isFileExists(project, "dochub.yaml"))  // Если это проект DocHub
            rootManifest = "dochub.yaml";
        else
            rootManifest = getFromEnv(project);

        Map<String, Object> context = new HashMap<>();

        if (rootManifest != null)
            parseYamlManifest(project.getBasePath() + "/" + rootManifest, context);

        return context;
    }

    private static Key cacheProjectKey = Key.create("dochub-global");

    private static class GlobalCacheProvider implements CachedValueProvider {
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

    public static Map<String, Object> getProjectCache(Project project) {
        if (globalCacheProvider == null) {
            globalCacheProvider = new GlobalCacheProvider(project);
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
