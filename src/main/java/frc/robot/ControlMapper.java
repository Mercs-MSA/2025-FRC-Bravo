package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.CommandMap;

public class ControlMapper {
    // This is the dashboard widget with a dropdown to select a Command
    private String mappingChooser = new String();
    private Command mappedCommand =
        new SelectCommand<>(
            new CommandMap().getMap(),
            this::getMappedCommandKey
        );
    public String preferenceKey;
    public String chooserTitle;
    
    public ControlMapper(Trigger buttonTrigger, String title, String pKey) {
        self(buttonTrigger, title, pKey, true);
    }

    public ControlMapper(Trigger buttonTrigger, String title, String pKey, boolean isPress) {
        new CommandMap().getMap().forEach((key, value) -> {
            mappingChooser = key;
        });
        
        SmartDashboard.putString(title, mappingChooser);
        preferenceKey = pKey;
        chooserTitle = title;
        if (isPress) {
            buttonTrigger.onTrue(mappedCommand);
        } else {
            buttonTrigger.whileTrue(mappedCommand);
        }
    }

    public String getMappedCommandKey() {
        String output = SmartDashboard.getString(chooserTitle, "");
        return output;
    }

    public void setMapperCommandKey(String input) {
        SmartDashboard.putString(chooserTitle, input);
    }

    public Command getCommand() {
        return mappedCommand;
    }
}