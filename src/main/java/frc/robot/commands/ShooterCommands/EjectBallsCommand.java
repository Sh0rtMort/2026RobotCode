package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Constants;
import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.SorterConstants;
import frc.robot.subsystems.ShooterFeeder;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.sorter;

public class EjectBallsCommand extends ParallelCommandGroup{

    public EjectBallsCommand(intake intake, ShooterFeeder feeder, sorter sorter) {
        addCommands(
            new RunCommand(() -> intake.runRollerVoltage(IntakeConstants.ejectionVoltage)),
            new RunCommand(() -> feeder.runFeederVoltage(FeederConstants.feederEjectVolts)),
            new RunCommand(() -> sorter.runSorterVolts(SorterConstants.sorterEjectVolts))
        );
        
    }
    
}
