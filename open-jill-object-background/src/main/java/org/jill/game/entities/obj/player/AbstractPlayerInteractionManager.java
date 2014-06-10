package org.jill.game.entities.obj.player;

import org.jill.game.entities.obj.util.UtilityObjectEntity;
import org.jill.openjill.core.api.message.player.GetPlayerPositionMessage;
import org.jill.openjill.core.api.message.player.MovePlayerMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.
        EnumInventoryObject;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryItemMessage;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.InterfaceMessageGameHandler;

/**
 * Class to implement InterfaceMessageGameHandler methods.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractPlayerInteractionManager
    extends AbstractPlayerManager implements InterfaceMessageGameHandler {

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        messageDispatcher.addHandler(EnumMessageType.PLAYER_MOVE, this);
        messageDispatcher.addHandler(EnumMessageType.PLAYER_GET_POSITION, this);
        messageDispatcher.addHandler(EnumMessageType.INVENTORY_ITEM, this);
    }

    /**
     * Manage.
     *
     * @param mpm message
     */
    private void messagePlayerMove(final MovePlayerMessage mpm) {
        setState(mpm.getState());

        boolean playerMoving = true;

        if (mpm.isRight()) {
            playerMoving = UtilityObjectEntity.moveObjectRight(this,
                    mpm.getOffsetX(),
                    getBackgroundObject());
        } else if (mpm.isLeft()) {
            playerMoving = UtilityObjectEntity.moveObjectLeft(this,
                    mpm.getOffsetX(),
                    getBackgroundObject());
        }

        if (mpm.isUp()) {
            playerMoving = UtilityObjectEntity.moveObjectUp(this,
                    mpm.getOffsetY(),
                    getBackgroundObject());
        } else if (mpm.isDown()) {
            //playerMoving = true;
            playerMoving = UtilityObjectEntity.moveObjectDown(this,
                    mpm.getOffsetY(),
                    getBackgroundObject());
        }

        mpm.setCanDoMove(playerMoving);

        if (playerMoving) {
            setxSpeed(0);
        }
    }

    /**
     * Manage item message.
     *
     * @param msg message
     */
    private void messageInventoryItem(final InventoryItemMessage msg) {
        if (msg.getObj() == EnumInventoryObject.HIGH_JUMP) {
            if (msg.isAddObject()) {
                addHighJump(PlayerJumpingConst.HIGH_JUMP_STEP_SIZE);
            } else {
                addHighJump(-PlayerJumpingConst.HIGH_JUMP_STEP_SIZE);
            }
        }
    }


    @Override
    public final void recieveMessage(final EnumMessageType type,
            final Object msg) {
        switch(type) {
            case INVENTORY_ITEM:
                messageInventoryItem((InventoryItemMessage) msg);
                break;
            case PLAYER_MOVE :
                messagePlayerMove((MovePlayerMessage) msg);
                break;
            case PLAYER_GET_POSITION:
                messagePlayerGetPosition((GetPlayerPositionMessage) msg);
                break;
            default :
        }
    }

    /**
     * Get player position.
     *
     * @param mpm message
     */
    private void messagePlayerGetPosition(final GetPlayerPositionMessage mpm) {
        mpm.setX(getX());
        mpm.setY(getY());
    }
}
