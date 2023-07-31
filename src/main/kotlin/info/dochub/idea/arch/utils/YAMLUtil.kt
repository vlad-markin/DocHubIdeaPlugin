package info.dochub.idea.arch.utils

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.yaml.psi.YAMLKeyValue
import org.jetbrains.yaml.psi.YAMLPsiElement
import org.jetbrains.yaml.psi.YAMLSequenceItem

class YAMLUtil {
    companion object {
        fun getConfigFullName(target: YAMLPsiElement): String {
            val builder = StringBuilder()
            var element: PsiElement? = target
            while (element != null) {
                val parent: PsiElement? = PsiTreeUtil.getParentOfType(
                    element,
                    YAMLKeyValue::class.java,
                    YAMLSequenceItem::class.java
                )
                if (element is YAMLKeyValue) {
                    builder.insert(0, element.keyText.let { if (it.contains('.')) "\"$it\"" else it })
                    if (parent != null) {
                        builder.insert(0, '.')
                    }
                } else if (element is YAMLSequenceItem) {
                    builder.insert(0, "[" + element.itemIndex + "]")
                }
                element = parent
            }
            return builder.toString()
        }
    }
}