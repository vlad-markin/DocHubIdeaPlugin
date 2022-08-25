package org.dochub.idea.arch.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.refactoring.extractSuperclass.BindToOldUsageInfo;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "org.dochub.idea.settings.DocHubSettings",
        storages = @Storage("DocHubSettings.xml")
)
public class SettingsState implements PersistentStateComponent<SettingsState> {
    public static String defaultRenderServer = "http://localhost:8079/svg/";
    public static Boolean defaultIsExternalRender = false;
    public static String defaultRenderMode = "Smetana";
    public static String renderModes[] = { "Smetana", "ELK", "GraphViz" };
    public String renderMode = SettingsState.defaultRenderMode;
    public Boolean renderIsExternal = SettingsState.defaultIsExternalRender;
    public String serverRendering = SettingsState.defaultRenderServer;

    public static SettingsState getInstance() {
        return ApplicationManager.getApplication().getService(SettingsState.class);
    }

    @Override
    public @Nullable SettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull SettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
