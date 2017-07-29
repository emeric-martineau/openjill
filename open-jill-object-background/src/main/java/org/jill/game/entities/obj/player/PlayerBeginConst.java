package org.jill.game.entities.obj.player;

/**
 * Constante for begin state.
 *
 * @author Emeric MARTINEAU
 */
public interface PlayerBeginConst {

    /**
     * Index of picture.
     */
    int PICTURE_HEAD_UP = 0;

    /**
     * Index of picture.
     */
    int PICTURE_HEAD_NORMAL = 1;

    /**
     * Index of picture.
     */
    int PICTURE_HEAD_DOWN = 2;

    /**
     * TileSet of begin picture.
     */
    int TILESET_INDEX = 8;

    /**
     * Tile of begin picture.
     */
    int TILE_HEAD_UP_INDEX = 19;

    /**
     * Tile of begin picture.
     */
    int TILE_HEAD_DOWN_INDEX = 18;

    /**
     * Tile of begin picture.
     */
    int TILE_HEAD_NORMAL_INDEX = 16;

    /**
     * Picture Number.
     */
    int PICTURE_NUMBER = 3;

    /**
     * Value of statecount to display picture.
     */
    int PICTURE_HEAD_UP_STATECOUNT = 15;

    /**
     * Value of statecount to display picture.
     */
    int PICTURE_HEAD_NORMAL_STATECOUNT = 24;

    /**
     * Value of statecount to display picture.
     */
    int PICTURE_HEAD_DOWN_STATECOUNT = 33;

    /**
     * Just for checkstyle and PMD.
     */
    void nothing();
}
