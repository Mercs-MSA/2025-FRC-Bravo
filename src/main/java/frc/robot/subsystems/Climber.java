package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemLib {
    public class TestSubsystemConfig extends Config {

        /* MAKE SURE TO CHANGE THESE VALUES! THE PID IS NOT CONFIGURED */

        /* These values will later be added into a constants file that has not yet been created. 
         */

        public final double velocityKp = ClimberConstants.kP;
        public final double velocityKs = 0;
        public final double velocityKv = 0;
        

        public TestSubsystemConfig() {
            super("IntakePivotMotor", ClimberConstants.id, "canivore");  //It is on rio, but make sure that you change the id
            configPIDGains(velocityKp, 0, 0);
            configForwardGains(velocityKs, velocityKv, 0, 0);
            configGearRatio(1);
            configNeutralBrakeMode(true);
            isClockwise(true); //true if you want it to spin clockwise
            // configStatorCurrentLimit(10, true);
            // SetPositionVoltage(rotations);
        }
    }

    public TestSubsystemConfig config;

    public Climber(boolean attached){
        super(attached);
        if(attached){
            motor = TalonFXFactory.createConfigTalon(config.id, config.talonConfig); 
        }
    }

    public void climberToPosMM(double pos) {
        setMMPositionFOC(pos);
    }

    public void climberGoToPosition(double pos) {
        SetPositionVoltage(pos); // doesnt actually go anywhere
    }

    public double getClimberMotorPosition() {
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
        SmartDashboard.putNumber("Climber Position", getClimberMotorPosition());
    }

}

