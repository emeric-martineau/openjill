/*

 */
package org.jill.jn.draw.cache;

import org.jill.dma.DmaEntry;
import org.jill.dma.DmaFile;
import org.jill.entities.param.BackgroundParamImpl;
import org.jill.jn.JnFileExtractor;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.BackgroundParam;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaFile;

import java.io.IOException;
import java.util.*;

/**
 * Cache of picture
 *
 * @author Emeric Martineau
 */
public class BackgroundManagerCache {
    /**
     * Map of tile.
     *
     * @param configFilename name of config file
     */
    private final Map<String, BackgroundEntity> mapOfManager;

    public BackgroundManagerCache(final String configFilename, final ShaFile shaFile, final DmaFile dmaFile,
                                  final EnumScreenType screen)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {

        final Properties prop = loadObjectTitle(configFilename);
        String value;

        BackgroundEntity manager;
        Class<BackgroundEntity> clazz;

        mapOfManager = new HashMap<>();

        final Iterator<Integer> itDma = dmaFile.getDmaEntryIterator();
        DmaEntry dmaEntry;

        while (itDma.hasNext()) {
            dmaEntry = dmaFile.getDmaEntry(itDma.next()).get();

            value = prop.getProperty(dmaEntry.getName());

            if (value == null) {
                value = prop.getProperty("default");
            }

            // Load manager class
            clazz = (Class<BackgroundEntity>) Class.forName(value);

            manager = clazz.newInstance();

            final BackgroundParam bckParam = new BackgroundParamImpl();

            bckParam.init(shaFile, dmaFile, dmaEntry, screen);

            manager.init(bckParam);

            mapOfManager.put(dmaEntry.getName(), manager);
        }
    }

    /**
     * Load properties.
     *
     * @param configFilename name of config file
     *
     * @return propoerties object
     */
    private static Properties loadObjectTitle(final String configFilename) {
        Properties mapObjectTile = new Properties();

        try {
            mapObjectTile.load(JnFileExtractor.class.getClassLoader().
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
     * Get mangager of background.
     *
     * @param backgroundName name of background
     *
     * @return manager
     */
    public BackgroundEntity getManager(final String backgroundName) {
        return mapOfManager.get(backgroundName);
    }
}
