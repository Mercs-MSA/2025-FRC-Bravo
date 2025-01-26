package frc.robot.subsystems.Mechanisms.Elevator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Constants.Elevator2Constants;

import frc.robot.Constants.elevatorMMConstants;
import frc.robot.subsystems.SensorSubsystems.ElevatorBeambreak;
import frc.robot.subsystems.SubsystemUtils.SubsystemLib;
import frc.robot.subsystems.SubsystemUtils.TalonFXFactory;


public class Elevator2 extends SubsystemLib {
    public class TestSubsystemConfig extends Config {

        /* MAKE SURE TO CHANGE THESE VALUES! THE PID IS NOT CONFIGURED */

        /* These values will later be added into a constants file that has not yet been created. 
         */

        public final double velocityKp = Elevator2Constants.kP;
        public final double velocityKs = 0;
        public final double velocityKv = 0;

        public TestSubsystemConfig() {
            super("Elevator2motor", Elevator2Constants.id, "rio");  //It is on rio, but make sure that you change the id
            configPIDGains(velocityKp, 0, 0);
            configForwardGains(velocityKs, velocityKv, 0, 0);
            configGearRatio(1);
            configNeutralBrakeMode(true);
            isClockwise(true); //true if you want it to spin clockwise
            // configStatorCurrentLimit(10, true);
            configMotionMagic(elevatorMMConstants.speed, elevatorMMConstants.acceleration, elevatorMMConstants.jerk);
            // SetPositionVoltage(rotations);
        }
    }

    public TestSubsystemConfig config;
    public boolean hasTared = false;


    public Elevator2(boolean attached){
        super(attached);
        if(attached){
            motor = TalonFXFactory.createConfigTalon(config.id, config.talonConfig); 
        }
    }

    public void motorToPosMM(double pos) {
        setMMPosition(pos);
    }

    // public void testMotorGoToPosition(double pos) {
    //     SetPositionVoltage(pos); // doesnt actually go anywhere
    // }

    public double elev2MotorGetPosition() {
        return GetPosition();
    }

   

    @Override
    protected Config setConfig() {
        config = new TestSubsystemConfig();
        return config;
    }


    
    @Override 
    public void periodic(){

         if (ElevatorBeambreak.checkBreak() && motor != null && !hasTared && Constants.isWithinTol(0, elev2MotorGetPosition(), 2)) {
            tareMotor(); 
            hasTared = true; 
        }

        // If the limit switch is released, reset the taring flag
        if (!ElevatorBeambreak.checkBreak()) {
            hasTared = false; 
        }

       
        SmartDashboard.putNumber("Elevator 2 Pos", elev2MotorGetPosition());

    }
}
