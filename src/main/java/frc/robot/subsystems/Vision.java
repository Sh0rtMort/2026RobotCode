package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.Constants.LimelightConstants;

public class Vision extends SubsystemBase{

    private String limelightName = Constants.LimelightConstants.limelightName;

    public Vision() {

        NetworkTable table = NetworkTableInstance.getDefault().getTable(limelightName);
        /* this sets the necessary tunings so the limelight gives an accurate pose
        TODO: do this to all parameters in the limelight constants
        -Height
        -Pitch
        -X and Y Offsets
        -Yaw
        IMPORTANT: confirm that the network key is the same as the pipeline name
        */
        // I cant stress this enough; if you can get this working you gain an advantage only worlds level teams have
        table.getEntry("camHeight").setDouble(LimelightConstants.limelightHeight);
        table.getEntry("camPitch").setDouble(LimelightConstants.limelightPitchOffset); //is the limelight tilted up or down?
        table.getEntry("camYaw").setDouble(LimelightConstants.limelightYaw); //if aiming forward, then its 0
        table.getEntry("camX").setDouble(LimelightConstants.xOffset); //how far left or right the limelight is placed on the robot
        table.getEntry("camY").setDouble(LimelightConstants.yOffset); //how far front or back the limelight is placed on the robot
        table.getEntry("pipeline").setNumber(1); //use pipeline one as displayed in the limelight app
        table.getEntry("ledMode").setNumber(0);
    }

     public void setLEDmode(int ledMode) {
        // switch(ledMode) {
        //     case 0:
        //         LimelightHelpers.setLEDMode_ForceOff(limelightName);
        //     break;
        //     case 1:
        //         LimelightHelpers.setLEDMode_ForceOn(limelightName);
        //     break;
        //     case 2:
        //         LimelightHelpers.setLEDMode_ForceBlink(limelightName);
        //     break;
        //     default: LimelightHelpers.setLEDMode_ForceOff(limelightName);
        // }
        //or...
        //I prefer this one as its simplier and less redundant
        switch (ledMode) {
            case 0 -> LimelightHelpers.setLEDMode_ForceOff(limelightName);
            case 1 -> LimelightHelpers.setLEDMode_ForceOn(limelightName);
            case 2 -> LimelightHelpers.setLEDMode_ForceBlink(limelightName);
            default -> LimelightHelpers.setLEDMode_ForceOff(limelightName);
        }
    }

    //this command allows to swap between pipelines ran in the limelight
    //I.E. retoflective, full color for driving, or aprilTag Tracking pipelines
    public void setPipelineIndex(int index) {
        LimelightHelpers.setPipelineIndex(limelightName, index);
    }

    public double getTXValue() {
        return LimelightHelpers.getTX(limelightName);
    }

    public double getTYValue() {
        return LimelightHelpers.getTY(limelightName);
    }

    public double getTAValue() {
        return LimelightHelpers.getTA(limelightName);
    }

    public boolean hasTarget() {
        return LimelightHelpers.getTV(limelightName);
    }

    public int getVisibleTagID() {
        return (int) NetworkTableInstance.getDefault()
            .getTable("limelight")
            .getEntry("tid")
            .getDouble(-1);
    }

}
