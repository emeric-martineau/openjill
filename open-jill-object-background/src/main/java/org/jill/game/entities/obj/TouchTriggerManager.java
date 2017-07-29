package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;

import org.jill.game.entities.ObjectEntityImpl;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;

/**
 * Touch trigger for trigger event.
 *
 * @author Emeric MARTINEAU
 */
public final class TouchTriggerManager extends ObjectEntityImpl {

    @Override
    public BufferedImage msgDraw() {
        return null;
    }

    @Override
    public void msgTouch(final ObjectEntity obj, KeyboardLayout keyboardLayout) {
        if (obj.isPlayer()) {
            messageDispatcher.sendMessage(EnumMessageType.TRIGGER, this);
        }
    }
}
