package org.simplegame;

/**
 * Class to changer handler of game (timer and keyboard).
 *
 * @author Emeric MARTINEAU
 */
public final class SimpleGameHandler {
    /**
     * Current handler.
     */
    private static InterfaceSimpleGameHandleInterface currentHandler = null;

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
        currentHandler = newHandler;
    }

    /**
     * Return current handler.
     *
     * @return current handler
     */
    public static InterfaceSimpleGameHandleInterface getHandler() {
        return currentHandler;
    }
}
