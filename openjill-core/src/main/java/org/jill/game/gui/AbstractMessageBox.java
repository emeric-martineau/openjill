package org.jill.game.gui;

import org.jill.game.gui.conf.MessageConf;
import org.jill.game.screen.conf.ImagesConf;
import org.jill.game.screen.conf.RectangleConf;
import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.picture.PictureTools;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

/**
 * Contain some usefull method.
 *
 * @author Emric MARTINEAU
 */
public abstract class AbstractMessageBox {
    /**
     * Sha file.
     */
    protected final ShaFile shaFile;

    /**
     * Screen conf.
     */
    protected final EnumScreenType screen;

    protected AbstractMessageBox(final ShaFile shaFile, final EnumScreenType screen) {
        this.shaFile = shaFile;
        this.screen = screen;
    }

    /**
     * Draw all picture in configuration.
     *
     * @param g2BoxPicture Graphic 2D
     * @param conf         current config
     */
    protected void drawAllPicture(final Graphics2D g2BoxPicture, final MessageConf conf) {
        // Draw picture
        for (ImagesConf ic : conf.getImages()) {
            drawOneTile(ic.getTileset(), ic.getTile(), ic.getX(),
                    ic.getY(), g2BoxPicture);
        }
    }

    /**
     * Draw area.
     *
     * @param g2BoxPicture grapic 2d to draw
     * @param textManager picture cache
     * @param textArea     aera
     * @throws NumberFormatException error when config file not valid
     */
    protected void drawArea(final Graphics2D g2BoxPicture,
            final TextManager textManager,
            final Optional<RectangleConf> textArea) throws NumberFormatException {

        if (textArea.isPresent()) {
            final RectangleConf currentTextArea = textArea.get();

            final Color baseColor = textManager.getColorMap()[
                    Integer.valueOf(currentTextArea.getColor())];
            // Draw background text area
            g2BoxPicture.setColor(new Color(baseColor.getRGB()));

            g2BoxPicture.fillRect(currentTextArea.getX(), currentTextArea.getY(),
                    currentTextArea.getWidth(), currentTextArea.getHeight());
        }
    }

    /**
     * Draw one tile on picture.
     *
     * @param tileSetIndex tileset index
     * @param tileIndex    tile index
     * @param x            x
     * @param y            y
     * @param g2           graphic 2d
     */
    protected void drawOneTile(final int tileSetIndex,
                               final int tileIndex, final int x, final int y, final Graphics2D g2) {
        // Left upper corner
        final BufferedImage tilePicture = PictureTools.getPicture(shaFile, tileSetIndex, tileIndex, screen).get();
        g2.drawImage(tilePicture, x, y, null);
    }
}
