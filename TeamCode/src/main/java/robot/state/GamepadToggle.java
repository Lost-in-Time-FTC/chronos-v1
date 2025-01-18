package robot.state;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadToggle {
    private Gamepad gamepad;
    private boolean[] toggleStates;
    private boolean[] previousStates;

    public GamepadToggle(Gamepad gamepad) {
        this.gamepad = gamepad;
        this.toggleStates = new boolean[10];
        this.previousStates = new boolean[10];
    }

    public enum Button {
        A, B, X, Y, LEFT_BUMPER, RIGHT_BUMPER, DPAD_UP, DPAD_DOWN, DPAD_LEFT, DPAD_RIGHT
    }

    // Update toggle states (call this in the loop)
    public void update() {
        updateToggle(Button.A.ordinal(), gamepad.a);
        updateToggle(Button.B.ordinal(), gamepad.b);
        updateToggle(Button.X.ordinal(), gamepad.x);
        updateToggle(Button.Y.ordinal(), gamepad.y);
        updateToggle(Button.LEFT_BUMPER.ordinal(), gamepad.left_bumper);
        updateToggle(Button.RIGHT_BUMPER.ordinal(), gamepad.right_bumper);
        updateToggle(Button.DPAD_UP.ordinal(), gamepad.dpad_up);
        updateToggle(Button.DPAD_DOWN.ordinal(), gamepad.dpad_down);
        updateToggle(Button.DPAD_LEFT.ordinal(), gamepad.dpad_left);
        updateToggle(Button.DPAD_RIGHT.ordinal(), gamepad.dpad_right);
    }

    public boolean getToggleState(Button button) {
        return toggleStates[button.ordinal()];
    }

    private void updateToggle(int index, boolean currentState) {
        if (currentState && !previousStates[index]) {
            toggleStates[index] = !toggleStates[index];
        }
        previousStates[index] = currentState;
    }
    public void resetToggle(Button button) {
        int index = button.ordinal();
        toggleStates[index] = false;
        previousStates[index] = false; // Reset the gamepad's "pressed" state
    }
}
