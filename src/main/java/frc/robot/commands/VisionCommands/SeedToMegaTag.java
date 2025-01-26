package frc.robot.commands.VisionCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.Swerve.CommandSwerveDrivetrain;

public class SeedToMegaTag extends Command {
  private final CommandSwerveDrivetrain drivetrain;
  private final String limelight;

  public SeedToMegaTag(CommandSwerveDrivetrain swerve, String limelightName) {
    this.drivetrain = swerve;
    this.limelight = limelightName;
    addRequirements(swerve);
  }

  @Override
  public void initialize() {
    LimelightHelpers.PoseEstimate mt = LimelightHelpers.getBotPoseEstimate_wpiBlue(this.limelight);
    if (mt == null) {
      return;
    } else if (mt.tagCount == 0) {
      return;
    } else {
      this.drivetrain.resetPose(mt.pose);
    }
  }

  @Override
  public void execute() {
    
}
  @Override
  public boolean isFinished(){
    return true;
  }
}