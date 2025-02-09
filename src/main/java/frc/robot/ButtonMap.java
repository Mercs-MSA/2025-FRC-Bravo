package frc.robot;

import java.util.Map;

import edu.wpi.first.networktables.BooleanEntry;
import edu.wpi.first.networktables.BooleanTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
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
    private boolean cachedMode;
    private String cachedCommandValue;

    private NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
    private NetworkTable controlMapTable = networkTableInstance.getTable("Control Map Table");
    private BooleanTopic isButtonHeldTopic;
    private BooleanEntry isButtonHeld;
    
    public ButtonMap(Map<String, Command> map, Trigger buttonTrigger, String title, String pKey) {
        mappedCommand = new SelectCommand<>(map, this::getSavedCommandKey);
        preferenceKey = pKey;
        widgetKey = title;
        widgetModeKey = widgetKey + " Mode";

        isButtonHeldTopic = controlMapTable.getBooleanTopic(widgetKey + " Held?");
        isButtonHeld = isButtonHeldTopic.getEntry(false);
    
        map.forEach((key, value) -> {
            commandName = key;
        });
        
        SmartDashboard.putString(widgetKey, commandName);

        isButtonHeldTopic.publish();
        
        buttonTrigger.and(() -> cachedMode == false).onTrue(mappedCommand);
        buttonTrigger.and(() -> cachedMode == true).whileTrue(mappedCommand);
    }

    public void updateCachedValues() {
        cachedCommandValue = SmartDashboard.getString(widgetKey, "");
        cachedMode = isButtonHeld.get();
    }

    public String getSavedCommandKey() {
        return cachedCommandValue;
    }

    public void loadPreferenceCommandKey(String saveSlotKey) {
        String combined = Preferences.getString(saveSlotKey + preferenceKey, SmartDashboard.getString(widgetKey, ""));
        // combined at this point is "Drive Position 1"
        System.out.println(combined);
        if (combined.indexOf("|") > 0) {
            String[] decombined = combined.split("\\|");
            SmartDashboard.putString(widgetKey, decombined[0]);
            isButtonHeld.set(Boolean.parseBoolean(decombined[1]));
            System.out.println(decombined);
        } else {
            SmartDashboard.putString(widgetKey, combined);
        }
        

    }

    public void savePreferenceCommandKey(String saveSlotKey) {
        StringBuilder combined = new StringBuilder();
        combined.append(SmartDashboard.getString(widgetKey, ""));
        combined.append("|");
        combined.append(Boolean.toString(isButtonHeld.get()));
        Preferences.setString(saveSlotKey + preferenceKey, combined.toString());
    }
}
