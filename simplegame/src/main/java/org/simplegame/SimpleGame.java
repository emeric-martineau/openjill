package org.simplegame;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple game.
 *
 * @author Emeric MARTINEAU
 */
public class SimpleGame {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(
                    SimpleGame.class.getName());

    /**
     * Frame of game.
     */
    protected SimpleGameJFrame jframe;

    /**
     * Constructor.
     *
     * @param properties properties of game
     */
    public SimpleGame(final Properties properties) {
        loadConfigClass(properties);

        // Load start class
        loadStartClass(
                SimpleGameConfig.getInstance().getGameStartClass());

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                jframe = new SimpleGameJFrame(
                        SimpleGameConfig.getInstance().getGameTitle(),
                        SimpleGameConfig.getInstance().getGameWidth(),
                        SimpleGameConfig.getInstance().getGameHeight(),
                        SimpleGameConfig.getInstance().getGameTimerDelay(),
                        SimpleGameConfig.getInstance().getZoom());
            }
        });
    }

    /**
     * Load config class.
     *
     * @param properties properties of game
     */
    private void loadConfigClass(final Properties properties) {
        // Read start class
        final String value = properties.getProperty("game.configClass");

        if (value == null) {
            SimpleGameConfig.setInstance(new SimpleGameConfig(properties));
            return;
        }

        try {
            Class configClass = Class.forName(value);

            final Object o = configClass.
                getConstructor(Properties.class).
                newInstance(properties);

            final SimpleGameConfig config = (SimpleGameConfig) o;
            SimpleGameConfig.setInstance(config);
        } catch (IllegalArgumentException | SecurityException |
                InvocationTargetException | NoSuchMethodException |
                IllegalAccessException | InstantiationException |
                ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Error reading config file.", ex);
        }
    }

    /**
     * Load start game class.
     *
     * @param value class name
     */
    private void loadStartClass(final String value) {
        if (value == null) {
            return;
        }

        try {
            Class startClass = Class.forName(value);

            SimpleGameHandler.setNewHandler(
                    (InterfaceSimpleGameHandleInterface)
                    startClass.newInstance());
        } catch (IllegalAccessException | InstantiationException |
                ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE,
                    "Error reading config file (start class).", ex);
        }
    }

    /**
     * Start game.
     */
    public final void start() {
        try {
            // Wait for jframe create
            while (jframe == null) {
                    Thread.sleep(1000);
            }

            jframe.start();
        } catch (InterruptedException ex) {
            Logger.getLogger(SimpleGame.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    /**
     * Stop game.
     */
    public final void stop() {
        jframe.stop();
    }
}
