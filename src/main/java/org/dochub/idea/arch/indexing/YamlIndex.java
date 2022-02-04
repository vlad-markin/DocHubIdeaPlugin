package org.dochub.idea.arch.indexing;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.indexing.*;
import com.intellij.util.io.DataExternalizer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class YamlIndex extends SingleEntryFileBasedIndexExtension<YamlIndexData.IndexData> {

    public static final ID<Integer, YamlIndexData.IndexData> INDEX_ID = ID.create("DocHubYamlIndex");

    private final SingleEntryIndexer<YamlIndexData.IndexData> myDataIndexer =
            new SingleEntryIndexer<>(false) {
                @Override
                protected @Nullable YamlIndexData.IndexData computeValue(@NotNull FileContent inputData) {
                    return new YamlIndexData.IndexData();
                }
            };

    private final DataExternalizer<YamlIndexData.IndexData> myValueExternalizer = new DataExternalizer<YamlIndexData.IndexData>() {
        @Override
        public void save(@NotNull DataOutput out, YamlIndexData.IndexData value) throws IOException {
            new YamlIndexData.IndexData();
        }

        @Override
        public YamlIndexData.IndexData read(@NotNull DataInput in) throws IOException {
            return new YamlIndexData.IndexData();
        }
    };

    private final FileBasedIndex.InputFilter myInputFilter = new FileBasedIndex.InputFilter() {
        @Override
        public boolean acceptInput(@NotNull VirtualFile file) {
            return file.getPath().endsWith(".yaml");
        }
    };

    @Override
    public @NotNull ID<Integer, YamlIndexData.IndexData> getName() {
        return INDEX_ID;
    }

    @Override
    public @NotNull SingleEntryIndexer<YamlIndexData.IndexData> getIndexer() {
        return myDataIndexer;
    }

    @Override
    public @NotNull DataExternalizer<YamlIndexData.IndexData> getValueExternalizer() {
        return myValueExternalizer;
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public FileBasedIndex.@NotNull InputFilter getInputFilter() {
        return myInputFilter;
    }
}
