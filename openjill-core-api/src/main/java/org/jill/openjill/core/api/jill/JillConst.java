package org.jill.openjill.core.api.jill;

import org.jill.jn.BackgroundLayer;

/**
 * All const.
 *
 * @author Emeric MARTINEAU
 */
public interface JillConst {
    /**
     * Size (width/height) for a block.
     */
    int BLOCK_SIZE = 16;

    /**
     * Maximum value of screen.
     */
    int MAX_WIDTH = BackgroundLayer.MAP_WIDTH * JillConst.BLOCK_SIZE;

    /**
     * Maximum value of screen.
     */
    int MAX_HEIGHT = BackgroundLayer.MAP_HEIGHT * JillConst.BLOCK_SIZE;

    /**
     * Border to update object.
     */
    int X_UPDATE_SCREEN_BORDER = 6 * JillConst.BLOCK_SIZE;

    /**
     * Border to update object.
     */
    int Y_UPDATE_SCREEN_BORDER = 3 * JillConst.BLOCK_SIZE;

    /**
     * Zapholder vlue of object when touch player.
     */
    int ZAPHOLD_VALUE_AFTER_TOUCH_PLAYER = 3;

    /**
     * Dummy method for checkstyle.
     */
    void nothing();
}
