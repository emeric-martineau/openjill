package org.jill.game.level.handler.jill1;

import java.io.IOException;

import org.jill.game.entities.obj.player.PlayerState;
import org.jill.game.level.cfg.JillLevelConfiguration;
import org.jill.game.level.handler.LoadNewLevelHandler;
import org.jill.jn.SaveData;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.statusbar.inventory.EnumInventoryObject;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryItemMessage;

/**
 * Map level for starting new game. For reload previous map see
 * LoadNewLevelGameHandler.
 *
 * @author Emeric MARTINEAU
 * @see org.jill.game.level.handler.LoadNewLevelHandler
 */
public class MapLevelHandler extends LoadNewLevelHandler {

    /**
     * Default constructor of level.
     *
     * @throws IOException                  if missing file
     * @throws ReflectiveOperationException if missing class must be load
     */
    public MapLevelHandler() throws IOException, ReflectiveOperationException {
        super(new JillLevelConfiguration("JILL1.SHA", "MAP.JN1", "JILL1.VCL",
                "JILL1.CFG", "JN1", StartMenuJill1Handler.class,
                SaveData.MAP_LEVEL));

        getPlayer().setState(PlayerState.BEGIN);

        this.messageDispatcher.sendMessage(EnumMessageType.INVENTORY_ITEM,
                new InventoryItemMessage(
                        EnumInventoryObject.JILL, false));
    }
}
