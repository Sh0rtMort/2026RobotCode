package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.lang.annotation.Documented;
import java.util.Optional;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import frc.robot.LimelightHelpers;
import frc.robot.Constants.SetpointConstants;
import frc.robot.commands.IntakeCommands.AgitateCommand;
import frc.robot.commands.IntakeCommands.IntakeDownCommand;
import frc.robot.commands.IntakeCommands.IntakePivotCommand;
import frc.robot.commands.IntakeCommands.IntakeUpCommand;
import frc.robot.commands.IntakeCommands.RunIntakeCommand;
import frc.robot.commands.ShooterCommands.BypassShooterCommand;
import frc.robot.commands.ShooterCommands.EjectBallsCommand;
import frc.robot.commands.ShooterCommands.FeedBallCommand;
import frc.robot.commands.ShooterCommands.ShootWhenReady;
import frc.robot.commands.ShooterCommands.SpinShooterCommand;
import frc.robot.commands.VisionCommands.AutoAim;
import frc.robot.commands.VisionCommands.AutoAlignCommand;
import frc.robot.commands.VisionCommands.TESTAimAtPose;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.sorter;
import frc.robot.subsystems.ShooterFeeder;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.shooter;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {

    private final SendableChooser<Command> autoChooser;

    private final intake intakeSubsystem = new intake();
    private final sorter sorterSubsystem = new sorter();
    private final ShooterFeeder feederSubsystem = new ShooterFeeder();
    private final shooter shooterSubsystem = new shooter();
    private final Vision vision = new Vision();

    private double MaxSpeed = 0.75 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); //changed from 1.0 to 0.75
    private double MaxAngularRate = RotationsPerSecond.of(1.25).in(RadiansPerSecond);

    private final CommandXboxController driverController = new CommandXboxController(0);
    private final CommandXboxController secondController = new CommandXboxController(1);

    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
        .withDeadband(MaxSpeed * 0.1)
        .withRotationalDeadband(MaxAngularRate * 0.1)
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    private final SwerveRequest.FieldCentric limelightDrive = new SwerveRequest.FieldCentric()
        .withDeadband(MaxSpeed * 0.1)
        .withRotationalDeadband(0)
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();

    public final CommandSwerveDrivetrain drivetrain;

public RobotContainer() {

    // ✅ Initialize drivetrain FIRST
    drivetrain = TunerConstants.createDrivetrain();

    vision.configureLimelight();

    RobotController.setBrownoutVoltage(Constants.brownoutVoltage); //change this voltage number to what it should be

    // Then register named commands
    NamedCommands.registerCommand("Intake Down", new ParallelCommandGroup(
            new IntakePivotCommand(intakeSubsystem, Constants.SetpointConstants.intakeGroundSetpoint),
            new RunCommand(() -> intakeSubsystem.runRollerVoltage(Constants.IntakeConstants.intakeVolatge))
        )
    );

    NamedCommands.registerCommand("Intake Up", new ParallelCommandGroup(
            new IntakePivotCommand(intakeSubsystem, 0),
            new RunCommand(() -> intakeSubsystem.runRollerVoltage(0))
        )
    );

    NamedCommands.registerCommand("No Calculator Shooting", new BypassShooterCommand(shooterSubsystem, sorterSubsystem, feederSubsystem));

    // NamedCommands.registerCommand("SpinShooterSai",
    //     new SpinShooterCommand(shooterSubsystem).withTimeout(1.5));

    // NamedCommands.registerCommand("FeedBallSai",
    //     new FeedBallCommand(sorterSubsystem, feederSubsystem).withTimeout(1.0));

    NamedCommands.registerCommand("better shooting command", new ShootWhenReady(shooterSubsystem, sorterSubsystem, feederSubsystem));
    NamedCommands.registerCommand("auto Aim", new AutoAim(shooterSubsystem, drivetrain, vision));
    // NamedCommands.registerCommand("AutoAlignSai",
    //     new AutoAlignCommand(drivetrain).withTimeout(2.5));

        //path planner has a built in wait command you can use
    // NamedCommands.registerCommand("Wait1sSai", Commands.waitSeconds(1.0));
    // NamedCommands.registerCommand("Wait2sSai", Commands.waitSeconds(2.0));
    // NamedCommands.registerCommand("Wait3sSai", Commands.waitSeconds(3.0));
    // NamedCommands.registerCommand("Wait4sSai", Commands.waitSeconds(4.0));


    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);
    SmartDashboard.putData("Field", drivetrain.getField());

    configureBindings();
}

    // Maps controller buttons/triggers to robot actions (drive, intake, shooter, etc.)
    private void configureBindings() {

        /* SECOND CONTROLLER BINDINGS
        Y = Intake to ground command
        B = Eject balls command
        A = Feed balls command
        X = Agitate intake command
        */

        secondController.y().onTrue(
            new ParallelCommandGroup(
                new IntakePivotCommand(intakeSubsystem, Constants.SetpointConstants.intakeGroundSetpoint),
                new RunCommand(() -> intakeSubsystem.runRollerVoltage(Constants.IntakeConstants.intakeVolatge)))
        );

        secondController.b().whileTrue(
            new EjectBallsCommand(intakeSubsystem, feederSubsystem, sorterSubsystem)
        );

        secondController.a().whileTrue(
            new FeedBallCommand(sorterSubsystem, feederSubsystem)
        );

        secondController.x().whileTrue(
            new AgitateCommand(intakeSubsystem)
        );

        /* DRIVER CONTROLLER BINDINGS
        A = Intake to ground command
        X = Intake store command
        B = Eject balls command
        Left Trigger = run roller commands, not really needed
        Left Trigger = Swerve slow mode
        Right trigger = Shoot command, no rps safe guards
        Left D-Pad = Shoot command, rps safe guard
        Up D-Pad = Feed balls command
        Right Bumper = Auto Aim
        */

        driverController.a().onTrue(
            new ParallelCommandGroup(
                new IntakePivotCommand(intakeSubsystem, Constants.SetpointConstants.intakeGroundSetpoint),
                new RunCommand(() -> intakeSubsystem.runRollerVoltage(Constants.IntakeConstants.intakeVolatge)),
                new InstantCommand(() -> SmartDashboard.putNumber("New Intake Tarket", SetpointConstants.intakeGroundSetpoint)),
                new RunCommand(() -> SmartDashboard.putBoolean("Intake Rollers Running?", true))
            )
        );
       
        driverController.x().onTrue(
            new ParallelCommandGroup(
                new IntakePivotCommand(intakeSubsystem, SetpointConstants.intakeStoreSetpoint),
                new RunCommand(() -> intakeSubsystem.runRollerVoltage(0)),
                new InstantCommand(() -> SmartDashboard.putNumber("New Intake Tarket", SetpointConstants.intakeStoreSetpoint)),
                new RunCommand(() -> SmartDashboard.putBoolean("Intake Rollers Running?", false))
            ).withTimeout(2) //this timeout allows the intake to go loosey-goosey after being at the setpoint for some time to conserve battery
        );
        
        driverController.b().whileTrue(
            new ParallelCommandGroup(
            new EjectBallsCommand(intakeSubsystem, feederSubsystem, sorterSubsystem),
            new RunCommand(() -> SmartDashboard.putBoolean("Ejecting Balls?", true))
            )
        );

        // Left trigger also runs rollers (same button, both happen simultaneously)
        driverController.leftTrigger().whileTrue(
            new RunCommand(() -> intakeSubsystem.runRollerMotor(), intakeSubsystem)
        );

        // RIGHT TRIGGER = SHOOTER
        driverController.rightTrigger().whileTrue(
            // new RunCommand(() -> shooterSubsystem.runShooterMotorPass(Constants.ShooterConstants.targetRPS), shooterSubsystem)
            new BypassShooterCommand(shooterSubsystem, sorterSubsystem, feederSubsystem)
        );

        driverController.povLeft().whileTrue(
            new ShootWhenReady(shooterSubsystem, sorterSubsystem, feederSubsystem)
        );

          driverController.rightBumper().whileTrue(
            new AutoAim(shooterSubsystem, drivetrain, vision)  
        );

        driverController.povUp().whileTrue(
            new ParallelCommandGroup(
            new FeedBallCommand(sorterSubsystem, feederSubsystem),
            new RunCommand(() -> SmartDashboard.putBoolean("Feeding Balls?", true))
            )
        );

        //holy fuck this actually works
        driverController.povRight().whileTrue(
            new TESTAimAtPose(drivetrain,
             () -> -driverController.getLeftY() * MaxSpeed * 0.8,
             () -> -driverController.getLeftX() * MaxSpeed * 0.8
            ) 
        );

        // Default drive with slow mode on left trigger
        drivetrain.setDefaultCommand(
            drivetrain.applyRequest(() -> {
                double speedMult = driverController.leftTrigger().getAsBoolean() ? 0.2 : 0.6; //changed from 1.0 to 0.6
                return drive
                    .withVelocityX(-driverController.getLeftY() * MaxSpeed * speedMult)
                    .withVelocityY(-driverController.getLeftX() * MaxSpeed * speedMult)
                    .withRotationalRate(-driverController.getRightX() * MaxAngularRate * speedMult);
            })
        );

        feederSubsystem.setDefaultCommand(feederSubsystem.run(() -> feederSubsystem.stop()));
        shooterSubsystem.setDefaultCommand(shooterSubsystem.run(() -> shooterSubsystem.stopShooter()));
        sorterSubsystem.setDefaultCommand(sorterSubsystem.run(() -> sorterSubsystem.stop()));
        intakeSubsystem.setDefaultCommand(intakeSubsystem.run(() -> intakeSubsystem.stopRoller()));

    }

    // Returns the autonomous command selected from the SmartDashboard auto chooser dropdown
    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}