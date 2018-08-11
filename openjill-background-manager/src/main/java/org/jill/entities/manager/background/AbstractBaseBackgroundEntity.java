package org.jill.entities.manager.background;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.jill.dma.DmaEntry;
import org.jill.dma.DmaFile;
import org.jill.jn.BackgroundLayer;
import org.jill.openjill.core.api.entities.BackgroundParam;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaFile;

/**
 * Abstract class for BASExxxx background.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractBaseBackgroundEntity extends AbstractBackground {
    /**
     * Picture array of original image.
     */
    private BufferedImage[] originalImages;

    /**
     * This class manage many background. We need use cache to avoid image conflict and increase speed performance.
     */
    private Map<String, BufferedImage[]> pictureCache = new HashMap();

    /**
     * Sha file to get near image.
     */
    private ShaFile shaFile;

    /**
     * Dma file to get near image.
     */
    private DmaFile dmaFile;

    /**
     * Screen configuration.
     */
    private EnumScreenType screen;

    /**
     * Index of picture.
     */
    private int indexPicture = 0;

    /**
     * For internal use only.
     */
    protected AbstractBaseBackgroundEntity() {
        super();
    }

    /**
     * Return true if background is BASExxx.
     *
     * @param x x
     * @param y y
     *
     * @return true/false
     */
    protected final boolean isBackBase(final BackgroundLayer background, final int x, final int y) {
        Optional<DmaEntry> dma = dmaFile.getDmaEntry(background.getMapCode(x, y));

        if (dma.isPresent()) {
            return dma.get().getName().startsWith("BASE");
        }

        return false;
    }

    /**
     * Return true if background on top is BASExxxx.
     *
     * @param x x
     * @param y y
     *
     * @return true/false
     */
    protected final boolean isBaseTop(final BackgroundLayer background, final int x, final int y) {
        return (y == 0) || isBackBase(background, x, y - 1);
    }

    /**
     * Return true if background on bottom is BASExxxx.
     *
     * @param x x
     * @param y y
     *
     * @return true/false
     */
    protected final boolean isBaseBottom(final BackgroundLayer background, final int x, final int y) {
        return (y == (BackgroundLayer.MAP_HEIGHT - 1))
                || isBackBase(background, x, y + 1);
    }

    /**
     * Return true if background on left is BASExxxx.
     *
     * @param x x
     * @param y y
     *
     * @return true/false
     */
    protected final boolean isBaseLeft(final BackgroundLayer background, final int x, final int y) {
        return (x == 0) || isBackBase(background,x - 1, y);
    }

    /**
     * Return true if background on right is BASExxxx.
     *
     * @param x x
     * @param y y
     *
     * @return true/false
     */
    protected final boolean isBaseRight(final BackgroundLayer background, final int x, final int y) {
        return (x == (BackgroundLayer.MAP_WIDTH - 1))
                || isBackBase(background, x + 1, y);
    }

    @Override
    public void init(BackgroundParam backParameter) {
        dmaFile = backParameter.getDmaFile();
        dmaEntry = backParameter.getDmaEntry();
        shaFile = backParameter.getShaFile();
        screen = backParameter.getScreen();

// TODO config file for size of images/originalImage and mask
// TODO add gamecount & 3 = 0 => update picture

        int tileIndex = dmaEntry.getTile();
        int tileSetIndex = dmaEntry.getTileset();

        originalImages = new BufferedImage[3];

        for (int index = 0; index < originalImages.length; index++) {
            final Optional<BufferedImage> currentPicture = getPicture(backParameter.getShaFile(), tileSetIndex,
                    tileIndex+ index, screen);

            if (currentPicture.isPresent()) {
                originalImages[index] = currentPicture.get();
            }
        }
    }

    @Override
    public void msgDraw(final BackgroundLayer background, final int x, final int y) {
        BufferedImage mask = null;

        final boolean isTop = !isBaseTop(background, x, y);
        final boolean isBottom = !isBaseBottom(background, x, y);
        final boolean isLeft = !isBaseLeft(background, x, y);
        final boolean isRight = !isBaseRight(background, x, y);

        if (isTop && isBottom && isLeft && isRight) {
            mask = getPicture (shaFile, 42, 5, screen).get();
        } else if (isTop && isLeft) {
            mask = getPicture (shaFile, 42, 1, screen).get();
        } else if (isTop && isRight) {
            mask = getPicture (shaFile, 42, 2, screen).get();
        } else if (isBottom && isLeft) {
            mask = getPicture (shaFile, 42, 3, screen).get();
        } else if (isBottom && isRight) {
            mask = getPicture (shaFile, 42, 4, screen).get();
        }

        Graphics2D g2;
        BufferedImage newImage;
        BufferedImage currentImage;

        final BufferedImage[] images = new BufferedImage[3];

        for (int index = 0; index < images.length; index++) {
            if (mask != null) {
                currentImage = originalImages[index];

                // Copy the commun picture cause is in cache
                newImage = new BufferedImage(currentImage.getWidth(),
                        currentImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

                g2 = newImage.createGraphics();

                draw(g2, currentImage, 0, 0);
                draw(g2, mask, 0, 0);

                g2.dispose();

                images[index] = newImage;
            } else {
                images[index] = originalImages[index];
            }
        }

        pictureCache.put(String.format("%d_%d", x, y), images);
    }

    @Override
    public BufferedImage getPicture(final int x, final int y) {
        final BufferedImage[] images = pictureCache.get(String.format("%d_%d", x, y));

        return images[indexPicture];
    }

    @Override
    public void msgUpdate(BackgroundLayer background, int x, int y) {
        System.out.println("TODO msgUpdate !!!!");
    }
}
