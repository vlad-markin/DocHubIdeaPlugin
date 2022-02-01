package org.dochub.idea.arch.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SuggestUtils {
    public static List<String> scanDirByContext(String basePath, String context, String[] extensions) {
        List<String> result = new ArrayList<String>();;
        String prefix =
                context.startsWith("../") ? context.substring(3) :
                        context.equals(".") || context.equals("..") ? "" : context;
        if (context.endsWith("/.") || context.equals(".")) {
            result.add(prefix + "./");
        } else if (context.endsWith("/..") ||  context.equals("..")) {
            result.add(prefix + "/");
        } else {
            String dirName =
                    context.endsWith("/") || context.length() == 0
                            ? context
                            : (new File(context)).getParent() + "/";
            ;
            File dir = new File(basePath + "/" + dirName);
            File[] listFiles = dir.listFiles();
            if (listFiles != null) {
                for (File f : listFiles) {
                    String suggest = (context.startsWith("../") ? dirName.substring(3) : dirName)
                            + f.getName();
                    if (f.isDirectory()) {
                        result.add(suggest + "/");
                    } else for (String ext : extensions) {
                        if (f.getName().endsWith(ext)) {
                            result.add(suggest);
                        }
                    }
                }
            }
        }
        return result;
    }

    public static List<String> scanYamlStreamToID(InputStream stream, String section) {
        List<String> result = new ArrayList<>();
        Yaml yml = new Yaml();
        Map<String, Object> document = yml.load(stream);
        if (document != null) {
            for (Map.Entry<String, Object> entry : document.entrySet()) {
                if (entry.getKey().equals(section)) {
                    Map<String, Object> components = (Map<String, Object>) entry.getValue();
                    for (Map.Entry<String, Object> component : components.entrySet()) {
                        result.add(component.getKey());
                    }
                }
            }
        }
        return result;
    }

    public static List<String> scanYamlStringToID(String data, String section) {
        return scanYamlStreamToID(new ByteArrayInputStream(data.getBytes()), section);
    }

    public static List<String> scanYamlFileToID(String path, String section) throws FileNotFoundException {
        return scanYamlStreamToID(new FileInputStream(path), section);
    }
}
