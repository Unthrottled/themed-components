package io.unthrottled.themed.components.settings.ui;

import com.intellij.ide.actions.QuickChangeLookAndFeel;
import com.intellij.ide.ui.LafManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.DumbAware;
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
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class SettingsUI implements SearchableConfigurable, Configurable.NoScroll, DumbAware {
  private PluginSettingsModel pluginSettingsModel = Configurations.getInitialSettings();
  private PluginSettingsModel initialSettings = pluginSettingsModel.duplicate();
  private JTabbedPane tabbedPane1;
  private JPanel rootPane;
  private JCheckBox themedTitleBarBox;
  private ColorPanel foregroundColorPanel;
  private ColorPanel inactiveForegroundColorPanel;
  private JCheckBox enableCustomColors;
  private ColorPanel selectedRowBackgroundColor;
  private ColorPanel activeSelectedColorPanel;

  private void createUIComponents() {
    var settings = Configurations.getInitialSettings();

    foregroundColorPanel = new ColorPanel();
    foregroundColorPanel.repaint();
    foregroundColorPanel.addActionListener(e ->
      pluginSettingsModel.setTitleForegroundColor(
        ToolBox.toHex(Objects.requireNonNull(this.foregroundColorPanel.getSelectedColor()))
      ));

    inactiveForegroundColorPanel = new ColorPanel();
    inactiveForegroundColorPanel.repaint();
    inactiveForegroundColorPanel.addActionListener(e ->
      pluginSettingsModel.setTitleInactiveForegroundColor(
        ToolBox.toHex(Objects.requireNonNull(inactiveForegroundColorPanel.getSelectedColor()))
      ));

    selectedRowBackgroundColor = new ColorPanel();
    selectedRowBackgroundColor.repaint();
    selectedRowBackgroundColor.addActionListener(e ->
      pluginSettingsModel.getCustomColoring().put(
        Constants.COMPLETION_SELECTION_INACTIVE,
        ToolBox.toHex(Objects.requireNonNull(selectedRowBackgroundColor.getSelectedColor()))
      ));

    activeSelectedColorPanel = new ColorPanel();
    activeSelectedColorPanel.repaint();
    activeSelectedColorPanel.addActionListener(e ->
      pluginSettingsModel.getCustomColoring().put(
        Constants.COMPLETION_SELECTION_ACTIVE,
        ToolBox.toHex(Objects.requireNonNull(activeSelectedColorPanel.getSelectedColor()))
      ));

    initializeCustomCreateComponents(settings);
  }

  private void initializeCustomCreateComponents(PluginSettingsModel settings) {
    foregroundColorPanel.setSelectedColor(
      ToolBox.toColor(settings.getTitleForegroundColor())
        .orElse(JBColor.namedColor(Constants.TITLE_PANE_PROP, JBColor.WHITE))
    );
    inactiveForegroundColorPanel.setSelectedColor(
      ToolBox.toColor(settings.getTitleInactiveForegroundColor())
        .orElse(JBColor.namedColor(Constants.TITLE_PANE_INACTIVE_PROP, JBColor.WHITE))
    );

    selectedRowBackgroundColor.setSelectedColor(
      ToolBox.toColor(settings.getCustomColoring().get(Constants.COMPLETION_SELECTION_INACTIVE))
        .orElse(JBColor.namedColor(Constants.COMPLETION_SELECTION_INACTIVE, JBColor.WHITE))
    );
    activeSelectedColorPanel.setSelectedColor(
      ToolBox.toColor(settings.getCustomColoring().get(Constants.COMPLETION_SELECTION_ACTIVE))
        .orElse(JBColor.namedColor(Constants.COMPLETION_SELECTION_ACTIVE, JBColor.WHITE))
    );

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
    initializeComponents();
    return rootPane;
  }

  private void initializeComponents() {
    enableCustomColors.setSelected(initialSettings.isCustomColors());
    enableCustomColors.addActionListener(e ->
      pluginSettingsModel.setCustomColors(enableCustomColors.isSelected()));
    themedTitleBarBox.setSelected(initialSettings.isThemedTitleBar());
    themedTitleBarBox.addActionListener(e ->
      pluginSettingsModel.setThemedTitleBar(themedTitleBarBox.isSelected()));
  }

  @Override
  public boolean isModified() {
    return !initialSettings.equals(pluginSettingsModel);
  }

  @Override
  public void reset() {
    initializeComponents();
    initializeCustomCreateComponents(initialSettings);
    pluginSettingsModel = initialSettings.duplicate();
  }

  @Override
  public void apply() {
    Configurations.getInstance().setCustomColors(pluginSettingsModel.isCustomColors());
    Configurations.getInstance().setTitleInactiveForegroundColor(pluginSettingsModel.getTitleInactiveForegroundColor());
    Configurations.getInstance().setTitleForegroundColor(pluginSettingsModel.getTitleForegroundColor());
    Configurations.getInstance().setThemedTitleBar(pluginSettingsModel.isThemedTitleBar());
    Configurations.getInstance().setCustomColoring(
      pluginSettingsModel.getCustomColoring().entrySet()
        .stream()
        .map(pair -> pair.getKey() + Configurations.DEFAULT_VALUE_DELIMITER + pair.getValue())
        .collect(Collectors.joining(Configurations.DEFAULT_DELIMITER))
    );

    LookAndFeelInstaller.INSTANCE.installAllUIComponents();

    var currentTheme = LafManager.getInstance().getCurrentLookAndFeel();
    Arrays.stream(LafManager.getInstance().getInstalledLookAndFeels())
      .filter(theme -> theme.equals(currentTheme))
      .findAny()
    .ifPresent(otherTheme -> {
      QuickChangeLookAndFeel.switchLafAndUpdateUI(
        LafManager.getInstance(),
        otherTheme,
        true
      );
      QuickChangeLookAndFeel.switchLafAndUpdateUI(
        LafManager.getInstance(),
        currentTheme,
        true
      );
    });

    initialSettings = pluginSettingsModel.duplicate();
  }
}
