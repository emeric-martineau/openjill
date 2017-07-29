package org.jill.openjill.core.api.entities;

import org.jill.jn.ObjectItem;
import org.jill.openjill.core.api.manager.TileManager;
import org.jill.openjill.core.api.message.MessageDispatcher;

/**
 * Object parameter when object is load.
 *
 * @author Emeric MARTINEAU
 */
public interface ObjectParam {

    /**
     * Default constructor.
     *
     * @param backgroundMap background
     * @param pictureCacheManager  picture
     * @param messageDispatcherManager message
     * @param levelNumber level
     */
    void init(BackgroundEntity[][] backgroundMap,
            TileManager pictureCacheManager,
            MessageDispatcher messageDispatcherManager,
            int levelNumber);

    /**
     * Background.
     *
     * @return background
     */
    BackgroundEntity[][] getBackgroundObject();

    /**
     * Level.
     *
     * @return return current level
     */
    int getLevel();

    /**
     * Message dispatcher.
     *
     * @return  message dispatcher for interaction with game
     */
    MessageDispatcher getMessageDispatcher();

    /**
     * Object.
     *
     * @return object
     */
    ObjectItem getObject();

    /**
     * Picture cache.
     *
     * @return picture cache
     */
    TileManager getPictureCache();

    /**
     * Object.
     *
     * @param obj set object
     */
    void setObject(final ObjectItem obj);

}
