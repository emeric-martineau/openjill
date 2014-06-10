package org.jill.game.entities.obj.player;

/**
 * Constante for begin state.
 *
 * @author Emeric MARTINEAU
 */
public interface PlayerClimbConst {
    /**
     * Picture Number.
     */
    int PICTURE_NUMBER = 6;

    /**
     * Picture number for running.
     */
    int PICTURE_RUNNING_NUMBER = 8;

    /**
     * SubState value when jump.
     */
    int SUBSTATE_JUMP_UP = 2;

    /**
     * SubState value when jump.
     */
    int SUBSTATE_JUMP_STOP = 0;

    /**
     * SubState index to size of move.
     */
    int SUBSTATE_TO_CLIMB_MOVE = 3;

    /**
     * TileSet of begin picture.
     */
    int TILESET_INDEX = 8;

    /**
     * Picture index.
     */
    int TILE_ONE = 24;

    /**
     * Picture index.
     */
    int TILE_TWO = 25;

    /**
     * Picture index.
     */
    int TILE_THREE = 26;

    /**
     * Player climb size to up.
     *
     * /!\ If length of array change, don't forget du update
     * PlayerManager.stClimbPicture array and it's init in PlayerManager()
     * constructor.
     */
    int[] PLAYER_MOVE_SIZE_CLIMB_UP = {0, 0, -6, -4, -4, -4};

    /**
     * Move size for climbing up/down.
     */
    int PLAYER_MOVE_SIZE_CLIMB_DOWN = 4;

    /**
     * Substate player down.
     */
    int PLAYER_SUBSTATE_DOWN = 2;

    /**
     * Just for checkstyle and PMD.
     */
    void nothing();
}
