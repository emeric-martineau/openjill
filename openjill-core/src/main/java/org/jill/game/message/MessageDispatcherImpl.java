package org.jill.game.message;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.InterfaceMessageGameHandler;
import org.jill.openjill.core.api.message.MessageDispatcher;

/**
 * Class to send/dispatch message.
 *
 * @author Emeric MARTINEAU
 */
public class MessageDispatcherImpl implements MessageDispatcher {
    /**
     * List of handler.
     */
    private final Map<EnumMessageType, List<InterfaceMessageGameHandler>>
            handlersList = new EnumMap<>(EnumMessageType.class);

    /**
     * List of unsend messsage.
     */
    private final Map<EnumMessageType, List<Object>>
            unsendMessageList = new EnumMap<>(EnumMessageType.class);

    /**
     * Constructor.
     */
    public MessageDispatcherImpl() {
        for (EnumMessageType mt : EnumMessageType.values()) {
            handlersList.put(mt, new ArrayList<InterfaceMessageGameHandler>());
            unsendMessageList.put(mt, new ArrayList());
        }
    }

    /**
     * Send a message to.
     *
     * @param type distination
     * @param msg  message
     */
    @Override
    public final void sendMessage(final EnumMessageType type,
            final Object msg) {
        List<InterfaceMessageGameHandler> handlers;

        handlers = handlersList.get(type);

        if (handlers.isEmpty()) {
            final List<Object> list = unsendMessageList.get(type);

            list.add(msg);
        } else {
            for (InterfaceMessageGameHandler mgh : handlers) {
                mgh.recieveMessage(type, msg);
            }
        }
    }

    /**
     * Add handler.
     *
     * @param type distination
     * @param handler  handler
     */
    @Override
    public final void addHandler(final EnumMessageType type,
            final InterfaceMessageGameHandler handler) {
        final List<InterfaceMessageGameHandler> handlers =
                handlersList.get(type);

        handlers.add(handler);

        final List<Object> list = unsendMessageList.get(type);

        if (!list.isEmpty()) {
            for (Object obj : list) {
                handler.recieveMessage(type, obj);
            }

            list.clear();
        }
    }

    /**
     * Clear message list of handler and message.
     */
    @Override
    public final void clear() {
        List<InterfaceMessageGameHandler> handlers;

        for (EnumMessageType mt : EnumMessageType.values()) {
            handlers = handlersList.get(mt);

            handlers.clear();
        }
    }

    /**
     * dispatch message
     */
    /*
    public void dispatch() {
        List<Object> messages;
        List<MessageGameHandler> handlers;

        for(MessageType mt : MessageType.values()) {
            messages = messagesList.get(mt);
            handlers = handlersList.get(mt);

            for(Object o : messages) {
                for(MessageGameHandler mgh : handlers) {
                    mgh.recieveMessage(mt, o);
                }
            }

            messages.clear();
        }
    }*/
}
