package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryPointMessage;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryLifeMessage;

/**
 * Apple object.
 *
 * @author Emeric MARTINEAU
 */
public final class BladeManager extends AbstractParameterObjectEntity {
    /**
     * To know if message must be display.
     */
    private static boolean messageDisplayAppleMessage = true;

    /**
     * To remove this object from object lis.
     */
    private ObjectListMessage killme;

    /**
     * Picture array.
     */
    private BufferedImage[] images;
    
    /**
     * SubState value to remove blade.
     */
    private int subStateToRemoveMe;

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        int tileIndex = getConfInteger("tile");
        int tileSetIndex = getConfInteger("tileSet");

        int numberTileSet = getConfInteger("numberTileSet");
        
        this.subStateToRemoveMe = getConfInteger("subStateToRemoveMe");

        // Load picture for each object. Don't use cache cause some picture
        // change between jill episod.
        this.images
            = new BufferedImage[numberTileSet];

        for (int index = 0; index < numberTileSet; index++) {
            this.images[index]
                = this.pictureCache.getImage(tileSetIndex, tileIndex
                    + index);
        }

        // Remove me from list of object (= kill me)
        this.killme = new ObjectListMessage(this, false);
    }

    /*
    @Override
    public void msgTouch(final ObjectEntity obj,
            final KeyboardLayout keyboardLayout) {
        if (obj.isPlayer()) {
            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT, killme);

            if (this.getState() == 0) {
                this.messageDispatcher.sendMessage(
                        EnumMessageType.INVENTORY_POINT,
                    new InventoryPointMessage(getConfInteger("point"), true,
                    this, obj));

                this.messageDispatcher.sendMessage(
                        EnumMessageType.INVENTORY_LIFE,
                        new InventoryLifeMessage(getConfInteger("life")));

                if (messageDisplayAppleMessage) {
                    sendMessage();
                    messageDisplayAppleMessage = false;
                }
            } else {
                Integer msgId = (getState() + getConfInteger("boxMsgOffset"))
                                & getConfInteger("boxMsgMask") ;
                this.messageDispatcher.sendMessage(
                        EnumMessageType.MESSAGE_BOX, msgId);
            }
        }
    }*/

    @Override
    public BufferedImage msgDraw() {
        return this.images[this.counter];
    }

    /**
     * Call to update.
     */
    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        setCounter(getCounter() + 1);
        setSubState(getSubState() + 1);

        if (getCounter() >= this.images.length) {
            setCounter(0);
        }
     
        // Remove blade
        if (getSubState() >= this.subStateToRemoveMe) {
            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT, killme);
        }
    }
}
