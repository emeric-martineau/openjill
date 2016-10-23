package org.jill.game.entities.obj.underwater;

import java.awt.image.BufferedImage;
import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.game.entities.obj.util.UtilityObjectEntity;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.ObjectListMessage;

/**
 * Rolling rock.
 *
 * @author Emeric MARTINEAU
 */
public final class BubblesManager extends AbstractParameterObjectEntity {

    /**
     * Picture array.
     */
    private BufferedImage image;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * Move Y
     */
    private int[] moveY;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        // Init list of picture
        int tileIndex = getConfInteger("tile");
        int tileSetIndex = getConfInteger("tileSet");

        this.image = objectParam.getPictureCache()
                .getImage(tileSetIndex, tileIndex + getCounter());

        this.backgroundObject = objectParam.getBackgroundObject();

        // Compute move
        final String strMove = getConfString("moveY");
        final String[] arrayMove = strMove.split(",");

        this.moveY = new int[arrayMove.length];

        for (int index = 0; index < arrayMove.length; index++) {
            this.moveY[index] = Integer.valueOf(arrayMove[index]);
        }

        if (getWidth() == 0 || getHeight() == 0) {
            setWidth(this.image.getWidth());
            setHeight(this.image.getHeight());
        }
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        // Move up
        if (!UtilityObjectEntity.moveObjectUp(this, this.moveY[getCounter()],
                this.backgroundObject)) {
            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                new ObjectListMessage(this, false));
        }

        // TODO check if water
        // TODO random update X -1/0/+1
        // TODO random change counter
    }

    @Override
    public BufferedImage msgDraw() {
        return this.image;
    }
}
