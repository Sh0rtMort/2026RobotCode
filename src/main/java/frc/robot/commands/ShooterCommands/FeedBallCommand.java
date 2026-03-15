package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.sorter;
import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.SorterConstants;
import frc.robot.subsystems.ShooterFeeder;

/**
 * Runs the sorter and feeder together until the command ends or times out.
 * Use: "FeedBall" in PathPlanner (add a timeout in the GUI, e.g. 1s).
 */
public class FeedBallCommand extends StartEndCommand {
    public FeedBallCommand(sorter sorterSubsystem, ShooterFeeder feederSubsystem) {
        
        super(
            () -> {
                sorterSubsystem.runSorterVolts(SorterConstants.sorterInVolts);
                feederSubsystem.runFeederVoltage(FeederConstants.feederInVolts);
            },
            () -> {
                sorterSubsystem.stop();
                feederSubsystem.stop();
            },
            sorterSubsystem, feederSubsystem
        );
    }
}
