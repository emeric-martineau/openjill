package org.jill.game.gui.menu.conf;

/**
 * Mode configuration (edit or readonly) for Highscore.
 *
 * @author Emeric MARTINEAU
 */
public final class ModeConf {
    /**
     * Background color.
     */
    private int backgroundColor;

    /**
     * Text color.
     */
    private int textColor;

    /**
     * Color of number.
     */
    private int numberColor;

    /**
     * Color f background.
     *
     * @return color
     */
    public int getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Color of background.
     *
     * @param bc color
     */
    public void setBackgroundColor(final int bc) {
        this.backgroundColor = bc;
    }

    /**
     * Color of text.
     *
     * @return color
     */
    public int getTextColor() {
        return textColor;
    }

    /**
     * Color of text.
     *
     * @param tc color
     */
    public void setTextColor(final int tc) {
        this.textColor = tc;
    }

    /**
     * Color of number.
     *
     * @return color
     */
    public int getNumberColor() {
        return numberColor;
    }

    /**
     * Color of number.
     *
     * @param nc color
     */
    public void setNumberColor(final int nc) {
        this.numberColor = nc;
    }
}
