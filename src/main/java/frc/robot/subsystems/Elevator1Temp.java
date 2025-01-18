package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Constants.Elevator1Constants;
import frc.robot.Constants.elevatorMMConstants;


public class Elevator1Temp extends SubsystemLib {
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


    public static boolean isPositioning = false;
    public static double positionTo = 0;

    public TestSubsystemConfig config;

    // public boolean isPressed;

    public Elevator1Temp(boolean attached){
        super(attached);
        if(attached){
            motor = TalonFXFactory.createConfigTalon(config.id, config.talonConfig); 
        }
    }

    public void motorToPosMM(double pos) {
        isPositioning = true;
        positionTo = pos;
        setMMPosition(pos);
    }

    // public void testMotorGoToPosition(double pos) {
    //     SetPositionVoltage(pos); // doesnt actually go anywhere
    // }

    public double elev1MotorGetPosition() {
        return GetPosition();
    }

    // public Command runPosition(double pos) {
    //     return run(() -> (pos)).withName("PivotGoUp");
    // }

   

    @Override
    protected Config setConfig() {
        config = new TestSubsystemConfig();
        return config;
    }


    @Override 
    public void periodic(){

        // if (LimitSwitch.checkSwitch() && motor != null && !hasTared && Constants.isWithinTol(0, getPivotMotorPosition(), 0.3)) {
        //     tareMotor(); 
        //     hasTared = true; 
        // }

        // // If the limit switch is released, reset the taring flag
        // if (!LimitSwitch.checkSwitch()) {
        //     hasTared = false; 
        // }

        // // Update motor position on the SmartDashboard
        SmartDashboard.putNumber("Elevator 1 Pos", elev1MotorGetPosition());

        if (isPositioning)
        {
            if (Constants.isWithinTol(positionTo, elev1MotorGetPosition(), 0.5)) {isPositioning = false;}

        }
    }

    public boolean isPositioning()
    {
        return isPositioning;
    }

}
