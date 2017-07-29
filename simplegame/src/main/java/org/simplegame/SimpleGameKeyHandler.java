package org.simplegame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * Key handler.
 *
 * @author Emeric MARTINEAU
 */
public final class SimpleGameKeyHandler extends KeyAdapter
        implements WindowFocusListener {

    /**
     * Instance.
     */
    private static final SimpleGameKeyHandler INSTANCE
            = new SimpleGameKeyHandler();

    /**
     * Key fire is pressed.
     */
    private boolean fire = false;

    /**
     * Key up is pressed.
     */
    private boolean up = false;

    /**
     * Key down is pressed.
     */
    private boolean down = false;

    /**
     * Key left is pressed.
     */
    private boolean left = false;

    /**
     * Key right is pressed.
     */
    private boolean right = false;

    /**
     * Key jump is pressed.
     */
    private boolean jump = false;

    /**
     * Key escape is pressed.
     */
    private boolean escape = false;

    /**
     * Key enter is pressed.
     */
    private boolean enter = false;

    /**
     * Key any key is pressed.
     */
    private boolean keyPressed = false;

    /**
     * Key value.
     */
    private char keyValue;

    /**
     * Flag to know if not control key pressed.
     */
    private boolean otherKey = true;

    /**
     * Private constructor.
     */
    private SimpleGameKeyHandler() {

    }

    /**
     * Return instance of keyboard.
     *
     * @return keyboard
     */
    public static SimpleGameKeyHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void windowGainedFocus(final WindowEvent e) {
        // Nothing
    }

    @Override
    public void windowLostFocus(final WindowEvent e) {
        clear();
    }

    @Override
    public void keyPressed(final KeyEvent ke) {

        if (ke.getID() == KeyEvent.KEY_PRESSED) {
            updateKeyboardStatus(ke.getKeyCode(), true);
        }

        keyValue = ke.getKeyChar();

        // TODO wrap with configured key in properties
    }

    @Override
    public void keyReleased(final KeyEvent ke) {
        if (ke.getID() == KeyEvent.KEY_RELEASED) {
            updateKeyboardStatus(ke.getKeyCode(), false);
        }

        this.keyValue = ke.getKeyChar();
    }

    /**
     * Update keyboard status.
     *
     * @param ke    key event
     * @param value value to set
     */
    private void updateKeyboardStatus(final int ke, final boolean value) {
        this.otherKey = false;

        switch (ke) {
            case KeyEvent.VK_SPACE:
                this.fire = value;
                break;
            case KeyEvent.VK_UP:
                this.up = value;
                break;
            case KeyEvent.VK_DOWN:
                this.down = value;
                break;
            case KeyEvent.VK_LEFT:
                this.left = value;
                break;
            case KeyEvent.VK_RIGHT:
                this.right = value;
                break;
            case KeyEvent.VK_SHIFT:
                this.jump = value;
                break;
            case KeyEvent.VK_ESCAPE:
                this.escape = value;
                break;
            case KeyEvent.VK_ENTER:
                this.enter = value;
                break;
            default:
                this.otherKey = value;
        }

        this.keyPressed = value;
    }

    /**
     * Fire.
     *
     * @return fire
     */
    public boolean isFire() {
        return this.fire;
    }

    /**
     * Up.
     *
     * @return up
     */
    public boolean isUp() {
        return this.up;
    }

    /**
     * Down.
     *
     * @return down
     */
    public boolean isDown() {
        return this.down;
    }

    /**
     * Left.
     *
     * @return left
     */
    public boolean isLeft() {
        return this.left;
    }

    /**
     * Right.
     *
     * @return right
     */
    public boolean isRight() {
        return this.right;
    }

    /**
     * Jump.
     *
     * @return jump
     */
    public boolean isJump() {
        return this.jump;
    }

    /**
     * Key pressed.
     *
     * @return keyPressed
     */
    public boolean isKeyPressed() {
        return this.keyPressed;
    }

    /**
     * Escape.
     *
     * @return escape
     */
    public boolean isEscape() {
        return this.escape;
    }

    /**
     * Enter.
     *
     * @return enter
     */
    public boolean isEnter() {
        return this.enter;
    }

    /**
     * Other key.
     *
     * @return other key
     */
    public boolean isOtherKey() {
        return this.otherKey;
    }
//
//    /**
//     * @return fire
//     */
//    public boolean consumeFire() {
//        boolean oldfire = fire;
//        fire = false;
//        keyPressed = false;
//        return oldfire;
//    }
//
//    /**
//     * @return up
//     */
//    public boolean consumeUp() {
//        boolean oldup = up;
//        up = false;
//        keyPressed = false;
//        return oldup;
//    }
//
//    /**
//     * @return down
//     */
//    public boolean consumeDown() {
//        boolean olddown = down;
//        down = false;
//        keyPressed = false;
//        return olddown;
//    }
//
//    /**
//     * @return left
//     */
//    public boolean consumeLeft() {
//        boolean oldleft = left;
//        left = false;
//        keyPressed = false;
//        return oldleft;
//    }
//
//    /**
//     * @return right
//     */
//    public boolean consumeRight() {
//        boolean oldright = right;
//        right = false;
//        keyPressed = false;
//        return oldright;
//    }
//
//    /**
//     * @return jump
//     */
//    public boolean consumeJump() {
//        boolean oldjump = jump;
//        jump = false;
//        keyPressed = false;
//        return oldjump;
//    }
//
//    /**
//     * @return keyPressed
//     */
//    public boolean consumeKeyPressed() {
//        boolean oldkeyPressed = keyPressed;
//        keyPressed = false;
//        return oldkeyPressed;
//    }
//
//    /**
//     * @return escape
//     */
//    public boolean consumeEscape() {
//        boolean oldescape = escape;
//        escape = false;
//        keyPressed = false;
//        return oldescape;
//    }
//
//    /**
//     * @return escape
//     */
//    public boolean consumeEnter() {
//        boolean oldenter = enter;
//        enter = false;
//        keyPressed = false;
//        return oldenter;
//    }

    /**
     * Other key.
     *
     * @return other key
     */
    public char consumeOtherKey() {
        this.otherKey = false;
        this.keyPressed = false;
        return this.keyValue;
    }

    /**
     * Simulate escape key.
     */
    public void escape() {
        this.escape = true;
        this.keyPressed = true;
    }

    /**
     * Simulate release escape key.
     */
    public void unescape() {
        this.escape = false;
        this.keyPressed = false;
    }

    /**
     * Clear all key
     */
    public void clear() {
        final boolean value = false;

        this.fire = value;
        this.up = value;
        this.down = value;
        this.left = value;
        this.right = value;
        this.jump = value;
        this.escape = value;
        this.enter = value;
        this.otherKey = value;
        this.keyPressed = value;
    }
}
