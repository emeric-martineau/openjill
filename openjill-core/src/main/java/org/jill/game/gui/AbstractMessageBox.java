package org.jill.game.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Optional;

import org.jill.game.gui.conf.MessageConf;
import org.jill.game.screen.conf.ImagesConf;
import org.jill.game.screen.conf.RectangleConf;
import org.jill.openjill.core.api.manager.TileManager;

/**
 * Contain some usefull method.
 *
 * @author Emric MARTINEAU
 */
public class AbstractMessageBox {

    /**
     * Draw all picture in configuration.
     *
     * @param g2BoxPicture Graphic 2D
     * @param pictureCache picture cache
     * @param conf         current config
     */
    protected void drawAllPicture(final Graphics2D g2BoxPicture,
            final TileManager pictureCache, final MessageConf conf) {
        // Draw picture
        for (ImagesConf ic : conf.getImages()) {
            drawOneTile(pictureCache, ic.getTileset(), ic.getTile(), ic.getX(),
                    ic.getY(), g2BoxPicture);
        }
    }

    /**
     * Draw area.
     *
     * @param g2BoxPicture grapic 2d to draw
     * @param pictureCache picture cache
     * @param textArea     aera
     * @throws NumberFormatException error when config file not valid
     */
    protected void drawArea(final Graphics2D g2BoxPicture,
            final TileManager pictureCache,
            final Optional<RectangleConf> textArea) throws NumberFormatException {

        if (textArea.isPresent()) {
            final RectangleConf currentTextArea = textArea.get();

            final Color baseColor = pictureCache.getColorMap()[
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
     * @param pictureCache picture cache
     * @param tileSetIndex tileset index
     * @param tileIndex    tile index
     * @param x            x
     * @param y            y
     * @param g2           graphic 2d
     */
    protected void drawOneTile(final TileManager pictureCache,
            final int tileSetIndex, final int tileIndex,
            final int x, final int y, final Graphics2D g2) {
        // Left upper corner
        final BufferedImage tilePicture = pictureCache.getImage(
                tileSetIndex, tileIndex);
        g2.drawImage(tilePicture, x, y, null);
    }
}
