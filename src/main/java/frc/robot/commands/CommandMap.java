package frc.robot.commands;

import java.util.Map;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.Constants;
import frc.robot.Constants.ScoringStageVal;
import frc.robot.subsystems.CANdle_LED;
import frc.robot.subsystems.CANdle_LED.AnimationTypes;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.IntakeBeambreak;
import frc.robot.subsystems.IntakeFlywheels;

public class CommandMap {
    public Map<String, Command> getMap(
        CommandSwerveDrivetrain drivetrain,
        IntakeFlywheels m_intakeFlywheels,
        IntakeBeambreak m_intakeBeamBreak,
        CANdle_LED m_leds,
        CommandXboxController driver
    ) {
        return Map.ofEntries(
            Map.entry(
                "Test Print",
                Commands.print("Command one was selected!")
            ),
            Map.entry(
                "PathWithDriveToPos",
                Commands.sequence(CommandMap.setDriveToPos("ReefTest"), CommandMap.toPos(drivetrain))
            ),
            Map.entry(
                "Intake Collect",
                intakeCollect(m_intakeFlywheels, m_intakeBeamBreak, Constants.MaxAngularRate)
            ),
            // Map.entry(
            //     "Intake Collect",
            //     intakeCollect(m_IntakeFlywheels, m_intakeBeamBreak, 8)
            // ),
            Map.entry(
                "Score",
                intakeOut(m_intakeFlywheels, m_intakeBeamBreak, Constants.MaxAngularRate)
            ),
            Map.entry(
                "L1",
                Commands.sequence(changeScoreStage(ScoringStageVal.L1), elevatorToStage())
            ),
            Map.entry(
                "L2",
                Commands.sequence(changeScoreStage(ScoringStageVal.L2), elevatorToStage())
            ),
            Map.entry(
                "L3",
                Commands.sequence(changeScoreStage(ScoringStageVal.L3), elevatorToStage())
            ),
            Map.entry(
                "L4",
                Commands.sequence(changeScoreStage(ScoringStageVal.L4), elevatorToStage())
            ),
            Map.entry(
                "ELEVIntakePos",
                Commands.sequence(changeScoreStage(ScoringStageVal.INTAKEREADY), elevatorToStage())
            ),
            Map.entry(
                "MoveFunnel",
                Commands.sequence(changeScoreStage(ScoringStageVal.INTAKEREADY), funnelPivot(Constants.FunnelPivotConstants.posUp))
            ),
            Map.entry(
                "Calibrate Brake",
                drivetrain.applyRequest(() -> new SwerveRequest.SwerveDriveBrake())
            ), // WHILETRUE
            Map.entry(
                "Calibrate Turn",
                drivetrain.applyRequest(() -> new SwerveRequest.PointWheelsAt().withModuleDirection(new Rotation2d(-driver.getLeftY(), -driver.getLeftX())))
            ), // WHILETRUE
            Map.entry(
                "Calibrate Dyn Forward",
                drivetrain.sysIdDynamic(Direction.kForward)
            ), // WHILETRUE
            Map.entry(
                "Calibrate Dyn Backward",
                drivetrain.sysIdDynamic(Direction.kReverse)
            ), // WHILETRUE
            Map.entry(
                "Calibrate Static Forward",
                drivetrain.sysIdQuasistatic(Direction.kForward)
            ), // WHILETRUE
            Map.entry(
                "Calibrate Static Backward",
                drivetrain.sysIdQuasistatic(Direction.kReverse)
            ), // WHILETRUE
            Map.entry(
                "Intake Reverse",
                intakeOut(m_intakeFlywheels, m_intakeBeamBreak, 5)
            ),
            Map.entry(
                "Reset Field Centric",
                drivetrain.runOnce(() -> drivetrain.seedFieldCentric())
            ),
            Map.entry(
                "Drive To Source",
                setDriveToPos("Source").andThen(changeScoreStage(ScoringStageVal.INTAKEREADY)).andThen(Commands.parallel(toPos(drivetrain),elevatorToStage(),intakeCollect(m_intakeFlywheels, m_intakeBeamBreak, 1)))
            ), // WHILETRUE
            Map.entry(
                "Score L1",
                changeScoreStage(ScoringStageVal.L1)
            ),
            Map.entry(
                "Score L2",
                changeScoreStage(ScoringStageVal.L2)
            ),
            Map.entry(
                "Score L3",
                changeScoreStage(ScoringStageVal.L3)
            ),
            Map.entry(
                "Score L4",
                changeScoreStage(ScoringStageVal.L4)
            ),
            Map.entry(
                "Score Climb",
                changeScoreStage(ScoringStageVal.CLIMBING)
            ),
            Map.entry(
                "Score Intake",
                Commands.sequence(changeScoreStage(ScoringStageVal.INTAKEREADY), elevatorToStage())
            ),
            Map.entry(
                "Toggle Climb",
                climbToggle()
            ),
            Map.entry(
                "Toggle Funnel",
                funnelToggle()
            ),
            Map.entry(
                "Reset MegaTag",
                seedToMegaTag(drivetrain, Constants.VisionConstants.limelightFrontName)
            ),
            Map.entry(
                "Drive Position 1",
                loadDriveToPos(0).andThen(Commands.parallel(toPos(drivetrain), elevatorToStage(), candleSetAnimation(m_leds, CANdle_LED.AnimationTypes.Strobe)))
            ), // WHILETRUE
            Map.entry(
                "Drive Position 2",
                loadDriveToPos(1).andThen(Commands.parallel(toPos(drivetrain), elevatorToStage(), candleSetAnimation(m_leds, CANdle_LED.AnimationTypes.Strobe)))
            ), // WHILETRUE
            Map.entry(
                "Candle Larson",
                candleSetAnimation(m_leds, CANdle_LED.AnimationTypes.Larson)
            ),
            Map.entry(
                "Candle Twinkle",
                candleSetAnimation(m_leds, CANdle_LED.AnimationTypes.Twinkle)
            )
        );
    }

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

    public static Command candleSetAnimation(CANdle_LED candle, AnimationTypes anim) {
        return new CommandCandleSetAnimation(candle, anim);
    }

    public static Command loadDriveToPos (int index) {
        return new CommandLoadDriveToPos(() -> Constants.DriveToPosRuntime.autoTargets.get(index));
    }
}
