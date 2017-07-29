package org.jill.openjill.core.api.manager;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.jill.dma.DmaFile;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaFile;

/**
 * Cache of picture.
 * @author Emeric MARTINEAU
 */
public interface TileManager {
    /**
     * Return an instance of TileManagerImpl.
     *
     * @param shaFileName sha file
     * @param dmaFileName dma file
     * @param typeScreen type of screen
     * *
     * @throws IOException if error reading file
     * @throws ClassNotFoundException if error when loading class
     * @throws IllegalAccessException if error when loading class
     * @throws InstantiationException if error when loading class
     */
    void init(File shaFileName,
            File dmaFileName, EnumScreenType typeScreen)
            throws IOException;

    /**
     * @return backgroundColor.
     */
    Color getBackgroundColor();

    /**
     * Return picture of background.
     *
     * @param mapCode map code
     *
     * @return null if background not found
     */
    BufferedImage getBackgroundPicture(final int mapCode);

    /**
     * @return colorMap
     */
    Color[] getColorMap();

    /**
     * Return dmaFile content of this cache.
     *
     * @return dma file
     */
    DmaFile getDmaFile();

    /**
     * Return picture aand cache it.
     *
     * @param tileSetIndex index of tileset
     * @param tileIndex index of tile
     *
     * @return can be return null if picture not found
     */
    BufferedImage getImage(final int tileSetIndex, final int tileIndex);

    /**
     * Return sha file content of this cache.
     *
     * @return sha file
     */
    ShaFile getShaFile();

    /**
     * @return textManager
     */
    TextManager getTextManager();

    /**
     * Return type of screen of this cache.
     *
     * @return type of screen
     */
    EnumScreenType getTypeScreen();
}
