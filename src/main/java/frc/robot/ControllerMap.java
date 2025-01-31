package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.CommandMap;

public class ControllerMap {
    CommandMap commandMap;
    CommandXboxController controller;
    String name;

    public ControllerMap(CommandMap commandMap, CommandXboxController controller, String name) {
        this.commandMap = commandMap;
        this.controller = controller;
        this.name = name;
    }

    private ButtonMap getButtonMap(Trigger trigger, String button) {
        String title = String.join(" ", name, button);

        return new ButtonMap(
            commandMap.getMap(), 
            trigger, 
            title,
            convertToSnakeCase(title)
        );
    }

    public ButtonMap a() {
        return getButtonMap(controller.a(), "A Button");
    }

    public ButtonMap b() {
        return getButtonMap(controller.b(), "B Button");
    }

    public ButtonMap x() {
        return getButtonMap(controller.x(), "X Button");
    }

    public ButtonMap y() {
        return getButtonMap(controller.y(), "Y Button");
    }

    public ButtonMap leftBumper() {
        return getButtonMap(controller.leftBumper(), "Left Bumper");
    }

    public ButtonMap rightBumper() {
        return getButtonMap(controller.rightBumper(), "Right Bumper");
    }

    public ButtonMap leftTrigger() {
        return getButtonMap(controller.leftTrigger(.8), "Left Trigger");
    }

    public ButtonMap rightTrigger() {
        return getButtonMap(controller.rightTrigger(.8), "Right Trigger");
    }

    public ButtonMap start() {
        return getButtonMap(controller.start(), "Start");
    }

    public ButtonMap back() {
        return getButtonMap(controller.back(), "Back");
    }

    public ButtonMap leftStick() {
        return getButtonMap(controller.leftStick(), "Left Stick");
    }

    public ButtonMap rightStick() {
        return getButtonMap(controller.rightStick(), "Right Stick");
    }

    public ButtonMap povUp() {
        return getButtonMap(controller.povUp(), "Dpad Up");
    }

    public ButtonMap povDown() {
        return getButtonMap(controller.povDown(), "Dpad Down");
    }

    public ButtonMap povLeft() {
        return getButtonMap(controller.povLeft(), "Dpad Left");
    }

    public ButtonMap povRight() {
        return getButtonMap(controller.povRight(), "Dpad Right");
    }

    public String convertToSnakeCase(String input) {
        return input.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
}
