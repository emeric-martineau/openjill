package org.jill.game.gui.menu;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Interface for menu.
 *
 * @author Emeric MARTINEAU
 */
public interface MenuInterface {

    /**
     * Clear all item of menu.
     */
    void clearAllItems();

    /**
     * Return current picture of menu. Each call change cursor state
     *
     * @return picture to draw
     */
    BufferedImage getPicture();

    /**
     * Up cursor.
     */
    void up();

    /**
     * Down cursor.
     */
    void down();

    /**
     * Return value of current cursor.
     *
     * @return cursor position
     */
    int getCursorValue();

    /**
     * Enable.
     *
     * @return enable
     */
    boolean isEnable();

    /**
     * Enable.
     *
     * @param enable enable
     */
    void setEnable(boolean enable);

    /**
     * Title.
     *
     * @return title title
     */
    SubMenu getTitle();

    /**
     * Set title.
     *
     * @param title title
     */
    void setTitle(SubMenu title);

    /**
     * Add item.
     *
     * @param item item
     */
    void addItem(SubMenu item);

    /**
     * Draw menu.
     *
     * @param g2 graphic 2d object
     */
    void draw(Graphics g2);

    /**
     * Call to grap key event not managed.
     *
     * @param consumeOtherKey key
     */
    void keyEvent(char consumeOtherKey);

    /**
     * Return previous menu.
     *
     * @return previous menu
     */
    MenuInterface getPreviousMenu();
}
