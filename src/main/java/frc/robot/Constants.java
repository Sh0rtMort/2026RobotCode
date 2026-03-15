package frc.robot;


//TODO: start putting contants in here so your not wasting time chasing them down in the subsystems
public final class Constants {

    public static final double brownoutVoltage = 6.3;

    public static final class MotorConstants {

        //this should be all the motor id's for the motors in the subsystems

    }

    public static final class SetpointConstants {
        public static final double intakeGroundSetpoint = 50; //this would be read in encoder ticks, call kaden for how to find this number
        public static final double intakeStoreSetpoint = 0; //bring it to the initial setpoint
    }
    
    public static final class ShooterConstants {
        public static final double targetRPS = 54; //tune this
    }

    public static final class LimelightConstants {
        public static final String limelightName = "limelight"; //change this in the pipeline so its not being effected at comps
        public static final double limelightHeight = 12; //this is in inches, change this to the real measurment
        public static final double limelightPitchOffset = 25; //this is how angles the camera is, change this to the real measurment

    }

    public static final class IntakeConstants {
        public static final double intakeVolatge = -3; //tune this
        public static final double ejectionVoltage = 3; //tune this
    }

    public static final class SorterConstants {
        public static final double sorterInVolts = -3; //tune this
        public static final double sorterEjectVolts = 3; //tune this
    }

    public static final class FeederConstants {
        public static final double feederInVolts = -3; //tune this
        public static final double feederEjectVolts = 3; //tune this
    }
}
