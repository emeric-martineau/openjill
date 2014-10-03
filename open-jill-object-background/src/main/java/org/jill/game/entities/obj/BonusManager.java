package org.jill.game.entities.obj;

import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import java.awt.image.BufferedImage;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.
        EnumInventoryObject;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryItemMessage;

/**
 * Bonus item manager.
 *
 * @author Emeric MARTINEAU
 */
public final class BonusManager extends AbstractParameterObjectEntity {

    /**
     * Picture array.
     */
    private BufferedImage images;

    /**
     * Inventory object to add.
     */
    private InventoryItemMessage inventory;

    /**
     * To remove this object from object list.
     */
    private ObjectListMessage killme;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        final EnumInventoryObject[] enumList
                = EnumInventoryObject.getEnumList();

        final String nameOfInventoryItem = enumList[counter].toString();

        final String key = getConfString(nameOfInventoryItem);
        final String[] keySplit = key.split(",");

        // Init list of picture
        final int tileIndex = Integer.valueOf(keySplit[1]);
        final int tileSetIndex = Integer.valueOf(keySplit[0]);

        this.images = this.pictureCache.getImage(tileSetIndex, tileIndex);

        final String dontRemove = getConfString("dontRemove");

        if (dontRemove.contains(nameOfInventoryItem)) {
            this.inventory = new InventoryItemMessage(enumList[this.counter],
                true, true);
        } else {
            this.inventory = new InventoryItemMessage(enumList[this.counter],
                true);
            // Remove me from list of object (= kill me)
            this.killme = new ObjectListMessage(this, false);
        }
    }

    @Override
    public BufferedImage msgDraw() {
        return this.images;
    }

    @Override
    public void msgTouch(final ObjectEntity obj,
            final KeyboardLayout keyboardLayout) {
        if (obj.isPlayer()) {
            sendItem();

            this.messageDispatcher.sendMessage(
                EnumMessageType.INVENTORY_ITEM,
                this.inventory);
        }
    }

    /**
     * Send item.
     */
    private void sendItem() {
        // If don't remove, don't send it.
        if (this.killme == null) {
            // Special case.
            // Remove all unremovable
            // TODO how manage it ?

        } else {
            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                    this.killme);
        }
    }
}
