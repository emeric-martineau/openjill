package org.jill.openjill.core.api.message.statusbar.inventory;

/**
 * Inventory item.
 *
 * @author Emeric MARTINEAU
 */
public enum EnumInventoryObject {
    /**
     * Jill character.
     */
    JILL(0),

    /**
     * Key to open door.
     */
    RED_KEY(1),

    /**
     * Knive to kill enemy.
     */
    KNIVE(2),

    /**
     * Map door key.
     */
    GEM(3),

    /**
     * Frog character.
     */
    FROG(4),

    /**
     * Firebird character.
     */
    FIREBIRD(5),

    /**
     * Not used.
     */
    BAG_OF_COIN(6),

    /**
     * Fish character.
     */
    FISH(7),

    /**
     * Weapon.
     */
    BLADE(8),

    /**
     * Big jump.
     */
    HIGH_JUMP(9),

    /**
     * Invincibility.
     */
    INVINCIBILITY(10);

    /**
     * Index (value) in map file.
     */
    private final int index;

    /**
     * List of enum.
     */
    private static final EnumInventoryObject[] ENUM_LIST;

    static {
        // Sur to init array of inventory as good order
        final EnumInventoryObject[] enumListLocal =
                EnumInventoryObject.values();

        ENUM_LIST = new EnumInventoryObject[enumListLocal.length];

        for (EnumInventoryObject eio : enumListLocal) {
            ENUM_LIST[eio.getIndex()] = eio;
        }
    }

    /**
     * Constructor.
     *
     * @param idx index in map file
     */
    EnumInventoryObject(final int idx) {
        this.index = idx;
    }

    /**
     * Return index value in map file.
     *
     * @return value
     */
    public final int getIndex() {
        return index;
    }

    /**
     * Enum list by index.
     *
     * @return this enum list bu index
     */
    public static EnumInventoryObject[] getEnumList() {
        final EnumInventoryObject[] dest =
                new EnumInventoryObject[ENUM_LIST.length];
        System.arraycopy(ENUM_LIST, 0, dest, 0, ENUM_LIST.length);
        return dest;
    }

}
