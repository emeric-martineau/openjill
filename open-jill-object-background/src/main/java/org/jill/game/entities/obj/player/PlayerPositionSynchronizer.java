package org.jill.game.entities.obj.player;

import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.player.GetPlayerPositionMessage;
import org.jill.openjill.core.api.message.MessageDispatcher;

/**
 * Some object need player position.
 *
 * @author Emeric Martineau
 */
public final class PlayerPositionSynchronizer {
    /**
     * Instance of object.
     */
    private static final PlayerPositionSynchronizer INSTANCE
            = new PlayerPositionSynchronizer();

    /**
     * Object to get player position.
     */
    private static final GetPlayerPositionMessage GET_PLAYER_POSITION
            = new GetPlayerPositionMessage();

    /**
     * Position of player.
     */
    private int x;

    /**
     * Position of player.
     */
    private int y;

    /**
     * Index to get player position once per cycle.
     */
    private int indexEtat = 0;

    /**
     * Instance.
     */
    private PlayerPositionSynchronizer() {

    }

    /**
     * Instance.
     *
     * @return single instance.
     */
    public static PlayerPositionSynchronizer getInstance() {
        return INSTANCE;
    }

    /**
     * Update index.
     *
     * @param messageDispatcher message dispatcher
     * @param currentIndex index of object
     *
     * @return new index
     */
    public int updatePlayerPosition(final MessageDispatcher messageDispatcher,
            final int currentIndex) {
        if (currentIndex == this.indexEtat) {
            this.indexEtat++;

            messageDispatcher.sendMessage(
                            EnumMessageType.PLAYER_GET_POSITION,
                    GET_PLAYER_POSITION);

            this.x = GET_PLAYER_POSITION.getX();
            this.y = GET_PLAYER_POSITION.getY();
        }

        return this.indexEtat;
    }

    /**
     * Return X.
     *
     * @return x
     */
    public int getX() {
        return this.x;
    }

    /**
     * Return Y.
     *
     * @return y
     */
    public int getY() {
        return this.y;
    }
}
