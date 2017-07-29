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
     * Left cursor.
     */
    void left();

    /**
     * Down cursor.
     */
    void right();

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
     * @return true if is shortcut and menu is validate
     */
    boolean keyEvent(char consumeOtherKey);

    /**
     * Return previous menu.
     *
     * @return previous menu
     */
    MenuInterface getPreviousMenu();

    /**
     * Give text position to draw menu x.
     *
     * @return x
     */
    int getX();

    /**
     * Set text position to draw menu x.
     *
     * @param x x
     */
    void setX(int x);

    /**
     * Give text position to draw menu y.
     *
     * @return y
     */
    int getY();

    /**
     * Set text position to draw menu y.
     *
     * @param y x
     */
    void setY(int y);
}
