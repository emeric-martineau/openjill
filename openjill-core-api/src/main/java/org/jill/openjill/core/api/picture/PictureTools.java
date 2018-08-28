package org.jill.openjill.core.api.picture;

import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaFile;
import org.jill.sha.ShaTile;
import org.jill.sha.ShaTileSet;

import java.awt.image.BufferedImage;
import java.util.Optional;

/**
 * Tool to draw picture.
 */
public final class PictureTools {
    private PictureTools() {
        // Nothing
    }

    /**
     * Get picture for current screen configuration.
     *
     * @param shaFile the object contain picture
     * @param tileSet index of tile set
     * @param tile tile index
     * @param screen screen configuration
     *
     * @return the desired picture.
     */
    public static Optional<BufferedImage> getPicture(final ShaFile shaFile, final int tileSet, final int tile,
                                                 final EnumScreenType screen) {
        final ShaTileSet[] shaTileset = shaFile.getShaTileSet();

        ShaTile[] currentShaTile = null;

        for (ShaTileSet tileset: shaTileset) {
            if (tileset.getTitleSetIndex() == tileSet) {
                currentShaTile = tileset.getShaTile();

                break;
            }
        }

        if (tile < currentShaTile.length) {
            // For background T16, T17, T18, T19, T20, tile are invalid !!!
            ShaTile currentTile = currentShaTile[tile];

            switch (screen) {
                case CGA:
                    return Optional.of(currentTile.getPictureCga());
                case EGA:
                    return  Optional.of(currentTile.getPictureEga());
                default:
                    return Optional.of(currentTile.getPictureVga());
            }
        }

        return Optional.empty();
    }
}
