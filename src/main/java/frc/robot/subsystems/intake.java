package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//TODO: make setpoint code and constant velocities and such

public class intake extends SubsystemBase {

    private final TalonFX pivotMotor = new TalonFX(22);
    private final TalonFX rollerMotor = new TalonFX(31);
    private final PositionVoltage positionRequest = new PositionVoltage(0);

    public intake() {
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        // PID for holding position — increase kP if it's not holding strong enough
        Slot0Configs slot0 = config.Slot0;
        slot0.kP = 4.0;  // ← increase this if still not holding
        slot0.kD = 0.1;

        pivotMotor.getConfigurator().apply(config);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Pivot Position", getPivotPosition());
    }

    // Runs the pivot motor at -15% to move the intake arm upward
    public void pivotToUp() {
        pivotMotor.set(-0.15);
    }

    // Runs the pivot motor at +15% to move the intake arm downward
    public void pivotToDown() {
        pivotMotor.set(0.15);
    }

    // Stops the pivot motor (arm stays in place due to coast mode, may drift)
    // coast mode should not be making it stay in the same place, but the opposite
    public void stopPivot() {
        // pivotMotor.set(0);
        pivotMotor.stopMotor();
    }

    // Locks the pivot arm at its current position using closed-loop PID control
    //this is something a PID controller does automatically so there is no reason to do this yourself
    @Deprecated
    public void lockPosition() {
        double currentPosition = pivotMotor.getPosition().getValueAsDouble();
        pivotMotor.setControl(positionRequest.withPosition(currentPosition));
    }

    // Releases position hold, returning the pivot motor to open-loop (coast/free)
    // this is actually not how this works in the slightest
    @Deprecated
    public void unlockPosition() {
        // pivotMotor.set(0);
        pivotMotor.stopMotor();
    }

    // Spins the intake roller at full speed (reverse direction) to pull game pieces in
    // NOTE: not fully certain if -1 is intake or outtake direction, depends on motor orientation
    //TODO: stuff like this is fine for debugging but should never be used for actual matches
    public void runRollerMotor() {
        rollerMotor.set(-1);
    }

    // Stops the intake roller motor
    public void stopRoller() {
        // rollerMotor.set(0);
        rollerMotor.stopMotor();
    }

    // Returns the current pivot motor encoder position (in rotations)
    public double getPivotPosition() {
        return pivotMotor.getPosition().getValueAsDouble();
    }




    //Kadens coding section


    public void zeroPivot() {
        pivotMotor.setPosition(0); //zero as a double not an angle
    }

    public void setPivotVolatage(double volts) {
        pivotMotor.setVoltage(volts);
    }

    public void setPivotSpeedPass(double speed) {
        pivotMotor.set(speed);
    }

    public void runRollerVoltage(double volts) {
        rollerMotor.setVoltage(volts);
    }
}