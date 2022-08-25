package org.dochub.idea.arch.settings;

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
    private final DefaultComboBoxModel<String> renderModeModel = new DefaultComboBoxModel<>();
    private final ComboBox<String> renderMode = new ComboBox<>(renderModeModel);
    private final JBTextField renderServer = new JBTextField();
    private final JBCheckBox isExternalRender = new JBCheckBox("External rendering");

    public SettingComponent() {
        renderModeModel.addAll(Arrays.asList(SettingsState.renderModes));

        isExternalRender.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateVisible();
            }
        });

        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Render mode: "), renderMode, 1, false)
                .addComponent(isExternalRender, 1)
                .addLabeledComponent(new JBLabel("Render server: "), renderServer, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();

        updateVisible();
    }

    private void updateVisible() {
        renderServer.setEnabled(isExternalRender.isSelected());
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return renderMode;
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

}
