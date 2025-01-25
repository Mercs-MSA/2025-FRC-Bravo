package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ScoringStageVal;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.IntakeBeambreak;
import frc.robot.subsystems.IntakeFlywheels;

public class StaticCustomCommands {
    public static Command intakeCollect(IntakeFlywheels m_intakeFlywheels, IntakeBeambreak m_beambreak, double voltage) {
        return new CommandIntakeCollect(m_intakeFlywheels, m_beambreak, voltage);
    }

    public static Command intakeOut(IntakeFlywheels m_intakeFlywheels, IntakeBeambreak m_beambreak, double voltage) {
        return new CommandIntakeOut(m_intakeFlywheels, m_beambreak, voltage);
    }

    public static Command changeScoreStage(ScoringStageVal t) {
        return new CommandChangeScoreStage(t);
    }

    public static Command setDriveToPos(String positionName) {
        return new CommandSetDriveToPos(positionName);
    }

    public static Command toPos(CommandSwerveDrivetrain swerve) {
        return new CommandToPos(swerve);
    }

    public static Command funnelToggle() {
        return new CommandFunnelToggle();
    }
    
    public static Command funnelPivot(double pos) {
        return new CommandFunnelPivot(pos);
    }

    public static Command elevatorToStage() {
        return new CommandElevatorToStage();
    }

    public static Command elevatorToPos(double pos) {
        return new CommandElevatorToPos(pos);
    }

    public static Command climbToggle() {
        return new CommandClimbToggle();
    }

    public static Command climb(double pos) {
        return new CommandClimb(pos);
    }

    public static Command seedToMegaTag(CommandSwerveDrivetrain swerve, String limelightName) {
        return new SeedToMegaTag(swerve, limelightName);
    }
}