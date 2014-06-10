/**
 * 
 */
package org.jill.jn.draw.tilemanager.impl;

import java.awt.image.BufferedImage;

import org.jill.jn.ObjectItem;

/**
 * Class to draw eyes
 * 
 * @author Emeric MARTINEAU
 *
 */
public class PressButtonTileManager extends StandardTileManager
{
    /* (non-Javadoc)
     * @see org.jill.jn.draw.tilemanager.AbstractTileManager#getTile(org.jill.dma.ObjectItem)
     */
    @Override
    public BufferedImage getTile(final ObjectItem object)
    {
        String objectType ;        
        BufferedImage image ;
        
        if (object.getxSpeed() == 1)
        {
            objectType = String.valueOf(object.getType()).concat("_R") ;
        }
        else
        {
            objectType = String.valueOf(object.getType()).concat("_L") ;
        }
        
        image = getMapObjectPicture().get(objectType) ;
        
        if (image == null) 
        {
            System.err.println("No tile found for tileType = ".concat(objectType)) ;
        }                        
        return image ;
    }

}
