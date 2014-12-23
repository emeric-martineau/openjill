package org.jill.game.gui.conf;

import java.util.List;
import java.util.Map;

/**
 * Configuration object for level message.
 *
 * @author Emeric MARTINEAU
 */
public final class LevelMessageConf extends MessageConf {
    /**
     * Message for level.
     */
    private Map<String, List<String>> messages;

    public Map<String, List<String>> getMessages() {
        return messages;
    }

    /**
     * Map (extension of file : JN1) with list of message.
     *
     * @param mmessages message.
     */
    public void setMessages(final Map<String, List<String>> mmessages) {
        this.messages = mmessages;
    }
}
