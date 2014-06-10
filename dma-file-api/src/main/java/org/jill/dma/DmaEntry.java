/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jill.dma;

/**
 *
 * @author emeric_martineau
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
