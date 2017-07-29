package org.jill.openjill.core.api.message.statusbar.inventory;

/**
 * Class permit add inventory.
 *
 * @author Emeric MARTINEAU
 */
public final class InventoryItemMessage {
    /**
     * Object.
     */
    private EnumInventoryObject obj;

    /**
     * If add object. False = delete object.
     */
    private boolean add;

    /**
     * Item is last in inventory list.
     */
    private boolean lastItemInv = false;

    /**
     * To know if item already exist don't add it.
     */
    private boolean alone = false;

    /**
     * Constructor.
     *
     * @param inv object
     * @param addInv add object
     * @param lastItem if last inventory item
     * @param once if alone in inventory
     */
    public InventoryItemMessage(final EnumInventoryObject inv,
            final boolean addInv, final boolean lastItem, final boolean once) {
        this(inv, addInv, once);

        this.lastItemInv = lastItem;
    }

    /**
     * Constructor.
     *
     * @param inv object
     * @param addInv add object
     */
    public InventoryItemMessage(final EnumInventoryObject inv,
            final boolean addInv) {
        this.obj = inv;
        this.add = addInv;
    }

    /**
     * Constructor.
     *
     * @param inv object
     * @param addInv add object
     */
    public InventoryItemMessage(final String inv, final boolean addInv) {
        this.obj = EnumInventoryObject.valueOf(inv);
        this.add = addInv;
    }

    /**
     * Constructor.
     *
     * @param inv object
     * @param addInv add object
     * @param once if alone in inventory
     */
    public InventoryItemMessage(final EnumInventoryObject inv,
            final boolean addInv, final boolean once) {
        this(inv, addInv);

        this.alone = once;
    }

    /**
     * Constructor.
     *
     * @param inv object
     * @param addInv add object
     * @param once if alone in inventory
     */
    public InventoryItemMessage(final String inv, final boolean addInv,
            final boolean once) {
        this(inv, addInv);

        this.alone = once;
    }

    /**
     * Return object to be concerned.
     *
     * @return inventory object
     */
    public EnumInventoryObject getObj() {
        return this.obj;
    }

    /**
     * If add object.
     *
     * @return  false = delete object from inventory
     */
    public boolean isAddObject() {
        return this.add;
    }

    /**
     * Item is last in inventory list.
     *
     * @return true/false
     */
    public boolean isLastItemInv() {
        return this.lastItemInv;
    }

    /**
     * If inventory must be alone in inventory.
     *
     * @return true/false
     */
    public boolean isAlone() {
        return this.alone;
    }
}
