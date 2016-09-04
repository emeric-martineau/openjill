package org.jill.game.screen;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jill.game.screen.conf.ControlAreaConf;
import org.jill.game.screen.conf.LineToDraw;
import org.jill.game.screen.conf.TextToDraw;
import org.jill.openjill.core.api.manager.
        TextManager;
import org.jill.openjill.core.api.manager.
        TileManager;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.
        InterfaceMessageGameHandler;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryItemMessage;

/**
 * Control area on screen and manage state.
 *
 * @author Emeric MARTINEAU
 */
public class ControlArea implements InterfaceMessageGameHandler {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(
                    ControlArea.class.getName());

    /**
     * Key for empty string.
     */
    private static final String EMPTY_ALT_TEXT = "empty";

    /**
     * To find alt key text.
     */
    private static final String ALT_TEXT_KEY = "-alt-";

    /**
     * Key for empty string.
     */
    private static final String EMPTY_CTRL_TEXT = "empty";

    /**
     * To find ctrl key text.
     */
    private static final String CTRL_TEXT_KEY = "-ctrl-";

    /**
     * Shift.
     */
    private static final String SHIFT = "SHIFT";

    /**
     * Alt.
     */
    private static final String ALT = "ALT";

    /**
     * F1.
     */
    private static final String F1 = "F1";

    /**
     * Picture cache.
     */
    private final TileManager pictureCache;

    /**
     * Control picture.
     */
    private final BufferedImage controlPicture;

    /**
     * Graphic object to draw control.
     */
    private final Graphics2D g2Control;

    /**
     * Bullet on.
     */
    private final BufferedImage bulletOn;

    /**
     * Bullet off.
     */
    private final BufferedImage bulletOff;

    /**
     * Background color for inventory/control area.
     */
    private final Color inventoryBackgroundColor;

    /**
     * Alt key text.
     */
    private final TextToDraw altKey;

    /**
     * Alt key text.
     */
    private final TextToDraw ctrlKey;

    /**
     * Turtle mode.
     */
    private boolean turtleMode = false;

    /**
     * Noise mode.
     */
    private boolean noiseMode = false;

    /**
     * Configuration.
     */
    private final ControlAreaConf conf;

    /**
     * Read config file.
     *
     * @param filename final name of config file
     *
     * @return properties file
     */
    private static ControlAreaConf readConf(final String filename) {

        final ObjectMapper mapper = new ObjectMapper();
        final InputStream is =
                ControlArea.class.getClassLoader().
                        getResourceAsStream(filename);

        ControlAreaConf mc;

        // Load menu
        try {
            mc = mapper.readValue(is, ControlAreaConf.class);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE,
                String.format("Unable to load config for control '%s'",
                        filename),
                ex);

            mc = null;
        }

        return mc;
    }

    /**
     * Constructor.
     *
     * @param pictureCacheManager picture cache
     * @param statusBar status bar
     */
    public ControlArea(final TileManager pictureCacheManager,
            final StatusBar statusBar) {
        this.conf = readConf("control_area.json");

        this.pictureCache = pictureCacheManager;

        inventoryBackgroundColor = pictureCache.getColorMap()[
                conf.getBackgroundColor()];

        bulletOn = pictureCache.getTextManager().grapSpecialKey(
                TextManager.SPECIAL_BULLET_ON,
                this.conf.getNoiseBullet().getColor(),
                TextManager.BACKGROUND_COLOR_NONE);
        bulletOff = pictureCache.getTextManager().grapSpecialKey(
                TextManager.SPECIAL_BULLET_OFF,
                this.conf.getTurtleBullet().getColor(),
                TextManager.BACKGROUND_COLOR_NONE);

        TextToDraw lAltKey = null;

        for (TextToDraw ttd : this.conf.getText()) {
            if (ALT_TEXT_KEY.equals(ttd.getText())) {
                lAltKey = ttd;
                ttd.setText(this.conf.getAltKeyText().get(EMPTY_ALT_TEXT));
                break;
            }
        }

        this.altKey = lAltKey;

        for (TextToDraw ttd : this.conf.getText()) {
            if (CTRL_TEXT_KEY.equals(ttd.getText())) {
                lAltKey = ttd;
                ttd.setText(this.conf.getCtrlKeyText().get(EMPTY_CTRL_TEXT));
                break;
            }
        }

        this.ctrlKey = lAltKey;

        controlPicture = statusBar.createControlArea();
        g2Control = controlPicture.createGraphics();
    }

    /**
     * Draw control panel.
     *
     * @return picture
     */
    public final BufferedImage drawControl() {
        // Draw background and clear
        g2Control.setColor(inventoryBackgroundColor);
        g2Control.fillRect(0, 0, controlPicture.getWidth(),
                controlPicture.getHeight());

        for (TextToDraw ttd : this.conf.getText()) {
            pictureCache.getTextManager().drawSmallText(g2Control, ttd.getX(),
                    ttd.getY(), ttd.getText(), ttd.getColor(),
                TextManager.BACKGROUND_COLOR_NONE);
        }

        for (TextToDraw ttd : this.conf.getBigText()) {
            pictureCache.getTextManager().drawBigText(g2Control, ttd.getX(),
                    ttd.getY(), ttd.getText(), ttd.getColor(),
                TextManager.BACKGROUND_COLOR_NONE);
        }

        BufferedImage picture;
        int index;

        for (TextToDraw ttd : this.conf.getSpecialKey()) {
            switch (ttd.getText()) {
                case SHIFT:
                    index = TextManager.SPECIAL_KEY_SHIFT;
                    break;
                case ALT:
                    index = TextManager.SPECIAL_KEY_ALT;
                    break;
                case F1:
                    index = TextManager.SPECIAL_KEY_F1;
                    break;
                default:
                    index = 0;
            }

            picture = pictureCache.getTextManager().grapSpecialKey(
                        index, ttd.getColor(),
                        TextManager.BACKGROUND_COLOR_NONE);

            g2Control.drawImage(picture, ttd.getX(), ttd.getY(), null);
        }

        // Draw middle line
        for (LineToDraw ltd : this.conf.getLines()) {
            g2Control.setColor(pictureCache.getColorMap()[ltd.getColor()]);
            g2Control.fillRect(0, ltd.getY(), controlPicture.getWidth(), 1);
        }

        BufferedImage bi;

        // Draw bullet
        if (turtleMode) {
            bi = bulletOn;
        } else {
            bi = bulletOff;
        }

        TextToDraw bullet = this.conf.getTurtleBullet();

        g2Control.drawImage(bi, bullet.getX(), bullet.getY(), null);

        if (noiseMode) {
            bi = bulletOn;
        } else {
            bi = bulletOff;
        }

        bullet = this.conf.getNoiseBullet();
        g2Control.drawImage(bi, bullet.getX(), bullet.getY(), null);

        return controlPicture;
    }

    /**
     * Turtule mode.
     *
     * @return turtleMode
     */
    public final boolean isTurtleMode() {
        return turtleMode;
    }

    /**
     * Turtule mode.
     *
     * @param turtle turtleMode
     */
    public final void setTurtleMode(final boolean turtle) {
        this.turtleMode = turtle;
    }

    /**
     * Noise mode.
     *
     * @return noiseMode
     */
    public final boolean isNoiseMode() {
        return noiseMode;
    }

    /**
     * Noise mode.
     *
     * @param noise noiseMode
     */
    public final void setNoiseMode(final boolean noise) {
        this.noiseMode = noise;
    }

    @Override
    public final void recieveMessage(final EnumMessageType type,
        final Object msg) {
        switch(type) {
            case INVENTORY_ITEM :
                final InventoryItemMessage iim = (InventoryItemMessage) msg;

                if (iim.isLastItemInv()) {
                    // clear text when not more item in list
                    this.altKey.setText(
                            this.conf.getAltKeyText().get(EMPTY_ALT_TEXT));

                    this.ctrlKey.setText(
                            this.conf.getCtrlKeyText().get(EMPTY_CTRL_TEXT));
                } else {
                    configureTextElement(iim, this.conf.getAltKeyText(),
                            this.altKey);
                    configureTextElement(iim, this.conf.getCtrlKeyText(),
                            this.ctrlKey);
                }

                break;
            default:
                // Nothing
        }
    }

    /**
     * Configure text element.
     *
     * @param iim inventory message
     * @param texts map of text
     * @param textDestination text to draw
     */
    private void configureTextElement(final InventoryItemMessage iim,
            final Map<String, String> texts, final TextToDraw textDestination) {
        final String txt = texts.get(
                iim.getObj().toString());

        if (txt != null) {
            // If text found
            textDestination.setText(txt);
        }
    }
}
