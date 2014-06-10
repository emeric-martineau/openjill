package org.jill.game.gui.menu.conf;

/**
 * Highcore menu configuration.
 *
 * @author Emeric MARTINEAU
 */
public final class LoadSaveGameMenuConf extends AbstractAdvancedMenuConf {
    /**
     * Display when name is not set.
     */
    private String emptyName;

    /**
     * Position to start draw cursor.
     */
    private TextPosition startCursorEdit;

    /**
     * Display when name is not set.
     *
     * @return text
     */
    public String getEmptyName() {
        return emptyName;
    }

    /**
     * Display when name is not set.
     *
     * @param en value to display when name is not set
     */
    public void setEmptyName(final String en) {
        this.emptyName = en;
    }


    /**
     * Position to start draw cursor.
     *
     * @return position
     */
    public TextPosition getStartCursorEdit() {
        return startCursorEdit;
    }

    /**
     * Position to start draw cursor.
     *
     * @param sc position
     */
    public void setStartCursorEdit(final TextPosition sc) {
        this.startCursorEdit = sc;
    }
}
