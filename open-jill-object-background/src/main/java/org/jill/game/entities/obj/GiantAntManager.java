package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.game.entities.obj.abs.AbstractHitPlayerObjectEntity;
import org.jill.game.entities.obj.util.UtilityObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryPointMessage;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;

/**
 * Firebird.
 *
 * @author Emeric MARTINEAU
 */
public final class GiantAntManager extends AbstractHitPlayerObjectEntity {

    /**
     * Picture array.
     */
    private BufferedImage[] leftImages;

    /**
     * Picture array.
     */
    private BufferedImage[] rightImages;

    /**
     * Kill message.
     */
    private ObjectListMessage killme;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * Number image to show left/right animation.
     */
    private int nbImagePerSideToDisplay;

    /**
     * State to move.
     */
    private int stateMove;

    /**
     * State to turn.
     */
    private int stateTurn;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        setKillabgeObject(true);

        this.stateMove = getConfInteger("stateMove");
        this.stateTurn = getConfInteger("stateTurn");

        // Init list of picture
        final int tileSetIndex = getConfInteger("tileSet");

        final int numberTileSet = getConfInteger("numberTileSet");

        final int nbImagePerSide = numberTileSet  / 2;

        // Alloc array of picture
        this.leftImages = new BufferedImage[nbImagePerSide];
        this.rightImages = new BufferedImage[nbImagePerSide];

        // Last picture is turn image
        this.nbImagePerSideToDisplay = nbImagePerSide - 1;

        // Init Right
        initPicture(this.rightImages, nbImagePerSide, tileSetIndex, 0);

        // Init Left
        initPicture(this.leftImages, nbImagePerSide, tileSetIndex,
                nbImagePerSide);

        this.killme = new ObjectListMessage(this, false);

        this.backgroundObject = objectParam.getBackgroundObject();
    }

    /**
     * Init picutre.
     *
     * @param images image array
     * @param numberTileSet number of tile
     * @param tileSetIndex tile set index
     * @param tileIndex tile index start
     */
    private void initPicture(final BufferedImage[] images,
        final int numberTileSet, final int tileSetIndex, final int tileIndex) {

        final int end = tileIndex + numberTileSet;

        for (int index = tileIndex; index < end; index++) {
            images[index - tileIndex] = this.pictureCache.getImage(tileSetIndex,
                index);
        }
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        // State = 0 -> move
        if (this.state == 0) {
            moveAnt();
        } else {
            this.state--;
        }
    }

    /**
     * Move ant.
     */
    private void moveAnt() {
        if ((this.xSpeed < ObjectEntity.X_SPEED_MIDDLE)
                && UtilityObjectEntity.moveObjectLeftOnFloor(this, this.xSpeed,
                        this.backgroundObject)
                || (this.xSpeed > ObjectEntity.X_SPEED_MIDDLE)
                && UtilityObjectEntity.moveObjectRightOnFloor(this, this.xSpeed,
                        this.backgroundObject)) {
            this.x += this.xSpeed;

            this.counter++;

            if (this.counter == this.nbImagePerSideToDisplay) {
                this.counter = 0;
            }
        } else {
            this.state = this.stateTurn;

            // Change way
            this.xSpeed *= -1;
            this.counter = 0;
        }
    }

    @Override
    public BufferedImage msgDraw() {
        BufferedImage[] currentPictureArray;

        if (this.xSpeed > 0) {
            // Right
            currentPictureArray = this.rightImages;
        } else {
            // Left
            currentPictureArray = this.leftImages;
        }

        BufferedImage img;

        if (this.state == this.stateMove) {
            img = currentPictureArray[this.counter];
        } else {
            // Turn picture is last picture
            img = currentPictureArray[currentPictureArray.length - 1];
        }

        return img;
    }

    @Override
    public void msgTouch(final ObjectEntity obj,
            final KeyboardLayout keyboardLayout) {
        if (obj.isPlayer()) {
            hitPlayer(obj);
        }
    }

    @Override
    public void msgKill(final ObjectEntity sender,
        final int nbLife, final int typeOfDeath) {
        this.messageDispatcher.sendMessage(EnumMessageType.INVENTORY_POINT,
            new InventoryPointMessage(getConfInteger("point"), true,
                    this, sender));
        this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
            this.killme);
    }
}
