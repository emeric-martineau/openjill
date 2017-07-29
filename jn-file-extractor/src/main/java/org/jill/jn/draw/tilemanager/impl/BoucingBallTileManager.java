/*

 */
package org.jill.jn.draw.tilemanager.impl;

import java.awt.image.BufferedImage;

import org.jill.jn.ObjectItem;
import org.jill.jn.draw.tilemanager.SharedCode;

/**
 * Class to draw bonus (hight jump, bag of coins...)
 *
 * @author Emeric MARTINEAU
 */
public class BoucingBallTileManager extends StandardTileManager {
    /* (non-Javadoc)
     * @see org.jill.jn.draw.tilemanager.AbstractTileManager#getTile(org.jill.dma.ObjectItem)
     */
    @Override
    public BufferedImage getTile(final ObjectItem object) {
        return SharedCode.getTile(object, getMapObjectPicture());
    }

}
