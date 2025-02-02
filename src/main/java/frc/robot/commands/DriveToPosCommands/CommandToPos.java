package frc.robot.commands.DriveToPosCommands;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve.CommandSwerveDrivetrain;

public class CommandToPos extends Command {
  private final CommandSwerveDrivetrain drivetrain;
  private CommandToPos.Destination target;
  SwerveRequest.FieldCentric driveRequest = new SwerveRequest.FieldCentric();

  private final ProfiledPIDController thetaController =
    new ProfiledPIDController(5, 0, 0, new TrapezoidProfile.Constraints(Math.PI, Math.PI));
  private final PIDController xVelController =
    // new ProfiledPIDController(2, 0, 0, new TrapezoidProfile.Constraints(Constants.DriveToPoseConstants.linearMetersMaxVel, Constants.DriveToPoseConstants.linearMetersMaxAccel));
    new PIDController(1.6, 0, 0);
  private final PIDController yVelController =
    new PIDController(1.6, 0, 0);
    // new ProfiledPIDController(2, 0, 0, new TrapezoidProfile.Constraints(Constants.DriveToPoseConstants.linearMetersMaxVel, Constants.DriveToPoseConstants.linearMetersMaxAccel));

  public static class Destination {
    public Pose2d destPose;
    public boolean invertRed = true;
    public String name;
 
    public Destination(String destName, Pose2d pose) {
      this.destPose = pose;
      this.name = destName;
    }

    public Destination(String destName, Pose2d pose, boolean allianceInvert) {
      this.destPose = pose;
      this.invertRed = allianceInvert;
      this.name = destName;
    }
  }

  public CommandToPos(CommandSwerveDrivetrain swerve) {
    this.drivetrain = swerve;

    thetaController.setTolerance(Units.degreesToRadians(Constants.DriveToPoseConstants.angularDegreesTolerance));
    xVelController.setTolerance(Constants.DriveToPoseConstants.linearMetersTolerance);
    yVelController.setTolerance(Constants.DriveToPoseConstants.linearMetersTolerance);
    
    thetaController.enableContinuousInput(-Math.PI, Math.PI);

    SmartDashboard.putData("driveToPosTheta_Pid", thetaController);
    SmartDashboard.putData("driveToPosX_Pid", xVelController);
    SmartDashboard.putData("driveToPosY_Pid", yVelController);

    SmartDashboard.putNumber("driveToPosX_Tgt", -1);
    SmartDashboard.putNumber("driveToPosY_Tgt", -1);
    SmartDashboard.putNumber("driveToPosTheta_Tgt", -1);

    addRequirements(swerve);
  }

  @Override
  public void initialize() {    
    var currPose = drivetrain.getState().Pose;
    thetaController.reset(currPose.getRotation().getRadians());
    // xVelController.reset(currPose.getX());
    // yVelController.reset(currPose.getY());

    if (Constants.DriveToPosRuntime.target == null) {
      return;
    }
    System.out.println(Constants.DriveToPosRuntime.target);
  }

  @Override
  public void execute() {
    if (Constants.DriveToPosRuntime.target == null) {
      return;
    }
    this.target = Constants.DriveToPoseConstants.positions.get(Constants.DriveToPosRuntime.target);

    if (this.target == null) {
      return;
    }

    double targetX = target.destPose.getX();
    double targetY = target.destPose.getY();
    double targetTheta = target.destPose.getRotation().getRadians();
    if (this.target.invertRed) {
      var alliance = DriverStation.getAlliance();
      if (alliance.isPresent() && alliance.get() == DriverStation.Alliance.Red) {
        targetX = Constants.FieldConstants.fieldLengthMeters - targetX;
        targetY = Constants.FieldConstants.fieldWidthMeters - targetY;
        targetTheta = targetTheta + Math.PI;
      }
    }

    var currPose = drivetrain.getState().Pose;
    SmartDashboard.putNumber("currPoseX", currPose.getX());
    SmartDashboard.putNumber("currPoseY", currPose.getY());
    final double thetaVelocity = //thetaController.atGoal() ? 0.0 :
        thetaController.calculate(
            currPose.getRotation().getRadians(), targetTheta);
    final double xVelocity = //xVelController.atSetpoint() ? 0.0 :
        xVelController.calculate(
            currPose.getX(), targetX
        );
    final double yVelocity = //yVelController.atSetpoint() ? 0.0 :
        yVelController.calculate(
            currPose.getY(), targetY
        );

    SmartDashboard.putNumber("driveToPosX_Vel", xVelocity);
    SmartDashboard.putNumber("driveToPosY_Vel", yVelocity);
    SmartDashboard.putNumber("driveToPosTheta_Vel", thetaVelocity);

    SmartDashboard.putNumber("driveToPosX_Tgt", targetX);
    SmartDashboard.putNumber("driveToPosY_Tgt", targetY);
    SmartDashboard.putNumber("driveToPosTheta_Tgt", target.destPose.getRotation().getDegrees());

    drivetrain.setControl(
        driveRequest
        .withVelocityX(xVelocity * (DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get() == Alliance.Red ? -1 : 1))
        .withVelocityY(yVelocity * (DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get() == Alliance.Red ? -1 : 1))
        .withRotationalRate(thetaVelocity)
    );
}
  public boolean atGoal() {
    return thetaController.atGoal() && xVelController.atSetpoint() && yVelController.atSetpoint();
  }

  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putNumber("driveToPosX_Vel", 0);
    SmartDashboard.putNumber("driveToPosY_Vel", 0);
    SmartDashboard.putNumber("driveToPosTheta_Vel", 0);
  }

  @Override
  public boolean isFinished(){
    return atGoal();
    // return true;
  }
}