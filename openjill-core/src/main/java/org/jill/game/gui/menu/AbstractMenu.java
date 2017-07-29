package org.jill.game.gui.menu;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jill.game.gui.menu.conf.MenuConf;
import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.manager.TileManager;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Abstract class for menu.
 *
 * @author Emeric MARTINEAU
 */
public class AbstractMenu {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(
            AbstractMenu.class.getName());

    /**
     * Number of image in cursor.
     */
    private static final int NB_CURSOR_IMAGE = 8;

    /**
     * Old cursor background.
     */
    protected final BufferedImage oldCursorBackground;

    /**
     * Cursor.
     */
    protected final BufferedImage[] cursors;
    /**
     * Position of cursor for each submenu.
     */
    protected final List<Point> cursorPositionBySubMenuIndex = new ArrayList<>();
    /**
     * Current position of cursor.
     */
    protected int currentMenuPos = 0;
    /**
     * Cursor index.
     */
    protected int cursorIndex = 0;

    /**
     * Position to draw menu.
     */
    private int positionToDrawMenuX;

    /**
     * Position to draw menu.
     */
    private int positionToDrawMenuY;

    /**
     * Next menu to draw.
     */
    private MenuInterface previousMenu;

    /**
     * Constructor.
     *
     * @param pictureCache    picture cache
     * @param previousMenuObj previous menu
     */
    public AbstractMenu(final TileManager pictureCache,
            final MenuInterface previousMenuObj) {

        this.previousMenu = previousMenuObj;

        final StringBuilder sb = new StringBuilder(NB_CURSOR_IMAGE);

        for (int indexImage = 1; indexImage <= NB_CURSOR_IMAGE; indexImage++) {
            sb.append((char) indexImage);
        }

        this.cursors = pictureCache.getTextManager().grapSmallLetter(
                sb.toString(),
                TextManager.COLOR_WHITE, TextManager.BACKGROUND_COLOR_NONE);

        this.oldCursorBackground = new BufferedImage(this.cursors[0].getWidth(),
                this.cursors[0].getHeight(),
                BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Erase cursor.
     *
     * @param menuPicture pictur of menu
     */
    protected final void eraseCursor(final BufferedImage menuPicture) {
        final Point cursorPos = this.cursorPositionBySubMenuIndex.get(
                this.currentMenuPos);

        eraseCursor(cursorPos, menuPicture);
    }

    /**
     * Erase cursor.
     *
     * @param cursorPos   cursor position
     * @param menuPicture picture of menu
     */
    protected final void eraseCursor(final Point cursorPos,
            final BufferedImage menuPicture) {
        // Draw new cursor position
        final Graphics2D g2 = menuPicture.createGraphics();

        // Clear old picture
        g2.drawImage(this.oldCursorBackground, cursorPos.x, cursorPos.y, null);

        g2.dispose();
    }

    /**
     * Copy background.
     *
     * @param menuPicture picture of menu
     */
    protected final void copyBackgroundCursor(final BufferedImage menuPicture) {
        final Graphics2D g2Old = this.oldCursorBackground.createGraphics();
        final Graphics2D g2 = menuPicture.createGraphics();

        final Point cursorPos = this.cursorPositionBySubMenuIndex.get(
                this.currentMenuPos);

        g2Old.drawImage(menuPicture, -1 * cursorPos.x, -1 * cursorPos.y, null);

        g2Old.dispose();
        g2.dispose();
    }

    /**
     * Draw cursor. Update cursor image at each time.
     *
     * @param menuPicture picture of menu
     */
    protected final void drawCursor(final BufferedImage menuPicture) {
        // Draw new cursor position
        final Graphics2D g2 = menuPicture.createGraphics();
        final Point cursorPos = this.cursorPositionBySubMenuIndex.get(
                this.currentMenuPos);

        drawCursor(g2, cursorPos, menuPicture);
    }

    /**
     * Draw cursor. Update cursor image at each time.
     *
     * @param g2          graphic 2d
     * @param cursorPos   cursor position
     * @param menuPicture picture of menu
     */
    protected final void drawCursor(final Graphics2D g2, final Point cursorPos,
            final BufferedImage menuPicture) {
        // Clear old picture
        g2.drawImage(this.oldCursorBackground, cursorPos.x, cursorPos.y, null);

        g2.drawImage(this.cursors[this.cursorIndex], cursorPos.x,
                cursorPos.y, null);
        this.cursorIndex++;

        if (this.cursorIndex >= this.cursors.length) {
            this.cursorIndex = 0;
        }
    }

    /**
     * Read config file.
     *
     * @param filename final name of config file
     * @return properties file
     */
    protected final MenuConf readConf(final String filename) {

        final ObjectMapper mapper = new ObjectMapper();
        final InputStream is =
                getClass().getClassLoader().getResourceAsStream(filename);

        MenuConf mc;

        // Load menu
        try {
            mc = mapper.readValue(is, MenuConf.class);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE,
                    String.format("Unable to load config for menu '%s'", filename),
                    ex);

            mc = null;
        }

        return mc;
    }

    /**
     * Read config file.
     *
     * @param filename final name of config file
     * @return properties file
     */
    protected final Properties readConfProp(final String filename) {
        final Properties conf = new Properties();

        // Load menu
        try {
            conf.load(
                    getClass().getClassLoader().getResourceAsStream(filename));
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE,
                    String.format("Unable to load config for '%s'", filename),
                    ex);
        }

        return conf;
    }

    /**
     * Read integer.
     *
     * @param conf configuration file
     * @param key  key to read
     * @return value
     */
    protected final int readInteger(final Properties conf, final String key) {
        return Integer.valueOf(conf.getProperty(key));
    }

    /**
     * Return previous menu.
     *
     * @return previous menu
     */
    public final MenuInterface getPreviousMenu() {
        return this.previousMenu;
    }

    /**
     * Return previous menu.
     *
     * @param prevMenu previous menu
     */
    public final void setPreviousMenu(final MenuInterface prevMenu) {
        this.previousMenu = prevMenu;
    }

    /**
     * Give text position to draw menu x.
     *
     * @return x
     */
    public int getX() {
        return positionToDrawMenuX;
    }

    /**
     * Set text position to draw menu x.
     *
     * @param x x
     */
    public void setX(final int x) {
        this.positionToDrawMenuX = x;
    }

    /**
     * Give text position to draw menu y.
     *
     * @return y
     */
    public int getY() {
        return positionToDrawMenuY;
    }

    /**
     * Set text position to draw menu y.
     *
     * @param y x
     */
    public void setY(final int y) {
        this.positionToDrawMenuY = y;
    }
}
