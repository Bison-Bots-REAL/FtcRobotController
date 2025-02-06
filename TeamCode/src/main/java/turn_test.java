import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Roadrunner.MecanumDrive;

@Disabled
@Autonomous
public class turn_test extends LinearOpMode {

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
                        .turnTo(90)
                        .turnTo(180)
                        .turnTo(270)
                        .turnTo(360)
                        .turnTo(-90)
                        .turnTo(-180)
                        .turnTo(-270)
                        .turnTo(-360)
                        .build()
        );

    }


}
