/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jill.jn;

import java.util.List;

/**
 *
 * @author emeric_martineau
 */
public interface SaveData {
    /**
     * Level map.
     */
    int MAP_LEVEL = 0x7f;
    /**
     * Number max of entry in inventory.
     */
    int MAX_INVENTORY_ENTRY = 16;
    /**
     * Hole in save, not used.
     */
    int SAVE_HOLE = 28;
    /**
     * Size in file (fixed).
     */
    int SIZE_IN_FILE = 70;

    /**
     * Health.
     *
     * @return health health
     */
    int getHealth();

    /**
     * Inventory.
     *
     * @return inventory inventory
     */
    List<Integer> getInventory();

    /**
     * Leve.
     *
     * @return level level
     */
    int getLevel();

    /**
     * Return offset in file for this object.
     *
     * @return offset
     */
    int getOffset();

    /**
     * Score.
     *
     * @return score score
     */
    int getScore();

}
