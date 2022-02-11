package org.dochub.idea.arch.manifests;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.core.DiagramDescription;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

// https://plantuml.com/ru/api

public class PlantUMLDriver {
    static public void test() {
        String source = "@startuml\n";
        source += "Bob -> Alice : hello\n";
        source += "@enduml\n";

        SourceStringReader reader = new SourceStringReader(source);
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
// Write the first image to "os"
        try {
            DiagramDescription diagram = reader.outputImage(os, new FileFormatOption(FileFormat.SVG));
            String desc = diagram.toString();
            os.close();
// The XML is stored into svg
            final String svg = new String(os.toByteArray(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
