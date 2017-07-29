package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;

import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.ObjectListMessage;

/**
 * Firebird.
 *
 * @author Emeric MARTINEAU
 */
public final class HitFireManager extends AbstractParameterObjectEntity {

    /**
     * Picture array.
     */
    private BufferedImage[] images;

    /**
     * Kill message.
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

        // Init list of picture
        final int tileIndex = getConfInteger("tile");
        final int tileSetIndex = getConfInteger("tileSet");
        final int numberTileSet = getConfInteger("numberTileSet");

        // Alloc array of picture
        this.images = new BufferedImage[(numberTileSet * 2) - 1];

        // Base tileset
        int baseTileIndex = tileIndex;

        for (int index = numberTileSet - 1; index < this.images.length;
             index++) {
            this.images[index] = this.pictureCache.getImage(tileSetIndex,
                    baseTileIndex);
            baseTileIndex++;
        }

        // Middle index to stop
        final int middleIndex = numberTileSet - 1;
        // Max index in array
        final int baseIndex = this.images.length - 1;

        for (int index = 0; index < middleIndex; index++) {
            this.images[index] = this.images[baseIndex - index];
        }

        this.killme = new ObjectListMessage(this, false);

        // Create at runtime, check width height
        if (this.width == 0 || this.height == 0) {
            this.width = this.images[0].getWidth();
            this.height = this.images[0].getHeight();
        }
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        this.counter++;

        if (this.counter == this.images.length) {
            this.messageDispatcher.sendMessage(
                    EnumMessageType.OBJECT, killme);

            // To prevend IndexOutBoundException
            this.counter--;
        }
    }

    @Override
    public BufferedImage msgDraw() {
        return this.images[this.counter];
    }
}
