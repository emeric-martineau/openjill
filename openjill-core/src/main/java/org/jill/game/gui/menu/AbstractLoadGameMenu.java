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
import org.jill.cfg.SaveGameItem;
import org.jill.game.gui.menu.conf.LoadSaveGameMenuConf;
import org.jill.game.screen.conf.LineToDraw;
import org.jill.game.screen.conf.TextToDraw;
import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.manager.TileManager;

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
     * If menu is enable.
     */
    private boolean enable = false;

    /**
     * Display.
     */
    private final BufferedImage saveGameScreen;

    /**
     * Picutre cache.
     */
    private final TileManager pictureCache;

    /**
     * List of high score.
     */
    private final List<SaveGameItem> listSaveGame;

    /**
     * To know if when are in edit mode.
     */
    private boolean editMode = false;

    /**
     * If picture need to redraw.
     */
    private boolean needToDrawPicture;

    /**
     * Background color (current).
     */
    private final int backgroundColor;

    /**
     * Configuration.
     */
    private final LoadSaveGameMenuConf conf;

    /**
     * Previous menu picture.
     */
    private BufferedImage previousMenuPicture;

    /**
     * Constructor.
     *
     * @param menuScreen object to draw high score
     * @param pictureCacheManager cache of picture
     * @param saveGameList list of high score (can be modified !)
     * @param positionToDrawMenuX x to draw
     * @param positionToDrawMenuY y to draw
     */
    public AbstractLoadGameMenu(final BufferedImage menuScreen,
        final TileManager pictureCacheManager,
        final List<SaveGameItem> saveGameList,
        final int positionToDrawMenuX,
        final int positionToDrawMenuY) {
        this(menuScreen, pictureCacheManager, saveGameList,
            positionToDrawMenuX,
            positionToDrawMenuY, null);
    }

    /**
     * Constructor.
     *
     * @param menuScreen object to draw high score
     * @param pictureCacheManager cache of picture
     * @param saveGameList list of high score (can be modified !)
     * @param positionToDrawMenuX x to draw
     * @param positionToDrawMenuY y to draw
     * @param nextMenuObj next menu to draw
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AbstractLoadGameMenu(final BufferedImage menuScreen,
        final TileManager pictureCacheManager,
        final List<SaveGameItem> saveGameList,
        final int positionToDrawMenuX,
        final int positionToDrawMenuY,
        final MenuInterface nextMenuObj) {
        super(pictureCacheManager, nextMenuObj);

        this.conf = readConf1(getConfigFileName());

        this.saveGameScreen = menuScreen;
        this.pictureCache = pictureCacheManager;
        this.listSaveGame = saveGameList;

        this.backgroundColor = this.conf.getReadonlymode().getBackgroundColor();

        this.currentMenuPos = 0;
        this.needToDrawPicture = true;

        setX(positionToDrawMenuX);
        setY(positionToDrawMenuY);

        drawPicture();
    }

    /**
     * Return name of file configuration.
     *
     * @return file name (json format)
     */
    protected abstract String getConfigFileName();

    /**
     * Read config file.
     *
     * @param filename final name of config file
     *
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

            mc = null;
        }

        return mc;
    }

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

        return this.saveGameScreen;
    }

    @Override
    public final int getCursorValue() {
        // Nothing
        return 0;
    }

    @Override
    public final boolean isEnable() {
        return this.enable;
    }

    @Override
    public final void setEnable(final boolean en) {
        // If parent, take picture
        if (getPreviousMenu() != null) {
            this.previousMenuPicture = getPreviousMenu().getPicture();
        }

        this.enable = en;
    }

    @Override
    public final SubMenu getTitle() {
        return null;
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
        final Graphics2D g2 = this.saveGameScreen.createGraphics();

        // draw background
        g2.setColor(pictureCache.getColorMap()[backgroundColor]);
        g2.fillRect(0, 0, this.saveGameScreen.getWidth(),
            this.saveGameScreen.getHeight());

        for (TextToDraw ttd : this.conf.getText()) {
            this.pictureCache.getTextManager().drawSmallText(g2, ttd.getX(),
                    ttd.getY(), ttd.getText(), ttd.getColor(),
                TextManager.BACKGROUND_COLOR_NONE);
        }

        // Draw middle line
        for (LineToDraw ltd : this.conf.getLines()) {
            g2.setColor(this.pictureCache.getColorMap()[ltd.getColor()]);
            g2.fillRect(0, ltd.getY(), this.saveGameScreen.getWidth(), 1);
        }

        // Draw name of save
        int y = this.conf.getStartText().getY();
        BufferedImage imageName;
        String name;

        final boolean addCursorList
            = this.cursorPositionBySubMenuIndex.isEmpty();

        int indexSave = 1;

        for (SaveGameItem saveGameItem : listSaveGame) {
            name = saveGameItem.getName();

            if (name.isEmpty()) {
                name = this.conf.getEmptyName();
            }

            name = String.format(SAVE_DISPLAY_FORMAT, indexSave, name);

            imageName = pictureCache.getTextManager().createSmallText(
                name, this.conf.getReadonlymode().getTextColor(),
                TextManager.BACKGROUND_COLOR_NONE);

            y += imageName.getHeight() + 2;

            g2.drawImage(imageName, null, this.conf.getStartText().getX(), y);

            // Init cursor position if ot yet done
            if (addCursorList) {
                this.cursorPositionBySubMenuIndex.add(
                    new Point(this.conf.getStartCursor().getX(), y));
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
        return this.editMode;
    }

    /**
     * Enable keyboard grap.
     *
     * @param mode edit mode
     */
    public void setEditorMode(final boolean mode) {
        this.editMode = mode;
        this.needToDrawPicture = true;
    }

    @Override
    public final void up() {
        if (!this.editMode && this.currentMenuPos > 0) {
            eraseCursor(this.saveGameScreen);

            this.currentMenuPos--;

            copyBackgroundCursor(this.saveGameScreen);
        }
    }

    @Override
    public final void down() {
        if (!this.editMode
                && this.currentMenuPos < (this.listSaveGame.size() - 1)) {
            eraseCursor(this.saveGameScreen);

            this.currentMenuPos++;

            copyBackgroundCursor(this.saveGameScreen);
        }
    }

    @Override
    public void draw(final Graphics g2) {
        g2.drawImage(getPicture(), getX(),
            getY(), null);

        if (this.previousMenuPicture != null) {
            g2.drawImage(this.previousMenuPicture, getPreviousMenu().getX(),
                    getPreviousMenu().getY(), null);
        }
    }

    @Override
    public boolean keyEvent(final char consumeOtherKey) {
        if (this.editMode) {
            return false;
        } else {
            switch(consumeOtherKey) {
                case '0':
                    this.currentMenuPos = 0;
                    break;
                case '1':
                    this.currentMenuPos = 1;
                    break;
                case '2':
                    this.currentMenuPos = 2;
                    break;
                case '3':
                    this.currentMenuPos = 3;
                    break;
                case '4':
                    this.currentMenuPos = 4;
                    break;
                case '5':
                    this.currentMenuPos = 5;
                    break;
                case '6':
                    this.currentMenuPos = 6;
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
        return this.currentMenuPos;
    }

    /**
     * Return edit mode.
     *
     * @return  edit mode
     */
    protected final boolean getEditMode() {
        return this.editMode;
    }

    /**
     * Return screen object to draw.
     *
     * @return draw.
     */
    protected final BufferedImage getSaveGameScreen() {
        return this.saveGameScreen;
    }

    /**
     * Return picture cache.
     *
     * @return picture cache
     */
    protected final TileManager getPictureCache() {
        return this.pictureCache;
    }

    /**
     * List save.
     *
     * @return list save
     */
    protected final List<SaveGameItem> getListSaveGame() {
        return this.listSaveGame;
    }

    /**
     * Background color.
     *
     * @return back ground color
     */
    protected final int getBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * Retourn configuration.
     *
     * @return configuration
     */
    protected final LoadSaveGameMenuConf getConf() {
        return this.conf;
    }
}
