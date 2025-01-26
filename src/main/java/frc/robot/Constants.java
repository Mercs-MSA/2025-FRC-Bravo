package frc.robot;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.numbers.N3;
import frc.robot.commands.DriveToPosCommands.CommandToPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constants {

    public enum ScoringStageVal {
        INTAKEREADY(0, true, false, true),
        INTAKING(0, false, false, false),
        L1(21, true, false, false),
        L2(38, true, false, false),
        L3(63, true, false, false),
        L4(101, true, false, false),
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
        public static final double positionUp = 320; //-240

        public static final double positionDown = 0;

        public static final double climberTol = 1;

    }


    public static final class elevatorMMConstants{
        public static final double acceleration = 100;
        public static final double speed = 150;
        public static final double jerk = 0;

    }



    public static final class elevatorBeambreakConstants {
        public static boolean breakAttached = false;
        public static final String beamBreakName = "elevatorBeambreak";
        public static final int beamBreakChannel = 2;

    }

    public static final class FunnelPivotConstants {
        public static final int id = 33;
        public static final boolean attached = true;


        public static final double kP = 1.9; 
        public static final double kS = 0; 
        public static final double kV = 0; 

        public static final double posUp = 57; //needs to be tested
        public static final double posDown = 0; //needs to be tested
        
    }
    
    public static final class IntakeFlywheelsConstants{
        public static final int id = 32;

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

    public static final class VisionConstants {
        public static final String limelightFrontName = "limelight-front";
        public static final String limelightBackName = "limelight-back";
        public static final Vector<N3> visionStdDevs = VecBuilder.fill(.7,.7,9999999);
    }

    public static final class FieldConstants {
        public static final double fieldLengthMeters = 16.54;
        public static final double fieldWidthMeters = 8.02;
    }

    public static final class DriveToPoseConstants {
        public static final double angularDegreesTolerance = 0.3;
        public static final double linearMetersTolerance = 0.05;
        public static final double linearMetersMaxVel = 2.0;
        public static final double linearMetersMaxAccel = 5.0;
        public static final HashMap<String, CommandToPos.Destination> positions = new HashMap<String, CommandToPos.Destination>() {{
            put("reefA", new CommandToPos.Destination("reefA", new Pose2d(3.276, 4.185, new Rotation2d(0))));
            put("reefB", new CommandToPos.Destination("reefB", new Pose2d(3.276, 3.79, new Rotation2d(0))));//
            put("reefC", new CommandToPos.Destination("reefC", new Pose2d(3.679, 2.958, new Rotation2d(1.047))));
            put("reefD", new CommandToPos.Destination("reefD", new Pose2d(3.961, 2.801, new Rotation2d(1.047))));
            put("reefE", new CommandToPos.Destination("reefE", new Pose2d(5.00, 2.789, new Rotation2d(2.0944))));
            put("reefF", new CommandToPos.Destination("reefF", new Pose2d(5.30, 2.970, new Rotation2d(2.0944))));
            put("reefG", new CommandToPos.Destination("reefG", new Pose2d(5.806, 3.858, new Rotation2d(3.1459))));
            put("reefH", new CommandToPos.Destination("reefH", new Pose2d(5.806, 4.190, new Rotation2d(3.1459))));
            put("reefI", new CommandToPos.Destination("reefI", new Pose2d(5.283, 5.096, new Rotation2d(-2.094))));
            put("reefJ", new CommandToPos.Destination("reefJ", new Pose2d(5.009, 5.259, new Rotation2d(-2.094))));
            put("reefK", new CommandToPos.Destination("reefK", new Pose2d(3.981, 5.253, new Rotation2d(-1.047))));
            put("reefL", new CommandToPos.Destination("reefL", new Pose2d(3.692, 5.092, new Rotation2d(-1.047))));
            put("Source", new CommandToPos.Destination("Source", new Pose2d(1.00, 7.2, new Rotation2d(-1.13))));
        }};

        public static final HashMap<String, List<String>> tagDestinationMap = new HashMap<String, List<String>>() {{
            put("18", List.of("reefA", "reefB"));
            put("17", List.of("reefC", "reefD"));
            put("19", List.of("reefK", "reefL"));
            put("21", List.of("reefH", "reefG"));
            put("20", List.of("reefI", "reefJ"));
            put("22", List.of("reefF", "reefE"));
        }};
    }

    public static boolean isWithinTol(double targetPose, double currentPose, double tolerance) {
        return (Math.abs(targetPose - currentPose) <= tolerance);
    }
    public class ScoringConstants
    {
        public static ScoringStageVal ScoringStage = ScoringStageVal.INTAKEREADY;
    }

    public class DriveToPosRuntime {
        public static String target = null;
        public static List<String> autoTargets = new ArrayList<String>(2) {{
            add("");
            add("");
        }};
    }
    
}
