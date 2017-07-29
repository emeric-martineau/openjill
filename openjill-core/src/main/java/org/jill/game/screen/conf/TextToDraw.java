package org.jill.game.screen.conf;

/**
 * Text to draw.
 *
 * @author Emeric MARTINEAU
 */
public final class TextToDraw {
    /**
     * Text to draw.
     */
    private String text;

    /**
     * Color of picture.
     */
    private int color;

    /**
     * Position.
     */
    private int x;

    /**
     * Position.
     */
    private int y;

    /**
     * Text to drax.
     *
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * Text to drax.
     *
     * @param t text
     */
    public void setText(final String t) {
        this.text = t;
    }

    /**
     * Color.
     *
     * @return color
     */
    public int getColor() {
        return color;
    }

    /**
     * Color.
     *
     * @param c color
     */
    public void setColor(final int c) {
        this.color = c;
    }

    /**
     * Position.
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Position.
     *
     * @param x1 x
     */
    public void setX(final int x1) {
        this.x = x1;
    }

    /**
     * Position.
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Position.
     *
     * @param y1 y
     */
    public void setY(final int y1) {
        this.y = y1;
    }
}