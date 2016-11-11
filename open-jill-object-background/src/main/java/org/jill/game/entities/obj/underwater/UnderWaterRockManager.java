package org.jill.game.entities.obj.underwater;

import java.awt.image.BufferedImage;
import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.CreateObjectMessage;
import org.jill.openjill.core.api.message.object.ObjectListMessage;

/**
 * Rolling rock.
 *
 * @author Emeric MARTINEAU
 */
public final class UnderWaterRockManager extends AbstractParameterObjectEntity {

    /**
     * Picture array.
     */
    private BufferedImage image;

    /**
     * Bubble trugger.
     */
    private int bubbleTrigger;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        // Init list of picture
        // Init list of picture
        int tileIndex = getConfInteger("tile");
        int tileSetIndex = getConfInteger("tileSet");

        this.image = objectParam.getPictureCache()
                .getImage(tileSetIndex, tileIndex);

        this.bubbleTrigger = getConfInteger("bubbleTrigger");
    }

    @Override
    public BufferedImage msgDraw() {
        return this.image;
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        final int randomTrigger = (int) (Math.random() * this.bubbleTrigger);

        if (randomTrigger == 0) {
            createBubbuleObject();
        }
    }

    /**
     * Create object.
     */
    private void createBubbuleObject() {
        final CreateObjectMessage com = CreateObjectMessage.buildFromClassName(
                getConfString("bubbleObject"));

        this.messageDispatcher.sendMessage(EnumMessageType.CREATE_OBJECT,
            com);

        ObjectEntity bees = com.getObject();
        bees.setY(getY() - bees.getY());

        bees.setX(getX() + (getWidth() / 2));

        this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                new ObjectListMessage(bees, true));
    }
}
