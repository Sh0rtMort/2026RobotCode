# **2026 Code for Rebuilt - Revamped**
>[!WARNING]
>***BELOW WILL WHAT NEEDS TO BE TUNED TO WORK, DO NOT ASSUME THE VALUES I USE ARE THE CORRECT ONES***

## Controller Bindings for Driver

## DRIVER CONTROLLER BINDINGS
        A = Intake to ground command
        
        X = Intake store command
        
        B = Eject balls command

        Y = Auto Hood Adjustment, No Robot Rotation (Apriltag based)
        
        Left Trigger = run roller commands, not really needed
        
        Left Trigger = Swerve slow mode
        
        Right trigger = Shoot command, no rps safe guards
        
        Left D-Pad = Shoot command, rps safe guard
        
        Up D-Pad = Feed balls command
        
        Right Bumper = Auto Aim (Apriltag Based)

        Left Bumper = Auto Hood Adjustment, Shooting, and rotation (Position Based)
        
## OPERATOR CONTROLLER BINDINGS

        Y = Intake to ground command
        
        B = Eject balls command
        
        A = Feed balls command
        
        X = Agitate intake command

>[!IMPORTANT]
>## Here is what needs to be tuned
>### There might be some additional items, so please make sure to double check all subsystems and constants

### Vision Subsystem
- Limelight Height: From ground to center of limelight, in meters
- Limelight Pitch: Angle of limelight from vertical axis, in degrees
- Limelight X and Y offset: Position of limelight from Forward/Back(Y), and Left/Right(X); in relation to robot frame
- Limelight Yaw: Angle the limelight is facing left or right, 0 degrees is forward

### Shooter Subsystem
- Target RPS: The optimal velocity for the shooter in most positions
- Default Hood Angle: Angle in which the hood can score from the hub and where the hood will rest when not shooting
- Hood Angle Table(Keys and Values): the distance and the corresponding angle of the hood to score from that distance: to find this, measure the robot at different distances and find the angle of the hood that can score at that point, record both values in the HoodTable File.
- Min. and Max. Shooting Distance: the minimum and maximum distance, in meters, that the shooter can score from

### Intake Subsystem
- Intake Ground Setpoints: The setpoint in which the intake will move to when picking up balls from the ground, use pheonix tuner and smartDashboard to move intake down and record setpoint.
- Intake PIDController P,I,D: look up a tuning video to tune these to reach the desired setpoint of the intake
- Intake Voltages: Adjust voltages to become optimal
- [!NOTE] Adjust the direction of the motor to assure that the positive moving direction is outward

### Sorter and Feeder Subsystems
- Intake and Eject Voltages(Sorter and Feeder): Find optimal values and apply to Constants file

### Swerve Drivetrain
- Auto Aim PIDs: Adjust PIDs so that the robot aims at the correct position on the field, this applies to both aiming based on Pose2D and Apriltag varients
- Pathplanner Holometric Drive PIDs: These are what make the pathing work, make sure these are accuretly tunes

### Pathplanner
- Kinematics: Make sure that the robots Weight, Moment of Inertia, Dimensions, Max Velocity, Acceleration, and other variables are properly initialized in the Pathplanner app.
>[!NOTE]
>With the vast changes in the code, you will need to remake the pathplanner commands and autos. Respectuflly, they didnt work before and this allows for afresh start.

>[!NOTE]
>If you have questions, reach out to me. I'll try my best to respond in a timely manner but im preparing for Nationals and Junior Olympics so I will be busier than normal.

![USMMA Crest](https://upload.wikimedia.org/wikipedia/commons/thumb/6/62/United_States_Merchant_Marine_Academy_seal.svg/330px-United_States_Merchant_Marine_Academy_seal.svg.png)
