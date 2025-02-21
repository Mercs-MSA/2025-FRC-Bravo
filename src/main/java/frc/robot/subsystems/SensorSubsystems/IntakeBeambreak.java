package frc.robot.subsystems.SensorSubsystems;

import java.util.function.BiConsumer;

import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface.RangingMode;
import edu.wpi.first.wpilibj.AsynchronousInterrupt;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeBeambreak extends SubsystemBase{
    private final LaserCan m_intakeBeamBreak = new LaserCan(Constants.IntakeBeambreakConstants.beamBreakChannel);

    private boolean detectsCoral = false;

    BiConsumer<Boolean, Boolean> callback = (risingEdge, fallingEdge) -> {
        if (risingEdge){
            this.detectsCoral = false;
            // RobotContainer.stopEverything();
        }
        if (fallingEdge){
            this.detectsCoral = true;
            //RobotContainer.stopEverything();
            // RobotContainer.prepShooter();
        }
        // RobotContainer.stopEverything();
    };

    //this.detectsCoral = false;


    // private AsynchronousInterrupt asynchronousInterrupt = new AsynchronousInterrupt(m_intakeBeamBreak, callback);

    public boolean checkBreak() {
        return (m_intakeBeamBreak.getMeasurement().distance_mm < 0.05);
    }

    @Override
    public void periodic(){
        // detectsCoral = m_intakeBeamBreak.get();
        SmartDashboard.putBoolean("Intake Beambreak broken", checkBreak());

        // SmartDashboard.putBoolean("Detects coral", detectsCoral);

    }
}
