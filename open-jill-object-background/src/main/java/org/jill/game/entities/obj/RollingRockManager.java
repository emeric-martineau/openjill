package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.game.entities.obj.abs.AbstractHitPlayerObjectEntity;
import org.jill.game.entities.obj.util.UtilityObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;

/**
 * Rolling rock.
 *
 * @author Emeric MARTINEAU
 */
public final class RollingRockManager extends AbstractHitPlayerObjectEntity {

    /**
     * Picture array.
     */
    private BufferedImage[] images;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * State to move.
     */
    private int counterFall;

    /**
     * YD when fall.
     */
    private int ySpeedFall;

    /**
     * YD max.
     */
    private int ySpeedMax;

    /**
     * Counter move max.
     */
    private int counterMoveMax;

    /**
     * Counter value start.
     */
    private int counterMoveValue;

    /**
     * XD when fall.
     */
    private int xSpeedFall;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        setKillabgeObject(false);

        this.counterFall = getConfInteger("counterFall");
        this.ySpeedFall = getConfInteger("ySpeedFall");
        this.ySpeedMax = getConfInteger("ySpeedMax");
        this.counterMoveMax = getConfInteger("counterMoveMax");
        this.counterMoveValue = getConfInteger("counterMoveValue");
        this.xSpeedFall = getConfInteger("xSpeedFall");

        // Init list of picture
        final int tileSetIndex = getConfInteger("tileSet");

        final int numberTileSet = getConfInteger("numberTileSet");

        // Alloc array of picture
        this.images = new BufferedImage[numberTileSet];
        // Init Right
        initPicture(this.images, tileSetIndex, getConfInteger("tile"));

        this.backgroundObject = objectParam.getBackgroundObject();
    }

    /**
     * Init picture.
     *
     * @param images image array
     * @param tileSetIndex tile set
     * @param tileIndex tile index start
     */
    private void initPicture(final BufferedImage[] images,
        final int tileSetIndex, final int tileIndex) {
        final int end = tileIndex + images.length;

        for (int index = tileIndex; index < end; index++) {
            images[index - tileIndex] = this.pictureCache.getImage(tileSetIndex,
                index);
        }
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        if (((getxSpeed() < X_SPEED_MIDDLE
                && UtilityObjectEntity.moveObjectLeft(this, getxSpeed(),
                        backgroundObject))
            || (getxSpeed() > X_SPEED_MIDDLE
                && UtilityObjectEntity.moveObjectRight(this, getxSpeed(),
                        backgroundObject)))) {

            if (getCounter() != this.counterFall) {
                setCounter(getCounter() + 1);

                if (getCounter() > this.counterMoveMax) {
                    setCounter(this.counterMoveValue);
                }
            }
        } else if (getxSpeed() == X_SPEED_MIDDLE) {
            if (UtilityObjectEntity.moveObjectRight(this, this.xSpeedFall,
                        backgroundObject)) {
                setxSpeed(this.xSpeedFall);
            } else {
                setxSpeed(-1 * this.xSpeedFall);
            }
        } else {
            setxSpeed(0);
        }

        int yd = getySpeed();

        if (yd == 0) {
            yd = this.ySpeedFall;
        }

        // Check if floor under object
        if (UtilityObjectEntity.moveObjectDown(this, yd,
                this.backgroundObject)) {
            setCounter(this.counterFall);

            if (getySpeed() < this.ySpeedMax) {
                setySpeed(getySpeed() + this.ySpeedFall);
            }
        } else if (getySpeed() > 0) {
            setySpeed(0);

            setCounter(this.counterMoveValue);

            setxSpeed(this.xSpeedFall);
        }
    }

    @Override
    public BufferedImage msgDraw() {
        return this.images[getCounter()];
    }

    @Override
    public void msgTouch(final ObjectEntity obj,
            final KeyboardLayout keyboardLayout) {
        if (obj.isPlayer()) {
            hitPlayer(obj);
        }
    }
}
