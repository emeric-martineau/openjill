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
    private EnumScreenType typeScreen = EnumScreenType.VGA;

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
     * Set type screen.
     *
     * @param tpScreen type of screen
     */
    public void setTypeScreen(final EnumScreenType tpScreen) {
        this.typeScreen = tpScreen;
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
}