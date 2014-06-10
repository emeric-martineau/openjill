package org.jill.game.screen.conf;

import java.util.List;

/**
 * Control area configuration.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractLineTextConf {
    /**
     * Background color of control area.
     */
    private int backgroundColor;

    /**
     * Text to draw.
     */
    private List<TextToDraw> text;

    /**
     * Text to draw.
     */
    private List<TextToDraw> bigText;

    /**
     * Line to draw.
     */
    private List<LineToDraw> lines;

    /**
     * Text to draw.
     *
     * @return list of text to draw.
     */
    public final List<TextToDraw> getText() {
        return text;
    }

    /**
     * Text to draw.
     *
     * @param t text
     */
    public final void setText(final List<TextToDraw> t) {
        this.text = t;
    }

    /**
     * Background color of control area.
     *
     * @return the backgroundColor
     */
    public final int getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Background color of control area.
     *
     * @param backColor the backgroundColor to set
     */
    public final void setBackgroundColor(final int backColor) {
        this.backgroundColor = backColor;
    }

    /**
     * Line to draw.
     *
     * @return lines
     */
    public final List<LineToDraw> getLines() {
        return lines;
    }

    /**
     * Lines to draw.
     *
     * @param l lines
     */
    public final void setLines(final List<LineToDraw> l) {
        this.lines = l;
    }

    /**
     * Text to draw.
     *
     * @return list of text to draw.
     */
    public final List<TextToDraw> getBigText() {
        return bigText;
    }

    /**
     * Text to draw.
     *
     * @param t text
     */
    public final void setBigText(final List<TextToDraw> t) {
        this.bigText = t;
    }

}
