package org.jill.game.entities.obj;

import org.jill.game.entities.obj.abs.AbstractSynchronisedImageObjectEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.statusbar.StatusBarTextMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryItemMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryPointMessage;

/**
 * Rockkey for mapdoor.
 *
 * @author Emeric MARTINEAU
 */
public final class RedKeyManager extends AbstractSynchronisedImageObjectEntity {

    /**
     * Message to display in bottom of screen.
     */
    private StatusBarTextMessage msg;

    /**
     * Inventory object to add.
     */
    private InventoryItemMessage inventory;

    /**
     * Point.
     */
    private InventoryPointMessage point;

    /**
     * To remove this object from object list.
     */
    private ObjectListMessage killme;

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        String textMsg = getConfString("msg");
        int textColor = getConfInteger("msgColor");
        int textTime = getConfInteger("msgTime");

        this.msg = new StatusBarTextMessage(textMsg, textTime, textColor);

        this.inventory = new InventoryItemMessage(
                getConfString("inventory"), true);

        // Remove me from list of object (= kill me)
        this.killme = new ObjectListMessage(this, false);

        // Point
        this.point = new InventoryPointMessage(getConfInteger("point"),
                true);
    }

    @Override
    public void msgTouch(final ObjectEntity obj) {
        if (obj.isPlayer()) {
            this.messageDispatcher.sendMessage(EnumMessageType.INVENTORY_ITEM,
                this.inventory);
            this.messageDispatcher.sendMessage(EnumMessageType.INVENTORY_POINT,
                this.point);
            this.messageDispatcher.sendMessage(
                EnumMessageType.MESSAGE_STATUS_BAR, msg);
            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT, killme);
        }
    }
}
