package org.dochub.idea.arch.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.util.NlsActions
import org.dochub.idea.arch.markline.LineMarkerNavigator
import java.util.function.Supplier
import javax.swing.Icon
import kotlin.io.encoding.Base64.Default.UrlSafe
import kotlin.io.encoding.ExperimentalEncodingApi

class PopupJSONataRunAction: AnAction {
    constructor() : super()
    constructor(icon: Icon?) : super(icon)
    constructor(text: @NlsActions.ActionText String?) : super(text)
    constructor(dynamicText: Supplier<@NlsActions.ActionText String>) : super(dynamicText)
    constructor(
        text: @NlsActions.ActionText String?,
        description: @NlsActions.ActionDescription String?,
        icon: Icon?
    ) : super(text, description, icon)

    constructor(dynamicText: Supplier<@NlsActions.ActionText String>, icon: Icon?) : super(dynamicText, icon)
    constructor(
        dynamicText: Supplier<@NlsActions.ActionText String>,
        dynamicDescription: Supplier<@NlsActions.ActionDescription String>,
        icon: Icon?
    ) : super(dynamicText, dynamicDescription, icon)

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = e.getData(CommonDataKeys.EDITOR)?.selectionModel?.selectedText != null
    }

    @OptIn(ExperimentalEncodingApi::class)
    override fun actionPerformed(e: AnActionEvent) {
        val selection = e.getData(CommonDataKeys.EDITOR)?.selectionModel?.selectedText ?: return
        e.project
            ?.messageBus
            ?.syncPublisher(LineMarkerNavigator.ON_NAVIGATE_MESSAGE)
            ?.go("devtool", "selection:${UrlSafe.encode(selection.toByteArray())}")
    }
}