package frc.robot.commands.DriveToPosCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;

public class CommandSetDriveToPos extends Command {
    private String pos;

    public CommandSetDriveToPos(String positionName) {
        this.pos = positionName;
    }

    @Override
    public void initialize() {
        Constants.DriveToPosRuntime.target = pos;
        SmartDashboard.putString("driveToPose_Target", pos);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
