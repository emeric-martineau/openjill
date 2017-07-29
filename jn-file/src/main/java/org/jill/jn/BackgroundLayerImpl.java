package org.jill.jn;

import java.io.IOException;

import org.jill.file.FileAbstractByte;

/**
 * Background of map.
 *
 * @author Emeric MARTINEAU
 * @version 1.0
 */
public class BackgroundLayerImpl implements BackgroundLayer {
    /**
     * Only lower three nybbles are valid.
     */
    private static final int MASK_OF_MAPCODE = 0x0FFF;

    /**
     * ID used in map file.
     */
    private final int[][] mapCode = new int[MAP_WIDTH][MAP_HEIGHT];

    /**
     * Constructor.
     *
     * @param jnFile file
     * @throws IOException if error
     */
    public BackgroundLayerImpl(final FileAbstractByte jnFile)
            throws IOException {
        // Background store by row
        for (int indexX = 0; indexX < MAP_WIDTH; indexX++) {
            for (int indexY = 0; indexY < MAP_HEIGHT; indexY++) {
                mapCode[indexX][indexY] = (jnFile.read16bitLE()
                        & MASK_OF_MAPCODE);
            }
        }
    }

    /**
     * Return map code value to seach it in dma file.
     *
     * @param x x-origin
     * @param y y-origine
     * @return map code
     */
    @Override
    public final int getMapCode(final int x, final int y) {
        return mapCode[x][y];
    }
}
