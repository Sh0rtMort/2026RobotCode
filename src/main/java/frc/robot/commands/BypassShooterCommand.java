package frc.robot.commands;

import java.lang.constant.Constable;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterFeeder;
import frc.robot.subsystems.shooter;
import frc.robot.subsystems.sorter;

public class BypassShooterCommand extends Command{

    private shooter shooter;
    private sorter sorter;
    private ShooterFeeder feeder;

    public BypassShooterCommand(shooter shooter, sorter sorter, ShooterFeeder feeder) {
        this.shooter = shooter;
        this.sorter = sorter;
        this.feeder = feeder;
    }
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        new SequentialCommandGroup(
        new RunCommand(() -> shooter.runShooterMotorPass(Constants.ShooterConstants.targetRPS)).withTimeout(1),
        new FeedBallCommand(sorter, feeder)
        );
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stopShooter();
        sorter.stop();
        feeder.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
