package org.firstinspires.ftc.teamcode.Autonomous;

//ATTENTION: Please copy and paste this file to make your auto this is a template and demo

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@Autonomous
public class AutoLeft_SpecimenSamplePark extends LinearOpMode{
    private DcMotor topleft;
    private DcMotor topright;
    private DcMotor bottomleft;
    private DcMotor bottomright;
    
    private DcMotor VSMotor = null;
    
    private Servo ClawRotServo =  null;
    private Servo ClawGrabServo = null;
    
    
    //distance variables
    private int toprightt;
    private int topleftt;
    private int bottomleftt;
    private int bottomrightt;
    private int vipert;
    @Override
    public void runOpMode() {
        topleft = hardwareMap.get(DcMotor.class, "frontLeft");
        topright = hardwareMap.get(DcMotor.class, "frontRight");
        bottomleft = hardwareMap.get(DcMotor.class, "backLeft");
        bottomright = hardwareMap.get(DcMotor.class, "backRight");
        ClawRotServo = hardwareMap.get(Servo.class, "ClawRotServo");
        ClawGrabServo = hardwareMap.get(Servo.class, "ClawGrabServo");
        VSMotor = hardwareMap.get(DcMotor.class, "VSMotor");
        
        VSMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        topleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        topright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bottomleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bottomright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        topleft.setDirection(DcMotor.Direction.REVERSE);
        topright.setDirection(DcMotor.Direction.FORWARD);
        bottomright.setDirection(DcMotor.Direction.FORWARD);
        bottomleft.setDirection(DcMotor.Direction.REVERSE);
        
        ClawRotServo.setDirection(Servo.Direction.FORWARD);
        ClawGrabServo.setDirection(Servo.Direction.FORWARD);
        
        ClawRotServo.setPosition(0.28);
        ClawGrabServo.setPosition(0.6);

        topleftt = 0;
        toprightt = 0;
        bottomleftt = 0;
        bottomrightt = 0;
        waitForStart();

    while (opModeIsActive()){
        //All Commands go here
        //This is a demo to test all functions
        moveForward(400,.5);
        sleep(500);
        moveBackward(380,.25);
        moveLeft(100,.5);
        lowerArm();
        sleep(1000);
        Grab();
        sleep(500);
        raiseArm();
        moveForward(960,.5);
        sleep(500);
        setViper(-1500, 0.5);
        sleep(1000);
        moveForward(300,.5);
        sleep(1000);
        setViper(-1000, 0.5);
        sleep(250);
        unGrab();
        sleep(250);
        moveBackward(1175,.5);
        setViper(0, 0.5);
        
        
        //sleep(5000);
        //Sample getting
        
        moveLeft(2000,.5);
        moveForward(925,.5);
        lowerArm();
        sleep(1000);
        Grab();
        sleep(500);
        raiseArm();
        sleep(1000);
        turnLeft(1150,.5);
        setViper(-2000,.5);
        moveForward(200,.5);
        unGrab();
        sleep(1000);
        moveBackward(1000,.5);
        
        //moveRight(2600,.5);
        break; //Remember to break to exit the auto mode
    }
    }
    private void raiseArm(){
        ClawRotServo.setPosition(0.28); //Raised Position
    }
    
    private void lowerArm(){
        ClawRotServo.setPosition(0.565); //Lowered Position
    }
    
    private void unGrab(){
        ClawGrabServo.setPosition(0.6); //Open Position
    }
    
    private void Grab(){
        ClawGrabServo.setPosition(0.28); //Closed Position
    }
    
        private void moveForward(int distance, double speed){
        if (speed > 1) speed = 1;
        drive(distance, distance, distance, distance, speed);
    }
        
    private void moveBackward(int distance, double speed){
        if (speed > 1) speed = 1;
        drive(-distance, -distance, -distance, -distance, speed);
    }
    
    private void moveRight(int distance, double speed){
        if (speed > 1) speed = 1;
        drive(distance, -distance, -distance, distance, speed);
    }
    
    private void moveLeft(int distance, double speed){
        if (speed > 1) speed = 1;
        drive(-distance, distance, distance, -distance, speed);
    }
    
    private void turnLeft(int distance, double speed){
        if (speed > 1) speed = 1;
        drive(-distance, distance, -distance, distance, speed);
    }
    
    private void turnRight(int distance, double speed){
        if (speed > 1) speed = 1;
        drive(distance, -distance, distance, -distance, speed);
    }
    
    private void setViper(int distance, double speed){
        if (distance < -3000) distance = -3000; //Top Limit
        if (distance > 0) distance = 0; //Bottom Limit
        //Limits are negative becaue the motor is in the reversed mode
        
        vipert = distance;

        VSMotor.setTargetPosition(distance);
        
        VSMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        VSMotor.setPower(speed);
        while (opModeIsActive() && VSMotor.isBusy()); {
            idle();
        }
    }
    
    private void drive(int distance, int distance2, int distance3, int distance4, double speed) {
        topleftt = distance;
        toprightt = distance2;
        bottomleftt = distance3;
        bottomrightt = distance4;

        topleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        topright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        topleft.setTargetPosition(topleftt);
        topright.setTargetPosition(toprightt);
        bottomleft.setTargetPosition(bottomleftt);
        bottomright.setTargetPosition(bottomrightt);

        topleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        topright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bottomleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bottomright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        topleft.setPower(speed);
        topright.setPower(speed);
        bottomleft.setPower(speed);
        bottomright.setPower(speed);
        while (opModeIsActive() && topleft.isBusy() && topright.isBusy() && bottomright.isBusy() && bottomright.isBusy()); {
            idle();
        }
    }
}
