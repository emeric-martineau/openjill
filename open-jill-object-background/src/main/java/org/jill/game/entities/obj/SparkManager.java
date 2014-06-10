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
     * Don't use a object field cause original game doesn't store picture index
     * in save file.
     */
    private int imageIndex = 0;

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

        // Init list of picture
        final int tileIndex = getConfInteger("tile");
        final int tileSetIndex = getConfInteger("tileSet");
        final int numberTileSet = getConfInteger("numberTileSet");

        // Alloc array of picture
        this.images = new BufferedImage[numberTileSet];

        // Init Right
        initPicture(this.images, numberTileSet, tileSetIndex, tileIndex);

        // Search block
        final BackgroundEntity[][] backMap =
                objectParam.getBackgroundObject();

        final int blockX = this.x / JillConst.BLOCK_SIZE;
        final int blockY = this.y / JillConst.BLOCK_SIZE;
        final int endBlockY = backMap[0].length;

        int startY = 0;
        int stopY = backMap[0].length - 1;

        // Search on bottom
        for (int indexY = blockY; indexY < endBlockY; indexY++) {
            if (!backMap[blockX][indexY].isPlayerThru()) {
                //stopY = indexY - 1;
                stopY = indexY;
                break;
            }
        }

        // Search on top
        for (int indexY = blockY; indexY > -1; indexY--) {
            if (!backMap[blockX][indexY].isPlayerThru()) {
                startY = indexY + 1;
                break;
            }
        }

        final int halfObject = this.height / 2;

        this.maxYTop = startY * JillConst.BLOCK_SIZE - halfObject;
        this.maxYBottom = stopY * JillConst.BLOCK_SIZE - halfObject;
    }

    /**
     * Init picture level.
     *
     * @param img picture array
     * @param numberTileSet number
     * @param tileSetIndex tileset
     * @param tileIndex tile
     */
    private void initPicture(final BufferedImage[] img,
        final int numberTileSet, final int tileSetIndex,
        final int tileIndex) {
        for (int index = 0; index < numberTileSet; index++) {
            img[index] = this.pictureCache.getImage(tileSetIndex,
                tileIndex + index);
        }
    }

    @Override
    public void msgUpdate() {
        this.imageIndex++;

        if (this.imageIndex == this.images.length) {
            this.imageIndex = 0;
        }

        if ((this.y > this.maxYTop && this.ySpeed < 0)
            || (this.y < this.maxYBottom && this.ySpeed > 0)) {
            this.y += this.ySpeed;
        } else {
            this.ySpeed *= -1;
        }
    }

    @Override
    public BufferedImage msgDraw() {
        return this.images[this.imageIndex];
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
