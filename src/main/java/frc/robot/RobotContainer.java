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

    private ButtonMap driverA;
    private ButtonMap driverB;
    private ButtonMap driverX;
    private ButtonMap driverY;
    private ButtonMap driverRightBumper;
    private ButtonMap driverLeftBumper;
    private ButtonMap driverStart;
    private ButtonMap driverBack;
    private ButtonMap driverLeftTrigger;
    private ButtonMap driverRightTrigger;
    private ButtonMap driverDpadUp;
    private ButtonMap driverDpadDown;
    private ButtonMap driverDpadLeft;
    private ButtonMap driverDpadRight;
    private ButtonMap driverLeftStickPress;
    private ButtonMap driverRightStickPress;

    private ButtonMap operatorA;
    private ButtonMap operatorB;
    private ButtonMap operatorX;
    private ButtonMap operatorY;
    private ButtonMap operatorRightBumper;
    private ButtonMap operatorLeftBumper;
    private ButtonMap operatorStart;
    private ButtonMap operatorBack;
    private ButtonMap operatorLeftTrigger;
    private ButtonMap operatorRightTrigger;
    private ButtonMap operatorDpadUp;
    private ButtonMap operatorDpadDown;
    private ButtonMap operatorDpadLeft;
    private ButtonMap operatorDpadRight;
    private ButtonMap operatorLeftStickPress;
    private ButtonMap operatorRightStickPress;

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public final Elevator1 m_Elevator1 = new Elevator1(false);

    public final Elevator2 m_Elevator2 = new Elevator2(false);

    public final Climber m_Climber = new Climber(false);

    public final IntakeFlywheels m_IntakeFlywheels = new IntakeFlywheels(true);

    public final IntakeBeambreak m_intakeBeamBreak = new IntakeBeambreak();

    public final FunnelPivot m_FunnelPivot = new FunnelPivot(true);

    public final CANdle_LED m_leds = new CANdle_LED();

    private final CommandMap commandMap = new CommandMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver);

    private final SendableChooser<Command> autoChooser;

    public RobotContainer() {
        NamedCommands.registerCommands(commandMap.getMap());
        autoChooser = AutoBuilder.buildAutoChooser("Do Nothing");
        SmartDashboard.putData("Auto Mode", autoChooser);
        configureBindings();
        driverControls();
        operatorControls();
    }

    public void savePreference(SendableChooser<String> savePref) {
        Preferences.setString(savePref.getSelected() + driverY.getPreferenceKey(), driverY.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverX.getPreferenceKey(), driverX.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverA.getPreferenceKey(), driverA.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverB.getPreferenceKey(), driverB.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverLeftTrigger.getPreferenceKey(), driverLeftTrigger.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverRightTrigger.getPreferenceKey(), driverRightTrigger.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverLeftBumper.getPreferenceKey(), driverLeftBumper.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverRightBumper.getPreferenceKey(), driverRightBumper.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverStart.getPreferenceKey(), driverStart.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverBack.getPreferenceKey(), driverBack.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverDpadUp.getPreferenceKey(), driverDpadUp.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverDpadDown.getPreferenceKey(), driverDpadDown.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverDpadLeft.getPreferenceKey(), driverDpadLeft.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverDpadRight.getPreferenceKey(), driverDpadRight.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverLeftStickPress.getPreferenceKey(), driverLeftStickPress.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + driverRightStickPress.getPreferenceKey(), driverRightStickPress.getMappedCommandKey());

        Preferences.setString(savePref.getSelected() + operatorY.getPreferenceKey(), operatorY.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorX.getPreferenceKey(), operatorX.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorA.getPreferenceKey(), operatorA.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorB.getPreferenceKey(), operatorB.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorLeftTrigger.getPreferenceKey(), operatorLeftTrigger.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorRightTrigger.getPreferenceKey(), operatorRightTrigger.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorLeftBumper.getPreferenceKey(), operatorLeftBumper.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorRightBumper.getPreferenceKey(), operatorRightBumper.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorStart.getPreferenceKey(), operatorStart.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorBack.getPreferenceKey(), operatorBack.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorDpadUp.getPreferenceKey(), operatorDpadUp.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorDpadDown.getPreferenceKey(), operatorDpadDown.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorDpadLeft.getPreferenceKey(), operatorDpadLeft.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorDpadRight.getPreferenceKey(), operatorDpadRight.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorLeftStickPress.getPreferenceKey(), operatorLeftStickPress.getMappedCommandKey());
        Preferences.setString(savePref.getSelected() + operatorRightStickPress.getPreferenceKey(), operatorRightStickPress.getMappedCommandKey());
    }

    public void loadPreference(SendableChooser<String> savePref) {
        String loadBControllerD = Preferences.getString(savePref.getSelected() + driverB.getPreferenceKey(), driverB.getMappedCommandKey());
        String loadXControllerD = Preferences.getString(savePref.getSelected() + driverX.getPreferenceKey(), driverX.getMappedCommandKey());
        String loadYControllerD = Preferences.getString(savePref.getSelected() + driverY.getPreferenceKey(), driverY.getMappedCommandKey());
        String loadAControllerD = Preferences.getString(savePref.getSelected() + driverA.getPreferenceKey(), driverA.getMappedCommandKey());
        String loadLeftTriggerControllerD = Preferences.getString(savePref.getSelected() + driverLeftTrigger.getPreferenceKey(), driverLeftTrigger.getMappedCommandKey());
        String loadRightTriggerControllerD = Preferences.getString(savePref.getSelected() + driverRightTrigger.getPreferenceKey(), driverRightTrigger.getMappedCommandKey());
        String loadLeftBumperControllerD = Preferences.getString(savePref.getSelected() + driverLeftBumper.getPreferenceKey(), driverLeftBumper.getMappedCommandKey());
        String loadRightBumperControllerD = Preferences.getString(savePref.getSelected() + driverRightBumper.getPreferenceKey(), driverRightBumper.getMappedCommandKey());
        String loadStartControllerD = Preferences.getString(savePref.getSelected() + driverStart.getPreferenceKey(), driverStart.getMappedCommandKey());
        String loadBackControllerD = Preferences.getString(savePref.getSelected() + driverBack.getPreferenceKey(), driverBack.getMappedCommandKey());
        String loadDpadUpControllerD = Preferences.getString(savePref.getSelected() + driverDpadUp.getPreferenceKey(), driverDpadUp.getMappedCommandKey());
        String loadDpadDownControllerD = Preferences.getString(savePref.getSelected() + driverDpadDown.getPreferenceKey(), driverDpadDown.getMappedCommandKey());
        String loadDpadLeftControllerD = Preferences.getString(savePref.getSelected() + driverDpadLeft.getPreferenceKey(), driverDpadLeft.getMappedCommandKey());
        String loadDpadRightControllerD = Preferences.getString(savePref.getSelected() + driverDpadRight.getPreferenceKey(), driverDpadRight.getMappedCommandKey());
        String loadLeftStickPressControllerD = Preferences.getString(savePref.getSelected() + driverLeftStickPress.getPreferenceKey(), driverLeftStickPress.getMappedCommandKey());
        String loadRightStickPressControllerD = Preferences.getString(savePref.getSelected() + driverRightStickPress.getPreferenceKey(), driverRightStickPress.getMappedCommandKey());

        String loadBControllerO = Preferences.getString(savePref.getSelected() + operatorB.getPreferenceKey(), operatorB.getMappedCommandKey());
        String loadXControllerO = Preferences.getString(savePref.getSelected() + operatorX.getPreferenceKey(), operatorX.getMappedCommandKey());
        String loadYControllerO = Preferences.getString(savePref.getSelected() + operatorY.getPreferenceKey(), operatorY.getMappedCommandKey());
        String loadAControllerO = Preferences.getString(savePref.getSelected() + operatorA.getPreferenceKey(), operatorA.getMappedCommandKey());
        String loadLeftTriggerControllerO = Preferences.getString(savePref.getSelected() + operatorLeftTrigger.getPreferenceKey(), operatorLeftTrigger.getMappedCommandKey());
        String loadRightTriggerControllerO = Preferences.getString(savePref.getSelected() + operatorRightTrigger.getPreferenceKey(), operatorRightTrigger.getMappedCommandKey());
        String loadLeftBumperControllerO = Preferences.getString(savePref.getSelected() + operatorLeftBumper.getPreferenceKey(), operatorLeftBumper.getMappedCommandKey());
        String loadRightBumperControllerO = Preferences.getString(savePref.getSelected() + operatorRightBumper.getPreferenceKey(), operatorRightBumper.getMappedCommandKey());
        String loadStartControllerO = Preferences.getString(savePref.getSelected() + operatorStart.getPreferenceKey(), operatorStart.getMappedCommandKey());
        String loadBackControllerO = Preferences.getString(savePref.getSelected() + operatorBack.getPreferenceKey(), operatorBack.getMappedCommandKey());
        String loadDpadUpControllerO = Preferences.getString(savePref.getSelected() + operatorDpadUp.getPreferenceKey(), operatorDpadUp.getMappedCommandKey());
        String loadDpadDownControllerO = Preferences.getString(savePref.getSelected() + operatorDpadDown.getPreferenceKey(), operatorDpadDown.getMappedCommandKey());
        String loadDpadLeftControllerO = Preferences.getString(savePref.getSelected() + operatorDpadLeft.getPreferenceKey(), operatorDpadLeft.getMappedCommandKey());
        String loadDpadRightControllerO = Preferences.getString(savePref.getSelected() + operatorDpadRight.getPreferenceKey(), operatorDpadRight.getMappedCommandKey());
        String loadLeftStickPressControllerO = Preferences.getString(savePref.getSelected() + operatorLeftStickPress.getPreferenceKey(), operatorLeftStickPress.getMappedCommandKey());
        String loadRightStickPressControllerO = Preferences.getString(savePref.getSelected() + operatorRightStickPress.getPreferenceKey(), operatorRightStickPress.getMappedCommandKey());
        
        driverX.setMapperCommandKey(loadXControllerD);
        driverA.setMapperCommandKey(loadAControllerD);
        driverY.setMapperCommandKey(loadYControllerD);
        driverB.setMapperCommandKey(loadBControllerD);
        driverLeftTrigger.setMapperCommandKey(loadLeftTriggerControllerD);
        driverRightTrigger.setMapperCommandKey(loadRightTriggerControllerD);
        driverLeftBumper.setMapperCommandKey(loadLeftBumperControllerD);
        driverRightBumper.setMapperCommandKey(loadRightBumperControllerD);
        driverStart.setMapperCommandKey(loadStartControllerD);
        driverBack.setMapperCommandKey(loadBackControllerD);
        driverDpadUp.setMapperCommandKey(loadDpadUpControllerD);
        driverDpadDown.setMapperCommandKey(loadDpadDownControllerD);
        driverDpadLeft.setMapperCommandKey(loadDpadLeftControllerD);
        driverDpadRight.setMapperCommandKey(loadDpadRightControllerD);
        driverLeftStickPress.setMapperCommandKey(loadLeftStickPressControllerD);
        driverRightStickPress.setMapperCommandKey(loadRightStickPressControllerD);

        operatorX.setMapperCommandKey(loadXControllerO);
        operatorA.setMapperCommandKey(loadAControllerO);
        operatorY.setMapperCommandKey(loadYControllerO);
        operatorB.setMapperCommandKey(loadBControllerO);
        operatorLeftTrigger.setMapperCommandKey(loadLeftTriggerControllerO);
        operatorRightTrigger.setMapperCommandKey(loadRightTriggerControllerO);
        operatorLeftBumper.setMapperCommandKey(loadLeftBumperControllerO);
        operatorRightBumper.setMapperCommandKey(loadRightBumperControllerO);
        operatorStart.setMapperCommandKey(loadStartControllerO);
        operatorBack.setMapperCommandKey(loadBackControllerO);
        operatorDpadUp.setMapperCommandKey(loadDpadUpControllerO);
        operatorDpadDown.setMapperCommandKey(loadDpadDownControllerO);
        operatorDpadLeft.setMapperCommandKey(loadDpadLeftControllerO);
        operatorDpadRight.setMapperCommandKey(loadDpadRightControllerO);
        operatorLeftStickPress.setMapperCommandKey(loadLeftStickPressControllerO);
        operatorRightStickPress.setMapperCommandKey(loadRightStickPressControllerO);
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
        ControllerMap driverMap = new ControllerMap(commandMap, driver, "Driver");

        driverA = driverMap.a();
        driverB = driverMap.b();
        driverX = driverMap.x();
        driverY = driverMap.y();
        driverLeftBumper = driverMap.leftBumper();
        driverRightBumper = driverMap.rightBumper();
        driverLeftTrigger = driverMap.leftTrigger();
        driverRightTrigger = driverMap.rightTrigger();
        driverStart = driverMap.start();
        driverBack = driverMap.back();
        driverLeftStickPress = driverMap.leftStick();
        driverRightStickPress = driverMap.rightStick();
        driverDpadUp = driverMap.povUp();
        driverDpadDown = driverMap.povDown();
        driverDpadLeft = driverMap.povLeft();
        driverDpadRight = driverMap.povRight();
    }

    public void operatorControls() {
        ControllerMap operatorMap = new ControllerMap(commandMap, operator, "Operator");
        
        operatorA = operatorMap.a();
        operatorB = operatorMap.b();
        operatorX = operatorMap.x();
        operatorY = operatorMap.y();
        operatorLeftBumper = operatorMap.leftBumper();
        operatorRightBumper = operatorMap.rightBumper();
        operatorLeftTrigger = operatorMap.leftTrigger();
        operatorRightTrigger = operatorMap.rightTrigger();
        operatorStart = operatorMap.start();
        operatorBack = operatorMap.back();
        operatorLeftStickPress = operatorMap.leftStick();
        operatorRightStickPress = operatorMap.rightStick();
        operatorDpadUp = operatorMap.povUp();
        operatorDpadDown = operatorMap.povDown();
        operatorDpadLeft = operatorMap.povLeft();
        operatorDpadRight = operatorMap.povRight();
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}
