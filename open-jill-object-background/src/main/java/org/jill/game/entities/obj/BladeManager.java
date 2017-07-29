package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;

import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.game.entities.obj.util.UtilityObjectEntity;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.ObjectListMessage;

/**
 * Blade object.
 *
 * @author Emeric MARTINEAU
 */
public final class BladeManager extends AbstractParameterObjectEntity {
    /**
     * To remove this object from object lis.
     */
    private ObjectListMessage killme;

    /**
     * Picture array.
     */
    private BufferedImage[] images;

    /**
     * SubState value to remove blade.
     */
    private int subStateToRemoveMe;

    /**
     * Statecount to start launch.
     */
    private int subStateLaunchStart;

    /**
     * Statecount to end launch.
     */
    private int subStateLaunchEnd;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

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

        this.subStateToRemoveMe = getConfInteger("subStateToRemoveMe");
        this.subStateLaunchStart = getConfInteger("subStateLaunchStart");
        this.subStateLaunchEnd = getConfInteger("subStateLaunchEnd");

        final int initXD = getConfInteger("initXD");
        final int initY = getConfInteger("initY");
        final int initX = getConfInteger("initX");

        // Load picture for each object. Don't use cache cause some picture
        // change between jill episod.
        this.images
                = new BufferedImage[numberTileSet];

        for (int index = 0; index < numberTileSet; index++) {
            this.images[index]
                    = this.pictureCache.getImage(tileSetIndex, tileIndex
                    + index);
        }

        this.backgroundObject = objectParam.getBackgroundObject();

        // Remove me from list of object (= kill me)
        this.killme = new ObjectListMessage(this, false);

        setRemoveOutOfVisibleScreen(true);

        // Blade can be create by player, check width height
        if (getWidth() == 0 || getHeight() == 0) {
            this.setWidth(this.images[0].getWidth());
            this.setHeight(this.images[0].getHeight());

            // Blade have not same y tha player
            this.y += initY;
            this.x += initX * this.info1;

            // this.xSpeed is -1 or 1 to know way to go
            setxSpeed(initXD * this.info1);

            setInfo1(0);
        }
    }


    @Override
    public void msgTouch(final ObjectEntity obj,
            final KeyboardLayout keyboardLayout) {
        if (obj.isPlayer() && !(getSubState() >= this.subStateLaunchStart
                && getSubState() <= this.subStateLaunchEnd)) {
            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT, killme);
        } else if (obj.isKillableObject()) {
            obj.msgKill(this, 0, 0);
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
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        setCounter(getCounter() - 1);
        setSubState(getSubState() + 1);

        if (getCounter() == -1) {
            setCounter(this.images.length - 1);
        }

        // Remove blade
        if (getSubState() >= this.subStateToRemoveMe) {
            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT, killme);
        }

        moveLeftRight();

        moveUpDown();
    }

    /**
     * Move blade up or down.
     */
    private void moveUpDown() {
        // If blade can't move fully, way not change.
        setySpeed(getySpeed() + 1);

        // Move blade
        if (getySpeed() > Y_SPEED_MIDDLE) {
            // Move down
            if (!UtilityObjectEntity.moveObjectDownWithIgnoreStair(this, getySpeed(),
                    backgroundObject)) {
                setySpeed(getySpeed() * -1);
            }
        } else {
            // Move up
            if (!UtilityObjectEntity.moveObjectUp(this, getySpeed(),
                    backgroundObject)) {
                setySpeed(getySpeed() * -1);
            }
        }
    }

    /**
     * Move blade left or right.
     */
    private void moveLeftRight() {
        // If blade can't move fully, way not change.
        // Way change only when blade don't move.
        if ((getxSpeed() > X_SPEED_MIDDLE
                && !UtilityObjectEntity.moveObjectRight(this, getxSpeed(),
                backgroundObject))
                || (getxSpeed() < X_SPEED_MIDDLE
                && !UtilityObjectEntity.moveObjectLeft(this, getxSpeed(),
                backgroundObject))) {
            setxSpeed(getxSpeed() * -1);
        }
    }
}
