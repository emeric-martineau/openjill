package org.jill.openjill.core.api.message;

/**
 * Class to send/dispatch message.
 *
 * @author Emeric MARTINEAU
 */
public interface MessageDispatcher {

    /**
     * Add handler.
     *
     * @param type distination
     * @param handler  handler
     */
    void addHandler(EnumMessageType type, InterfaceMessageGameHandler handler);

    /**
     * Clear message list of handler and message.
     */
    void clear();

    /**
     * Send a message to.
     *
     * @param type distination
     * @param msg  message
     */
    void sendMessage(EnumMessageType type, Object msg);

}
