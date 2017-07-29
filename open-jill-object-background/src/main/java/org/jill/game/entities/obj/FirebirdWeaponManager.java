package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.game.entities.obj.util.UtilityObjectEntity;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.entities.ObjectEntity;
import static org.jill.openjill.core.api.entities.ObjectEntity.X_SPEED_MIDDLE;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;

/**
 * Firebird weapon object.
 *
 * TODO why counter/state/substate is alway same value ?
 * TODO it-it destroy when out of screen ?
 *
 * @author Emeric MARTINEAU
 */
public final class FirebirdWeaponManager extends AbstractParameterObjectEntity {

    /**
     * To remove this object from object lis.
     */
    private ObjectListMessage killme;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

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
            = new BufferedImage[numberTileSet];

        for (int index = 0; index < numberTileSet; index++) {
            this.images[index]
                = this.pictureCache.getImage(tileSetIndex, tileIndex
                    + index);
        }

        // Remove me from list of object (= kill me)
        this.killme = new ObjectListMessage(this, false);

        this.backgroundObject = objectParam.getBackgroundObject();

        if (getWidth() == 0 || getHeight() == 0) {
            setWidth(this.images[0].getWidth());
            setHeight(this.images[0].getHeight());
        }
    }

    @Override
    public void msgTouch(final ObjectEntity obj,
            final KeyboardLayout keyboardLayout) {
        if (!obj.isPlayer() && obj.isKillableObject()) {
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
        setCounter(getCounter() + 1);
        setState(getCounter());
        setSubState(getCounter());

        if (this.counter >= this.images.length) {
            setCounter(0);
            setState(0);
            setSubState(0);
        }

        if (!((getxSpeed() < X_SPEED_MIDDLE
                && UtilityObjectEntity.moveObjectLeft(this, getxSpeed(),
                        backgroundObject))
            || (getxSpeed() > X_SPEED_MIDDLE
                && UtilityObjectEntity.moveObjectRight(this, getxSpeed(),
                        backgroundObject)))) {
            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT, killme);
        }
    }

    @Override
    public void setInfo1(final int info1) {
        final int initXD = getConfInteger("initXD");
        final int offsetX = getConfInteger("offsetX");

        // this.xSpeed is -1 or 1 to know way to go
        setxSpeed(initXD * info1);

        setX(getX() + (offsetX * info1));

        this.info1 = 0;
    }
}
