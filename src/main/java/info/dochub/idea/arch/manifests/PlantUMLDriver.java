package info.dochub.idea.arch.manifests;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.eclipse.elk.alg.layered.options.LayeredOptions;
import org.eclipse.elk.core.data.LayoutMetaDataService;

// https://plantuml.com/ru/api
// https://www.eclipse.org/forums/index.php/t/129584/


public class PlantUMLDriver {
    private static LayoutMetaDataService service;
    static public void init() {
        System.setProperty("org.eclipse.emf.ecore.EPackage.Registry.INSTANCE", "org.eclipse.emf.ecore.impl.EPackageRegistryImpl");
        LayoutMetaDataService.getInstance().registerLayoutMetaDataProviders(new LayeredOptions());
    }

    static public String makeSVG(String source) {
        String result = null;
        SourceStringReader reader = new SourceStringReader(source);
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            reader.outputImage(os, new FileFormatOption(FileFormat.SVG));
            os.close();
            result = new String(os.toByteArray(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
