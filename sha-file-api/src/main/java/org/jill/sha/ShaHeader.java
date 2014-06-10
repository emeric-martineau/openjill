/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jill.sha;

/**
 *
 * @author emeric_martineau
 */
public interface ShaHeader {
    /**
     * Maximum of entry number in file.
     */
    int ENTRY_NUMBER = 128;

    /**
     * Accesseur de offsetTileSet.
     *
     * @return offset of tileset
     */
    int[] getOffsetTileSet();

    /**
     * Accesseur de sizeTileSet.
     *
     * @return size of tile et
     */
    int[] getSizeTileSet();

    /**
     * Check if entry is valid.
     *
     * @param index index to check if valie
     *
     * @return true if valid
     */
    boolean isValideEntry(int index);

}
