package org.jill.game.gui.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jill.cfg.SaveGameItem;
import org.jill.game.gui.menu.conf.LoadSaveGameMenuConf;
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
 * Class to load game.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractLoadGameMenu extends AbstractMenu
        implements MenuInterface {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(
            AbstractLoadGameMenu.class.getName());
    /**
     * Format of string format.
     */
    private static final String SAVE_DISPLAY_FORMAT
            = "%d %-" + SaveGameItem.LEN_SAVE_NAME + "s";

    /**
     * Display.
     */
    private final BufferedImage saveGameScreen;

    /**
     * List of high score.
     */
    private final List<SaveGameItem> listSaveGame;

    /**
     * Background color (current).
     */
    private final int backgroundColor;

    /**
     * Configuration.
     */
    private final LoadSaveGameMenuConf conf;

    /**
     * If menu is enable.
     */
    private boolean enable = false;

    /**
     * To know if when are in edit mode.
     */
    private boolean editMode = false;

    /**
     * If picture need to redraw.
     */
    private boolean needToDrawPicture;

    /**
     * Previous menu picture.
     */
    private BufferedImage previousMenuPicture;

    /**
     * Constructor.
     *
     * @param menuScreen          object to draw high score
     * @param textManager cache of picture
     * @param saveGameList        list of high score (can be modified !)
     * @param positionToDrawMenuX x to draw
     * @param positionToDrawMenuY y to draw
     */
    public AbstractLoadGameMenu(final BufferedImage menuScreen,
            final TextManager textManager,
            final List<SaveGameItem> saveGameList,
            final int positionToDrawMenuX,
            final int positionToDrawMenuY) {
        this(menuScreen, textManager, saveGameList,
                positionToDrawMenuX,
                positionToDrawMenuY, Optional.empty());
    }

    /**
     * Constructor.
     *
     * @param menuScreen          object to draw high score
     * @param textManager cache of picture
     * @param saveGameList        list of high score (can be modified !)
     * @param positionToDrawMenuX x to draw
     * @param positionToDrawMenuY y to draw
     * @param nextMenuObj         next menu to draw
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AbstractLoadGameMenu(final BufferedImage menuScreen,
            final TextManager textManager,
            final List<SaveGameItem> saveGameList,
            final int positionToDrawMenuX,
            final int positionToDrawMenuY,
            final Optional<MenuInterface> nextMenuObj) {
        super(textManager, nextMenuObj);

        conf = readConf1(getConfigFileName());

        saveGameScreen = menuScreen;
        this.textManager = textManager;
        listSaveGame = saveGameList;

        backgroundColor = conf.getReadonlymode().getBackgroundColor();

        currentMenuPos = 0;
        needToDrawPicture = true;

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
    private static LoadSaveGameMenuConf readConf1(final String filename) {

        final ObjectMapper mapper = new ObjectMapper();
        final InputStream is =
                AbstractLoadGameMenu.class.getClassLoader().
                        getResourceAsStream(filename);

        LoadSaveGameMenuConf mc;

        // Load menu
        try {
            mc = mapper.readValue(is, LoadSaveGameMenuConf.class);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE,
                    String.format("Unable to load config for highscore '%s'",
                            filename),
                    ex);

            throw new RuntimeException(ex);
        }

        return mc;
    }

    /**
     * Return name of file configuration.
     *
     * @return file name (json format)
     */
    protected abstract String getConfigFileName();

    /**
     * Clear all item.
     */
    @Override
    public final void clearAllItems() {
        // Nothing
    }

    @Override
    public BufferedImage getPicture() {
        if (needToDrawPicture) {
            drawPicture();
            copyBackgroundCursor(saveGameScreen);
            needToDrawPicture = false;
        }

        drawCursor(saveGameScreen);

        return saveGameScreen;
    }

    @Override
    public final int getCursorValue() {
        // Nothing
        return 0;
    }

    @Override
    public final boolean isEnable() {
        return enable;
    }

    @Override
    public void setEnable(final boolean en) {
        // If parent, take picture
        if (getPreviousMenu().isPresent()) {
            previousMenuPicture = getPreviousMenu().get().getPicture();
        }

        enable = en;
    }

    @Override
    public final Optional<SubMenu> getTitle() {
        return Optional.empty();
    }

    @Override
    public final void setTitle(final SubMenu ttl) {
    }

    @Override
    public void addItem(final SubMenu it) {
        // Nothing
    }

    /**
     * Draw picture.
     */
    private void drawPicture() {
        final Graphics2D g2 = saveGameScreen.createGraphics();

        // draw background
        g2.setColor(textManager.getColorMap()[backgroundColor]);
        g2.fillRect(0, 0, saveGameScreen.getWidth(),
                saveGameScreen.getHeight());

        for (TextToDraw ttd : conf.getText()) {
            textManager.drawSmallText(g2, ttd.getX(),
                    ttd.getY(), ttd.getText(), ttd.getColor(),
                    TextManager.BACKGROUND_COLOR_NONE);
        }

        // Draw middle line
        for (LineToDraw ltd : conf.getLines()) {
            g2.setColor(textManager.getColorMap()[ltd.getColor()]);
            g2.fillRect(0, ltd.getY(), saveGameScreen.getWidth(), 1);
        }

        // Draw name of save
        int y = conf.getStartText().getY();
        BufferedImage imageName;
        String name;

        final boolean addCursorList
                = cursorPositionBySubMenuIndex.isEmpty();

        int indexSave = 1;

        for (SaveGameItem saveGameItem : listSaveGame) {
            name = saveGameItem.getName();

            if (name.isEmpty()) {
                name = conf.getEmptyName();
            }

            name = String.format(SAVE_DISPLAY_FORMAT, indexSave, name);

            imageName = textManager.createSmallText(
                    name, conf.getReadonlymode().getTextColor(),
                    TextManager.BACKGROUND_COLOR_NONE);

            y += imageName.getHeight() + 2;

            g2.drawImage(imageName, null, conf.getStartText().getX(), y);

            // Init cursor position if ot yet done
            if (addCursorList) {
                cursorPositionBySubMenuIndex.add(
                        new Point(conf.getStartCursor().getX(), y));
            }

            indexSave++;
        }

        g2.dispose();
    }

    /**
     * To know if we arre in edit mode.
     *
     * @return enable
     */
    public final boolean isEditorMode() {
        return editMode;
    }

    /**
     * Enable keyboard grap.
     *
     * @param mode edit mode
     */
    public void setEditorMode(final boolean mode) {
        editMode = mode;
        needToDrawPicture = true;
    }

    @Override
    public final void up() {
        if (!editMode && currentMenuPos > 0) {
            eraseCursor(saveGameScreen);

            currentMenuPos--;

            copyBackgroundCursor(saveGameScreen);
        }
    }

    @Override
    public final void down() {
        if (!editMode
                && currentMenuPos < (listSaveGame.size() - 1)) {
            eraseCursor(saveGameScreen);

            currentMenuPos++;

            copyBackgroundCursor(saveGameScreen);
        }
    }

    @Override
    public void draw(final Graphics g2) {
        g2.drawImage(getPicture(), getX(),
                getY(), null);

        if (getPreviousMenu().isPresent()) {
            g2.drawImage(previousMenuPicture, getPreviousMenu().get().getX(),
                    getPreviousMenu().get().getY(), null);
        }
    }

    @Override
    public boolean keyEvent(final char consumeOtherKey) {
        if (editMode) {
            return false;
        } else {
            switch (consumeOtherKey) {
                case '0':
                    currentMenuPos = 0;
                    break;
                case '1':
                    currentMenuPos = 1;
                    break;
                case '2':
                    currentMenuPos = 2;
                    break;
                case '3':
                    currentMenuPos = 3;
                    break;
                case '4':
                    currentMenuPos = 4;
                    break;
                case '5':
                    currentMenuPos = 5;
                    break;
                case '6':
                    currentMenuPos = 6;
                    break;
                default:
                    return false;
            }

            return true;
        }
    }

    /**
     * Return number of save choice.
     *
     * @return index of save selected
     */
    public final int getNumberSave() {
        return currentMenuPos;
    }

    /**
     * Return edit mode.
     *
     * @return edit mode
     */
    protected final boolean getEditMode() {
        return editMode;
    }

    /**
     * Return screen object to draw.
     *
     * @return draw.
     */
    protected final BufferedImage getSaveGameScreen() {
        return saveGameScreen;
    }

    /**
     * List save.
     *
     * @return list save
     */
    protected final List<SaveGameItem> getListSaveGame() {
        return listSaveGame;
    }

    /**
     * Background color.
     *
     * @return back ground color
     */
    protected final int getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Retourn configuration.
     *
     * @return configuration
     */
    protected final LoadSaveGameMenuConf getConf() {
        return conf;
    }
}
