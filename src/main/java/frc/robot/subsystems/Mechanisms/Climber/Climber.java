package frc.robot.subsystems.Mechanisms.Climber;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ClimberConstants;
import frc.robot.subsystems.SubsystemUtils.SubsystemLib;
import frc.robot.subsystems.SubsystemUtils.TalonFXFactory;

public class Climber extends SubsystemLib {
    public class TestSubsystemConfig extends Config {

   

        public final double velocityKp = ClimberConstants.kP;
        public final double velocityKs = 0;
        public final double velocityKv = 0;
        

        public TestSubsystemConfig() {
            super("IntakePivotMotor", ClimberConstants.id, "rio");  //It is on rio, but make sure that you change the id
            configPIDGains(velocityKp, 0, 0);
            configForwardGains(velocityKs, velocityKv, 0, 0);
            configGearRatio(1);
            configNeutralBrakeMode(true);
            isClockwise(true); //true if you want it to spin clockwise
     
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
        SetPositionVoltage(pos); 
    }

    public double getClimberMotorPosition() {
        return GetPosition();
    }

    

   

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

