package org.jill.jn;

import java.util.List;

/**
 * Save data object (70 bytes lenght).
 *
 * @author Emeric MARTINEAU
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
