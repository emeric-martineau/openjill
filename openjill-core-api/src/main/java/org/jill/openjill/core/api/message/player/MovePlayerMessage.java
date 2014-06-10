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
     * Move up.
     */
    private boolean up;

    /**
     * Move down.
     */
    private boolean down;

    /**
     * Move left.
     */
    private boolean left;

    /**
     * Move right.
     */
    private boolean right;

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

    /**
     * Move up.
     *
     * @return move
     */
    public boolean isUp() {
        return up;
    }

    /**
     * Move up player.
     *
     * @param upPlayer if move up
     */
    public void setUp(final boolean upPlayer) {
        this.up = upPlayer;
    }

    /**
     * If player down.
     *
     * @return true/false
     */
    public boolean isDown() {
        return down;
    }

    /**
     * Move down player.
     *
     * @param downPlayer if move down
     */
    public void setDown(final boolean downPlayer) {
        this.down = downPlayer;
    }

    /**
     * If player move left.
     *
     * @return  true/false
     */
    public boolean isLeft() {
        return left;
    }

    /**
     * Move left player.
     *
     * @param leftPlayer if move left
     */
    public void setLeft(final boolean leftPlayer) {
        this.left = leftPlayer;
    }

    /**
     * If player move right.
     *
     * @return true/false
     */
    public boolean isRight() {
        return right;
    }

    /**
     * Move right player.
     *
     * @param rightPlayer if move right
     */
    public void setRight(final boolean rightPlayer) {
        this.right = rightPlayer;
    }
}
