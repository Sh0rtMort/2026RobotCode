package frc.robot.commands;

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

        //this theoretically spinns the motor to the speed then waits for the motor to be there before firing
        shooter.runShooterMotorPass(Constants.ShooterConstants.targetRPM);
        
        if (shooter.isShooterAtSpeed(Constants.ShooterConstants.targetRPM)) {
            shooter.runShooterMotorPass(Constants.ShooterConstants.targetRPM);
            new FeedBallCommand(sorter, feeder);
        } else {
            shooter.runShooterMotorPass(Constants.ShooterConstants.targetRPM);
        }
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stopShooter();
        sorter.stop();

    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}
