package org.jill.game.entities.obj.underwater;

import java.awt.image.BufferedImage;
import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;

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
                .getImage(tileSetIndex, tileIndex + getCounter());
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        // TODO move object
    }

    @Override
    public BufferedImage msgDraw() {
        return this.image;
    }
}
