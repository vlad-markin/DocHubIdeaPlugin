// This is a generated file. Not intended for manual editing.
package org.intellij.sdk.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface JSONataArguments extends PsiElement {

  @Nullable
  JSONataDeepProp getDeepProp();

  @Nullable
  JSONataExpression getExpression();

  @Nullable
  JSONataFilter getFilter();

  @Nullable
  JSONataFunctionCall getFunctionCall();

  @Nullable
  JSONataJson getJson();

  @Nullable
  JSONataOrder getOrder();

  @Nullable
  PsiElement getBoolean();

  @Nullable
  PsiElement getNumber();

  @Nullable
  PsiElement getString();

}
