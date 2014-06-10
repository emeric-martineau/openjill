package org.jill.openjill.core.api.message.player;

/**
 * Class to move player and set a new status.
 *
 * @author Emeric MARTINEAU
 */
public final class GetPlayerPositionMessage {
    /**
     * X.
     */
    private int x;

    /**
     * Y.
     */
    private int y;

    /**
     * Constructor.
     */
    public GetPlayerPositionMessage() {
    }

    /**
     * X.
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Offset  X.
     *
     * @param xpos x
     */
    public void setX(final int xpos) {
        this.x = xpos;
    }

    /**
     *  Y.
     *
     * @return  y
     */
    public int getY() {
        return y;
    }

    /**
     * Y.
     *
     * @param ypos y
     */
    public void setY(final int ypos) {
        this.y = ypos;
    }
}
