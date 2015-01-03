package org.jill.game.config;

import java.util.Properties;

import org.jill.openjill.core.api.screen.EnumScreenType;
import org.simplegame.SimpleGameConfig;

/**
 * Specific config.
 *
 * @author Emeric MARTINEAU
 */
public final class JillGameConfig extends SimpleGameConfig {
    /**
     * Jill path file.
     */
    public static final String JILL_PATH_FILE = "jill.pathFile";

    /**
     * Player state to move screen.
     */
    public static final String JILL_SCREENMOVE_STATE
            = "jill.screenmove.player.state";

    /**
     * Player state to move screen.
     */
    public static final String JILL_SCREENMOVE_UP
            = "jill.screenmove.player.yd.up";

    /**
     * Player state to move screen.
     */
    public static final String JILL_SCREENMOVE_DOWN
            = "jill.screenmove.player.yd.down";

    /**
     * Screen type by default.
     */
    public static final String JILL_DEFAULT_SCREEN_TYPE
            = "jill.screen.type.default";

    /**
     * Timeout of display level message.
     */
    public static final String JILL_LEVEL_MESSAGE_TIMEOUT
            = "jill.levelmessage.timeout";

    /**
     * File path of jill data.
     */
    private final String filePath;

    /**
     * Jill state.
     */
    private final int jillState;

    /**
     * Jill up.
     */
    private final int jillUp;

    /**
     * Jill down.
     */
    private final int jillDown;

    /**
     * Screen config.
     */
    private final EnumScreenType typeScreen;

    /**
     * Level message timeout.
     */
    private final int levelMessageTimeout;

    /**
     * Constructor.
     *
     * @param properties file to read
     */
    public JillGameConfig(final Properties properties) {
        super(properties);

        String value = properties.getProperty(JILL_PATH_FILE);

        if (value != null) {
            this.filePath = value;
        } else {
            this.filePath = ".";
        }

        value = properties.getProperty(JILL_SCREENMOVE_STATE);

        this.jillState = Integer.valueOf(value);

        value = properties.getProperty(JILL_SCREENMOVE_UP);

        this.jillUp = Integer.valueOf(value);

        value = properties.getProperty(JILL_SCREENMOVE_DOWN);

        this.jillDown = Integer.valueOf(value);

        value = properties.getProperty(JILL_DEFAULT_SCREEN_TYPE);

        if (value != null) {
            this.typeScreen = EnumScreenType.valueOf(value.toUpperCase());
        } else {
            this.typeScreen = EnumScreenType.VGA;
        }

        value = properties.getProperty(JILL_LEVEL_MESSAGE_TIMEOUT);

        this.levelMessageTimeout = Integer.valueOf(value);
    }

    /**
     * Return file path to file jill data file.
     *
     * @return file path
     */
    public String getFilePath() {
        return this.filePath;
    }

    /**
     * Get type screen.
     *
     * @return type of screen
     */
    public EnumScreenType getTypeScreen() {
        return this.typeScreen;
    }

    /**
     * Return state of player to move screen.
     *
     * @return state
     */
    public int getPlayerMoveScreenState() {
        return this.jillState;
    }

    /**
     * Return yd of player to move screen.
     *
     * @return yd
     */
    public int getPlayerMoveScreenYdUp() {
        return this.jillUp;
    }

    /**
     * Return yd of player to move screen.
     *
     * @return yd
     */
    public int getPlayerMoveScreenYdDown() {
        return this.jillDown;
    }

    /**
     * Level message timeout.
     *
     * @return time in millisecond
     */
    public int getLevelMessageTimeout() {
        return levelMessageTimeout;
    }


}