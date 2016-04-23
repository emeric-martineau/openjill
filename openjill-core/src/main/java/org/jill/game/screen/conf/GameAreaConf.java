package org.jill.game.screen.conf;

/**
 * Configuration of game screen .
 *
 * @author emeric MARTINEAU
 */
public class GameAreaConf extends RectangleConf {
    /**
     * The X coordinate of the upper-left corner of the <code>Rectangle</code>.
     */
    private int offsetX;

    /**
     * The Y coordinate of the upper-left corner of the <code>Rectangle</code>.
     */
    private int offsetY;
    
        /**
     * The X coordinate of the upper-left corner of the <code>Rectangle</code>.
     *
     * @return x
     */
    public int getOffsetX() {
        return offsetX;
    }

    /**
     * The X coordinate of the upper-left corner of the <code>Rectangle</code>.
     *
     * @param x1 x
     */
    public void setOffsetX(final int x1) {
        this.offsetX = x1;
    }

    /**
     * The Y coordinate of the upper-left corner of the <code>Rectangle</code>.
     *
     * @return y
     */
    public int getOffsetY() {
        return offsetY;
    }

    /**
     * The Y coordinate of the upper-left corner of the <code>Rectangle</code>.
     *
     * @param y1 y
     */
    public void setOffsetY(final int y1) {
        this.offsetY = y1;
    }

}
