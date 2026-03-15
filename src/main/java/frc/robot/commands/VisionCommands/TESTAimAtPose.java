package frc.robot.commands.VisionCommands;

import java.lang.annotation.Documented;
import java.util.Optional;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.ExponentialProfile.Constraints;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.Constants.LimelightConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Vision;

public class TESTAimAtPose extends Command{
    private CommandSwerveDrivetrain swerve;
    private Translation2d target = new Translation2d(fieldLength/4.25, 4.105);

    private DoubleSupplier xSpeed;
    private DoubleSupplier ySpeed;


    public static final double fieldLength = 16.54;

    //this aim relies on the robot knowing its position based on the limelight, make sure its tuned well before using
    public TESTAimAtPose(CommandSwerveDrivetrain swerve, DoubleSupplier xSpeed, DoubleSupplier ySpeed) {
        this.swerve = swerve;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;

        addRequirements(swerve);

    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        Translation2d newTarget = getAllianceAdjustedPoint(target);
        swerve.aimAtPoint(newTarget, xSpeed.getAsDouble(), ySpeed.getAsDouble());
    }

    @Override
    public void end(boolean interrupted) {
        swerve.setControl(new SwerveRequest.Idle());
    }

    @Override
    public boolean isFinished() {
        return false;
    }



    //change target depending on alliance
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
