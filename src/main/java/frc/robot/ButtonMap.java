package frc.robot;

import java.util.Map;

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

    private enum ButtonMode {
        PRESS,
        HOLD,
        RELEASE
    };

    private SendableChooser<ButtonMode> buttonAction = new SendableChooser<>();
    
    public ButtonMap(Map<String, Command> map, Trigger buttonTrigger, String title, String pKey) {
        mappedCommand = new SelectCommand<>(map, this::getMappedCommandKey);
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

        buttonTrigger.and(() -> buttonAction.getSelected() == ButtonMode.PRESS).onTrue(mappedCommand);
        buttonTrigger.and(() -> buttonAction.getSelected() == ButtonMode.HOLD).whileTrue(mappedCommand);
        buttonTrigger.and(() -> buttonAction.getSelected() == ButtonMode.RELEASE).onFalse(mappedCommand);
    }

    public String getMappedCommandKey() {
        String output = SmartDashboard.getString(widgetKey, "");
        return output;
    }

    public void setMapperCommandKey(String newCommandName) {
        SmartDashboard.putString(widgetKey, newCommandName);
    }

    public Command getCommand() {
        return mappedCommand;
    }

    public String getPreferenceKey() {
        return preferenceKey;
    }

    public String getWidgetKey() {
        return widgetKey;
    }
}