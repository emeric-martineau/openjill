package org.jill.game.manager.object.weapon;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class to manager Object weapon.
 *
 * @author Emeric MARTINEAU
 */
public class ObjectMappingWeapon {
    /**
     * Order of check weapon. I.e. in inventory if blade
     * and knive are present, create blade, then if
     * player fire again, create knive.
     */
    private int order;
    
    /**
     * Name of inventory key.
     */
    private String inventoryKey;
    
    /**
     * For knive, remove entry in inventory.
     */
    private boolean removeInInventory;
    
    /**
     * How many object can be create by item.
     */
    private int numberItemPerInventory;

    /**
     * Type of object.
     */
    @JsonIgnore
    private int type;
    
    /**
     * Order of weapon to fire.
     * 
     * @return integer
     */
    public int getOrder() {
        return order;
    }

    /**
     * Order of weapon to fire.
     * 
     * @param order order
     */
    public void setOrder(final int order) {
        this.order = order;
    }

    /**
     * Name of inventory key.
     * 
     * @return name
     */
    public String getInventoryKey() {
        return inventoryKey;
    }

    /**
     * Name of inventory key.
     * 
     * @param inventoryKey name of weapon
     */
    public void setInventoryKey(final String inventoryKey) {
        this.inventoryKey = inventoryKey;
    }

    /**
     * For knive, remove entry in inventory.
     * 
     * @return true/false
     */
    public boolean isRemoveInInventory() {
        return removeInInventory;
    }

    /**
     * For knive, remove entry in inventory.
     * 
     * @param removeInInventory true/false
     */
    public void setRemoveInInventory(final boolean removeInInventory) {
        this.removeInInventory = removeInInventory;
    }

    /**
     * How many object can be create by item.
     * 
     * @return integer
     */
    public int getNumberItemPerInventory() {
        return numberItemPerInventory;
    }

    /**
     * How many object can be create by item.
     * 
     * @param numberItemPerInventory integer
     */
    public void setNumberItemPerInventory(final int numberItemPerInventory) {
        this.numberItemPerInventory = numberItemPerInventory;
    }

    /**
     * Type of object.
     * 
     * @return type
     */
    public int getType() {
        return type;
    }

    /**
     * Type of object.
     * 
     * @param type type
     */
    public void setType(final int type) {
        this.type = type;
    }
    
    
}
