/*

 */
package org.jill.jn.draw.cache;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.jill.dma.DmaEntry;
import org.jill.dma.DmaFile;
import org.jill.jn.JnFileExtractor;
import org.jill.jn.ObjectItem;
import org.jill.jn.draw.ScreenType;
import org.jill.jn.draw.tilemanager.AbstractTileManager;
import org.jill.sha.ShaFile;
import org.jill.sha.ShaTile;
import org.jill.sha.ShaTileSet;

/**
 * Cache of picture
 *
 * @author Emeric Martineau
 */
public class PictureCache
{
    /**
     * Map of tile
     */
    private final Map<Integer, ShaTile[]> mapOfTile ;

    /**
     * Map of background tile
     */
    private final Map<Integer, BufferedImage> mapBackgroundPicture ;

    /**
     * Type opf screen
     */
    private final ScreenType typeScreen ;

    /**
     * Map of object tile
     */
    private final Map<String, AbstractTileManager> mapObjectPicture ;

    public PictureCache(final ShaFile shaFile, final DmaFile dmaFile,
            final ScreenType typeScreen)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException
    {
        this.typeScreen = typeScreen ;

        // Init map sprite
        mapOfTile = initMapSprite(shaFile) ;

        // Init background picture
        mapBackgroundPicture = initMapOfBackgroundSprite(dmaFile) ;

        // Init object picture
        mapObjectPicture = initMapOfObjectSrite() ;
    }

    /**
     * Init map of sprite
     *
     * @param shaFile sha file
     *
     * @return map of sprite
     */
    private Map<Integer, ShaTile[]> initMapSprite(final ShaFile shaFile)
    {
        // TileSet array
        final ShaTileSet[] tileSetArray = shaFile.getShaTileSet() ;
        // Map of tile
        final Map<Integer, ShaTile[]> mapOfTile =
                new HashMap<>(tileSetArray.length) ;

        // Tile set index
        Integer tileSetIndex ;
        // TileSet
        ShaTileSet tileSet ;

        // Init map of tileset
        for (ShaTileSet tileSetArray1 : tileSetArray) {
            tileSet = tileSetArray1;
            if ((tileSet.getBitColor() != 8) || (typeScreen == ScreenType.VGA)) {
                tileSetIndex = tileSet.getTitleSetIndex();
                mapOfTile.put(tileSetIndex, tileSet.getShaTile()) ;
            }
        }

        return mapOfTile ;
    }

    /**
     * Init picture of know object
     *
     * @return map of tile
     *
     * @throws ClassNotFoundException if error
     * @throws InstantiationException if error
     * @throws IllegalAccessException if error
     */
    @SuppressWarnings("unchecked")
    private Map<String, AbstractTileManager> initMapOfObjectSrite()
            throws ClassNotFoundException, IllegalAccessException,
                InstantiationException
    {
        // Load mapping
        final Properties mapObjectTile = loadObjectTitle() ;
        // Get keys
        final Enumeration<?> e = mapObjectTile.propertyNames();
        // Map between key and manager
        final Map<String, AbstractTileManager> mapObjectPicture =
                new HashMap<>() ;
        // Map between manager name and manager
        final Map<String, AbstractTileManager> mapManagerName =
                new HashMap<>() ;


        String key ;
        String value ;

        AbstractTileManager manager ;

        Class<AbstractTileManager> c ;

        while(e.hasMoreElements())
        {
            key = (String) e.nextElement() ;

            value = mapObjectTile.getProperty(key) ;

            manager = mapManagerName.get(value) ;

            // Search if manager already exists
            if (manager == null)
            {
                c = (Class<AbstractTileManager>) Class.forName(value) ;
                manager = c.newInstance() ;
                manager.init(mapOfTile, typeScreen) ;

                mapObjectPicture.put(key, manager) ;
                mapManagerName.put(value, manager) ;
            }
            else
            {
                mapObjectPicture.put(key, manager) ;
            }
        }

        return mapObjectPicture ;
    }

    /**
     * Init picture from Dma file
     *
     * @param dmaFile dma file
     */
    private Map<Integer, BufferedImage> initMapOfBackgroundSprite(
            final DmaFile dmaFile)
    {
        final Iterator<Integer> itDma = dmaFile.getDmaEntryIterator() ;
        final Map<Integer, BufferedImage> mapBackgroundPicture =
                new HashMap<>(dmaFile.getDmaEntryCount()) ;

        // Tile set index
        Integer tileSetIndex ;
        // Dma entry
        DmaEntry dmaEntry ;
        // Map code
        int mapCode ;
        // Tile picture
        BufferedImage tilePicture ;
        // Array of tile
        ShaTile[] tileArray ;
        // Tile
        ShaTile tile ;

        while(itDma.hasNext())
        {
            mapCode = itDma.next() ;

            dmaEntry = dmaFile.getDmaEntry(mapCode) ;

            if (dmaEntry != null)
            {
                tileSetIndex = dmaEntry.getTileset() ;

                // Get picture
                tileArray = mapOfTile.get(tileSetIndex) ;

                // Some Dma entry are invalid
                if (tileArray != null && (dmaEntry.getTile() < tileArray.length))
                {
                    tile = tileArray[dmaEntry.getTile()] ;

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

                    mapBackgroundPicture.put(mapCode, tilePicture) ;
                }
            }
        }

        return mapBackgroundPicture ;
    }


    /**
     * Load properties.
     *
     * @return propoerties object
     */
    private static Properties loadObjectTitle()
    {
        Properties mapObjectTile = new Properties() ;

        try
        {
            mapObjectTile.load(JnFileExtractor.class.getClassLoader().
                    getResourceAsStream("objects_manager_mapping.properties")) ;
        }
        catch (IOException e)
        {
            System.out.println("Error, can't load properties file where " +
                    " mapping objects and manager") ;
            e.printStackTrace() ;

            mapObjectTile = null ;
        }

        return mapObjectTile ;
    }

    /**
     * Return picture of background
     *
     * @param mapCode map code
     *
     * @return null if background not found
     */
    public BufferedImage getBackgroundPicture(final int mapCode)
    {
        return mapBackgroundPicture.get(mapCode) ;
    }

    /**
     * Return picture of object
     *
     * @param object object to get picture
     *
     * @return null if background not found
     */
    public BufferedImage getObjectPicture(final ObjectItem object)
    {
        String objectType = String.valueOf(object.getType()) ;
        AbstractTileManager manager ;

        manager = mapObjectPicture.get(objectType) ;

        if (manager == null)
        {
            //System.err.println("No tile manager found for tileType = ".concat(objectType)) ;

            return null ;
        }

        return manager.getTile(object) ;
    }
}
