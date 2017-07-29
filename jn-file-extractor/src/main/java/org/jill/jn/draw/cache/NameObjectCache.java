/*

 */
package org.jill.jn.draw.cache;

import java.io.IOException;
import java.util.Properties;

/**
 * Cache of picture
 * 
 * @author Emeric Martineau
 */
public class NameObjectCache
{
    /**
     * Map of object tile
     */
    private Properties mapObjectTile = new Properties() ;
    
    public NameObjectCache()
    {
        try {
            mapObjectTile.load(NameObjectCache.class.getClassLoader().
                    getResourceAsStream(
                    "objects_description_mapping.properties")) ;
        } catch (IOException e) {
            System.out.println(
                    "Error, can't load properties file where " +
                    "mapping objects and desription") ;
            e.printStackTrace() ;
        }                
    }
    
    /**
     * Return type description
     * 
     * @param type
     * 
     * @return nescription or null if not exists
     */
    public String getDescription(final int type)
    {
        return mapObjectTile.getProperty(String.valueOf(type)) ;
    }
}
