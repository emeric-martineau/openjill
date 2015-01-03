package org.jill.game.gui.menu.conf;

import java.util.List;
import org.jill.game.gui.menu.SubMenu;
import org.jill.game.screen.conf.PictureConf;

/**
 * Config for menu.
 *
 * @author Emeric MARTINEAU
 */
public class MenuConf {
    /**
     * Position X.
     */
    private int x;

    /**
     * Position Y.
     */
    private int y;

    /**
     * Position X.
     */
    private int textX;

    /**
     * Position Y.
     */
    private int textY;

    /**
     * Empty space before text.
     */
    private int nbSpaceBefore;

    /**
     * Title of menu.
     */
    private SubMenu title;

    /**
     * Item of menu.
     */
    private List<SubMenu> item;

    /**
     * Picutre.
     */
    private PictureConf rightUpperCorner;

    /**
     * Picutre.
     */
    private PictureConf leftUpperCorner;

    /**
     * Picutre.
     */
    private PictureConf rightLowerCorner;

    /**
     * Picutre.
     */
    private PictureConf leftLowerCorner;

    /**
     * Picutre.
     */
    private PictureConf upperBar;

    /**
     * Picutre.
     */
    private PictureConf lowerBar;

    /**
     * Picutre.
     */
    private PictureConf rightBar;

    /**
     * Picutre.
     */
    private PictureConf leftBar;

    /**
     * Picutre.
     */
    private PictureConf backImage;

    /**
     * X.
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * X.
     *
     * @param ix X
     */
    public void setX(final int ix) {
        this.x = ix;
    }

    /**
     * Y.
     *
     * @return Y
     */
    public int getY() {
        return y;
    }

    /**
     * Y.
     *
     * @param iy Y
     */
    public void setY(final int iy) {
        this.y = iy;
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
     * Space before text.
     *
     * @return nb space
     */
    public int getNbSpaceBefore() {
        return nbSpaceBefore;
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

    /**
     * Text position.
     *
     * @return  x
     */
    public int getTextX() {
        return textX;
    }

    /**
     * Text position.
     *
     * @param x  x
     */
    public void setTextX(final int x) {
        this.textX = x;
    }

    /**
     * Text position.
     *
     * @return  y
     */
    public int getTextY() {
        return textY;
    }

    /**
     * Text position.
     *
     * @param y y
     */
    public void setTextY(int y) {
        this.textY = y;
    }

/**
     * Corner.
     *
     * @return corner
     */
    public PictureConf getRightUpperCorner() {
        return rightUpperCorner;
    }

    /**
     * Corner.
     *
     * @param right picture
     */
    public void setRightUpperCorner(final PictureConf right) {
        this.rightUpperCorner = right;
    }

    /**
     * Corner.
     *
     * @return corner
     */
    public PictureConf getLeftUpperCorner() {
        return leftUpperCorner;
    }

    /**
     * Corner.
     *
     * @param left picture
     */
    public void setLeftUpperCorner(final PictureConf left) {
        this.leftUpperCorner = left;
    }

    /**
     * Corner.
     *
     * @return corner
     */
    public PictureConf getRightLowerCorner() {
        return rightLowerCorner;
    }

    /**
     * Corner.
     *
     * @param right picture
     */
    public void setRightLowerCorner(final PictureConf right) {
        this.rightLowerCorner = right;
    }

    /**
     * Corner.
     *
     * @return corner
     */
    public PictureConf getLeftLowerCorner() {
        return leftLowerCorner;
    }

    /**
     * Corner.
     *
     * @param left picture
     */
    public void setLeftLowerCorner(final PictureConf left) {
        this.leftLowerCorner = left;
    }

    /**
     * Bar.
     *
     * @return bar
     */
    public PictureConf getUpperBar() {
        return upperBar;
    }

    /**
     * Bar.
     *
     * @param upper picture
     */
    public void setUpperBar(final PictureConf upper) {
        this.upperBar = upper;
    }

    /**
     * Bar.
     *
     * @return bar
     */
    public PictureConf getLowerBar() {
        return lowerBar;
    }

    /**
     * Bar.
     *
     * @param lower picture
     */
    public void setLowerBar(final PictureConf lower) {
        this.lowerBar = lower;
    }

    /**
     * Bar.
     *
     * @return bar
     */
    public PictureConf getRightBar() {
        return rightBar;
    }

    /**
     * Bar.
     *
     * @param right picture
     */
    public void setRightBar(final PictureConf right) {
        this.rightBar = right;
    }

    /**
     * Bar.
     *
     * @return bar
     */
    public PictureConf getLeftBar() {
        return leftBar;
    }

    /**
     * Bar.
     *
     * @param left picture
     */
    public void setLeftBar(final PictureConf left) {
        this.leftBar = left;
    }

    /**
     * Background image.
     *
     * @return image
     */
    public PictureConf getBackImage() {
        return backImage;
    }

    /**
     * Set back picture.
     *
     * @param back picture
     */
    public void setBackImage(final PictureConf back) {
        this.backImage = back;
    }
}
