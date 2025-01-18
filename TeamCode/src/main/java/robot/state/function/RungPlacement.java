package robot.state.function;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import robot.state.GamepadToggle;
import robot.state.IOController;
import robot.state.StateFunction;
import robot.system.subsystem.Outtake;

public class RungPlacement implements StateFunction {
    public static void run(IOController ioController) {
        ioController.getOuttake().setToTargetPosition(ioController.getOuttake().getLevel()); // loops
        ioController.getOuttake().rotate(ioController.getOuttake().getRotatePosition());

//        if (ioController.getGamepadToggle().getToggleState(GamepadToggle.Button.X)) {
//            ioController.getOuttake().pincerClose();
//        } else {
//            ioController.getOuttake().pincerOpen();
//        }

        if (ioController.getCurrentGamepad2().x && !ioController.getPreviousGamepad2().x) {
            ioController.getOuttake().setPincerToggled(!ioController.getOuttake().isPincerToggled());
        }

        if (ioController.getOuttake().isPincerToggled()) {
            ioController.getOuttake().pincerClose();
        } else {
            ioController.getOuttake().pincerOpen();
        }

        if (ioController.getCurrentGamepad2().y && !ioController.getPreviousGamepad2().y) {
            ioController.getOuttake().setRungPlacement(true);
        }

        // fsjdoifse
//        if (ioController.getCurrentGamepad2().dpad_up
//                && !ioController.getPreviousGamepad2().dpad_up
//                && (ioController.getOuttake().getLevel() == Outtake.OuttakeLevel.LOW_RUNG)) {
//            ioController.getOuttake().setLevel(Outtake.OuttakeLevel.HIGH_RUNG);
//        }
//
//        if (ioController.getCurrentGamepad2().dpad_down
//                && !ioController.getPreviousGamepad2().dpad_down
//                && (ioController.getOuttake().getLevel() == Outtake.OuttakeLevel.HIGH_RUNG)) {
//            ioController.getOuttake().setLevel(Outtake.OuttakeLevel.LOW_RUNG);
//        }

        if (ioController.getOuttake().isRungPlacement()
                && ioController.getCurrentGamepad2().b
                && !ioController.getPreviousGamepad2().b) {
            ioController.getOuttake().pincerOpen();
            ioController.getOuttake().setPincerToggled(false);
            ioController.getOuttake().setLevel(Outtake.OuttakeLevel.WALL);

                int powerMilliSeconds = 1000;
                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

                // Schedule to run after x milliseconds
                scheduler.schedule(
                        () -> {

//                            ioController.getOuttake().twistHorizontal();
//                            ioController.getOuttake().setRotatePosition(Outtake.OuttakeRotate.TRANSFER);
//                            ioController.getOuttake().setLevel(Outtake.OuttakeLevel.GROUND);
                            ioController.setState(IOController.State.IDLE);

//                            if (ioController.getOuttake().isRotated()) {
//                                ioController.getOuttake().setLevel(Outtake.OuttakeLevel.GROUND);
//                                ioController.setState(IOController.State.IDLE);
//                            } this never runs due to the scheduling thing

                        },
                        powerMilliSeconds,
                        TimeUnit.MILLISECONDS
                );

                scheduler.shutdown();
            ioController.getOuttake().setRungPlacement(false);
            ioController.getGamepadToggle().resetToggle(GamepadToggle.Button.B);
            ioController.getGamepadToggle().resetToggle(GamepadToggle.Button.X);
        }

        if (ioController.getOuttake().isRungPlacement()) {
            ioController.getOuttake().twistInverseHorizontal();
            ioController.getOuttake().linkageForward();
            ioController.getOuttake().setRotatePosition(Outtake.OuttakeRotate.SUBMERSIBLE);
            ioController.getOuttake().setLevel(Outtake.OuttakeLevel.HIGH_RUNG);

            // TODO: adjust value for twist servo flip flop

//            if (ioController.getCurrentGamepad2().dpad_up
//                    && !ioController.getPreviousGamepad2().dpad_up
//                    && (ioController.getOuttake().getLevel() == Outtake.OuttakeLevel.LOW_RUNG)) {
//                ioController.getOuttake().setLevel(Outtake.OuttakeLevel.HIGH_RUNG);
//            }
//
//            if (ioController.getCurrentGamepad2().dpad_down
//                    && !ioController.getPreviousGamepad2().dpad_down
//                    && (ioController.getOuttake().getLevel() == Outtake.OuttakeLevel.HIGH_RUNG)) {
//                ioController.getOuttake().setLevel(Outtake.OuttakeLevel.LOW_RUNG);
//            }
//
//            if (ioController.getCurrentGamepad2().b && !ioController.getPreviousGamepad2().b) {
//                ioController.getOuttake().setRotatePosition(Outtake.OuttakeRotate.WALL);

//                int powerMilliSeconds = 2000;
//                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//
//                // Schedule to run after x milliseconds
//                scheduler.schedule(
//                        () -> {
//                            ioController.getOuttake().pincerOpen();
//                            ioController.getOuttake().linkageBackward();
//                            ioController.getOuttake().twistHorizontal();
//                            ioController.getOuttake().setLevel(Outtake.OuttakeLevel.GROUND);
//                        },
//                        powerMilliSeconds,
//                        TimeUnit.MILLISECONDS
//                );
//
//                scheduler.shutdown();
            }

            // if pincer is open at this point, then we move all the motors back to defaults and return back to idle state
//            if (!ioController.getGamepadToggle().getToggleState(GamepadToggle.Button.X)) {
//                ioController.getOuttake().linkageBackward();
//                ioController.getOuttake().twistHorizontal();
//                ioController.getOuttake().rotate(Outtake.OuttakeRotate.TRANSFER);
//                ioController.getOuttake().setLevel(Outtake.OuttakeLevel.GROUND);
//
//                if (ioController.getOuttake().isAtTargetPosition()
//                        && ioController.getOuttake().isRotated()) {
//                    ioController.setState(IOController.State.IDLE);
//                }
////                ioController.setState(IOController.State.IDLE);
//            }
//        } else if (ioController.getCurrentGamepad2().dpad_down
//                && !ioController.getPreviousGamepad2().dpad_down
//                && (ioController.getOuttake().getLevel() == Outtake.OuttakeLevel.WALL)) {
//            ioController.getOuttake().setLevel(Outtake.OuttakeLevel.GROUND);
//            ioController.getOuttake().setRotatePosition(Outtake.OuttakeRotate.TRANSFER);
//        }

//        if (ioController.getOuttake().isAtTargetPosition()
//                && (ioController.getOuttake().getRotatePosition() == Outtake.OuttakeRotate.TRANSFER)
//                && ioController.getOuttake().isRotated()) {
//            ioController.setState(IOController.State.IDLE);
//        }
//
//        if (ioController.getGamepadToggle().getToggleState(GamepadToggle.Button.Y)) {
//
//        }
    }
}
