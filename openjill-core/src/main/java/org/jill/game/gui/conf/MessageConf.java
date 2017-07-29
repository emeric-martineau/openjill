package org.jill.game.gui.conf;

import java.util.List;

import org.jill.game.screen.conf.ImagesConf;
import org.jill.game.screen.conf.RectangleConf;

/**
 * Basic message configuration
 *
 * @author Emeric MARTINEAU
 */
public class MessageConf {
    /**
     * Position X.
     */
    private int x;

    /**
     * Position Y.
     */
    private int y;

    /**
     * Width.
     */
    private int width;

    /**
     * Height.
     */
    private int height;

    /**
     * Text color.
     */
    private int textColor;

    /**
     * Background color.
     */
    private RectangleConf textarea;

    /**
     * Background color.
     */
    private RectangleConf picturearea;

    /**
     * Images to draw.
     */
    private List<ImagesConf> images;

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
     * Width.
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Width.
     *
     * @param iwidth width
     */
    public void setWidth(int iwidth) {
        this.width = iwidth;
    }

    /**
     * Height.
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Height.
     *
     * @param iheight height
     */
    public void setHeight(final int iheight) {
        this.height = iheight;
    }

    /**
     * Color of text.
     *
     * @return color
     */
    public int getTextColor() {
        return textColor;
    }

    /**
     * Color of text.
     *
     * @param tc color
     */
    public void setTextColor(final int tc) {
        this.textColor = tc;
    }

    /**
     * List of image to draw.
     *
     * @return list image
     */
    public List<ImagesConf> getImages() {
        return images;
    }

    /**
     * List of image to draw.
     *
     * @param limages list image
     */
    public void setImages(final List<ImagesConf> limages) {
        this.images = limages;
    }

    /**
     * Get text area.
     *
     * @return text area
     */
    public RectangleConf getTextarea() {
        return textarea;
    }

    /**
     * Text area.
     *
     * @param txtarea text area
     */
    public void setTextarea(final RectangleConf txtarea) {
        this.textarea = txtarea;
    }

    /**
     * Picture area background color.
     *
     * @return picture area
     */
    public RectangleConf getPicturearea() {
        return picturearea;
    }

    /**
     * Picture area background color.
     *
     * @param pctcfg picture area config
     */
    public void setPicturearea(final RectangleConf pctcfg) {
        this.picturearea = pctcfg;
    }


}
