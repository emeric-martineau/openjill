package org.jill.cfg;

/**
 * High score.
 *
 * @author Emeric MARTINEAU
 */
public class HighScoreItemImpl implements HighScoreItem {
    /**
     * Name of high score.
     */
    private String name;

    /**
     * Score.
     */
    private int score;

    /**
     * Constructor.
     *
     * @param nm name
     * @param sc score
     */
    HighScoreItemImpl(final String nm, final int sc) {
        this.name = nm;
        this.score = sc;
    }

    /**
     * Name.
     *
     * @return  name
     */
    @Override
    public final String getName() {
        return name;
    }

    /**
     * Socre.
     *
     * @return score
     */
    @Override
    public final int getScore() {
        return score;
    }

    /**
     * Name.
     *
     * @param nm  name
     */
    @Override
    public final void setName(final String nm) {
        this.name = nm;
    }

    /**
     * Score.
     *
     * @param sc score
     */
    @Override
    public final void setScore(final int sc) {
        this.score = sc;
    }
}
