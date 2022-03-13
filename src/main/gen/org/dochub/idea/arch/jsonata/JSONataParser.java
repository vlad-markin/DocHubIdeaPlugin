// This is a generated file. Not intended for manual editing.
package org.dochub.idea.arch.jsonata;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static org.dochub.idea.arch.jsonata.psi.JSONataTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class JSONataParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, EXTENDS_SETS_);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return root(b, l + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(ARRAY, JSON, OBJECT, VALUE),
  };

  /* ********************************************************** */
  // string | number | boolean | function_call | order | filter | deep_prop | object | array | expression
  public static boolean arguments(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arguments")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ARGUMENTS, "<arguments>");
    r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, BOOLEAN);
    if (!r) r = function_call(b, l + 1);
    if (!r) r = order(b, l + 1);
    if (!r) r = filter(b, l + 1);
    if (!r) r = deep_prop(b, l + 1);
    if (!r) r = object(b, l + 1);
    if (!r) r = array(b, l + 1);
    if (!r) r = expression(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '[' [!']' item (!']' ',' item) *] ']'
  public static boolean array(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array")) return false;
    if (!nextTokenIs(b, BRACK1)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ARRAY, null);
    r = consumeToken(b, BRACK1);
    p = r; // pin = 1
    r = r && report_error_(b, array_1(b, l + 1));
    r = p && consumeToken(b, BRACK2) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [!']' item (!']' ',' item) *]
  private static boolean array_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_1")) return false;
    array_1_0(b, l + 1);
    return true;
  }

  // !']' item (!']' ',' item) *
  private static boolean array_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = array_1_0_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, item(b, l + 1));
    r = p && array_1_0_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // !']'
  private static boolean array_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, BRACK2);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (!']' ',' item) *
  private static boolean array_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_1_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!array_1_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "array_1_0_2", c)) break;
    }
    return true;
  }

  // !']' ',' item
  private static boolean array_1_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_1_0_2_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = array_1_0_2_0_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && item(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // !']'
  private static boolean array_1_0_2_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_1_0_2_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, BRACK2);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '#' variable
  static boolean bind_arr_index(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bind_arr_index")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "#");
    r = r && consumeToken(b, VARIABLE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '@' variable
  static boolean bind_arr_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bind_arr_item")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "@");
    r = r && consumeToken(b, VARIABLE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // bind_arr_item [bind_arr_index] | bind_arr_index [bind_arr_item]
  static boolean bind_arr_variable(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bind_arr_variable")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = bind_arr_variable_0(b, l + 1);
    if (!r) r = bind_arr_variable_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // bind_arr_item [bind_arr_index]
  private static boolean bind_arr_variable_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bind_arr_variable_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = bind_arr_item(b, l + 1);
    r = r && bind_arr_variable_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [bind_arr_index]
  private static boolean bind_arr_variable_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bind_arr_variable_0_1")) return false;
    bind_arr_index(b, l + 1);
    return true;
  }

  // bind_arr_index [bind_arr_item]
  private static boolean bind_arr_variable_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bind_arr_variable_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = bind_arr_index(b, l + 1);
    r = r && bind_arr_variable_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [bind_arr_item]
  private static boolean bind_arr_variable_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bind_arr_variable_1_1")) return false;
    bind_arr_item(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '(' block_body ')'
  public static boolean block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BLOCK, "<block>");
    r = consumeToken(b, "(");
    r = r && block_body(b, l + 1);
    r = r && consumeToken(b, ")");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // jsonata (';' jsonata) *
  static boolean block_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_body")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = jsonata(b, l + 1);
    r = r && block_body_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (';' jsonata) *
  private static boolean block_body_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_body_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!block_body_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "block_body_1", c)) break;
    }
    return true;
  }

  // ';' jsonata
  private static boolean block_body_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "block_body_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ";");
    r = r && jsonata(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // chain_simple ('~>' transform | chain | variable) *
  public static boolean chain(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "chain")) return false;
    if (!nextTokenIs(b, VARIABLE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = chain_simple(b, l + 1);
    r = r && chain_1(b, l + 1);
    exit_section_(b, m, CHAIN, r);
    return r;
  }

  // ('~>' transform | chain | variable) *
  private static boolean chain_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "chain_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!chain_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "chain_1", c)) break;
    }
    return true;
  }

  // '~>' transform | chain | variable
  private static boolean chain_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "chain_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = chain_1_0_0(b, l + 1);
    if (!r) r = chain(b, l + 1);
    if (!r) r = consumeToken(b, VARIABLE);
    exit_section_(b, m, null, r);
    return r;
  }

  // '~>' transform
  private static boolean chain_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "chain_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "~>");
    r = r && transform(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // variable '~>' (transform | chain | variable)
  public static boolean chain_simple(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "chain_simple")) return false;
    if (!nextTokenIs(b, VARIABLE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, VARIABLE);
    r = r && consumeToken(b, "~>");
    r = r && chain_simple_2(b, l + 1);
    exit_section_(b, m, CHAIN_SIMPLE, r);
    return r;
  }

  // transform | chain | variable
  private static boolean chain_simple_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "chain_simple_2")) return false;
    boolean r;
    r = transform(b, l + 1);
    if (!r) r = chain(b, l + 1);
    if (!r) r = consumeToken(b, VARIABLE);
    return r;
  }

  /* ********************************************************** */
  // '=' | '!=' | '>' | '<' | '>=' | '<=' | ' in ' | ' and ' | ' or '
  static boolean comp_operators(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comp_operators")) return false;
    boolean r;
    r = consumeToken(b, "=");
    if (!r) r = consumeToken(b, "!=");
    if (!r) r = consumeToken(b, ">");
    if (!r) r = consumeToken(b, "<");
    if (!r) r = consumeToken(b, ">=");
    if (!r) r = consumeToken(b, "<=");
    if (!r) r = consumeToken(b, " in ");
    if (!r) r = consumeToken(b, " and ");
    if (!r) r = consumeToken(b, " or ");
    return r;
  }

  /* ********************************************************** */
  // '&'
  static boolean concat_operators(PsiBuilder b, int l) {
    return consumeToken(b, "&");
  }

  /* ********************************************************** */
  // '?' expression ':'
  static boolean cond_operators(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cond_operators")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "?");
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, COLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (range | sys_variable | variable | jname | object | array | block)
  //             [bind_arr_variable]
  //             ( '.' (jname | methods | object )) *
  public static boolean deep_prop(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "deep_prop")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DEEP_PROP, "<deep prop>");
    r = deep_prop_0(b, l + 1);
    r = r && deep_prop_1(b, l + 1);
    r = r && deep_prop_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // range | sys_variable | variable | jname | object | array | block
  private static boolean deep_prop_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "deep_prop_0")) return false;
    boolean r;
    r = consumeToken(b, RANGE);
    if (!r) r = consumeToken(b, SYS_VARIABLE);
    if (!r) r = consumeToken(b, VARIABLE);
    if (!r) r = jname(b, l + 1);
    if (!r) r = object(b, l + 1);
    if (!r) r = array(b, l + 1);
    if (!r) r = block(b, l + 1);
    return r;
  }

  // [bind_arr_variable]
  private static boolean deep_prop_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "deep_prop_1")) return false;
    bind_arr_variable(b, l + 1);
    return true;
  }

  // ( '.' (jname | methods | object )) *
  private static boolean deep_prop_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "deep_prop_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!deep_prop_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "deep_prop_2", c)) break;
    }
    return true;
  }

  // '.' (jname | methods | object )
  private static boolean deep_prop_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "deep_prop_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ".");
    r = r && deep_prop_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // jname | methods | object
  private static boolean deep_prop_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "deep_prop_2_0_1")) return false;
    boolean r;
    r = jname(b, l + 1);
    if (!r) r = methods(b, l + 1);
    if (!r) r = object(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // regex | arguments  (operators arguments) *
  public static boolean expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXPRESSION, "<expression>");
    r = consumeToken(b, REGEX);
    if (!r) r = expression_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // arguments  (operators arguments) *
  private static boolean expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = arguments(b, l + 1);
    r = r && expression_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (operators arguments) *
  private static boolean expression_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_1_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!expression_1_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "expression_1_1", c)) break;
    }
    return true;
  }

  // operators arguments
  private static boolean expression_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = operators(b, l + 1);
    r = r && arguments(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // deep_prop  '[' expression ']' post *
  public static boolean filter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "filter")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FILTER, "<filter>");
    r = deep_prop(b, l + 1);
    r = r && consumeToken(b, BRACK1);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, BRACK2);
    r = r && filter_4(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // post *
  private static boolean filter_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "filter_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!post(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "filter_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // variable ':=' 'function' '(' (function_params|) ')' '{' function_body '}'
  public static boolean function(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function")) return false;
    if (!nextTokenIs(b, VARIABLE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, VARIABLE);
    r = r && consumeToken(b, ":=");
    r = r && consumeToken(b, "function");
    r = r && consumeToken(b, "(");
    r = r && function_4(b, l + 1);
    r = r && consumeToken(b, ")");
    r = r && consumeToken(b, BRACE1);
    r = r && function_body(b, l + 1);
    r = r && consumeToken(b, BRACE2);
    exit_section_(b, m, FUNCTION, r);
    return r;
  }

  // function_params|
  private static boolean function_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = function_params(b, l + 1);
    if (!r) r = consumeToken(b, FUNCTION_4_1_0);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // jsonata *
  static boolean function_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_body")) return false;
    while (true) {
      int c = current_position_(b);
      if (!jsonata(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "function_body", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // variable '(' [function_call_params] ')'
  public static boolean function_call(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call")) return false;
    if (!nextTokenIs(b, VARIABLE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, VARIABLE);
    r = r && consumeToken(b, "(");
    r = r && function_call_2(b, l + 1);
    r = r && consumeToken(b, ")");
    exit_section_(b, m, FUNCTION_CALL, r);
    return r;
  }

  // [function_call_params]
  private static boolean function_call_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_2")) return false;
    function_call_params(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // expression (',' expression) *
  public static boolean function_call_params(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_params")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FUNCTION_CALL_PARAMS, "<function call params>");
    r = expression(b, l + 1);
    r = r && function_call_params_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (',' expression) *
  private static boolean function_call_params_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_params_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!function_call_params_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "function_call_params_1", c)) break;
    }
    return true;
  }

  // ',' expression
  private static boolean function_call_params_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_params_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // variable (',' variable) *
  static boolean function_params(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_params")) return false;
    if (!nextTokenIs(b, VARIABLE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, VARIABLE);
    r = r && function_params_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' variable) *
  private static boolean function_params_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_params_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!function_params_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "function_params_1", c)) break;
    }
    return true;
  }

  // ',' variable
  private static boolean function_params_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_params_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, COMMA, VARIABLE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // string | number | boolean | json
  static boolean item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, BOOLEAN);
    if (!r) r = json(b, l + 1);
    exit_section_(b, l, m, r, false, JSONataParser::recover);
    return r;
  }

  /* ********************************************************** */
  // id | string
  public static boolean jname(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "jname")) return false;
    if (!nextTokenIs(b, "<jname>", ID, STRING)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, JNAME, "<jname>");
    r = consumeToken(b, ID);
    if (!r) r = consumeToken(b, STRING);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // array | object
  public static boolean json(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "json")) return false;
    if (!nextTokenIs(b, "<json>", BRACE1, BRACK1)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, JSON, "<json>");
    r = array(b, l + 1);
    if (!r) r = object(b, l + 1);
    register_hook_(b, WS_BINDERS, null, null);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // function | variable_set | chain | transform_call | expression
  public static boolean jsonata(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "jsonata")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, JSONATA, "<jsonata>");
    r = function(b, l + 1);
    if (!r) r = variable_set(b, l + 1);
    if (!r) r = chain(b, l + 1);
    if (!r) r = transform_call(b, l + 1);
    if (!r) r = expression(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '+' | '-' | '*' | '/' | '%'
  static boolean math_operators(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "math_operators")) return false;
    boolean r;
    r = consumeToken(b, "+");
    if (!r) r = consumeToken(b, "-");
    if (!r) r = consumeToken(b, "*");
    if (!r) r = consumeToken(b, "/");
    if (!r) r = consumeToken(b, "%");
    return r;
  }

  /* ********************************************************** */
  // expression (',' expression) *
  public static boolean method_params(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_params")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, METHOD_PARAMS, "<method params>");
    r = expression(b, l + 1);
    r = r && method_params_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (',' expression) *
  private static boolean method_params_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_params_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!method_params_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "method_params_1", c)) break;
    }
    return true;
  }

  // ',' expression
  private static boolean method_params_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_params_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ('$string' | '$number()') '(' [method_params|] ')'
  public static boolean methods(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "methods")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, METHODS, "<methods>");
    r = methods_0(b, l + 1);
    r = r && consumeToken(b, "(");
    r = r && methods_2(b, l + 1);
    r = r && consumeToken(b, ")");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '$string' | '$number()'
  private static boolean methods_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "methods_0")) return false;
    boolean r;
    r = consumeToken(b, "$string");
    if (!r) r = consumeToken(b, "$number()");
    return r;
  }

  // [method_params|]
  private static boolean methods_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "methods_2")) return false;
    methods_2_0(b, l + 1);
    return true;
  }

  // method_params|
  private static boolean methods_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "methods_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = method_params(b, l + 1);
    if (!r) r = consumeToken(b, METHODS_2_0_1_0);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '{' [!'}' prop (!'}' ',' prop) *] '}'
  public static boolean object(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object")) return false;
    if (!nextTokenIs(b, BRACE1)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, OBJECT, null);
    r = consumeToken(b, BRACE1);
    p = r; // pin = 1
    r = r && report_error_(b, object_1(b, l + 1));
    r = p && consumeToken(b, BRACE2) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [!'}' prop (!'}' ',' prop) *]
  private static boolean object_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_1")) return false;
    object_1_0(b, l + 1);
    return true;
  }

  // !'}' prop (!'}' ',' prop) *
  private static boolean object_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = object_1_0_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, prop(b, l + 1));
    r = p && object_1_0_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // !'}'
  private static boolean object_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, BRACE2);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (!'}' ',' prop) *
  private static boolean object_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_1_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!object_1_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "object_1_0_2", c)) break;
    }
    return true;
  }

  // !'}' ',' prop
  private static boolean object_1_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_1_0_2_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = object_1_0_2_0_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && prop(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // !'}'
  private static boolean object_1_0_2_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_1_0_2_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, BRACE2);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // math_operators | comp_operators | concat_operators | cond_operators
  public static boolean operators(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operators")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPERATORS, "<operators>");
    r = math_operators(b, l + 1);
    if (!r) r = comp_operators(b, l + 1);
    if (!r) r = concat_operators(b, l + 1);
    if (!r) r = cond_operators(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (deep_prop | filter) '^(' order_params ')' post *
  public static boolean order(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "order")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ORDER, "<order>");
    r = order_0(b, l + 1);
    r = r && consumeToken(b, "^(");
    r = r && order_params(b, l + 1);
    r = r && consumeToken(b, ")");
    r = r && order_4(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // deep_prop | filter
  private static boolean order_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "order_0")) return false;
    boolean r;
    r = deep_prop(b, l + 1);
    if (!r) r = filter(b, l + 1);
    return r;
  }

  // post *
  private static boolean order_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "order_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!post(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "order_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // ['>'|'<'] expression
  static boolean order_param(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "order_param")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = order_param_0(b, l + 1);
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ['>'|'<']
  private static boolean order_param_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "order_param_0")) return false;
    order_param_0_0(b, l + 1);
    return true;
  }

  // '>'|'<'
  private static boolean order_param_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "order_param_0_0")) return false;
    boolean r;
    r = consumeToken(b, ">");
    if (!r) r = consumeToken(b, "<");
    return r;
  }

  /* ********************************************************** */
  // order_param (',' order_param) *
  public static boolean order_params(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "order_params")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ORDER_PARAMS, "<order params>");
    r = order_param(b, l + 1);
    r = r && order_params_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (',' order_param) *
  private static boolean order_params_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "order_params_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!order_params_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "order_params_1", c)) break;
    }
    return true;
  }

  // ',' order_param
  private static boolean order_params_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "order_params_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && order_param(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ('.' (filter | deep_prop )) | ('^(' order_params ')')
  static boolean post(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "post")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = post_0(b, l + 1);
    if (!r) r = post_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '.' (filter | deep_prop )
  private static boolean post_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "post_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ".");
    r = r && post_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // filter | deep_prop
  private static boolean post_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "post_0_1")) return false;
    boolean r;
    r = filter(b, l + 1);
    if (!r) r = deep_prop(b, l + 1);
    return r;
  }

  // '^(' order_params ')'
  private static boolean post_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "post_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "^(");
    r = r && order_params(b, l + 1);
    r = r && consumeToken(b, ")");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // [] jname ':' value
  public static boolean prop(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prop")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PROP, "<prop>");
    r = prop_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, jname(b, l + 1));
    r = p && report_error_(b, consumeToken(b, COLON)) && r;
    r = p && value(b, l + 1) && r;
    exit_section_(b, l, m, r, p, JSONataParser::recover);
    return r || p;
  }

  // []
  private static boolean prop_0(PsiBuilder b, int l) {
    return true;
  }

  /* ********************************************************** */
  // !(',' | ']' | '}' | '[' | '{' | '(' | ')')
  static boolean recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ',' | ']' | '}' | '[' | '{' | '(' | ')'
  private static boolean recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "recover_0")) return false;
    boolean r;
    r = consumeToken(b, COMMA);
    if (!r) r = consumeToken(b, BRACK2);
    if (!r) r = consumeToken(b, BRACE2);
    if (!r) r = consumeToken(b, BRACK1);
    if (!r) r = consumeToken(b, BRACE1);
    if (!r) r = consumeToken(b, "(");
    if (!r) r = consumeToken(b, ")");
    return r;
  }

  /* ********************************************************** */
  // jsonata
  static boolean root(PsiBuilder b, int l) {
    return jsonata(b, l + 1);
  }

  /* ********************************************************** */
  // '|' transform_location '|' expression [','  array] '|'
  public static boolean transform(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "transform")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TRANSFORM, "<transform>");
    r = consumeToken(b, "|");
    r = r && transform_location(b, l + 1);
    r = r && consumeToken(b, "|");
    r = r && expression(b, l + 1);
    r = r && transform_4(b, l + 1);
    r = r && consumeToken(b, "|");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [','  array]
  private static boolean transform_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "transform_4")) return false;
    transform_4_0(b, l + 1);
    return true;
  }

  // ','  array
  private static boolean transform_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "transform_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && array(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // expression '~>' transform ('~>' transform) *
  public static boolean transform_call(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "transform_call")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TRANSFORM_CALL, "<transform call>");
    r = expression(b, l + 1);
    r = r && consumeToken(b, "~>");
    r = r && transform(b, l + 1);
    r = r && transform_call_3(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ('~>' transform) *
  private static boolean transform_call_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "transform_call_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!transform_call_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "transform_call_3", c)) break;
    }
    return true;
  }

  // '~>' transform
  private static boolean transform_call_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "transform_call_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "~>");
    r = r && transform(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // id ('.' id) *
  public static boolean transform_location(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "transform_location")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    r = r && transform_location_1(b, l + 1);
    exit_section_(b, m, TRANSFORM_LOCATION, r);
    return r;
  }

  // ('.' id) *
  private static boolean transform_location_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "transform_location_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!transform_location_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "transform_location_1", c)) break;
    }
    return true;
  }

  // '.' id
  private static boolean transform_location_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "transform_location_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ".");
    r = r && consumeToken(b, ID);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // jsonata | string | number | boolean
  public static boolean value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "value")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VALUE, "<value>");
    r = jsonata(b, l + 1);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, BOOLEAN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // variable ':=' jsonata
  public static boolean variable_set(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_set")) return false;
    if (!nextTokenIs(b, VARIABLE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, VARIABLE);
    r = r && consumeToken(b, ":=");
    r = r && jsonata(b, l + 1);
    exit_section_(b, m, VARIABLE_SET, r);
    return r;
  }

}
