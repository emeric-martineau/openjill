package org.jill.game.entities.obj.player;

/**
 * Constante for begin state.
 *
 * @author Emeric MARTINEAU
 */
public interface PlayerJumpingConst {
    /**
     * TileSet of begin picture.
     */
    int TILESET_INDEX = 8;

    /**
     * Picture Number.
     */
    int PICTURE_NUMBER = 3;

    /**
     * Picture index.
     */
    int TILE_MIDDLE_INDEX = 56;

    /**
     * Picture index.
     */
    int TILE_LEFT_INDEX = 32;

    /**
     * Picture index.
     */
    int TILE_RIGHT_INDEX = 40;

    /**
     * Increment value between two jump.
     */
    int JUMP_INCREMENT_VALUE = 2;

    /**
     * Limit of jump speed when player fall.
     */
    int JUMP_FALLING_SPEED_LIMIT = 16;

    /**
     * Initial size of jump.
     */
    int JUMP_INIT_SIZE = 16;

    /**
     * Initial size of jump or climb.
     */
    int JUMP_INIT_SIZE_FOR_CLIMB = 12;

    /**
     * Size of high jump size.
     */
    int HIGH_JUMP_STEP_SIZE = 4;

    /**
     * Just for checkstyle and PMD.
     */
    void nothing();
}
