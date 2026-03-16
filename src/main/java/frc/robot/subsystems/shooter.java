package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class shooter extends SubsystemBase {

    private static final int SHOOTER_MOTOR_ID = 24;

    private final TalonFX shooterMotor = new TalonFX(SHOOTER_MOTOR_ID);
    private final VelocityVoltage velocityRequest = new VelocityVoltage(0).withSlot(0);

    public shooter() {
        TalonFXConfiguration config = new TalonFXConfiguration();

        //TODO: THIS IS WHAT IS KILLING YOUR BATTERY AND SHOOTING CHANGE THIS
        // NEVER EVER SET A SHOOTER MOTOR TO BRAKING
        // config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;

        config.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = 2.0;

        // Velocity PID — tune kP if wheel bogs down under load
        Slot0Configs slot0 = config.Slot0;
        slot0.kP = 0.6;  // increase if wheel slows down when ball hits
        slot0.kV = 0.10; // feedforward — helps reach target speed faster

        shooterMotor.getConfigurator().apply(config);
    }

    // Returns a command that runs the shooter while active and stops it when ended
    // NOTE: this method is not currently called anywhere in RobotContainer or commands
    @Deprecated
    public Command runShooter() {
        return runEnd(
            () -> runShooterMotor(),
            () -> stopShooter()
        );
    }

    // Spins the shooter wheel at 54 RPS using closed-loop velocity control to launch game pieces
    //honestly this is fine but there should be a way to run a selected velocity to help with later usage
    @Deprecated
    public void runShooterMotor() {
        shooterMotor.setControl(velocityRequest.withVelocity(54));
    }

    public void runShooterMotorPass(double RPS) {
        shooterMotor.setControl(velocityRequest.withVelocity(RPS));
    }

    public double getShooterSpeed() {
        return shooterMotor.getVelocity().getValueAsDouble();
    }

    // Stops the shooter motor
    public void stopShooter() {
        // shooterMotor.set(0);
        shooterMotor.stopMotor();
    }

    public boolean isShooterAtSpeed(double target) {
        double allowedError = 5; //allowed to be off by 5 rps and still fire
        return Math.abs(getShooterSpeed() - target) < allowedError;
    }

}