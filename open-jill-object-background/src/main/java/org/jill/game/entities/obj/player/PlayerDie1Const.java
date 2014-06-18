package org.jill.game.entities.obj.player;

/**
 * Constante for Die other.
 *
 * @author Emeric MARTINEAU
 */
public interface PlayerDie1Const {
    /**
     * Number of image die 2.
     */
    int IMAGE_NUMBER = 5;

    /**
     * Statecount to restart game.
     */
    int STATECOUNT_MAX_TO_RESTART_GAME = 20;

    /**
     * Tile index.
     */
    int TILE_INDEX = 64;

    /**
     * Count to change picture.
     */
    int STATECOUNT_STEP_TO_CHANGE_PICTURE = 4;

    /**
     * TileSet of begin picture.
     */
    int TILESET_INDEX = 8;

    /**
     * First picture.
     */
    int FIRST_PICTURE = 2;

    /**
     * Start value of XD for die 2 (other background).
     */
    int START_YD = -12;

    /**
     * Just for checkstyle and PMD.
     */
    void nothing();
}
