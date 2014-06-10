/**
 *
 */
package org.jill.jn.draw.tilemanager.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.jill.jn.JnFileExtractor;
import org.jill.jn.ObjectItem;
import org.jill.jn.draw.ScreenType;
import org.jill.jn.draw.tilemanager.AbstractTileManager;
import org.jill.sha.ShaTile;

/**
 * Class to draw standard manage tile (apple, crystal rock...)
 *
 * @author Emeric MARTINEAU
 *
 */
public class StandardTileManager extends AbstractTileManager
{
    /**
     * Map of object tile
     */
    private static volatile Map<String, BufferedImage> mapObjectPicture = null ;

    /**
     * Map of tile
     */
    protected Map<Integer, ShaTile[]> mapOfTile ;

    /**
     * Type screen
     */
    protected ScreenType typeScreen ;

    /**
     * Static cache of picture
     * @return
     */
    protected Map<String, BufferedImage> getMapObjectPicture()
    {
        return mapObjectPicture ;
    }

    /**
     * Load properties
     * @return
     */
    protected Properties loadObjectTitle()
    {
        Properties mapObjectTile = new Properties() ;

        try
        {
            mapObjectTile.load(JnFileExtractor.class.getClassLoader().getResourceAsStream("objects_picture_mapping.properties")) ;
        }
        catch (IOException e)
        {
            System.out.println("Error, can't load properties file where mapping objects and tiles") ;
            e.printStackTrace() ;

            mapObjectTile = null ;
        }

        return mapObjectTile ;
    }

    /**
     * Init picture of know object
     * @param typeScreen type of display
     * @return
     */
    protected Map<String, BufferedImage> initMapOfObjectSrite()
    {
        // Load mapping
        final Properties mapObjectTile = loadObjectTitle() ;
        // Get keys
        final Enumeration<?> e = mapObjectTile.propertyNames();
        // Map
        final Map<String, BufferedImage> mapObjectPicture = new HashMap<>() ;

        // Tile picture
        BufferedImage tilePicture ;
        String key ;
        String value ;
        String[] tileSetTile ;
        ShaTile[] tileArray ;
        ShaTile tile ;

        while(e.hasMoreElements())
        {
            key = (String) e.nextElement() ;

            value = mapObjectTile.getProperty(key) ;

            tileSetTile = value.split(",") ;

            // Array of tile
            tileArray = mapOfTile.get(Integer.valueOf(tileSetTile[0]));
            tile = tileArray[Integer.valueOf(tileSetTile[1])] ;

            if (typeScreen == ScreenType.CGA)
            {
                tilePicture = tile.getPictureCga() ;
            }
            else if (typeScreen == ScreenType.EGA)
            {
                tilePicture = tile.getPictureEga() ;
            }
            else
            {
                tilePicture = tile.getPictureVga() ;
            }

            mapObjectPicture.put(key, tilePicture) ;
        }

        return mapObjectPicture ;
    }

    /* (non-Javadoc)
     * @see org.jill.jn.draw.tilemanager.AbstractTileManager#init(java.util.Map, org.jill.jn.draw.ScreenType)
     */
    @Override
    public void init(final Map<Integer, ShaTile[]> mapOfTile, final ScreenType typeScreen)
    {
        this.mapOfTile = mapOfTile ;
        this.typeScreen = typeScreen ;

        if (mapObjectPicture == null)
        {
            mapObjectPicture = initMapOfObjectSrite() ;
        }

        this.mapOfTile = null ;
        this.typeScreen = null ;
    }

    /* (non-Javadoc)
     * @see org.jill.jn.draw.tilemanager.AbstractTileManager#getTile(org.jill.dma.ObjectItem)
     */
    @Override
    public BufferedImage getTile(final ObjectItem object)
    {
        String objectType = String.valueOf(object.getType()) ;
        BufferedImage bi ;

        bi = mapObjectPicture.get(objectType) ;

        if (bi == null)
        {
            System.err.println("No tile found for tileType = ".concat(objectType)) ;
        }

        return bi ;
    }

}
