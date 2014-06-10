package org.jill.game.manager.background;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jill.dma.DmaEntry;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.BackgroundParam;

/**
 * Background manager.
 *
 * @author Emeric MARTINEAU
 */
public final class BackgroundManager {

    /**
     * default background class.
     */
    private static final String DEFAULT_BACKGROUND_CLASS = "default";

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(
        BackgroundManager.class.getName());

    /**
     * Instance of this object.
     */
    private static final BackgroundManager INSTANCE = new BackgroundManager();

    /**
     * Map of object tile.
     */
    private Map<String, Class<BackgroundEntity>> mapObjectPicture;

    /**
     * Return this instance of object.
     *
     * @return background manager
     */
    public static BackgroundManager getInstance() {
        return INSTANCE;
    }

    /**
     * Load properties.
     *
     * @return properties
     */
    private static Properties loadObjectTitle() {
        Properties mapObjectTile = new Properties();

        try {
            mapObjectTile.load(BackgroundManager.class.getClassLoader().
                getResourceAsStream(
                    "background_manager_mapping.properties"));
        } catch (final IOException ex) {
            LOGGER.log(Level.SEVERE,
                "Error, can't load properties file where "
                + "mapping background and manager", ex);

            mapObjectTile = null;
        }

        return mapObjectTile;
    }

    /**
     * Private constructor.
     */
    private BackgroundManager() {
        try {
            mapObjectPicture = initMapOfObjectSrite();
        } catch (ReflectiveOperationException ex) {
            LOGGER.log(Level.SEVERE, "Error loading background", ex);
        }
    }

    /**
     * Init picture of know object.
     *
     * @return map between name of background and class
     *
     * @throws ClassNotFoundException if error
     * @throws InstantiationException if error
     * @throws IllegalAccessException if error
     */
    @SuppressWarnings("unchecked")
    private Map<String, Class<BackgroundEntity>> initMapOfObjectSrite()
        throws ClassNotFoundException, IllegalAccessException,
        InstantiationException {
        // Load mapping
        final Properties mapObjectTile = loadObjectTitle();
        // Get keys
        final Enumeration<?> e = mapObjectTile.propertyNames();
        // Map between key and manager
        final Map<String, Class<BackgroundEntity>> mapBackNamePicture
            = new HashMap<>();

        String key;
        String value;

        Class<BackgroundEntity> c;

        while (e.hasMoreElements()) {
            key = (String) e.nextElement();

            value = mapObjectTile.getProperty(key);

            c = (Class<BackgroundEntity>) Class.forName(value);

            mapBackNamePicture.put(key, c);
        }

        return mapBackNamePicture;
    }

    /**
     * Create a object.
     *
     * @param className class
     * @param backParam back parameter
     *
     * @return background
     */
    private BackgroundEntity createObject(
        final Class<BackgroundEntity> className,
        final BackgroundParam backParam) {
        BackgroundEntity o = null;

        try {
            o = (BackgroundEntity) className.
                getConstructor().newInstance();

            o.init(backParam);
        } catch (IllegalArgumentException | SecurityException |
            InvocationTargetException | NoSuchMethodException |
            IllegalAccessException | InstantiationException ex) {
            LOGGER.log(Level.SEVERE,
                "Create jill background error !", ex);
        }

        return o;
    }

    /**
     * Create an jill object.
     *
     * @param backParam object in file of level
     *
     * @return background
     */
    public BackgroundEntity getJillBackground(
            final BackgroundParam backParam) {
        final DmaEntry de = backParam.getDmaEntry();
        final String dmaName = de.getName();
        Class<BackgroundEntity> className = this.mapObjectPicture.get(
            dmaName);

        BackgroundEntity o;

        if (className == null) {
            className = this.mapObjectPicture.get(DEFAULT_BACKGROUND_CLASS);
        }

        if (className != null) {
            o = createObject(className, backParam);
        } else {
            LOGGER.severe(String.format(
                "Can't find class associate to '%s' name", dmaName));
            o = null;
        }

        return o;
    }
}
