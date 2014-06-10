package org.jill.game.screen.conf;

/**
 * Item configuration.
 *
 * @author Emeric MARTINEAU
 */
public final class ItemConf {
    /**
     * Position.
     */
    private int x;

    /**
     * Position.
     */
    private int y;

    /**
     * Number of row.
     */
    private int nbRow;

    /**
     * Number of col.
     */
    private int nbCol;

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
     * Number of row.
     *
     * @return number
     */
    public int getNbRow() {
        return nbRow;
    }

    /**
     * Number of row.
     *
     * @param r number
     */
    public void setNbRow(final int r) {
        this.nbRow = r;
    }

    /**
     * Number of col.
     *
     * @return number
     */
    public int getNbCol() {
        return nbCol;
    }

    /**
     * Number of col.
     *
     * @param c number
     */
    public void setNbCol(final int c) {
        this.nbCol = c;
    }


}
