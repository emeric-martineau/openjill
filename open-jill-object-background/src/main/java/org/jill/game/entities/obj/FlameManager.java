package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;

import org.jill.game.entities.obj.abs.AbstractFireHitPlayerObject;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;

/**
 * Apple object.
 *
 * @author Emeric MARTINEAU
 */
public final class FlameManager extends AbstractFireHitPlayerObject {
    /**
     * Picture array.
     */
    private BufferedImage[] imagesUp;

    /**
     * Picture array.
     */
    private BufferedImage[] imagesDown;

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

        this.imagesUp
                = new BufferedImage[numberTileSet * 2];
        this.imagesDown
                = new BufferedImage[this.imagesUp.length];

        initPicture(this.imagesUp, numberTileSet, tileSetIndex, tileIndex);
        initPicture(this.imagesDown, numberTileSet, tileSetIndex,
                tileIndex + numberTileSet);

        if (getWidth() == 0 || getHeight() == 0) {
            setHeight(this.imagesUp[0].getHeight());
            setWidth(this.imagesUp[0].getWidth());
        }

    }

    /**
     * Load picture.
     *
     * @param images        picture arry
     * @param numberTileSet number of tileset
     * @param tileSetIndex  tileset
     * @param tileIndex     tile
     */
    private void initPicture(final BufferedImage[] images,
            final int numberTileSet, final int tileSetIndex,
            final int tileIndex) {
        int indexArray = 0;

        for (int index = 0; index < numberTileSet; index++) {
            images[indexArray]
                    = this.pictureCache.getImage(tileSetIndex, tileIndex
                    + index);
            images[indexArray + 1] = images[indexArray];

            indexArray += 2;
        }
    }

    @Override
    public BufferedImage msgDraw() {
        BufferedImage[] img;

        if (this.getySpeed() > Y_SPEED_MIDDLE) {
            img = this.imagesDown;
        } else {
            img = this.imagesUp;
        }

        return img[this.counter];
    }

    /**
     * Call to update.
     */
    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        this.counter++;

        BufferedImage[] img;

        if (this.getySpeed() > Y_SPEED_MIDDLE) {
            img = this.imagesDown;
        } else {
            img = this.imagesUp;
        }

        if (this.counter >= img.length) {
            this.counter--;
            killMe();
        }
    }

    @Override
    protected void touchPlayer(final ObjectEntity obj,
            final KeyboardLayout keyboardLayout) {
    }
}
