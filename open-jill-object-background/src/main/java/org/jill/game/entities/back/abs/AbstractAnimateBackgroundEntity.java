package org.jill.game.entities.back.abs;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Optional;

import org.jill.game.entities.picutre.PictureSynchronizer;
import org.jill.openjill.core.api.entities.BackgroundParam;

/**
 * Abstract class for animate background.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractAnimateBackgroundEntity extends
        AbstractSynchronisedImageBackgroundEntity {

    /**
     * Picture array.
     */
    private BufferedImage[] images;

    /**
     * Current inde image to display.
     */
    private int indexEtat = 0;


    /**
     * For internal use only.
     */
    protected AbstractAnimateBackgroundEntity() {
        super();
    }

    /**
     * Create a background with manager.
     *
     * @param backParam parameter of background
     */
    @Override
    public void init(final BackgroundParam backParam) {
        super.init(backParam);

        // For animage, use non number name
        setDmaName(getName().replaceAll("\\d", ""));

        int tileIndex = getConfInteger("tile");
        final int tileSetIndex = getConfInteger("tileSet");

        final Color backColor = getPictureCache().getBackgroundColor();
        final int increment = getConfInteger("increment", 1);

        images = new BufferedImage[getConfInteger("numberTileSet")];

        for (int index = 0; index < images.length; index++) {
            images[index] =
                    createPicture(getPictureCache().getImage(
                        tileSetIndex, tileIndex).get(), backColor);

            tileIndex += increment;
        }

        if (getPictureSync(getName()) == null) {
            int maxDisplayCounter = getConfInteger("cycle");

            // Create synchronizer
            final PictureSynchronizer ps =
                    new PictureSynchronizer(maxDisplayCounter);

            addPictureSync(getName(), ps);
        }
    }

    @Override
    public BufferedImage getPicture() {
        final int index = getPictureSync().getIndexPicture();

        return images[index];
    }

    @Override
    public void msgUpdate() {
        this.indexEtat = getPictureSync().updatePictureIndex(
                this.indexEtat, images.length);
    }
}
