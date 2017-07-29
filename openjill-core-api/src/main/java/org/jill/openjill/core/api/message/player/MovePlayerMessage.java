package org.jill.openjill.core.api.message.player;

/**
 * Class to move player and set a new status.
 *
 * @author Emeric MARTINEAU
 */
public final class MovePlayerMessage {
    /**
     * Offset to move X.
     */
    private int offsetX;

    /**
     * Offset to move Y.
     */
    private int offsetY;

    /**
     * Player state.
     */
    private int state;

    /**
     * If player can move.
     */
    private boolean canDoMove;

    /**
     * Constructor.
     */
    public MovePlayerMessage() {
    }

    /**
     * Offset to move X.
     * @return offset
     */
    public int getOffsetX() {
        return offsetX;
    }

    /**
     * Offset to move X.
     *
     * @param offX offset
     */
    public void setOffsetX(final int offX) {
        this.offsetX = offX;
    }

    /**
     * Offset to move Y.
     *
     * @return  offset
     */
    public int getOffsetY() {
        return offsetY;
    }

    /**
     * Offset to move X.
     *
     * @param offY offset
     */
    public void setOffsetY(final int offY) {
        this.offsetY = offY;
    }

    /**
     * Player state.
     *
     * @return  state
     */
    public int getState() {
        return state;
    }

    /**
     * Player state.
     *
     * @param status state
     */
    public void setState(final int status) {
        this.state = status;
    }

    /**
     * If player can move.
     *
     * @return true/false
     */
    public boolean isCanDoMove() {
        return canDoMove;
    }

    /**
     * If player can move.
     *
     * @param move if can move
     */
    public void setCanDoMove(final boolean move) {
        this.canDoMove = move;
    }
}
