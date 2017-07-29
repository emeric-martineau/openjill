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
public class SnakeTileManager extends StandardTileManager
{
    /* (non-Javadoc)
     * @see org.jill.jn.draw.tilemanager.AbstractTileManager#getTile(org.jill.dma.ObjectItem)
     */
    @Override
    public BufferedImage getTile(final ObjectItem object)
    {
        String objectType1 = String.valueOf(object.getType()).concat("_0") ;
        String objectType2 = String.valueOf(object.getType()).concat("_1") ;
        String objectType3 = String.valueOf(object.getType()).concat("_2") ;
        BufferedImage bi1 ;
        BufferedImage bi2 ;
        BufferedImage bi3 ;
        
        bi1 = getMapObjectPicture().get(objectType1) ;
        bi2 = getMapObjectPicture().get(objectType2) ;
        bi3 = getMapObjectPicture().get(objectType3) ;
        
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
        
        if (bi3 == null) 
        {
            System.err.println("No tile found for tileType = ".concat(objectType3)) ;
            return null ;
        }
        
        // Buffer image
        final BufferedImage image =
            new BufferedImage(object.getWidth(), object.getHeight(),
                    BufferedImage.TYPE_INT_ARGB) ;
        // Graphic
        final Graphics2D g2 = image.createGraphics() ;        
        
        final int width = object.getWidth() ;
        final int endX = width - bi3.getWidth() ;
        int newX ;
        final int newY = object.getHeight() - bi2.getHeight() ;
        
        // Draw head
        g2.drawImage(bi1, 0, 0, null) ;
        
        for(newX = bi1.getWidth(); newX < endX; newX += bi2.getWidth())
        {
            g2.drawImage(bi2, newX, newY, null) ;
        }
        
        g2.drawImage(bi3, newX, object.getHeight() - bi3.getHeight(), null) ;
        
        g2.dispose() ;
        
        return image ;
    }

}
