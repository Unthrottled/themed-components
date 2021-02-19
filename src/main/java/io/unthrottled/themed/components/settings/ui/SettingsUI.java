package io.unthrottled.themed.components.settings.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.wm.impl.IdeBackgroundUtil;
import com.intellij.ui.ColorPanel;
import com.intellij.ui.JBColor;
import io.unthrottled.themed.components.laf.LookAndFeelInstaller;
import io.unthrottled.themed.components.settings.ComponentsSettings;
import io.unthrottled.themed.components.settings.Configurations;
import io.unthrottled.themed.components.settings.PluginSettingsModel;
import io.unthrottled.themed.components.util.Constants;
import io.unthrottled.themed.components.util.ToolBox;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;

public class SettingsUI implements SearchableConfigurable, Configurable.NoScroll, DumbAware {
  private final PluginSettingsModel pluginSettingsModel = Configurations.getInitialSettings();
  private PluginSettingsModel initialSettings = Configurations.getInitialSettings();
  private JTabbedPane tabbedPane1;
  private JPanel panel1;
  private JCheckBox themedTitleBarBox;
  private ColorPanel foregroundColorPanel;
  private ColorPanel inactiveForegroundColorPanel;
  private JCheckBox enableCustomColors;

  private void createUIComponents() {
    var settings = Configurations.getInitialSettings();
    foregroundColorPanel = new ColorPanel();
    foregroundColorPanel.setSelectedColor(
      ToolBox.toColor(settings.getTitleForegroundColor())
        .orElse(JBColor.namedColor(Constants.TITLE_PANE_PROP, JBColor.WHITE))
    );
    foregroundColorPanel.repaint();
    foregroundColorPanel.addActionListener(e ->
      pluginSettingsModel.setTitleForegroundColor(
        ToolBox.toHex(Objects.requireNonNull(this.foregroundColorPanel.getSelectedColor()))
      ));

    inactiveForegroundColorPanel = new ColorPanel();
    inactiveForegroundColorPanel.setSelectedColor(
      ToolBox.toColor(settings.getTitleForegroundColor())
        .orElse(JBColor.namedColor(Constants.TITLE_PANE_INACTIVE_PROP, JBColor.WHITE))
    );
    inactiveForegroundColorPanel.repaint();
    inactiveForegroundColorPanel.addActionListener(e ->
      pluginSettingsModel.setTitleForegroundColor(
        ToolBox.toHex(Objects.requireNonNull(inactiveForegroundColorPanel.getSelectedColor()))
      ));
  }

  @Override
  public @NotNull
  @NonNls
  String getId() {
    return "io.unthrottled.themed.components.settings.PluginSettings";
  }

  @Override
  public String getDisplayName() {
    return ComponentsSettings.THEME_SETTINGS_DISPLAY_NAME;
  }

  @Override
  public @Nullable JComponent createComponent() {
    enableCustomColors.setSelected(initialSettings.isCustomColors());

    return panel1;
  }

  @Override
  public boolean isModified() {
    return !initialSettings.equals(pluginSettingsModel);
  }

  @Override
  public void apply() {
    Configurations.getInstance().setCustomColors(pluginSettingsModel.isCustomColors());
    Configurations.getInstance().setTitleInactiveForegroundColor(pluginSettingsModel.getTitleInactiveForegroundColor());
    Configurations.getInstance().setTitleForegroundColor(pluginSettingsModel.getTitleForegroundColor());

    LookAndFeelInstaller.INSTANCE.installAllUIComponents();
    IdeBackgroundUtil.repaintAllWindows();
    initialSettings = pluginSettingsModel.duplicate();
  }
}
