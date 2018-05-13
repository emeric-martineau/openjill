package org.jill.game.entities.back;

import java.awt.image.BufferedImage;

import org.jill.game.entities.back.abs.AbstractParameterBackgroundEntity;
import org.jill.game.entities.back.obj.ObjectSyncrhonizer;
import org.jill.openjill.core.api.entities.BackgroundParam;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.jill.JillConst;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.MessageDispatcher;
import org.jill.openjill.core.api.message.object.CreateObjectMessage;
import org.jill.openjill.core.api.message.object.ObjectListMessage;

/**
 * Wood torch background.
 *
 * @author Emeric MARTINEAU
 */
public class WoodTorchBackgroundEntity
        extends AbstractParameterBackgroundEntity {

    /**
     * Object syncronizer.
     */
    private static final ObjectSyncrhonizer OS = new ObjectSyncrhonizer();

    /**
     * Current inde image to display.
     */
    private int indexEtat = 0;

    /**
     * Object id of fire.
     */
    private String object;

    /**
     * To dispatch message for any object in game.
     */
    private MessageDispatcher messageDispatcher;

    @Override
    public void init(final BackgroundParam backParam) {
        super.init(backParam);

        OS.setMaxCounter(getConfInteger("maxCounter"));

        this.object = getConfString("object");

        this.messageDispatcher = backParam.getMessageDispatcher();
    }

    @Override
    public void msgUpdate() {
        this.indexEtat = OS.updateCounter(this.indexEtat);

        if (OS.isCreateObject(getX())) {
            // Create point object
            CreateObjectMessage com = CreateObjectMessage.buildFromClassName(
                    this.object);

            this.messageDispatcher.sendMessage(EnumMessageType.CREATE_OBJECT,
                    com);

            ObjectEntity obj = com.getObject();

            obj.setX(this.getX() * JillConst.getBlockSize());
            obj.setY(
                    (this.getY() * JillConst.getBlockSize()) - obj.getHeight()
                            + (JillConst.getBlockSize() / 2));
            obj.setySpeed(ObjectEntity.Y_SPEED_UP);

            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                    new ObjectListMessage(obj, true));
        }
    }

    @Override
    public BufferedImage getPicture() {
        return getPictureCache().getBackgroundPicture(getMapCode()).get();
    }

    @Override
    public void msgDraw() {
        // Nothing
    }
}
