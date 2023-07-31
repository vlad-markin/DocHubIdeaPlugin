package info.dochub.idea.arch.indexing;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.indexing.*;
import com.intellij.util.io.DataExternalizer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

public class DocHubIndex extends SingleEntryFileBasedIndexExtension<DocHubIndexData> {

    public static final ID<Integer, DocHubIndexData> INDEX_ID = ID.create("DocHubYamlIndex");

    private final SingleEntryIndexer<DocHubIndexData> myDataIndexer =
            new SingleEntryIndexer<>(false) {
                @Override
                protected @Nullable DocHubIndexData computeValue(@NotNull FileContent inputData) {
                    return new DocHubIndexData(inputData.getPsiFile());
                }
            };

    private final DataExternalizer<DocHubIndexData> myValueExternalizer = new DataExternalizer<DocHubIndexData>() {
        @Override
        public void save(@NotNull DataOutput out, DocHubIndexData value) throws IOException {
            value.stringify(out);
        }

        @Override
        public DocHubIndexData read(@NotNull DataInput in) throws IOException {
            try {
                return new DocHubIndexData(in);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
    };

    private final FileBasedIndex.InputFilter myInputFilter = new FileBasedIndex.InputFilter() {
        @Override
        public boolean acceptInput(@NotNull VirtualFile file) {
            return file.getPath().endsWith(".yaml");
        }
    };

    @Override
    public @NotNull ID<Integer, DocHubIndexData> getName() {
        return INDEX_ID;
    }

    @Override
    public @NotNull SingleEntryIndexer<DocHubIndexData> getIndexer() {
        return myDataIndexer;
    }

    @Override
    public @NotNull DataExternalizer<DocHubIndexData> getValueExternalizer() {
        return myValueExternalizer;
    }

    @Override
    public int getVersion() {
        return 7;
    }

    @Override
    public FileBasedIndex.@NotNull InputFilter getInputFilter() {
        return myInputFilter;
    }
}
