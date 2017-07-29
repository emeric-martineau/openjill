package org.jill.game.screen.conf;

/**
 * Draw line.
 *
 * @author Emeric MARTINEAU
 */
public class LineToDraw {
    /**
     * Color of picture.
     */
    private int color;

    /**
     * Position.
     */
    private int y;

    /**
     * Color.
     *
     * @return color
     */
    public int getColor() {
        return color;
    }

    /**
     * Color.
     *
     * @param c color
     */
    public void setColor(final int c) {
        this.color = c;
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
}
