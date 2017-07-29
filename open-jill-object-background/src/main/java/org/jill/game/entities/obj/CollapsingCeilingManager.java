package org.jill.game.entities.obj;

import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import java.awt.image.BufferedImage;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.background.BackgroundMessage;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.jill.JillConst;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.InterfaceMessageGameHandler;

/**
 * Collapsing ceiling.
 *
 * @author Emeric MARTINEAU
 */
public final class CollapsingCeilingManager
    extends AbstractParameterObjectEntity
    implements InterfaceMessageGameHandler {
    /**
     * Switch on.
     */
    private static final int SWITCH_ON = 1;

    /**
     * Switch off.
     */
    private static final int SWITCH_OFF = 0;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * Picture array.
     */
    private BufferedImage image;

    /**
     * Background massage to send.
     */
    private final BackgroundMessage backMsg = new BackgroundMessage(0, 0, 0);

    /**
     * To remove this object from object list.
     */
    private ObjectListMessage killme;

    /**
     * Speed fall.
     */
    private int speedFall;

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @SuppressWarnings("LeakingThisInConstructor")
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        this.backgroundObject = objectParam.getBackgroundObject();

        this.speedFall = getConfInteger("speedFall");

        final int tileIndex = getConfInteger("tile");
        final int tileSetIndex = getConfInteger("tileSet");

        this.image = this.pictureCache.getImage(tileSetIndex, tileIndex);

        this.messageDispatcher.addHandler(EnumMessageType.TRIGGER, this);

        // Remove me from list of object (= kill me)
        this.killme = new ObjectListMessage(this, false);

        // Grap background to clone
        final int backX = x / JillConst.getBlockSize();
        final int backY = y / JillConst.getBlockSize();

        final BackgroundEntity backToClone = this.backgroundObject[backX][backY - 1];
        this.backMsg.setMapCode(backToClone.getMapCode());

        this.backMsg.setX(backX);
    }

    @Override
    public BufferedImage msgDraw() {
        return this.image;
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        if (this.state != SWITCH_OFF) {
            // Replace at current position background by new background
            final int backY = this.y / JillConst.getBlockSize();

            this.backMsg.setY(backY);

            this.messageDispatcher.sendMessage(EnumMessageType.BACKGROUND,
                this.backMsg);

            if (backgroundObject[backMsg.getX()][backY + 1].isPlayerThru()) {
                // Decrease position
                this.y += this.speedFall;
            } else {
                // remove this object from list
                this.messageDispatcher.sendMessage(
                    EnumMessageType.OBJECT, this.killme);
            }
        }
    }

    @Override
    public void recieveMessage(final EnumMessageType type,
        final Object msg) {
        switch (type) {
            case TRIGGER:
                triggerMessage(type, msg);
                break;
            default:
        }
    }

    /**
     * Manage TRIGGER message.
     *
     * @param type message type
     * @param msg message
     */
    private void triggerMessage(final EnumMessageType type,
        final Object msg) {
        if (this.state == SWITCH_OFF) {
            final ObjectEntity source = (ObjectEntity) msg;

            if (source.getCounter() == this.counter) {
                this.state = SWITCH_ON;
            }
        }
    }
}
