package org.jill.game.entities.param;

import org.jill.jn.ObjectItem;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.manager.TileManager;
import org.jill.openjill.core.api.message.MessageDispatcher;


/**
 * Object parameter (same object for all object. Juste object field change).
 *
 * @author Emeric MARTINEAU
 */
public final class ObjectParamImpl implements ObjectParam {
    /**
     * Object in file.
     */
    private ObjectItem object;

    /**
     * Cache manager.
     */
    private TileManager pictureCache;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * Message dispatcher.
     */
    private MessageDispatcher messageDispatcher;

    /**
     * Current level number.
     */
    private int level;

    /**
     * Default constructor.
     *
     * @param backgroundMap            background
     * @param pictureCacheManager      picture
     * @param messageDispatcherManager message
     * @param levelNumber              level
     */
    @Override
    public void init(final BackgroundEntity[][] backgroundMap,
            final TileManager pictureCacheManager,
            final MessageDispatcher messageDispatcherManager,
            final int levelNumber) {
        this.pictureCache = pictureCacheManager;
        this.backgroundObject = backgroundMap;
        this.messageDispatcher = messageDispatcherManager;
        this.level = levelNumber;
    }

    /**
     * Object.
     *
     * @return object
     */
    @Override
    public ObjectItem getObject() {
        return object;
    }

    /**
     * Object.
     *
     * @param obj set object
     */
    @Override
    public void setObject(final ObjectItem obj) {
        this.object = obj;
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
     * Message dispatcher.
     *
     * @return message dispatcher for interaction with game
     */
    @Override
    public MessageDispatcher getMessageDispatcher() {
        return messageDispatcher;
    }

    /**
     * Level.
     *
     * @return return current level
     */
    @Override
    public int getLevel() {
        return level;
    }
}