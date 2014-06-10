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
public interface ShaTileSet {
    /**
     * Flag is picture.
     */
    int SHM_BLFLAG = 4;
    /**
     * Flag is font.
     */
    int SHM_FONTF = 1;

    /**
     * Bit color.
     *
     * @return bit color
     */
    int getBitColor();

    /**
     * Size for CGA display in video memory after decompress.
     *
     * @return cga size of picture
     */
    int getCgaSize();

    /**
     * Size for EGA display in video memory after decompress.
     *
     * @return ega size of picture
     */
    int getEgaSize();

    /**
     * Flasgs.
     *
     * @return flags.
     */
    int getFlags();

    /**
     * Number tile.
     *
     * @return index of this tile in file
     */
    int getNumberTile();

    /**
     * Accesseur de offset.
     *
     * @return offset
     */
    int getOffset();

    /**
     * Accessor of shaTile.
     *
     * @return shaTile
     */
    ShaTile[] getShaTile();

    /**
     * Accesseur de size.
     *
     * @return size
     */
    int getSize();

    /**
     * The number of this tileset in entry.
     *
     * @return tileset index
     */
    int getTitleSetIndex();

    /**
     * Size for VGA display in video memory after decompress.
     *
     * @return vga size of picture
     */
    int getVgaSize();

    /**
     * Font.
     *
     * @return true if tile is font
     */
    boolean isFont();

    /**
     * Tileset.
     *
     * @return  true if tileset (picture)
     */
    boolean isTileset();

    /**
     * Mutateur de offset.
     *
     * @param offsetInFile offset
     */
    void setOffset(int offsetInFile);

    /**
     * Mutateur de size.
     *
     * @param sizeInFile size
     */
    void setSize(int sizeInFile);

}
