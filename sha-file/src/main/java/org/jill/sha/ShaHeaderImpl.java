package org.jill.sha;

import java.io.IOException;
import org.jill.file.FileAbstractByte;

/**
 * Class to read SHA file who contain picture of game.
 *
 * @author emeric martineau
 * @version 1.0
 */
public class ShaHeaderImpl implements ShaHeader {
    /**
     * Array of offset in file of Tileset.
     */
    private final int[] offsetTileSet = new int[ENTRY_NUMBER];

    /**
     * Array of size in file of Tileset.
     */
    private final int[] sizeTileSet = new int[ENTRY_NUMBER];

    /**
     * Constructor of class ShaHeader.
     *
     * @param shaFile file data
     *
     * @throws IOException if error
     */
    public ShaHeaderImpl(final FileAbstractByte shaFile) throws IOException {
        int index;

        // Read offset
        for (index = 0; index < ENTRY_NUMBER; index++) {
            offsetTileSet[index] = shaFile.read32bitLE();
        }

        // Read lenght
        for (index = 0; index < ENTRY_NUMBER; index++) {
            sizeTileSet[index] = shaFile.read16bitLE();
        }
    }

    /**
     * Accesseur de offsetTileSet.
     *
     * @return offset of tileset
     */
    @Override
    public final int[] getOffsetTileSet() {
        int[] desArray = new int[offsetTileSet.length];
        System.arraycopy(offsetTileSet, 0, desArray, 0, offsetTileSet.length);
        return desArray;
    }

    /**
     * Accesseur de sizeTileSet.
     *
     * @return size of tile et
     */
    @Override
    public final int[] getSizeTileSet() {
        int[] desArray = new int[sizeTileSet.length];
        System.arraycopy(sizeTileSet, 0, desArray, 0, sizeTileSet.length);
        return desArray;
    }

    /**
     * Check if entry is valid.
     *
     * @param index index to check if valie
     *
     * @return true if valid
     */
    @Override
    public final boolean isValideEntry(final int index) {
        return (offsetTileSet[index] != 0) && (sizeTileSet[index] != 0);
    }
}
