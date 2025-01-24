package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;

public class CommandLoadDriveToPos extends Command {
    private Supplier<String> getter;

    public CommandLoadDriveToPos(Supplier<String> positionGetter) {
        this.getter = positionGetter;
    }

    @Override
    public void initialize() {
        Constants.DriveToPosRuntime.target = this.getter.get();
        SmartDashboard.putString("driveToPose_Target", Constants.DriveToPosRuntime.target);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
