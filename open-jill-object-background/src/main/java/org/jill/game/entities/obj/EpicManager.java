package org.jill.game.entities.obj;

import org.jill.game.entities.obj.abs.AbstractSynchronisedImageObjectEntity;
import org.jill.game.entities.obj.bullet.BulletObjectFactory;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryPointMessage;

/**
 * Epic bonus.
 *
 * @author Emeric MARTINEAU
 */
public final class EpicManager extends AbstractSynchronisedImageObjectEntity {

    /**
     * To remove this object from object list.
     */
    private ObjectListMessage killme;

    /**
     * Number of hit before kill it.
     */
    private int match;

    /**
     * Colored bullet when player hit firebird.
     */
    private int nbColoredBullet;

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        this.match = getConfInteger("pointMatch");

        // Remove me from list of object (= kill me)
        this.killme = new ObjectListMessage(this, false);

        this.nbColoredBullet = getConfInteger("nbColoredBullet");
    }

    @Override
    public void msgTouch(final ObjectEntity obj,
            final KeyboardLayout keyboardLayout) {
        if (obj.isPlayer()) {
            this.messageDispatcher.sendMessage(EnumMessageType.INVENTORY_POINT,
                    new InventoryPointMessage(getConfInteger("point"), true,
                            this, obj));

            this.state++;

            if (this.state == this.match) {
                this.messageDispatcher.sendMessage(
                        EnumMessageType.OBJECT, this.killme);

                BulletObjectFactory.explode(this, this.nbColoredBullet,
                        this.messageDispatcher);
            }
        }
    }
}
