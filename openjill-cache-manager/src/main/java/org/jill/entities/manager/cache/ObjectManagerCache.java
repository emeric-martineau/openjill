/*

 */
package org.jill.entities.manager.cache;

import org.jill.dma.DmaFile;
import org.jill.entities.manager.param.ObjectParamImpl;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaFile;

import java.io.IOException;
import java.util.*;

/**
 * Cache of picture
 *
 * @author Emeric Martineau
 */
public class ObjectManagerCache {
    /**
     * Map of tile.
     *
     * @param configFilename name of config file
     */
    private final Map<String, ObjectEntity> mapOfManager;

    public ObjectManagerCache(final String configFilename, final ShaFile shaFile, final DmaFile dmaFile,
                              final EnumScreenType screen)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {

        final Properties prop = loadObjectTitle(configFilename);
        String value;

        ObjectEntity manager;
        Class<ObjectEntity> clazz;

        mapOfManager = new HashMap<>();

        final Set<String> propertyNames = prop.stringPropertyNames();

        for (String key: propertyNames) {
            value = prop.getProperty(key);

            if (value == null) {
                // TODO raise error
            }

            // Load manager class
            clazz = (Class<ObjectEntity>) Class.forName(value);

            manager = clazz.newInstance();

            // TODO need to be change BackgroundParamImpl like game
            final ObjectParam objParam = new ObjectParamImpl();

            objParam.init(null, null, shaFile, dmaFile, null, screen, -1);

            manager.init(objParam);

            mapOfManager.put(key, manager);
        }
    }

    /**
     * Load properties.
     *
     * @param configFilename name of config file
     *
     * @return propoerties object
     */
    private Properties loadObjectTitle(final String configFilename) {
        Properties mapObjectTile = new Properties();

        try {
            mapObjectTile.load(getClass().getClassLoader().
                    getResourceAsStream(configFilename));
        } catch (IOException e) {
            System.out.println("Error, can't load properties file where " +
                    " mapping objects and manager");
            e.printStackTrace();

            mapObjectTile = null;
        }

        return mapObjectTile;
    }

    /**
     * Get manager of object.
     *
     * @param type name of background
     *
     * @return manager
     */
    public ObjectEntity getManager(final int type) {
        return mapOfManager.get(String.valueOf(type));
    }
}
