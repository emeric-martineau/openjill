package org.jill.game.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.jill.game.screen.conf.RectangleConf;
import org.jill.openjill.core.api.manager.TileManager;

/**
 * Contain some usefull method.
 *
 * @author Emric MARTINEAU
 */
public class AbstractMessageBox {
    /**
     * Draw area.
     *
     * @param g2BoxPicture grapic 2d to draw
     * @param pictureCache picture cache
     * @param textArea aera
     * @throws NumberFormatException
     */
    protected void drawArea(final Graphics2D g2BoxPicture,
            final TileManager pictureCache,
            final RectangleConf textArea) throws NumberFormatException {

        if (textArea != null) {
            final Color baseColor = pictureCache.getColorMap()[
                    Integer.valueOf(textArea.getColor())];
            // Draw background text area
            g2BoxPicture.setColor(new Color(baseColor.getRGB()));

            g2BoxPicture.fillRect(textArea.getX(), textArea.getY(),
                    textArea.getWidth(), textArea.getHeight());
        }
    }

    /**
     * Draw one tile on picture.
     *
     * @param pictureCache picture cache
     * @param tileSetIndex tileset index
     * @param tileIndex tile index
     * @param x x
     * @param y y
     * @param g2 graphic 2d
     */
    protected void drawOneTile(final TileManager pictureCache,
            final int tileSetIndex, final int tileIndex,
            final int x, final int y, final Graphics2D g2) {
        // Left upper corner
        final BufferedImage tilePicture = pictureCache.getImage(
                tileSetIndex,tileIndex);
        g2.drawImage(tilePicture, x, y, null);
    }
}
