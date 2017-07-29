package org.jill.cfg;

/**
 * Class represent high sore entry.
 *
 * @author Emeric MARTINEAU
 */
public interface HighScoreItem {
    /**
     * Maximal save len of name.
     */
    int LEN_HIGHSCORE_NAME = 7;

    /**
     * Name.
     *
     * @return name
     */
    String getName();

    /**
     * Name.
     *
     * @param nm name
     */
    void setName(final String nm);

    /**
     * Socre.
     *
     * @return score
     */
    int getScore();

    /**
     * Score.
     *
     * @param sc score
     */
    void setScore(final int sc);
}
