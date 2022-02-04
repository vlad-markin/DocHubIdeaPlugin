package org.dochub.idea.arch.utils;

import com.intellij.AbstractBundle;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.util.ObjectUtils;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

public class SuggestUtils {
    private static final Logger LOG = Logger.getInstance(SuggestUtils.class);

    public static List<String> scanDirByContext(String basePath, String context, String[] extensions) {
        List<String> result = new ArrayList<String>();
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

    public static void appendDividerItem(List<String> list, String item, String context, String divider) {
        String[] contextParts = (context + " ").split(new String("\\" + divider));
        String[] itemParts = item.split(new String("\\" + divider));
        if (item.startsWith(context) && item.length() > context.length()) {
            String suggest = contextParts.length > 0 ? String.join(divider,
                    Arrays.copyOfRange(contextParts, 0 , contextParts.length - 1)
            ) : "";

            suggest +=  (suggest.length() > 0 ? divider : "") + itemParts[contextParts.length - 1];

            if (list.indexOf(suggest) < 0)
                list.add(suggest);
        }
    }

    public static List<String> scanYamlStreamToID(InputStream stream, String section, String context) {
        List<String> result = new ArrayList<>();
        Yaml yml = new Yaml();
        Map<String, Object> document = yml.load(stream);
        if (document != null) {
            for (Map.Entry<String, Object> entry : document.entrySet()) {
                if (entry.getKey().equals(section)) {
                    Map<String, Object> components = (Map<String, Object>) entry.getValue();
                    for (Map.Entry<String, Object> component : components.entrySet()) {
                        appendDividerItem(result, component.getKey(), context,".");
                    }
                }
            }
        }

        return result;
    }

    public static List<String> scanYamlPsiTreeToID(PsiElement document, String section) {
        List<String> result = new ArrayList<>();
        PsiElement[] yamlSections = document.getFirstChild().getChildren();
        for (PsiElement yamlSection : yamlSections) {
            YAMLKeyValue yamlKey = ObjectUtils.tryCast(yamlSection,  YAMLKeyValue.class);
            if (yamlKey != null && PsiUtils.getText(yamlKey.getKey()).equals(section)) {
                PsiElement[] yamlIDs = yamlSection.getLastChild().getChildren();
                for (PsiElement id : yamlIDs ) {
                    YAMLKeyValue yamlID = ObjectUtils.tryCast(id,  YAMLKeyValue.class);
                    if (yamlID != null) {
                        // appendDividerItem(result, PsiUtils.getText(yamlID.getKey()), context, ".");
                        result.add(PsiUtils.getText(yamlID.getKey()));
                    }
                }
            }
        }

        return result;
    }

    public static List<String> scanYamlPsiTreeToLocation(PsiElement element, String section, String context) {
        PsiElement document = element;
        List<String> result = new ArrayList<>();
        while (document != null) {
            if (ObjectUtils.tryCast(document,  YAMLDocument.class) != null)
                break;
            document = document.getParent();
        }
        if (document != null) {
            PsiElement[] yamlSections = document.getFirstChild().getChildren();
            // Обход корневых секций
            for (PsiElement yamlSection : yamlSections) {
                YAMLKeyValue yamlKey = ObjectUtils.tryCast(yamlSection,  YAMLKeyValue.class);
                if (yamlKey != null && PsiUtils.getText(yamlKey.getKey()).equals(section)) {
                    // Обход нужной секции
                    PsiElement[] yamlIDs = yamlSection.getLastChild().getChildren();
                    for (PsiElement id : yamlIDs ) {
                        YAMLKeyValue yamlID = ObjectUtils.tryCast(id,  YAMLKeyValue.class);
                        if (yamlID != null) {
                            // Обход полей
                            PsiElement[] fields = id.getLastChild().getChildren();
                            for (PsiElement field : fields) {
                                YAMLKeyValue yamlField = ObjectUtils.tryCast(field,  YAMLKeyValue.class);
                                // Если нашли поле location
                                String key = PsiUtils.getText(yamlField.getKey());
                                if (yamlField != null && key.equals("location")) {
                                    // appendDividerItem(result, PsiUtils.getText(field.getLastChild()), context, "/");
                                    result.add(PsiUtils.getText(yamlID.getKey()));
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    public static List<String> scanYamlStringToID(String data, String section, String context) {
        return scanYamlStreamToID(new ByteArrayInputStream(data.getBytes()), section, context);
    }

    public static List<String> scanYamlFileToID(String path, String section, String context) throws FileNotFoundException {
        return scanYamlStreamToID(new FileInputStream(path), section, context);
    }
}
