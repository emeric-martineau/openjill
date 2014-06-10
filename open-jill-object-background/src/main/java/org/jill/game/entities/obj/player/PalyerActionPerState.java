package org.jill.game.entities.obj.player;

/**
 * List of player action can do per action.
 * Don't use enum cause if, more clear in code.
 *
 * @author Emeric MARTINEAU
 */
public final class PalyerActionPerState {
    /**
     * Size of state info.
     */
    private static final int STATE_INFO_SIZE = 6;

    /**
     * Stat info.
     */
    private static final int[] STATE_INFO = new int[STATE_INFO_SIZE];

    static {
        STATE_INFO[PlayerState.BEGIN] = PlayerAction.INVINCIBLE;
        STATE_INFO[PlayerState.STAND] = PlayerAction.CANFIRE
                | PlayerAction.CANMOVE | PlayerAction.CANJUMP;
        STATE_INFO[PlayerState.STILL] = PlayerAction.INVINCIBLE
                | PlayerAction.CANMOVE | PlayerAction.CANJUMP;
        STATE_INFO[PlayerState.JUMPING] = PlayerAction.CANFIRE
                | PlayerAction.CANMOVE;
        STATE_INFO[PlayerState.CLIMBING] = PlayerAction.CANMOVE
                | PlayerAction.CANJUMP;
        STATE_INFO[PlayerState.DIE] = PlayerAction.INVINCIBLE;
    }

    /**
     * Pivate constructor.
     */
    private PalyerActionPerState() {
        // Nothing
    }

    /**
     * Return if player can do this action.
     *
     * @param palyerState player state
     * @param action type of action
     *
     * @return true/false
     */
    public static boolean canDo(final int palyerState, final int action) {
        return (STATE_INFO[palyerState] & action) != 0;
    }
}
