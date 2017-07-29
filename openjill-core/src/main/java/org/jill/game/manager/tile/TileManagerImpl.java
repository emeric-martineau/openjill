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

    /*
      Instance by name of dma/sha file.
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

    private Map<String, BufferedImage> imageCache = new HashMap<>();

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

            final ShaFile currentShaFile = ObjectInstanceFactory.getNewSha();
            currentShaFile.load(shaFileName.getAbsolutePath());

            final DmaFile currentDmaFile = ObjectInstanceFactory.getNewDma();
            currentDmaFile.load(dmaFileName.getAbsolutePath());

            init(currentShaFile, currentDmaFile, typeScreen);
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

        // Init map sprite
        mapOfTile = initMapSprite(shaFileContent);

        // Init background picture
        mapBackgroundPicture = initMapOfBackgroundSprite(dmaFileContent);

        switch (tpScreen) {
            case CGA:
                colorMap = CGA_COLOR_MAP.getColorMap();
                break;
            case EGA:
                colorMap = EGA_COLOR_MAP.getColorMap();
                break;
            default:
                colorMap = VGA_COLOR_MAP.getColorMap();
                break;
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
        for (ShaTileSet tileSetArray1 : tileSetArray) {
            tileSet = tileSetArray1;

            tileSetIndex = tileSet.getTitleSetIndex();
            localMapOfTile.put(tileSetIndex, tileSet.getShaTile());
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
        BufferedImage image;
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

                    image = returnImageFromScreenColor(tile);

                    localMapBackgroundPicture.put(mapCode, image);
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
        return mapBackgroundPicture.get(mapCode);
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
        BufferedImage image = null;

        final String cacheKey = String.format("%d-%d", tileSetIndex, tileIndex);

        if (imageCache.containsKey(cacheKey)) {
            image = imageCache.get(cacheKey);
        } else {

            // Array of tile
            ShaTile[] tileArray = mapOfTile.get(tileSetIndex);

            if (tileArray != null && (tileIndex < tileArray.length)) {
                ShaTile tile = tileArray[tileIndex];

                image = returnImageFromScreenColor(tile);
            }

            imageCache.put(cacheKey, image);
        }

        return image;
    }

    /**
     * Return image with good screen resolution.
     *
     * @param tile tile
     *
     * @return picture
     */
    private BufferedImage returnImageFromScreenColor(final ShaTile tile) {
        final BufferedImage image;
        switch (typeScreen) {
            case CGA:
                image = tile.getPictureCga();
                break;
            case EGA:
                image = tile.getPictureEga();
                break;
            default:
                image = tile.getPictureVga();
                break;
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
