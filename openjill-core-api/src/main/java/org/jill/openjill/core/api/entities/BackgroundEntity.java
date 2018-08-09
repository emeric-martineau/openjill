package org.jill.openjill.core.api.entities;

import org.jill.jn.BackgroundLayer;

import java.awt.image.BufferedImage;

/**
 * Background entity interface.
 *
 * @author Emeric MARTINEAU
 */
public interface BackgroundEntity {
    /**
     * @return mapCode
     */
    int getMapCode();

    /**
     * @return name
     */
    String getName();

    /**
     * Picture.
     *
     * @return picture
     */
    BufferedImage getPicture();

    /**
     * @return tile
     */
    int getTile();

    /**
     * @return tileset
     */
    int getTileset();

    /**
     * Init object.
     *
     * @param backParameter background parameter
     */
    void init(BackgroundParam backParameter);

    /**
     * Msg touch.
     *
     * @return true/false
     */
    boolean isMsgTouch();

    /**
     * Msg draw.
     *
     * @return true/false
     */
    boolean isMsgDraw();

    /**
     * Msg update.
     *
     * @return true/false
     */
    boolean isMsgUpdate();

    /**
     * If player can cross over.
     *
     * @return true/false
     */
    boolean isPlayerThru();

    /**
     * If player can stand here.
     *
     * @return true/false
     */
    boolean isStair();

    /**
     * Player can climb here.
     *
     * @return boolean
     */
    boolean isVine();

    /**
     * Draw special case.
     *
     * @param background background
     * @param x background position x
     * @param y background position x
     */
    void msgDraw(BackgroundLayer background, int x, int y);

    /**
     * Player touch this background.
     *
     * @param obj player
     */
    void msgTouch(ObjectEntity obj);

    /**
     * Update in special case.
     *
     * @param background background
     * @param x background position x
     * @param y background position x
     */
    void msgUpdate(BackgroundLayer background, int x, int y);
}
