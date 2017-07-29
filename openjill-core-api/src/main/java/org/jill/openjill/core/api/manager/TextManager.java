package org.jill.openjill.core.api.manager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaTile;

/**
 * Text manager.
 *
 * @author emeric_martineau
 */
public interface TextManager {

    /**
     * To get upper color.
     */
    int BACK_COLOR_SHIFT = 8;

    /**
     * Color blue.
     */
    int COLOR_BLUE = 3;

    /**
     * Color blue.
     */
    int COLOR_DARK_BLUE = 1;

    /**
     * Color green.
     */
    int COLOR_GREEN = 2;

    /**
     * Color magenta.
     */
    int COLOR_MAGENTA = 5;

    /**
     * Color red.
     */
    int COLOR_RED = 4;

    /**
     * Color white.
     */
    int COLOR_WHITE = 7;

    /**
     * Color yellow.
     */
    int COLOR_YELLOW = 6;

    /**
     * Bullet off.
     */
    int SPECIAL_BULLET_OFF = 10;

    /**
     * Bullet on.
     */
    int SPECIAL_BULLET_ON = 11;

    /**
     * Alt.
     */
    int SPECIAL_KEY_ALT = 1;

    /**
     * Ctrl.
     */
    int SPECIAL_KEY_CTRL = 2;

    /**
     * Escape.
     */
    int SPECIAL_KEY_ESC = 8;

    /**
     * F1.
     */
    int SPECIAL_KEY_F1 = 9;

    /**
     * Shift.
     */
    int SPECIAL_KEY_SHIFT = 0;

    /**
     * Space.
     */
    int SPECIAL_KEY_SPACE = 3;

    /**
     * Color blue.
     */
    int BACKGROUND_COLOR_BLUE = COLOR_BLUE + BACK_COLOR_SHIFT;

    /**
     * Color blue.
     */
    int BACKGROUND_COLOR_DARK_BLUE = COLOR_DARK_BLUE + BACK_COLOR_SHIFT;
    /**
     * Color green.
     */
    int BACKGROUND_COLOR_GREEN = COLOR_GREEN + BACK_COLOR_SHIFT;
    /**
     * Color magenta.
     */
    int BACKGROUND_COLOR_MAGENTA = COLOR_MAGENTA + BACK_COLOR_SHIFT;
    /**
     * Don't draw background.
     */
    int BACKGROUND_COLOR_NONE = -1;
    /**
     * Color red.
     */
    int BACKGROUND_COLOR_RED = COLOR_RED + BACK_COLOR_SHIFT;
    /**
     * Color white.
     */
    int BACKGROUND_COLOR_WHITE = COLOR_WHITE + BACK_COLOR_SHIFT;
    /**
     * Color yellow.
     */
    int BACKGROUND_COLOR_YELLOW = COLOR_YELLOW + BACK_COLOR_SHIFT;

    /**
     * Constructor.
     *
     * @param mapOfTile map of tile
     * @param tpScreen type of screen
     */
    void init( Map<Integer, ShaTile[]> mapOfTile,
            EnumScreenType tpScreen);

    /**
     * Draw big text.
     *
     * @param msg text to draw
     * @param foreColor text color
     * @param backColor background color (BACKGROUND_COLOR_NONE = none)
     *
     * @return picture
     */
    BufferedImage createBigText(String msg, int foreColor, int backColor);

    /**
     * Draw small number.
     *
     * @param number number to draw
     * @param foreColor text color
     * @param backColor background color (BACKGROUND_COLOR_NONE = none)
     *
     * @return picture
     */
    BufferedImage createSmallNumber(int number, int foreColor, int backColor);

    /**
     * Draw small text.
     *
     * @param msg text to draw
     * @param foreColor text color
     * @param backColor background color (BACKGROUND_COLOR_NONE = none)
     *
     * @return picture
     */
    BufferedImage createSmallText(String msg, int foreColor, int backColor);

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
    void drawBigText(Graphics2D g2, int x, int y, String msg, int foreColor,
            int backColor);

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
    void drawSmallText(Graphics2D g2, int x, int y, String msg, int foreColor,
            int backColor);

    /**
     * Grap small letter.
     *
     * @param msg message to display
     * @param foreColor text color
     * @param backColor background color
     *
     * @return picture array
     */
    BufferedImage[] grapSmallLetter(String msg, int foreColor, int backColor);

    /**
     * grap special key.
     *
     * @param key key to draw
     * @param foreColor text color
     * @param backColor background color (BACKGROUND_COLOR_NONE = none)
     *
     * @return picture
     */
    BufferedImage grapSpecialKey(int key, int foreColor, int backColor);

}
