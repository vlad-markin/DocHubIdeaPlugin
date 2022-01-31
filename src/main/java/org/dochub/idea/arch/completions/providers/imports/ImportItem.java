package org.dochub.idea.arch.completions.providers.imports;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.ProcessingContext;
import org.dochub.idea.arch.completions.providers.CustomProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLSequenceItem;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImportItem extends CustomProvider {
    private static String keyword = "imports";
    private static final String[] keys = {
            "test"
    };

    public static final ElementPattern<? extends PsiElement> rootPattern = PlatformPatterns.or(
            PlatformPatterns.psiElement()
                    .withSuperParent(4,
                            psi(YAMLKeyValue.class)
                                    .withName(PlatformPatterns.string().equalTo(keyword))
                                    .withSuperParent(2, psi(YAMLDocument.class))
                    ),
            PlatformPatterns.psiElement()
                    .withSuperParent(5,
                            psi(YAMLKeyValue.class)
                                    .withName(PlatformPatterns.string().equalTo(keyword))
                                    .withSuperParent(2, psi(YAMLDocument.class))
                    )
    );

    @Override
    public void appendToCompletion(CompletionContributor completion) {
        completion.extend(
                CompletionType.BASIC,
                rootPattern,
                new CompletionProvider<>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               @NotNull ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        try {
                            VirtualFile thisFile = FileDocumentManager.getInstance().getFile(
                                    parameters.getEditor().getDocument()
                            );

                            if (thisFile != null) {
                                String text = parameters.getPosition().getContext().getText()
                                        .replaceFirst("IntellijIdeaRulezzz", "");;
                                String prefix =
                                        text.startsWith("../") ? text.substring(3) :
                                                text.equals(".") || text.equals("..") ? "" : text;
                                if (text.endsWith("/.") || text.equals(".")) {
                                    resultSet.addElement(LookupElementBuilder.create(prefix + "./"));
                                } else if (text.endsWith("/..") ||  text.equals("..")) {
                                    resultSet.addElement(LookupElementBuilder.create(prefix + "/"));
                                } else {
                                    String dirName =
                                            text.endsWith("/") || text.length() == 0
                                                    ? text
                                                    : (new File(text)).getParent() + "/";
                                    ;
                                    File dir = new File(thisFile.getParent().getPath() + "/" + dirName);
                                    File[] listFiles = dir.listFiles();
                                    if (listFiles != null) {
                                        for (File f : listFiles) {
                                            String suggest = (text.startsWith("../") ? dirName.substring(3) : dirName)
                                                    + f.getName();
                                            if (f.getName().endsWith(".yaml")) {
                                                resultSet.addElement(LookupElementBuilder.create(suggest));
                                            } else if (f.isDirectory()) {
                                                resultSet.addElement(LookupElementBuilder.create(suggest + "/"));
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

}
