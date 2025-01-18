package robot.state.function;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import robot.state.GamepadToggle;
import robot.state.IOController;
import robot.state.StateFunction;

public class Pickup implements StateFunction {
    public static void run(IOController ioController) {
        if (!ioController.getGamepadToggle().getToggleState(GamepadToggle.Button.A)) {
            ioController.getIntake().retract();

//            if (ioController.getIntake().isRetracted()) {
//                ioController.setState(IOController.State.HANDOFF_INITIALIZING);
//            }

//            return;
        }

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
            int powerMilliSeconds = 1500;
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
