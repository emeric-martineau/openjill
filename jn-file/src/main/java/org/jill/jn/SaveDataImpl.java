package org.jill.jn;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import org.jill.file.FileAbstractByte;


/**
 * Save data object (70 bytes lenght).
 *
 * @author Emeric MARTINEAU
 * @version 1.0
 */
public class SaveDataImpl implements SaveData {

    /**
     * Display level.
     */
    private final int level;

    /**
     * X-coordinate of object.
     */
    private final int health;

    /**
     * Y-coordinate of object.
     */
    private final int score;

    /**
     * List of inventory item (knife, gem...).
     */
    private final List<Integer> inventory;

    /**
     * Offset in file of this record.
     */
    private final int offset;

    /**
     * Constructor.
     *
     * @param jnFile file
     *
     * @throws IOException if error
     */
    public SaveDataImpl(final FileAbstractByte jnFile) throws IOException {
        this.offset = (int) jnFile.getFilePointer();

        level = jnFile.read16bitLE();
        health = jnFile.read16bitLE();

        final int nbInventory =  jnFile.read16bitLE();
        inventory = new ArrayList<>(nbInventory);
        int index;

        for (index = 0; index < nbInventory; index++) {
            inventory.add(jnFile.read16bitLE());
        }

        final int skipEntry = MAX_INVENTORY_ENTRY - nbInventory;

        for (index = 0; index < skipEntry; index++) {
            jnFile.read16bitLE();
        }

        score = jnFile.read32bitLE();

        // Skip to go 70 bytes
        jnFile.skipBytes(SAVE_HOLE);
    }

    /**
     * Leve.
     *
     * @return level level
     */
    @Override
    public final int getLevel() {
        return level;
    }

    /**
     * Health.
     *
     * @return health health
     */
    @Override
    public final int getHealth() {
        return health;
    }

    /**
     * Score.
     *
     * @return score score
     */
    @Override
    public final int getScore() {
        return score;
    }

    /**
     * Inventory.
     *
     * @return inventory inventory
     */
    @Override
    public final List<Integer> getInventory() {
        return inventory;
    }

    /**
     * Return offset in file for this object.
     *
     * @return offset
     */
    @Override
    public final int getOffset() {
        return offset;
    }
}
