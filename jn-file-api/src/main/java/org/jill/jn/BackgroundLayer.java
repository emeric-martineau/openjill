package org.jill.jn;

/**
 * Background of map.
 *
 * @author Emeric MARTINEAU
 */
public interface BackgroundLayer {
    /**
     * Size in file (fixed).
     */
    int SIZE_IN_FILE = 16384;

    /**
     * Height of map.
     */
    int MAP_HEIGHT = 64;

    /**
     * Height of map.
     */
    int MAP_WIDTH = 128;

    /**
     * Return map code value to search it in dma file.
     *
     * @param x x-origin
     * @param y y-origine
     * @return map code
     */
    int getMapCode(int x, int y);

    /**
     * Return true if background update already call.
     *
     * @param backgroundName name of background
     *
     * @return true if background msgUpdate already call
     */
    boolean isUpdateBackground(String backgroundName);

    /**
     * Set if background need update.
     *
     * @param backgroundName name of background
     * @param update true or false
     */
    void setUpdateBackground(String backgroundName, boolean update);

    /**
     * Return true if background need update.
     *
     * @param backgroundName name of background
     *
     * @return true or false
     */
    boolean isBackgroundUpdated(String backgroundName);

    /**
     * Clear state of all background.
     */
    void clearBackgroundUpdate();
}
