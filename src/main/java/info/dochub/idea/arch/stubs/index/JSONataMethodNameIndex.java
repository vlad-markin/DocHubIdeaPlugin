package info.dochub.idea.arch.stubs.index;

import com.intellij.psi.stubs.StubIndexKey;
import info.dochub.idea.arch.jsonata.psi.JSONataPsiVariable;
import org.jetbrains.annotations.NotNull;

public class JSONataMethodNameIndex extends JSONataNameIndex<JSONataPsiVariable>{

    private final StubIndexKey<Integer, JSONataPsiVariable> KEY = StubIndexKey.createIndexKey("info.dochub.idea.arch.stubs.index.JSONataMethodNameIndex");

    @Override
    public @NotNull StubIndexKey<Integer, JSONataPsiVariable> getKey() {
        return KEY;
    }
}
