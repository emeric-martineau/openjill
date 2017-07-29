package org.jill.game.entities.obj.player;

/**
 * List of player action can do. Don't use enum cause it's more clear in code.
 *
 * @author Emeric MARTINEAU
 */
public interface PlayerAction {
    /**
     * Player do nothing.
     */
    int NOTHING = 0;

    /**
     * Player can fire.
     */
    int CANFIRE = 1;

    /**
     * Player is invincible.
     */
    int INVINCIBLE = 2;

    /**
     * Player can move.
     */
    int CANMOVE = 4;

    /**
     * Player can jump.
     */
    int CANJUMP = 8;
}
