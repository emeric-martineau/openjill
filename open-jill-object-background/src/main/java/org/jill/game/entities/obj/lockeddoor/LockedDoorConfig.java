package org.jill.game.entities.obj.lockeddoor;

import org.jill.openjill.core.api.message.statusbar.inventory.EnumInventoryObject;

/**
 * Configuration object for;locked door.
 *
 * @author Emeric MARTINEAU
 */
public final class LockedDoorConfig {
    /**
     * Message when door open.
     */
    private final String openMessage;

    /**
     * Message when door is close.
     */
    private final String closeMessage;

    /**
     * Inventory item to open door.
     */
    private final EnumInventoryObject inventory;

    /**
     * To know if message must be display.
     */
    private boolean messageDisplayCloseMessage = true;

    /**
     * Constructor.
     *
     * @param openMsg message when door open
     * @param closeMsg message when door is closes
     * @param inv inventory item to open door
     */
    public LockedDoorConfig(final String openMsg, final String closeMsg,
            final EnumInventoryObject inv) {
        this.openMessage = openMsg;
        this.closeMessage = closeMsg;
        this.inventory = inv;
    }

    /**
     * Message.
     *
     * @return message
     */
    public String getOpenMessage() {
        return openMessage;
    }

    /**
     * Message.
     *
     * @return message
     */
    public String getCloseMessage() {
        return closeMessage;
    }

    /**
     * Inventory.
     *
     * @return message
     */
    public EnumInventoryObject getInventory() {
        return inventory;
    }

    /**
     * If close message must be display.
     *
     * @return true/false
     */
    public boolean isMessageDisplayCloseMessage() {
        return messageDisplayCloseMessage;
    }

    /**
     * If close message must be display.
     *
     * @param displayCloseMsg true/false
     */
    public void setMessageDisplayCloseMessage(boolean displayCloseMsg) {
        this.messageDisplayCloseMessage = displayCloseMsg;
    }
}
