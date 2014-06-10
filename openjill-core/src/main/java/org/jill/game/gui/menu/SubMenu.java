package org.jill.game.gui.menu;

/**
 * This class is sub menu entry.
 *
 * @author Emeric MARTINEAU
 */
public final class SubMenu {
    /**
     * Title of text.
     */
    private String text;

    /**
     * Front color.
     */
    private int color;

    /**
     * Index associate to the menu.
     */
    private int value;

    /**
     * Default constructor.
     */
    public SubMenu() {
        // Nothing
    }

    /**
     * Constrcutor.
     *
     * @param clr color
     * @param txt text
     */
    public SubMenu(final int clr, final String txt) {
        this.color = clr;
        this.text = txt;
    }

    /**
     * Constrcutor.
     *
     * @param clr color
     * @param txt text
     * @param index index
     */
    public SubMenu(final int clr, final String txt, final int index) {
        this.color = clr;
        this.text = txt;
        this.value = index;
    }

    /**
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * @param txt text to display
     */
    public void setText(final String txt) {
        this.text = txt;
    }

    /**
     * @return color
     */
    public int getColor() {
        return color;
    }

    /**
     * @param clr color to display
     */
    public void setColor(final int clr) {
        this.color = clr;
    }

    /**
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param index value to return
     */
    public void setValue(final int index) {
        this.value = index;
    }
}
