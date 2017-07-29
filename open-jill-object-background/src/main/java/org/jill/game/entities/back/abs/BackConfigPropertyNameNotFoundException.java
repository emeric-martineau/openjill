package org.jill.game.entities.back.abs;

/**
 * If property not found in config file of Object.
 *
 * @author Emeric MARTINEAU
 */
public class BackConfigPropertyNameNotFoundException extends RuntimeException {

    /**
     * Create exception.
     *
     * @param properties name of properties
     * @param configFile name of config file
     */
    public BackConfigPropertyNameNotFoundException(final String properties,
        final String configFile) {
        super(String.format(
            "The key '%s' not found in properties file '%s'",
            properties, configFile));
    }
}
