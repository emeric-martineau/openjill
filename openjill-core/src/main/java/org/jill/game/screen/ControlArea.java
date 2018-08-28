package org.jill.game.screen;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jill.game.screen.conf.ControlAreaConf;
import org.jill.game.screen.conf.KeysControlText;
import org.jill.game.screen.conf.LineToDraw;
import org.jill.game.screen.conf.TextToDraw;
import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.InterfaceMessageGameHandler;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryItemMessage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final TextManager textManager;

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
     * @param textManager picture cache
     * @param statusBar           status bar
     */
    public ControlArea(final TextManager textManager,
            final StatusBar statusBar) {
        conf = readConf("control_area.json");

        this.textManager = textManager;

        inventoryBackgroundColor = textManager.getColorMap()[
                conf.getBackgroundColor()];

        bulletOn = textManager.grapSpecialKey(
                TextManager.SPECIAL_BULLET_ON,
                conf.getNoiseBullet().getColor(),
                TextManager.BACKGROUND_COLOR_NONE);
        bulletOff = textManager.grapSpecialKey(
                TextManager.SPECIAL_BULLET_OFF,
                conf.getTurtleBullet().getColor(),
                TextManager.BACKGROUND_COLOR_NONE);

        TextToDraw lAltKey = null;

        for (TextToDraw ttd : conf.getText()) {
            if (ALT_TEXT_KEY.equals(ttd.getText())) {
                lAltKey = ttd;
                ttd.setText(EMPTY_TEXT);
                break;
            }
        }

        altKey = lAltKey;

        for (TextToDraw ttd : conf.getText()) {
            if (CTRL_TEXT_KEY.equals(ttd.getText())) {
                lAltKey = ttd;
                ttd.setText(EMPTY_TEXT);
                break;
            }
        }

        shiftKey = lAltKey;

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

        for (TextToDraw ttd : conf.getText()) {
            textManager.drawSmallText(g2Control, ttd.getX(),
                    ttd.getY(), ttd.getText(), ttd.getColor(),
                    TextManager.BACKGROUND_COLOR_NONE);
        }

        for (TextToDraw ttd : conf.getBigText()) {
            textManager.drawBigText(g2Control, ttd.getX(),
                    ttd.getY(), ttd.getText(), ttd.getColor(),
                    TextManager.BACKGROUND_COLOR_NONE);
        }

        BufferedImage picture;
        int index;

        for (TextToDraw ttd : conf.getSpecialKey()) {
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

            picture = textManager.grapSpecialKey(
                    index, ttd.getColor(),
                    TextManager.BACKGROUND_COLOR_NONE);

            g2Control.drawImage(picture, ttd.getX(), ttd.getY(), null);
        }

        // Draw middle line
        for (LineToDraw ltd : conf.getLines()) {
            g2Control.setColor(textManager.getColorMap()[ltd.getColor()]);
            g2Control.fillRect(0, ltd.getY(), controlPicture.getWidth(), 1);
        }

        BufferedImage bi;

        // Draw bullet
        if (turtleMode) {
            bi = bulletOn;
        } else {
            bi = bulletOff;
        }

        TextToDraw bullet = conf.getTurtleBullet();

        g2Control.drawImage(bi, bullet.getX(), bullet.getY(), null);

        if (noiseMode) {
            bi = bulletOn;
        } else {
            bi = bulletOff;
        }

        bullet = conf.getNoiseBullet();
        g2Control.drawImage(bi, bullet.getX(), bullet.getY(), null);

        return controlPicture;
    }

    /**
     * Find default player character.
     */
    private void findDefaultPlayerCharacter() {
        if (currentPlayerConfig == null) {
            for (KeysControlText kct : conf.getKeysControlText()) {
                if (kct.isDefaut()) {
                    currentPlayerConfig = kct;
                    altKey.setText(kct.getAlt());
                    shiftKey.setText(kct.getShift());

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
        turtleMode = turtle;
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
        noiseMode = noise;
    }

    @Override
    public final void recieveMessage(final EnumMessageType type,
            final Object msg) {
        switch (type) {
            case INVENTORY_ITEM:
                if (currentPlayerConfig == null) {
                    findDefaultPlayerCharacter();
                }

                if (currentPlayerConfig.isCanUpdateAltText()) {
                    updateTextWithInventory(msg);
                }
                break;
            case CHANGE_PLAYER_CHARACTER:
                final KeysControlText kct = conf.getKeysControlText(
                        msg.toString());

                shiftKey.setText(kct.getShift());
                altKey.setText(kct.getAlt());

                currentPlayerConfig = kct;

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
            altKey.setText(EMPTY_TEXT);
        } else {
            final KeysControlText kct = conf.getKeysControlText(
                    iim.getObj().toString());

            if (kct != null) {
                altKey.setText(kct.getAlt());
            }
        }
    }
}
