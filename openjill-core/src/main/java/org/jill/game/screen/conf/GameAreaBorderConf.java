package org.jill.game.screen.conf;

/**
 * Configuration of border for game area.
 *
 * @author emeric MARTINEAU
 */
public class GameAreaBorderConf {
    /**
     * The left of the border.
     */
    private int left;

    /**
     * The right of the border.
     */
    private int right;

    /**
     * The top of the border.
     */
    private int top;
    
    /**
     * The bottom of the border.
     */
    private int bottom;
    
    /**
     * The left of the border.
     *
     * @return width
     */
    public int getLeft() {
        return left;
    }

    /**
     * The left of the border.
     *
     * @param w width
     */
    public void setLeft(final int w) {
        this.left = w;
    }

    /**
     * The right of the border.
     *
     * @return height
     */
    public int getRight() {
        return right;
    }

    /**
     * The right of the border.
     *
     * @param h height
     */
    public void setRight(final int h) {
        this.right = h;
    }
    
    /**
     * The top of the border.
     * 
     * @return top
     */
    public int getTop() {
        return top;
    }
    
    /**
     * The top of the border.
     * 
     * @param t top
     */
    public void setTop(final int t) {
        top = t;
    }
    
    /**
     * The bottom of the border.
     * 
     * @return bottom
     */
    public int getBottom() {
        return bottom;
    }
    
    /**
     * The bottom of the border.
     * 
     * @param b bottom
     */
    public void setBottom(final int b) {
        bottom = b;
    }
}
