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
     * Short cut.
     */
    private char shortCut = '\0';

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
        this.shortCut = Character.toUpperCase(txt.charAt(0));
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

    /**
     * Short cut of this entry.
     *
     * @return a char
     */
    public char getShortCut() {
        if (this.shortCut == '\0') {
            this.shortCut = Character.toUpperCase(this.text.charAt(0));
        }
        return shortCut;
    }

    /**
     * Short cut of this entry.
     * @param shortCut a char
     */
    public void setShortCut(final char shortCut) {
        this.shortCut = shortCut;
    }


}
