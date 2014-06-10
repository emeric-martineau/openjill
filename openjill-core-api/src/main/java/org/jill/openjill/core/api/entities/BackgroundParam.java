package org.jill.openjill.core.api.entities;

import org.jill.dma.DmaEntry;
import org.jill.openjill.core.api.manager.TileManager;

/**
 * Background parameter when background is load.
 *
 * @author Emeric MARTINEAU
 */
public interface BackgroundParam {

    /**
     * Default constructor.
     *
     * @param backgroundMap background map
     * @param pictureCacheManager picture cache
     */
    void init(BackgroundEntity[][] backgroundMap,
            TileManager pictureCacheManager);

    /**
     * Background.
     *
     * @return background
     */
    BackgroundEntity[][] getBackgroundObject();

    /**
     * Dma entry.
     *
     * @return DmaEntry
     */
    DmaEntry getDmaEntry();

    /**
     * Picture cache.
     *
     * @return picture cache
     */
    TileManager getPictureCache();

    /**
     * X.
     *
     * @return the x
     */
    int getX();

    /**
     * Y.
     *
     * @return the y
     */
    int getY();

    /**
     * dma entry.
     *
     * @param dma set dmaEntry
     */
    void setDmaEntry(DmaEntry dma);

    /**
     * X.
     *
     * @param xPos the x to set
     */
    void setX(int xPos);

    /**
     * Y.
     *
     * @param yPos the y to set
     */
    void setY(int yPos);

}
