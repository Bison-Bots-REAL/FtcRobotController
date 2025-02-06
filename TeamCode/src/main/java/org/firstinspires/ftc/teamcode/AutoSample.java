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

// This Auto mode is for Samples when robot is on the left
@Disabled
@Autonomous
public class AutoSample extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor VSMotor = null;
    
    private DcMotor frontLeft = null;
    private DcMotor backLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backRight = null;

    private Servo ClawRotServo = null;
    private Servo ClawGrabServo = null;
    private double rotationPosition = 0.3;
    private double grabPosition = 0.3;
    
    private void grab(){
        ClawGrabServo.setPosition(0.28);
    }
    private void ungrab(){
        ClawGrabServo.setPosition(0.6);
    }
    private void lowerarm(){
        ClawRotServo.setPosition(0.6);
    }
    private void raisearm(){
        ClawRotServo.setPosition(0.3);
    }
    private void turnright(int time){
        frontLeft.setPower(0.5);
        frontRight.setPower(-0.4);
        backLeft.setPower(0.4);
        backRight.setPower(-0.5);
        sleep(time);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
    private void moveleft(int time){
        frontLeft.setPower(-0.5);
        frontRight.setPower(0.5);
        backLeft.setPower(0.5);
        backRight.setPower(-0.5);
        sleep(time);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
    private void moveright(int time){
        frontLeft.setPower(0.5);
        frontRight.setPower(-0.5);
        backLeft.setPower(-0.5);
        backRight.setPower(0.5);
        sleep(time);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
    private void moveforward(int time, int mul){
        frontLeft.setPower(0.5 * mul);
        frontRight.setPower(0.5 * mul);
        backLeft.setPower(0.5 * mul);
        backRight.setPower(0.5 * mul);
        sleep(time);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
    private void rise(int time){
        VSMotor.setPower(-1);
        sleep(time);
        VSMotor.setPower(0);
    }
    private void hold(){
        VSMotor.setPower(-0.25);
    }
    private void unhold(){
        VSMotor.setPower(0);
    }
    private void fall(int time){
        VSMotor.setPower(0.5);
        sleep(time);
        VSMotor.setPower(0);
    }

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
        VSMotor = hardwareMap.get(DcMotor.class, "VSMotor");
        
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        VSMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        
        ClawRotServo.setDirection(Servo.Direction.FORWARD);
        ClawGrabServo.setDirection(Servo.Direction.FORWARD);
        
        VSMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        ClawRotServo.setPosition(0.3);
        ClawGrabServo.setPosition(0.6);


        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();
        
        while (opModeIsActive()) {
            /*
            double frontLeftPower  = 0.5;
            double frontRightPower = -0.5;
            double backLeftPower   = -0.5;
            double backRightPower  = 0.5;
            */

            // Send calculated power to wheels

            //sleep(500);
            //unhold();
            //fall(1800);
            
            moveforward(400,1);
            moveleft(1000);
            moveforward(750,1);
            moveleft(350);
            lowerarm();
            sleep(500);
            grab();
            sleep(500);
            raisearm();
            sleep(400);
            turnright(1400);
            moveright(600);
            turnright(300);
            moveleft(300);
            rise(900);
            hold();
            moveforward(400,1);
            sleep(300);
            ungrab();
            sleep(200);
            moveforward(400, -1);
            unhold();
            fall(2000);
            
            turnright(750);
            moveleft(400);
            turnright(380);
            moveleft(420);
            ungrab();
            sleep(200);
            moveforward(300,1);
            moveright(200);
            lowerarm();
            sleep(500);
            grab();
            sleep(500);
            raisearm();
            sleep(400);
            turnright(1600);
            moveleft(400);
            rise(900);
            hold();
            moveforward(400,1);
            sleep(300);
            ungrab();
            sleep(200);
            moveforward(400, -1);
            unhold();
            fall(2000);
            break;

            // Show the elapsed game time and wheel power.
            //telemetry.addData("Status", "Run Time: " + runtime.toString());
            //telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
            //telemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
            //telemetry.update();
        }
    }
}
