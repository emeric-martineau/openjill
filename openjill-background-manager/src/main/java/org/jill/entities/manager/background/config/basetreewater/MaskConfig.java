package org.jill.entities.manager.background.config.basetreewater;

/**
 * Class provide configuration of mask (tileset, tile...).
 */
public class MaskConfig {
    /**
     * If background is on top of lake.
     */
    private boolean top;

    /**
     * If background is on bottom of lake.
     */
    private boolean bottom;

    /**
     * If background is on left of lake.
     */
    private boolean left;

    /**
     * If background is on right of lake.
     */
    private boolean right;

    /**
     * Tileset of picture.
     */
    private int tileset;

    /**
     * Tile of picture.
     */
    private int tile;

    /**
     * If background is on top of lake.
     *
     * @return true/false
     */
    public boolean isTop() {
        return top;
    }

    /**
     * If background is on bottom of lake.
     *
     * @return true/false
     */
    public boolean isBottom() {
        return bottom;
    }

    /**
     * If background is on left of lake.
     *
     * @return true/false
     */
    public boolean isLeft() {
        return left;
    }

    /**
     * If background is on right of lake.
     *
     * @return true/false
     */
    public boolean isRight() {
        return right;
    }

    /**
     * Tileset of picture.
     *
     * @return index of tileset
     */
    public int getTileset() {
        return tileset;
    }

    /**
     * Tile of picture.
     *
     * @return index of tile
     */
    public int getTile() {
        return tile;
    }
}
