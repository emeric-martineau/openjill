package org.jill.openjill.core.api.entities;

import org.jill.jn.BackgroundLayer;
import org.jill.jn.ObjectItem;

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
     * @param x background position x
     * @param y background position y
     *
     * @return picture
     */
    BufferedImage getPicture(int x, int y);

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
     * @param y background position y
     */
    void msgDraw(BackgroundLayer background, int x, int y);

    /**
     * Player touch this background.
     *
     * @param obj player
     */
    void msgTouch(ObjectItem obj);

    /**
     * Update in special case.
     *
     * @param background background
     * @param x background position x
     * @param y background position y
     */
    void msgUpdate(BackgroundLayer background, int x, int y);

    // TODO add method to clear cache in all background entities
}
