package frc.robot.commands;

import com.ctre.phoenix6.mechanisms.swerve.LegacySwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.shooter;

public class AutoAim extends Command{

    private shooter shooter;
    private CommandSwerveDrivetrain swerve;
    private Vision vision;

    private PIDController pidController = new PIDController(3, 0, 0);

    private double tolerance = 0.5; //example

    private final SwerveRequest.FieldCentric alignRequest = new SwerveRequest.FieldCentric()
    .withDeadband(0)
    .withRotationalDeadband(0);

    public AutoAim(shooter shooter, CommandSwerveDrivetrain swerve, Vision vision) {
        this.shooter = shooter;
        this.swerve = swerve;
        this.vision = vision;

        pidController.setTolerance(tolerance);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        double rotation = pidController.calculate(vision.getTXValue(), 0);

        swerve.setControl(alignRequest
        .withVelocityX(0)
        .withVelocityY(0)
        .withRotationalRate(rotation)
        );
    }

    @Override
    public void end(boolean interrupted) {
        swerve.setControl(alignRequest.withRotationalRate(0));
    }

    @Override
    public boolean isFinished() {
        return Math.abs(vision.getTXValue()) <= tolerance;
    }
    
}
