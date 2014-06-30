package org.jill.game.gui.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jill.cfg.HighScoreItem;
import org.jill.game.gui.menu.conf.HighScoreMenuConf;
import org.jill.game.gui.tools.LimitedString;
import org.jill.game.screen.conf.LineToDraw;
import org.jill.game.screen.conf.TextToDraw;
import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.manager.TileManager;

/**
 * Class to display/edit high score.
 *
 * @author Emeric MARTINEAU
 */
public final class HighScoreMenu extends AbstractMenu implements MenuInterface {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(
                    HighScoreMenu.class.getName());

    /**
     * Maximum position of X.
     */
    private static final int NUMBER_MAXIMUM_POSITION_X = 62;

    /**
     * If menu is enable.
     */
    private boolean enable = false;

    /**
     * Display.
     */
    private BufferedImage highScoreScreen;

    /**
     * Picutre cache.
     */
    private TileManager pictureCache;

    /**
     * List of high score.
     */
    private List<HighScoreItem> listHiScore;

    /**
     * titile.
     */
    private SubMenu title;

    /**
     * Background color (current).
     */
    private int backgroundColor;

    /**
     * To know if when are in edit mode.
     */
    private boolean editMode = false;

    /**
     * Score.
     */
    private int score;

    /**
     * Name of high score.
     */
    private final LimitedString nameHighScore = new LimitedString(
        HighScoreItem.LEN_HIGHSCORE_NAME);

    /**
     * To know if update hight score.
     */
    private boolean updateNameHighScore = false;

    /**
     * Configuration.
     */
    private HighScoreMenuConf conf;

    /**
     * Constructor.
     *
     * @param highScorePicture object to draw high score
     * @param pictureCacheManager cache of picture
     * @param hiScoreList list of high score (can be modified !)
     * @param positionToDrawMenuX x to draw
     * @param positionToDrawMenuY y to draw
     */
    public HighScoreMenu(final BufferedImage highScorePicture,
        final TileManager pictureCacheManager,
        final List<HighScoreItem> hiScoreList,
        final int positionToDrawMenuX, final int positionToDrawMenuY) {
        this(highScorePicture, pictureCacheManager, hiScoreList,
            positionToDrawMenuX,
            positionToDrawMenuY, null);
    }

    /**
     * Constructor.
     *
     * @param highScorePicture object to draw high score
     * @param pictureCacheManager cache of picture
     * @param hiScoreList list of high score (can be modified !)
     * @param positionToDrawMenuX x to draw
     * @param positionToDrawMenuY y to draw
     * @param nextMenuObject next menu to draw
     */
    public HighScoreMenu(final BufferedImage highScorePicture,
        final TileManager pictureCacheManager,
        final List<HighScoreItem> hiScoreList,
        final int positionToDrawMenuX, final int positionToDrawMenuY,
        final MenuInterface nextMenuObject) {
        super(pictureCacheManager, nextMenuObject);

        this.conf = readConf1("high_score_menu.json");

        this.highScoreScreen = highScorePicture;
        this.pictureCache = pictureCacheManager;
        this.listHiScore = hiScoreList;

        final TextToDraw titleConf = this.conf.getText().get(0);

        this.title = new SubMenu(titleConf.getColor(), titleConf.getText());

        this.backgroundColor = this.conf.getReadonlymode().getBackgroundColor();

        this.currentMenuPos = 0;
        this.cursorPositionBySubMenuIndex.add(new Point(0, 0));

        this.positionToDrawMenuX = positionToDrawMenuX;
        this.positionToDrawMenuY = positionToDrawMenuY;

        drawPicture();
    }

    /**
     * Read config file.
     *
     * @param filename final name of config file
     *
     * @return properties file
     */
    private static HighScoreMenuConf readConf1(final String filename) {

        final ObjectMapper mapper = new ObjectMapper();
        final InputStream is =
                HighScoreMenu.class.getClassLoader().
                        getResourceAsStream(filename);

        HighScoreMenuConf mc;

        // Load menu
        try {
            mc = mapper.readValue(is, HighScoreMenuConf.class);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE,
                String.format("Unable to load config for highscore '%s'",
                        filename),
                ex);

            mc = null;
        }

        return mc;
    }

    @Override
    public void clearAllItems() {
        // Nothing
    }

    @Override
    public BufferedImage getPicture() {
        String name = nameHighScore.toString();

        final Point ptoDraw = cursorPositionBySubMenuIndex.get(currentMenuPos);
        final Graphics2D g2 = highScoreScreen.createGraphics();

        if (updateNameHighScore) {
            // Erase line if update line need
            eraseLine(g2, ptoDraw);

            if (name.isEmpty()) {
                // Replace cursor
                ptoDraw.setLocation(this.conf.getStartCursor().getX(),
                        ptoDraw.y);
            } else {
                writeCurrentHighScoreNameEnterByPlayer(name, g2, ptoDraw);
            }

            updateNameHighScore = false;
        }

        drawCursor(g2, ptoDraw, highScoreScreen);

        g2.dispose();

        return highScoreScreen;
    }

    /**
     * Erase line.
     *
     * @param g2 Graphic2D of player list
     * @param ptoDraw current position of cursor
     */
    private void eraseLine(final Graphics2D g2, final Point ptoDraw) {
        // Clear all letter and cursor
        final int cursorWidth = oldCursorBackground.getWidth();
        final int maxLenText = nameHighScore.getCapacity() + 1;

        for (int index = 0; index < maxLenText; index++) {
            g2.drawImage(oldCursorBackground, null,
                this.conf.getStartCursor().getX() + index * cursorWidth,
                ptoDraw.y);
        }
    }

    /**
     * Write current name of high score enter by player in key board.
     *
     * @param name palyer name
     * @param g2 Graphic2D of player list
     * @param ptoDraw current position of cursor
     */
    private void writeCurrentHighScoreNameEnterByPlayer(final String name,
        final Graphics2D g2,
        final Point ptoDraw) {
        BufferedImage imageName = pictureCache.getTextManager().createSmallText(
            name, this.conf.getEditmode().getTextColor(),
            this.conf.getEditmode().getBackgroundColor());

        g2.drawImage(imageName, null, this.conf.getStartCursor().getX(),
                ptoDraw.y);

        ptoDraw.setLocation(this.conf.getStartCursor().getX()
                + imageName.getWidth(), ptoDraw.y);
    }

    @Override
    public void up() {
        // Nothing
    }

    @Override
    public void down() {
        // Nothing
    }

    @Override
    public int getCursorValue() {
        // Nothing
        return 0;
    }

    @Override
    public boolean isEnable() {
        return this.enable;
    }

    @Override
    public void setEnable(final boolean en) {
        this.enable = en;
    }

    @Override
    public SubMenu getTitle() {
        return title;
    }

    @Override
    public void setTitle(final SubMenu ttl) {
        this.title = ttl;
    }

    @Override
    public void addItem(final SubMenu item) {
        // Nothing
    }

    /**
     * Draw picture.
     */
    private void drawPicture() {
        final Graphics2D g2 = this.highScoreScreen.createGraphics();

        // draw background
        g2.setColor(this.pictureCache.getColorMap()[this.backgroundColor]);
        g2.fillRect(0, 0, this.highScoreScreen.getWidth(),
            this.highScoreScreen.getHeight());

        copyBackgroundCursor(this.highScoreScreen);

        for (TextToDraw ttd : this.conf.getText()) {
            this.pictureCache.getTextManager().drawSmallText(g2, ttd.getX(),
                    ttd.getY(), ttd.getText(), ttd.getColor(),
                TextManager.BACKGROUND_COLOR_NONE);
        }

        // Draw middle line
        for (LineToDraw ltd : this.conf.getLines()) {
            g2.setColor(this.pictureCache.getColorMap()[ltd.getColor()]);
            g2.fillRect(0, ltd.getY(), this.highScoreScreen.getWidth(), 1);
        }

        // Draw name of high score
        int y = this.conf.getStartText().getY();
        BufferedImage imageName;
        String name;

        // To know if line jump already done
        boolean jumpLineCheck = true;

        for (HighScoreItem hiScoreItem : this.listHiScore) {
            name = hiScoreItem.getName();

            if (name.isEmpty()) {
                name = " ";
            }

            imageName = this.pictureCache.getTextManager().createSmallText(
                name, this.conf.getReadonlymode().getTextColor(),
                TextManager.BACKGROUND_COLOR_NONE);

            if (jumpLineCheck && this.editMode
                && (hiScoreItem.getScore() < this.score)) {
                // Jump line
                jumpLineCheck = false;

                cursorPositionBySubMenuIndex.get(currentMenuPos
                ).setLocation(this.conf.getStartCursor().getX(), y);

                y += imageName.getHeight() + this.conf.getTextInterSpace();
            }

            g2.drawImage(imageName, null, this.conf.getStartText().getX(), y);

            imageName = pictureCache.getTextManager().createSmallNumber(
                hiScoreItem.getScore(),
                this.conf.getReadonlymode().getNumberColor(),
                TextManager.BACKGROUND_COLOR_NONE);

            g2.drawImage(imageName, null,
                NUMBER_MAXIMUM_POSITION_X - imageName.getWidth(), y);

            y += imageName.getHeight() + this.conf.getTextInterSpace();
        }

        if (this.editMode && jumpLineCheck) {
            // No new entry found to enter score
            this.editMode = false;
        }

        g2.dispose();
    }

    /**
     * Switch to edit mode.
     *
     * @param scr score to know where place cursor
     */
    public void setModeEdit(final int scr) {
        this.editMode = true;
        this.score = scr;
        this.backgroundColor = this.conf.getEditmode().getBackgroundColor();

        drawPicture();
    }

    /**
     * Switch to read mode.
     */
    public void setModeRead() {
        this.editMode = false;
        this.backgroundColor = this.conf.getReadonlymode().getBackgroundColor();

        drawPicture();
    }

    /**
     * To know if we arre in edit mode.
     *
     * @return true/false
     */
    public boolean isEditorMode() {
        return this.editMode;
    }

    @Override
    public void keyEvent(final char consumeOtherKey) {
        nameHighScore.add(consumeOtherKey);

        updateNameHighScore = true;
    }

    /**
     * Return current name of player that have enter in keyboard.
     *
     * @return hi score name
     */
    public String getNameHighScore() {
        return nameHighScore.toString();
    }

    /**
     * Draw menu.
     *
     * @param g2
     */
    @Override
    public void draw(final Graphics g2) {
        g2.drawImage(getPicture(), this.positionToDrawMenuX,
            this.positionToDrawMenuY, null);

        final MenuInterface previousMenu = this.getPreviousMenu();

        if (previousMenu != null) {
            previousMenu.draw(g2);
        }
    }
}
