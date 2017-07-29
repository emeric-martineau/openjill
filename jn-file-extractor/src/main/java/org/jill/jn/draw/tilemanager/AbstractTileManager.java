/*

 */
package org.jill.jn.draw.tilemanager;

import java.awt.image.BufferedImage;
import java.util.Map;

import org.jill.jn.ObjectItem;
import org.jill.jn.draw.ScreenType;
import org.jill.sha.ShaTile;

/**
 * Abstract class to get tile
 *
 * @author  Emeric MARTINEAU
 */
public abstract class AbstractTileManager
{

    /**
     * Default constructor
     */
    public AbstractTileManager()
    {
        // Do nothing
    }

    /**
     * Init
     *
     * @param mapOfTile map object contain tile
     * @param typeScreen type of screen (EGA, CGA, VGA)
     */
    public abstract void init(final Map<Integer, ShaTile[]> mapOfTile, final ScreenType typeScreen);

    /**
     * Get tile of object.
     *
     * @param object jill game object
     *
     * @return picture
     */
    public abstract BufferedImage getTile(final ObjectItem object) ;
}
