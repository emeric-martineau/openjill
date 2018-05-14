package org.jill.game.entities.obj.abs;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.jill.game.entities.picutre.PictureSynchronizer;
import org.jill.openjill.core.api.entities.ObjectParam;

/**
 * Class to implement abstract synchronized picture class.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractSynchronisedImageObjectEntity
        extends AbstractParameterObjectEntity {

    /**
     * Map of picture.
     */
    private static final Map<Class, PictureSynchronizer> MAP_PICUTRE
            = new HashMap<>();

    /**
     * Current picture synchronizer.
     */
    private PictureSynchronizer ps;

    /**
     * Picture array.
     */
    private Optional<BufferedImage>[] images;

    /**
     * Current inde image to display.
     */
    private int indexEtat = 0;

    /**
     * Constructor.
     *
     * @param objParam object parameter
     */
    @Override
    public void init(final ObjectParam objParam) {
        super.init(objParam);

        int tileIndex = getConfInteger("tile");
        int tileSetIndex = getConfInteger("tileSet");

        // Load picture for each object. Don't use cache cause some picture
        // change between jill episod.
        this.images
                = new Optional[getConfInteger("numberTileSet")];

        for (int index = 0; index < this.images.length; index++) {
            this.images[this.images.length - 1 - index]
                    = this.pictureCache.getImage(tileSetIndex, tileIndex
                    + index);
        }

        if (!MAP_PICUTRE.containsKey(this.getClass())) {
            int maxDisplayCounter = getConfInteger("cycle");

            // Create synchronizer
            addPictureSync(this.getClass(),
                    new PictureSynchronizer(maxDisplayCounter));
        } else {
            this.ps = MAP_PICUTRE.get(this.getClass());
        }
    }

    /**
     * Add picture synchronizer.
     *
     * @param clazz   class
     * @param picture picture synchronizer
     */
    private void addPictureSync(final Class clazz,
            final PictureSynchronizer picture) {
        this.ps = picture;

        MAP_PICUTRE.put(clazz, picture);
    }

    @Override
    public final Optional<BufferedImage> msgDraw() {
        this.indexEtat = this.ps.updatePictureIndex(this.indexEtat,
                this.images.length);

        return images[this.ps.getIndexPicture()];
    }
}
