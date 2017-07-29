/*

 */
package org.jill.jn.draw.tilemanager.impl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.jill.jn.ObjectItem;

/**
 * Class to draw gator
 * 
 * @author Emeric MARTINEAU
 *
 */
public class GatorTileManager extends StandardTileManager
{
    /* (non-Javadoc)
     * @see org.jill.jn.draw.tilemanager.AbstractTileManager#getTile(org.jill.dma.ObjectItem)
     */
    @Override
    public BufferedImage getTile(final ObjectItem object)
    {
        String objectType1 = String.valueOf(object.getType()).concat("_0") ;
        String objectType2 = String.valueOf(object.getType()).concat("_1") ;
        BufferedImage bi1 ;
        BufferedImage bi2 ;
        
        bi1 = getMapObjectPicture().get(objectType1) ;
        bi2 = getMapObjectPicture().get(objectType2) ;
        
        if (bi1 == null) 
        {
            System.err.println("No tile found for tileType = ".concat(objectType1)) ;
            return null ;
        }

        if (bi2 == null) 
        {
            System.err.println("No tile found for tileType = ".concat(objectType2)) ;
            return null ;
        }
        
        // Buffer image
        final BufferedImage image =
            new BufferedImage(object.getWidth(), object.getHeight(),
                    BufferedImage.TYPE_INT_ARGB) ;
        // Graphic
        final Graphics2D g2 = image.createGraphics() ;        
        
        g2.drawImage(bi1, 0, 0, null) ;
        g2.drawImage(bi2, bi1.getWidth(), 0, null) ;
        
        g2.dispose() ;
        
        return image ;
    }

}
