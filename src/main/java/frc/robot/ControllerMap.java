package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.CommandMap;

public class ControllerMap {
    CommandMap commandMap;
    CommandXboxController controller;
    String controllerName;

    private ButtonMap buttonMapA;
    private ButtonMap buttonMapB;
    private ButtonMap buttonMapX;
    private ButtonMap buttonMapY;
    private ButtonMap buttonMapRightBumper;
    private ButtonMap buttonMapLeftBumper;
    private ButtonMap buttonMapStart;
    private ButtonMap buttonMapBack;
    private ButtonMap buttonMapLeftTrigger;
    private ButtonMap buttonMapRightTrigger;
    private ButtonMap buttonMapDpadUp;
    private ButtonMap buttonMapDpadDown;
    private ButtonMap buttonMapDpadLeft;
    private ButtonMap buttonMapDpadRight;
    private ButtonMap buttonMapLeftStickPress;
    private ButtonMap buttonMapRightStickPress;

    public ControllerMap(CommandMap commandMap, CommandXboxController controller, String controllerName) {
        this.commandMap = commandMap;
        this.controller = controller;
        this.controllerName = controllerName;

        buttonMapA = getButtonMap(controller.a(), "A Button");
        buttonMapB = getButtonMap(controller.b(), "B Button");
        buttonMapX = getButtonMap(controller.x(), "X Button");
        buttonMapY = getButtonMap(controller.y(), "Y Button");
        buttonMapRightBumper = getButtonMap(controller.leftBumper(), "Left Bumper");
        buttonMapLeftBumper = getButtonMap(controller.rightBumper(), "Right Bumper");
        buttonMapStart = getButtonMap(controller.leftTrigger(.8), "Left Trigger");
        buttonMapBack = getButtonMap(controller.rightTrigger(.8), "Right Trigger");
        buttonMapLeftTrigger = getButtonMap(controller.start(), "Start");
        buttonMapRightTrigger = getButtonMap(controller.back(), "Back");
        buttonMapDpadUp = getButtonMap(controller.leftStick(), "Left Stick");
        buttonMapDpadDown = getButtonMap(controller.rightStick(), "Right Stick");
        buttonMapDpadLeft = getButtonMap(controller.povUp(), "Dpad Up");
        buttonMapDpadRight = getButtonMap(controller.povDown(), "Dpad Down");
        buttonMapLeftStickPress = getButtonMap(controller.povLeft(), "Dpad Left");
        buttonMapRightStickPress = getButtonMap(controller.povRight(), "Dpad Right");
    }

    private ButtonMap getButtonMap(Trigger trigger, String button) {
        String title = String.join(" ", controllerName, button);

        return new ButtonMap(
            commandMap.getMap(), 
            trigger, 
            title,
            convertToSnakeCase(title)
        );
    }

    public void updateControllerMaps() {
        buttonMapA.updateCachedValues();
        buttonMapB.updateCachedValues();
        buttonMapX.updateCachedValues();
        buttonMapY.updateCachedValues();
        buttonMapRightBumper.updateCachedValues();
        buttonMapLeftBumper.updateCachedValues();
        buttonMapStart.updateCachedValues();
        buttonMapBack.updateCachedValues();
        buttonMapLeftTrigger.updateCachedValues();
        buttonMapRightTrigger.updateCachedValues();
        buttonMapDpadUp.updateCachedValues();
        buttonMapDpadDown.updateCachedValues();
        buttonMapDpadLeft.updateCachedValues();
        buttonMapDpadRight.updateCachedValues();
        buttonMapLeftStickPress.updateCachedValues();
        buttonMapRightStickPress.updateCachedValues();
    }

    public void savePreference(String saveSlotKey) {
        buttonMapA.savePreferenceCommandKey(saveSlotKey);
        buttonMapB.savePreferenceCommandKey(saveSlotKey);
        buttonMapX.savePreferenceCommandKey(saveSlotKey);
        buttonMapY.savePreferenceCommandKey(saveSlotKey);
        buttonMapRightBumper.savePreferenceCommandKey(saveSlotKey);
        buttonMapLeftBumper.savePreferenceCommandKey(saveSlotKey);
        buttonMapStart.savePreferenceCommandKey(saveSlotKey);
        buttonMapBack.savePreferenceCommandKey(saveSlotKey);
        buttonMapLeftTrigger.savePreferenceCommandKey(saveSlotKey);
        buttonMapRightTrigger.savePreferenceCommandKey(saveSlotKey);
        buttonMapDpadUp.savePreferenceCommandKey(saveSlotKey);
        buttonMapDpadDown.savePreferenceCommandKey(saveSlotKey);
        buttonMapDpadLeft.savePreferenceCommandKey(saveSlotKey);
        buttonMapDpadRight.savePreferenceCommandKey(saveSlotKey);
        buttonMapLeftStickPress.savePreferenceCommandKey(saveSlotKey);
        buttonMapRightStickPress.savePreferenceCommandKey(saveSlotKey);
    }

    public void loadPreference(String saveSlotKey) {
        buttonMapA.loadPreferenceCommandKey(saveSlotKey);
        buttonMapB.loadPreferenceCommandKey(saveSlotKey);
        buttonMapX.loadPreferenceCommandKey(saveSlotKey);
        buttonMapY.loadPreferenceCommandKey(saveSlotKey);
        buttonMapRightBumper.loadPreferenceCommandKey(saveSlotKey);
        buttonMapLeftBumper.loadPreferenceCommandKey(saveSlotKey);
        buttonMapStart.loadPreferenceCommandKey(saveSlotKey);
        buttonMapBack.loadPreferenceCommandKey(saveSlotKey);
        buttonMapLeftTrigger.loadPreferenceCommandKey(saveSlotKey);
        buttonMapRightTrigger.loadPreferenceCommandKey(saveSlotKey);
        buttonMapDpadUp.loadPreferenceCommandKey(saveSlotKey);
        buttonMapDpadDown.loadPreferenceCommandKey(saveSlotKey);
        buttonMapDpadLeft.loadPreferenceCommandKey(saveSlotKey);
        buttonMapDpadRight.loadPreferenceCommandKey(saveSlotKey);
        buttonMapLeftStickPress.loadPreferenceCommandKey(saveSlotKey);
        buttonMapRightStickPress.loadPreferenceCommandKey(saveSlotKey);
    }

    public String convertToSnakeCase(String input) {
        return input.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
}
