package org.jill.game.entities.obj;

/**
 * Redkey for door.
 *
 * @author Emeric MARTINEAU
 */
public final class RedKeyManager extends AbstractKeyManager {
    /**
     * To know if message must be display.
     */
    private static boolean messageDisplayRedKeyMessage = true;

    @Override
    protected boolean getDisplayMessage() {
        boolean oldValue = messageDisplayRedKeyMessage;
        messageDisplayRedKeyMessage = false;

        return oldValue;
    }
}
