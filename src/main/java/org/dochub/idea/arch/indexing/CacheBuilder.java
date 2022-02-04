package org.dochub.idea.arch.indexing;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import org.apache.commons.collections.MapIterator;
import org.dochub.idea.arch.utils.VirtualFileSystemUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        Map<String, Object> cacheAspectIDs = getCacheSection(section, context);
        if (yamlSection != null) {
            for ( String aspectID : yamlSection.keySet()) {
                Map<String, Object> files = getCacheSection(aspectID, cacheAspectIDs);
                files.put(path, new CacheFileData());
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
            if (path.endsWith("systems.yaml")) {
                Integer a = 1;
            }
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

    public static Map<String, Object> buildForProject(Project project)  {
        String rootManifest = null;
        if (isFileExists(project, "dochub.yaml")) { // Если это проект DocHub
            rootManifest = "dochub.yaml";
        } else if (isFileExists(project, ".env.local")) { // Если это VUE проект
            Map<String, String> env = parseEnvFile(project.getBasePath() + "/.env.local");
            rootManifest = "public/" + env.get("VUE_APP_DOCHUB_ROOT_MANIFEST");
        }

        if (rootManifest == null) return null;

        Map<String, Object> context = new HashMap<>();
        parseYamlManifest(project.getBasePath() + "/" + rootManifest, context);

        return context;

//        File envLocal = new File(project.getBasePath() + )
//        File dir = new File(project.getBasePath() + relativePath);
//        File[] listFiles = dir.listFiles();
//        if (listFiles != null) {
//            for (File f : listFiles) {
//                String path = relativePath + "/" + f.getName();
//                if (f.isDirectory()) {
//                    rebuildForProject(project, path);
//                } else if (path.endsWith(".yaml")) {
//                    VirtualFile file = VirtualFileSystemUtils.findFile(path, project);
//                    VirtualFile file2 = file;
//                }
//            }
//        }
//        VirtualFile[] vFiles = ProjectRootManager.getInstance(project).getContentRootsFromAllModules();
//        String sourceRootsList = Arrays.stream(vFiles).map(VirtualFile::getUrl).collect(Collectors.joining("\n"));
    }
}
