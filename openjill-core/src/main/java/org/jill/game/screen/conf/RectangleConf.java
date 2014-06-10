package org.jill.game.screen.conf;

/**
 * Configuration of rectangle.
 *
 * @author emeric MARTINEAU
 */
public final class RectangleConf {
    /**
     * The X coordinate of the upper-left corner of the <code>Rectangle</code>.
     */
    private int x;

    /**
     * The Y coordinate of the upper-left corner of the <code>Rectangle</code>.
     */
    private int y;

    /**
     * The width of the <code>Rectangle</code>.
     */
    private int width;

    /**
     * The height of the <code>Rectangle</code>.
     */
    private int height;

    /**
     * Color of rectangle.
     */
    private String color;

    /**
     * The X coordinate of the upper-left corner of the <code>Rectangle</code>.
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * The X coordinate of the upper-left corner of the <code>Rectangle</code>.
     *
     * @param x1 x
     */
    public void setX(final int x1) {
        this.x = x1;
    }

    /**
     * The Y coordinate of the upper-left corner of the <code>Rectangle</code>.
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * The Y coordinate of the upper-left corner of the <code>Rectangle</code>.
     *
     * @param y1 y
     */
    public void setY(final int y1) {
        this.y = y1;
    }

    /**
     * The width of the <code>Rectangle</code>.
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * The width of the <code>Rectangle</code>.
     *
     * @param w width
     */
    public void setWidth(final int w) {
        this.width = w;
    }

    /**
     * The height of the <code>Rectangle</code>.
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * The height of the <code>Rectangle</code>.
     *
     * @param h height
     */
    public void setHeight(final int h) {
        this.height = h;
    }

    /**
     * Color of rectangle.
     *
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * Color of rectangle.
     *
     * @param c background color
     */
    public void setColor(final String c) {
        this.color = c;
    }


}
