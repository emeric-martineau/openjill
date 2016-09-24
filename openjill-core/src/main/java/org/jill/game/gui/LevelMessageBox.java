package org.jill.game.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jill.game.gui.conf.LevelMessageConf;
import org.jill.game.screen.conf.RectangleConf;
import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.manager.TileManager;
import org.jill.openjill.core.api.screen.EnumScreenType;

/**
 * Display level message.
 *
 * @author Emeric MARTINEAU
 */
public final class LevelMessageBox extends AbstractMessageBox {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(
                    LevelMessageBox.class.getName());

    /**
     * Configuration.
     */
    private final LevelMessageConf conf;

    /**
     * If menu is enable.
     */
    private boolean enable = false;

    /**
     * If level can be load.
     */
    private boolean canchange = false;

    /**
     * Background.
     */
    private final BufferedImage boxPicture;

    /**
     * Graphic of status bar.
     */
    private final Graphics2D g2BoxPicture;

    /**
     * Picture cache.
     */
    private final TileManager pictureCache;

    /**
     * Key to get good message for level.
     */
    private final String keyOfMessage;

    public LevelMessageBox(final TileManager pctCache,
            final String saveExtension, final EnumScreenType screen) {

        this.pictureCache = pctCache;

        if (screen == EnumScreenType.VGA) {
            this.conf = readConf("level_messagebox_vga.json");
        } else {
            this.conf = readConf("level_messagebox.json");
        }

        // Buffer image
        this.boxPicture =
            new BufferedImage(conf.getWidth(), conf.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);

        // Graphic
        this.g2BoxPicture = this.boxPicture.createGraphics();

        RectangleConf textArea = this.conf.getTextarea();

        drawArea(this.g2BoxPicture, pctCache, textArea);

        textArea = this.conf.getPicturearea();

        drawArea(this.g2BoxPicture, pctCache, textArea);

        // Draw picture
        drawAllPicture(this.g2BoxPicture, this.pictureCache, this.conf);

        this.keyOfMessage = saveExtension;
    }


    /**
     * Read config file.
     *
     * @param filename final name of config file
     *
     * @return properties file
     */
    private static LevelMessageConf readConf(final String filename) {

        final ObjectMapper mapper = new ObjectMapper();
        final InputStream is =
                LevelMessageBox.class.getClassLoader().
                        getResourceAsStream(filename);

        LevelMessageConf mc;

        // Load menu
        try {
            mc = mapper.readValue(is, LevelMessageConf.class);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE,
                String.format("Unable to load config for message level '%s'",
                        filename),
                ex);

            mc = null;
        }

        return mc;
    }

    /**
     * @return enable
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * @param en enable
     */
    public void setEnable(boolean en) {
        this.enable = en;
    }

    /**
     * Position of box.
     *
     * @return X
     */
    public int getX() {
        return conf.getX();
    }

    /**
     * Position of box.
     *
     * @return Y
     */
    public int getY() {
        return conf.getY();
    }

    /**
     * Return status bar
     *
     * @return
     */
    public BufferedImage getBox() {
        return boxPicture;
    }

    /**
     * If can change level.
     *
     * @return
     */
    public boolean isCanchange() {
        return canchange;
    }

    /**
     * Set change level.
     *
     * @param change change level
     */
    public void setCanchange(boolean change) {
        this.canchange = change;

        if (change) {
            this.enable = false;
        }
    }

    /**
     * Set level.
     *
     * @param level
     */
    public void setLevel(final int level) {
        RectangleConf textAreaConf = this.conf.getTextarea();

        // Clear text area
        drawArea(this.g2BoxPicture, this.pictureCache, textAreaConf);

        // Compute size of text.
        // Text is splited by \n.
        List<String> listMessage = this.conf.getMessages().get(keyOfMessage);

        if (level >= 0 && level < listMessage.size()) {
            // Get message for this level
            String textRaw = listMessage.get(level);

            // Split message on '\n'
            String[] textToDraw = textRaw.split("\n");

            // Create one image per line to calcu
            BufferedImage[] pictureToDraw =
                    new BufferedImage[textToDraw.length];

            int heightOfMessages = 0;

            for (int indexMsg = 0; indexMsg < textToDraw.length; indexMsg++) {
                if (textToDraw[indexMsg].length() == 0) {
                    // If empty line, add space
                    textToDraw[indexMsg] = " ";
                }

                pictureToDraw[indexMsg] = this.pictureCache.getTextManager()
                        .createSmallText(textToDraw[indexMsg],
                                this.conf.getTextColor(),
                                TextManager.BACKGROUND_COLOR_NONE);

                heightOfMessages += pictureToDraw[indexMsg].getHeight();
            }

            // Compute Y to start draw
            int startY = textAreaConf.getY()
                    + ((textAreaConf.getHeight() - heightOfMessages) / 2);

            int textAreaWidth = textAreaConf.getWidth();
            int textAreaX = textAreaConf.getX();

            // Draw text and center it
            for (BufferedImage pictureToDraw1 : pictureToDraw) {
                this.g2BoxPicture.drawImage(pictureToDraw1, textAreaX + ((textAreaWidth - pictureToDraw1.getWidth()) / 2), startY, null);
                startY += pictureToDraw1.getHeight();
            }
        }
    }
}
