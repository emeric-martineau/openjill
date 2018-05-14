package org.jill.game.entities.obj.underwater;

import java.awt.image.BufferedImage;
import java.util.Optional;

import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.jill.JillConst;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.ObjectListMessage;

/**
 * Rolling rock.
 *
 * @author Emeric MARTINEAU
 */
public final class BubblesManager extends AbstractParameterObjectEntity {

    /**
     * Picture array.
     */
    private Optional<BufferedImage> image;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * Move Y.
     */
    private int[] moveY;

    /**
     * Move X.
     */
    private int[] moveX;

    /**
     * Regex to check if water.
     */
    private String waterRegEx;

    /**
     * Max counter value.
     */
    private int maxCounter;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        loadImage();

        this.backgroundObject = objectParam.getBackgroundObject();

        this.moveY = initIntegerArray("moveY");
        this.moveX = initIntegerArray("moveX");

        this.waterRegEx = getConfString("waterRegEx");

        this.maxCounter = getConfInteger("maxCounter");

        if (getWidth() == 0 || getHeight() == 0) {
            setWidth(this.image.get().getWidth());
            setHeight(this.image.get().getHeight());
        }
    }

    /**
     * Load image.
     */
    private void loadImage() {
        // Init list of picture
        final int tileIndex = getConfInteger("tile");
        final int tileSetIndex = getConfInteger("tileSet");

        this.image = this.pictureCache
                .getImage(tileSetIndex, tileIndex + getCounter());
    }

    /**
     * Create int array from value.
     *
     * @param confKey configuration key
     * @return array of integer
     */
    private int[] initIntegerArray(final String confKey) {
        final String strMove = getConfString(confKey);
        final String[] arrayMove = strMove.split(",");

        final int[] result = new int[arrayMove.length];

        for (int index = 0; index < arrayMove.length; index++) {
            result[index] = Integer.valueOf(arrayMove[index]);
        }

        return result;
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        // Move up. Don't need check if block cause, after check if water.
        setY(getY() + this.moveY[getCounter()]);

        moveLeftRight();

        checkIfWater();
    }

    /**
     * Check if water and kill object.
     */
    private void checkIfWater() {
        final int indexX = getX() / JillConst.getBlockSize();
        final int indexY = getY() / JillConst.getBlockSize();

        final BackgroundEntity block = this.backgroundObject[indexX][indexY];

        if (!block.getName().matches(waterRegEx)) {
            killMe();
        }
    }

    /**
     * Move left/right.
     */
    private void moveLeftRight() {
        // Random update X -1/0/+1
        final int index = (int) (Math.random() * this.moveX.length);

        final int currentMoveX = this.moveX[index];

        setX(getX() + currentMoveX);

        if (currentMoveX == 0 && getCounter() < this.maxCounter) {
            final int increaseCounter = (int) (Math.random() * 2);

            setCounter(getCounter() + increaseCounter);

            loadImage();
        }
    }

    /**
     * Kill me.
     */
    private void killMe() {
        this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                new ObjectListMessage(this, false));
    }

    @Override
    public Optional<BufferedImage> msgDraw() {
        return this.image;
    }
}
