package frc.robot.generated;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import frc.robot.Constants.ShooterConstants;

public class HoodTable {

    static InterpolatingDoubleTreeMap hoodTable = new InterpolatingDoubleTreeMap();

    static {
        /* change these to match what you need them to be. Example to follow
        KEY = this is the limelight distance to the target, in meters
        VALUE = this is the degrees of the hood that make it work
        */
        hoodTable.put(1.5, 10.0); 
        hoodTable.put(2.0, 15.0);
        hoodTable.put(2.5, 20.0);
        hoodTable.put(3.0, 25.0);
        hoodTable.put(3.5, 30.0);

    }

    //without the min/max angles
    // public static double getAngle(double distance){
    //     return hoodTable.get(distance);
    // }

    public static double getAngle(double distance){

    if(distance < ShooterConstants.minShootingDistance ||
       distance > ShooterConstants.maxShootingDistance ||
       Double.isNaN(distance))
    {
        // return ShooterConstants.defaultHoodAngle;
        distance = MathUtil.clamp(distance, ShooterConstants.minShootingDistance, ShooterConstants.maxShootingDistance);
    }
    return hoodTable.get(distance);
}
    
}
