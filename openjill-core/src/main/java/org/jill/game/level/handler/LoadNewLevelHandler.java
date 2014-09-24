package org.jill.game.level.handler;

import java.io.IOException;
import java.util.List;
import org.jill.game.entities.obj.player.PlayerState;
import org.jill.game.level.AbstractChangeLevel;
import org.jill.game.level.cfg.LevelConfiguration;
import org.jill.openjill.core.api.message.statusbar.inventory.EnumInventoryObject;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryItemMessage;
import org.jill.jn.SaveData;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.message.EnumMessageType;

/**
 * Class to load a nex level (from map) or restore map.
 *
 * @author Emeric MARTINEAU
 */
public class LoadNewLevelHandler extends AbstractChangeLevel {
    /**
     * Level configuration.
     *
     * @param cfgLevel  configuration of level
     *
     * @throws IOException if error reading file
     * @throws ClassNotFoundException if error create class of object
     * @throws IllegalAccessException if error create class of object
     * @throws InstantiationException if error create class of object
     */
    public LoadNewLevelHandler(final LevelConfiguration cfgLevel)
            throws IOException, ClassNotFoundException, IllegalAccessException,
            InstantiationException {
        super(cfgLevel);

        constructor(cfgLevel);

        // Store map level
        mapLevel = cfgLevel.getLevelMapData();
    }

    /**
     * Construct object.
     *
     * @param cfgLevel level configuration
     */
    private void constructor(final LevelConfiguration cfgLevel) {
        final int level = cfgLevel.getLevelNumber();

        drawControl();

        if (cfgLevel.isRestoreMap()) {
            final SaveData saveData = this.jnFile.getSaveData();

            this.inventoryArea.setLevel(saveData.getLevel());

            this.inventoryArea.setScore(saveData.getScore());

            //this.inventoryArea.setLife(saveData.getHealth());
        } else {
            // New level.
            // In start of map level, don't search check point
            if (level != SaveData.MAP_LEVEL) {
                // Search checkpoint to move player on only on new map
                player.setState(PlayerState.BEGIN);

                final ObjectEntity checkPoint = findCheckPoint(level);

                player.setX(checkPoint.getX());
                player.setY(checkPoint.getY() - checkPoint.getHeight());
            }

            // Keep score
            this.inventoryArea.setScore(cfgLevel.getScore());

            // Keep gem
            final int gemNumber = cfgLevel.getNumberGem();
            final List<Integer> inventory =
                    this.jnFile.getSaveData().getInventory();

            for (int indexGem = 0; indexGem < gemNumber; indexGem++) {
                inventory.add(EnumInventoryObject.GEM.getIndex());
            }
        }

        restoreInventory();

        drawInventory();
    }

    /**
     * Restore inventory list of level.
     */
    private void restoreInventory() {
        final EnumInventoryObject[] enumList =
                EnumInventoryObject.getEnumList();
        InventoryItemMessage inventory;

        final List<Integer> inventoryList =
                this.jnFile.getSaveData().getInventory();

        for (Integer inventoryItem : inventoryList) {
            inventory = new InventoryItemMessage(
                    enumList[inventoryItem], true);

            this.messageDispatcher.sendMessage(EnumMessageType.INVENTORY_ITEM,
                inventory);
        }

        // always restore life.
        this.inventoryArea.setLife(this.inventoryArea.getDefaultLife());
    }

    /**
     * Find check point for current level.
     *
     * @param level level number
     *
     * @return checkpoint or null
     */
    private ObjectEntity findCheckPoint(final int level) {
        for (ObjectEntity obj : listObject) {
            if (obj.isCheckPoint() && obj.getCounter() == level) {
                return obj;
            }
        }

        return null;
    }

    @Override
    protected boolean isCurrentLevelMap() {
        return inventoryArea.getLevel() == SaveData.MAP_LEVEL;
    }
}
