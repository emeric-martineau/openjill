package org.jill.jn;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
     * Map to store background update state.
     */
    private Map<String, Boolean> backgroundUpdate = new HashMap();

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

    @Override
    public final int getMapCode(final int x, final int y) {
        return mapCode[x][y];
    }

    @Override
    public boolean isUpdateBackground(String backgroundName) {
        return backgroundUpdate.containsKey(backgroundName);
    }

    @Override
    public void setUpdateBackground(String backgroundName, boolean update) {
        backgroundUpdate.put(backgroundName, Boolean.valueOf(update));
    }

    @Override
    public boolean isBackgroundUpdated(String backgroundName) {
        return backgroundUpdate.containsKey(backgroundName) && backgroundUpdate.get(backgroundName);
    }

    @Override
    public void clearBackgroundUpdate() {
        backgroundUpdate.clear();
    }
}
