package org.jill.game.entities.back;

import java.awt.image.BufferedImage;
import org.jill.game.entities.back.abs.AbstractParameterBackgroundEntity;
import org.jill.game.entities.obj.player.PlayerState;
import org.jill.openjill.core.api.entities.BackgroundParam;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.MessageDispatcher;
import org.jill.openjill.core.api.message.object.CreateObjectMessage;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryLifeMessage;

/**
 * Kill 2 background.
 *
 * @author Emeric MARTINEAU
 */
public final class KillLavaBackgroundEntity extends
        AbstractParameterBackgroundEntity {
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
     * Init object.
     *
     * @param backParameter background parameter
     */
    @Override
    public void init(final BackgroundParam backParameter) {
        super.init(backParameter);

        this.messageDispatcher = backParameter.getMessageDispatcher();
    }

    @Override
    public void msgTouch(final ObjectEntity obj) {
        obj.msgKill(this, InventoryLifeMessage.DEAD_MESSAGE,
                PlayerState.DIE_SUB_STATE_WATER_BACK);

        // Create dead object.
        // Create here, cause object manager is not avaible when background
        // is created
        createDeadObject(getConfInteger("hitObject"));

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
    private void createDeadObject(final int typeHit) {
        final CreateObjectMessage com = new CreateObjectMessage(typeHit);

        this.messageDispatcher.sendMessage(EnumMessageType.CREATE_OBJECT,
            com);

        this.deadObject = com.getObject();

        this.deadMessage = new ObjectListMessage(this.deadObject, true);
    }

    @Override
    public BufferedImage getPicture() {
        return getPictureCache().getBackgroundPicture(getMapCode());
    }

    @Override
    public void msgDraw() {
        // Nothing
    }

    @Override
    public void msgUpdate() {
        // Nothing
    }
}