package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;

import org.jill.game.entities.obj.abs.AbstractHitPlayerObjectEntity;
import org.jill.game.entities.obj.player.PlayerState;
import org.jill.game.entities.obj.util.SharedCode;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.jill.JillConst;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.player.MovePlayerMessage;

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

        this.images = SharedCode.loadPicture(this.pictureCache, getConfInteger("tile"),
                getConfInteger("tileSet"), getConfInteger("numberTileSet"));

        // Search block
        final BackgroundEntity[][] backMap =
                objectParam.getBackgroundObject();

        final int blockX = this.x / JillConst.getBlockSize();
        final int blockYTop = this.y / JillConst.getBlockSize();
        final int blockYBottom = (this.y + this.height) / JillConst.getBlockSize();

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
            this.maxYTop = startY * JillConst.getBlockSize() - halfObject;
            this.maxYBottom = stopY * JillConst.getBlockSize() - halfObject;
        } else {
            this.maxYTop = startY * JillConst.getBlockSize() - halfObject;
            this.maxYBottom = stopY * JillConst.getBlockSize() - halfObject;
        }
    }

    @Override
    public void msgUpdate(KeyboardLayout keyboardLayout) {
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
    public void msgTouch(final ObjectEntity obj,
            KeyboardLayout keyboardLayout) {
        if (obj.isPlayer()) {
            // Move player to right
            MOVE_PLAYER_OBJECT.setOffsetX(-JillConst.getBlockSize());
            MOVE_PLAYER_OBJECT.setOffsetY(0);

            MOVE_PLAYER_OBJECT.setState(PlayerState.STAND);

            this.messageDispatcher.sendMessage(
                    EnumMessageType.PLAYER_MOVE, MOVE_PLAYER_OBJECT);

            // Hit player
            hitPlayer(obj);
        }
    }
}
