package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;

/**
 * Bees object.
 *
 * @author Emeric MARTINEAU
 */
public final class BeesManager extends AbstractParameterObjectEntity {

     /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);
    }

    @Override
    public BufferedImage msgDraw() {
         return null;
    }

    /**
     * Call to update.
     */
    @Override
    public void msgUpdate() {
    }

    @Override
    public void msgKill(final ObjectEntity sender,
        final int nbLife, final int typeOfDeath) {
    }
}
