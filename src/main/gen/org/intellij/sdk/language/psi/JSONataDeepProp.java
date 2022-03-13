// This is a generated file. Not intended for manual editing.
package org.intellij.sdk.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface JSONataDeepProp extends PsiElement {

  @Nullable
  JSONataBlock getBlock();

  @NotNull
  List<JSONataJname> getJnameList();

  @NotNull
  List<JSONataJson> getJsonList();

  @NotNull
  List<JSONataMethods> getMethodsList();

  @Nullable
  PsiElement getRange();

  @Nullable
  PsiElement getSysVariable();

}
