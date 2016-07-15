package org.jill.game.entities.obj.player.firebird;

import java.awt.image.BufferedImage;
import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;

/**
 * Firebird for player.
 *
 * @author Emeric MARTINEAU
 */
public final class FirebirdPlayerManager extends AbstractParameterObjectEntity {

    /**
     * Number of picture to remove from left/right.
     */
    private static final int NUMBER_PICTURE_TO_REMOVE = 2;

    /**
     * Number of side.
     */
    private static final int NUMBER_OF_SIDE = 2;

    /**
     * Picture array.
     */
    private BufferedImage[] leftImages;

    /**
     * Picture array.
     */
    private BufferedImage[] rightImages;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * Maximum falling player speed.
     */
    private int maxYSpeed;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        playerObject = true;

        // Init list of picture
        final int tileIndex = getConfInteger("tile");
        final int tileSetIndex = getConfInteger("tileSet");

        final int baseTileNumber = getConfInteger("baseTileNumber");

        // Number picture for one side
        final int numberPicturePerSide = (baseTileNumber * NUMBER_OF_SIDE)
                - NUMBER_PICTURE_TO_REMOVE;

        // Alloc array of picture
        this.leftImages = new BufferedImage[numberPicturePerSide];
        this.rightImages = new BufferedImage[numberPicturePerSide];

        // Init Right
        initPicture(this.rightImages, baseTileNumber, tileSetIndex,
                tileIndex);

        // Init Left
        initPicture(this.leftImages, baseTileNumber, tileSetIndex,
                tileIndex + baseTileNumber);

        // Search block
        this.backgroundObject = objectParam.getBackgroundObject();

        this.maxYSpeed = getConfInteger("maxYSpeed");
    }



    /**
     * Init picture level.
     *
     * @param images picture array
     * @param baseTileNumber number
     * @param tileSetIndex tileset
     * @param tileIndex tile
     */
    private void initPicture(final BufferedImage[] images,
        final int baseTileNumber, final int tileSetIndex,
        final int tileIndex) {
        for (int index = 0; index < baseTileNumber; index++) {
            images[index] = this.pictureCache.getImage(tileSetIndex,
                tileIndex + index);
        }

        int indexArray = baseTileNumber;

        for (int index = baseTileNumber - NUMBER_PICTURE_TO_REMOVE; index > 0;
            index--) {
            images[indexArray] = images[index];
            indexArray++;
        }
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        // Go to left
        this.counter++;

        // Turn picture, but we want continue to move.
        if (this.counter == this.leftImages.length) {
            this.counter = 0;
        }

        int newYSpeed = getySpeed() + 1;

        if (newYSpeed > this.maxYSpeed) {
            newYSpeed = this.maxYSpeed;
        }

        setySpeed(newYSpeed);
    }

    @Override
    public BufferedImage msgDraw() {
        BufferedImage[] currentPictureArray;

        if (this.xSpeed < 0) {
            // Right
            currentPictureArray = this.leftImages;
        } else {
            // Left
            currentPictureArray = this.rightImages;
        }

        return currentPictureArray[this.counter];
    }
}
