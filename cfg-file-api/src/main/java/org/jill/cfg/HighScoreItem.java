/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jill.cfg;

/**
 *
 * @author emeric_martineau
 */
public interface HighScoreItem {
    /**
     * Maximal save len of name.
     */
    int LEN_HIGHSCORE_NAME = 7;

    /**
     * Name.
     *
     * @return  name
     */
    String getName();

    /**
     * Socre.
     *
     * @return score
     */
    int getScore();

    /**
     * Name.
     *
     * @param nm  name
     */
    void setName(final String nm);

    /**
     * Score.
     *
     * @param sc score
     */
    void setScore(final int sc);
}
