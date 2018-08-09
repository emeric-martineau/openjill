package org.jill.game.entities.param;

import org.jill.dma.DmaEntry;
import org.jill.dma.DmaFile;
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
     * Dma file
     */
    private DmaFile dmaFile;

    /**
     * Background map.
     */
    private int[][] backgroundObject;

    /**
     * Pos X in background map.
     */
    private int x;

    /**
     * Pos Y in background map.
     */
    private int y;

    @Override
    public void init(int[][] backgroundMap, TileManager pictureCacheManager, DmaFile dmaFile, DmaEntry dmaEntry) {
        this.pictureCache = pictureCacheManager;
        this.backgroundObject = backgroundMap;
        this.dmaEntry = dmaEntry;
        this.dmaFile = dmaFile;
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
     * Background.
     *
     * @return background
     */
    @Override
    public int[][] getBackgroundObject() {
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

    @Override
    public DmaFile getDmaFile() {
        return this.dmaFile;
    }

}