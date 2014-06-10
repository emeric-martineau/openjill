package org.jill.game.entities.obj.player;

/**
 * Constante for Die other.
 *
 * @author Emeric MARTINEAU
 */
public interface PlayerDie2Const {

    /**
     * Number of image die 2.
     */
    int IMAGE_NUMBER = 4;

    /**
     * Index of first picture.
     */
    int FIRST_PICTURE = 0;

    /**
     * Y speed to start animation.
     */
    int YD_MAX_TO_CHANGE = 13;

    /**
     * Statcount to chante animation.
     */
    int STATECOUNT_MAX_TO_FIRST_ANIMATION = 15;

    /**
     * Statecount to restart game.
     */
    int STATECOUNT_MAX_TO_RESTART_GAME = 20;

    /**
     * Index of last picture.
     */
    int LAST_PICTURE = 3;

    /**
     * Start value of XD for die 2 (other background).
     */
    int START_YD = -10;

    /**
     * TileSet of begin picture.
     */
    int TILESET_INDEX = 8;

    /**
     * Picture tile.
     */
    int TILE0 = 66;

    /**
     * Picture tile.
     */
    int TILE1 = 72;

    /**
     * Picture tile.
     */
    int TILE2 = 73;

    /**
     * Picture tile.
     */
    int TILE3 = 74;

    /**
     * Just for checkstyle and PMD.
     */
    void nothing();
}
