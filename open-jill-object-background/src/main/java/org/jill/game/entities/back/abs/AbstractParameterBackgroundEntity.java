package org.jill.game.entities.back.abs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jill.game.entities.BackgroundEntityImpl;
import org.jill.openjill.core.api.entities.BackgroundParam;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Abstract.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractParameterBackgroundEntity extends
        BackgroundEntityImpl {
    /**
     * Config file name.
     */
    private static final String CONFIG_FILENAME = "background_conf.json";

    /**
     * Configuration.
     */
    private static Map<String, Map<String, String>> conf;

    static {
        final ObjectMapper mapper = new ObjectMapper();
        final InputStream is = AbstractParameterBackgroundEntity.class.
                getClassLoader().getResourceAsStream(CONFIG_FILENAME);

        try {
            conf = mapper.readValue(is,
                    new TypeReference<Map<String, Map<String, String>>>() {
                    });
        } catch (IOException e) {
            Logger.getLogger(
                    AbstractParameterBackgroundEntity.class.getName()).
                    log(Level.SEVERE,
                            "Can't load background config file !", e);
        }
    }

    /**
     * Name in DMA file.
     */
    private String dmaName;

    /**
     * For internal use only.
     */
    protected AbstractParameterBackgroundEntity() {
        super();
    }

    /**
     * Constructor.
     *
     * @param backParam background parameter
     */
    @Override
    public void init(final BackgroundParam backParam) {
        super.init(backParam);

        dmaName = getDmaEntry().getName();
    }

    /**
     * Allow chande dmaName.
     *
     * @param newDmaName new dmaName.
     */
    protected void setDmaName(final String newDmaName) {
        this.dmaName = newDmaName;
    }

    /**
     * Configuration string.
     *
     * @param properties properties file
     * @return value
     */
    protected final String getConfString(final String properties) {

        return getConfString(properties, true);
    }

    /**
     * Configuration string.
     *
     * @param properties properties file
     * @param requiered  if properties requiered
     * @return value
     */
    protected final String getConfString(final String properties,
            final boolean requiered) {
        String value;

        if (conf.containsKey(this.dmaName)) {
            final Map<String, String> classConf = conf.get(this.dmaName);

            if (!requiered || classConf.containsKey(properties)) {
                value = classConf.get(properties);
            } else {
                // Property not found for class
                throw new BackConfigPropertyNameNotFoundException(properties,
                        CONFIG_FILENAME);
            }
        } else {
            // Class not found in properties file
            throw new BackConfigPropertyNameNotFoundException(this.dmaName,
                    CONFIG_FILENAME);
        }

        return value;
    }

    /**
     * Configuration string.
     *
     * @param properties properties file
     * @return value
     */
    protected final int getConfInteger(final String properties) {
        return Integer.valueOf(getConfString(properties));
    }

    /**
     * Configuration string.
     *
     * @param properties properties file
     * @param def        default value
     * @return value
     */
    protected final int getConfInteger(final String properties, final int def) {
        final String value = getConfString(properties, false);

        int result;

        if (value == null) {
            result = def;
        } else {
            result = Integer.valueOf(value);
        }

        return result;
    }
}
