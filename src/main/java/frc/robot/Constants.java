package frc.robot;


//TODO: start putting contants in here so your not wasting time chasing them down in the subsystems
public final class Constants {

    public static final class MotorConstants {

        //this should be all the motor id's for the motors in the subsystems

    }

    public static final class SetpointConstants {
        public static final double intakeGroundSetpoint = 50; //this would be read in encoder ticks, call kaden for how to find this number
        public static final double intakeStoreSetpoint = 0; //bring it to the initial setpoint
    }
    
    public static final class ShooterConstants {
        public static final double targetRPM = 54;
    }
}
