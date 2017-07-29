package org.jill.game;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.simplegame.SimpleGame;

/**
 * Start class of game OpenJill.
 *
 * @author Emeric MARTINEAU
 * @version 1.0.0
 */
public final class JillMain {

    /**
     * Private constructor.
     */
    private JillMain() {

    }

    /**
     * Main.
     *
     * @param args command line
     */
    public static void main(final String[] args) {

        final Properties prop = new Properties();

        final InputStream is = JillMain.class.getClassLoader().
            getResourceAsStream("open_jill.properties");

        try {
            if (is == null) {
                Logger.getLogger(JillMain.class.getName()).
                    log(Level.SEVERE,
                        "Could not find open jill config file");
            } else {
                prop.load(is);

                if (args.length > 0) {
                    String[] properties;

                    for (String a : args) {
                        properties = a.split("=");

                        if (properties.length == 2) {
                            prop.setProperty(properties[0], properties[1]);
                        }
                    }
                }

                SimpleGame game = new SimpleGame(prop);

                game.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
