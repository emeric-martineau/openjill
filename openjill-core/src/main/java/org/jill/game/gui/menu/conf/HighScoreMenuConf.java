package org.jill.game.gui.menu.conf;

/**
 * Highcore menu configuration.
 *
 * @author Emeric MARTINEAU
 */
public final class HighScoreMenuConf extends AbstractAdvancedMenuConf {
    /**
     * Interspace between text.
     */
    private int textInterSpace;

    /**
     * Interspace between text.
     *
     * @return size
     */
    public int getTextInterSpace() {
        return textInterSpace;
    }

    /**
     * Interspace between text.
     *
     * @param tis size
     */
    public void setTextInterSpace(final int tis) {
        this.textInterSpace = tis;
    }
}
