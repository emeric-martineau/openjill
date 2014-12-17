package org.jill.game.entities.obj;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.jill.game.entities.ObjectEntityImpl;
import org.jill.openjill.core.api.entities.ObjectParam;

/**
 * MAP/DEMO object.
 *
 * @author Emeric MARTINEAU
 */
public final class DemoMapManager extends ObjectEntityImpl {
    /**
     * Start index of letter.
     */
    private static final int MAP_START_LETTER = 20;

    /**
     * End index of letter.
     */
    private static final int MAP_END_LETTER = 23;

    /**
     * Start index of letter.
     */
    private static final int DEMO_START_LETTER = 16;

    /**
     * End index of letter.
     */
    private static final int DEMO_END_LETTER = 20;

    /**
     * Picture.
     */
    private BufferedImage image;

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        alwaysOnScreen = true;

        // Buffer image
       image =
            new BufferedImage(getWidth(), getHeight(),
                    BufferedImage.TYPE_INT_ARGB);

       final Graphics g2 = image.createGraphics();
       BufferedImage letter;
       int posX = 0;
       int start;
       int end;

       if (getxSpeed() > 0) {
           // MAP
           start = MAP_START_LETTER;
           end = MAP_END_LETTER;
       } else {
           // DEMO
           start = DEMO_START_LETTER;
           end = DEMO_END_LETTER;
       }

       for (int indexLetter = start; indexLetter < end; indexLetter++) {
           letter = pictureCache.getImage(3, indexLetter);

           g2.drawImage(letter, posX, 0, null);

           posX += letter.getWidth();
       }

       g2.dispose();
    }

    /* (non-Javadoc)
     * @see org.jill.openjill.core.api.entities.ObjectEntity#getPicture()
     */
    @Override
    public BufferedImage msgDraw() {
        return image;
    }

    @Override
    public int getX() {
        return 16;
    }

    @Override
    public int getY() {
        return 4;
    }
}