package org.jill.game.entities.back;

import java.awt.image.BufferedImage;

import org.jill.game.entities.BackgroundEntityImpl;

/**
 * Standard background object.
 *
 * @author Emeric MARTINEAU
 */
public class StdBackgroundEntity extends BackgroundEntityImpl {
    @Override
    public BufferedImage getPicture() {
        return getPictureCache().getBackgroundPicture(getMapCode());
    }

    @Override
    public void msgDraw() {
    }

    @Override
    public void msgUpdate() {
    }
}