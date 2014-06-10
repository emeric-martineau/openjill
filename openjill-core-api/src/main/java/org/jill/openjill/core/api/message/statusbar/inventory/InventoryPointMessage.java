package org.jill.openjill.core.api.message.statusbar.inventory;

/**
 * Class permit add inventory.
 *
 * @author Emeric MARTINEAU
 */
public final class InventoryPointMessage {
    /**
     * Point.
     */
    private final int point;

    /**
     * If add object. False = delete object.
     */
    private final boolean add;

    /**
     * Inventory message.
     *
     * @param pt score
     * @param addObject add score
     */
    public InventoryPointMessage(final int pt, final boolean addObject) {
        this.point = pt;
        this.add = addObject;
    }

    /**
     * Return object to be concerned.
     *
     * @return point
     */
    public int getPoint() {
        return point;
    }

    /**
     * If add object.
     *
     * @return  false = delete object from inventory
     */
    public boolean isAddObject() {
        return add;
    }
}
