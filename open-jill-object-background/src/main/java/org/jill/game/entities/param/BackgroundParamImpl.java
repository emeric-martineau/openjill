package org.jill.game.entities.param;

import org.jill.dma.DmaEntry;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.BackgroundParam;
import org.jill.openjill.core.api.manager.TileManager;


/**
 * Background parameter (same background for all background. Juste dmaEntry
 * field change).
 *
 * @author Emeric MARTINEAU
 */
public final class BackgroundParamImpl implements BackgroundParam {
    /**
     * Object in file.
     */
    private DmaEntry dmaEntry;

    /**
     * Cache manager.
     */
    private TileManager pictureCache;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * Pos X in background map.
     */
    private int x;

    /**
     * Pos Y in background map.
     */
    private int y;

    /**
     * Default constructor.
     *
     * @param backgroundMap background map
     * @param pictureCacheManager picture cache
     */
    @Override
    public void init(final BackgroundEntity[][] backgroundMap,
            final TileManager pictureCacheManager) {
        this.pictureCache = pictureCacheManager;
        this.backgroundObject = backgroundMap;
    }

    /**
     * Dma entry.
     *
     * @return DmaEntry
     */
    public DmaEntry getDmaEntry() {
        return dmaEntry;
    }

    /**
     * dma entry.
     *
     * @param dma set dmaEntry
     */
    @Override
    public void setDmaEntry(final DmaEntry dma) {
        this.dmaEntry = dma;
    }

    /**
     * Background.
     *
     * @return background
     */
    @Override
    public BackgroundEntity[][] getBackgroundObject() {
        return backgroundObject;
    }

    /**
     * Picture cache.
     *
     * @return picture cache
     */
    @Override
    public TileManager getPictureCache() {
        return pictureCache;
    }

    /**
     * X.
     *
     * @return the x
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * X.
     *
     * @param xPos the x to set
     */
    @Override
    public void setX(final int xPos) {
        this.x = xPos;
    }

    /**
     * Y.
     *
     * @return the y
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * Y.
     *
     * @param yPos the y to set
     */
    @Override
    public void setY(final int yPos) {
        this.y = yPos;
    }
}