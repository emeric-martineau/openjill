package org.jill.game.gui.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jill.cfg.HighScoreItem;
import org.jill.game.gui.menu.conf.HighScoreMenuConf;
import org.jill.game.gui.tools.LimitedString;
import org.jill.game.screen.conf.LineToDraw;
import org.jill.game.screen.conf.TextToDraw;
import org.jill.openjill.core.api.manager.TextManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * Display.
     */
    private final BufferedImage highScoreScreen;

    /**
     * List of high score.
     */
    private final List<HighScoreItem> listHiScore;

    /**
     * Name of high score.
     */
    private final LimitedString nameHighScore = new LimitedString(
            HighScoreItem.LEN_HIGHSCORE_NAME);

    /**
     * Configuration.
     */
    private final HighScoreMenuConf conf;

    /**
     * If menu is enable.
     */
    private boolean enable = false;

    /**
     * title.
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
     * To know if update hight score.
     */
    private boolean updateNameHighScore = false;

    /**
     * Constructor.
     *
     * @param highScorePicture    object to draw high score
     * @param textManager cache of picture
     * @param hiScoreList         list of high score (can be modified !)
     * @param positionToDrawMenuX x to draw
     * @param positionToDrawMenuY y to draw
     */
    public HighScoreMenu(final BufferedImage highScorePicture,
            final TextManager textManager,
            final List<HighScoreItem> hiScoreList,
            final int positionToDrawMenuX, final int positionToDrawMenuY) {
        this(highScorePicture, textManager, hiScoreList,
                positionToDrawMenuX,
                positionToDrawMenuY, Optional.empty());
    }

    /**
     * Constructor.
     *
     * @param highScorePicture    object to draw high score
     * @param textManager cache of picture
     * @param hiScoreList         list of high score (can be modified !)
     * @param positionToDrawMenuX x to draw
     * @param positionToDrawMenuY y to draw
     * @param nextMenuObject      next menu to draw
     */
    public HighScoreMenu(final BufferedImage highScorePicture,
            final TextManager textManager,
            final List<HighScoreItem> hiScoreList,
            final int positionToDrawMenuX, final int positionToDrawMenuY,
            final Optional<MenuInterface> nextMenuObject) {
        super(textManager, nextMenuObject);

        conf = readConf1("high_score_menu.json");

        highScoreScreen = highScorePicture;
        listHiScore = hiScoreList;

        final TextToDraw titleConf = conf.getText().get(0);

        title = new SubMenu(titleConf.getColor(), titleConf.getText());

        backgroundColor = conf.getReadonlymode().getBackgroundColor();

        currentMenuPos = 0;
        cursorPositionBySubMenuIndex.add(new Point(0, 0));

        setX(positionToDrawMenuX);
        setY(positionToDrawMenuY);

        drawPicture();
    }

    /**
     * Read config file.
     *
     * @param filename final name of config file
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

            throw new RuntimeException(ex);
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
                ptoDraw.setLocation(conf.getStartCursor().getX(),
                        ptoDraw.y);
            } else {
                writeCurrentHighScoreNameEnterByPlayer(name, g2, ptoDraw);
            }

            updateNameHighScore = false;
        }

        drawCursor(g2, ptoDraw);

        g2.dispose();

        return highScoreScreen;
    }

    /**
     * Erase line.
     *
     * @param g2      Graphic2D of player list
     * @param ptoDraw current position of cursor
     */
    private void eraseLine(final Graphics2D g2, final Point ptoDraw) {
        // Clear all letter and cursor
        final int cursorWidth = oldCursorBackground.getWidth();
        final int maxLenText = nameHighScore.getCapacity() + 1;

        for (int index = 0; index < maxLenText; index++) {
            g2.drawImage(oldCursorBackground, null,
                    conf.getStartCursor().getX() + index * cursorWidth,
                    ptoDraw.y);
        }
    }

    /**
     * Write current name of high score enter by player in key board.
     *
     * @param name    palyer name
     * @param g2      Graphic2D of player list
     * @param ptoDraw current position of cursor
     */
    private void writeCurrentHighScoreNameEnterByPlayer(final String name,
            final Graphics2D g2,
            final Point ptoDraw) {
        BufferedImage imageName = textManager.createSmallText(
                name, conf.getEditmode().getTextColor(),
                conf.getEditmode().getBackgroundColor());

        g2.drawImage(imageName, null, conf.getStartCursor().getX(),
                ptoDraw.y);

        ptoDraw.setLocation(conf.getStartCursor().getX()
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
        return enable;
    }

    @Override
    public void setEnable(final boolean en) {
        enable = en;
    }

    @Override
    public Optional<SubMenu> getTitle() {
        return Optional.of(title);
    }

    @Override
    public void setTitle(final SubMenu ttl) {
        title = ttl;
    }

    @Override
    public void addItem(final SubMenu item) {
        // Nothing
    }

    /**
     * Draw picture.
     */
    private void drawPicture() {
        final Graphics2D g2 = highScoreScreen.createGraphics();

        // draw background
        g2.setColor(textManager.getColorMap()[backgroundColor]);
        g2.fillRect(0, 0, highScoreScreen.getWidth(),
                highScoreScreen.getHeight());

        copyBackgroundCursor(highScoreScreen);

        for (TextToDraw ttd : conf.getText()) {
            textManager.drawSmallText(g2, ttd.getX(),
                    ttd.getY(), ttd.getText(), ttd.getColor(),
                    TextManager.BACKGROUND_COLOR_NONE);
        }

        // Draw middle line
        for (LineToDraw ltd : conf.getLines()) {
            g2.setColor(textManager.getColorMap()[ltd.getColor()]);
            g2.fillRect(0, ltd.getY(), highScoreScreen.getWidth(), 1);
        }

        // Draw name of high score
        int y = conf.getStartText().getY();
        BufferedImage imageName;
        String name;

        // To know if line jump already done
        boolean jumpLineCheck = true;

        for (HighScoreItem hiScoreItem : listHiScore) {
            name = hiScoreItem.getName();

            if (name.isEmpty()) {
                name = " ";
            }

            imageName = textManager.createSmallText(
                    name, conf.getReadonlymode().getTextColor(),
                    TextManager.BACKGROUND_COLOR_NONE);

            if (jumpLineCheck && editMode
                    && (hiScoreItem.getScore() < score)) {
                // Jump line
                jumpLineCheck = false;

                cursorPositionBySubMenuIndex.get(currentMenuPos
                ).setLocation(conf.getStartCursor().getX(), y);

                y += imageName.getHeight() + conf.getTextInterSpace();
            }

            g2.drawImage(imageName, null, conf.getStartText().getX(), y);

            imageName = textManager.createSmallNumber(
                    hiScoreItem.getScore(),
                    conf.getReadonlymode().getNumberColor(),
                    TextManager.BACKGROUND_COLOR_NONE);

            g2.drawImage(imageName, null,
                    NUMBER_MAXIMUM_POSITION_X - imageName.getWidth(), y);

            y += imageName.getHeight() + conf.getTextInterSpace();
        }

        if (editMode && jumpLineCheck) {
            // No new entry found to enter score
            editMode = false;
        }

        g2.dispose();
    }

    /**
     * Switch to edit mode.
     *
     * @param scr score to know where place cursor
     */
    public void setModeEdit(final int scr) {
        editMode = true;
        score = scr;
        backgroundColor = conf.getEditmode().getBackgroundColor();

        drawPicture();
    }

    /**
     * Switch to read mode.
     */
    public void setModeRead() {
        editMode = false;
        backgroundColor = conf.getReadonlymode().getBackgroundColor();

        drawPicture();
    }

    /**
     * To know if we arre in edit mode.
     *
     * @return true/false
     */
    public boolean isEditorMode() {
        return editMode;
    }

    @Override
    public boolean keyEvent(final char consumeOtherKey) {
        nameHighScore.add(consumeOtherKey);

        updateNameHighScore = true;

        return false;
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
     * @param g2 graphic object to draw
     */
    @Override
    public void draw(final Graphics g2) {
        g2.drawImage(getPicture(), getX(),
                getY(), null);

        if (getPreviousMenu().isPresent()) {
            getPreviousMenu().get().draw(g2);
        }
    }

    @Override
    public void left() {
        up();
    }

    @Override
    public void right() {
        down();
    }
}
