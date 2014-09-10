package org.jill.game.entities.back.abs;

import java.util.HashMap;
import java.util.Map;
import org.jill.game.entities.picutre.PictureSynchronizer;

/**
 * Class to implement abstract synchronized picture class.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractSynchronisedImageBackgroundEntity
    extends AbstractParameterBackgroundEntity {

    /**
     * Map of picture.
     */
    private static final Map<String, PictureSynchronizer> MAP_PICUTRE =
            new HashMap<>();

    /**
     * Current picture synchronizer.
     */
    private PictureSynchronizer ps;

    /**
     * For internal use only.
     */
    protected AbstractSynchronisedImageBackgroundEntity() {
        super();
    }

    /**
     * Return picture object.
     *
     * @param clazz class
     * @return picture synchronizer
     */
    protected final PictureSynchronizer getPictureSync(final Class clazz) {
        return getPictureSync(clazz.toString());
    }

    /**
     * Return picture object.
     *
     * @param name name
     * @return picture synchronizer
     */
    protected final PictureSynchronizer getPictureSync(final String name) {
        ps = MAP_PICUTRE.get(name);

        return ps;
    }

    /**
     * Return picture synchronizer.
     *
     * @return picture synchronizer
     */
    protected final PictureSynchronizer getPictureSync() {
        return ps;
    }

    /**
     * Add picture synchronizer.
     *
     * @param clazz class
     * @param picture picture synchronizer
     */
    protected final void addPictureSync(final Class clazz,
            final PictureSynchronizer picture) {
        addPictureSync(clazz.toString(), picture);
    }

    /**
     * Add picture synchronizer.
     *
     * @param name class
     * @param picture picture synchronizer
     */
    protected final void addPictureSync(final String name,
            final PictureSynchronizer picture) {
        ps = picture;

        MAP_PICUTRE.put(name, picture);
    }
}
