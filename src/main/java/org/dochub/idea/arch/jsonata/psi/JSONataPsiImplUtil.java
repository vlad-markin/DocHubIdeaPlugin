package org.dochub.idea.arch.jsonata.psi;

import com.intellij.lang.ASTNode;


public class JSONataPsiImplUtil {

    public static String getKey(JSONataPsiVariable element) {
        ASTNode keyNode = element.getNode().findChildByType(JSONataTypes.VARIABLE);
        if (keyNode != null) {
            // IMPORTANT: Convert embedded escaped spaces to simple spaces
            return keyNode.getText().replaceAll("\\\\ ", " ");
        } else {
            return null;
        }
    }
}
