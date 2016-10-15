package org.jill.game.screen.conf;

/**
 * Configuration of game screen .
 *
 * @author emeric MARTINEAU
 */
public class GameAreaConf extends RectangleConf {
    /**
     * The coordinate offset screen.
     */
    private RectangleConf offset = new RectangleConf();

    /**
     * The coordinate of player when level load.
     */
    private RectangleConf levelStart;

    /**
     * Game border (space between player and screen border).
     */
    private GameAreaBorderConf border;

    /**
     * Special screen shift (up/dow).
     */
    private int specialScreenShift = 0;

    /**
     * The coordinate offset screen.
     *
     * @return x
     */
    public RectangleConf getOffset() {
        return offset;
    }

    /**
     * The coordinate offset screen.
     *
     * @param x1 x
     */
    public void setOffsetX(final RectangleConf x1) {
        this.offset = x1;
    }

    /**
     * The coordinate when level load.
     *
     * @return conf
     */
    public RectangleConf getLevelStart() {
        return levelStart;
    }

    /**
     * The coordinate when level load.
     *
     * @param levelStart1 conf
     */
    public void setLevelStart(final RectangleConf levelStart1) {
        levelStart = levelStart1;
    }

    /**
     * Game border (space between player and screen border).
     *
     * @return border
     */
    public GameAreaBorderConf getBorder() {
        return border;
    }

    /**
     * Game border (space between player and screen border).
     *
     * @param b border
     */
    public void setBorder(final GameAreaBorderConf b) {
        border = b;
    }

    /**
     * Special screen shift.
     *
     * @return value of shift
     */
    public int getSpecialScreenShift() {
        return specialScreenShift;
    }

    /**
     * Special screen shift.
     *
     * @param specialScreenShift value of shift
     */
    public void setSpecialScreenShift(final int specialScreenShift) {
        this.specialScreenShift = specialScreenShift;
    }


}
