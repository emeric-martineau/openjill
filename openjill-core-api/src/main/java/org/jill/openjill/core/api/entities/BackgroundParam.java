package org.jill.openjill.core.api.entities;

import org.jill.dma.DmaEntry;
import org.jill.openjill.core.api.manager.TileManager;
import org.jill.openjill.core.api.message.MessageDispatcher;

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
     * @param messageDispatcherManager message dispatcher
     */
    void init(BackgroundEntity[][] backgroundMap,
            TileManager pictureCacheManager,
            MessageDispatcher messageDispatcherManager);

    /**
     * Background.
     *
     * @return background
     */
    BackgroundEntity[][] getBackgroundObject();

    /**
     * Message dispatcher.
     *
     * @return  message dispatcher for interaction with game
     */
    MessageDispatcher getMessageDispatcher();

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
