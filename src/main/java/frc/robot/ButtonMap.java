package frc.robot;

import java.util.Map;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class ButtonMap {
    private Command mappedCommand;
    private String widgetString;
    private String preferenceKey;
    private String widgetTitle;
    
    public ButtonMap(Map<String, Command> map, Trigger buttonTrigger, String title, String pKey) {
        this(map, buttonTrigger, title, pKey, true);
    }

    public ButtonMap(Map<String, Command> map, Trigger buttonTrigger, String title, String pKey, boolean isPress) {
        mappedCommand = new SelectCommand<>(map, this::getMappedCommandKey);
        preferenceKey = pKey;
        widgetTitle = title;

        map.forEach((key, value) -> {
            widgetString = key;
        });
        
        SmartDashboard.putString(title, widgetString);

        if (isPress) {
            buttonTrigger.onTrue(mappedCommand);
        } else {
            buttonTrigger.whileTrue(mappedCommand);
        }
    }

    public String getMappedCommandKey() {
        String output = SmartDashboard.getString(widgetTitle, "");
        return output;
    }

    public void setMapperCommandKey(String input) {
        SmartDashboard.putString(widgetTitle, input);
    }

    public Command getCommand() {
        return mappedCommand;
    }

    public String getPreferenceKey() {
        return preferenceKey;
    }

    public String getWidgetTitle() {
        return widgetTitle;
    }
}