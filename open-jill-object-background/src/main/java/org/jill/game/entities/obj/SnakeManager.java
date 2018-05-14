package org.jill.game.entities.obj;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Optional;

import org.jill.game.entities.obj.abs.AbstractHitPlayerObjectEntity;
import org.jill.game.entities.obj.util.UtilityObjectEntity;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryPointMessage;

/**
 * Firebird.
 *
 * @author Emeric MARTINEAU
 */
public final class SnakeManager extends AbstractHitPlayerObjectEntity {

    /**
     * Picture array.
     */
    private BufferedImage[] leftHeadImages;

    /**
     * Picture array.
     */
    private BufferedImage[] leftTailImages;

    /**
     * Picture array.
     */
    private BufferedImage[] rightHeadImages;

    /**
     * Picture array.
     */
    private BufferedImage[] rightTailImages;

    /**
     * Picture array.
     */
    private BufferedImage[] middleImages;

    /**
     * Kill message.
     */
    private ObjectListMessage killme;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * X position to draw head.
     */
    private int rightHeadX;

    /**
     * Y to draw tail.
     */
    private int tailY;

    /**
     * X to draw tail.
     */
    private int leftTailX;

    /**
     * Width of tail.
     */
    private int tailWidth;

    /**
     * Width of middle.
     */
    private int middleWidth;

    /**
     * Width of head.
     */
    private int headWidth;

    /**
     * Value of state when touch by weapon.
     */
    private int stateWhenTouch;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        setKillabgeObject(true);

        this.stateWhenTouch = getConfInteger("stateWhenTouch");

        // Init list of picture
        final int tileSetIndex = getConfInteger("tileSet");

        this.rightHeadImages = initPicture("rightTileHead", tileSetIndex);
        this.rightTailImages = initPicture("rightTileTail", tileSetIndex);
        this.leftHeadImages = initPicture("leftTileHead", tileSetIndex);
        this.leftTailImages = initPicture("leftTileTail", tileSetIndex);
        this.middleImages = initPicture("middleTile", tileSetIndex);

        this.killme = new ObjectListMessage(this, false);

        this.backgroundObject = objectParam.getBackgroundObject();

        // Calculate some value need to draw
        BufferedImage cP = this.rightHeadImages[0];
        this.headWidth = cP.getWidth();
        this.rightHeadX = getWidth() - cP.getWidth();
        cP = this.rightTailImages[0];
        this.tailY = getHeight() - cP.getHeight();
        cP = this.leftTailImages[0];
        this.leftTailX = getWidth() - cP.getWidth();
        this.tailWidth = this.rightTailImages[getCounter()].getWidth();
        this.middleWidth = this.middleImages[getCounter()].getWidth();
    }

    /**
     * Init picture.
     *
     * @param key          config key
     * @param tileSetIndex tilset
     * @return array of picture
     */
    private BufferedImage[] initPicture(final String key, final int tileSetIndex) {
        final String rightTileHead = getConfString(key);
        final String[] tiles = rightTileHead.split(",");
        final BufferedImage[] images = new BufferedImage[tiles.length];
        initPicture(images, tiles, tileSetIndex);

        return images;
    }

    /**
     * Init picutre.
     *
     * @param images       image array
     * @param tiles        number of tile
     * @param tileSetIndex tile set index
     */
    private void initPicture(final BufferedImage[] images,
            final String[] tiles, final int tileSetIndex) {

        for (int index = 0; index < tiles.length; index++) {
            images[index] = this.pictureCache.getImage(tileSetIndex,
                    Integer.valueOf(tiles[index])).get();
        }
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        if ((this.xSpeed < ObjectEntity.X_SPEED_MIDDLE)
                && UtilityObjectEntity.moveObjectLeftOnFloor(this, this.xSpeed,
                this.backgroundObject)
                || (this.xSpeed > ObjectEntity.X_SPEED_MIDDLE)
                && UtilityObjectEntity.moveObjectRightOnFloor(this, this.xSpeed,
                this.backgroundObject)) {
            this.counter++;

            if (this.counter == this.middleImages.length) {
                this.counter = 0;
            }
        } else {
            // Actually picture turn, change way
            this.xSpeed *= -1;
            this.counter = 0;
        }

        if (getState() > 0) {
            setState(getState() - 1);
        }
    }

    @Override
    public Optional<BufferedImage> msgDraw() {
        BufferedImage currentPicture = new BufferedImage(getWidth(),
                getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g2d = currentPicture.createGraphics();

        if (this.xSpeed > 0) {
            // Right
            // Draw head
            draw(g2d, this.rightHeadImages[getCounter()], this.rightHeadX, 0);
            // Draw tail
            draw(g2d, this.rightTailImages[getCounter()], 0, this.tailY);

            drawMiddle(this.tailWidth, this.rightHeadX, g2d);
        } else {
            // Left
            // Draw head
            draw(g2d, this.leftHeadImages[getCounter()], 0, 0);
            // Draw tail
            draw(g2d, this.leftTailImages[getCounter()], this.leftTailX, this.tailY);

            drawMiddle(this.headWidth, this.leftTailX, g2d);
        }

        g2d.dispose();

        return Optional.of(currentPicture);
    }

    /**
     * Draw middle of snake.
     *
     * @param start start X
     * @param end   end X
     * @param g2d   Graphic2D of image
     */
    private void drawMiddle(final int start, final int end,
            final Graphics g2d) {
        for (int drawX = start; drawX < end; drawX += this.middleWidth) {
            draw(g2d, this.middleImages[getCounter()], drawX, this.tailY);
        }
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

        if (getState() == 0) {
            setState(this.stateWhenTouch);

            // Decrease
            setWidth(getWidth() - this.middleWidth);

            if (getWidth() <= this.tailWidth + this.headWidth) {
                this.messageDispatcher.sendMessage(
                        EnumMessageType.INVENTORY_POINT,
                        new InventoryPointMessage(getConfInteger("point"), true,
                                this, sender));
                this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                        this.killme);
            }

            // Update rightHeadX and leftTailX
            BufferedImage cP = this.rightHeadImages[0];
            this.rightHeadX = getWidth() - cP.getWidth();

            cP = this.leftTailImages[0];
            this.leftTailX = getWidth() - cP.getWidth();
        }
    }
}
