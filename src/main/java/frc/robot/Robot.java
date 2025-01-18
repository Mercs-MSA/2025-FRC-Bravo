// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.*;
import frc.robot.commands.CommandClimb;
import frc.robot.commands.CommandElevatorToPos;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  boolean moveClimberDown;
  boolean spinIntake;
  boolean moveClimberUp;
  private SendableChooser<XboxController.Button> controlChoiceClimberDown = new SendableChooser<>();
  private SendableChooser<XboxController.Button> controlChoiceIntake = new SendableChooser<>();
  private SendableChooser<XboxController.Button> controlChoiceClimberUp = new SendableChooser<>();

  private final RobotContainer m_robotContainer;

  public final XboxController testJoystick = new XboxController(2);

  public Robot() {
    m_robotContainer = new RobotContainer();

    SmartDashboard.putData("Selectable Action Test", new Sendable() {
      @Override
      public void initSendable(SendableBuilder builder) {
        builder.addBooleanProperty("Climber Up", () -> moveClimberDown, null);
        builder.addBooleanProperty("Spin Intake", () -> spinIntake, null);
        builder.addBooleanProperty("Climber Down", () -> moveClimberUp, null);
      }});
    controlChoiceClimberDown.setDefaultOption("A Button", XboxController.Button.kA);
    controlChoiceClimberDown.addOption("B Button", XboxController.Button.kB);
    controlChoiceClimberDown.addOption("X Button", XboxController.Button.kX);
    SmartDashboard.putData("Climber Up Buttonmap", controlChoiceClimberDown);
    controlChoiceIntake.addOption("A Button", XboxController.Button.kA);
    controlChoiceIntake.setDefaultOption("B Button", XboxController.Button.kB);
    controlChoiceIntake.addOption("X Button", XboxController.Button.kX);
    SmartDashboard.putData("Intake Buttonmap", controlChoiceIntake);
    controlChoiceClimberUp.addOption("A Button", XboxController.Button.kA);
    controlChoiceClimberUp.addOption("B Button", XboxController.Button.kB);
    controlChoiceClimberUp.setDefaultOption("X Button", XboxController.Button.kX);
    SmartDashboard.putData("Climber Down Buttonmap", controlChoiceClimberUp);
  }

  @Override
  public void robotPeriodic() {
    System.out.println(Constants.ScoringConstants.ScoringStage + " " + Constants.ScoringConstants.ScoringStage.getElevatorRotations());

    moveClimberDown = false;
    spinIntake = false;
    moveClimberUp = false;
    if (testJoystick.getAButton()) {
      if (controlChoiceClimberDown.getSelected() == XboxController.Button.kA) {
        moveClimberDown = true;
        new CommandClimb(Constants.ClimberConstants.positionDown);
      }
      else if (controlChoiceClimberUp.getSelected() == XboxController.Button.kA) {
        moveClimberUp = true;
        new CommandClimb(Constants.ClimberConstants.positionUp);
      }
      else if (controlChoiceIntake.getSelected() == XboxController.Button.kA) {
        spinIntake = true;
      }
    }

    if (testJoystick.getBButton()) {
      if (controlChoiceClimberDown.getSelected() == XboxController.Button.kB) {
        moveClimberDown = true;
        new CommandClimb(Constants.ClimberConstants.positionDown);
      }
      else if (controlChoiceClimberUp.getSelected() == XboxController.Button.kB) {
        moveClimberUp = true;
        new CommandClimb(Constants.ClimberConstants.positionUp);
      }
      else if (controlChoiceIntake.getSelected() == XboxController.Button.kB) {
        spinIntake = true;
      }
    }
    
    if (testJoystick.getXButton()) {
      if (controlChoiceClimberDown.getSelected() == XboxController.Button.kX) {
        moveClimberDown = true;
        new CommandClimb(Constants.ClimberConstants.positionDown);
      }
      else if (controlChoiceClimberUp.getSelected() == XboxController.Button.kX) {
        moveClimberUp = true;
        new CommandClimb(Constants.ClimberConstants.positionUp);
      }
      else if (controlChoiceIntake.getSelected() == XboxController.Button.kX) {
        spinIntake = true;
      }
    }
    
    CommandScheduler.getInstance().run(); 
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}

  @Override
  public void simulationPeriodic() {}
}
