package frc.robot;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.None;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.Measure;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Rotations;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Constants {

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

        public static final boolean attached = false;

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
        public static final int beamBreakChannel = 0;

    }


    public static boolean isWithinTol(double targetPose, double currentPose, double tolerance) {
        return (Math.abs(targetPose - currentPose) <= tolerance);
    }
    
}
