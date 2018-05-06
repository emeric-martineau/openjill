package org.simplegame;

import java.util.Optional;

/**
 * Class to changer handler of game (timer and keyboard).
 *
 * @author Emeric MARTINEAU
 */
public final class SimpleGameHandler {
    /**
     * Current handler.
     */
    private static Optional<InterfaceSimpleGameHandleInterface> currentHandler = Optional.empty();

    /**
     * Private constructor.
     */
    private SimpleGameHandler() {

    }

    /**
     * Set current handler.
     *
     * @param newHandler new handler
     */
    public static void setNewHandler(
            final InterfaceSimpleGameHandleInterface newHandler) {
        currentHandler = Optional.ofNullable(newHandler);
    }

    /**
     * Return current handler.
     *
     * @return current handler
     */
    public static Optional<InterfaceSimpleGameHandleInterface> getHandler() {
        return currentHandler;
    }
}
