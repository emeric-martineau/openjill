package org.jill.game.entities.back;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.jill.game.entities.back.abs.
        AbstractSynchronisedImageBackgroundEntity;
import org.jill.game.entities.obj.player.PlayerState;
import org.jill.game.entities.picutre.PictureSynchronizer;
import org.jill.openjill.core.api.entities.BackgroundParam;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryLifeMessage;

/**
 * Spike.
 *
 * @author Emeric MARTINEAU
 */
public final class SpikeBackgroundEntity
    extends AbstractSynchronisedImageBackgroundEntity {
    /**
     * Picture array.
     */
    private BufferedImage[] images;

    /**
     * Current inde image to display.
     */
    private int indexEtat = 0;

    /**
     * Constructor.
     *
     * @param backParam background parameter
     */
    @Override
    public void init(final BackgroundParam backParam) {
        super.init(backParam);

        int tileIndex = getDmaEntry().getTile();
        int tileSetIndex = getDmaEntry().getTileset();

        int nbPicture = getConfInteger("numberTileSet");

        images = new BufferedImage[nbPicture];

        final Color backColor = getPictureCache().getBackgroundColor();
        BufferedImage srcImage;
        BufferedImage destImage;
        Graphics2D g2;

        for (int index = 0; index < images.length; index++) {
            srcImage = getPictureCache().getImage(tileSetIndex, tileIndex
                    + index);

            destImage = new BufferedImage(srcImage.getWidth(),
                    srcImage.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            g2 = destImage.createGraphics();
            g2.setColor(backColor);
            g2.fillRect(0, 0, srcImage.getWidth(), srcImage.getHeight());
            g2.drawImage(srcImage, 0, 0, null);

            g2.dispose();

            images[index] = destImage;
        }

        if (getPictureSync(this.getClass()) == null) {
            int maxDisplayCounter = getConfInteger("cycle");

            // Create synchronizer
            final PictureSynchronizer ps =
                    new PictureSynchronizer(maxDisplayCounter);

            addPictureSync(this.getClass(), ps);
        }
    }

    @Override
    public BufferedImage getPicture() {
        return images[getPictureSync().getIndexPicture()];
    }

    @Override
    public void msgDraw() {
        // Not special draw need

    }

    @Override
    public void msgUpdate() {
        this.indexEtat = getPictureSync().updatePictureIndex(
                this.indexEtat, images);
    }

    @Override
    public void msgTouch(final ObjectEntity obj) {
        obj.msgKill(this, InventoryLifeMessage.DEAD_MESSAGE,
                PlayerState.DIE_SUB_STATE_OTHER_BACK);
    }
}
