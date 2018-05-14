package org.jill.game.entities.back;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.jill.game.entities.back.abs.AbstractSynchronisedImageBackgroundEntity;
import org.jill.game.entities.picutre.PictureSynchronizer;
import org.jill.openjill.core.api.entities.BackgroundParam;

/**
 * Mist.
 *
 * @author Emeric MARTINEAU
 */
public final class MistBackgroundEntity
        extends AbstractSynchronisedImageBackgroundEntity {
    /**
     * Picture array.
     */
    private BufferedImage[] images;

    /**
     * Current inde image to display.
     */
    private int indexEtat = 0;

    /**
     * Constructor.
     *
     * @param backParam background parameter
     */
    @Override
    public void init(final BackgroundParam backParam) {
        super.init(backParam);

        int tileIndex = getDmaEntry().getTile();
        int tileSetIndex = getDmaEntry().getTileset();

        final Color backColor = getPictureCache().getBackgroundColor();
        BufferedImage srcImage;

        images = new BufferedImage[getConfInteger("numberTileSet")];

        for (int index = 0; index < images.length; index++) {
            srcImage = getPictureCache().getImage(tileSetIndex, tileIndex
                    + index).get();

            images[index] = createPicture(srcImage, backColor);
        }

        if (getPictureSync(this.getClass()) == null) {
            int maxDisplayCounter = getConfInteger("cycle");

            // Create synchronizer
            final PictureSynchronizer ps =
                    new PictureSynchronizer(maxDisplayCounter);

            addPictureSync(this.getClass(), ps);
        }
    }

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
                this.indexEtat, images.length);
    }
}
