package org.jill.entities.manager.background;

import org.jill.dma.DmaEntry;
import org.jill.dma.DmaFile;
import org.jill.jn.BackgroundLayer;
import org.jill.openjill.core.api.entities.BackgroundParam;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DoubleImageCopyRightBackgroundEntity extends AbstractBackground {
    /**
     * Sha file to get near image.
     */
    private ShaFile shaFile;

    /**
     * Dma file to get near image.
     */
    private DmaFile dmaFile;

    /**
     * Screen configuration.
     */
    private EnumScreenType screen;

    /**
     * This class manage many background. We need use cache to avoid image conflict and increase speed performance.
     */
    private Map<String, BufferedImage> pictureCache = new HashMap();

    @Override
    public void init(BackgroundParam backParameter) {
        dmaFile = backParameter.getDmaFile();
        dmaEntry = backParameter.getDmaEntry();
        shaFile = backParameter.getShaFile();
        screen = backParameter.getScreen();

        final Optional<BufferedImage> currentPicture = getPicture(backParameter.getShaFile(), dmaEntry.getTileset(),
                dmaEntry.getTile(), backParameter.getScreen());

        if (currentPicture.isPresent()) {
            picture = currentPicture.get();
        }
    }

    @Override
    public void msgDraw(final BackgroundLayer background, final int x, final int y) {
        int theX = x;

        if (theX == 0) {
            theX = 2;
        }

        final int mapCode = background.getMapCode(theX - 1, y);

        final DmaEntry nearDma = dmaFile.getDmaEntry(mapCode).get();

        final BufferedImage backPicture = getPicture(shaFile, nearDma.getTileset(), nearDma.getTile(), screen).get();

        int width = picture.getWidth();
        int height = picture.getHeight();

        if (width < backPicture.getWidth()) {
            width = backPicture.getWidth();
        }

        if (height < backPicture.getHeight()) {
            height = backPicture.getHeight();
        }

        final BufferedImage realPicutre = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);

        final Graphics2D g2 = realPicutre.createGraphics();

        draw(g2, backPicture, 0, 0);
        draw(g2, picture, 0, 0);

        g2.dispose();

        pictureCache.put(String.format("%d_%d", x, y), realPicutre);
    }

    @Override
    public BufferedImage getPicture(final int x, final int y) {
        return pictureCache.get(String.format("%d_%d", x, y));
    }
}
