package org.jill.game.gui.menu.conf;

import java.util.List;
import org.jill.game.gui.menu.SubMenu;

/**
 * Config for menu.
 *
 * @author Emeric MARTINEAU
 */
public final class MenuConf {
    /**
     * Position X.
     */
    private int positionX;

    /**
     * Position Y.
     */
    private int positionY;

    /**
     * Title of menu.
     */
    private SubMenu title;

    /**
     * Item of menu.
     */
    private List<SubMenu> item;

    /**
     * Position.
     *
     * @return the positionX
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Position.
     *
     * @param posX the positionX to set
     */
    public void setPositionX(final int posX) {
        this.positionX = posX;
    }

    /**
     * Position.
     *
     * @return the positionY
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * Position.
     *
     * @param posY the positionY to set
     */
    public void setPositionY(final int posY) {
        this.positionY = posY;
    }

    /**
     * Title.
     *
     * @return the title
     */
    public SubMenu getTitle() {
        return title;
    }

    /**
     * Title.
     *
     * @param t the title to set
     */
    public void setTitle(final SubMenu t) {
        this.title = t;
    }

    /**
     * Item.
     *
     * @return the item
     */
    public List<SubMenu> getItem() {
        return item;
    }

    /**
     * Item.
     *
     * @param items the item to set
     */
    public void setItem(final List<SubMenu> items) {
        this.item = items;
    }
}
