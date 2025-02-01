// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ctre.phoenix6.Utils;

import edu.wpi.first.networktables.BooleanArrayEntry;
import edu.wpi.first.networktables.BooleanArrayTopic;
import edu.wpi.first.networktables.BooleanEntry;
import edu.wpi.first.networktables.BooleanTopic;
import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.ScoringStageVal;
import frc.robot.LimelightHelpers.RawFiducial;
import frc.robot.commands.ClimberCommands.CommandClimbToPos;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  boolean moveClimberDown;
  boolean spinIntake;
  boolean moveClimberUp;
  private SendableChooser<XboxController.Button> controlChoiceClimberDown = new SendableChooser<>();
  private SendableChooser<XboxController.Button> controlChoiceIntake = new SendableChooser<>();
  private SendableChooser<XboxController.Button> controlChoiceClimberUp = new SendableChooser<>();

  private final RobotContainer m_robotContainer;
  public SendableChooser<String> savePref = new SendableChooser<>();
  private NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
  private NetworkTable controlMapTable = networkTableInstance.getTable("Control Map Table");
  private BooleanTopic saveTriggerTopic = controlMapTable.getBooleanTopic("SaveTrigger");
  private BooleanEntry saveTrigger = saveTriggerTopic.getEntry(false);
  private BooleanTopic loadTriggerTopic = controlMapTable.getBooleanTopic("LoadTrigger");
  private BooleanEntry loadTrigger = loadTriggerTopic.getEntry(false);

  public final XboxController testJoystick = new XboxController(2);

  private List<Integer> validIDs = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22));





  public Robot() {
    m_robotContainer = new RobotContainer();
    savePref.addOption("Competition", "Competition");
    savePref.setDefaultOption("Testing", "Testing");
    savePref.addOption("A", "A");
    savePref.addOption("B", "B");
    savePref.addOption("C", "C");
    SmartDashboard.putData("Save Map Slot", savePref);

    saveTriggerTopic.publish();
    loadTriggerTopic.publish();
  }
  
  @Override
  public void robotPeriodic() {
    // System.out.println(Constants.ScoringConstants.ScoringStage + " " + Constants.ScoringConstants.ScoringStage.getElevatorRotations());

    SmartDashboard.putString("Scoring Stage", Constants.ScoringConstants.ScoringStage.toString());

    moveClimberDown = false;
    spinIntake = false;
    moveClimberUp = false;
    if (testJoystick.getAButton()) {
      if (controlChoiceClimberDown.getSelected() == XboxController.Button.kA) {
        moveClimberDown = true;
        new CommandClimbToPos(Constants.ClimberConstants.positionDown);
      }
      else if (controlChoiceClimberUp.getSelected() == XboxController.Button.kA) {
        moveClimberUp = true;
        new CommandClimbToPos(Constants.ClimberConstants.positionUp);
      }
      else if (controlChoiceIntake.getSelected() == XboxController.Button.kA) {
        spinIntake = true;
      }
    }

    if (testJoystick.getBButton()) {
      if (controlChoiceClimberDown.getSelected() == XboxController.Button.kB) {
        moveClimberDown = true;
        new CommandClimbToPos(Constants.ClimberConstants.positionDown);
      }
      else if (controlChoiceClimberUp.getSelected() == XboxController.Button.kB) {
        moveClimberUp = true;
        new CommandClimbToPos(Constants.ClimberConstants.positionUp);
      }
      else if (controlChoiceIntake.getSelected() == XboxController.Button.kB) {
        spinIntake = true;
      }
    }
    
    if (testJoystick.getXButton()) {
      if (controlChoiceClimberDown.getSelected() == XboxController.Button.kX) {
        moveClimberDown = true;
        new CommandClimbToPos(Constants.ClimberConstants.positionDown);
      }
      else if (controlChoiceClimberUp.getSelected() == XboxController.Button.kX) {
        moveClimberUp = true;
        new CommandClimbToPos(Constants.ClimberConstants.positionUp);
      }
      else if (controlChoiceIntake.getSelected() == XboxController.Button.kX) {
        spinIntake = true;
      }
    }
    
    CommandScheduler.getInstance().run(); 

    boolean doRejectUpdate = false;
    SmartDashboard.putNumber("PigeonRotation", m_robotContainer.drivetrain.getState().Pose.getRotation().getDegrees());
    LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightFrontName, m_robotContainer.drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);
    LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightBackName, m_robotContainer.drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);
    
    LimelightHelpers.PoseEstimate mt_front = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightFrontName);
    LimelightHelpers.PoseEstimate mt_back = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightBackName);

    //Update Valid IDs
    LimelightHelpers.SetFiducialIDFiltersOverride("limelight", validIDs.stream().mapToInt(Integer::intValue).toArray());

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
    } 
    
    else if (mt_front == null) {
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
    if (Constants.DriveToPosRuntime.target == "Source") {
      if (validIDs.contains(18)) {
        validIDs.remove(Integer.valueOf(18));
      }
      
      if (validIDs.contains(7)) {
        validIDs.remove(Integer.valueOf(7));
      }
    } else {
      if (!validIDs.contains(18)) {
        validIDs.add(18);
      }
      if (!validIDs.contains(7)) {
        validIDs.add(7);
      }
    }

    RawFiducial closestTag = null;
    if (mt_front != null) {
      for (RawFiducial tag : mt_front.rawFiducials) {
        if (closestTag == null) {
          closestTag = tag;
        } else if (tag.distToRobot < closestTag.distToRobot) {
          closestTag = tag;
        }
      }
    }
    if (closestTag != null) {
      if (Constants.DriveToPoseConstants.tagDestinationMap.containsKey(Integer.toString(closestTag.id))) {
        Constants.DriveToPosRuntime.autoTargets = Constants.DriveToPoseConstants.tagDestinationMap.get(Integer.toString(closestTag.id));
      }
    }
    SmartDashboard.putNumber("frontClosestTag", (closestTag != null ? closestTag.id : 0));
    SmartDashboard.putString("possibleDestinationA", Constants.DriveToPosRuntime.autoTargets.get(0));
    SmartDashboard.putString("possibleDestinationB", Constants.DriveToPosRuntime.autoTargets.get(1));

    SmartDashboard.updateValues();
    SmartDashboard.putNumberArray("Valid IDs", validIDs.stream().mapToDouble(Integer::intValue).toArray());

  }


  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void disabledInit() {
    Constants.robotEnabled = false;
  }

  @Override
  public void disabledPeriodic() {
    if (saveTrigger.get() == true)  {
      saveTrigger.set(false);
      m_robotContainer.savePreference(savePref);
    }

    if (loadTrigger.get() == true) {
      loadTrigger.set(false);
      m_robotContainer.loadPreference(savePref);
    }
  }

  @Override
  public void disabledExit() {
    Constants.robotEnabled = true;
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
