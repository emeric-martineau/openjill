package org.jill.game.entities.back;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.jill.game.entities.BackgroundEntityImpl;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.BackgroundParam;

/**
 * Standard background object.
 *
 * @author Emeric MARTINEAU
 */
public class DoubleImageCopyTopBackgroundEntity extends BackgroundEntityImpl {
    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * Picture with special draw.
     */
    private BufferedImage realPicutre;

    /**
     * Constructor.
     *
     * @param backParam background parameter
     */
    @Override
    public void init(final BackgroundParam backParam) {
        super.init(backParam);

        backgroundObject = backParam.getBackgroundObject();
    }

    @Override
    public BufferedImage getPicture() {
        return realPicutre;
    }

    @Override
    public void msgDraw() {
        final BufferedImage thisPicture =
                getPictureCache().getBackgroundPicture(getMapCode()).get();

        int theY = getY();

        if (theY == 0) {
            theY = 2;
        }

        final BufferedImage backPicture =
                backgroundObject[getX()][theY - 1].getPicture();

        int width = thisPicture.getWidth();
        int height = thisPicture.getHeight();

        if (width < backPicture.getWidth()) {
            width = backPicture.getWidth();
        }

        if (height < backPicture.getHeight()) {
            height = backPicture.getHeight();
        }

        realPicutre = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);

        final Graphics2D g2 = realPicutre.createGraphics();

        draw(g2, backPicture, 0, 0);
        draw(g2, thisPicture, 0, 0);

        g2.dispose();
    }

    @Override
    public void msgUpdate() {
    }
}