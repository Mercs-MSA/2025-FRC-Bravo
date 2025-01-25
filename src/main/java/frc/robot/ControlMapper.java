package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.CommandMap;

public class ControlMapper {
    // This is the dashboard widget with a dropdown to select a Command
    private SendableChooser<String> mappingChooser = new SendableChooser<String>();
    private Command mappedCommand =
        new SelectCommand<>(
            new CommandMap().getMap(),
            this::getMappedCommandKey
        );
        public String preferenceKey;
        public String chooserTitle;
        
        public ControlMapper(Trigger buttonTrigger, String title, String pKey) {
            new CommandMap().getMap().forEach((key, value) -> {
                mappingChooser.setDefaultOption(key, key);
            });
            
            SmartDashboard.putData(title, mappingChooser);
            preferenceKey = pKey;
            chooserTitle = title;
            buttonTrigger.onTrue(mappedCommand);
        }

    public String getMappedCommandKey() {
        return mappingChooser.getSelected();
    }

    public void setMapperCommandKey(String input) {
        //SmartDashboard.putData(chooserTitle, input);
        mappingChooser.setDefaultOption(input, input);
    }

    public Command getCommand() {
        return mappedCommand;
    }
}