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
    private static final Map<Class, PictureSynchronizer> MAP_PICUTRE =
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
        ps = MAP_PICUTRE.get(clazz);

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
        ps = picture;

        MAP_PICUTRE.put(clazz, picture);
    }
}
