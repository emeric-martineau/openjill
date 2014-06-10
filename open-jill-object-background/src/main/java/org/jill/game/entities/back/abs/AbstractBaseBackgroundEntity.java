package org.jill.game.entities.back.abs;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.jill.game.entities.picutre.PictureSynchronizer;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.BackgroundParam;

/**
 * Abstract class for BASExxxx background.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractBaseBackgroundEntity extends
        AbstractSynchronisedImageBackgroundEntity {

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

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
    protected AbstractBaseBackgroundEntity() {
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

        backgroundObject = backParam.getBackgroundObject();

        int tileIndex = dmaEntry.getTile();
        int tileSetIndex = dmaEntry.getTileset();

        images = new BufferedImage[getConfInteger("numberTileSet")];

        for (int index = 0; index < images.length; index++) {
            images[index] = pictureCache.getImage(tileSetIndex, tileIndex
                    + index);
        }

        if (getPictureSync(this.getClass()) == null) {
            int maxDisplayCounter = getConfInteger("cycle");

            // Create synchronizer
            final PictureSynchronizer ps =
                    new PictureSynchronizer(maxDisplayCounter);

            addPictureSync(this.getClass(), ps);
        }
    }

    /**
     * Return true if background is BASExxx.
     *
     * @param x x
     * @param y y
     *
     * @return true/false
     */
    protected final boolean isBackBase(final int x, final int y) {
        return backgroundObject[x][y].getName().startsWith("BASE");
    }

    /**
     * Return true if background on top is BASExxxx.
     *
     * @return true/false
     */
    protected final boolean isBaseTop() {
        return (y == 0) || isBackBase(x, y - 1);
    }

    /**
     * Return true if background on bottom is BASExxxx.
     *
     * @return true/false
     */
    protected final boolean isBaseBottom() {
        return (y == (backgroundObject[0].length - 1)) || isBackBase(x, y + 1);
    }

    /**
     * Return true if background on left is BASExxxx.
     *
     * @return true/false
     */
    protected final boolean isBaseLeft() {
        return (x == 0) || isBackBase(x - 1, y);
    }

    /**
     * Return true if background on right is BASExxxx.
     *
     * @return true/false
     */
    protected final boolean isBaseRight() {
        return (x == (backgroundObject.length - 1)) || isBackBase(x + 1, y);
    }

    @Override
    public final void msgDraw() {
        BufferedImage mask = null;

        final boolean isTop = !isBaseTop();
        final boolean isBottom = !isBaseBottom();
        final boolean isLeft = !isBaseLeft();
        final boolean isRight = !isBaseRight();

        if (isTop && isBottom && isLeft && isRight) {
            mask = pictureCache.getImage(42, 5);
        } else if (isTop && isLeft) {
            mask = pictureCache.getImage(42, 1);
        } else if (isTop && isRight) {
            mask = pictureCache.getImage(42, 2);
        } else if (isBottom && isLeft) {
            mask = pictureCache.getImage(42, 3);
        } else if (isBottom && isRight) {
            mask = pictureCache.getImage(42, 4);
        }

        if (mask != null) {
            Graphics2D g2;
            BufferedImage newImage;
            BufferedImage currentImage;

            for (int index = 0; index < images.length; index++) {
                currentImage = images[index];

                // Copy the commun picture cause is in cache
                newImage = new BufferedImage(currentImage.getWidth(),
                        currentImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

                g2 = newImage.createGraphics();

                g2.drawImage(currentImage, 0, 0, null);
                g2.drawImage(mask, 0, 0, null);

                g2.dispose();

                images[index] = newImage;
            }
        }
    }

    @Override
    public BufferedImage getPicture() {
        return images[getPictureSync().getIndexPicture()];
    }

    @Override
    public void msgUpdate() {
        this.indexEtat = getPictureSync().updatePictureIndex(
                this.indexEtat, images);
    }
}
