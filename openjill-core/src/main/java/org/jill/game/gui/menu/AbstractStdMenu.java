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
public abstract class AbstractStdMenu extends AbstractMenu
    implements MenuInterface {

    /**
     * Number of space for boder.
     */
    private static final int NB_BORDER = 5;

    /**
     * Empty space before text.
     */
    private int nbSpaceBefore;

    /**
     * Start text position x.
     */
    private int textX;

    /**
     * Start text position y.
     */
    private int textY;

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
    private BufferedImage rightUpperCorner;

    /**
     * Picutre.
     */
    private BufferedImage leftUpperCorner;

    /**
     * Picutre.
     */
    private BufferedImage rightLowerCorner;

    /**
     * Picutre.
     */
    private BufferedImage leftLowerCorner;

    /**
     * Picutre.
     */
    private BufferedImage upperBar;

    /**
     * Picutre.
     */
    private BufferedImage lowerBar;

    /**
     * Picutre.
     */
    private BufferedImage rightBar;

    /**
     * Picutre.
     */
    private BufferedImage leftBar;

    /**
     * Picutre.
     */
    private BufferedImage backImage;

    /**
     * Picutre.
     */
    private BufferedImage background;

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
    public AbstractStdMenu(final TileManager pictureCacheManager) {
        super(pictureCacheManager, null);

        // Calculate font size
        this.fontSize = pictureCacheManager.getTextManager().createSmallText(" ",
            TextManager.COLOR_BLUE,
            TextManager.COLOR_BLUE).getHeight();

        this.fontSizeSpace = this.fontSize + 2;

        this.pictureCache = pictureCacheManager;

        this.needToDrawPicture = true;
    }

    /**
     * Create background.
     *
     * @param pictureCacheManager picture cache
     */
    protected void createBackground(final TileManager pictureCacheManager) {
        // Create background image
        this.background = new BufferedImage(this.backImage.getWidth(),
                this.backImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        final Graphics2D g2 = this.background.createGraphics();

        // Fill background cause in CGA mode, background picture can be empty
        g2.setColor(pictureCacheManager.getBackgroundColor());
        g2.fillRect(0, 0, this.background.getWidth(),
                this.background.getWidth());

        // Now, draw back picture
        g2.drawImage(this.backImage, 0, 0, null);

        g2.dispose();
    }

    @Override
    public void clearAllItems() {
        this.needToDrawPicture = true;
        this.items.clear();
    }

    /**
     * Get minimum size of picture.
     *
     * @return minimum size
     */
    private int calculateWidthMinimum() {
        // length title is tile
        int maximum = this.title.getText().length();

        int currentsize;

        for (SubMenu sub : this.items) {
            // 4 space before
            currentsize = sub.getText().length() + this.nbSpaceBefore;

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
        final int height = NB_BORDER + this.items.size();
        // + 1 for border
        final int width = calculateWidthMinimum() + 1;
        // Picture size
        final int pictureWidth = width * this.fontSizeSpace;
        final int pictureHeight = height * this.fontSizeSpace;

        this.menuPicture = new BufferedImage(pictureWidth, pictureHeight,
            BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2 = this.menuPicture.createGraphics();

        // Fill background
        final int widthBack = width - 1;
        final int heightBack = height - 1;
        for (int x = 1; x < widthBack; x++) {
            for (int y = 1; y < heightBack; y++) {
                g2.drawImage(this.background, x * this.fontSizeSpace,
                    y * this.fontSizeSpace, null);
            }
        }

        // Draw corner
        g2.drawImage(this.rightUpperCorner, 0, 0, null);
        g2.drawImage(this.leftUpperCorner, widthBack * this.fontSizeSpace,
                0, null);
        g2.drawImage(this.rightLowerCorner, 0, heightBack * this.fontSizeSpace,
                null);
        g2.drawImage(this.leftLowerCorner, widthBack * this.fontSizeSpace,
            heightBack * this.fontSizeSpace, null);

        // Draw upper border
        for (int x = 1; x < widthBack; x++) {
            g2.drawImage(this.upperBar, x * this.fontSizeSpace, 0, null);
        }

        // Draw lower border
        final int yPos = heightBack * this.fontSizeSpace;

        for (int x = 1; x < widthBack; x++) {
            g2.drawImage(this.lowerBar, x * this.fontSizeSpace, yPos, null);
        }

        // Draw right border
        for (int y = 1; y < heightBack; y++) {
            g2.drawImage(this.rightBar, 0, y * this.fontSizeSpace, null);
        }

        // Draw left border
        final int xPos = widthBack * this.fontSizeSpace;

        for (int y = 1; y < heightBack; y++) {
            g2.drawImage(this.leftBar, xPos, y * this.fontSizeSpace, null);
        }

        // Draw title
        this.pictureCache.getTextManager().drawSmallText(g2, this.textX,
            this.textY, this.title.getText(), this.title.getColor(),
            TextManager.BACKGROUND_COLOR_NONE);

        final int posCursorX = this.textX + this.fontSize;
        final int posTextX = this.textX + (this.nbSpaceBefore * this.fontSize);
        int posTextY = this.textY + this.fontSizeSpace;

        this.cursorPositionBySubMenuIndex.clear();

        for (SubMenu entry : items) {
            this.pictureCache.getTextManager().drawSmallText(g2, posTextX,
                    posTextY, entry.getText(), entry.getColor(),
                TextManager.BACKGROUND_COLOR_NONE);

            // Calculate cursor position
            this.cursorPositionBySubMenuIndex.add(new Point(posCursorX,
                    posTextY));

            posTextY += this.fontSizeSpace;
        }

        g2.dispose();
    }

    @Override
    public BufferedImage getPicture() {
        if (this.needToDrawPicture) {
            drawPicture();
            copyBackgroundCursor(this.menuPicture);
            this.needToDrawPicture = false;
        }

        drawCursor(this.menuPicture);

        return this.menuPicture;
    }

    @Override
    public void up() {
        if (this.currentMenuPos > 0) {
            //eraseCursor(menuPicture);

            this.currentMenuPos--;
            this.needToDrawPicture = true;

            //copyBackgroundCursor(menuPicture);
        }
    }

    @Override
    public void down() {
        if (this.currentMenuPos < (this.items.size() - 1)) {
            //eraseCursor(menuPicture);

            this.currentMenuPos++;
            this.needToDrawPicture = true;
            //copyBackgroundCursor(menuPicture);
        }
    }

    @Override
    public int getCursorValue() {
        return this.items.get(this.currentMenuPos).getValue();
    }

    /**
     * @return name
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Set name.
     *
     * @param nm name
     */
    public final void setName(final String nm) {
        this.name = nm;
    }

    /**
     * Get position text X.
     *
     * @return x
     */
    protected int getTextX() {
        return this.textX;
    }

    /**
     * Set position text X.
     *
     * @param x x
     */
    protected void setTextX(final int x) {
        this.textX = x;
    }

    /**
     * Get position text Y.
     *
     * @return y
     */
    protected int getTextY() {
        return this.textY;
    }

    /**
     * Set position text Y.
     *
     * @param y y
     */
    protected void setTextY(final int y) {
        this.textY = y;
    }

    /**
     * Space before text.
     *
     * @return nb space
     */
    public int getNbSpaceBefore() {
        return this.nbSpaceBefore;
    }

    /**
     * Space before text.
     *
     * @param nb nb space.
     */
    public void setNbSpaceBefore(final int nb) {
        this.nbSpaceBefore = nb;
    }

    /**
     * Corner.
     *
     * @return corner
     */
    public BufferedImage getRightUpperCorner() {
        return this.rightUpperCorner;
    }

    /**
     * Corner.
     *
     * @param right picture
     */
    public void setRightUpperCorner(final BufferedImage right) {
        this.rightUpperCorner = right;
    }

    /**
     * Corner.
     *
     * @return corner
     */
    public BufferedImage getLeftUpperCorner() {
        return this.leftUpperCorner;
    }

    /**
     * Corner.
     *
     * @param left picture
     */
    public void setLeftUpperCorner(final BufferedImage left) {
        this.leftUpperCorner = left;
    }

    /**
     * Corner.
     *
     * @return corner
     */
    public BufferedImage getRightLowerCorner() {
        return this.rightLowerCorner;
    }

    /**
     * Corner.
     *
     * @param right picture
     */
    public void setRightLowerCorner(final BufferedImage right) {
        this.rightLowerCorner = right;
    }

    /**
     * Corner.
     *
     * @return corner
     */
    public BufferedImage getLeftLowerCorner() {
        return leftLowerCorner;
    }

    /**
     * Corner.
     *
     * @param left picture
     */
    public void setLeftLowerCorner(final BufferedImage left) {
        this.leftLowerCorner = left;
    }

    /**
     * Bar.
     *
     * @return bar
     */
    public BufferedImage getUpperBar() {
        return upperBar;
    }

    /**
     * Bar.
     *
     * @param upper picture
     */
    public void setUpperBar(final BufferedImage upper) {
        this.upperBar = upper;
    }

    /**
     * Bar.
     *
     * @return bar
     */
    public BufferedImage getLowerBar() {
        return lowerBar;
    }

    /**
     * Bar.
     *
     * @param lower picture
     */
    public void setLowerBar(final BufferedImage lower) {
        this.lowerBar = lower;
    }

    /**
     * Bar.
     *
     * @return bar
     */
    public BufferedImage getRightBar() {
        return rightBar;
    }

    /**
     * Bar.
     *
     * @param right picture
     */
    public void setRightBar(final BufferedImage right) {
        this.rightBar = right;
    }

    /**
     * Bar.
     *
     * @return bar
     */
    public BufferedImage getLeftBar() {
        return leftBar;
    }

    /**
     * Bar.
     *
     * @param left picture
     */
    public void setLeftBar(final BufferedImage left) {
        this.leftBar = left;
    }

    /**
     * Background image.
     *
     * @return image
     */
    public BufferedImage getBackImage() {
        return backImage;
    }

    /**
     * Set back picture.
     *
     * @param back picture
     */
    public void setBackImage(final BufferedImage back) {
        this.backImage = back;
    }

    /**
     * Return menu picture.
     *
     * @return picture
     */
    public BufferedImage getMenuPicture() {
        return this.menuPicture;
    }

    /**
     * Set menu picture.
     *
     * @param menu picture
     */
    public void setMenuPicture(final BufferedImage menu) {
        this.menuPicture = menu;
    }

    public boolean isNeedToDrawPicture() {
        return this.needToDrawPicture;
    }

    public void setNeedToDrawPicture(boolean needToDrawPicture) {
        this.needToDrawPicture = needToDrawPicture;
    }

    @Override
    public SubMenu getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(SubMenu ttl) {
        this.title = ttl;
        this.needToDrawPicture = true;
    }

    @Override
    public void addItem(final SubMenu it) {
        this.items.add(it);
        this.needToDrawPicture = true;
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
        g2.drawImage(getPicture(), getX(),
            getY(), null);
    }

    @Override
    public boolean keyEvent(char consumeOtherKey) {
        char key = Character.toUpperCase(consumeOtherKey);
        int menuPos = 0;

        for (SubMenu sm : this.items) {
            if (key == sm.getShortCut()) {
                this.currentMenuPos = menuPos;

                this.needToDrawPicture = true;

                return true;
            }

            menuPos++;
        }

        return false;
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
