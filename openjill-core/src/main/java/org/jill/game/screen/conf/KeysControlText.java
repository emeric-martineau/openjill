package org.jill.game.screen.conf;

/**
 * This class contain text of controls key.
 *
 * @author Emeric MARINEAU
 */
public class KeysControlText {
    /**
     * Text for alt key.
     */
    private String alt;

    /**
     * Text for shift key.
     */
    private String shift;

    /**
     * If inventory item can change status of alt text (default tre).
     */
    private boolean canUpdateAltText = true;

    /**
     * If default at startup.
     */
    private boolean defaut = false;

    /**
     * Retrun text for alt key.
     *
     * @return text
     */
    public String getAlt() {
        return alt;
    }

    /**
     * Set text for alt.
     *
     * @param alt text
     */
    public void setAlt(final String alt) {
        this.alt = alt;
    }

    /**
     * Retrun text for shift key.
     *
     * @return text
     */
    public String getShift() {
        return shift;
    }

    /**
     * Set text for shift.
     *
     * @param shift text
     */
    public void setShift(final String shift) {
        this.shift = shift;
    }

    /**
     * If inventory item can change status of alt text.
     *
     * @return good boy :-
     */
    public boolean isCanUpdateAltText() {
        return canUpdateAltText;
    }

    /**
     * If inventory item can change status of alt text.
     *
     * @param canUpdateAltText true/false
     */
    public void setCanUpdateAltText(final boolean canUpdateAltText) {
        this.canUpdateAltText = canUpdateAltText;
    }

    /**
     * If default at startup.
     *
     * @return true/false
     */
    public boolean isDefaut() {
        return defaut;
    }

    /**
     * If default at startip.
     *
     * @param defaut
     */
    public void setDefaut(final boolean defaut) {
        this.defaut = defaut;
    }


}
