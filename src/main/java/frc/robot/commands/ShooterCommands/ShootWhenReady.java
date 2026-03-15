package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterFeeder;
import frc.robot.subsystems.shooter;
import frc.robot.subsystems.sorter;

public class ShootWhenReady extends Command{

    private shooter shooter;
    private sorter sorter;
    private ShooterFeeder feeder;

    public ShootWhenReady(shooter shooter, sorter sorter, ShooterFeeder feeder) {
        this.shooter = shooter;
        this.sorter = sorter;
        this.feeder = feeder;
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {

        //this theoretically spins the motor to the speed then waits for the motor to be there before firing
        shooter.runShooterMotorPass(Constants.ShooterConstants.targetRPS);
        SmartDashboard.putNumber("Shooter RPS", shooter.getShooterSpeed());
        
        if (shooter.isShooterAtSpeed(Constants.ShooterConstants.targetRPS)) {
            shooter.runShooterMotorPass(Constants.ShooterConstants.targetRPS);
            new FeedBallCommand(sorter, feeder);
        } else {
            shooter.runShooterMotorPass(Constants.ShooterConstants.targetRPS);
        }
        SmartDashboard.putBoolean("Shooting When Ready?", true);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stopShooter();
        sorter.stop();
        feeder.stop();
        SmartDashboard.putBoolean("Shooting When Ready?", false);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}
