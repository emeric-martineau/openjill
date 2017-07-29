package org.jill.game.screen.conf;

/**
 * Class represente picture by tile/tilset.
 *
 * @author Emeric MARTINEAU
 */
public class PictureConf {
    /**
     * Tile of picture.
     */
    private int tile;

    /**
     * Tileset of picture.
     */
    private int tileset;

    /**
     * Tile of picture.
     *
     * @return the tile
     */
    public final int getTile() {
        return tile;
    }

    /**
     * Tile of picture.
     *
     * @param t the tile to set
     */
    public final void setTile(final int t) {
        this.tile = t;
    }

    /**
     * Tileset of picture.
     *
     * @return the tilset
     */
    public final int getTileset() {
        return tileset;
    }

    /**
     * Tileset of picture.
     *
     * @param t the tilset to set
     */
    public final void setTileset(final int t) {
        this.tileset = t;
    }
}
