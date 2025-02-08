// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import frc.robot.commands.CommandMap;

import frc.robot.subsystems.Mechanisms.Climber.Climber;
import frc.robot.subsystems.Mechanisms.Elevator.Elevator1;
import frc.robot.subsystems.Mechanisms.Elevator.Elevator2;
import frc.robot.subsystems.Mechanisms.Funnel.FunnelPivot;
import frc.robot.subsystems.Mechanisms.Intake.IntakeFlywheels;
import frc.robot.subsystems.SensorSubsystems.CANdle_LED;
import frc.robot.subsystems.SensorSubsystems.IntakeBeambreak;
import frc.robot.subsystems.Swerve.CommandSwerveDrivetrain;
import frc.robot.generated.TunerConstants;

public class RobotContainer {
    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(Constants.MaxSpeed * 0.1).withRotationalDeadband(Constants.MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

    private final Telemetry logger = new Telemetry(Constants.MaxSpeed);

    private final CommandXboxController driver = new CommandXboxController(0);
    private final CommandXboxController operator = new CommandXboxController(1);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public final Elevator1 m_Elevator1 = new Elevator1(true);

    public final Elevator2 m_Elevator2 = new Elevator2(true);

    public final Climber m_Climber = new Climber(false);

    public final IntakeFlywheels m_IntakeFlywheels = new IntakeFlywheels(true);

    public final IntakeBeambreak m_intakeBeamBreak = new IntakeBeambreak();

    public final FunnelPivot m_FunnelPivot = new FunnelPivot(true);

    public final CANdle_LED m_leds = new CANdle_LED();

    private final CommandMap commandMap = new CommandMap(drivetrain, m_IntakeFlywheels, m_intakeBeamBreak, m_leds, driver, m_Elevator1, m_Elevator2, m_FunnelPivot,m_Climber);
    private ControllerMap driverMap;
    private ControllerMap operatorMap;

    private final SendableChooser<Command> autoChooser;

    public RobotContainer() {
        NamedCommands.registerCommands(commandMap.getMap());
        autoChooser = AutoBuilder.buildAutoChooser("Do Nothing");
        SmartDashboard.putData("Auto Mode", autoChooser);
        configureBindings();
        driverControls();
        operatorControls();
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
        driverMap = new ControllerMap(commandMap, driver, "Driver");
    }

    public void operatorControls() {
        operatorMap = new ControllerMap(commandMap, operator, "Operator");
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }

    public void updateControllerMaps() {
        driverMap.updateControllerMaps();
        operatorMap.updateControllerMaps();
    }

    public void savePreference(String savePref) {
        driverMap.savePreference(savePref);
        operatorMap.savePreference(savePref);
    }

    public void loadPreference(String savePref) {
        driverMap.loadPreference(savePref);
        operatorMap.loadPreference(savePref);
    }
}
