package org.jill.game.entities.back.abs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
     * Create picture for animate background.
     *
     * @param srcImage src image
     * @param backColor background color
     *
     * @return new image
     */
    protected static BufferedImage createPicture(final BufferedImage srcImage,
            final Color backColor) {
        BufferedImage destImage;
        Graphics2D g2;

        destImage = new BufferedImage(srcImage.getWidth(),
                srcImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        g2 = destImage.createGraphics();
        g2.setColor(backColor);
        g2.fillRect(0, 0, srcImage.getWidth(), srcImage.getHeight());
        g2.drawImage(srcImage, 0, 0, null);

        g2.dispose();

        return destImage;
    }

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
