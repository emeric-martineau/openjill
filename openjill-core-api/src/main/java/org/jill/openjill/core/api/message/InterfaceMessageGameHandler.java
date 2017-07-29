package org.jill.openjill.core.api.message;

/**
 * Interface to recieve notification of message.
 *
 * @author Emeric MARTNIEAU
 */
public interface InterfaceMessageGameHandler {
    /**
     * Receive message.
     *
     * @param type message type
     * @param msg message
     */
    void recieveMessage(EnumMessageType type, Object msg);
}
