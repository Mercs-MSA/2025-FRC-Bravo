// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import frc.robot.subsystems.Elevator1;
import frc.robot.subsystems.Elevator2;
import frc.robot.subsystems.FunnelPivot;
import frc.robot.subsystems.IntakeBeambreak;
import frc.robot.subsystems.IntakeFlywheels;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CANdle_LED;
import frc.robot.commands.CommandMap;

public class RobotContainer {
    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(Constants.MaxSpeed * 0.1).withRotationalDeadband(Constants.MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

    private final Telemetry logger = new Telemetry(Constants.MaxSpeed);

    private final CommandXboxController driver = new CommandXboxController(0);
    private final CommandXboxController operator = new CommandXboxController(1);

    private ControlMapper driverMappedButtonA;
    private ControlMapper driverMappedButtonB;
    private ControlMapper driverMappedButtonX;
    private ControlMapper driverMappedButtonY;
    private ControlMapper driverMappedButtonRightBumper;
    private ControlMapper driverMappedButtonLeftBumper;
    private ControlMapper driverMappedButtonStart;
    private ControlMapper driverMappedButtonBack;
    private ControlMapper driverMappedButtonLeftTrigger;
    private ControlMapper driverMappedButtonRightTrigger;
    private ControlMapper driverMappedButtonDpadUp;
    private ControlMapper driverMappedButtonDpadDown;
    private ControlMapper driverMappedButtonDpadLeft;
    private ControlMapper driverMappedButtonDpadRight;
    private ControlMapper driverMappedButtonLeftStickPress;
    private ControlMapper driverMappedButtonRightStickPress;

    private ControlMapper operatorMappedButtonA;
    private ControlMapper operatorMappedButtonB;
    private ControlMapper operatorMappedButtonX;
    private ControlMapper operatorMappedButtonY;
    private ControlMapper operatorMappedButtonRightBumper;
    private ControlMapper operatorMappedButtonLeftBumper;
    private ControlMapper operatorMappedButtonStart;
    private ControlMapper operatorMappedButtonBack;
    private ControlMapper operatorMappedButtonLeftTrigger;
    private ControlMapper operatorMappedButtonRightTrigger;
    private ControlMapper operatorMappedButtonDpadUp;
    private ControlMapper operatorMappedButtonDpadDown;
    private ControlMapper operatorMappedButtonDpadLeft;
    private ControlMapper operatorMappedButtonDpadRight;
    private ControlMapper operatorMappedButtonLeftStickPress;
    private ControlMapper operatorMappedButtonRightStickPress;

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public final Elevator1 m_Elevator1 = new Elevator1(false);

    public final Elevator2 m_Elevator2 = new Elevator2(false);

    public final Climber m_Climber = new Climber(false);

    public final IntakeFlywheels m_IntakeFlywheels = new IntakeFlywheels(true);

    public final IntakeBeambreak m_intakeBeamBreak = new IntakeBeambreak();

    public final FunnelPivot m_FunnelPivot = new FunnelPivot(true);

    public final CANdle_LED m_leds = new CANdle_LED();

    private final SendableChooser<Command> autoChooser;

    public RobotContainer() {
        NamedCommands.registerCommands(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver));
        autoChooser = AutoBuilder.buildAutoChooser("Do Nothing");
        SmartDashboard.putData("Auto Mode", autoChooser);
        configureBindings();
        driverControls();
        operatorControls();
    }

    public void savePreference(SendableChooser<String> savePref) {
        Preferences.setString(savePref.getSelected() + driverMappedButtonY.getPreferenceKey(), driverMappedButtonY.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverMappedButtonX.getPreferenceKey(), driverMappedButtonX.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverMappedButtonA.getPreferenceKey(), driverMappedButtonA.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverMappedButtonB.getPreferenceKey(), driverMappedButtonB.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverMappedButtonLeftTrigger.getPreferenceKey(), driverMappedButtonLeftTrigger.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverMappedButtonRightTrigger.getPreferenceKey(), driverMappedButtonRightTrigger.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverMappedButtonLeftBumper.getPreferenceKey(), driverMappedButtonLeftBumper.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverMappedButtonRightBumper.getPreferenceKey(), driverMappedButtonRightBumper.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverMappedButtonStart.getPreferenceKey(), driverMappedButtonStart.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverMappedButtonBack.getPreferenceKey(), driverMappedButtonBack.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverMappedButtonDpadUp.getPreferenceKey(), driverMappedButtonDpadUp.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverMappedButtonDpadDown.getPreferenceKey(), driverMappedButtonDpadDown.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverMappedButtonDpadLeft.getPreferenceKey(), driverMappedButtonDpadLeft.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverMappedButtonDpadRight.getPreferenceKey(), driverMappedButtonDpadRight.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverMappedButtonLeftStickPress.getPreferenceKey(), driverMappedButtonLeftStickPress.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverMappedButtonRightStickPress.getPreferenceKey(), driverMappedButtonRightStickPress.getMappedCommandKey());

        Preferences.setString(savePref.getSelected() + operatorMappedButtonY.getPreferenceKey(), operatorMappedButtonY.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorMappedButtonX.getPreferenceKey(), operatorMappedButtonX.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorMappedButtonA.getPreferenceKey(), operatorMappedButtonA.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorMappedButtonB.getPreferenceKey(), operatorMappedButtonB.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorMappedButtonLeftTrigger.getPreferenceKey(), operatorMappedButtonLeftTrigger.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorMappedButtonRightTrigger.getPreferenceKey(), operatorMappedButtonRightTrigger.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorMappedButtonLeftBumper.getPreferenceKey(), operatorMappedButtonLeftBumper.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorMappedButtonRightBumper.getPreferenceKey(), operatorMappedButtonRightBumper.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorMappedButtonStart.getPreferenceKey(), operatorMappedButtonStart.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorMappedButtonBack.getPreferenceKey(), operatorMappedButtonBack.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorMappedButtonDpadUp.getPreferenceKey(), operatorMappedButtonDpadUp.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorMappedButtonDpadDown.getPreferenceKey(), operatorMappedButtonDpadDown.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorMappedButtonDpadLeft.getPreferenceKey(), operatorMappedButtonDpadLeft.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorMappedButtonDpadRight.getPreferenceKey(), operatorMappedButtonDpadRight.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorMappedButtonLeftStickPress.getPreferenceKey(), operatorMappedButtonLeftStickPress.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorMappedButtonRightStickPress.getPreferenceKey(), operatorMappedButtonRightStickPress.getMappedCommandKey());
    }

    public void loadPreference(SendableChooser<String> savePref) {
        String loadBControllerD = Preferences.getString(savePref.getSelected() + driverMappedButtonB.getPreferenceKey(), driverMappedButtonB.getMappedCommandKey());
        String loadXControllerD = Preferences.getString(savePref.getSelected() + driverMappedButtonX.getPreferenceKey(), driverMappedButtonX.getMappedCommandKey());
        String loadYControllerD = Preferences.getString(savePref.getSelected() + driverMappedButtonY.getPreferenceKey(), driverMappedButtonY.getMappedCommandKey());
        String loadAControllerD = Preferences.getString(savePref.getSelected() + driverMappedButtonA.getPreferenceKey(), driverMappedButtonA.getMappedCommandKey());
        String loadLeftTriggerControllerD = Preferences.getString(savePref.getSelected() + driverMappedButtonLeftTrigger.getPreferenceKey(), driverMappedButtonLeftTrigger.getMappedCommandKey());
        String loadRightTriggerControllerD = Preferences.getString(savePref.getSelected() + driverMappedButtonRightTrigger.getPreferenceKey(), driverMappedButtonRightTrigger.getMappedCommandKey());
        String loadLeftBumperControllerD = Preferences.getString(savePref.getSelected() + driverMappedButtonLeftBumper.getPreferenceKey(), driverMappedButtonLeftBumper.getMappedCommandKey());
        String loadRightBumperControllerD = Preferences.getString(savePref.getSelected() + driverMappedButtonRightBumper.getPreferenceKey(), driverMappedButtonRightBumper.getMappedCommandKey());
        String loadStartControllerD = Preferences.getString(savePref.getSelected() + driverMappedButtonStart.getPreferenceKey(), driverMappedButtonStart.getMappedCommandKey());
        String loadBackControllerD = Preferences.getString(savePref.getSelected() + driverMappedButtonBack.getPreferenceKey(), driverMappedButtonBack.getMappedCommandKey());
        String loadDpadUpControllerD = Preferences.getString(savePref.getSelected() + driverMappedButtonDpadUp.getPreferenceKey(), driverMappedButtonDpadUp.getMappedCommandKey());
        String loadDpadDownControllerD = Preferences.getString(savePref.getSelected() + driverMappedButtonDpadDown.getPreferenceKey(), driverMappedButtonDpadDown.getMappedCommandKey());
        String loadDpadLeftControllerD = Preferences.getString(savePref.getSelected() + driverMappedButtonDpadLeft.getPreferenceKey(), driverMappedButtonDpadLeft.getMappedCommandKey());
        String loadDpadRightControllerD = Preferences.getString(savePref.getSelected() + driverMappedButtonDpadRight.getPreferenceKey(), driverMappedButtonDpadRight.getMappedCommandKey());
        String loadLeftStickPressControllerD = Preferences.getString(savePref.getSelected() + driverMappedButtonLeftStickPress.getPreferenceKey(), driverMappedButtonLeftStickPress.getMappedCommandKey());
        String loadRightStickPressControllerD = Preferences.getString(savePref.getSelected() + driverMappedButtonRightStickPress.getPreferenceKey(), driverMappedButtonRightStickPress.getMappedCommandKey());

        String loadBControllerO = Preferences.getString(savePref.getSelected() + operatorMappedButtonB.getPreferenceKey(), operatorMappedButtonB.getMappedCommandKey());
        String loadXControllerO = Preferences.getString(savePref.getSelected() + operatorMappedButtonX.getPreferenceKey(), operatorMappedButtonX.getMappedCommandKey());
        String loadYControllerO = Preferences.getString(savePref.getSelected() + operatorMappedButtonY.getPreferenceKey(), operatorMappedButtonY.getMappedCommandKey());
        String loadAControllerO = Preferences.getString(savePref.getSelected() + operatorMappedButtonA.getPreferenceKey(), operatorMappedButtonA.getMappedCommandKey());
        String loadLeftTriggerControllerO = Preferences.getString(savePref.getSelected() + operatorMappedButtonLeftTrigger.getPreferenceKey(), operatorMappedButtonLeftTrigger.getMappedCommandKey());
        String loadRightTriggerControllerO = Preferences.getString(savePref.getSelected() + operatorMappedButtonRightTrigger.getPreferenceKey(), operatorMappedButtonRightTrigger.getMappedCommandKey());
        String loadLeftBumperControllerO = Preferences.getString(savePref.getSelected() + operatorMappedButtonLeftBumper.getPreferenceKey(), operatorMappedButtonLeftBumper.getMappedCommandKey());
        String loadRightBumperControllerO = Preferences.getString(savePref.getSelected() + operatorMappedButtonRightBumper.getPreferenceKey(), operatorMappedButtonRightBumper.getMappedCommandKey());
        String loadStartControllerO = Preferences.getString(savePref.getSelected() + operatorMappedButtonStart.getPreferenceKey(), operatorMappedButtonStart.getMappedCommandKey());
        String loadBackControllerO = Preferences.getString(savePref.getSelected() + operatorMappedButtonBack.getPreferenceKey(), operatorMappedButtonBack.getMappedCommandKey());
        String loadDpadUpControllerO = Preferences.getString(savePref.getSelected() + operatorMappedButtonDpadUp.getPreferenceKey(), operatorMappedButtonDpadUp.getMappedCommandKey());
        String loadDpadDownControllerO = Preferences.getString(savePref.getSelected() + operatorMappedButtonDpadDown.getPreferenceKey(), operatorMappedButtonDpadDown.getMappedCommandKey());
        String loadDpadLeftControllerO = Preferences.getString(savePref.getSelected() + operatorMappedButtonDpadLeft.getPreferenceKey(), operatorMappedButtonDpadLeft.getMappedCommandKey());
        String loadDpadRightControllerO = Preferences.getString(savePref.getSelected() + operatorMappedButtonDpadRight.getPreferenceKey(), operatorMappedButtonDpadRight.getMappedCommandKey());
        String loadLeftStickPressControllerO = Preferences.getString(savePref.getSelected() + operatorMappedButtonLeftStickPress.getPreferenceKey(), operatorMappedButtonLeftStickPress.getMappedCommandKey());
        String loadRightStickPressControllerO = Preferences.getString(savePref.getSelected() + operatorMappedButtonRightStickPress.getPreferenceKey(), operatorMappedButtonRightStickPress.getMappedCommandKey());
        
        driverMappedButtonX.setMapperCommandKey(loadXControllerD);
        driverMappedButtonA.setMapperCommandKey(loadAControllerD);
        driverMappedButtonY.setMapperCommandKey(loadYControllerD);
        driverMappedButtonB.setMapperCommandKey(loadBControllerD);
        driverMappedButtonLeftTrigger.setMapperCommandKey(loadLeftTriggerControllerD);
        driverMappedButtonRightTrigger.setMapperCommandKey(loadRightTriggerControllerD);
        driverMappedButtonLeftBumper.setMapperCommandKey(loadLeftBumperControllerD);
        driverMappedButtonRightBumper.setMapperCommandKey(loadRightBumperControllerD);
        driverMappedButtonStart.setMapperCommandKey(loadStartControllerD);
        driverMappedButtonBack.setMapperCommandKey(loadBackControllerD);
        driverMappedButtonDpadUp.setMapperCommandKey(loadDpadUpControllerD);
        driverMappedButtonDpadDown.setMapperCommandKey(loadDpadDownControllerD);
        driverMappedButtonDpadLeft.setMapperCommandKey(loadDpadLeftControllerD);
        driverMappedButtonDpadRight.setMapperCommandKey(loadDpadRightControllerD);
        driverMappedButtonLeftStickPress.setMapperCommandKey(loadLeftStickPressControllerD);
        driverMappedButtonRightStickPress.setMapperCommandKey(loadRightStickPressControllerD);

        operatorMappedButtonX.setMapperCommandKey(loadXControllerO);
        operatorMappedButtonA.setMapperCommandKey(loadAControllerO);
        operatorMappedButtonY.setMapperCommandKey(loadYControllerO);
        operatorMappedButtonB.setMapperCommandKey(loadBControllerO);
        operatorMappedButtonLeftTrigger.setMapperCommandKey(loadLeftTriggerControllerO);
        operatorMappedButtonRightTrigger.setMapperCommandKey(loadRightTriggerControllerO);
        operatorMappedButtonLeftBumper.setMapperCommandKey(loadLeftBumperControllerO);
        operatorMappedButtonRightBumper.setMapperCommandKey(loadRightBumperControllerO);
        operatorMappedButtonStart.setMapperCommandKey(loadStartControllerO);
        operatorMappedButtonBack.setMapperCommandKey(loadBackControllerO);
        operatorMappedButtonDpadUp.setMapperCommandKey(loadDpadUpControllerO);
        operatorMappedButtonDpadDown.setMapperCommandKey(loadDpadDownControllerO);
        operatorMappedButtonDpadLeft.setMapperCommandKey(loadDpadLeftControllerO);
        operatorMappedButtonDpadRight.setMapperCommandKey(loadDpadRightControllerO);
        operatorMappedButtonLeftStickPress.setMapperCommandKey(loadLeftStickPressControllerO);
        operatorMappedButtonRightStickPress.setMapperCommandKey(loadRightStickPressControllerO);
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
                // Drivetrain will execute this command periodically
                drivetrain.applyRequest(() -> drive.withVelocityX(-driver.getLeftY() * Constants.MaxSpeed) // Drive forward with
                                                                                                 // negative Y (forward)
                        .withVelocityY(-driver.getLeftX() * Constants.MaxSpeed) // Drive left with negative X (left)
                        .withRotationalRate(-driver.getRightX() * Constants.MaxAngularRate) // Drive counterclockwise with
                                                                                  // negative X (left)
                ));

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public void driverControls() {
        driverMappedButtonY = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), driver.y(), "Driver Button Y", "yButtonD");
        driverMappedButtonA = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), driver.a(), "Driver Button A", "aButtonD");
        driverMappedButtonX = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), driver.x(), "Driver Button X", "xButtonD");
        driverMappedButtonB = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), driver.b(), "Driver Button B", "bButtonD");
        driverMappedButtonLeftBumper = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), driver.leftBumper(), "Driver Bumper Left", "leftBumperD");
        driverMappedButtonRightBumper = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), driver.rightBumper(), "Driver Bumper Right", "rightBumperD");
        driverMappedButtonLeftTrigger = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), driver.leftTrigger(.8), "Driver Trigger Left", "leftTriggerD");
        driverMappedButtonRightTrigger = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), driver.rightTrigger(.8), "Driver Trigger Right", "rightTriggerD");
        driverMappedButtonStart = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), driver.start(), "Driver Button Start", "startButtonD");
        driverMappedButtonBack = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), driver.back(), "Driver Button Back", "backButtonD");
        driverMappedButtonLeftStickPress = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), driver.leftStick(), "Driver Button Left Stick", "leftStickD");
        driverMappedButtonRightStickPress = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), driver.rightStick(), "Driver Button Right Stick", "rightStickD");
        driverMappedButtonDpadUp = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), driver.povUp(), "Driver Dpad Up", "upDpadD");
        driverMappedButtonDpadDown = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), driver.povDown(), "Driver Dpad Down", "downDpadD");
        driverMappedButtonDpadLeft = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), driver.povLeft(), "Driver Dpad Left", "leftDpadD");
        driverMappedButtonDpadRight = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), driver.povRight(), "Driver Dpad Right", "rightDpadD");
    }

    public void operatorControls() {
        operatorMappedButtonA = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), operator.a(), "Operator Button A", "aButtonO");
        operatorMappedButtonX = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), operator.x(), "Operator Button X", "xButtonO");
        operatorMappedButtonY = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), operator.y(), "Operator Button Y", "yButtonO");
        operatorMappedButtonB = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), operator.b(), "Operator Button B", "bButtonO");
        operatorMappedButtonLeftBumper = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), operator.leftBumper(), "Operator Bumper Left", "leftBumperO");
        operatorMappedButtonRightBumper = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), operator.rightBumper(), "Operator Bumper Right", "rightBumperO");
        operatorMappedButtonLeftTrigger = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), operator.leftTrigger(.8), "Operator Trigger Left", "leftTriggerO");
        operatorMappedButtonRightTrigger = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), operator.rightTrigger(.8), "Operator Trigger Right", "rightTriggerO");
        operatorMappedButtonStart = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), operator.start(), "Operator Button Start", "startButtonO");
        operatorMappedButtonBack = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), operator.back(), "Operator Button Back", "backButtonO");
        operatorMappedButtonLeftStickPress = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), operator.leftStick(), "Operator Button Left Stick", "leftStickO");
        operatorMappedButtonRightStickPress = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), operator.rightStick(), "Operator Button Right Stick", "rightStickO");
        operatorMappedButtonDpadUp = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), operator.povUp(), "Operator Dpad Up", "upDpadO");
        operatorMappedButtonDpadDown = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), operator.povDown(), "Operator Dpad Down", "downDpadO");
        operatorMappedButtonDpadLeft = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), operator.povLeft(), "Operator Dpad Left", "leftDpadO");
        operatorMappedButtonDpadRight = new ControlMapper(new CommandMap().getMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver), operator.povRight(), "Operator Dpad Right", "rightDpadO");
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}
