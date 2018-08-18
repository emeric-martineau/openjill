package org.jill.openjill.core.api.entities;

import org.jill.dma.DmaFile;
import org.jill.jn.BackgroundLayer;
import org.jill.jn.ObjectItem;
import org.jill.openjill.core.api.message.MessageDispatcher;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaFile;

/**
 * Object parameter when object is load.
 *
 * @author Emeric MARTINEAU
 */
public interface ObjectParam {

    /**
     * Default constructor.
     *
     * @param background            background
     * @param messageDispatcherManager message
     * @param shaFile picture
     * @param dmaFile  dma file of game
     * @param object object item
     * @param screen screen type
     * @param levelNumber              level
     */
    void init(BackgroundLayer background, MessageDispatcher messageDispatcherManager, ShaFile shaFile, DmaFile dmaFile,
              ObjectItem object, EnumScreenType screen, int levelNumber);

    /**
     * Background.
     *
     * @return background
     */
    BackgroundLayer getBackground();

    /**
     * Message dispatcher.
     *
     * @return message dispatcher for interaction with game
     */
    MessageDispatcher getMessageDispatcher();

    /**
     * Picture cache.
     *
     * @return picture cache
     */
    ShaFile getShaFile();

    /**
     * Current dma file of game.
     *
     * @return dma file
     */
    DmaFile getDmaFile();

    /**
     * Object.
     *
     * @return object
     */
    ObjectItem getObject();

    /**
     * Current type of screen.
     *
     * @return enum
     */
    EnumScreenType getScreen();

    /**
     * Level.
     *
     * @return return current level
     */
    int getLevel();
}
