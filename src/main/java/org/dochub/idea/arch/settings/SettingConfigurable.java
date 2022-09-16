package org.dochub.idea.arch.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SettingConfigurable implements Configurable {

    private SettingComponent settingComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "DocHub: External server rendering";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return settingComponent.getPreferredFocusedComponent();
    }

    @Override
    public @Nullable JComponent createComponent() {
        settingComponent = new SettingComponent();
        return settingComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        SettingsState settingsState = SettingsState.getInstance();
        boolean modified = !settingComponent.getRenderServerText().equals(settingsState.serverRendering);
        modified |= !settingComponent.getRenderModeText().equals(settingsState.renderMode);
        modified |= settingComponent.getIsExternalRenderBool() != settingsState.renderIsExternal;
        return modified;
    }

    @Override
    public void apply() throws ConfigurationException {
        SettingsState settingsState = SettingsState.getInstance();
        settingsState.renderMode = settingComponent.getRenderModeText();
        settingsState.serverRendering = settingComponent.getRenderServerText();
        settingsState.renderIsExternal = settingComponent.getIsExternalRenderBool();
    }

    @Override
    public void reset() {
        SettingsState settingsState = SettingsState.getInstance();
        settingComponent.setRenderModeText(settingsState.renderMode);
        settingComponent.setRenderServerText(settingsState.serverRendering);
        settingComponent.setIsExternalRenderBool(settingsState.renderIsExternal);
    }

    @Override
    public void disposeUIResources() {
        settingComponent = null;
    }
}
