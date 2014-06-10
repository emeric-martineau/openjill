package org.jill.game.gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.jill.game.gui.menu.SubMenu;
import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.manager.TileManager;

/**
 * Information box like instruction or int.
 *
 * @author Emeric MARINEAU
 */
public final class InformationBox {
    /**
     * Length of line in box.
     */
    private int lineLength;

    /**
     * Number of line in box.
     */
    private int numberLinePerScreen;

    /**
     * Background.
     */
    private BufferedImage boxPicture;

    /**
     * Graphic of status bar.
     */
    private Graphics2D g2BoxPicture;

    /**
     * Picture cache.
     */
    private TileManager pictureCache;

    /**
     * Size.
     */
    private int width;

    /**
     * Size.
     */
    private int height;

    /**
     *  List of text.
     */
    private ArrayList<SubMenu> listText = new ArrayList<>();

    /**
     * Title of dialog box.
     */
    private SubMenu title;

    /**
     *  Index of text.
     */
    private int currentMenuPos = 0;

    /**
     * End of index to don't display blank screen.
     */
    private int endMenuPos;

    /**
     * If menu is enable.
     */
    private boolean enable = false;

    /**
     * Picture.
     */
    private BufferedImage leftTopCorner;

    /**
     * Picture.
     */
    private BufferedImage rightTopCorner;

    /**
     * Picture.
     */
    private BufferedImage leftBottomCorner;

    /**
     * Picture.
     */
    private BufferedImage rightBottomCorner;

    /**
     * Picture.
     */
    private BufferedImage rightBorder;

    /**
     * Picture.
     */
    private BufferedImage leftBorder;

    /**
     * Picture.
     */
    private BufferedImage topBorder;

    /**
     * Picture.
     */
    private BufferedImage bottomBorder;

    /**
     * Constructor for dialog box 190x130 size.
     *
     * @param pctCache cache picture manager
     */
    public InformationBox(final TileManager pctCache) {
        this(190, 130, pctCache);
    }

    /**
     * Constructor for dialog box with personnal size.
     *
     * @param wd size of window
     * @param hg size of window
     * @param pctCache cache picture manager
     */
    public InformationBox(final int wd, final int hg,
            final TileManager pctCache) {
        this.pictureCache = pctCache;
        this.width = wd;
        this.height = hg;

        initPicture();

        createStatusBar(wd, hg);

        lineLength = (wd - 16) / 6;
        numberLinePerScreen = (hg - (16 + 12)) / 6;
    }

    /**
     * Ini picture.
     */
    private void initPicture() {
        leftTopCorner = pictureCache.getImage(3, 2);
        rightTopCorner = pictureCache.getImage(3, 3);
        leftBottomCorner = pictureCache.getImage(3, 5);
        rightBottomCorner = pictureCache.getImage(3, 7);
        leftBorder = pictureCache.getImage(3, 9);
        rightBorder = pictureCache.getImage(3, 1);
        topBorder = pictureCache.getImage(3, 4);
        bottomBorder = pictureCache.getImage(3, 6);
    }

    /**
     * Create status bar with inventory.
     */
    private void createStatusBar(final int width, final int height) {
        // Buffer image
        boxPicture =
            new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_ARGB);
        // Graphic
        g2BoxPicture = boxPicture.createGraphics();

        drawStatusBarRightVerticalBar(g2BoxPicture);

        drawStatusBarLeftVerticalBar(g2BoxPicture);

        drawStatusBarUpperHorizontalBar(g2BoxPicture);

        drawStatusBarLowerHorizontalBar(g2BoxPicture);
    }

    private void drawStatusBarUpperHorizontalBar(final Graphics2D g2) {
        //-[ Draw upper horizontal bar ]---------------------------------------
        // Left upper corner
        g2.drawImage(leftTopCorner, 0, 0, null);

        // Height to draw
        final int widthtToDraw = width - leftTopCorner.getWidth() - rightBorder.getWidth();
        // Number to picture to draw
        final int number = (widthtToDraw / topBorder.getHeight()) + 1;

        int x = leftTopCorner.getWidth();

        for(int index = 0; index < number; index++) {
            g2.drawImage(topBorder, x, 0, null);

            x += topBorder.getWidth();
        }

        g2.drawImage(rightTopCorner, width - rightBorder.getWidth(), 0, null);
    }

    private void drawStatusBarLowerHorizontalBar(final Graphics2D g2) {
        //-[ Draw lower horizontal bar ]---------------------------------------
        final int y = height - bottomBorder.getHeight();

        g2.drawImage(leftBottomCorner, 0, y, null);

        final int widthBottomBorder = bottomBorder.getWidth();

        for(int index = leftBottomCorner.getWidth(); index < width; index += widthBottomBorder) {
            g2.drawImage(bottomBorder, index, y, null);
        }

        g2.drawImage(rightBottomCorner, width - rightBottomCorner.getWidth(), y, null);
    }

    private void drawStatusBarRightVerticalBar(final Graphics2D g2) {
        //-[ Draw right vertical bar ]-----------------------------------------
        // Height to draw
        final int heightToDraw = height - topBorder.getHeight() - bottomBorder.getHeight();
        // Number to picture to draw
        final int number = (heightToDraw / rightBorder.getHeight()) + 1;

        int y = topBorder.getHeight();

        for(int index = 0; index < number; index++) {
            g2.drawImage(rightBorder, 0, y, null);

            y += rightBorder.getHeight();
        }
    }

    private void drawStatusBarLeftVerticalBar(final Graphics2D g2) {
        //-[ Draw right vertical bar ]-----------------------------------------

        // Height to draw
        final int heightToDraw = height - topBorder.getHeight() - bottomBorder.getHeight();
        // Number to picture to draw
        final int number = (heightToDraw / rightBorder.getHeight()) + 1;
        // X to draw
        final int x = width - rightBorder.getWidth();

        int y = topBorder.getHeight();

        for(int index = 0; index < number; index++) {
            g2.drawImage(leftBorder, x, y, null);

            y += leftBorder.getHeight();
        }
    }

    /**
     * Add space
     *
     * @param text
     * @return
     */
    private String addSpace(final String text) {
        final int numberStartSpace = (lineLength - text.length()) / 2;
        final int numberEndSpace = lineLength
                - numberStartSpace - text.length();

        final StringBuilder sb = new StringBuilder(lineLength);

        for(int i = 0; i < numberStartSpace; i++) {
            sb.append(' ');
        }

        sb.append(text);

        for(int i = 0; i < numberEndSpace; i++) {
            sb.append(' ');
        }

        return sb.toString();
    }

    /**
     * Draw text
     */
    private void drawTextArea() {
        // Draw background text
        g2BoxPicture.setColor(pictureCache.getColorMap()[
                TextManager.COLOR_DARK_BLUE]);
        g2BoxPicture.fillRect(
                leftTopCorner.getWidth(),
                leftTopCorner.getHeight(),
                width - rightBottomCorner.getWidth()
                    - leftTopCorner.getWidth(),
                height - rightBottomCorner.getHeight()
                    - leftTopCorner.getHeight());

        final int end = currentMenuPos + numberLinePerScreen;

        int yText = 16;

        SubMenu line;

        // Draw text
        for(int index = currentMenuPos; index < end; index++) {
            line = listText.get(index);
            pictureCache.getTextManager().drawSmallText(g2BoxPicture,
                    8, yText, line.getText(), line.getColor(),
                    TextManager.BACKGROUND_COLOR_NONE);

            yText += 6;
        }

        // Now draw title
        pictureCache.getTextManager().drawSmallText(g2BoxPicture,
                8, 6, title.getText(), title.getColor(),
                TextManager.BACKGROUND_COLOR_NONE);
    }

    /**
     * Return status bar
     *
     * @return
     */
    public BufferedImage getBox() {
        return boxPicture;
    }

    /**
     * Set content text
     *
     * @param content
     */
    public void setContent(final String content) {
        // Split CRLF
        final String[] lines = content.split("\r\n");

        // Clear an set new capacity
        listText.clear();
        listText.ensureCapacity(lines.length);

        // Character of color if set
        char colorChar;
        // Color after convertion
        int color;
        // Text
        String text;

        for(String currentLine : lines) {
            // Check if first char is number. If it's, use for color
            if (currentLine.length() > 0) {
                colorChar = currentLine.charAt(0);
            } else {
                colorChar = ' ';
            }

            if (colorChar >= '0' && colorChar <= '9') {
                color = Integer.valueOf(colorChar) - 48;
                text = currentLine.substring(1);
            } else {
                color = TextManager.COLOR_WHITE;
                text = currentLine;
            }

            listText.add(new SubMenu(color, addSpace(text)));
        }

        title = listText.get(0);
        listText.remove(0);

        // Calculate the last first line to display
        endMenuPos = lines.length - numberLinePerScreen - 1;

        drawTextArea();
    }

    /**
     * Up cursor
     */
    public void up() {
        if (currentMenuPos > 0) {
            currentMenuPos--;

            drawTextArea();
        }
    }

    /**
     * Down cursor
     */
    public void down() {
        if (currentMenuPos < endMenuPos) {
            currentMenuPos++;

            drawTextArea();
        }
    }

    /**
     * @return enable
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * @param enable enable � d�finir
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}