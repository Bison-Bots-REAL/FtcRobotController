package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Roadrunner.MecanumDrive;

@Autonomous(name="Left")
public class Left_Auto_RR extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        Servo arm = hardwareMap.servo.get("ClawRotServo");
        Servo hand = hardwareMap.servo.get("ClawGrabServo");

        DcMotor VSMotor = hardwareMap.dcMotor.get("VSMotor");

        hand.setDirection(Servo.Direction.FORWARD);
        arm.setDirection(Servo.Direction.FORWARD);
        VSMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        arm.setPosition(0.28);
        hand.setPosition(0.6);

        waitForStart();

        //hand.setPosition(0.6) max
        //arm.setPosition(0.5) max

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(0, 0, 0))
                        //Hang Specimen
                        .lineToX(11)
                        .lineToX(2)
                        //.strafeTo(new Vector2d(0, 2))
                        .stopAndAdd(new ServoAction(arm, 0.52))
                        .waitSeconds(1)
                        .stopAndAdd(new ServoAction(hand, 0.28))
                        .waitSeconds(1)
                        .stopAndAdd(new ServoAction(arm, 0.28))
                        .stopAndAdd(new SetViper(VSMotor,-2000,0.75))
                        .strafeTo(new Vector2d(32,0))
                        .stopAndAdd(new SetViper(VSMotor,-1000,0.8))
                        .waitSeconds(0.5)
                        .stopAndAdd(new ServoAction(hand,0.6))
                        .waitSeconds(0.5)
                        .stopAndAdd(new SetViper(VSMotor,0,0.5))

                        //Grab Samples (into Low Basket)
                        .strafeTo(new Vector2d(16, 0))
                        .strafeTo(new Vector2d(23,96))
                        .turnTo(0)
                        .stopAndAdd(new ServoAction(arm,0.54))
                        .waitSeconds(0.5)
                        .stopAndAdd(new ServoAction(hand,0.28))
                        .waitSeconds(0.5)
                        .stopAndAdd(new SetViper(VSMotor,-2500,0.8))
                        .stopAndAdd(new ServoAction(arm, 0.35))
                        .strafeTo(new Vector2d(10,104))
                        .turnTo(2.44346095)
                        .waitSeconds(0.5)
                        .stopAndAdd(new ServoAction(hand,0.6))
                        .stopAndAdd(new ServoAction(arm, 0.35))
                        .waitSeconds(1)
                        .turnTo(0)
                        .stopAndAdd(new SetViper(VSMotor,0,0.8))
                        .strafeTo(new Vector2d(23,118))
                        .waitSeconds(1)
                        .stopAndAdd(new ServoAction(arm,0.54))
                        .waitSeconds(0.5)
                        .stopAndAdd(new ServoAction(hand,0.28))
                        .waitSeconds(0.5)
                        .stopAndAdd(new ServoAction(arm, 0.35))
                        .stopAndAdd(new SetViper(VSMotor,-2500,0.8))
                        .strafeTo(new Vector2d(10,104))
                        .turnTo(2.44346095)
                        .waitSeconds(0.5)
                        .stopAndAdd(new ServoAction(hand,0.6))
                        .stopAndAdd(new ServoAction(arm, 0.35))
                        .waitSeconds(0.5)
                        .turnTo(0)
                        .stopAndAdd(new SetViper(VSMotor,0,0.8))
                        .waitSeconds(4)
                        .strafeTo(new Vector2d(0,0))
                        .build()
        );

    }
}
