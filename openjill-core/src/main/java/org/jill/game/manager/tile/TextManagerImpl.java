package org.jill.game.manager.tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import org.jill.game.config.ObjectInstanceFactory;
import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ColorMap;
import org.jill.sha.ShaTile;

/**
 * Class to create text on screen.
 *
 * @author Emeric MARTINEAU
 */
public class TextManagerImpl implements TextManager {
    /**
     * Color map.
     */
    private final ColorMap vgaColorMap
            = ObjectInstanceFactory.getVgaColor();

    /**
     * Color map.
     */
    private final ColorMap egaColorMap
            = ObjectInstanceFactory.getEgaColor();

    /**
     * Color map.
     */
    private final ColorMap cgaColorMap
            = ObjectInstanceFactory.getCgaColor();

    /**
     * Background color index.
     */
    private static final int BACKGROUND_COLOR_INDEX = 3;

    /**
     * Foreground color index.
     */
    private static final int FOREGROUND_COLOR_INDEX1 = 1;

    /**
     * Foreground color index.
     */
    private static final int FOREGROUND_COLOR_INDEX2 = 2;


    /**
     * Small letter.
     */
    private static final int SMALL_LETTER_TILESET = 2;

    /**
     * Big letter.
     */
    private static final int BIG_LETTER_TILESET = 1;

    /**
     * Special letter.
     */
    private static final int SPECIAL_LETTER_TILESET = 6;

    /**
     * Small number.
     */
    private static final int SMALL_NUMBER_TILESET = 4;

    /**
     * Maximum color for draw letter.
     */
    private static final int MAX_LETTER_COLOR = 4;

    /**
     * To divide number for draw it.
     */
    private static final int DIVISOR_NUMBER_TO_DRAW = 10;


    /**
     * Tile to draw big text.
     */
    private ShaTile[] bigText;

    /**
     * Tile to draw small text.
     */
    private ShaTile[] smallText;

    /**
     * Tile to draw small text.
     */
    private ShaTile[] smallNumber;

    /**
     * Tile to draw special key.
     */
    private ShaTile[] specialKey;

    /**
     * Color map EGA or VGA.
     */
    private Color[] colorMap;

    /**
     * Screen type.
     */
    private EnumScreenType typeScreen;

    /**
     * Void constructor.
     */
    public TextManagerImpl() {
        // Nothing
    }

    /**
     * Constructor.
     *
     * @param mapOfTile map of tile
     * @param tpScreen type of screen
     */
    @Override
    public void init(final Map<Integer, ShaTile[]> mapOfTile,
            final EnumScreenType tpScreen) {
        this.typeScreen = tpScreen;

        // Small text is tileset 2
        smallText = mapOfTile.get(SMALL_LETTER_TILESET);

        // Big text is tileset 1
        bigText = mapOfTile.get(BIG_LETTER_TILESET);

        // Specaial key like shift...
        specialKey = mapOfTile.get(SPECIAL_LETTER_TILESET);

        // Small number for higscore
        smallNumber = mapOfTile.get(SMALL_NUMBER_TILESET);

        // Grap color map
        if (tpScreen == EnumScreenType.VGA) {
            colorMap = vgaColorMap.getColorMap();
        } else if (tpScreen == EnumScreenType.EGA) {
            colorMap = egaColorMap.getColorMap();
        } else if (tpScreen == EnumScreenType.CGA) {
            colorMap = cgaColorMap.getColorMap();
        }
    }

    /**
     * Init table letter.
     *
     * @param tiles tile (big or small)
     * @param text text to draw
     * @param textColor color
     *
     * @return table letter
     */
    private Map<Character, BufferedImage> initLetterMap(final  ShaTile[] tiles,
            final String text, final Color[] textColor) {
        // Create map with letter and font
        final Map<Character, BufferedImage> mapLetter = new HashMap<>();

        // Draw letter
        final int textLenght = text.length();
        char letter;

        for (int indexText = 0; indexText < textLenght; indexText++) {
            letter = text.charAt(indexText);

            if (!mapLetter.containsKey(letter)) {
                mapLetter.put(
                    Character.valueOf(letter),
                    tiles[(int) letter].getFont(textColor));
            }
        }

        return mapLetter;
    }

    /**
     * Init table letter.
     *
     * @param tiles tile (big or small)
     * @param textColor color
     *
     * @return table letter
     */
    private BufferedImage[] initNumberMap(final  ShaTile[] tiles,
            final Color[] textColor) {
        final BufferedImage[] arrayNumber = new BufferedImage[tiles.length];

        for (int index = 0; index < tiles.length; index++) {
            arrayNumber[index] = tiles[index].getFont(textColor);
        }

        return arrayNumber;
    }

    /**
     * Init color tab for text.
     *
     * @param foreColor text color
     * @param backColor background color (BACKGROUND_COLOR_NONE = none)
     *
     * @return tab color
     */
    private Color[] initColorTextMap(final int foreColor, final int backColor) {
        final Color[] textColor = new Color[MAX_LETTER_COLOR];

        // First color is always transparant
        textColor[0] = new Color(0x0, true);

        int colorIndex = backColor;

        // --[ BACKGROUND ]----------------------------------------------------
        // Draw background
        if (colorIndex == BACKGROUND_COLOR_NONE) {
            // Transparent
            textColor[BACKGROUND_COLOR_INDEX] = new Color(0x0, true);
        } else {
            if (typeScreen == EnumScreenType.CGA) {
                // No transparancy
                textColor[BACKGROUND_COLOR_INDEX] =
                        new Color(colorMap[0].getRGB());
            } else {
                if (colorIndex > COLOR_WHITE) {
                    colorIndex = COLOR_WHITE;
                }

                textColor[BACKGROUND_COLOR_INDEX] =
                        new Color(colorMap[colorIndex].getRGB());
            }
        }

        // --[ FOREGROUND ]----------------------------------------------------
        if (typeScreen == EnumScreenType.CGA) {
            textColor[FOREGROUND_COLOR_INDEX1] =
                    new Color(colorMap[BACKGROUND_COLOR_INDEX].getRGB());
            textColor[FOREGROUND_COLOR_INDEX2] =
                    new Color(colorMap[FOREGROUND_COLOR_INDEX1].getRGB());
        } else {
            colorIndex = foreColor + BACK_COLOR_SHIFT;

            if (colorIndex > BACKGROUND_COLOR_WHITE) {
                colorIndex = BACKGROUND_COLOR_WHITE;
            }

            textColor[FOREGROUND_COLOR_INDEX1] =
                    new Color(colorMap[colorIndex].getRGB());
            textColor[FOREGROUND_COLOR_INDEX2] =
                    new Color(colorMap[colorIndex].getRGB());
        }

        return textColor;
    }

    /**
     * Draw text.
     *
     * @param g2 picture where draw
     * @param x x
     * @param y y
     * @param msg text to draw
     * @param foreColor text color
     * @param backColor background color (BACKGROUND_COLOR_NONE = none)
     * @param textTile text tile
     */
    private void drawText(final Graphics2D g2, final int x, final int y,
            final String msg, final int foreColor, final int backColor,
            final ShaTile[] textTile) {
        // Init tab color
        final Color[] textColor = initColorTextMap(foreColor, backColor);
        final Map<Character, BufferedImage> mapLetter =
                initLetterMap(textTile, msg, textColor);

        // Draw letter
        final String text = msg;
        final int textLenght = text.length();

        char letter;

        BufferedImage letterToDraw;

        int newX = x;

        for (int indexText = 0; indexText < textLenght; indexText++) {
            letter = text.charAt(indexText);

            letterToDraw = mapLetter.get(letter);

            g2.drawImage(letterToDraw, newX, y, null);

            newX += letterToDraw.getWidth();
        }
    }

    /**
     * Draw text.
     *
     * @param msg text to draw
     * @param foreColor text color
     * @param backColor background color (BACKGROUND_COLOR_NONE = none)
     * @param textTile text tile
     *
     * @return picture
     */
    private BufferedImage createText(final String msg, final int foreColor,
            final int backColor, final ShaTile[] textTile) {
        // Init tab color
        final Color[] textColor = initColorTextMap(foreColor, backColor);
        final Map<Character, BufferedImage> mapLetter =
                initLetterMap(textTile, msg, textColor);

        // Draw letter
        final String text = msg;
        final int textLenght = text.length();

        char letter;
        BufferedImage letterToDraw;

        int width = 0;
        int height = 0;

        // First, calculate size
        for (int indexText = 0; indexText < textLenght; indexText++) {
            letter = text.charAt(indexText);

            letterToDraw = mapLetter.get(letter);

            width += letterToDraw.getWidth();
            height = letterToDraw.getHeight();
        }

        // Create picture
        final BufferedImage returnImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2 = returnImage.createGraphics();

        int newX = 0;

        // Draw message
        for (int indexText = 0; indexText < textLenght; indexText++) {
            letter = text.charAt(indexText);

            letterToDraw = mapLetter.get(letter);

            g2.drawImage(letterToDraw, newX, 0, null);

            newX += letterToDraw.getWidth();
        }

        return returnImage;
    }

    /**
     * Draw number.
     *
     * @param number number to draw
     * @param foreColor text color
     * @param backColor background color (BACKGROUND_COLOR_NONE = none)
     * @param textTile text tile
     *
     * @return picture
     */
    private BufferedImage createNumber(final int number, final int foreColor,
            final int backColor, final ShaTile[] textTile) {
        // Init tab color
        final Color[] textColor = initColorTextMap(foreColor, backColor);
        final BufferedImage[] arrayNumber = initNumberMap(textTile, textColor);

        BufferedImage letterToDraw;

        int width = 0;
        int height = 0;

        int newNumber = number;
        int currentNumber;

        // Calculate size
        while (newNumber > 0) {
            currentNumber = newNumber % DIVISOR_NUMBER_TO_DRAW;

            letterToDraw = arrayNumber[currentNumber];

            width += letterToDraw.getWidth();
            height = letterToDraw.getHeight();

            newNumber /= DIVISOR_NUMBER_TO_DRAW;
        }

        if (number == 0) {
            // Special case when number is 0
            letterToDraw = arrayNumber[0];
            width += letterToDraw.getWidth();
            height = letterToDraw.getHeight();
        }

        // Create picture
        final BufferedImage returnImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2 = returnImage.createGraphics();

        int newX = width;
        newNumber = number;

        // Draw message
        while (newNumber > 0) {
            currentNumber = newNumber % DIVISOR_NUMBER_TO_DRAW;

            letterToDraw = arrayNumber[currentNumber];

            newX -= letterToDraw.getWidth();

            g2.drawImage(letterToDraw, newX, 0, null);

            newNumber /= DIVISOR_NUMBER_TO_DRAW;
        }

        if (number == 0) {
            // Special case when number is 0
            letterToDraw = arrayNumber[0];
            newX -= letterToDraw.getWidth();
            g2.drawImage(letterToDraw, newX, 0, null);
        }

        return returnImage;
    }

    /**
     * Draw big text.
     *
     * @param g2 picture where draw
     * @param x x
     * @param y y
     * @param msg text to draw
     * @param foreColor text color
     * @param backColor background color (BACKGROUND_COLOR_NONE = none)
     */
    @Override
    public final void drawBigText(final Graphics2D g2, final int x, final int y,
            final String msg, final int foreColor, final int backColor) {
        drawText(g2, x, y, msg, foreColor, backColor, bigText);
    }

    /**
     * Draw small text.
     *
     * @param g2 picture where draw
     * @param x x
     * @param y y
     * @param msg text to draw
     * @param foreColor text color
     * @param backColor background color (BACKGROUND_COLOR_NONE = none)
     */
    @Override
    public final void drawSmallText(final Graphics2D g2, final int x,
            final int y, final String msg, final int foreColor,
            final int backColor) {
        drawText(g2, x, y, msg, foreColor, backColor, smallText);
    }

    /**
     * Draw big text.
     *
     * @param msg text to draw
     * @param foreColor text color
     * @param backColor background color (BACKGROUND_COLOR_NONE = none)
     *
     * @return picture
     */
    @Override
    public final BufferedImage createBigText(final String msg,
            final int foreColor, final int backColor) {
        return createText(msg, foreColor, backColor, bigText);
    }

    /**
     * Draw small text.
     *
     * @param msg text to draw
     * @param foreColor text color
     * @param backColor background color (BACKGROUND_COLOR_NONE = none)
     *
     * @return picture
     */
    @Override
    public final BufferedImage createSmallText(final String msg,
            final int foreColor, final int backColor) {
        return createText(msg, foreColor, backColor, smallText);
    }

    /**
     * Draw small number.
     *
     * @param number number to draw
     * @param foreColor text color
     * @param backColor background color (BACKGROUND_COLOR_NONE = none)
     *
     * @return picture
     */
    @Override
    public final BufferedImage createSmallNumber(final int number,
            final int foreColor, final int backColor) {
        return createNumber(number, foreColor, backColor, smallNumber);
    }

    /**
     * Grap small letter.
     *
     * @param msg message to display
     * @param foreColor text color
     * @param backColor background color
     *
     * @return picture array
     */
    @Override
    public final BufferedImage[] grapSmallLetter(final String msg, final int
            foreColor, final int backColor) {
        // Init tab color
        final Color[] textColor = initColorTextMap(foreColor, backColor);
        final Map<Character, BufferedImage> mapLetter =
                initLetterMap(smallText, msg, textColor);
        // Return value
        final BufferedImage[] result = new BufferedImage[msg.length()];

        // Draw letter
        final int textLenght = msg.length();
        char letter;

        for (int indexText = 0; indexText < textLenght; indexText++) {
            letter = msg.charAt(indexText);

            result[indexText] = mapLetter.get(letter);
        }

        return result;
    }

    /**
     * grap special key.
     *
     * @param key key to draw
     * @param foreColor text color
     * @param backColor background color (BACKGROUND_COLOR_NONE = none)
     *
     * @return picture
     */
    @Override
    public final BufferedImage grapSpecialKey(final int key,
            final int foreColor, final int backColor) {
        // Init tab color
        final Color[] textColor = initColorTextMap(foreColor, backColor);

        return specialKey[key].getFont(textColor);
    }
}
