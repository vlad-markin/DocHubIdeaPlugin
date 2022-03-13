// This is a generated file. Not intended for manual editing.
package org.dochub.idea.arch.jsonata.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import org.intellij.sdk.language.psi.impl.*;

public interface JSONataTypes {

  IElementType ARGUMENTS = new JSONataElementType("ARGUMENTS");
  IElementType ARRAY = new JSONataElementType("ARRAY");
  IElementType BLOCK = new JSONataElementType("BLOCK");
  IElementType CHAIN = new JSONataElementType("CHAIN");
  IElementType CHAIN_SIMPLE = new JSONataElementType("CHAIN_SIMPLE");
  IElementType DEEP_PROP = new JSONataElementType("DEEP_PROP");
  IElementType EXPRESSION = new JSONataElementType("EXPRESSION");
  IElementType FILTER = new JSONataElementType("FILTER");
  IElementType FUNCTION = new JSONataElementType("FUNCTION");
  IElementType FUNCTION_CALL = new JSONataElementType("FUNCTION_CALL");
  IElementType FUNCTION_CALL_PARAMS = new JSONataElementType("FUNCTION_CALL_PARAMS");
  IElementType JNAME = new JSONataElementType("JNAME");
  IElementType JSON = new JSONataElementType("JSON");
  IElementType JSONATA = new JSONataElementType("JSONATA");
  IElementType METHODS = new JSONataElementType("METHODS");
  IElementType METHOD_PARAMS = new JSONataElementType("METHOD_PARAMS");
  IElementType OBJECT = new JSONataElementType("OBJECT");
  IElementType OPERATORS = new JSONataElementType("OPERATORS");
  IElementType ORDER = new JSONataElementType("ORDER");
  IElementType ORDER_PARAMS = new JSONataElementType("ORDER_PARAMS");
  IElementType PROP = new JSONataElementType("PROP");
  IElementType TRANSFORM = new JSONataElementType("TRANSFORM");
  IElementType TRANSFORM_CALL = new JSONataElementType("TRANSFORM_CALL");
  IElementType TRANSFORM_LOCATION = new JSONataElementType("TRANSFORM_LOCATION");
  IElementType VALUE = new JSONataElementType("VALUE");
  IElementType VARIABLE_SET = new JSONataElementType("VARIABLE_SET");

  IElementType BOOLEAN = new JSONataTokenType("boolean");
  IElementType BRACE1 = new JSONataTokenType("{");
  IElementType BRACE2 = new JSONataTokenType("}");
  IElementType BRACK1 = new JSONataTokenType("[");
  IElementType BRACK2 = new JSONataTokenType("]");
  IElementType COLON = new JSONataTokenType(":");
  IElementType COMMA = new JSONataTokenType(",");
  IElementType COMMENT = new JSONataTokenType("comment");
  IElementType FUNCTION_4_1_0 = new JSONataTokenType("function_4_1_0");
  IElementType ID = new JSONataTokenType("id");
  IElementType METHODS_2_0_1_0 = new JSONataTokenType("methods_2_0_1_0");
  IElementType NUMBER = new JSONataTokenType("number");
  IElementType RANGE = new JSONataTokenType("range");
  IElementType REGEX = new JSONataTokenType("regex");
  IElementType STRING = new JSONataTokenType("string");
  IElementType SYS_VARIABLE = new JSONataTokenType("sys_variable");
  IElementType VARIABLE = new JSONataTokenType("variable");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ARGUMENTS) {
        return new JSONataArgumentsImpl(node);
      }
      else if (type == ARRAY) {
        return new JSONataArrayImpl(node);
      }
      else if (type == BLOCK) {
        return new JSONataBlockImpl(node);
      }
      else if (type == CHAIN) {
        return new JSONataChainImpl(node);
      }
      else if (type == CHAIN_SIMPLE) {
        return new JSONataChainSimpleImpl(node);
      }
      else if (type == DEEP_PROP) {
        return new JSONataDeepPropImpl(node);
      }
      else if (type == EXPRESSION) {
        return new JSONataExpressionImpl(node);
      }
      else if (type == FILTER) {
        return new JSONataFilterImpl(node);
      }
      else if (type == FUNCTION) {
        return new JSONataFunctionImpl(node);
      }
      else if (type == FUNCTION_CALL) {
        return new JSONataFunctionCallImpl(node);
      }
      else if (type == FUNCTION_CALL_PARAMS) {
        return new JSONataFunctionCallParamsImpl(node);
      }
      else if (type == JNAME) {
        return new JSONataJnameImpl(node);
      }
      else if (type == JSON) {
        return new JSONataJsonImpl(node);
      }
      else if (type == JSONATA) {
        return new JSONataJsonataImpl(node);
      }
      else if (type == METHODS) {
        return new JSONataMethodsImpl(node);
      }
      else if (type == METHOD_PARAMS) {
        return new JSONataMethodParamsImpl(node);
      }
      else if (type == OBJECT) {
        return new JSONataObjectImpl(node);
      }
      else if (type == OPERATORS) {
        return new JSONataOperatorsImpl(node);
      }
      else if (type == ORDER) {
        return new JSONataOrderImpl(node);
      }
      else if (type == ORDER_PARAMS) {
        return new JSONataOrderParamsImpl(node);
      }
      else if (type == PROP) {
        return new JSONataPropImpl(node);
      }
      else if (type == TRANSFORM) {
        return new JSONataTransformImpl(node);
      }
      else if (type == TRANSFORM_CALL) {
        return new JSONataTransformCallImpl(node);
      }
      else if (type == TRANSFORM_LOCATION) {
        return new JSONataTransformLocationImpl(node);
      }
      else if (type == VALUE) {
        return new JSONataValueImpl(node);
      }
      else if (type == VARIABLE_SET) {
        return new JSONataVariableSetImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
