package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Arclength;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.PosePath;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Roadrunner.MecanumDrive;

import java.util.Vector;

@Autonomous(name="Right")
public class RRFirstAction extends LinearOpMode {

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

                        //Grab Samples (into observation zone)
                        .strafeTo(new Vector2d(16, 0))
                        .strafeTo(new Vector2d(28,-96))
                        .turnTo(0)
                        .stopAndAdd(new ServoAction(arm,0.54))
                        .waitSeconds(0.5)
                        .stopAndAdd(new ServoAction(hand,0.28))
                        .waitSeconds(0.5)
                        .stopAndAdd(new ServoAction(arm, 0.35))
                        .turnTo(135)
                        .strafeTo(new Vector2d(24,-110))
                        .waitSeconds(1)
                        .stopAndAdd(new ServoAction(hand,0.6))
                        .stopAndAdd(new ServoAction(arm, 0.35))
                        .waitSeconds(1)
                        .turnTo(0)
                        .strafeTo(new Vector2d(28,-122))
                        .stopAndAdd(new ServoAction(arm,0.54))
                        .waitSeconds(0.5)
                        .stopAndAdd(new ServoAction(hand,0.28))
                        .waitSeconds(0.5)
                        .stopAndAdd(new ServoAction(arm, 0.35))
                        .strafeTo(new Vector2d(24,-110))
                        .turnTo(135)
                        .stopAndAdd(new ServoAction(hand,0.6))
                        .stopAndAdd(new ServoAction(arm, 0.35))
                        .waitSeconds(0.5)
                        .strafeTo(new Vector2d(24,-118))
                        .stopAndAdd(new ServoAction(hand,0.28))
                        .stopAndAdd(new ServoAction(arm,0.52))
                        .waitSeconds(2)
                        //.waitSeconds(4)
                        //.strafeTo(new Vector2d(0,0))
                        .build()
        );

    }


}

class ServoAction implements Action {
    Servo servo;
    double position;

    public ServoAction(Servo s, double p) {
        this.servo = s;
        this.position = p;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        servo.setPosition(position);
        return false;
    }
}
class SetViper implements Action{

    DcMotor VSMotor;
    int position;
    double speed;

    public SetViper(DcMotor v,int p, double s) {
        this.VSMotor = v;
        this.speed = s;
        this.position = p;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        if (position < -3000) position = -3000; //Top Limit
        if (position > 0) position = 0; //Bottom Limit
        //Limits are negative becaue the motor is in the reversed mode

        VSMotor.setTargetPosition(position);

        VSMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        VSMotor.setPower(speed);
        return false;
    }
}

