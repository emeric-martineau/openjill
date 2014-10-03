package org.jill.game.entities.obj.player;

/**
 * List of player state. Don't use enum cause it's more clear in code.
 *
 * @author Emeric MARTINEAU
 */
public interface PlayerState {

    /**
     * Only use to move player and nothing change state.
     */
    int NOTHING_CHANGE = -1;

    /**
     * Stand.
     */
    int STAND = 0;

    /**
     * Still.
     */
    int STILL = 1;

    /**
     * Jumping.
     */
    int JUMPING = 2;

    /**
     * Climb.
     */
    int CLIMBING = 3;

    /**
     * Begin.
     */
    int BEGIN = 4;

    /**
     * Die.
     */
    int DIE = 5;

    /**
     * Sub state of die.
     */
    int DIE_SUB_STATE_ENNEMY = 0;

    /**
     * Sub state of die.
     */
    int DIE_SUB_STATE_WATER_BACK = 1;

    /**
     * Sub state of die.
     */
    int DIE_SUB_STATE_OTHER_BACK = 2;

    /**
     * Begin.
     */
    int BEGIN_SUB_STATE = 18;
}
