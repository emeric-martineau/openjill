package org.jill.game.level.handler;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.jill.game.config.ObjectInstanceFactory;
import org.jill.game.entities.obj.player.PlayerState;
import org.jill.game.level.AbstractChangeLevel;
import org.jill.game.level.cfg.LevelConfiguration;
import org.jill.jn.ObjectItem;
import org.jill.jn.SaveData;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.ReplaceObjectMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.EnumInventoryObject;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryItemMessage;

/**
 * Class to load a nex level (from map) or restore map.
 *
 * @author Emeric MARTINEAU
 */
public class LoadNewLevelHandler extends AbstractChangeLevel {
    /**
     * Level configuration.
     *
     * @param cfgLevel configuration of level
     * @throws IOException                  if error reading file
     * @throws ReflectiveOperationException if error create class of object
     */
    public LoadNewLevelHandler(final LevelConfiguration cfgLevel)
            throws IOException, ReflectiveOperationException {
        super(cfgLevel);

        constructor(cfgLevel);

        // Store map level
        if (cfgLevel.getLevelMapData().isPresent()) {
            this.mapLevel = cfgLevel.getLevelMapData().get();
        }
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
            ObjectEntity player = getPlayer().get();

            // New level.
            // In start of map level, don't search check point
            if (level != SaveData.MAP_LEVEL) {
                final int startObject = this.objectCache.getStartLevelObject();

                if (player.getType() != startObject) {
                    player = restoreStdPlayerForRestartLevel(startObject,
                            player);
                }

                // Search checkpoint to move player on only on new map
                player.setState(PlayerState.BEGIN);

                final Optional<ObjectEntity> checkPoint = findCheckPoint(level);

                if (checkPoint.isPresent()) {
                    final ObjectEntity chkPt = checkPoint.get();

                    player.setX(chkPt.getX());
                    player.setY(chkPt.getY() - chkPt.getHeight());
                }
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

    protected ObjectEntity restoreStdPlayerForRestartLevel(final int startObject, ObjectEntity player) {
        // Object parameter
        final ObjectParam objParam
                = ObjectInstanceFactory.getNewObjParam();
        objParam.init(this.backgroundObject,
                this.pictureCache, this.messageDispatcher,
                this.levelConfiguration.getLevelNumber());
        final ObjectItem tmpPlayer
                = ObjectInstanceFactory.getNewObjectItem();
        tmpPlayer.setType(startObject);
        objParam.setObject(tmpPlayer);

        final ObjectEntity newPlayer = this.objectCache.getNewObject(objParam).get();

        recieveMessage(EnumMessageType.REPLACE_OBJECT,
                new ReplaceObjectMessage(player, newPlayer));
        player = newPlayer;
        return player;
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
     * @return checkpoint or null
     */
    private Optional<ObjectEntity> findCheckPoint(final int level) {
        for (ObjectEntity obj : this.listObject) {
            if (obj.isCheckPoint() && obj.getCounter() == level) {
                return Optional.of(obj);
            }
        }

        return Optional.empty();
    }

    @Override
    protected boolean isCurrentLevelMap() {
        return this.inventoryArea.getLevel() == SaveData.MAP_LEVEL;
    }
}
