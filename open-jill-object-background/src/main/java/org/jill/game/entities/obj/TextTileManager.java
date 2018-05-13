package org.jill.game.entities.obj;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.jill.game.entities.ObjectEntityImpl;
import org.jill.openjill.core.api.entities.ObjectParam;

/**
 * Object to draw text.
 *
 * @author Emeric MARTINEAU
 */
public final class TextTileManager extends ObjectEntityImpl {
    /**
     * Small text type.
     */
    private static final int SMALL_TEXT = 20;

    /**
     * Big text type.
     */
    private static final int BIG_TEXT = 21;

    /**
     * Default constructor.
     *
     * @param objectParam object param
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);
        this.writeOnBackGround = true;
    }

    @Override
    public BufferedImage msgDraw() {
        // Create picture
        int widthObj = getWidth();
        int heightObj = getHeight();

        // Some text are empty, width or height = 0. But BufferedImage want > 0
        if (widthObj <= 0) {
            widthObj = 1;
        }

        if (heightObj <= 0) {
            heightObj = 1;
        }

        // Buffer image
        final BufferedImage image =
                new BufferedImage(widthObj, heightObj,
                        BufferedImage.TYPE_INT_ARGB);
        // Graphic
        final Graphics2D g2 = image.createGraphics();

        if (getType() == SMALL_TEXT) {
            pictureCache.getTextManager().drawSmallText(g2, 0, 0,
                    getStringStackEntry().get().getValue(),
                    getxSpeed(), getySpeed());
        } else if (getType() == BIG_TEXT) {
            pictureCache.getTextManager().drawBigText(g2, 0, 0,
                    getStringStackEntry().get().getValue(),
                    getxSpeed(), getySpeed());
        }

        return image;
    }
}
