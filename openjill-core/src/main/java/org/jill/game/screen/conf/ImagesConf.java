package org.jill.game.screen.conf;

/**
 * Images configuration to draw.
 *
 * @author Emeric MARTINEAU
 */
public final class ImagesConf extends PictureConf {
    /**
     * Comment.
     */
    private String comment;

    /**
     * Position.
     */
    private int x;

    /**
     * Position.
     */
    private int y;

    /**
     * Position.
     *
     * @return  x
     */
    public int getX() {
        return x;
    }

    /**
     * Position.
     *
     * @param x1 x
     */
    public void setX(final int x1) {
        this.x = x1;
    }

    /**
     * Position.
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Position.
     *
     * @param y1 y
     */
    public void setY(final int y1) {
        this.y = y1;
    }

    /**
     * Comment.
     *
     * @return comment.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Comment.
     *
     * @param ct comment
     */
    public void setComment(final String ct) {
        this.comment = ct;
    }


}
