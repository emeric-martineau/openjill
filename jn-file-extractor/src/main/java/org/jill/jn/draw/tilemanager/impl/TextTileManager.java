/*

 */
package org.jill.jn.draw.tilemanager.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.jill.jn.ObjectItem;
import org.jill.jn.draw.tilemanager.AbstractTileManager;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.CgaColorMapImpl;
import org.jill.sha.ColorMap;
import org.jill.sha.EgaColorMapImpl;
import org.jill.sha.ShaTile;
import org.jill.sha.VgaColorMapImpl;

/**
 * Class to draw text
 *
 * @author Emeric MARTINEAU
 */
public class TextTileManager extends AbstractTileManager {
    /**
     * Value to know transparent color
     */
    public static final int TRANSPARENT_COLOR_VALUE = -1;
    /**
     * Color map.
     */
    private static final ColorMap VGA_COLOR_MAP = new VgaColorMapImpl();
    /**
     * Color map.
     */
    private static final ColorMap EGA_COLOR_MAP = new EgaColorMapImpl();
    /**
     * Color map.
     */
    private static final ColorMap CGA_COLOR_MAP = new CgaColorMapImpl();
    /**
     * Background color index
     */
    private static final int BACKGROUND_COLOR_INDEX = 3;

    /**
     * Foreground color index
     */
    private static final int FOREGROUND_COLOR_INDEX1 = 1;

    /**
     * Foreground color index
     */
    private static final int FOREGROUND_COLOR_INDEX2 = 2;

    /**
     * Tile to draw big text
     */
    private ShaTile[] bigText;

    /**
     * Tile to draw small text
     */
    private ShaTile[] smallText;

    /**
     * Color map EGA or VGA
     */
    private Color[] colorMap;

    /**
     * Screen type
     */
    private EnumScreenType typeScreen;

    /**
     * Fill picture in black.
     *
     * @param image picture
     * @param g2    graphic object to draw
     */
    private static void fillBackground(final BufferedImage image, final Graphics2D g2, final Color color) {
        // Fill screen to black
        final Rectangle rect = new Rectangle(0, 0, image.getWidth(), image.getHeight());

        // Draw black
        g2.setColor(color);

        g2.fill(rect);
    }

    /* (non-Javadoc)
     * @see org.jill.jn.draw.tilemanager.AbstractTileManager#init(java.util.Map, org.jill.jn.draw.ScreenType)
     */
    @Override
    public void init(Map<Integer, ShaTile[]> mapOfTile, EnumScreenType typeScreen) {
        // Small text is tileset 2
        smallText = mapOfTile.get(2);

        // Big text is tileset 1
        bigText = mapOfTile.get(1);

        // Grap color map
        if (typeScreen == EnumScreenType.VGA) {
            colorMap = VGA_COLOR_MAP.getColorMap();
        } else if (typeScreen == EnumScreenType.EGA) {
            colorMap = EGA_COLOR_MAP.getColorMap();
        } else if (typeScreen == EnumScreenType.CGA) {
            colorMap = CGA_COLOR_MAP.getColorMap();
        }

        this.typeScreen = typeScreen;
    }

    /**
     * Init color tab for text.
     *
     * @param object jill object
     * @return tab color
     */
    private Color[] initColorTextMap(final ObjectItem object) {
        final Color[] textColor = new Color[4];

        // First color is always transparant
        textColor[0] = new Color(0x0, true);

        int colorIndex = object.getySpeed();

        // Draw background
        if (colorIndex == TRANSPARENT_COLOR_VALUE) {
            // Transparent
            textColor[BACKGROUND_COLOR_INDEX] = new Color(0x0, true);
        } else {
            if (typeScreen == EnumScreenType.CGA) {
                // No transparancy
                textColor[BACKGROUND_COLOR_INDEX] =
                        new Color(colorMap[0].getRGB());
            } else {
                if (colorIndex > 7) {
                    colorIndex = 7;
                }

                textColor[BACKGROUND_COLOR_INDEX] =
                        new Color(colorMap[colorIndex].getRGB());
            }
        }

        if (typeScreen == EnumScreenType.CGA) {
            textColor[FOREGROUND_COLOR_INDEX1] =
                    new Color(colorMap[3].getRGB());
            textColor[FOREGROUND_COLOR_INDEX2] =
                    new Color(colorMap[1].getRGB());
        } else {
            colorIndex = object.getxSpeed() + 8;

            if (colorIndex > 15) {
                colorIndex = 15;
            }

            textColor[FOREGROUND_COLOR_INDEX1] =
                    new Color(colorMap[colorIndex].getRGB());
            textColor[FOREGROUND_COLOR_INDEX2] =
                    new Color(colorMap[colorIndex].getRGB());
        }

        return textColor;
    }

    /**
     * Init table letter.
     *
     * @param tiles  all tiles
     * @param object jill object
     * @return table letter
     */
    private Map<Character, BufferedImage> initLetterMap(final ShaTile[] tiles,
            final ObjectItem object, final Color[] textColor) {
        // Create map with letter and font
        final Map<Character, BufferedImage> mapLetter = new HashMap<>();

        // Draw letter
        final String text = object.getStringStackEntry().get().getValue();
        final int textLenght = text.length();
        char letter;

        for (int indexText = 0; indexText < textLenght; indexText++) {
            letter = text.charAt(indexText);

            if (!mapLetter.containsKey(letter)) {
                mapLetter.put(letter,
                        tiles[(int) letter].getFont(textColor));
            }
        }

        return mapLetter;
    }

    /**
     * Draw all letter
     *
     * @param g2     graphic object to draw
     * @param tiles  tiles of letter (big or small)
     * @param object jill object
     */
    private void drawLetter(final Graphics2D g2, final ShaTile[] tiles, final ObjectItem object, final Color[] textColor) {
        final Map<Character, BufferedImage> mapLetter = initLetterMap(tiles, object, textColor);

        // Draw letter
        final String text = object.getStringStackEntry().get().getValue();
        final int textLenght = text.length();

        char letter;

        BufferedImage letterToDraw;

        int newX = 0;

        for (int indexText = 0; indexText < textLenght; indexText++) {
            letter = text.charAt(indexText);

            letterToDraw = mapLetter.get(letter);

            g2.drawImage(letterToDraw, newX, 0, null);

            newX += letterToDraw.getWidth();
        }
    }

    /* (non-Javadoc)
     * @see org.jill.jn.draw.tilemanager.AbstractTileManager#getTile(org.jill.dma.ObjectItem)
     */
    @Override
    public BufferedImage getTile(final ObjectItem object) {
        // Create picture
        int width = object.getWidth();
        int height = object.getHeight();

        // Some text are empty, width or height = 0. But BufferedImage want > 0
        if (width <= 0) {
            width = 1;
        }

        if (height <= 0) {
            height = 1;
        }

        // Buffer image
        final BufferedImage image =
                new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_ARGB);
        // Graphic
        final Graphics2D g2 = image.createGraphics();

        // Tile
        ShaTile[] tiles;

        if (object.getType() == 20) {
            // Small text
            tiles = smallText;
        } else if (object.getType() == 21) {
            // Big text
            tiles = bigText;
        } else {
            System.err.println("Invalid object for TextTileManager. Must be type = 20 or 21");
            return null;
        }

        // Init tab color
        final Color[] textColor = initColorTextMap(object);

        // Draw background
        if (object.getySpeed() != TRANSPARENT_COLOR_VALUE) {
            fillBackground(image, g2, textColor[BACKGROUND_COLOR_INDEX]);
        }

        drawLetter(g2, tiles, object, textColor);

        g2.dispose();

        return image;
    }

}