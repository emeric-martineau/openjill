package org.jill.game.entities.obj.abs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jill.game.entities.ObjectEntityImpl;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.statusbar.StatusBarTextMessage;


/**
 * Abstract object with parameter file.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractParameterObjectEntity extends ObjectEntityImpl {

    /**
     * Config file name.
     */
    private static final String CONFIG_FILENAME = "object_conf.json";

    /**
     * Configuration.
     */
    private static Map<String, Map<String, String>> conf;

    static {
        final ObjectMapper mapper = new ObjectMapper();
        final InputStream is = AbstractParameterObjectEntity.class.
                getClassLoader().getResourceAsStream(CONFIG_FILENAME);

        try {
            conf = mapper.readValue(is,
                    new TypeReference<Map<String, Map<String, String>>>() { });
        } catch (IOException e) {
            Logger.getLogger(
                AbstractParameterObjectEntity.class.getName()).
                log(Level.SEVERE,
                    "Can't load object config file !", e);
        }
    }

    /**
     * Configuration string.
     *
     * @param properties properties file
     *
     * @return value
     */
    protected final String getConfString(final String properties) {
        return getConfString(properties, true);
    }

    /**
     * Configuration string.
     *
     * @param properties properties file
     * @param requiered if properties requiered
     *
     * @return value
     */
    protected final String getConfString(final String properties,
            final boolean requiered) {
        final String className = this.getClass().getSimpleName();

        String value;

        if (conf.containsKey(className)) {
            final Map<String, String> classConf = conf.get(className);

            if (!requiered || classConf.containsKey(properties)) {
                value = classConf.get(properties);
            } else {
                // Property not found for class
                throw new ObjectConfigPropertyNameNotFoundException(properties,
                    CONFIG_FILENAME);
            }
        } else {
            // Class not found in properties file
            throw new ObjectConfigPropertyNameNotFoundException(className,
                CONFIG_FILENAME);
        }

        return value;
    }

    /**
     * Configuration string.
     *
     * @param properties properties file
     *
     * @return value
     */
    protected final int getConfInteger(final String properties) {
        return Integer.valueOf(getConfString(properties));
    }

    /**
     * Send default message.
     */
    protected final void sendMessage() {
        final String textMsg = getConfString("msg");
        final int textColor = getConfInteger("msgColor");
        final int textTime = getConfInteger("msgTime");

        final StatusBarTextMessage msg = new StatusBarTextMessage(textMsg,
                textTime, textColor);

        this.messageDispatcher.sendMessage(
                    EnumMessageType.MESSAGE_STATUS_BAR, msg);
    }
    
    /**
     * Send default message.
     * 
     * @param textMsg message to display.
     */
    protected final void sendMessage(final String textMsg) {
        final int textColor = getConfInteger("msgColor");
        final int textTime = getConfInteger("msgTime");

        final StatusBarTextMessage msg = new StatusBarTextMessage(textMsg,
                textTime, textColor);

        this.messageDispatcher.sendMessage(
                    EnumMessageType.MESSAGE_STATUS_BAR, msg);
    }
}