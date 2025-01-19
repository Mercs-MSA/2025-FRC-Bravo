package frc.robot.subsystems;


import frc.robot.Constants.FunnelPivotConstants;
import frc.robot.Constants.elevatorMMConstants;



public class FunnelPivot extends SubsystemLib {
    public class TestSubsystemConfig extends Config {

        /* MAKE SURE TO CHANGE THESE VALUES! THE PID IS NOT CONFIGURED */

        /* These values will later be added into a constants file that has not yet been created. 
         */

        public final double velocityKp = FunnelPivotConstants.kP;
        public final double velocityKs = 0;
        public final double velocityKv = 0;
        public final double rotations = 0;

        public TestSubsystemConfig() {
            super("FunnelPivot", FunnelPivotConstants.id, "rio");  //It is on rio, but make sure that you change the id
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


    public FunnelPivot(boolean attached){
        super(attached);
        if(attached){
            motor = TalonFXFactory.createConfigTalon(config.id, config.talonConfig); 
        }
    }

    public void motorToPosMM(double pos) {
        setMMPosition(pos);
    }

    public void funnelPositionVoltage(double pos) {
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

    

}
