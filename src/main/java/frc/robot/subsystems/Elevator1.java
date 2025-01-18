package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.Elevator1Constants;
import frc.robot.Constants.elevatorMMConstants;
import frc.robot.Constants;


public class Elevator1 extends SubsystemLib {
    public class TestSubsystemConfig extends Config {

        /* MAKE SURE TO CHANGE THESE VALUES! THE PID IS NOT CONFIGURED */

        /* These values will later be added into a constants file that has not yet been created. 
         */

        public final double velocityKp = Elevator1Constants.kP;
        public final double velocityKs = 0;
        public final double velocityKv = 0;
        public final double rotations = Elevator1Constants.positionUp;

        public TestSubsystemConfig() {
            super("ELevatorMotor1", Elevator1Constants.id, "canivore");  //It is on rio, but make sure that you change the id
            configPIDGains(velocityKp, 0, 0);
            configForwardGains(velocityKs, velocityKv, 0, 0);
            configGearRatio(1);
            configNeutralBrakeMode(true);
            isClockwise(false); //true if you want it to spin clockwise
            // configStatorCurrentLimit(10, true);
            configMotionMagic(elevatorMMConstants.speed, elevatorMMConstants.acceleration, elevatorMMConstants.jerk);
            // SetPositionVoltage(rotations);
        }
    }



    public TestSubsystemConfig config;

    private boolean hasTared = false;


    // public boolean isPressed;

    public Elevator1(boolean attached){
        super(attached);
        if(attached){
            motor = TalonFXFactory.createConfigTalon(config.id, config.talonConfig); 
        }
    }

    public void motorToPosMM(double pos) {
        setMMPosition(pos);
    }

    public double elev1MotorGetPosition() {
        return GetPosition();
    }
   

    @Override
    protected Config setConfig() {
        config = new TestSubsystemConfig();
        return config;
    }


    @Override 
    public void periodic(){

        if (Constants.elevatorBeambreakConstants.breakAttached = true && ElevatorBeambreak.checkBreak() && motor != null && !hasTared && Constants.isWithinTol(0, elev1MotorGetPosition(), 0.3)) {
            tareMotor(); 
            hasTared = true; 
        }

        // If the limit switch is released, reset the taring flag
        if (Constants.elevatorBeambreakConstants.breakAttached = true && !ElevatorBeambreak.checkBreak()) {
            hasTared = false; 
        }

        // // Update motor position on the SmartDashboard
        SmartDashboard.putNumber("Elevator 1 Pos", elev1MotorGetPosition());
    }

    

}
