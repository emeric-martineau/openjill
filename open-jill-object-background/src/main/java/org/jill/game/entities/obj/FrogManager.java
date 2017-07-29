package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;

import org.jill.game.entities.obj.abs.AbstractHitPlayerObjectEntity;
import org.jill.game.entities.obj.player.PlayerPositionSynchronizer;
import org.jill.game.entities.obj.util.UtilityObjectEntity;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryPointMessage;

/**
 * Frog manager.
 *
 * @author Emeric MARTINEAU
 */
public final class FrogManager extends AbstractHitPlayerObjectEntity {

    /**
     * Player position object.
     */
    private static final PlayerPositionSynchronizer PLAYER_POSITION
            = PlayerPositionSynchronizer.getInstance();

    /**
     * Value of state to know if on floor.
     */
    private int stateOnFloor;

    /**
     * Value of state to know if currently jumping.
     */
    private int stateOnJump;

    /**
     * To change picture before jump.
     */
    private int ySpeedChangePicture;

    /**
     * Max value when frog fall.
     */
    private int ySpeedMax;

    /**
     * X speed value.
     */
    private int xSpeedMax;

    /**
     * Counter value to jump.
     */
    private int counterBeforeJump;

    /**
     * Picture array.
     */
    private BufferedImage[] leftImages;

    /**
     * Picture array.
     */
    private BufferedImage[] rightImages;

    /**
     * To remove this object from object list.
     */
    private ObjectListMessage killme;

    /**
     * To get player position.
     */
    private int indexEtat = 0;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

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

        this.stateOnFloor = getConfInteger("stateOnFloor");
        this.stateOnJump = getConfInteger("stateOnJump");
        this.ySpeedChangePicture = getConfInteger("ySpeedChangePicture");
        this.ySpeedMax = getConfInteger("ySpeedMax");
        this.xSpeedMax = getConfInteger("xSpeedMax");
        this.counterBeforeJump = getConfInteger("counterBeforeJump");

        final int numberTileSet = getConfInteger("numberTileSet");

        final int middle = numberTileSet / 2;

        this.leftImages = new BufferedImage[middle];
        this.rightImages = new BufferedImage[middle];

        for (int index = 0; index < middle; index++) {
            this.rightImages[index] = this.pictureCache.getImage(
                    tileSetIndex, tileIndex + index);
        }

        for (int index = 0; index < middle; index++) {
            this.leftImages[index] = this.pictureCache.getImage(
                    tileSetIndex, tileIndex + index + middle);
        }

        this.killme = new ObjectListMessage(this, false);

        this.backgroundObject = objectParam.getBackgroundObject();

        setKillabgeObject(true);
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        if (this.state == this.stateOnFloor) {
            // Wait to jump
            this.counter++;

            if (this.counter == this.counterBeforeJump) {
                this.indexEtat = PLAYER_POSITION.updatePlayerPosition(
                        this.messageDispatcher, this.indexEtat);

                final int xd = PLAYER_POSITION.getX() - this.x;

                this.state = this.stateOnJump;
                this.ySpeed = this.ySpeedChangePicture;
                this.xSpeed = this.xSpeedMax;

                if (xd < 0) {
                    // Correct way
                    this.xSpeed *= -1;
                }

                // Picture have size greater then previous
                this.y--;
            }
        } else {
            if (this.xSpeed < X_SPEED_MIDDLE) {
                UtilityObjectEntity.moveObjectLeft(this, this.xSpeed,
                        this.backgroundObject);
            } else {
                UtilityObjectEntity.moveObjectRight(this, this.xSpeed,
                        this.backgroundObject);
            }

            if (this.ySpeed < Y_SPEED_MIDDLE) {
                if (!UtilityObjectEntity.moveObjectUp(this, this.ySpeed,
                        this.backgroundObject)) {
                    this.ySpeed = Y_SPEED_MIDDLE;
                }
            } else if (this.ySpeed > Y_SPEED_MIDDLE) {
//                this.y += this.ySpeed;
//
//                if (UtilityObjectEntity.checkIfObjectHitFloor(this,
//                    this.backgroundObject)) {
//                    this.state = this.stateOnFloor;
//                    this.counter = 0;
//                }

                if (!UtilityObjectEntity.moveObjectDown(this, this.ySpeed,
                        this.backgroundObject)) {
                    this.state = this.stateOnFloor;
                    this.counter = 0;
                }
            }

            if (this.ySpeed < this.ySpeedMax) {
                this.ySpeed++;
            }
        }
    }

    @Override
    public BufferedImage msgDraw() {
        BufferedImage image;
        int indexPicutre;

        if (this.state == this.stateOnFloor) {
            indexPicutre = 0;
        } else if (this.ySpeed == 0) {
            indexPicutre = 2;
        } else {
            indexPicutre = 1;
        }

        if (this.xSpeed < 0) {
            image = this.leftImages[indexPicutre];
        } else {
            image = this.rightImages[indexPicutre];
        }

        this.width = image.getWidth();
        this.height = image.getHeight();

        return image;
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
