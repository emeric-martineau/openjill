package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;

import org.jill.game.entities.obj.abs.AbstractHitPlayerObjectEntity;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.jill.JillConst;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;

/**
 * Spark.
 *
 * @author Emeric MARTINEAU
 */
public final class GhostManager extends AbstractHitPlayerObjectEntity {
    /**
     * Image up.
     */
    private static final int IMAGE_UP = 2;

    /**
     * Image down.
     */
    private static final int IMAGE_DOWN = 3;

    /**
     * Image left.
     */
    private static final int IMAGE_LEFT = 1;

    /**
     * Image right.
     */
    private static final int IMAGE_RIGHT = 0;

    /**
     * Picture array.
     */
    private BufferedImage[] images;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backMap;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        this.backMap = objectParam.getBackgroundObject();

        loadPicture();
    }

    /**
     * Load picture.
     */
    private void loadPicture() {
        int tileIndex = getConfInteger("tile");
        int tileSetIndex = getConfInteger("tileSet");

        int numberTileSet = getConfInteger("numberTileSet");

        this.images
                = new BufferedImage[numberTileSet];

        for (int index = 0; index < numberTileSet; index++) {
            this.images[index]
                    = this.pictureCache.getImage(tileSetIndex, tileIndex
                    + index);
        }
    }

    @Override
    public void msgUpdate(KeyboardLayout keyboardLayout) {
        if (getySpeed() != Y_SPEED_MIDDLE) {
            moveUpDown();
        }

        if (getxSpeed() != X_SPEED_MIDDLE) {
            moveLeftRight();
        }

    }

    /**
     * Move gost/up down.
     */
    private void moveUpDown() {
        int blockX = this.getX() / JillConst.getBlockSize();
        int blockY;
        int newY;
        int newBlockY;

        newY = this.getY() + getySpeed();

        if (getySpeed() > Y_SPEED_MIDDLE) {
            // Down
            setySpeed(this.getCounter());

            blockY = (this.getY() + this.getHeight() - 1)
                    / JillConst.getBlockSize();

            newBlockY = (newY + this.getHeight() - 1) / JillConst.getBlockSize();
        } else {
            // Up
            setySpeed(this.getCounter() * Y_SPEED_UP);

            blockY = this.getY() / JillConst.getBlockSize();

            newBlockY = newY / JillConst.getBlockSize();
        }

        int currentMapCode = this.backMap[blockX][blockY].getMapCode();

        if (currentMapCode == this.backMap[blockX][newBlockY].getMapCode()) {
            this.setY(newY);
        } else {
            boolean isRightBorder = blockX + 1
                    > JillConst.getMaxWidth() / JillConst.getBlockSize();

            setySpeed(0);

            if (!isRightBorder && this.backMap[blockX + 1][blockY].getMapCode()
                    == currentMapCode) {
                setxSpeed(this.getCounter());
            } else if (blockX > 0
                    && this.backMap[blockX - 1][blockY].getMapCode()
                    == currentMapCode) {
                setxSpeed(this.getCounter() * X_SPEED_LEFT);
            } else if (getySpeed() > Y_SPEED_MIDDLE) {
                // Go down
                setySpeed(this.getCounter());
            } else if (getySpeed() < Y_SPEED_MIDDLE) {
                // Go up
                setySpeed(this.getCounter() * Y_SPEED_UP);
            }
        }
    }

    /**
     * Move ghost to left/right.
     */
    private void moveLeftRight() {
        int blockY = this.getY() / JillConst.getBlockSize();
        int blockX;
        int newBlockX;
        int newX;

        newX = this.getX() + this.getxSpeed();

        if (getxSpeed() > X_SPEED_MIDDLE) {
            // left
            blockX = (this.getX() + this.getWidth() - 1) / JillConst.getBlockSize();

            newBlockX = (newX + this.getWidth() - 1) / JillConst.getBlockSize();
        } else {
            // right
            blockX = this.getX() / JillConst.getBlockSize();

            newBlockX = newX / JillConst.getBlockSize();
        }

        int currentMapCode = this.backMap[blockX][blockY].getMapCode();

        if (currentMapCode == this.backMap[newBlockX][blockY].getMapCode()) {
            this.setX(newX);
        } else {
            boolean isBottomBorder = blockX
                    == JillConst.getMaxWidth() / JillConst.getBlockSize();

            setxSpeed(0);

            if (blockX == 0 || (!isBottomBorder && currentMapCode
                    == this.backMap[blockX][blockY + 1].getMapCode())) {
                // Go down
                setySpeed(getCounter());
            } else if (isBottomBorder || (blockX > 0 && currentMapCode
                    == this.backMap[blockX][blockY - 1].getMapCode())) {
                // Go up
                setySpeed(getCounter() * Y_SPEED_UP);
            }
        }
    }

    @Override
    public BufferedImage msgDraw() {
        BufferedImage image;

        if (getxSpeed() < X_SPEED_MIDDLE) {
            image = this.images[IMAGE_LEFT];
        } else if (getxSpeed() > X_SPEED_MIDDLE) {
            image = this.images[IMAGE_RIGHT];
        } else if (getySpeed() > Y_SPEED_MIDDLE) {
            image = this.images[IMAGE_DOWN];
        } else {
            image = this.images[IMAGE_UP];
        }

        return image;
    }

    @Override
    public void msgTouch(final ObjectEntity obj,
            KeyboardLayout keyboardLayout) {
        if (obj.isPlayer()) {
            // Hit player
            hitPlayer(obj);
        }
    }
}
