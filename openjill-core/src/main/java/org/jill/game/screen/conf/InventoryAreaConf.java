package org.jill.game.screen.conf;

import java.util.Map;

/**
 * Inventory configuration.
 *
 * @author Emeric MARTINEAU
 */
public final class InventoryAreaConf extends AbstractLineTextConf {
    /**
     * Life picture start.
     */
    private PictureConf lifebarPictureStart;

    /**
     * Life picture end.
     */
    private PictureConf lifebarPictureEnd;

    /**
     * Inventory background color when player hit.
     */
    private int backgroundHitPlayerColor;

    /**
     * Position score to draw.
     */
    private TextToDraw score;

    /**
     * Position life bar to draw.
     */
    private TextToDraw lifebar;

    /**
     * Inventory item per picture.
     */
    private Map<String, PictureConf> items;

    /**
     * Item configuration.
     */
    private ItemConf itemConf;

    /**
     * Default life at start new game.
     */
    private int defaultLife;

    /**
     * Maximum life for player.
     */
    private int maxLife;

    /**
     * Life picture start.
     *
     * @return the lifebarPictureStart
     */
    public PictureConf getLifebarPictureStart() {
        return lifebarPictureStart;
    }

    /**
     * Life picture start.
     *
     * @param lifebarStart the lifebarPictureStart to set
     */
    public void setLifebarPictureStart(final PictureConf lifebarStart) {
        this.lifebarPictureStart = lifebarStart;
    }

    /**
     * Life picture end.
     *
     * @return the lifebarPictureEnd
     */
    public PictureConf getLifebarPictureEnd() {
        return lifebarPictureEnd;
    }

    /**
     * Life picture end.
     *
     * @param lifebarEnd the lifebarPictureEnd to set
     */
    public void setLifebarPictureEnd(final PictureConf lifebarEnd) {
        this.lifebarPictureEnd = lifebarEnd;
    }

    /**
     * Inventory background color when player hit.
     *
     * @return the backgroundHitPlayerColor
     */
    public int getBackgroundHitPlayerColor() {
        return backgroundHitPlayerColor;
    }

    /**
     * Inventory background color when player hit.
     *
     * @param color the backgroundHitPlayerColor to set
     */
    public void setBackgroundHitPlayerColor(final int color) {
        this.backgroundHitPlayerColor = color;
    }

    /**
     * Inventory item per picture.
     *
     * @return the items
     */
    public Map<String, PictureConf> getItems() {
        return items;
    }

    /**
     * Inventory item per picture.
     *
     * @param map the items to set
     */
    public void setItems(final Map<String, PictureConf> map) {
        this.items = map;
    }

    /**
     * Position to draw score.
     *
     * @return position to draw score
     */
    public TextToDraw getScore() {
        return score;
    }

    /**
     * Positions to draw score.
     *
     * @param s position
     */
    public void setScore(final TextToDraw s) {
        this.score = s;
    }

    /**
     * Position to lifebar score.
     *
     * @return position to draw lifebar
     */
    public TextToDraw getLifebar() {
        return lifebar;
    }

    /**
     * Positions to draw lifebar.
     *
     * @param l position
     */
    public void setLifebar(final TextToDraw l) {
        this.lifebar = l;
    }

    /**
     * Item configuration.
     *
     * @return conf
     */
    public ItemConf getItemConf() {
        return itemConf;
    }

    /**
     * Item configuration.
     *
     * @param ic conf
     */
    public void setItemConf(final ItemConf ic) {
        this.itemConf = ic;
    }

    /**
     * Default life at start new game.
     *
     * @return life
     */
    public int getDefaultLife() {
        return defaultLife;
    }

    /**
     * Default life at start new game.
     *
     * @param dl life
     */
    public void setDefaultLife(final int dl) {
        this.defaultLife = dl;
    }

    /**
     * Maximum life for player.
     *
     * @return life
     */
    public int getMaxLife() {
        return maxLife;
    }

    /**
     * Maximum life for player.
     *
     * @param ml life
     */
    public void setMaxLife(final int ml) {
        this.maxLife = ml;
    }
}
