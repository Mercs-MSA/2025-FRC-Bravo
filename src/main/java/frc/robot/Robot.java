// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.Utils;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.*;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  
  public final CommandXboxController driver = new CommandXboxController(2);
  private final ControlMapper driverMappedButtonA = new ControlMapper(driver.a(), "Driver Button A", "aButton");
  private final ControlMapper driverMappedButtonB = new ControlMapper(driver.b(), "Driver Button B", "bButton");
  private final ControlMapper driverMappedButtonX = new ControlMapper(driver.x(), "Driver Button X", "xButton");
  private final ControlMapper driverMappedButtonY = new ControlMapper(driver.y(), "Driver Button Y", "yButton");

  // Need input field for text on elastic, treat like seeds/codes in a game where you enter the name of the person and get your presets

  private final RobotContainer m_robotContainer;


  public Robot() {
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    // System.out.println(Constants.ScoringConstants.ScoringStage + " " + Constants.ScoringConstants.ScoringStage.getElevatorRotations());

    SmartDashboard.putString("Scoring Stage", Constants.ScoringConstants.ScoringStage.toString());
    
    CommandScheduler.getInstance().run(); 

    boolean doRejectUpdate = false;
    SmartDashboard.putNumber("PigeonRotation", m_robotContainer.drivetrain.getState().Pose.getRotation().getDegrees());
    LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightFrontName, m_robotContainer.drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);
    LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightBackName, m_robotContainer.drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);
    
    LimelightHelpers.PoseEstimate mt_front = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightFrontName);
    LimelightHelpers.PoseEstimate mt_back = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightBackName);

    SmartDashboard.putBoolean("FrontLimelightOnlineStatus", mt_front != null);
    SmartDashboard.putBoolean("BackLimelightOnlineStatus", mt_back != null);

    m_robotContainer.drivetrain.setVisionMeasurementStdDevs(Constants.VisionConstants.visionStdDevs);
    LimelightHelpers.PoseEstimate mt_inUse = null;
    if (mt_front != null && mt_back != null) {
      if (mt_front.avgTagArea > mt_back.avgTagArea) {
        mt_inUse = mt_front;
        SmartDashboard.putString("LimelightInUse", "Front");
      } else {
        mt_inUse = mt_back;
        SmartDashboard.putString("LimelightInUse", "Back");
      }
    } else if (mt_front == null) {
      mt_inUse = mt_back;
      SmartDashboard.putString("LimelightInUse", "Back");
    } else if (mt_back == null) {
      mt_inUse = mt_front;
      SmartDashboard.putString("LimelightInUse", "Front");
    } else {
      SmartDashboard.putString("LimelightInUse", "None");
    }
    SmartDashboard.putNumber("angularVel", m_robotContainer.drivetrain.getPigeon2().getAngularVelocityZWorld().getValueAsDouble());

    if (mt_inUse != null) {
      if(Math.abs(m_robotContainer.drivetrain.getPigeon2().getAngularVelocityZWorld().getValueAsDouble()) > 720) // if our angular velocity is greater than 720 degrees per second, ignore vision updates
        {
          doRejectUpdate = true;
        }
        if(mt_inUse.tagCount == 0)
        {
          doRejectUpdate = true;
        }
        if(!doRejectUpdate)
        {
          m_robotContainer.drivetrain.addVisionMeasurement(
              mt_inUse.pose,
              Utils.fpgaToCurrentTime(mt_inUse.timestampSeconds));
        }
    }
  }


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
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }
}
