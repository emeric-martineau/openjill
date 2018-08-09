package org.jill.openjill.core.api.entities;

import org.jill.dma.DmaEntry;
import org.jill.dma.DmaFile;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaFile;

/**
 * Background parameter when background is load.
 *
 * @author Emeric MARTINEAU
 */
public interface BackgroundParam {

    /**
     * Default constructor.
     *
     * @param shaFile picture
     * @param dmaFile  dma file of game
     * @param dmaEntry dma entry
     * @param screen screen type
     */
    void init(ShaFile shaFile, DmaFile dmaFile, DmaEntry dmaEntry, EnumScreenType screen);

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
     * Current dma entry of this background.
     *
     * @return dma entry
     */
    DmaEntry getDmaEntry();

    /**
     * Current type of screen.
     *
     * @return enum
     */
    EnumScreenType getScreen();
}
