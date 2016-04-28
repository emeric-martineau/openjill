package org.jill.game.screen.conf;

/**
 * Configuration of border for game area.
 *
 * @author emeric MARTINEAU
 */
public class GameAreaBorderConf {
    /**
     * The width of the <code>Rectangle</code>.
     */
    private int left;

    /**
     * The height of the <code>Rectangle</code>.
     */
    private int right;

    /**
     * The width of the <code>Rectangle</code>.
     *
     * @return width
     */
    public int getLeft() {
        return left;
    }

    /**
     * The width of the <code>Rectangle</code>.
     *
     * @param w width
     */
    public void setLeft(final int w) {
        this.left = w;
    }

    /**
     * The height of the <code>Rectangle</code>.
     *
     * @return height
     */
    public int getRight() {
        return right;
    }

    /**
     * The height of the <code>Rectangle</code>.
     *
     * @param h height
     */
    public void setRight(final int h) {
        this.right = h;
    }
}
