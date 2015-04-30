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
 * Crab.
 *
 * @author Emeric MARTINEAU
 */
public final class CrabManager extends AbstractHitPlayerObjectEntity {

    /**
     * Picture array.
     */
    private BufferedImage[] images;

    /**
     * Kill message.
     */
    private ObjectListMessage killme;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * State to move.
     */
    private int stateUpDown;

    /**
     * Size of move up/down.
     */
    private int downUpMvtSize;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        setKillabgeObject(true);

        this.stateUpDown = getConfInteger("stateUpDown");
        this.downUpMvtSize = getConfInteger("downUpMvtSize");

        // Init list of picture
        final int tileSetIndex = getConfInteger("tileSet");
        final int tileIndex = getConfInteger("tile");

        final int numberTileSet = getConfInteger("numberTileSet");

        // Alloc array of picture
        this.images = new BufferedImage[numberTileSet];

        // Init Right
        initPicture(this.images, numberTileSet, tileSetIndex, tileIndex);

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
            images[index] = this.pictureCache.getImage(tileSetIndex,
                index);
        }
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        this.counter++;

        if (this.counter == this.images.length) {
            this.counter = 0;
        }

        // State = 0 -> move
        if (this.getState() == this.stateUpDown) {
            moveUpDown();
        } else {
            moveFloor();

            if (UtilityObjectEntity.isClimbing(this,
                    this.backgroundObject)) {
                setState((int) (Math.random() * this.stateUpDown + 0.1));
                // Always start up
                setySpeed(-1 * this.downUpMvtSize);
            }
        }
    }

    /**
     * Move ant.
     */
    private void moveFloor() {
        if (!((this.xSpeed < ObjectEntity.X_SPEED_MIDDLE)
                && UtilityObjectEntity.moveObjectLeftOnFloor(this, this.xSpeed,
                        this.backgroundObject)
                || (this.xSpeed > ObjectEntity.X_SPEED_MIDDLE)
                && UtilityObjectEntity.moveObjectRightOnFloor(this, this.xSpeed,
                        this.backgroundObject))) {
            // Change way
            this.xSpeed *= -1;
            this.counter = 0;
        }
    }

    /**
     * Move up/down.
     */
    private void moveUpDown() {
        if (this.ySpeed < ObjectEntity.Y_SPEED_MIDDLE) {
            if (!UtilityObjectEntity.moveObjectUp(this, this.ySpeed,
                        this.backgroundObject)) {
                this.ySpeed *= -1;
            }
        } else if (this.ySpeed > ObjectEntity.Y_SPEED_MIDDLE) {
            if (!UtilityObjectEntity.moveObjectDown(this, this.ySpeed,
                        this.backgroundObject)) {
                setState(0);
                setySpeed(0);
            }
        }
    }

    @Override
    public BufferedImage msgDraw() {
        return this.images[this.getCounter()];
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
