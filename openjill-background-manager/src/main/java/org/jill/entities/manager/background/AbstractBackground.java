package org.jill.entities.manager.background;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jill.dma.DmaEntry;
import org.jill.jn.BackgroundLayer;
import org.jill.jn.ObjectItem;
import org.jill.openjill.core.api.entities.BackgroundEntity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractBackground implements BackgroundEntity {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(AbstractBackground.class.getName());

    /**
     * Picture of background.
     */
    protected BufferedImage picture;

    /**
     * Dma entry of current background.
     */
    protected DmaEntry dmaEntry;

    @Override
    public int getMapCode() {
        return dmaEntry.getMapCode();
    }

    @Override
    public String getName() {
        return dmaEntry.getName();
    }

    @Override
    public BufferedImage getPicture(final int x, final int y) {
        return picture;
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
    public boolean isMsgDraw() {
        return dmaEntry.isMsgDraw();
    }

    @Override
    public boolean isMsgUpdate() {
        return dmaEntry.isMsgUpdate();
    }


    @Override
    public boolean isMsgTouch() {
        return dmaEntry.isMsgTouch();
    }

    @Override
    public boolean isPlayerThru() {
        return dmaEntry.isPlayerThru();
    }

    @Override
    public boolean isStair() {
        return dmaEntry.isStair();
    }

    @Override
    public boolean isVine() {
        return dmaEntry.isVine();
    }

    @Override
    public void msgDraw(BackgroundLayer background, int x, int y) {

    }

    @Override
    public void msgTouch(ObjectItem obj) {

    }

    @Override
    public void msgUpdate(BackgroundLayer background, int x, int y) {

    }
}
