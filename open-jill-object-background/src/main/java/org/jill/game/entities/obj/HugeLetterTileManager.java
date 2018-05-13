package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;

import org.jill.game.entities.ObjectEntityImpl;
import org.jill.openjill.core.api.entities.ObjectParam;

/**
 * Object to draw text.
 *
 * @author Emeric MARTINEAU
 */
public final class HugeLetterTileManager extends ObjectEntityImpl {
    /**
     * Piture.
     */
    private BufferedImage hugeLetter;

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);
        this.writeOnBackGround = true;

//        42_0=33,0
//        42_1=33,1
//        42_2=33,2
//        42_3=33,3
//        42_4=33,4
//
//        for(int tileIndex = 0; tileIndex < hugeLetter.length; tileIndex++) {
//            hugeLetter[tileIndex] = pictureCache.getImage(33, tileIndex);
//        }

        hugeLetter = pictureCache.getImage(33, getxSpeed()).get();
    }

    @Override
    public BufferedImage msgDraw() {
        return hugeLetter;
    }
}
