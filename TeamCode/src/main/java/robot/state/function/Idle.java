package robot.state.function;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import robot.state.GamepadToggle;
import robot.state.IOController;
import robot.state.StateFunction;
import robot.system.subsystem.Outtake;

public class Idle implements StateFunction {
    public static void run(IOController ioController) {
        ioController.getOuttake().setToTargetPosition(ioController.getOuttake().getLevel());
        ioController.getOuttake().rotate(ioController.getOuttake().getRotatePosition());

        ioController.getOuttake().linkageBackward();
        ioController.getOuttake().twistHorizontal();
        ioController.getOuttake().pincerOpen();
        ioController.getOuttake().setLevel(Outtake.OuttakeLevel.WALL);

        if (ioController.getOuttake().getLevel() == Outtake.OuttakeLevel.WALL
                && ioController.getOuttake().isAtTargetPosition()) {
            ioController.getOuttake().setRotatePosition(Outtake.OuttakeRotate.WALL);
        }

        if (ioController.getCurrentGamepad2().dpad_up
                && !ioController.getPreviousGamepad2().dpad_up
                && (ioController.getOuttake().getLevel() == Outtake.OuttakeLevel.WALL)) {
            ioController.setState(IOController.State.RUNG_PLACEMENT);
        }

//        ioController.getOuttake().setLevel(Outtake.OuttakeLevel.GROUND);

        if (ioController.getGamepadToggle().getToggleState(GamepadToggle.Button.A)) {
            ioController.getIntake().extend();

            if (ioController.getIntake().isExtended()) {
//                ioController.setState(IOController.State.PICKUP);

            }
        } else {
            ioController.getIntake().retract();
        }

//        if (!ioController.getGamepadToggle().getToggleState(GamepadToggle.Button.A)) {
//            ioController.getIntake().retract();
//
////            if (ioController.getIntake().isRetracted()) {
////                ioController.setState(IOController.State.HANDOFF_INITIALIZING);
////            }
//
////            return;
//        }

        if (ioController.getGamepadToggle().getToggleState(GamepadToggle.Button.B)) {
            ioController.getIntake().twistHorizontal();
        } else {
            ioController.getIntake().twistVertical();
        }

        if (ioController.getGamepadToggle().getToggleState(GamepadToggle.Button.X)) {
            ioController.getIntake().rotateDown();
            ioController.getIntake().pincerOpen();
        } else {
            ioController.getIntake().pincerClose();
            int powerMilliSeconds = 500;
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

            // Schedule to run after x milliseconds
            scheduler.schedule(
                    () -> ioController.getIntake().rotateUp(),
                    powerMilliSeconds,
                    TimeUnit.MILLISECONDS
            );

            scheduler.shutdown();
        }
    }
}
