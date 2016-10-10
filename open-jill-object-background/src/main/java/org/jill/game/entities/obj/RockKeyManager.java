package org.jill.game.entities.obj;

/**
 * Rockkey for mapdoor.
 *
 * @author Emeric MARTINEAU
 */
public final class RockKeyManager
    extends AbstractKeyManager {

    /**
     * To know if message must be display.
     */
    private static boolean messageDisplayRockMessage = true;

    @Override
    protected boolean getDisplayMessage() {
        boolean oldValue = messageDisplayRockMessage;
        messageDisplayRockMessage = false;

        return oldValue;
    }
}
