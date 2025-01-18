package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.Elevator1Constants;
import frc.robot.Constants.elevatorMMConstants;



public class FunnelPivot extends SubsystemLib {
    public class TestSubsystemConfig extends Config {

        /* MAKE SURE TO CHANGE THESE VALUES! THE PID IS NOT CONFIGURED */

        /* These values will later be added into a constants file that has not yet been created. 
         */

        public final double velocityKp = 0;
        public final double velocityKs = 0;
        public final double velocityKv = 0;
        public final double rotations = 0;

        public TestSubsystemConfig() {
            super("FunnelPivot", Elevator1Constants.id, "canivore");  //It is on rio, but make sure that you change the id
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


    private boolean hasTared = false;

    public TestSubsystemConfig config;

    // public boolean isPressed;

    public FunnelPivot(boolean attached){
        super(attached);
        if(attached){
            motor = TalonFXFactory.createConfigTalon(config.id, config.talonConfig); 
        }
    }

    public void motorToPosMM(double pos) {
        setMMPosition(pos);
    }

    public void testMotorGoToPosition(double pos) {
        SetPositionVoltage(pos); // doesnt actually go anywhere
    }

    public double testMotorGetPosition() {
        return GetPosition();
    }
   

    @Override
    protected Config setConfig() {
        config = new TestSubsystemConfig();
        return config;
    }


    // @Override 
    // public void periodic(){

    //     if (LimitSwitch.checkSwitch() && motor != null && !hasTared && Constants.isWithinTol(0, getPivotMotorPosition(), 0.3)) {
    //         tareMotor(); 
    //         hasTared = true; 
    //     }

    //     // If the limit switch is released, reset the taring flag
    //     if (!LimitSwitch.checkSwitch()) {
    //         hasTared = false; 
    //     }

    //     // Update motor position on the SmartDashboard
    //     SmartDashboard.putNumber("FunnelPivot Pos", testMotorGetPosition());
    // }

    

}
