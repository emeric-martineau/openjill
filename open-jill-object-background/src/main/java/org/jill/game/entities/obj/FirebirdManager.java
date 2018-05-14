package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import java.util.Optional;

import org.jill.game.entities.obj.abs.AbstractFireHitPlayerObject;
import org.jill.game.entities.obj.bullet.BulletObjectFactory;
import org.jill.game.entities.obj.util.UtilityObjectEntity;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryPointMessage;

/**
 * Firebird.
 *
 * @author Emeric MARTINEAU
 */
public final class FirebirdManager extends AbstractFireHitPlayerObject {

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
    private Optional<BufferedImage>[] leftImages;

    /**
     * Picture array.
     */
    private Optional<BufferedImage>[] rightImages;

    /**
     * Index of turn picture.
     */
    private int turnIndexPicture;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * Colored bullet when player hit firebird.
     */
    private int nbColoredBullet;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        setKillabgeObject(true);

        // Init list of picture
        final int tileIndex = getConfInteger("tile");
        final int tileSetIndex = getConfInteger("tileSet");

        final int baseTileNumber = getConfInteger("baseTileNumber");
        final int turnTileNumber = getConfInteger("turnTileNumber");

        // Number picture for one side
        final int numberPicturePerSide = (baseTileNumber * NUMBER_OF_SIDE)
                - NUMBER_PICTURE_TO_REMOVE + turnTileNumber;

        this.turnIndexPicture = numberPicturePerSide - 1;

        // Tile index for turn picture
        final int tileTurnIndex = tileIndex + (baseTileNumber
                * NUMBER_OF_SIDE);

        // Alloc array of picture
        this.leftImages = new Optional[numberPicturePerSide];
        this.rightImages = new Optional[numberPicturePerSide];

        // Init Right
        initPicture(this.rightImages, baseTileNumber, tileSetIndex,
                tileIndex);
        this.rightImages[this.turnIndexPicture] =
                this.pictureCache.getImage(tileSetIndex, tileTurnIndex);

        // Init Left
        initPicture(this.leftImages, baseTileNumber, tileSetIndex,
                tileIndex + baseTileNumber);
        this.leftImages[this.turnIndexPicture] = this.pictureCache.getImage(
                tileSetIndex, tileTurnIndex + turnTileNumber);

        // Search block
        this.backgroundObject = objectParam.getBackgroundObject();

        setKillabgeObject(true);

        this.nbColoredBullet = getConfInteger("nbColoredBullet");
    }


    /**
     * Init picture level.
     *
     * @param images         picture array
     * @param baseTileNumber number
     * @param tileSetIndex   tileset
     * @param tileIndex      tile
     */
    private void initPicture(final Optional<BufferedImage>[] images,
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
        if (this.counter >= this.turnIndexPicture) {
            // Actually picture turn, change way
            this.xSpeed *= -1;
            this.counter = 0;
        } else {
            // Go to left
            this.counter++;

            // Turn picture, but we want continue to move.
            if (this.counter >= this.turnIndexPicture) {
                this.counter = 0;
            }

            if ((this.xSpeed < ObjectEntity.X_SPEED_MIDDLE)
                    && !UtilityObjectEntity.moveObjectLeft(this, this.xSpeed,
                    this.backgroundObject)) {
                // Turn
                this.counter = this.turnIndexPicture;
            } else if ((this.xSpeed > ObjectEntity.X_SPEED_MIDDLE)
                    && !UtilityObjectEntity.moveObjectRight(this, this.xSpeed,
                    this.backgroundObject)) {
                // Turn
                this.counter = this.turnIndexPicture;
            }
        }
    }

    @Override
    public Optional<BufferedImage> msgDraw() {
        Optional<BufferedImage>[] currentPictureArray;

        if (this.xSpeed < 0) {
            // Right
            currentPictureArray = this.leftImages;
        } else {
            // Left
            currentPictureArray = this.rightImages;
        }

        return currentPictureArray[this.counter];
    }

    @Override
    public void touchPlayer(final ObjectEntity obj,
            final KeyboardLayout keyboardLayout) {
        killMe();

        BulletObjectFactory.explode(this, this.nbColoredBullet,
                this.messageDispatcher);
    }

    @Override
    public void msgKill(final ObjectEntity sender,
            final int nbLife, final int typeOfDeath) {
        this.messageDispatcher.sendMessage(EnumMessageType.INVENTORY_POINT,
                new InventoryPointMessage(getConfInteger("point"), true,
                        this, sender));
        killMe();
    }
}
