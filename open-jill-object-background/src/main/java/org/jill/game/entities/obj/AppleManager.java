package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryPointMessage;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.message.EnumMessageType;

/**
 * Apple object.
 *
 * @author Emeric MARTINEAU
 */
public final class AppleManager extends AbstractParameterObjectEntity {

    /**
     * To remove this object from object lis.
     */
    private ObjectListMessage killme;

    /**
     * Picture array.
     */
    private BufferedImage[] images;

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        int tileIndex = getConfInteger("tile");
        int tileSetIndex = getConfInteger("tileSet");

        int numberTileSet = getConfInteger("numberTileSet");

        // Load picture for each object. Don't use cache cause some picture
        // change between jill episod.
        this.images
            = new BufferedImage[numberTileSet * 2];

        int indexArray = (numberTileSet * 2) - 1;

        for (int index = 0; index < numberTileSet; index++) {
            this.images[indexArray]
                = this.pictureCache.getImage(tileSetIndex, tileIndex
                    + index);
            this.images[indexArray - 1] = this.images[indexArray];

            indexArray -= 2;
        }

        // Remove me from list of object (= kill me)
        this.killme = new ObjectListMessage(this, false);
    }

    @Override
    public void msgTouch(final ObjectEntity obj) {
        if (obj.isPlayer()) {
            this.messageDispatcher.sendMessage(EnumMessageType.INVENTORY_POINT,
                    new InventoryPointMessage(getConfInteger("point"), true,
                    this, obj));
            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT, killme);
        }
    }

    @Override
    public BufferedImage msgDraw() {
        return this.images[this.counter];
    }

    /**
     * Call to update.
     */
    @Override
    public void msgUpdate() {
        this.counter++;

        if (this.counter >= this.images.length) {
            this.counter = 0;
        }
    }
}
