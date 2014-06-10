package org.jill.game.manager.tile;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.jill.dma.DmaEntry;
import org.jill.dma.DmaFile;
import org.jill.game.config.ObjectInstanceFactory;
import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.manager.TileManager;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ColorMap;
import org.jill.sha.ShaFile;
import org.jill.sha.ShaTile;
import org.jill.sha.ShaTileSet;

/**
 * Cache of picture.
 *
 * @author Emeric Martineau
 */
public final class TileManagerImpl implements TileManager {
    /**
     * Color map.
     */
    private static final ColorMap VGA_COLOR_MAP
            = ObjectInstanceFactory.getVgaColor();

    /**
     * Color map.
     */
    private static final ColorMap EGA_COLOR_MAP
            = ObjectInstanceFactory.getEgaColor();

    /**
     * Color map.
     */
    private static final ColorMap CGA_COLOR_MAP
            = ObjectInstanceFactory.getCgaColor();

    /**
     * Instance by name of dma/sha file.
     */
//    private static final Map<String, TileManager> INSTANCES = new HashMap<>();

    /**
     * Map of tile.
     */
    private Map<Integer, ShaTile[]> mapOfTile;

    /**
     * Map of background tile.
     */
    private Map<Integer, BufferedImage> mapBackgroundPicture;

    /**
     * Map of picture.
     */
    private Map<String, BufferedImage> mapPicture;

    /**
     * Type opf screen.
     */
    private EnumScreenType typeScreen;

    /**
     * Sha file content.
     */
    private ShaFile shaFile;

    /**
     * Dma file content.
     */
    private DmaFile dmaFile;

    /**
     * Background color.
     */
    private Color backgroundColor;

    /**
     * Text manager.
     */
    private TextManager textManager;

    /**
     * Current color map.
     */
    private Color[] colorMap;


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
    @Override
    public void init(final File shaFileName,
            final File dmaFileName, final EnumScreenType typeScreen)
            throws IOException, ClassNotFoundException, IllegalAccessException,
            InstantiationException {
//        final String key = String.format("%s:%s:%s", shaFileName.getName(),
//                dmaFileName.getName(), typeScreen.toString());
//
//        TileManager pc;

//        if (INSTANCES.containsKey(key)) {
//            pc = INSTANCES.get(key);
//        } else {
            final ShaFile shaFile = ObjectInstanceFactory.getNewSha();
            shaFile.load(shaFileName.getAbsolutePath());

            final DmaFile dmaFile = ObjectInstanceFactory.getNewDma();
            dmaFile.load(dmaFileName.getAbsolutePath());

            init(shaFile, dmaFile, typeScreen);

//            INSTANCES.put(key, pc);
//        }
//
//        return pc;
    }

    /**
     * Constructor.
     *
     * @param shaFileContent sha file
     * @param dmaFileContent dma file
     * @param tpScreen type of screen
     *
     * @throws ClassNotFoundException if error when loading class
     * @throws IllegalAccessException if error when loading class
     * @throws InstantiationException if error when loading class
     */
    private void init(final ShaFile shaFileContent,
            final DmaFile dmaFileContent,
            final EnumScreenType tpScreen) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException {
        this.shaFile = shaFileContent;
        this.dmaFile = dmaFileContent;
        this.typeScreen = tpScreen;

        mapPicture = new HashMap<>();

        // Init map sprite
        mapOfTile = initMapSprite(shaFileContent);

        // Init background picture
        mapBackgroundPicture = initMapOfBackgroundSprite(dmaFileContent);

        if (EnumScreenType.CGA == tpScreen) {
            colorMap = CGA_COLOR_MAP.getColorMap();
        } else if (EnumScreenType.EGA == tpScreen) {
            colorMap = EGA_COLOR_MAP.getColorMap();
        } else {
            colorMap = VGA_COLOR_MAP.getColorMap();
        }

        // Recreate color but without alpha chanel
        backgroundColor = new Color(colorMap[0].getRGB());

        textManager = ObjectInstanceFactory.getNewTxtMng();
        textManager.init(mapOfTile, tpScreen);
    }

    /**
     * Init map of sprite.
     *
     * @param shaContent sha file
     *
     * @return a map link tileset index with tile
     */
    private Map<Integer, ShaTile[]> initMapSprite(final ShaFile shaContent) {
        // TileSet array
        final ShaTileSet[] tileSetArray = shaContent.getShaTileSet();
        // Map of tile
        final Map<Integer, ShaTile[]> localMapOfTile =
                new HashMap<>(tileSetArray.length);

        // Tile set index
        Integer tileSetIndex;
        // TileSet
        ShaTileSet tileSet;

        // Init map of tileset
        for (int indexTileSet = 0; indexTileSet < tileSetArray.length;
                indexTileSet++) {
            tileSet = tileSetArray[indexTileSet];

//           if ((tileSet.getBitColor() != 8) || (typeScreen == ScreenType.VGA))
//            {
                tileSetIndex = Integer.valueOf(tileSet.getTitleSetIndex());

                localMapOfTile.put(tileSetIndex, tileSet.getShaTile());
//            }
        }

        return localMapOfTile;
    }

    /**
     * Init picture from Dma file.
     *
     * @param dmaContentFile dma file
     *
     * @return map between tile and picture
     */
    private Map<Integer, BufferedImage> initMapOfBackgroundSprite(
            final DmaFile dmaContentFile) {
        final Iterator<Integer> itDma = dmaContentFile.getDmaEntryIterator();
        final Map<Integer, BufferedImage> localMapBackgroundPicture =
                new HashMap<>(dmaContentFile.getDmaEntryCount());

        // Tile set index
        Integer tileSetIndex;
        // Dma entry
        DmaEntry dmaEntry;
        // Map code
        int mapCode;
        // Tile picture
        BufferedImage tilePicture;
        // Array of tile
        ShaTile[] tileArray;
        // Tile
        ShaTile tile;

        while (itDma.hasNext()) {
            mapCode = itDma.next();

            dmaEntry = dmaContentFile.getDmaEntry(mapCode);

            if (dmaEntry != null) {
                tileSetIndex = dmaEntry.getTileset();

                // Get picture
                tileArray = mapOfTile.get(tileSetIndex);

                // Some Dma entry are invalid
                if (tileArray != null
                        && (dmaEntry.getTile() < tileArray.length)) {
                    tile = tileArray[dmaEntry.getTile()];

                    if (typeScreen == EnumScreenType.CGA) {
                        tilePicture = tile.getPictureCga();
                    } else if (typeScreen == EnumScreenType.EGA) {
                        tilePicture = tile.getPictureEga();
                    } else {
                        tilePicture = tile.getPictureVga();
                    }

                    mapPicture.put(String.format("%d_%d",
                            tile.getTitleSetIndex(), tile.getImageIndex()),
                            tilePicture);
                    localMapBackgroundPicture.put(mapCode, tilePicture);
                }
            }
        }

        return localMapBackgroundPicture;
    }

    /**
     * Return picture of background.
     *
     * @param mapCode map code
     *
     * @return null if background not found
     */
    @Override
    public BufferedImage getBackgroundPicture(final int mapCode) {
        return mapBackgroundPicture.get(Integer.valueOf(mapCode));
    }

    /**
     * Return picture aand cache it.
     *
     * @param tileSetIndex index of tileset
     * @param tileIndex index of tile
     *
     * @return can be return null if picture not found
     */
    @Override
    public BufferedImage getImage(final int tileSetIndex,
            final int tileIndex) {
        final String key = String.format("%d_%d", tileSetIndex, tileIndex);
        BufferedImage image = mapPicture.get(key);

        if (image == null) {
            // Array of tile
            ShaTile[] tileArray = mapOfTile.get(tileSetIndex);

            if (tileArray != null && (tileIndex < tileArray.length)) {
                ShaTile tile = tileArray[tileIndex];

                if (typeScreen == EnumScreenType.CGA) {
                    image = tile.getPictureCga();
                } else if (typeScreen == EnumScreenType.EGA) {
                    image = tile.getPictureEga();
                } else {
                    image = tile.getPictureVga();
                }

                mapPicture.put(key, image);
            }
        }

        return image;
    }

    /**
     * Return type of screen of this cache.
     *
     * @return type of screen
     */
    @Override
    public EnumScreenType getTypeScreen() {
        return typeScreen;
    }

    /**
     * Return sha file content of this cache.
     *
     * @return sha file
     */
    @Override
    public ShaFile getShaFile() {
        return shaFile;
    }

    /**
     * Return dmaFile content of this cache.
     *
     * @return dma file
     */
    @Override
    public DmaFile getDmaFile() {
        return dmaFile;
    }

    /**
     * @return backgroundColor.
     */
    @Override
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @return textManager
     */
    @Override
    public TextManager getTextManager() {
        return textManager;
    }

    /**
     * @return colorMap
     */
    @Override
    public Color[] getColorMap() {
        return colorMap;
    }
}
