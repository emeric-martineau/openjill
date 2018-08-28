package org.jill.entities.manager.object;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jill.openjill.core.api.entities.ObjectEntity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractObject implements ObjectEntity {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(AbstractObject.class.getName());

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
