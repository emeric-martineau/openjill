package org.jill.game.gui.menu.conf;

import org.jill.game.screen.conf.AbstractLineTextConf;

/**
 * Advanced menu configuration.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractAdvancedMenuConf extends AbstractLineTextConf {
    /**
     * Edit mode configuation.
     */
    private ModeConf editmode;

    /**
     * Readonly mod configuration.
     */
    private ModeConf readonlymode;

    /**
     * Position to start draw text.
     */
    private TextPosition startText;

    /**
     * Position to start draw cursor.
     */
    private TextPosition startCursor;

    /**
     * Edit mode configuation.
     *
     * @return configuration
     */
    public final ModeConf getEditmode() {
        return editmode;
    }

    /**
     * Edit mode configuation.
     *
     * @param em configuration
     */
    public final void setEditmode(final ModeConf em) {
        this.editmode = em;
    }

    /**
     * Readonly mod configuration.
     *
     * @return configuration
     */
    public final ModeConf getReadonlymode() {
        return readonlymode;
    }

    /**
     * Readonly mod configuration.
     *
     * @param rom configuration
     */
    public final void setReadonlymode(final ModeConf rom) {
        this.readonlymode = rom;
    }

    /**
     * Position to start draw text.
     *
     * @return position
     */
    public final TextPosition getStartText() {
        return startText;
    }

    /**
     * Position to start draw text.
     *
     * @param st position
     */
    public final void setStartText(final TextPosition st) {
        this.startText = st;
    }

    /**
     * Position to start draw cursor.
     *
     * @return position
     */
    public final TextPosition getStartCursor() {
        return startCursor;
    }

    /**
     * Position to start draw cursor.
     *
     * @param sc position
     */
    public final void setStartCursor(final TextPosition sc) {
        this.startCursor = sc;
    }
}
