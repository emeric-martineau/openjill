package org.jill.game.entities.back;

import org.jill.game.entities.obj.player.PlayerState;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryLifeMessage;

/**
 * Kill 2 background.
 *
 * @author Emeric MARTINEAU
 */
public final class KillWaterBackgroundEntity extends StdBackgroundEntity {
    @Override
    public void msgTouch(final ObjectEntity obj) {
        obj.msgKill(this, InventoryLifeMessage.DEAD_MESSAGE,
                PlayerState.DIE_SUB_STATE_WATER_BACK);
    }
}