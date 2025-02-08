package frc.robot;

import java.util.Map;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class ButtonMap {
    private Command mappedCommand;
    private String commandName;
    private String preferenceKey;
    private String widgetKey;
    private String widgetModeKey;
    private ButtonMode cachedMode;
    private String cachedCommandValue;

    private enum ButtonMode {
        PRESS,
        HOLD,
        RELEASE
    };

    private SendableChooser<ButtonMode> buttonAction = new SendableChooser<>();
    
    public ButtonMap(Map<String, Command> map, Trigger buttonTrigger, String title, String pKey) {
        mappedCommand = new SelectCommand<>(map, this::getSavedCommandKey);
        preferenceKey = pKey;
        widgetKey = title;
        widgetModeKey = widgetKey + " Mode";

        map.forEach((key, value) -> {
            commandName = key;
        });
        
        SmartDashboard.putString(widgetKey, commandName);

        
        buttonAction.setDefaultOption("Press", ButtonMode.PRESS);
        buttonAction.addOption("Hold", ButtonMode.HOLD);
        buttonAction.addOption("Release", ButtonMode.RELEASE);
        SmartDashboard.putData(widgetModeKey, buttonAction);
        
        buttonTrigger.and(() -> cachedMode == ButtonMode.PRESS).onTrue(mappedCommand);
        buttonTrigger.and(() -> cachedMode == ButtonMode.HOLD).whileTrue(mappedCommand);
        //buttonTrigger.and(() -> cachedMode == ButtonMode.RELEASE).onFalse(mappedCommand);
    }

    public void updateCachedValues() {
        cachedCommandValue = SmartDashboard.getString(widgetKey, "");
        cachedMode = buttonAction.getSelected();
    }

    public String getSavedCommandKey() {
        return cachedCommandValue;
    }

    public void loadPreferenceCommandKey(String saveSlotKey) {
        String newCommandName = Preferences.getString(saveSlotKey + preferenceKey, SmartDashboard.getString(widgetKey, ""));
        SmartDashboard.putString(widgetKey, newCommandName);
    }

    public void savePreferenceCommandKey(String saveSlotKey) {
        Preferences.setString(saveSlotKey + preferenceKey, SmartDashboard.getString(widgetKey, ""));
    }
}
