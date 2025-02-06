package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

// This TeleOp mode is for the Viperslide and claw controls
@Disabled
@TeleOp(name="ArmControls", group="Linear OpMode")
public class PlayerControl2 extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor VSMotor = null;

    private Servo ClawRotServo = null;
    private Servo ClawGrabServo = null;
    private DcMotor frontLeft = null;
    private DcMotor backLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backRight = null;
    
    private double rotationPosition = 0;
    private double grabPosition = 0;

    @Override
    public void runOpMode() {
        // Initializing the motor direction and names
        VSMotor = hardwareMap.get(DcMotor.class, "VSMotor");
        ClawRotServo = hardwareMap.get(Servo.class, "ClawRotator");
        ClawGrabServo = hardwareMap.get(Servo.class, "ClawGrip");
        
        VSMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        ClawRotServo.setDirection(Servo.Direction.FORWARD);
        ClawGrabServo.setDirection(Servo.Direction.FORWARD);

        grabPosition = 0;
        rotationPosition = 0;

        ClawRotServo.setPosition(rotationPosition);
        ClawGrabServo.setPosition(grabPosition);

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();
/*
        int firstArmPos = 0;
        int secondArmPos = 0;
        double firstArmPower = 0;
        double secondArmPower = 0;
        boolean secondArmYes = false;
*/
        double lowPower = 0.8;
        // run until the end of the match (driver presses STOP)

        while (opModeIsActive()) {
            double max;

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double vertical = gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value

            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.
            max = Math.max(Math.abs(vertical), 100);

            if (max > 1.0) {
                vertical /= max;
            }
            // This is test code:
            //
            // Uncomment the following code to test your motor directions.
            // Each button should make the corresponding motor run FORWARD.
            //   1) First get all the motors to take to correct positions on the robot
            //      by adjusting your Robot Configuration if necessary.
            //   2) Then make sure they run in the correct direction by modifying the
            //      the setDirection() calls above.
            // Once the correct motors move in the correct direction re-comment this code.

/*
            frontLeftPower  = gamepad1.x ? 1.0 : 0.0;  // X gamepad1
            backLeftPower   = gamepad1.a ? 1.0 : 0.0;  // A gamepad1
            frontRightPower = gamepad1.y ? 1.0 : 0.0;  // Y gamepad1
            backRightPower  = gamepad1.b ? 1.0 : 0.0;  // B gamepad1
            firstArmPower = gamepad2.left_bumper ? 1.0 : 0.0; // LeftBumper gamepad2
            firstArmPower = gamepad2.right_bumper ? -1.0 : 0.0; // RightBumper gamepad2
*/
            if (gamepad1.a) {
                lowPower = 0.8;
            } else if (gamepad1.x) {
                lowPower = 0.4;
            } else if (gamepad1.b) {
                lowPower = 0.6;
            } else if (gamepad1.y) {
                lowPower = 0.2;
            }
            vertical *= lowPower;

            if (gamepad1.right_bumper){
                grabPosition = 0.5;
            }
            else if (gamepad1.left_bumper){
                grabPosition = 0;
            }

            rotationPosition += gamepad1.right_trigger/2;
            rotationPosition -= gamepad1.left_trigger/2;

            if (rotationPosition > 0.5){
                rotationPosition = 0.5;
            }
            if(rotationPosition < 0.1){
                rotationPosition = 0.1;
            }

            /*
            if(grabPosition > 0.5) {
                grabPosition = 0.5;
            }
            if (grabPosition < 0){
                grabPosition = 0;
            }
            */

            /*if (gamepad1.left_trigger == 0 && gamepad1.right_trigger == 0 && gamepad2.left_trigger == 0 && gamepad2.right_trigger == 0)
            {
                waitSecondArm(0);
                waitFirstArm(0);
                firstArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                secondArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                waitFirstArm(500);
                waitSecondArm(500);
            }*/

            // Send calculated power to the motor and servos
            VSMotor.setPower(vertical);

            ClawRotServo.setPosition(rotationPosition);
            ClawGrabServo.setPosition(grabPosition);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("ViperSlide", "%4.2f", vertical);
            telemetry.addData("Rotation", "%4.2f", rotationPosition);
            telemetry.addData("Grab", "%4.2f", grabPosition);
            telemetry.update();
        }
    }
}
