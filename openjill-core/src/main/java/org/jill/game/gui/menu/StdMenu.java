package org.jill.game.gui.menu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.manager.TileManager;

/**
 * This class is menu to display on screen.
 *
 * @author Emeric MARTINEAU
 */
public class StdMenu extends AbstractMenu implements MenuInterface {

    /**
     * Start position of text in menu.
     */
    private static final int MENU_TEXT_START_X = 13;

    /**
     * Start position of text in menu.
     */
    private static final int MENU_TEXT_START_Y = 16;

    /**
     * Nb space before.
     */
    private static final int NB_SPACE_BEFORE = 4;

    /**
     * Number of space for boder.
     */
    private static final int NB_BORDER = 5;

    /**
     * Name of menu.
     */
    private String name;

    /**
     * Title of menu.
     */
    private SubMenu title;

    /**
     * Items of menu.
     */
    private final List<SubMenu> items = new ArrayList<>();

    /**
     * If menu is enable.
     */
    private boolean enable = false;

    /**
     * Picture manager.
     */
    private final TileManager pictureCache;

    /**
     * Size of font.
     */
    private final int fontSize;

    /**
     * Size of font.
     */
    private final int fontSizeSpace;

    /**
     * Picutre.
     */
    private final BufferedImage rightUpperCorner;

    /**
     * Picutre.
     */
    private final BufferedImage leftUpperCorner;

    /**
     * Picutre.
     */
    private final BufferedImage rightLowerCorner;

    /**
     * Picutre.
     */
    private final BufferedImage leftLowerCorner;

    /**
     * Picutre.
     */
    private final BufferedImage upperBar;

    /**
     * Picutre.
     */
    private final BufferedImage lowerBar;

    /**
     * Picutre.
     */
    private final BufferedImage rightBar;

    /**
     * Picutre.
     */
    private final BufferedImage leftBar;

    /**
     * Picutre.
     */
    private final BufferedImage background;

    /**
     * Current menu image.
     */
    private BufferedImage menuPicture;

    /**
     * If picture need to redraw.
     */
    private boolean needToDrawPicture;

    /**
     * Constructeur.
     *
     * @param pictureCacheManager picture cache
     */
    public StdMenu(final TileManager pictureCacheManager) {
        super(pictureCacheManager, null);

        // Calculate font size
        fontSize = pictureCacheManager.getTextManager().createSmallText(" ",
            TextManager.COLOR_BLUE,
            TextManager.COLOR_BLUE).getHeight();

        fontSizeSpace = fontSize + 2;

        this.pictureCache = pictureCacheManager;

        rightUpperCorner = pictureCacheManager.getImage(7, 1);
        leftUpperCorner = pictureCacheManager.getImage(7, 3);
        rightLowerCorner = pictureCacheManager.getImage(7, 6);
        leftLowerCorner = pictureCacheManager.getImage(7, 8);
        upperBar = pictureCacheManager.getImage(7, 2);
        lowerBar = pictureCacheManager.getImage(7, 7);
        rightBar = pictureCacheManager.getImage(7, 4);
        leftBar = pictureCacheManager.getImage(7, 5);

        // Get image of background
        final BufferedImage backImage = pictureCacheManager.getImage(7, 9);

        // Create background image
        background = new BufferedImage(backImage.getWidth(),
            backImage.getHeight(),
            BufferedImage.TYPE_INT_ARGB);

        final Graphics2D g2 = background.createGraphics();

        // Fill background cause in CGA mode, background picture can be empty
        g2.setColor(pictureCacheManager.getBackgroundColor());
        g2.fillRect(0, 0, background.getWidth(), background.getWidth());

        // Now, draw back picture
        g2.drawImage(backImage, 0, 0, null);

        g2.dispose();

        needToDrawPicture = true;
    }

    @Override
    public void clearAllItems() {
        needToDrawPicture = true;
        items.clear();
    }

    /**
     * Get minimum size of picture.
     *
     * @return minimum size
     */
    private int calculateWidthMinimum() {
        // length title is tile
        int maximum = title.getText().length();

        int currentsize;

        for (SubMenu sub : items) {
            // 4 space before
            currentsize = sub.getText().length() + NB_SPACE_BEFORE;

            if (currentsize > maximum) {
                maximum = currentsize;
            }
        }

        return maximum;
    }

    /**
     * Draw picture.
     */
    private void drawPicture() {
        // 1 line at top, 1 line bottom, 1 line for title, + 2 border
        final int height = NB_BORDER + items.size();
        // + 1 for border
        final int width = calculateWidthMinimum() + 1;
        // Picture size
        final int pictureWidth = width * fontSizeSpace;
        final int pictureHeight = height * fontSizeSpace;

        menuPicture = new BufferedImage(pictureWidth, pictureHeight,
            BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2 = menuPicture.createGraphics();

        // Fill background
        final int widthBack = width - 1;
        final int heightBack = height - 1;
        for (int x = 1; x < widthBack; x++) {
            for (int y = 1; y < heightBack; y++) {
                g2.drawImage(background, x * fontSizeSpace,
                    y * fontSizeSpace, null);
            }
        }

        // Draw corner
        g2.drawImage(rightUpperCorner, 0, 0, null);
        g2.drawImage(leftUpperCorner, widthBack * fontSizeSpace, 0, null);
        g2.drawImage(rightLowerCorner, 0, heightBack * fontSizeSpace, null);
        g2.drawImage(leftLowerCorner, widthBack * fontSizeSpace,
            heightBack * fontSizeSpace, null);

        // Draw upper border
        for (int x = 1; x < widthBack; x++) {
            g2.drawImage(upperBar, x * fontSizeSpace, 0, null);
        }

        // Draw lower border
        final int yPos = heightBack * fontSizeSpace;

        for (int x = 1; x < widthBack; x++) {
            g2.drawImage(lowerBar, x * fontSizeSpace, yPos, null);
        }

        // Draw right border
        for (int y = 1; y < heightBack; y++) {
            g2.drawImage(rightBar, 0, y * fontSizeSpace, null);
        }

        // Draw left border
        final int xPos = widthBack * fontSizeSpace;

        for (int y = 1; y < heightBack; y++) {
            g2.drawImage(leftBar, xPos, y * fontSizeSpace, null);
        }

        // Draw title
        pictureCache.getTextManager().drawSmallText(g2, MENU_TEXT_START_X,
            MENU_TEXT_START_Y, title.getText(), title.getColor(),
            TextManager.BACKGROUND_COLOR_NONE);

        final int posCursorX = MENU_TEXT_START_X + fontSize;
        final int posTextX = MENU_TEXT_START_X + (NB_SPACE_BEFORE * fontSize);
        int posTextY = MENU_TEXT_START_Y + fontSizeSpace;

        cursorPositionBySubMenuIndex.clear();

        for (SubMenu entry : items) {
            pictureCache.getTextManager().drawSmallText(g2, posTextX, posTextY,
                entry.getText(), entry.getColor(),
                TextManager.BACKGROUND_COLOR_NONE);

            // Calculate cursor position
            cursorPositionBySubMenuIndex.add(new Point(posCursorX, posTextY));

            posTextY += fontSizeSpace;
        }

        g2.dispose();
    }

    @Override
    public BufferedImage getPicture() {
        if (needToDrawPicture) {
            drawPicture();
            copyBackgroundCursor(menuPicture);
            needToDrawPicture = false;
        }

        drawCursor(menuPicture);

        return menuPicture;
    }

    @Override
    public void up() {
        if (currentMenuPos > 0) {
            eraseCursor(menuPicture);

            currentMenuPos--;

            copyBackgroundCursor(menuPicture);
        }
    }

    @Override
    public void down() {
        if (currentMenuPos < (items.size() - 1)) {
            eraseCursor(menuPicture);

            currentMenuPos++;

            copyBackgroundCursor(menuPicture);
        }
    }

    @Override
    public int getCursorValue() {
        return items.get(currentMenuPos).getValue();
    }

    /**
     * @return name
     */
    public final String getName() {
        return name;
    }

    public final void setName(final String nm) {
        this.name = nm;
    }

    @Override
    public SubMenu getTitle() {
        return title;
    }

    @Override
    public void setTitle(SubMenu ttl) {
        this.title = ttl;
        needToDrawPicture = true;
    }

    @Override
    public void addItem(final SubMenu it) {
        this.items.add(it);
        needToDrawPicture = true;
    }

    @Override
    public boolean isEnable() {
        return enable;
    }

    @Override
    public void setEnable(final boolean en) {
        this.enable = en;
    }

    @Override
    public void draw(final Graphics g2) {
        g2.drawImage(getPicture(), this.positionToDrawMenuX,
            this.positionToDrawMenuY, null);
    }

    @Override
    public void keyEvent(char consumeOtherKey) {
        // Nothing
    }
}
