package org.jill.game.entities.obj.player;

/**
 * Constante for begin state.
 *
 * @author Emeric MARTINEAU
 */
public interface PlayerStandConst {
    /**
     * Picture Number.
     */
    int PICTURE_NUMBER = 5;

    /**
     * Picture number for running.
     */
    int PICTURE_RUNNING_NUMBER = 8;

    /**
     * SubState value to fall.
     */
    int SUBSTATE_VALUE_TO_FALL = 3;

    /**
     * TileSet of begin picture.
     */
    int TILESET_INDEX = 8;

    /**
     * StateCount when player hit floor.
     */
    int HIT_FLOOR_ANIMATION_STATECOUNT = 65529;

    /**
     * StateCount when player hit floor.
     */
    int HIT_FLOOR_ANIMATION_COUNT_END = 5;

    /**
     * Picture index.
     */
    int TILE_LEFT_RUNNING_INDEX = 8;

    /**
     * Picture index.
     */
    int TILE_LEFT_INDEX = 21;

    /**
     * Picture index.
     */
    int TILE_RIGHT_INDEX = 20;

    /**
     * Picture index.
     */
    int TILE_MIDDLE_INDEX = 16;

    /**
     * Picture index.
     */
    int TILE_FALL_INDEX = 60;

    /**
     * Picture index.
     */
    int TILE_DOWN_INDEX = 61;

    /**
     * Picture index.
     */
    int TILE_LEFT_HIT_FLOOR_INDEX0 = 36;

    /**
     * Picture index.
     */
    int TILE_LEFT_HIT_FLOOR_INDEX1 = 20;

    /**
     * Picture index.
     */
    int TILE_LEFT_HIT_FLOOR_INDEX2 = 37;

    /**
     * Picture index.
     */
    int TILE_RIGHT_HIT_FLOOR_INDEX0 = 44;

    /**
     * Picture index.
     */
    int TILE_RIGHT_HIT_FLOOR_INDEX1 = 21;

    /**
     * Picture index.
     */
    int TILE_RIGHT_HIT_FLOOR_INDEX2 = 45;

    /**
     * Picture index.
     */
    int TILE_ARM_INDEX = 17;

    /**
     * Jill return to face.
     */
    int STATECOUNT_LEFT_RIGHT_TO_FACE = 19;

    /**
     * Jill wait with arm.
     */
    int STATECOUNT_WAIT_ARM = 154;

    /**
     * Jill wait display message.
     */
    int STATECOUNT_WAIT_MSG = 254;

    /**
     * Jill wait start animation.
     */
    int STATECOUNT_WAIT_ANIMATION = 272;

    /**
     * Jill wait start end.
     */
    int STATECOUNT_WAIT_END = 301;

    /**
     * Move size for stand player left/right.
     */
    int PLAYER_MOVE_SIZE = 8;

    /**
     * Head up value for yd.
     */
    int Y_SPEED_HEAD_UP = -3;

    /**
     * Head down value for yd.
     */
    int Y_SPEED_HEAD_DOWN = 1;

    /**
     * Squat value for yd.
     */
    int Y_SPEED_SQUAT_DOWN = 3;

    /**
     * Just for checkstyle and PMD.
     */
    void nothing();
}
