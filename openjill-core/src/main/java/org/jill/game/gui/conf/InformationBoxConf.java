package org.jill.game.gui.conf;

/**
 * Configuration for Information box.
 *
 * @author Emeric MARTINEAU
 */
public class InformationBoxConf extends MessageConf {
    /**
     * Size of border width.
     */
    private int borderWith;

    /**
     * Size of border height.
     */
    private int borderHeight;

    /**
     * Number line to draw in one screen.
     */
    private int nbLineDraw;

    /**
     * Offset text.
     */
    private int offsetTextDrawX;

    /**
     * Offset title.
     */
    private int offsetTitleDrawX;

    /**
     * Offset title.
     */
    private int offsetTitleDrawY;

    /**
     * Size of border width.
     *
     * @return Size of border width.
     */
    public int getBorderWith() {
        return borderWith;
    }

    /**
     * Size of border height.
     *
     * @return Size of border height.
     */
    public int getBorderHeight() {
        return borderHeight;
    }

    /**
     * Number line to draw in one screen.
     *
     * @return Number line to draw in one screen.
     */
    public int getNbLineDraw() {
        return nbLineDraw;
    }

    /**
     * Offset text.
     *
     * @return Offset text.
     */
    public int getOffsetTextDrawX() {
        return offsetTextDrawX;
    }

    /**
     * Offset title.
     *
     * @return Offset title.
     */
    public int getOffsetTitleDrawX() {
        return offsetTitleDrawX;
    }

    /**
     * Offset title.
     *
     * @return Offset title.
     */
    public int getOffsetTitleDrawY() {
        return offsetTitleDrawY;
    }
}
