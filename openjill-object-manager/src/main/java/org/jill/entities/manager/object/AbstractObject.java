package org.jill.entities.manager.object;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaFile;
import org.jill.sha.ShaTile;
import org.jill.sha.ShaTileSet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public abstract class AbstractObject implements ObjectEntity {

    /**
     * Get picture for current screen configuration.
     *
     * @param shaFile the object contain picture
     * @param tileSet index of tile set
     * @param tile tile index
     * @param screen screen configuration
     *
     * @return the desired picture.
     */
    protected Optional<BufferedImage> getPicture(final ShaFile shaFile, final int tileSet, final int tile,
                                                final EnumScreenType screen) {
        final ShaTileSet[] shaTileset = shaFile.getShaTileSet();

        ShaTile[] currentShaTile = null;

        for (ShaTileSet tileset: shaTileset) {
            if (tileset.getTitleSetIndex() == tileSet) {
                currentShaTile = tileset.getShaTile();

                 break;
            }
        }

        if (tile < currentShaTile.length) {
            // For background T16, T17, T18, T19, T20, tile are invalid !!!
            ShaTile currentTile = currentShaTile[tile];

            switch (screen) {
                case CGA:
                    return Optional.of(currentTile.getPictureCga());
                case EGA:
                    return  Optional.of(currentTile.getPictureEga());
                default:
                    return Optional.of(currentTile.getPictureVga());
            }
        }

        return Optional.empty();
    }

    /**
     * Short way to draw.
     *
     * @param g2d graphic
     * @param img image
     * @param x x
     * @param y y
     */
    protected static void draw(Graphics g2d, Image img, int x, int y) {
        g2d.drawImage(img, x, y, null);
    }

    /**
     * Short way to draw.
     *
     * @param dest image
     * @param src
     * @param x x
     * @param y y
     */
    protected static void drawFromImage(BufferedImage dest, Image src, int x, int y) {
        final Graphics2D g2d = dest.createGraphics();

        g2d.drawImage(src, x, y, null);

        g2d.dispose();
    }

    /**
     * Read config file.
     *
     * @param filename final name of config file
     * @return properties file
     */
    protected <T> T readConf(final String filename, final Class<T> clazz) {

        final ObjectMapper mapper = new ObjectMapper();
        final InputStream is =
                this.getClass().getClassLoader().
                        getResourceAsStream(filename);

        T mc;

        // Load config
        try {
            mc = mapper.readValue(is, clazz);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE,
                    String.format("Unable to load config for control '%s'",
                            filename),
                    ex);

            mc = null;
        }

        return mc;
    }

    @Override
    public boolean isAlwaysOnScreen() {
        return false;
    }

    @Override
    public boolean isCheckPoint() {
        return false;
    }

    @Override
    public boolean isKillableObject() {
        return false;
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public boolean canFire() {
        return false;
    }
}
