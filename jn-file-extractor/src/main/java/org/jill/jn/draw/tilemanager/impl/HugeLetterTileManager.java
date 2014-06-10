/**
 * 
 */
package org.jill.jn.draw.tilemanager.impl;

import java.awt.image.BufferedImage;

import org.jill.jn.ObjectItem;

/**
 * Class to draw huge letters
 * 
 * @author Emeric MARTINEAU
 *
 */
public class HugeLetterTileManager extends StandardTileManager
{
    /* (non-Javadoc)
     * @see org.jill.jn.draw.tilemanager.AbstractTileManager#getTile(org.jill.dma.ObjectItem)
     */
    @Override
    public BufferedImage getTile(final ObjectItem object)
    {
        String objectType = String.valueOf(object.getType()).concat("_").concat(String.valueOf(object.getxSpeed())) ;
        BufferedImage bi ;
        
        bi = getMapObjectPicture().get(objectType) ;
        
        if (bi == null) 
        {
            System.err.println("No tile found for tileType = ".concat(objectType)) ;
        }
        
        return bi ;
    }

}
