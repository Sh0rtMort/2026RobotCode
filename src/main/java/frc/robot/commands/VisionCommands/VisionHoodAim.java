package frc.robot.commands.VisionCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ShooterConstants;
import frc.robot.generated.HoodTable;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.shooter;

public class VisionHoodAim extends Command{

    private shooter shooter;
    private Vision vision;
    private HoodSubsystem hood;

    public VisionHoodAim(shooter shooter, Vision vision, HoodSubsystem hood) {
        this.shooter = shooter;
        this.vision = vision;
        this.hood = hood;

        addRequirements(shooter,hood);

    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {

        double distance = vision.hasTarget()
        ? Vision.getDistanceMeters()
        : Double.NaN;

        double angle = HoodTable.getAngle(distance);
        hood.setAngle(angle);
    }

    @Override
    public void end(boolean interrupted) {
        hood.setAngle(ShooterConstants.defaultHoodAngle);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}
