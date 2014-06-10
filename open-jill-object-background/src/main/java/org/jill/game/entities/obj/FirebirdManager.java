package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.game.entities.obj.abs.AbstractHitPlayerObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.CreateObjectMessage;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryPointMessage;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.jill.JillConst;

/**
 * Firebird.
 *
 * @author Emeric MARTINEAU
 */
public final class FirebirdManager extends AbstractHitPlayerObjectEntity {

    /**
     * Number of picture to remove from left/right.
     */
    private static final int NUMBER_PICTURE_TO_REMOVE = 2;

    /**
     * Number of side.
     */
    private static final int NUMBER_OF_SIDE = 2;

    /**
     * Picture array.
     */
    private BufferedImage[] leftImages;

    /**
     * Picture array.
     */
    private BufferedImage[] rightImages;

    /**
     * Max X pos left.
     */
    private int maxXLeft;

    /**
     * Max X pos right.
     */
    private int maxXRight;

    /**
     * Index of turn picture.
     */
    private int turnIndexPicture;

    /**
     * Kill message.
     */
    private ObjectListMessage killme;

    /**
     * Dead object.
     */
    private ObjectListMessage deadMessage;

    /**
     * Dead object.
     */
    private ObjectEntity deadObject;

    /**
     * Point message.
     */
    private InventoryPointMessage pointMsg;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        setKillabgeObject(true);

        // Init list of picture
        final int tileIndex = getConfInteger("tile");
        final int tileSetIndex = getConfInteger("tileSet");

        final int baseTileNumber = getConfInteger("baseTileNumber");
        final int turnTileNumber = getConfInteger("turnTileNumber");

        final int point = getConfInteger("point");

        // Number picture for one side
        final int numberPicturePerSide = (baseTileNumber * NUMBER_OF_SIDE)
                - NUMBER_PICTURE_TO_REMOVE + turnTileNumber;

        this.turnIndexPicture = numberPicturePerSide - 1;

        // Tile index for turn picture
        final int tileTurnIndex = tileIndex + (baseTileNumber
                * NUMBER_OF_SIDE);

        // Alloc array of picture
        this.leftImages = new BufferedImage[numberPicturePerSide];
        this.rightImages = new BufferedImage[numberPicturePerSide];

        // Init Right
        initPicture(this.rightImages, baseTileNumber, tileSetIndex,
                tileIndex);
        this.rightImages[this.turnIndexPicture] =
                this.pictureCache.getImage(tileSetIndex, tileTurnIndex);

        // Init Left
        initPicture(this.leftImages, baseTileNumber, tileSetIndex,
                tileIndex + baseTileNumber);
        this.leftImages[this.turnIndexPicture] = this.pictureCache.getImage(
                tileSetIndex, tileTurnIndex + turnTileNumber);

        // Search block
        final BackgroundEntity[][] backMap =
                objectParam.getBackgroundObject();

        final int blockX = this.x / JillConst.BLOCK_SIZE;
        final int blockY = this.y / JillConst.BLOCK_SIZE;

        int startX = 0;
        int stopX = backMap.length - 1;

        // Search on right
        for (int indexX = blockX; indexX < backMap.length; indexX++) {
            if (!backMap[indexX][blockY].isPlayerThru()) {
                stopX = indexX;
                break;
            }
        }

        // Search on right
        for (int indexX = blockX; indexX > -1; indexX--) {
            if (!backMap[indexX][blockY].isPlayerThru()) {
                startX = indexX + 1;
                break;
            }
        }

        this.maxXLeft = startX * JillConst.BLOCK_SIZE;
        this.maxXRight = (stopX * JillConst.BLOCK_SIZE) - this.width;

        setKillabgeObject(true);

        this.killme = new ObjectListMessage(this, false);
        this.pointMsg = new InventoryPointMessage(point, true);

        // Create dead object
        createDeadObject(getConfInteger("hitObject"));
    }

    /**
     * Create object.
     *
     * @param typeHit type of hit (37)
     */
    private void createDeadObject(final int typeHit) {
        final CreateObjectMessage com = new CreateObjectMessage(typeHit);

        this.messageDispatcher.sendMessage(EnumMessageType.CREATE_OBJECT,
            com);

        this.deadObject = com.getObject();

        this.deadMessage = new ObjectListMessage(this.deadObject, true);
    }

    /**
     * Init picture level.
     *
     * @param images picture array
     * @param baseTileNumber number
     * @param tileSetIndex tileset
     * @param tileIndex tile
     */
    private void initPicture(final BufferedImage[] images,
        final int baseTileNumber, final int tileSetIndex,
        final int tileIndex) {
        for (int index = 0; index < baseTileNumber; index++) {
            images[index] = this.pictureCache.getImage(tileSetIndex,
                tileIndex + index);
        }

        int indexArray = baseTileNumber;

        for (int index = baseTileNumber - NUMBER_PICTURE_TO_REMOVE; index > 0;
            index--) {
            images[indexArray] = images[index];
            indexArray++;
        }
    }

    @Override
    public void msgUpdate() {
        if ((this.x > this.maxXLeft && this.xSpeed < 0)
            || (this.x < this.maxXRight && this.xSpeed > 0)) {
            this.x += this.xSpeed;

            this.counter++;

            if (this.counter == this.turnIndexPicture) {
                this.counter = 0;
            }
        } else if (this.counter == this.turnIndexPicture) {
            this.xSpeed *= -1;
            this.counter = 0;
        } else {
            this.counter = this.turnIndexPicture;
        }
    }

    @Override
    public BufferedImage msgDraw() {
        BufferedImage[] currentPictureArray;

        if (this.xSpeed > 0) {
            // Right
            currentPictureArray = this.rightImages;
        } else {
            // Left
            currentPictureArray = this.leftImages;
        }

        return currentPictureArray[this.counter];
    }

    @Override
    public void msgTouch(final ObjectEntity obj) {
        if (obj.isPlayer()) {
            hitPlayer(obj);

            // Dead object have same position that player
            this.deadObject.setX(obj.getX());
            this.deadObject.setY(obj.getY());

            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                this.deadMessage);

            msgKill(this, 0, 0);
        }
    }

    @Override
    public void msgKill(final ObjectEntity sender,
        final int nbLife, final int typeOfDeath) {
        this.messageDispatcher.sendMessage(EnumMessageType.INVENTORY_POINT,
            this.pointMsg);
        this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
            this.killme);
    }
}
