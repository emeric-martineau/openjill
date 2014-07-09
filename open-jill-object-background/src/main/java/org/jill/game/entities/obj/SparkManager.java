package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import org.jill.game.entities.obj.abs.AbstractHitPlayerObjectEntity;
import org.jill.game.entities.obj.player.PlayerState;
import org.jill.openjill.core.api.message.player.MovePlayerMessage;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.jill.JillConst;
import org.jill.openjill.core.api.message.EnumMessageType;

/**
 * Spark.
 *
 * @author Emeric MARTINEAU
 */
public final class SparkManager extends AbstractHitPlayerObjectEntity {

    /**
     * Static cause only one lift move player at one time.
     */
    private static final MovePlayerMessage MOVE_PLAYER_OBJECT
        = new MovePlayerMessage();

    /**
     * Picture array.
     */
    private BufferedImage[] images;

    /**
     * Max Y pos top.
     */
    private int maxYTop;

    /**
     * Max Y pos bottom.
     */
    private int maxYBottom;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        loadPicture();

        // Search block
        final BackgroundEntity[][] backMap =
                objectParam.getBackgroundObject();

        final int blockX = this.x / JillConst.BLOCK_SIZE;
        final int blockYTop = this.y / JillConst.BLOCK_SIZE;
        final int blockYBottom = (this.y + this.height) / JillConst.BLOCK_SIZE;

        final int endBlockY = backMap[0].length;

        int startY = 0;
        int stopY = backMap[0].length - 1;

        // Search on bottom
        for (int indexY = blockYBottom; indexY < endBlockY; indexY++) {
            if (!backMap[blockX][indexY].isVine()) {
                //stopY = indexY - 1;
                stopY = indexY;
                break;
            }
        }

        // Search on top
        for (int indexY = blockYTop; indexY > -1; indexY--) {
            if (!backMap[blockX][indexY].isVine()) {
                startY = indexY + 1;
                break;
            }
        }

        final int halfObject = this.height / 2;

        if (startY < stopY) {
            this.maxYTop = startY * JillConst.BLOCK_SIZE - halfObject;
            this.maxYBottom = stopY * JillConst.BLOCK_SIZE - halfObject;
        } else {
            this.maxYTop = startY * JillConst.BLOCK_SIZE - halfObject;
            this.maxYBottom = stopY * JillConst.BLOCK_SIZE - halfObject;
        }
    }

    /**
     * Load picture.
     */
    private void loadPicture() {
        int tileIndex = getConfInteger("tile");
        int tileSetIndex = getConfInteger("tileSet");

        int numberTileSet = getConfInteger("numberTileSet");

        // Load picture for each object. Don't use cache cause some picture
        // change between jill episod.
        this.images
            = new BufferedImage[numberTileSet * 2];

        int indexArray = 0;

        for (int index = 0; index < numberTileSet; index++) {
            this.images[indexArray]
                = this.pictureCache.getImage(tileSetIndex, tileIndex
                    + index);
            this.images[indexArray + 1] = this.images[indexArray];

            indexArray += 2;
        }
    }

    @Override
    public void msgUpdate() {
        this.counter++;

        if (this.counter == this.images.length) {
            this.counter = 0;
        }

        if ((this.y > this.maxYTop && this.ySpeed < Y_SPEED_MIDDLE)
            || (this.y < this.maxYBottom && this.ySpeed > Y_SPEED_MIDDLE)) {
            this.y += this.ySpeed;
        } else {
            // U turn
            this.ySpeed *= -1;
        }
    }

    @Override
    public BufferedImage msgDraw() {
        return this.images[this.counter];
    }

    @Override
    public void msgTouch(final ObjectEntity obj) {
        if (obj.isPlayer()) {
            // Move player to right
            MOVE_PLAYER_OBJECT.setOffsetX(-JillConst.BLOCK_SIZE);
            MOVE_PLAYER_OBJECT.setOffsetY(0);

            MOVE_PLAYER_OBJECT.setState(PlayerState.STAND);

            MOVE_PLAYER_OBJECT.setRight(true);

            this.messageDispatcher.sendMessage(
                EnumMessageType.PLAYER_MOVE, MOVE_PLAYER_OBJECT);

            // Hit player
            hitPlayer(obj);
        }
    }
}
