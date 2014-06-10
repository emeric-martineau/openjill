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
 * @see org.jill.game.level.handler.LoadNewLevelHandler
 *
 * @author Emeric MARTINEAU
 */
public class MapLevelHandler extends LoadNewLevelHandler {

    /**
     * Default constructor of level.
     *
     * @throws IOException if missing file
     * @throws ClassNotFoundException if missing class must be load
     * @throws IllegalAccessException if trouble when class must be load
     * @throws InstantiationException if trouble when class must be load
     */
    public MapLevelHandler() throws IOException, ClassNotFoundException,
            IllegalAccessException, InstantiationException {
        super(new JillLevelConfiguration("JILL1.SHA", "MAP.JN1", "JILL1.VCL",
                "JILL1.CFG", "JN1", StartMenuJill1Handler.class,
                SaveData.MAP_LEVEL));

        this.player.setState(PlayerState.BEGIN);

        this.messageDispatcher.sendMessage(EnumMessageType.INVENTORY_ITEM,
                new InventoryItemMessage(
                                    EnumInventoryObject.JILL, false));
    }
}
