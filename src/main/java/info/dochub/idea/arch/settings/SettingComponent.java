package info.dochub.idea.arch.settings;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.*;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

public class SettingComponent {
    private final JPanel myMainPanel;
    private final JPanel enterprisePanel;
    private final JPanel paramsPanel;
    private final DefaultComboBoxModel<String> modeModel = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<String> renderModeModel = new DefaultComboBoxModel<>();
    private final ComboBox<String> usingMode = new ComboBox<>(modeModel);
    private final ComboBox<String> renderMode = new ComboBox<>(renderModeModel);
    private final JBTextField renderServer = new JBTextField();
    private final JBCheckBox isExternalRender = new JBCheckBox("External rendering");
    private final JBTextField enterprisePortal = new JBTextField();

    public SettingComponent() {
        modeModel.addAll(Arrays.asList(SettingsState.modes));
        renderModeModel.addAll(Arrays.asList(SettingsState.renderModes));

        isExternalRender.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateVisible();
            }
        });

        usingMode.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateVisible();
            }
        });

        enterprisePanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Enterprise portal: "), enterprisePortal, 1, false)
                .addComponent(new JBLabel("IMPORTANT: All settings will get from enterprise portal."), 1)
                .getPanel();

        paramsPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Render mode: "), renderMode, 1, false)
                .addComponent(isExternalRender, 1)
                .addLabeledComponent(new JBLabel("Render server: "), renderServer, 1, false)
                .getPanel();

        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Using mode: "), usingMode, 1, false)
                .addComponent(enterprisePanel, 1)
                .addComponent(paramsPanel, 1)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();

        updateVisible();
    }

    private void updateVisible() {
        if (usingMode.getSelectedIndex() == 1) {
            enterprisePanel.setVisible(true);
            paramsPanel.setVisible(false);
        } else {
            enterprisePanel.setVisible(false);
            paramsPanel.setVisible(true);
            renderServer.setEnabled(isExternalRender.isSelected());
        }
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return renderMode;
    }

    public String getUsingModeText() {
        return modeModel.getElementAt(usingMode.getSelectedIndex());
    }

    public void setUsingModeText(@NotNull String mode) {
        int index = modeModel.getIndexOf(mode);
        if (index >= 0) usingMode.setSelectedIndex(index);
        updateVisible();
    }

    @NotNull
    public String getRenderModeText() {
        return renderModeModel.getElementAt(renderMode.getSelectedIndex());
    }

    public void setRenderModeText(@NotNull String mode) {
        int index = renderModeModel.getIndexOf(mode);
        if (index >= 0) renderMode.setSelectedIndex(index);
        updateVisible();
    }

    public Boolean getIsExternalRenderBool() {
        return isExternalRender.isSelected();
    }

    public void setIsExternalRenderBool(Boolean value) {
        isExternalRender.setSelected(value);
        updateVisible();
    }

    @NotNull
    public String getRenderServerText() {
        return renderServer.getText();
    }

    public void setRenderServerText(@NotNull String server) {
        renderServer.setText(server);
        updateVisible();
    }

    public String getEnterprisePortalText() {
        return enterprisePortal.getText();
    }

    public void setEnterprisePortalText(@NotNull String portal) {
        enterprisePortal.setText(portal);
        updateVisible();
    }

}
