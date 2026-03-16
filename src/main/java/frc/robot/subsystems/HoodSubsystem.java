package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HoodSubsystem extends SubsystemBase{
    private TalonFX hoodMotor = new TalonFX(35); //change to be actual ID

    public HoodSubsystem() {
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        // config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive; //uncomment whichever is the right one
        // config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

        hoodMotor.getConfigurator().apply(config);
    }

    public void setAngle(double angDeg) {
        double rotations = angDeg / 360.0;

        hoodMotor.setControl(new PositionVoltage(rotations));
    }


    @Override
    public void periodic() {
        
    }
}
