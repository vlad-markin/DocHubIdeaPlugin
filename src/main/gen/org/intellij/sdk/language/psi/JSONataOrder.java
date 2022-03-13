// This is a generated file. Not intended for manual editing.
package org.intellij.sdk.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface JSONataOrder extends PsiElement {

  @NotNull
  List<JSONataDeepProp> getDeepPropList();

  @NotNull
  List<JSONataFilter> getFilterList();

  @NotNull
  List<JSONataOrderParams> getOrderParamsList();

}
