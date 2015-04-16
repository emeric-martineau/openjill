package org.jill.game.entities.obj.abs;

import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.CreateObjectMessage;
import org.jill.openjill.core.api.message.object.ObjectListMessage;

/**
 * Hit player with flame.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractFireHitPlayerObject
    extends AbstractHitPlayerObjectEntity {
    /**
     * Dead object.
     */
    private ObjectListMessage deadMessage;

    /**
     * Dead object.
     */
    private ObjectEntity deadObject;

    /**
     * Kill message.
     */
    private ObjectListMessage killme;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        // Create dead object
        createDeadObject(getConfInteger("hitObject"));

        this.killme = new ObjectListMessage(this, false);
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
    public void msgTouch(final ObjectEntity obj,
            final KeyboardLayout keyboardLayout) {
        if (obj.isPlayer()) {
            hitPlayer(obj);

            // Dead object have same position that player
            this.deadObject.setX(obj.getX());
            this.deadObject.setY(obj.getY());

            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                this.deadMessage);

            touchPlayer(obj, keyboardLayout);
        }
    }

    /**
     * Kill object.
     */
    protected void killMe() {
        this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
            this.killme);
    }

    /**
     * Call when object touch player.
     *
     * @param obj object (player)
     * @param keyboardLayout keyboard
     */
    protected abstract void touchPlayer(final ObjectEntity obj,
            final KeyboardLayout keyboardLayout);
}
