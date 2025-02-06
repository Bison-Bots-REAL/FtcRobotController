package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

// This TeleOp mode is for Everything
@Disabled
@Autonomous
public class AutoParkLeft extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor VSMotor = null;
    
    private DcMotor frontLeft = null;
    private DcMotor backLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backRight = null;

    private Servo ClawRotServo = null;
    private Servo ClawGrabServo = null;
    private double rotationPosition = 0;
    private double grabPosition = 0;

    @Override
    public void runOpMode() {
        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        ClawRotServo = hardwareMap.get(Servo.class, "ClawRotServo");
        ClawGrabServo = hardwareMap.get(Servo.class, "ClawGrabServo");
        
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        
        ClawRotServo.setDirection(Servo.Direction.FORWARD);
        ClawGrabServo.setDirection(Servo.Direction.FORWARD);
        
        ClawRotServo.setPosition(0.3);
        ClawGrabServo.setPosition(0.6);


        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();
        
        while (opModeIsActive()) {
            double frontLeftPower  = 0.5;
            double frontRightPower = -0.5;
            double backLeftPower   = -0.5;
            double backRightPower  = 0.5;

            // Send calculated power to wheels
            sleep(17000);
            frontLeft.setPower(frontLeftPower);
            frontRight.setPower(frontRightPower);
            backLeft.setPower(backLeftPower);
            backRight.setPower(backRightPower);
            sleep(2500);
            break;
        }
    }
}
