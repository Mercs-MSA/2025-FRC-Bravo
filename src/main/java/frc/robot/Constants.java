package frc.robot;


public class Constants {

    public enum ScoringStageVal {
        INTAKEREADY(0, true, false, true),
        INTAKING(0, false, false, false),
        L2(27.5, true, false, false),
        L3(53, true, false, false),
        L4(100, true, false, false),
        CLIMBING(0, false, true, false);

        private double elevatorRotations;
        private boolean canElev;
        private boolean canClimb;
        private boolean canPivot;


        private ScoringStageVal(double elevRotation, boolean moveElev, boolean moveClimb, boolean movePivot)
        {
            this.elevatorRotations = elevRotation;
            this.canElev = moveElev;
            this.canClimb = moveClimb;
            this.canPivot = movePivot;
        }

        public double getElevatorRotations()
        {
            return this.elevatorRotations;
        }

        public boolean canElev()
        {
            return this.canElev;
        }

        public boolean canClimb()
        {
            return this.canClimb;
        }

        public boolean canPivot()
        {
            return this.canPivot;
        }
    }

    public static final class Elevator1Constants{
        public static final int id = 20;

        public static final boolean attached = true;

        public static final double kP = 3; 
        public static final double kS = 0; 
        public static final double kV = 0; 



        public static final double voltageOut = 0;
        public static final double positionUp = 90; //change this
        public static final double positionDown = 7;

        public static final double tol = 0.4;
    }

    public static final class Elevator2Constants{
        public static final int id = 36;

        public static final boolean attached = true;

        public static final double kP = 3; 
        public static final double kS = 0; 
        public static final double kV = 0; 


    }

 

    public static final class ClimberConstants{
        public static final int id = 16;

        public static final boolean attached = true;

        public static final double kP = 1.9; 
        public static final double kS = 0; 
        public static final double kV = 0; 



        public static final double voltageOut = 0;
        public static final double positionUp = -320; //-240
        public static final double positionHold = -70; //-240

        public static final double positionDown = 0;

    }


    public static final class elevatorMMConstants{
        public static final double acceleration = 50;
        public static final double speed = 100;
        public static final double jerk = 0;

    }



    public static final class elevatorBeambreakConstants {
        public static boolean breakAttached = false;
        public static final String beamBreakName = "elevatorBeambreak";
        public static final int beamBreakChannel = 2;

    }

    public static final class FunnelPivotConstants {
        public static final int id = 30;
        public static final boolean attached = false;


        public static final double kP = 1.9; 
        public static final double kS = 0; 
        public static final double kV = 0; 

        public static final double posUp = 55; //needs to be tested
        public static final double posDown = 0; //needs to be tested
        
    }
    
    public static final class IntakeFlywheelsConstants{
        public static final int id = 7;

        public static final boolean attached = true;

        public static final double kP = 5; 
        public static final double kS = 0; 
        public static final double kV = 0; 


        // public static final double voltageOut = 2;
        // public static final double position = 0;
    }


    public static final class IntakeBeambreakConstants {
        public static final boolean breakAttached = true;
        public static final String beamBreakName = "intake_beambreak";
        public static final int beamBreakChannel = 0;

    }

    public static boolean isWithinTol(double targetPose, double currentPose, double tolerance) {
        return (Math.abs(targetPose - currentPose) <= tolerance);
    }
    public class ScoringConstants
    {
        public static ScoringStageVal ScoringStage = ScoringStageVal.INTAKEREADY;
    }
    
}
