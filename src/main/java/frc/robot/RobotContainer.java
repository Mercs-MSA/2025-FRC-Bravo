// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.util.Map;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.Constants.ScoringStageVal;
import frc.robot.subsystems.Elevator1;
import frc.robot.subsystems.Elevator2;
import frc.robot.subsystems.FunnelPivot;
import frc.robot.subsystems.IntakeBeambreak;
import frc.robot.subsystems.IntakeFlywheels;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CANdle_LED;
import frc.robot.commands.StaticCustomCommands;
import edu.wpi.first.wpilibj.Preferences;

public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second
                                                                                      // max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

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

    private Map<String, Command> commandMap = Map.ofEntries(
            Map.entry("Command 111", Commands.print("Command one was selected!")),
            Map.entry("PathWithDriveToPos",
                Commands.sequence(StaticCustomCommands.setDriveToPos("ReefTest"),
                StaticCustomCommands.toPos(drivetrain))),
            Map.entry("Intake Collect",
                StaticCustomCommands.intakeCollect(m_IntakeFlywheels, m_intakeBeamBreak, MaxAngularRate)),
            Map.entry("Score", StaticCustomCommands.intakeOut(m_IntakeFlywheels, m_intakeBeamBreak, MaxAngularRate)),
            Map.entry("L1",
                Commands.sequence(StaticCustomCommands.changeScoreStage(ScoringStageVal.L1),
                StaticCustomCommands.elevatorToStage())),
            Map.entry("L2",
                Commands.sequence(StaticCustomCommands.changeScoreStage(ScoringStageVal.L2),
                StaticCustomCommands.elevatorToStage())),
            Map.entry("L3",
                Commands.sequence(StaticCustomCommands.changeScoreStage(ScoringStageVal.L3),
                StaticCustomCommands.elevatorToStage())),
            Map.entry("L4",
                Commands.sequence(StaticCustomCommands.changeScoreStage(ScoringStageVal.L4),
                StaticCustomCommands.elevatorToStage())),
            Map.entry("ELEVIntakePos",
                Commands.sequence(StaticCustomCommands.changeScoreStage(ScoringStageVal.INTAKEREADY),
                StaticCustomCommands.elevatorToStage())),
            Map.entry("MoveFunnel",
                Commands.sequence(StaticCustomCommands.changeScoreStage(ScoringStageVal.INTAKEREADY),
                StaticCustomCommands.funnelPivot(Constants.FunnelPivotConstants.posUp))),
            Map.entry("Brake", drivetrain.applyRequest(() -> brake)), // WHILETRUE
            Map.entry("Move Turn",
                drivetrain.applyRequest(
                () -> point.withModuleDirection(new Rotation2d(-driver.getLeftY(), -driver.getLeftX())))), // WHILETRUE
            Map.entry("Move Forward", drivetrain.sysIdDynamic(Direction.kForward)), // WHILETRUE
            Map.entry("Move Backward", drivetrain.sysIdDynamic(Direction.kReverse)), // WHILETRUE
            Map.entry("Intake Reverse", StaticCustomCommands.intakeOut(m_IntakeFlywheels, m_intakeBeamBreak, 5)),
            Map.entry("Set Field Centric?", drivetrain.runOnce(() -> drivetrain.seedFieldCentric())),
            Map.entry("Drive To Source", StaticCustomCommands.setDriveToPos("Source")
                .andThen(StaticCustomCommands.changeScoreStage(ScoringStageVal.INTAKEREADY)).andThen(
                Commands.parallel(StaticCustomCommands.toPos(drivetrain),
                StaticCustomCommands.elevatorToStage(),
                StaticCustomCommands.intakeCollect(m_IntakeFlywheels, m_intakeBeamBreak, 1)))), // WHILETRUE
            Map.entry("Score L1", StaticCustomCommands.changeScoreStage(ScoringStageVal.L1)),
            Map.entry("Score L2", StaticCustomCommands.changeScoreStage(ScoringStageVal.L2)),
            Map.entry("Score L3", StaticCustomCommands.changeScoreStage(ScoringStageVal.L3)),
            Map.entry("Score L4", StaticCustomCommands.changeScoreStage(ScoringStageVal.L4)),
            Map.entry("Score Climb", StaticCustomCommands.changeScoreStage(ScoringStageVal.CLIMBING)),
            Map.entry("Score Intake",
            Commands.sequence(StaticCustomCommands.changeScoreStage(ScoringStageVal.INTAKEREADY),
                StaticCustomCommands.elevatorToStage())),
            Map.entry("Toggle Climb", StaticCustomCommands.climbToggle()),
            Map.entry("Toggle Funnel", StaticCustomCommands.funnelToggle()),
            Map.entry("Intake Collect", StaticCustomCommands.intakeCollect(m_IntakeFlywheels, m_intakeBeamBreak, 8)),
            Map.entry("Limelight Action?",
                StaticCustomCommands.seedToMegaTag(drivetrain, Constants.VisionConstants.limelightFrontName)),
            Map.entry("Drive Position 1", StaticCustomCommands.loadDriveToPos(0).andThen(Commands.parallel(
                StaticCustomCommands.toPos(drivetrain), StaticCustomCommands.elevatorToStage(),
                StaticCustomCommands.candleSetAnimation(m_leds, CANdle_LED.AnimationTypes.Strobe)))), // WHILETRUE
            Map.entry("Drive Position 2", StaticCustomCommands.loadDriveToPos(1).andThen(Commands.parallel(
                StaticCustomCommands.toPos(drivetrain), StaticCustomCommands.elevatorToStage(),
                StaticCustomCommands.candleSetAnimation(m_leds, CANdle_LED.AnimationTypes.Strobe)))), // WHILETRUE
            Map.entry("Candle Larson", StaticCustomCommands.candleSetAnimation(m_leds, CANdle_LED.AnimationTypes.Larson)),
            Map.entry("Candle Twinkle", StaticCustomCommands.candleSetAnimation(m_leds, CANdle_LED.AnimationTypes.Twinkle)));

    public RobotContainer() {
        // TODO if for some reason the pKeys are acting weird, check if you need to differenciate between driver and operator
        // Because that might cause some issues with making it look like the keys duplicated or something
        driverMappedButtonY = new ControlMapper(commandMap, driver.y(), "Driver Button Y", "yButtonD");
        driverMappedButtonA = new ControlMapper(commandMap, driver.a(), "Driver Button A", "aButtonD");
        driverMappedButtonX = new ControlMapper(commandMap, driver.x(), "Driver Button X", "xButtonD");
        driverMappedButtonB = new ControlMapper(commandMap, driver.b(), "Driver Button B", "bButtonD");
        driverMappedButtonLeftBumper = new ControlMapper(commandMap, driver.leftBumper(), "Driver Bumper Left", "leftBumperD");
        driverMappedButtonRightBumper = new ControlMapper(commandMap, driver.rightBumper(), "Driver Bumper Right", "rightBumperD");
        driverMappedButtonLeftTrigger = new ControlMapper(commandMap, driver.leftTrigger(.8), "Driver Trigger Left", "leftTriggerD");
        driverMappedButtonRightTrigger = new ControlMapper(commandMap, driver.rightTrigger(.8), "Driver Trigger Right", "rightTriggerD");
        driverMappedButtonStart = new ControlMapper(commandMap, driver.start(), "Driver Button Start", "startButtonD");
        driverMappedButtonBack = new ControlMapper(commandMap, driver.back(), "Driver Button Back", "backButtonD");
        driverMappedButtonLeftStickPress = new ControlMapper(commandMap, driver.leftStick(), "Driver Button Left Stick", "leftStickD");
        driverMappedButtonRightStickPress = new ControlMapper(commandMap, driver.rightStick(), "Driver Button Right Stick", "rightStickD");
        driverMappedButtonDpadUp = new ControlMapper(commandMap, driver.povUp(), "Driver Dpad Up", "upDpadD");
        driverMappedButtonDpadDown = new ControlMapper(commandMap, driver.povDown(), "Driver Dpad Down", "downDpadD");
        driverMappedButtonDpadLeft = new ControlMapper(commandMap, driver.povLeft(), "Driver Dpad Left", "leftDpadD");
        driverMappedButtonDpadRight = new ControlMapper(commandMap, driver.povRight(), "Driver Dpad Right", "rightDpadD");

        operatorMappedButtonA = new ControlMapper(commandMap, operator.a(), "Operator Button A", "aButtonO");
        operatorMappedButtonX = new ControlMapper(commandMap, operator.x(), "Operator Button X", "xButtonO");
        operatorMappedButtonY = new ControlMapper(commandMap, operator.y(), "Operator Button Y", "yButtonO");
        operatorMappedButtonB = new ControlMapper(commandMap, operator.b(), "Operator Button B", "bButtonO");
        operatorMappedButtonLeftBumper = new ControlMapper(commandMap, operator.leftBumper(), "Operator Bumper Left", "leftBumperO");
        operatorMappedButtonRightBumper = new ControlMapper(commandMap, operator.rightBumper(), "Operator Bumper Right", "rightBumperO");
        operatorMappedButtonLeftTrigger = new ControlMapper(commandMap, operator.leftTrigger(.8), "Operator Trigger Left", "leftTriggerO");
        operatorMappedButtonRightTrigger = new ControlMapper(commandMap, operator.rightTrigger(.8), "Operator Trigger Right", "rightTriggerO");
        operatorMappedButtonStart = new ControlMapper(commandMap, operator.start(), "Operator Button Start", "startButtonO");
        operatorMappedButtonBack = new ControlMapper(commandMap, operator.back(), "Operator Button Back", "backButtonO");
        operatorMappedButtonLeftStickPress = new ControlMapper(commandMap, operator.leftStick(), "Operator Button Left Stick", "leftStickO");
        operatorMappedButtonRightStickPress = new ControlMapper(commandMap, operator.rightStick(), "Operator Button Right Stick", "rightStickO");
        operatorMappedButtonDpadUp = new ControlMapper(commandMap, operator.povUp(), "Operator Dpad Up", "upDpadO");
        operatorMappedButtonDpadDown = new ControlMapper(commandMap, operator.povDown(), "Operator Dpad Down", "downDpadO");
        operatorMappedButtonDpadLeft = new ControlMapper(commandMap, operator.povLeft(), "Operator Dpad Left", "leftDpadO");
        operatorMappedButtonDpadRight = new ControlMapper(commandMap, operator.povRight(), "Operator Dpad Right", "rightDpadO");

        NamedCommands.registerCommands(commandMap);

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
        //drivetrain.setDefaultCommand(
                // Drivetrain will execute this command periodically
                //drivetrain.applyRequest(() -> drive.withVelocityX(-driver.getLeftY() * MaxSpeed) // Drive forward with
                                                                                                 // negative Y (forward)
                        //.withVelocityY(-driver.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                        //.withRotationalRate(-driver.getRightX() * MaxAngularRate) // Drive counterclockwise with
                                                                                  // negative X (left)
                //));

        //driver.a().whileTrue(drivetrain.applyRequest(() -> brake));
        //driver.b().whileTrue(drivetrain
                //.applyRequest(() -> point.withModuleDirection(new Rotation2d(-driver.getLeftY(), -driver.getLeftX()))));
        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        // driver.back().and(driver.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        // driver.back().and(driver.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        // driver.start().and(driver.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        // driver.start().and(driver.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));
        // drivetrain.registerTelemetry(logger::telemeterize);
        // reset the field-centric heading on left bumper press
        // driver.leftBumper().onTrue(drivetrain.runOnce(() ->
        // drivetrain.seedFieldCentric()));
    }

    public void driverControls() {

        // driver.x().onTrue(new
        // CommandElevatorToPos(Constants.Elevator1Constants.positionUp));
        // driver.y().onTrue(new
        // CommandElevatorToPos(Constants.Elevator1Constants.positionDown));

        // DONE
        // driver.leftBumper().onTrue(new CommandIntakeOut(m_IntakeFlywheels, m_intakeBeamBreak, 5));

        // DONE
        // driver.x().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        // DONE
        // driver.back().onTrue(new CommandCandleSetAnimation(m_leds, CANdle_LED.AnimationTypes.Larson));
        // driver.start().onTrue(new CommandCandleSetAnimation(m_leds, CANdle_LED.AnimationTypes.Twinkle));
        // DONE
        // driver.a().whileTrue(
                // new CommandSetDriveToPos("Source").andThen(
                        // new CommandChangeScoreStage(ScoringStageVal.INTAKEREADY)).andThen(
                                // new ParallelCommandGroup(
                                        // new CommandToPos(drivetrain),
                                        // new CommandElevatorToStage(),
                                        // new CommandIntakeCollect(m_IntakeFlywheels, m_intakeBeamBreak, 1))));

        // DONE
        // driver.leftTrigger(0.8)
                // .whileTrue(new CommandLoadDriveToPos(() -> Constants.DriveToPosRuntime.autoTargets.get(0))
                        // .andThen(new ParallelCommandGroup(
                                // new CommandToPos(drivetrain),
                                // new CommandElevatorToStage(),
                                // new CommandCandleSetAnimation(m_leds, CANdle_LED.AnimationTypes.Strobe))));// keep
        // DONE
        // driver.rightTrigger(0.8)
                // .whileTrue(new CommandLoadDriveToPos(() -> Constants.DriveToPosRuntime.autoTargets.get(1))
                        // .andThen(new ParallelCommandGroup(
                                // new CommandToPos(drivetrain),
                                // new CommandElevatorToStage(),
                                // new CommandCandleSetAnimation(m_leds, CANdle_LED.AnimationTypes.Strobe))));// keep
        // DON'T NEED?
        // driver.leftTrigger().whileTrue(new
        // CommandSetDriveToPos("ReefTest").andThen(new ParallelCommandGroup (
        // new CommandToPos(drivetrain),
        // new CommandElevatorToStage()
        // )));//keep

    }

    public void operatorControls() {

        // DONE
        // operator.pov(180).onTrue(new CommandChangeScoreStage(ScoringStageVal.L1));
        // DONE
        // operator.pov(270).onTrue(new CommandChangeScoreStage(ScoringStageVal.L2));
        // DONE
        // operator.pov(0).onTrue(new CommandChangeScoreStage(ScoringStageVal.L3));
        // DONE
        // operator.pov(90).onTrue(new CommandChangeScoreStage(ScoringStageVal.L4));

        // DONE
        // operator.a().onTrue(new CommandChangeScoreStage(ScoringStageVal.CLIMBING));
        // DONE
        // operator.b().onTrue(new SequentialCommandGroup(
                // new CommandChangeScoreStage(ScoringStageVal.INTAKEREADY),
                // new CommandElevatorToStage()));

        // DONE
        // operator.leftStick().onTrue(new CommandClimbToggle());
        // DONE
        // operator.rightStick().onTrue(new CommandFunnelToggle());
        // DONE
        // operator.leftBumper().onTrue(new CommandIntakeCollect(m_IntakeFlywheels, m_intakeBeamBreak, 8));

        // DONE
        // driver.y().onTrue(new SeedToMegaTag(drivetrain, Constants.VisionConstants.limelightFrontName));

        // SmartDashboard.putData("SeedToMegaTag1_Front", new SeedToMegaTag(drivetrain,
        // Constants.VisionConstants.limelightFrontName));
        // SmartDashboard.putData("SeedToMegaTag1_Back", new SeedToMegaTag(drivetrain,
        // Constants.VisionConstants.limelightBackName));

    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}
