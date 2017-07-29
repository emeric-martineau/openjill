package org.jill.openjill.core.api.keyboard;

/**
 * Keyboard layout.
 *
 * @author Emeric MARTINEAU
 */
public final class KeyboardLayout {
    /**
     * Key.
     */
    private boolean left;

    /**
     * Key.
     */
    private boolean right;

    /**
     * Key.
     */
    private boolean up;

    /**
     * Key.
     */
    private boolean down;

    /**
     * Key.
     */
    private boolean jump;

    /**
     * Key.
     */
    private boolean fire;

    /**
     * To know if no key pressed.
     */
    private boolean noKeyPressed = true;

    /**
     * Clear all key envent.
     */
    public void clear() {
        left = false;
        right = false;
        up = false;
        down = false;
        jump = false;
        fire = false;

        noKeyPressed = true;
    }

    /**
     * To know if left keyboard event is produce.
     *
     * @return true/false
     */
    public boolean isLeft() {
        return left;
    }

    /**
     * Set left key event.
     *
     * @param l true/false
     */
    public void setLeft(final boolean l) {
        this.left = l;
        this.noKeyPressed = false;
    }

    /**
     * To know if right keyboard event is produce.
     *
     * @return true/false
     */
    public boolean isRight() {
        return right;
    }

    /**
     * Set right key event.
     *
     * @param r true/false
     */
    public void setRight(final boolean r) {
        this.right = r;
        this.noKeyPressed = false;
    }

    /**
     * To know if up keyboard event is produce.
     *
     * @return true/false
     */
    public boolean isUp() {
        return up;
    }

    /**
     * Set right key event.
     *
     * @param u true/false
     */
    public void setUp(final boolean u) {
        this.up = u;
        this.noKeyPressed = false;
    }

    /**
     * To know if down keyboard event is produce.
     *
     * @return true/false
     */
    public boolean isDown() {
        return down;
    }

    /**
     * Set down key event.
     *
     * @param d true/false
     */
    public void setDown(final boolean d) {
        this.down = d;
        this.noKeyPressed = false;
    }

    /**
     * To know if jump keyboard event is produce.
     *
     * @return true/false
     */
    public boolean isJump() {
        return jump;
    }

    /**
     * Jump.
     *
     * @param j true/false
     */
    public void setJump(final boolean j) {
        this.jump = j;
        this.noKeyPressed = false;
    }

    /**
     * To know if fire keyboard event is produce.
     *
     * @return true/false
     */
    public boolean isFire() {
        return fire;
    }

    /**
     * Set fire key event.
     *
     * @param f true/false
     */
    public void setFire(final boolean f) {
        this.fire = f;
        this.noKeyPressed = false;
    }

    /**
     * To know if no key event produce.
     *
     * @return true/false
     */
    public boolean isNoKeyPressed() {
        return noKeyPressed;
    }

    /**
     * Set no key event produce.
     *
     * @param nkp true/false
     */
    public void setNoKeyPressed(final boolean nkp) {
        this.noKeyPressed = nkp;
    }
}
