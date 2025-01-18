// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.util.HashMap;
import java.util.Map;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.*;
import frc.robot.Constants.ScoringStageVal;
import frc.robot.subsystems.Elevator1;
import frc.robot.subsystems.Elevator2;
import frc.robot.subsystems.FunnelPivot;
import frc.robot.subsystems.IntakeBeambreak;
import frc.robot.subsystems.IntakeFlywheels;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandXboxController driver = new CommandXboxController(0);
    private final CommandXboxController operator = new CommandXboxController(1);


    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public final Elevator1 m_Elevator1 = new Elevator1(false);

    public final Elevator2 m_Elevator2 = new Elevator2(false);

    public final Climber m_Climber = new Climber(false);

    public final IntakeFlywheels m_IntakeFlywheels = new IntakeFlywheels(false);

    public final IntakeBeambreak m_intakeBeamBreak = new IntakeBeambreak();

    public final FunnelPivot m_FunnelPivot = new FunnelPivot(false);

    private final SendableChooser<Command> autoChooser;

       Map<String, Command> autonomousCommands = new HashMap<String, Command>() {
        {
            
            // put("Enter Command Name", new Command(m_));

             put("Enter Command Name", new SequentialCommandGroup(

            ));
    
            
            put("Reset All", new ParallelCommandGroup(
            
            ));
    
        }
        
    };
    public RobotContainer() {
       
        configureBindings();
        driverControls();
        operatorControls();
        autoChooser = AutoBuilder.buildAutoChooser("Do Nothing");
        SmartDashboard.putData("Auto Mode", autoChooser);
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-driver.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-driver.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-driver.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        driver.a().whileTrue(drivetrain.applyRequest(() -> brake));
        driver.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-driver.getLeftY(), -driver.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        driver.back().and(driver.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        driver.back().and(driver.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));   

        driver.start().and(driver.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        driver.start().and(driver.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        drivetrain.registerTelemetry(logger::telemeterize);

        // reset the field-centric heading on left bumper press
        // driver.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

    }

        public void driverControls() {

            // driver.x().onTrue(new CommandElevatorToPos(Constants.Elevator1Constants.positionUp));
            // driver.y().onTrue(new CommandElevatorToPos(Constants.Elevator1Constants.positionDown));

            driver.rightBumper().onTrue(new CommandElevatorToStage());

            // driver.rightBumper().onTrue(new CommandIntakeCollect(m_IntakeFlywheels, m_intakeBeamBreak, 5));

            // driver.leftBumper().onTrue(new CommandIntakeOut(m_IntakeFlywheels, m_intakeBeamBreak, 5));



          
        }

        public void operatorControls(){


            operator.pov(0).onTrue(new CommandChangeScoreStage(ScoringStageVal.INTAKEREADY));

            operator.pov(90).onTrue(new CommandChangeScoreStage(ScoringStageVal.L2));

            operator.pov(180).onTrue(new CommandChangeScoreStage(ScoringStageVal.L3));

            operator.pov(270).onTrue(new CommandChangeScoreStage(ScoringStageVal.L4));

            // operator.rightStick().onTrue(new CommandChangeScoreStage(ScoringStageVal.CLIMBING));


            operator.rightBumper().onTrue(new CommandClimb(Constants.ClimberConstants.positionUp));

            operator.leftBumper().onTrue(new CommandClimb(Constants.ClimberConstants.positionDown));


            operator.rightTrigger().onTrue(new CommandFunnelPivot(Constants.FunnelPivotConstants.posUp));

            operator.leftTrigger().onTrue(new CommandFunnelPivot(Constants.FunnelPivotConstants.posDown));

            operator.y().whileTrue(new CommandToPos(drivetrain, new Pose2d(1.4, 6.8, new Rotation2d(2.23))));




        }




    public Command getAutonomousCommand() {
        // return Commands.print("No autonomous command configured");
        return autoChooser.getSelected(); 
    }

}
