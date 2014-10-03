package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryItemMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryPointMessage;

/**
 * Rockkey for mapdoor.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractKeyManager
    extends AbstractParameterObjectEntity {

    /**
     * Inventory object to add.
     */
    private InventoryItemMessage inventory;

    /**
     * To remove this object from object list.
     */
    private ObjectListMessage killme;

    /**
     * Picture array.
     */
    private BufferedImage[] images;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public final void init(final ObjectParam objectParam) {
        super.init(objectParam);

        this.inventory = new InventoryItemMessage(
                getConfString("inventory"), true);

        // Remove me from list of object (= kill me)
        this.killme = new ObjectListMessage(this, false);

        loadPicture();
    }

    /**
     * Load picture.
     */
    private void loadPicture() {
        int tileIndex = getConfInteger("tile");
        int tileSetIndex = getConfInteger("tileSet");

        int numberTileSet = getConfInteger("numberTileSet");

        // Load picture for each object. Don't use cache cause some picture
        // change between jill episod.
        this.images
            = new BufferedImage[numberTileSet * 2];

        int indexArray = 0;

        for (int index = 0; index < numberTileSet; index++) {
            this.images[indexArray]
                = this.pictureCache.getImage(tileSetIndex, tileIndex
                    + index);
            this.images[indexArray + 1] = this.images[indexArray];

            indexArray += 2;
        }
    }

    @Override
    public final void msgTouch(final ObjectEntity obj,
            final KeyboardLayout keyboardLayout) {
        if (obj.isPlayer()) {
            this.messageDispatcher.sendMessage(EnumMessageType.INVENTORY_ITEM,
                this.inventory);
            this.messageDispatcher.sendMessage(EnumMessageType.INVENTORY_POINT,
                new InventoryPointMessage(
                    getConfInteger("point"), true, this, obj));

            if (getDisplayMessage()) {
                sendMessage();
            }

            messageDispatcher.sendMessage(EnumMessageType.OBJECT, this.killme);
        }
    }

    @Override
    public final BufferedImage msgDraw() {
        return this.images[this.counter];
    }

    /**
     * Call to update.
     */
    @Override
    public final void msgUpdate(final KeyboardLayout keyboardLayout) {
        this.counter++;

        if (this.counter >= this.images.length) {
            this.counter = 0;
        }
    }

    /**
     * Return if display message in status bar.
     *
     * @return if display message.
     */
    protected abstract boolean getDisplayMessage();
}
