package org.jill.dma;

/**
 * Entry of dma file.
 * 
 * @author Emeic MARTINEAU
 */
public interface DmaEntry {

    /**
     * Flags.
     *
     * @return flags flags value
     */
    int getFlags();

    /**
     * Index.
     *
     * @return index index of this entry in file
     */
    int getIndex();

    /**
     * Return map code.
     *
     * @return mapCode code of background
     */
    int getMapCode();

    /**
     * Name.
     *
     * @return name name of background in file
     */
    String getName();

    /**
     * Offset in file where entry can be found.
     *
     * @return offset  offset of this entry in file
     */
    int getOffset();

    /**
     * Tile.
     *
     * @return tile index of tile
     */
    int getTile();

    /**
     * Tileset.
     *
     * @return tileset index of tileset
     */
    int getTileset();

}
