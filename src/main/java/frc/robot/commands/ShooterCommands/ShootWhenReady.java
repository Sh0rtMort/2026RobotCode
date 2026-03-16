package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.SorterConstants;
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

        double target = Constants.ShooterConstants.targetRPS;

        shooter.runShooterMotorPass(target);

        SmartDashboard.putNumber("Shooter RPS", shooter.getShooterSpeed());

        if (shooter.isShooterAtSpeed(target)) {
            sorter.runSorterVolts(SorterConstants.sorterInVolts);
            feeder.runFeederVoltage(FeederConstants.feederInVolts);
        } else {
            sorter.stop();
            feeder.stop();
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
