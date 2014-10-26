package org.jill.game.screen.conf;

import java.util.List;

/**
 * Configuration class.
 *
 * @author Emeric MARTINEAU
 */
public final class StatusBarConf {
    /**
     * Text to draw.
     */
    private List<TextToDraw> text;

    /**
     * Text to draw.
     */
    private List<TextToDraw> bigtext;

    /**
     * Message bar.
     */
    private RectangleConf messageBar;

    /**
     * Control area.
     */
    private RectangleConf controlArea;

    /**
     * Invenory area.
     */
    private RectangleConf inventoryArea;

    /**
     * Game area.
     */
    private RectangleConf gameArea;

    /**
     * Images to draw.
     */
    private List<ImagesConf> images;

    /**
     * Text to draw.
     *
     * @return list of text to draw.
     */
    public List<TextToDraw> getText() {
        return text;
    }

    /**
     * Text to draw.
     *
     * @param t text
     */
    public void setText(final List<TextToDraw> t) {
        this.text = t;
    }

    /**
     * Message bar.
     *
     * @return message bar configuration
     */
    public RectangleConf getMessageBar() {
        return messageBar;
    }

    /**
     * Message bar.
     *
     * @param mb message bar configuration
     */
    public void setMessageBar(final RectangleConf mb) {
        this.messageBar = mb;
    }

    /**
     * Control area.
     *
     * @return  control area config
     */
    public RectangleConf getControlArea() {
        return controlArea;
    }

    /**
     * Control area.
     *
     * @param ca control area config
     */
    public void setInventoryArea(final RectangleConf ca) {
        this.inventoryArea = ca;
    }

    /**
     * Control area.
     *
     * @return  control area config
     */
    public RectangleConf getInventoryArea() {
        return inventoryArea;
    }

    /**
     * Control area.
     *
     * @param ca control area config
     */
    public void setControlArea(final RectangleConf ca) {
        this.controlArea = ca;
    }

    /**
     * Game area.
     *
     * @return  control area config
     */
    public RectangleConf getGameArea() {
        return gameArea;
    }

    /**
     * Game area.
     *
     * @param ca control area config
     */
    public void setGameArea(final RectangleConf ca) {
        this.gameArea = ca;
    }

    /**
     * Images to draw.
     *
     * @return list of images
     */
    public List<ImagesConf> getImages() {
        return images;
    }

    /**
     * Images to draw.
     *
     * @param imgs list of images
     */
    public void setImages(final List<ImagesConf> imgs) {
        this.images = imgs;
    }

    /**
     * Text to draw.
     *
     * @return list of text to draw.
     */
    public List<TextToDraw> getBigtext() {
        return bigtext;
    }

    /**
     * Text to draw.
     *
     * @param t text
     */
    public void setBigtext(final List<TextToDraw> t) {
        this.bigtext = t;
    }

}
