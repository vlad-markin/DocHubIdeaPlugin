package org.dochub.idea.arch.indexing;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.indexing.*;
import com.intellij.util.io.DataExternalizer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class YamlIndex extends SingleEntryFileBasedIndexExtension<YamlIndexData> {

    public static final ID<Integer, YamlIndexData> INDEX_ID = ID.create("DocHubYamlIndex");

    private final SingleEntryIndexer<YamlIndexData> myDataIndexer =
            new SingleEntryIndexer<>(false) {
                @Override
                protected @Nullable YamlIndexData computeValue(@NotNull FileContent inputData) {
                    CacheBuilder.makeCacheDataManifest(inputData.getPsiFile());
                    return new YamlIndexData();
                }
            };

    private final DataExternalizer<YamlIndexData> myValueExternalizer = new DataExternalizer<YamlIndexData>() {
        @Override
        public void save(@NotNull DataOutput out, YamlIndexData value) throws IOException {
            new YamlIndexData();
        }

        @Override
        public YamlIndexData read(@NotNull DataInput in) throws IOException {
            return new YamlIndexData;
        }
    };

    private final FileBasedIndex.InputFilter myInputFilter = new FileBasedIndex.InputFilter() {
        @Override
        public boolean acceptInput(@NotNull VirtualFile file) {
            return file.getPath().endsWith(".yaml");
        }
    };

    @Override
    public @NotNull ID<Integer, YamlIndexData> getName() {
        return INDEX_ID;
    }

    @Override
    public @NotNull SingleEntryIndexer<YamlIndexData> getIndexer() {
        return myDataIndexer;
    }

    @Override
    public @NotNull DataExternalizer<YamlIndexData> getValueExternalizer() {
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
