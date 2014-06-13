package org.simplegame;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to changer handler of game (timer and keyboard).
 *
 * @author Emeric MARTINEAU
 */
public class SimpleGameConfig {
    /**
     * Instance.
     */
    private static SimpleGameConfig instance = null;

    /**
     * Pattern du workspace.
     */
    private static final Pattern PATTERN_INTEGER =
            Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE);

    /**
     * Default timer delay.
     */
    public static final int DEFAULT_TIMER_DELAY = 55;

    /**
     * Default game width.
     */
    public static final int DEFAULT_GAME_WIDTH = 320;

    /**
     * Default game height.
     */
    public static final int DEFAULT_GAME_HEIGHT = 200;

    /**
     * Key.
     */
    public static final String GAME_TIMER_DELAY = "game.timer.delay";

    /**
     * Key.
     */
    public static final String GAME_TITLE = "game.title";

    /**
     * Key.
     */
    public static final String GAME_WIDTH = "game.width";

    /**
     * Key.
     */
    public static final String GAME_HEIGHT = "game.height";

    /**
     * Key.
     */
    public static final String GAME_START_CLASS = "game.startClass";

    /**
     * Key.
     */
    public static final String GAME_SCREEN_ZOOM = "game.zoom";

    /**
     * Timer delay (default DEFAULT_TIMER_DELAY).
     */
    private int gameTimerDelay = DEFAULT_TIMER_DELAY;

    /**
     * Game title.
     */
    private String gameTitle;

    /**
     * Game width (default DEFAULT_GAME_WIDTH).
     */
    private int gameWidth = DEFAULT_GAME_WIDTH;

    /**
     * Game Height (default DEFAULT_GAME_HEIGHT).
     */
    private int gameHeight = DEFAULT_GAME_HEIGHT;

    /**
     * Game start class.
     */
    private final String gameStartClass;

    /**
     * Zoom screen.
     */
    private final int zoom;

    /**
     * Constructor.
     *
     * @param properties properties
     */
    public SimpleGameConfig(final Properties properties) {
        gameTimerDelay = getInteger(
                properties.getProperty(GAME_TIMER_DELAY));

        String value = properties.getProperty(GAME_TITLE);

        if (value != null) {
            gameTitle = value;
        }

        gameWidth = getInteger(
                properties.getProperty(GAME_WIDTH));

        gameHeight = getInteger(
                properties.getProperty(GAME_HEIGHT));

        gameStartClass = properties.getProperty(GAME_START_CLASS);

         zoom = getInteger(
                    properties.getProperty(GAME_SCREEN_ZOOM));
    }

    /**
     * Get integer value.
     *
     * @param value integer value
     *
     * @return integer
     */
    private int getInteger(final String value) {
        int convertedValue = 0;

        if (value != null) {
            // Creer le matcher
            final Matcher matcher = PATTERN_INTEGER.matcher(value);

            if (matcher.matches()) {
                convertedValue = Integer.valueOf(value);
            }
        }

        return convertedValue;
    }

    /**
     * Set config only if never set !
     *
     * @param game config
     */
    public static void setInstance(final SimpleGameConfig game) {
        if (instance == null) {
            instance = game;
        }
    }

    /**
     * Game config.
     *
     * @return  game config
     */
    public static SimpleGameConfig getInstance() {
        return instance;
    }

    /**
     * Timer delay.
     *
     * @return timer delay
     */
    public final int getGameTimerDelay() {
        return gameTimerDelay;
    }

    /**
     * Tile.
     *
     * @return game title
     */
    public final String getGameTitle() {
        return gameTitle;
    }

    /**
     * Window width.
     *
     * @return width
     */
    public final int getGameWidth() {
        return gameWidth;
    }

    /**
     * Window height.
     *
     * @return height
     */
    public final int getGameHeight() {
        return gameHeight;
    }

    /**
     * Start class.
     *
     * @return class
     */
    public final String getGameStartClass() {
        return gameStartClass;
    }

    /**
     * Zoom.
     *
     * @return zoom
     */
    public final int getZoom() {
        return zoom;
    }
}
