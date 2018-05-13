package org.jill.game.entities.back;

import java.awt.image.BufferedImage;

import org.jill.game.entities.back.abs.AbstractSynchronisedImageBackgroundEntity;
import org.jill.game.entities.obj.player.PlayerState;
import org.jill.game.entities.picutre.PictureSynchronizer;
import org.jill.openjill.core.api.entities.BackgroundParam;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.MessageDispatcher;
import org.jill.openjill.core.api.message.object.CreateObjectMessage;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryLifeMessage;

/**
 * Laval background.
 *
 * @author Emeric MARTINEAU
 */
public final class KillLavaBackgroundEntity extends
        AbstractSynchronisedImageBackgroundEntity {
    /**
     * Picture array.
     */
    private BufferedImage[] images;

    /**
     * Current inde image to display.
     */
    private int indexEtat = 0;

    /**
     * To dispatch message for any object in game.
     */
    private MessageDispatcher messageDispatcher;

    /**
     * Dead object.
     */
    private ObjectListMessage deadMessage;

    /**
     * Dead object.
     */
    private ObjectEntity deadObject;

    /**
     * Dma name.
     */
    private String dmaName;

    /**
     * Init object.
     *
     * @param backParameter background parameter
     */
    @Override
    public void init(final BackgroundParam backParameter) {
        super.init(backParameter);

        int tileIndex = getConfInteger("tileStart");
        int tileSetIndex = getDmaEntry().getTileset();

        int nbPicture = getConfInteger("numberTileSet");

        this.images = new BufferedImage[nbPicture];

        this.dmaName = getDmaEntry().getName();

        for (int index = 0; index < this.images.length; index++) {
            this.images[index] = getPictureCache().getImage(tileSetIndex,
                    tileIndex + index).get();
        }

        if (getPictureSync(this.getClass()) == null) {
            int maxDisplayCounter = getConfInteger("cycle");

            // Create synchronizer
            final PictureSynchronizer ps =
                    new PictureSynchronizer(maxDisplayCounter);
            addPictureSync(this.dmaName, ps);
        }

        this.messageDispatcher = backParameter.getMessageDispatcher();
    }

    @Override
    public void msgTouch(final ObjectEntity obj) {
        obj.msgKill(this, InventoryLifeMessage.DEAD_MESSAGE,
                PlayerState.DIE_SUB_STATE_WATER_BACK);

        // Create dead object.
        // Create here, cause object manager is not avaible when background
        // is created
        createDeadObject(getConfString("hitObject"));

        // Dead object have same position that player.
        // Player move when receive kill message. Now player have good position.
        this.deadObject.setX(obj.getX());
        this.deadObject.setY(obj.getY());

        this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                this.deadMessage);
    }

    /**
     * Create object.
     *
     * @param typeHit type of hit (37)
     */
    private void createDeadObject(final String typeHit) {
        final CreateObjectMessage com
                = CreateObjectMessage.buildFromClassName(typeHit);

        this.messageDispatcher.sendMessage(EnumMessageType.CREATE_OBJECT,
                com);

        this.deadObject = com.getObject();

        this.deadMessage = new ObjectListMessage(this.deadObject, true);
    }

    @Override
    public BufferedImage getPicture() {
        return images[getPictureSync(this.dmaName).getIndexPicture()];
    }

    @Override
    public void msgDraw() {
        // Nothing
    }

    @Override
    public void msgUpdate() {
        this.indexEtat = getPictureSync(this.dmaName).updatePictureIndex(
                this.indexEtat, images);
    }
}