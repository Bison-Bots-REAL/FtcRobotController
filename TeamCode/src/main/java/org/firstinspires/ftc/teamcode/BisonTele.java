package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class BisonTele extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotorEx VSMotor = null;
    
    private DcMotor frontLeft = null;
    private DcMotor backLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backRight = null;

    private Servo ClawRotServo = null;
    private Servo ClawGrabServo = null;
    private double rotationPosition = 0;
    private double grabPosition = 0;

    private int pos = 0;

    @Override
    public void runOpMode() {
        // Initializing the motor direction and names
        VSMotor = hardwareMap.get(DcMotorEx.class, "VSMotor");
        ClawRotServo = hardwareMap.get(Servo.class, "ClawRotServo");
        ClawGrabServo = hardwareMap.get(Servo.class, "ClawGrabServo");
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        VSMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        ClawRotServo.setDirection(Servo.Direction.FORWARD);
        ClawGrabServo.setDirection(Servo.Direction.FORWARD);

        grabPosition = 0.6;
        rotationPosition = 0.28;

        ClawRotServo.setPosition(rotationPosition);
        ClawGrabServo.setPosition(grabPosition);
        
        VSMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        VSMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        VSMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
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
        double lowPower = 0.65;
        // run until the end of the match (driver presses STOP)

        while (opModeIsActive()) {
            double max;

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            //double vertical = gamepad2.left_stick_y;
            
            double axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            double lateral =  gamepad1.left_stick_x;
            double yaw     =  gamepad1.right_stick_x;
            
            double frontLeftPower  = axial + lateral + yaw;
            double frontRightPower = axial - lateral - yaw;
            double backLeftPower   = axial - lateral + yaw;
            double backRightPower  = axial + lateral - yaw;

            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.
            //max = Math.max(Math.abs(vertical), 100);
            
            max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
            max = Math.max(max, Math.abs(backLeftPower));
            max = Math.max(max, Math.abs(backRightPower));

            if (max > 1.0) {
                //vertical /= max;
                frontLeftPower  /= max;
                frontRightPower /= max;
                backLeftPower   /= max;
                backRightPower  /= max;
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
/*
            if (gamepad1.a) {
                lowPower = 1;
            } else if (gamepad1.x) {
                lowPower = 0.5;
            } else if (gamepad1.b) {
                lowPower = 0.75;
            } else if (gamepad1.y) {
                lowPower = 0.25;
            }
*/
            //vertical *= 0.5;

            if (gamepad2.dpad_up){
                grabPosition += 0.005; //lonk changed from 0.001
            }
            else if (gamepad2.dpad_down){
                grabPosition -= 0.005; //lonk changed from 0.001
            }

            if (gamepad1.left_bumper || gamepad1.right_bumper){
                lowPower = 0.5;
            }
            else {
                lowPower = 0.75;
            }

            if (gamepad2.y || gamepad2.b){
                rotationPosition = 0.36;
            }

            if (gamepad2.x || gamepad2.a){
                rotationPosition = 0.52;
            }

            if(gamepad2.left_bumper || gamepad2.right_bumper){
                pos = -152;
            }

            rotationPosition += gamepad2.right_stick_y/512;//lonk changed from 2048

            if (rotationPosition > 0.54){
                rotationPosition = 0.54;
            }
            if (rotationPosition < 0.28){
                rotationPosition = 0.28;
            }

            if (grabPosition > 0.6) {
                grabPosition = 0.6;
            }
            if (grabPosition < 0.28){
                grabPosition = 0.28;
            }

            frontLeftPower = frontLeftPower * lowPower;
            frontRightPower = frontRightPower * lowPower;
            backLeftPower = backLeftPower * lowPower;
            backRightPower = backRightPower * lowPower;

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
            //VSMotor.setPower(vertical);
            
            frontLeft.setPower(frontLeftPower);
            frontRight.setPower(frontRightPower);
            backLeft.setPower(backLeftPower);
            backRight.setPower(backRightPower);

            ClawRotServo.setPosition(rotationPosition);
            ClawGrabServo.setPosition(grabPosition);
            
            if (gamepad2.left_stick_y > 0.2 && (VSMotor.getCurrentPosition() < 0) || gamepad2.guide) { //lonkus changed to 280 (original was 170)
                VSMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                if (pos > -200){
                    VSMotor.setPower(gamepad2.left_stick_y/8);
                }
                else{
                    VSMotor.setPower(gamepad2.left_stick_y/1.5);
                }
                pos = VSMotor.getCurrentPosition();
                if (pos > 0) pos = 0;
            }
            else if (gamepad2.left_stick_y < -0.2 && (VSMotor.getCurrentPosition() > -3000) || gamepad2.guide) {
                VSMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                if (pos < -2800){
                    VSMotor.setPower(gamepad2.left_stick_y/8);
                }
                else{
                    VSMotor.setPower(gamepad2.left_stick_y/1.5);
                }
                pos = VSMotor.getCurrentPosition();
                if (pos < -3000) pos = -3000;
            }
            else {
                VSMotor.setTargetPosition(pos);
                VSMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
            //telemetry.addData("ViperSlide", "%4.2f", vertical);
            telemetry.addData("Encoder Position", (VSMotor.getCurrentPosition()));
            telemetry.addData("Rotation", "%4.2f", ClawRotServo.getPosition());
            telemetry.addData("Grab", "%4.2f", ClawGrabServo.getPosition());
            telemetry.update();
        }
    }
}
