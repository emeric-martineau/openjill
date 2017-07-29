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
package org.jill.jn.draw.tilemanager;

import java.awt.image.BufferedImage;
import java.util.Map;

import org.jill.jn.ObjectItem;

/**
 * Classe to share code.
 * Created by emeric_martineau on 20/07/2017.
 */
public final class SharedCode {
    private SharedCode() {
        // Nothing :-)
    }

    /**
     * Load a picture.
     *
     * @param object current object
     * @param mapObjectPicture mapping objet and picture
     *
     * @return picture
     */
    public static BufferedImage getTile(final ObjectItem object, final Map<String, BufferedImage> mapObjectPicture)
    {
        String objectType = String.valueOf(object.getType()).concat("_").concat(String.valueOf(object.getCounter())) ;
        BufferedImage bi ;

        bi = mapObjectPicture.get(objectType) ;

        if (bi == null)
        {
            System.err.println("No tile found for tileType = ".concat(objectType)) ;
        }

        return bi ;
    }
}
