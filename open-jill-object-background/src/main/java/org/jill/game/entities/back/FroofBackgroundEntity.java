package org.jill.game.entities.back;

import java.awt.image.BufferedImage;

import org.jill.game.entities.back.abs.AbstractOorBackgroundEntity;

/**
 * Flame roof.
 *
 * @author Emeric MARTINEAU
 */
public final class FroofBackgroundEntity
    extends AbstractOorBackgroundEntity {

    /**
     * Current inde image to display.
     */
    private int indexEtat = 0;

    @Override
    public BufferedImage getPicture() {
        return images[getPictureSync().getIndexPicture()];
    }

    @Override
    public void msgDraw() {
        // Not special draw need

    }

    @Override
    public void msgUpdate() {
        this.indexEtat = getPictureSync().updatePictureIndex(
                this.indexEtat, images);
    }
}
