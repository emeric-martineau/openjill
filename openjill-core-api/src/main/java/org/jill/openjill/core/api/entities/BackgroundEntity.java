package org.jill.openjill.core.api.entities;

import java.awt.image.BufferedImage;

/**
 * Background entity interface.
 *
 * @author Emeric MARTINEAU
 */
public interface BackgroundEntity {
    /**
     * Return initial config for ReplaceTileBackgroundEntity.
     *
     * @return backparameter
     */
    BackgroundParam getBackParam();

    /**
     * Flags.
     *
     * @return flags
     */
    int getFlags();

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
     * Return X.
     *
     * @return x
     */
    int getX();

    /**
     * Return Y.
     *
     * @return y
     */
    int getY();

    /**
     * Init object.
     *
     * @param backParameter background parameter
     */
    void init(BackgroundParam backParameter);

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
     */
    void msgDraw();

    /**
     * Player touch this background.
     *
     * @param obj player
     */
    void msgTouch(ObjectEntity obj);

    /**
     * Update in special case.
     */
    void msgUpdate();

}
