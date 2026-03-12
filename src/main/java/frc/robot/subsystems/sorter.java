package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class sorter extends SubsystemBase {

    //put this into the constants
    private final TalonFX sorterMotor = new TalonFX(32);

    // Runs the sorter motor at -70% to move game pieces toward the feeder/shooter
    // NOTE: not fully certain if -0.7 is toward shooter or away, depends on motor orientation
    @Deprecated
    public void runSorterMotor() {
        sorterMotor.set(-0.7);
    }

    // Runs the sorter motor in reverse at 70% to eject/unjam game pieces
    @Deprecated
    public void runSorterMotorReverse() {
        sorterMotor.set(0.7);
    }

    // Stops the sorter motor
    public void stop() {
        // sorterMotor.set(0);
        sorterMotor.stopMotor();
    }

    public void runSorterVolts(double volts) {
        sorterMotor.setVoltage(volts);
    }
}