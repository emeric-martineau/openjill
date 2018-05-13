/*
 *
 *  * This file is part of the Hesperides distribution.
 *  * (https://github.com/voyages-sncf-technologies/hesperides)
 *  * Copyright (c) 2016 VSCT.
 *  *
 *  * Hesperides is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, version 3.
 *  *
 *  * Hesperides is distributed in the hope that it will be useful, but
 *  * WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  * General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */
package org.jill.game.entities.obj.util;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.jill.game.entities.obj.bees.MoveSizeAndInterval;
import org.jill.openjill.core.api.manager.TileManager;

/**
 * Class to share code.
 *
 * Created by emeric_martineau on 20/07/2017.
 */
public final class SharedCode {
    private SharedCode() {
        // Nothing :-)
    }

    /**
     * Load picture.
     *
     * @param pictureCache  picture cache object
     * @param tileIndex     index of tile
     * @param tileSetIndex  index in tile set
     * @param numberTileSet number images in tile set
     */
    public static BufferedImage[] loadPicture(final TileManager pictureCache, final int tileIndex, final int tileSetIndex, final int numberTileSet) {
        // Load picture for each object. Don't use cache cause some picture
        // change between jill episod.
        final BufferedImage[] images
                = new BufferedImage[numberTileSet * 2];

        int indexArray = 0;

        for (int index = 0; index < numberTileSet; index++) {
            images[indexArray]
                    = pictureCache.getImage(tileSetIndex, tileIndex
                    + index).get();
            images[indexArray + 1] = images[indexArray];

            indexArray += 2;
        }

        return images;
    }

    /**
     * Populate list of movement.
     *
     * @param mvtX name of key in config file
     * @return list of movement
     */
    public static List<MoveSizeAndInterval> populateMove(final String mvtX) {
        // Split #
        String[] arrayMoveX = mvtX.split("#");

        final List<MoveSizeAndInterval> listMvt =
                new ArrayList<>(arrayMoveX.length);

        for (String currentMove : arrayMoveX) {
            listMvt.add(new MoveSizeAndInterval(currentMove));
        }

        return listMvt;
    }
}
