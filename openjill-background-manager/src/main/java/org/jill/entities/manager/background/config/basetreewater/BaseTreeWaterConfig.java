package org.jill.entities.manager.background.config.basetreewater;

/**
 * Class provide configuration of basetree and basewater background.
 */
public class BaseTreeWaterConfig {
    /**
     * Number of tile contain background.
     */
    private int numberTileSet;

    /**
     * Gamecount before change background.
     */
    private int gamecount;

    /**
     * Mask configuration.
     */
    private MaskConfig[] mask;

    /**
     * Number of tile contain background.
     *
     * @return number
     */
    public int getNumberTileSet() {
        return numberTileSet;
    }

    /**
     * Gamecount before change background.
     *
     * @return number of cycle
     */
    public int getGamecount() {
        return gamecount;
    }

    /**
     * Mask configuration.
     *
     * @return array of all configuration possible
     */
    public MaskConfig[] getMask() {
        return mask;
    }
}
