/*

 */
package org.jill.jn.draw.tilemanager;

import java.awt.image.BufferedImage;
import java.util.Map;

import org.jill.jn.ObjectItem;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaTile;

/**
 * Abstract class to get tile
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractTileManager {

    /**
     * Default constructor
     */
    public AbstractTileManager() {
        // Do nothing
    }

    /**
     * Init
     *
     * @param mapOfTile  map object contain tile
     * @param typeScreen type of screen (EGA, CGA, VGA)
     */
    public abstract void init(final Map<Integer, ShaTile[]> mapOfTile, final EnumScreenType typeScreen);

    /**
     * Get tile of object.
     *
     * @param object jill game object
     * @return picture
     */
    public abstract BufferedImage getTile(final ObjectItem object);
}
