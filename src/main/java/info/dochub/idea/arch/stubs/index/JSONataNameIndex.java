package info.dochub.idea.arch.stubs.index;

import com.intellij.psi.stubs.IntStubIndexExtension;
import com.intellij.psi.stubs.StubIndexKey;
import info.dochub.idea.arch.jsonata.psi.JSONataNamedElement;
import org.jetbrains.annotations.NotNull;

public class JSONataNameIndex<Psi extends JSONataNamedElement> extends IntStubIndexExtension<Psi> {

    private final StubIndexKey<Integer, Psi> KEY = StubIndexKey.createIndexKey("info.dochub.idea.arch.stubs.index.JSONataNameIndex");

    @Override
    public @NotNull StubIndexKey<Integer, Psi> getKey() {
        return KEY;
    }
}
