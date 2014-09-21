package org.jill.game.entities.obj;

import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import java.awt.image.BufferedImage;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.message.EnumMessageType;

import org.jill.openjill.core.api.keyboard.KeyboardLayout;

/**
 * Switch.
 *
 * @author Emeric MARTINEAU
 */
public final class SwitchManager extends AbstractParameterObjectEntity {

    /**
     * Switch on.
     */
    private static final int SWITCH_ON = 1;

    /**
     * Switch off.
     */
    private static final int SWITCH_OFF = 0;

    /**
     * To know if message must be display.
     */
    private static boolean messageDisplaySwitchMessage = true;

    /**
     * Picture array.
     */
    private BufferedImage[] images;

    /**
     * Default constructor.
     *
     * @param objectParam object param
     */
    @SuppressWarnings("LeakingThisInConstructor")
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        int tileIndex = getConfInteger("tile");
        int tileSetIndex = getConfInteger("tileSet");

        this.images = new BufferedImage[getConfInteger("numberTileSet")];

        for (int index = 0; index < this.images.length; index++) {
            this.images[index] = this.pictureCache.getImage(tileSetIndex,
                    tileIndex + index);
        }
    }

    @Override
    public BufferedImage msgDraw() {
        return this.images[this.state];
    }

    @Override
    public void msgTouch(final ObjectEntity obj) {
        if (obj.isPlayer()) {
            if (messageDisplaySwitchMessage) {
                sendMessage();

                messageDisplaySwitchMessage = false;
            }
        }
    }

    @Override
    public void msgKeyboard(final ObjectEntity obj,
        final KeyboardLayout keyboardLayout) {
        if (obj.isPlayer()) {
            if (keyboardLayout.isUp() && this.state != SWITCH_OFF) {
                // Original game don't grap keyboard key for switch
                //keyboardLayout.setUp(false);

                this.state = SWITCH_OFF;

                this.messageDispatcher.sendMessage(EnumMessageType.TRIGGER,
                    this);
            } else if (keyboardLayout.isDown() && state != SWITCH_ON) {
                //keyboardLayout.setDown(false);

                this.state = SWITCH_ON;

                messageDispatcher.sendMessage(EnumMessageType.TRIGGER,
                    this);
            }
        }
    }
}
