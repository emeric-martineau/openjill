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
public interface SaveGameItem {
    /**
     * Maximal save len of name.
     */
    int LEN_SAVE_NAME = 7;

    /**
     * Name.
     *
     * @return name
     */
    String getName();

    /**
     * Save file name.
     *
     * @return filename
     */
    String getSaveGameFile();

    /**
     * Map file name.
     *
     * @return filename
     */
    String getSaveMapFile();

    /**
     * Set name of save entry.
     *
     * @param nm name
     */
    void setName(String nm);
}
