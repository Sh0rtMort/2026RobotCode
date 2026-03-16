package frc.robot.commands.VisionCommands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.generated.HoodTable;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.HoodSubsystem;

public class PositionHoodAim extends Command {

    private CommandSwerveDrivetrain swerve;
    private HoodSubsystem hood;

    // private double maxSpeed = 0.8;

    private static final double fieldLength = 16.54;
    private Translation2d target = new Translation2d(4.03, 4.105);


    public PositionHoodAim(CommandSwerveDrivetrain swerve, HoodSubsystem hood) {
        this.swerve = swerve;
        this.hood = hood;

        addRequirements(hood);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {

    // Pose2d robotPose = swerve.getState().Pose;
    // Translation2d goal = getAllianceAdjustedPoint(target);
    // double distance = robotPose.getTranslation().getDistance(goal);
    // double hoodAngle = HoodTable.getAngle(distance);

    // hood.setAngle(hoodAngle);

    Pose2d robotPose = swerve.getState().Pose;

    Translation2d goal = getAllianceAdjustedPoint(target);

    // robot velocity
    ChassisSpeeds speeds = swerve.getState().Speeds;

    Translation2d robotVelocity = new Translation2d(
        speeds.vxMetersPerSecond,
        speeds.vyMetersPerSecond
    );

    // normal distance
    double distance = robotPose.getTranslation().getDistance(goal);

    // estimate flight time
    double shotTime = distance / 18.0;

    // lead target calculation (STEP 6)
    Translation2d goalLead = goal.minus(robotVelocity.times(shotTime));

    // corrected distance
    double correctedDistance =
        robotPose.getTranslation().getDistance(goalLead);

    // lookup shooter values
    double hoodAngle = HoodTable.getAngle(correctedDistance);

    hood.setAngle(hoodAngle);

    SmartDashboard.putNumber("Hood Angle", hoodAngle);

    }

    @Override
    public void end(boolean interrupted) {
        hood.setAngle(ShooterConstants.defaultHoodAngle);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public static Translation2d getAllianceAdjustedPoint(Translation2d bluePoint) {

    var alliance = DriverStation.getAlliance();

    if (alliance.isPresent() && alliance.get() == Alliance.Red) {
        return new Translation2d(
            fieldLength - bluePoint.getX(),
            bluePoint.getY()
        );
    }

    return bluePoint;
}

    
}
