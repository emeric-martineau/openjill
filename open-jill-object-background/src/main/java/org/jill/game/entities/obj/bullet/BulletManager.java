package org.jill.game.entities.obj.bullet;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.game.entities.obj.bees.MoveSizeAndInterval;
import org.jill.game.entities.obj.util.UtilityObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.ObjectListMessage;

/**
 * Bullet object.
 *
 * @author Emeric MARTINEAU
 */
public final class BulletManager extends AbstractParameterObjectEntity {

    /**
     * To remove this object from object lis.
     */
    private ObjectListMessage killme;

    /**
     * Picture array.
     */
    private BufferedImage[] images;

    /**
     * tile by state.
     */
    private List<MoveSizeAndInterval> tileByState;

    /**
     * Counter value to die bullet.
     */
    private int counterDie;

    /**
     * Yd max.
     */
    private int ySpeedMax;

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

        setWidth(getConfInteger("width"));
        setHeight(getConfInteger("height"));

        this.tileByState = populateMove("tileByState");

        this.counterDie = getConfInteger("counterDie");

        this.ySpeedMax = getConfInteger("ySpeedMax");

        // Remove me from list of object (= kill me)
        this.killme = new ObjectListMessage(this, false);
    }

    @Override
    public BufferedImage msgDraw() {
        int baseTile = moveXorY(getCounter(), this.tileByState);

        return this.images[baseTile + getState()];
    }

    /**
     * Call to update.
     */
    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        int newCounter = getCounter() + 1;

        setCounter(newCounter);

        if (newCounter >= this.counterDie) {
            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                this.killme);
        }

        setX(getX() + getxSpeed());

        int yd = getySpeed();

        UtilityObjectEntity.forceMoveUpDown(this, yd);

        if (yd < this.ySpeedMax) {
            yd++;

            setySpeed(yd);
        }
    }

    /**
     * Populate list of movement.
     *
     * @param keyName name of key in config file
     *
     * @return list of movement
     */
    private List<MoveSizeAndInterval> populateMove(final String keyName) {
       // Read moveX
        String mvtX = getConfString(keyName);
        // Split #
        String[] arrayMoveX = mvtX.split("#");

        final List<MoveSizeAndInterval> listMvt =
                new ArrayList<>(arrayMoveX.length);

        for (String currentMove : arrayMoveX) {
            listMvt.add(new MoveSizeAndInterval(currentMove));
        }

        return listMvt;
    }

    /**
     * Get speed X.
     *
     * @param c state value
     * @param moveXY movement list
     *
     * @return size of move
     */
    private int moveXorY(final int c, final List<MoveSizeAndInterval> moveXY) {
        int xd = 0;

        for (MoveSizeAndInterval currentMoveX : moveXY) {
            if (c < currentMoveX.getBound()) {
                xd = currentMoveX.get();
                break;
            }
        }

        return xd;
    }
}
