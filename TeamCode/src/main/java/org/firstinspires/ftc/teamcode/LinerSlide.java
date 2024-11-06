package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robot.RobotState;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.Range;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="LinerSlide", group="Linear Opmode")
public class LinerSlide extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor RightFORWARD = null;
    private DcMotor RightBF = null;
    private DcMotor LeftFORWARD = null;
    private DcMotor LeftBF = null;

    private boolean manualMode = false;
    private double armSetpoint = 0.0;

    private final double armManualDeadband = 0.03;

    private final double gripperClosedPosition = 1.0;
    private final double gripperOpenPosition = 0.5;
    private final double wristUpPosition = 1.0;
    private final double wristDownPosition = 0.0;

    private final int armHomePosition = 0;
    private final int armIntakePosition = 10;
    private final int armScorePosition = 600;
    private final int armShutdownThreshold = 5;




    @Override
    public void runOpMode() {
        // Initialize the hardware variables.
        RightFORWARD  = hardwareMap.get(DcMotor.class, "RightFORWARD");
        RightBF = hardwareMap.get(DcMotor.class, "RightBF");
        LeftFORWARD = hardwareMap.get(DcMotor.class, "LeftFORWARD");
        LeftBF = hardwareMap.get(DcMotor.class, "LeftBF");



        // Set motor direction
        RightFORWARD.setDirection(DcMotor.Direction.REVERSE);
        LeftBF.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            double manualArmPower;

            manualArmPower = gamepad1.right_trigger - gamepad1.left_trigger;
            if (Math.abs(manualArmPower) > armManualDeadband) {
                if (!manualMode) {
                    RightBF.setPower(0.0);
                    LeftBF.setPower(0.0);
                    RightBF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    LeftBF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    manualMode = true;
                }
                RightFORWARD.setPower(manualArmPower);
                LeftFORWARD.setPower(manualArmPower);
            }
            else {
                if (manualMode) {
                    RightFORWARD.setTargetPosition(RightFORWARD.getCurrentPosition());
                    LeftFORWARD.setTargetPosition(LeftFORWARD.getCurrentPosition());
                    RightFORWARD.setPower(1.0);
                    LeftFORWARD.setPower(1.0);
                    RightFORWARD.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    LeftFORWARD.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    manualMode = false;
                }

                //preset buttons
                if (gamepad1.a) {
                    RightBF.setTargetPosition(armHomePosition);
                    LeftBF.setTargetPosition(armHomePosition);
                    RightBF.setPower(1.0);
                    LeftBF.setPower(1.0);
                    RightBF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    LeftBF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                else if (gamepad1.b) {
                    RightFORWARD.setTargetPosition(armIntakePosition);
                    LeftFORWARD.setTargetPosition(armIntakePosition);
                    RightFORWARD.setPower(1.0);
                    LeftFORWARD.setPower(1.0);
                    RightFORWARD.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    LeftFORWARD.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                else if (gamepad1.y) {
                    RightBF.setTargetPosition(armScorePosition);
                    LeftBF.setTargetPosition(armScorePosition);
                    RightBF.setPower(1.0);
                    LeftBF.setPower(1.0);
                    RightBF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    LeftBF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
            }

        }

    }
}


