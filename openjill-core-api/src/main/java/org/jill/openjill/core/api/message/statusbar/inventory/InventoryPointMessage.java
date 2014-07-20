package org.jill.openjill.core.api.message.statusbar.inventory;

import org.jill.jn.ObjectItem;

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
     * Object kill that have point.
     */
    private final ObjectItem objToKill;

    /**
     * Object touch previous object.
     */
    private final ObjectItem objWhoKill;

    /**
     * Inventory message.
     *
     * @param pt score
     * @param addObject add score
     * @param objKill Object kill that have point
     * @param objTouch Object touch previous object
     */
    public InventoryPointMessage(final int pt, final boolean addObject,
            final ObjectItem objKill, final ObjectItem objTouch) {
        this.point = pt;
        this.add = addObject;
        this.objToKill = objKill;
        this.objWhoKill = objTouch;
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

    /**
     * Object kill that have point.
     *
     * @return object
     */
    public ObjectItem getObjToKill() {
        return objToKill;
    }

    /**
     * Object touch previous object.
     *
     * @return object
     */
    public ObjectItem getObjWhoKill() {
        return objWhoKill;
    }


}
