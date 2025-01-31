package frc.robot.subsystems.SensorSubsystems;

import java.util.function.BiConsumer;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorBeambreak extends SubsystemBase {
    private static final DigitalInput m_beamBreak = new DigitalInput(
            Constants.elevatorBeambreakConstants.beamBreakChannel);

    private boolean elevatorDown = false;

    BiConsumer<Boolean, Boolean> callback = (risingEdge, fallingEdge) -> {
        if (risingEdge) {
            this.elevatorDown = false;

        }
        if (fallingEdge) {
            this.elevatorDown = true;
        }

    };

    public ElevatorBeambreak() {

    }

    public static boolean checkBreak() {
        return m_beamBreak.get();
    }

    @Override
    public void periodic() {
        elevatorDown = m_beamBreak.get();
        SmartDashboard.putBoolean("Elevator Down", checkBreak());

        // SmartDashboard.putBoolean("Detects coral", detectsCoral);

    }
}
