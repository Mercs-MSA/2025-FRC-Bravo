package frc.robot.commands;

import java.util.Map;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.Constants;
import frc.robot.Constants.ScoringStageVal;
import frc.robot.commands.CANdleCommands.CommandCandleSetAnimation;
import frc.robot.commands.ClimberCommands.CommandClimbToPos;
import frc.robot.commands.ClimberCommands.CommandClimbToggle;
import frc.robot.commands.DriveToPosCommands.CommandLoadDriveToPos;
import frc.robot.commands.DriveToPosCommands.CommandSetDriveToPos;
import frc.robot.commands.DriveToPosCommands.CommandToPos;
import frc.robot.commands.ElevatorCommands.CommandElevatorToStage;
import frc.robot.commands.FunnelCommands.CommandFunnelPivotToPos;
import frc.robot.commands.FunnelCommands.CommandFunnelToggle;
import frc.robot.commands.IntakeCommands.CommandIntakeCollect;
import frc.robot.commands.IntakeCommands.CommandIntakeOut;
import frc.robot.commands.ScoringModeCommands.CommandChangeScoreStage;
import frc.robot.commands.VisionCommands.SeedToMegaTag;
import frc.robot.subsystems.SensorSubsystems.CANdle_LED;
import frc.robot.subsystems.Swerve.CommandSwerveDrivetrain;
import frc.robot.subsystems.SensorSubsystems.IntakeBeambreak;
import frc.robot.subsystems.Mechanisms.Intake.IntakeFlywheels;

public class CommandMap {
    private CommandSwerveDrivetrain drivetrain;
    private IntakeFlywheels flywheels;
    private IntakeBeambreak beamBreak;
    private CANdle_LED leds;
    private CommandXboxController driver;

    public CommandMap (
        CommandSwerveDrivetrain drivetrain,
        IntakeFlywheels flywheels,
        IntakeBeambreak beamBreak,
        CANdle_LED leds,
        CommandXboxController driver
    ) {
        this.drivetrain = drivetrain;
        this.flywheels = flywheels;
        this.beamBreak = beamBreak;
        this.leds = leds;
        this.driver = driver;
    }

    public Map<String, Command> getMap() {
        return Map.ofEntries(



            /****************************************/
            /*         DRIVE TO POS COMMANDS        */
            /****************************************/
            Map.entry(
                "Drive To Source",
                new SequentialCommandGroup(
                    new CommandSetDriveToPos("Source"),
                    new CommandChangeScoreStage(ScoringStageVal.INTAKEREADY),
                    new ParallelCommandGroup(
                        new CommandToPos(drivetrain),
                        new CommandElevatorToStage(),
                        new CommandIntakeCollect(flywheels, beamBreak, 1)
                    )
                )
            ), // WHILETRUE
            Map.entry(
                "Drive Position 1",
                new SequentialCommandGroup(
                    new CommandLoadDriveToPos(() -> Constants.DriveToPosRuntime.autoTargets.get(0)),
                    new ParallelCommandGroup(
                        new CommandToPos(drivetrain),
                        new CommandElevatorToStage(),
                        new CommandCandleSetAnimation(leds, CANdle_LED.AnimationTypes.Strobe)
                    )
                )
            ), // WHILETRUE
            Map.entry(
                "Drive Position 2",
                new SequentialCommandGroup(
                    new CommandLoadDriveToPos(() -> Constants.DriveToPosRuntime.autoTargets.get(1)),
                    new ParallelCommandGroup(
                        new CommandToPos(drivetrain),
                        new CommandElevatorToStage()
                    )
                )
            ), // WHILETRUE
            Map.entry(
                "PathWithDriveToPos",
                new SequentialCommandGroup(
                    new CommandSetDriveToPos("ReefTest"),
                    new CommandToPos(drivetrain)
                )
            ),
            Map.entry(
                "Reef Test",
                new SequentialCommandGroup(
                    new CommandSetDriveToPos("ReefTest"),
                    new ParallelCommandGroup(
                        new CommandToPos(drivetrain),
                        new CommandElevatorToStage()
                    )
                )
            ),



            /****************************************/
            /*            INTAKE COMMANDS           */
            /****************************************/
            Map.entry(
                "Intake Collect",
                new CommandIntakeCollect(flywheels, beamBreak, Constants.MaxAngularRate)
            ),
            // Map.entry(
            //     "Intake Collect",
            //     new CommandIntakeCollect(flywheels, beamBreak, 8)
            // ),
            Map.entry(
                "Score",
                new CommandIntakeOut(flywheels, beamBreak, Constants.MaxAngularRate)
            ),
            Map.entry(
                "Intake Reverse",
                new CommandIntakeOut(flywheels, beamBreak, 5)
            ),



            /****************************************/
            /*           ELEVATOR COMMANDS          */
            /****************************************/
            Map.entry(
                "L1",
                new SequentialCommandGroup(
                    new CommandChangeScoreStage(ScoringStageVal.L1),
                    new CommandElevatorToStage()
                )
            ),
            Map.entry(
                "L2",
                new SequentialCommandGroup(
                    new CommandChangeScoreStage(ScoringStageVal.L2),
                    new CommandElevatorToStage()
                )
            ),
            Map.entry(
                "L3",
                new SequentialCommandGroup(
                    new CommandChangeScoreStage(ScoringStageVal.L3),
                    new CommandElevatorToStage()
                )
            ),
            Map.entry(
                "L4",
                new SequentialCommandGroup(
                    new CommandChangeScoreStage(ScoringStageVal.L4),
                    new CommandElevatorToStage()
                )
            ),
            Map.entry(
                "ELEVIntakePos",
                new SequentialCommandGroup(
                    new CommandChangeScoreStage(ScoringStageVal.INTAKEREADY),
                    new CommandElevatorToStage()
                )
            ),
            Map.entry(
                "Elevator To Stage",
                new CommandElevatorToStage()
            ),



            /****************************************/
            /*            FUNNEL COMMANDS           */
            /****************************************/
            Map.entry(
                "MoveFunnel",
                new SequentialCommandGroup(
                    new CommandChangeScoreStage(ScoringStageVal.INTAKEREADY),
                    new CommandFunnelPivotToPos(Constants.FunnelPivotConstants.posUp)
                )
            ),
            Map.entry(
                "Toggle Funnel",
                new CommandFunnelToggle()
            ),



            /****************************************/
            /*           CLIMBING COMMANDS          */
            /****************************************/
            Map.entry(
                "Toggle Climb",
                new CommandClimbToggle()
            ),
            Map.entry(
                "Climb Up",
                new CommandClimbToPos(Constants.ClimberConstants.positionDown)
            ),
            Map.entry(
                "Climb Down",
                new CommandClimbToPos(Constants.ClimberConstants.positionUp)
            ),
            Map.entry(
                "Score Climb",
                new CommandChangeScoreStage(ScoringStageVal.CLIMBING)
            ),



            /****************************************/
            /*         STATE CHANGE COMMANDS        */
            /****************************************/
            Map.entry(
                "Score L1",
                new CommandChangeScoreStage(ScoringStageVal.L1)
            ),
            Map.entry(
                "Score L2",
                new CommandChangeScoreStage(ScoringStageVal.L2)
            ),
            Map.entry(
                "Score L3",
                new CommandChangeScoreStage(ScoringStageVal.L3)
            ),
            Map.entry(
                "Score L4",
                new CommandChangeScoreStage(ScoringStageVal.L4)
            ),



            /****************************************/
            /*              LED COMMANDS            */
            /****************************************/
            Map.entry(
                "Candle Larson",
                new CommandCandleSetAnimation(leds, CANdle_LED.AnimationTypes.Larson)
            ),
            Map.entry(
                "Candle Twinkle",
                new CommandCandleSetAnimation(leds, CANdle_LED.AnimationTypes.Twinkle)
            ),



            /****************************************/
            /*            RESET COMMANDS            */
            /****************************************/
            Map.entry(
                "Reset Field Centric",
                drivetrain.runOnce(
                    () -> drivetrain.seedFieldCentric()
                )
            ),
            Map.entry(
                "Reset All",
                new ParallelCommandGroup()
            ),
            Map.entry(
                "Reset MegaTag",
                new SeedToMegaTag(drivetrain, Constants.VisionConstants.limelightFrontName)
            ),



            /****************************************/
            /*         CALIBRATION COMMANDS         */
            /****************************************/
            Map.entry(
                "Calibrate Brake",
                drivetrain.applyRequest(
                    () -> new SwerveRequest.SwerveDriveBrake()
                )
            ), // WHILETRUE
            Map.entry(
                "Calibrate Turn",
                drivetrain.applyRequest(
                    () -> new SwerveRequest.PointWheelsAt().withModuleDirection(
                        new Rotation2d(
                            -driver.getLeftY(),
                            -driver.getLeftX()
                        )
                    )
                )
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



            /****************************************/
            /*            TEST COMMANDS             */
            /****************************************/
            Map.entry(
                "Test Print",
                new PrintCommand("Command one was selected!")
            )
        );
    }
}
