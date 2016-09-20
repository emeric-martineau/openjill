package org.jill.game.manager.object;

import org.jill.game.manager.object.weapon.ObjectMappingWeapon;

/**
 * Class to manager Object by type and weapon.
 *
 * @author Emeric MARTINEAU
 */
public final class ObjectMapping {
    /**
     * Type.
     */
    private int type;

    /**
     * Implementation class.
     */
    private String implementationClass;

    /**
     * Weapon name (Inventory) if class is Weapon.
     */
    private ObjectMappingWeapon weapon;

    /**
     * If this object must be use when load level or restart level (after die).
     */
    private boolean startLevelObject;

    /**
     * Name of object in inventory.
     */
    private String inventoryName;

    /**
     * Type of object.
     *
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * Type of object.
     *
     * @param ty the type to set
     */
    public void setType(final int ty) {
        this.type = ty;
    }

    /**
     * Implementation class.
     *
     * @return the implementationClass
     */
    public String getImplementationClass() {
        return implementationClass;
    }

    /**
     * Implementation class.
     *
     * @param implClass the implementationClass to set
     */
    public void setImplementationClass(final String implClass) {
        this.implementationClass = implClass;
    }

    /**
     * Weapon mapping.
     *
     * @return the weapon
     */
    public ObjectMappingWeapon getWeapon() {
        return weapon;
    }

    /**
     * Weapon mapping.
     *
     * @param weap the weapon to set
     */
    public void setWeapon(final ObjectMappingWeapon weap) {
        this.weapon = weap;
    }

    /**
     * If this object must be use when load level or restart level (after die).
     *
     * @return true/false
     */
    public boolean isStartLevelObject() {
        return startLevelObject;
    }

    /**
     * If this object must be use when load level or restart level (after die).
     *
     * @param startLevelObject
     */
    public void setStartLevelObject(final boolean startLevelObject) {
        this.startLevelObject = startLevelObject;
    }

    /**
     * Name of object in inventory.
     *
     * @return name in inventory
     */
    public String getInventoryName() {
        return inventoryName;
    }

    /**
     * Name of object in inventory.
     *
     * @param inventoryName Name of object in inventory.
     */
    public void setInventoryName(final String inventoryName) {
        this.inventoryName = inventoryName;
    }


}
