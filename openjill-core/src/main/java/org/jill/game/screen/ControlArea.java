package org.jill.game.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jill.game.screen.conf.ControlAreaConf;
import org.jill.game.screen.conf.KeysControlText;
import org.jill.game.screen.conf.LineToDraw;
import org.jill.game.screen.conf.TextToDraw;
import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.manager.TileManager;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.InterfaceMessageGameHandler;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryItemMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

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
     * To find alt key text.
     */
    private static final String ALT_TEXT_KEY = "-alt-";

    /**
     * Key for empty string.
     */
    private static final String EMPTY_TEXT = "      ";

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
     * Shift key text.
     */
    private final TextToDraw shiftKey;

    /**
     * Configuration.
     */
    private final ControlAreaConf conf;

    /**
     * Turtle mode.
     */
    private boolean turtleMode = false;

    /**
     * Noise mode.
     */
    private boolean noiseMode = false;


    /**
     * Current player config for update text.
     */
    private KeysControlText currentPlayerConfig;

    /**
     * Constructor.
     *
     * @param pictureCacheManager picture cache
     * @param statusBar           status bar
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
                ttd.setText(EMPTY_TEXT);
                break;
            }
        }

        this.altKey = lAltKey;

        for (TextToDraw ttd : this.conf.getText()) {
            if (CTRL_TEXT_KEY.equals(ttd.getText())) {
                lAltKey = ttd;
                ttd.setText(EMPTY_TEXT);
                break;
            }
        }

        this.shiftKey = lAltKey;

        controlPicture = statusBar.createControlArea();
        g2Control = controlPicture.createGraphics();
    }

    /**
     * Read config file.
     *
     * @param filename final name of config file
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
     * Draw control panel.
     *
     * @return picture
     */
    public final BufferedImage drawControl() {
        findDefaultPlayerCharacter();

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
     * Find default player character.
     */
    private void findDefaultPlayerCharacter() {
        if (this.currentPlayerConfig == null) {
            for (KeysControlText kct : this.conf.getKeysControlText()) {
                if (kct.isDefaut()) {
                    this.currentPlayerConfig = kct;
                    this.altKey.setText(kct.getAlt());
                    this.shiftKey.setText(kct.getShift());

                    break;
                }
            }
        }
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
        switch (type) {
            case INVENTORY_ITEM:
                if (this.currentPlayerConfig == null) {
                    findDefaultPlayerCharacter();
                }

                if (this.currentPlayerConfig.isCanUpdateAltText()) {
                    updateTextWithInventory(msg);
                }
                break;
            case CHANGE_PLAYER_CHARACTER:
                final KeysControlText kct = this.conf.getKeysControlText(
                        msg.toString());

                this.shiftKey.setText(kct.getShift());
                this.altKey.setText(kct.getAlt());

                this.currentPlayerConfig = kct;

                break;
            default:
                // Nothing
        }
    }

    /**
     * Update text when receive inventory message.
     *
     * @param msg message
     */
    private void updateTextWithInventory(Object msg) {
        final InventoryItemMessage iim = (InventoryItemMessage) msg;

        if (iim.isLastItemInv()) {
            this.altKey.setText(EMPTY_TEXT);
        } else {
            final KeysControlText kct = this.conf.getKeysControlText(
                    iim.getObj().toString());

            if (kct != null) {
                this.altKey.setText(kct.getAlt());
            }
        }
    }
}
