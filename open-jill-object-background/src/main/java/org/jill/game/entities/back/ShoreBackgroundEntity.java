package org.jill.game.entities.back;

import java.awt.image.BufferedImage;

import org.jill.game.entities.BackgroundEntityImpl;

/**
 * Standard background object.
 *
 * @author Emeric MARTINEAU
 */
public final class ShoreBackgroundEntity extends BackgroundEntityImpl {
    @Override
    public BufferedImage getPicture() {
        return getPictureCache().getBackgroundPicture(getMapCode());
    }


    @Override
    public void msgDraw() {
        // Do nothing. See BaseTree and BaseWater
    }

    @Override
    public void msgUpdate() {
    }
}
