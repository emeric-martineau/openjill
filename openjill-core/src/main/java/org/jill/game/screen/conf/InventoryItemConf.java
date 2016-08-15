
package org.jill.game.screen.conf;

/**
 * This file alow to setup picture configuration of item in list to display in
 * inventory and item in special case, like firebird/fish...
 *
 * Firebird/Fish... not show in item and not store in save game but we store in
 * item list to create weapon.
 *
 * @author Emeric MARTINEAU
 */
public class InventoryItemConf extends PictureConf {
    /**
     * Display item ?
     */
    private boolean display = true;

    /**
     * If display in item list.
     *
     * @return true/false
     */
    public boolean isDisplay() {
        return display;
    }

    /**
     * If display in item list.
     *
     * @param display set display
     */
    public void setDisplay(final boolean display) {
        this.display = display;
    }


}
