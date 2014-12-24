package org.jill.openjill.core.api.jill;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jill.jn.BackgroundLayer;

/**
 * All const.
 *
 * @author Emeric MARTINEAU
 */
public class JillConst {
    /**
     * Size (width/height) for a block.
     */
    private static int blockSize = 16;

    /**
     * Maximum value of screen.
     */
    private static final int MAX_WIDTH = BackgroundLayer.MAP_WIDTH * blockSize;

    /**
     * Maximum value of screen.
     */
    private static final int MAX_HEIGHT =
            BackgroundLayer.MAP_HEIGHT * blockSize;

    /**
     * Border to update object.
     */
    private static int xUpdateScreenBorder;

    /**
     * Border to update object.
     */
    private static int yUpdateScreenBorder;

    /**
     * Zapholder vlue of object when touch player.
     */
    private static int zapholdValueAfterTouchPlayer;

    static {
        loadFromClasspath("jill_const.properties");
    }

    public static void loadFromClasspath(String fileName) {
        final Properties prop = new Properties();
        final InputStream is = JillConst.class.getClassLoader().
                    getResourceAsStream("jill_const.properties");

        try {
            prop.load(is);

            blockSize = Integer.valueOf(prop.getProperty("blockSize"));
            xUpdateScreenBorder = Integer.valueOf(
                    prop.getProperty("xUpdateScreenBorder"));
            yUpdateScreenBorder = Integer.valueOf(
                    prop.getProperty("yUpdateScreenBorder"));
            zapholdValueAfterTouchPlayer = Integer.valueOf(
                    prop.getProperty("zapholdValueAfterTouchPlayer"));
        } catch (IOException ex) {
            Logger.getLogger(JillConst.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    /**
     * Return size in pixel of block in game.
     *
     * @return
     */
    public static int getBlockSize() {
        return blockSize;
    }

    /**
     * Return maximum size of map in pixel.
     *
     * @return
     */
    public static int getMaxWidth() {
        return MAX_WIDTH;
    }

    /**
     * Return maximum size of map in pixel.
     *
     * @return
     */
    public static int getMaxHeight() {
        return MAX_HEIGHT;
    }

    /**
     * Return size in pixel to update object.
     *
     * @return
     */
    public static int getxUpdateScreenBorder() {
        return xUpdateScreenBorder;
    }

    /**
     * Return size in pixel to update object.
     *
     * @return
     */
    public static int getyUpdateScreenBorder() {
        return yUpdateScreenBorder;
    }

    /**
     * Return value of zaphold field to put in object when it touch player.
     *
     * @return
     */
    public static int getZapholdValueAfterTouchPlayer() {
        return zapholdValueAfterTouchPlayer;
    }
}
