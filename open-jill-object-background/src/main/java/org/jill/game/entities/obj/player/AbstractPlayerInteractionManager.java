package org.jill.game.entities.obj.player;

import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.game.entities.obj.util.UtilityObjectEntity;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.jill.JillConst;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.InterfaceMessageGameHandler;
import org.jill.openjill.core.api.message.player.GetPlayerPositionMessage;
import org.jill.openjill.core.api.message.player.MovePlayerMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.EnumInventoryObject;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryItemMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryLifeMessage;

/**
 * Class to implement InterfaceMessageGameHandler methods.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractPlayerInteractionManager
        extends AbstractParameterObjectEntity
        implements InterfaceMessageGameHandler {

    protected abstract BackgroundEntity[][] getBackgroundObject();

    protected abstract void addHighJump(final int value);

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

        int offsetX = mpm.getOffsetX();
        int offsetY = mpm.getOffsetY();

        if (offsetX > X_SPEED_MIDDLE) {
            playerMoving = UtilityObjectEntity.moveObjectRight(this,
                    offsetX, getBackgroundObject());
        } else if (offsetX < X_SPEED_MIDDLE) {
            playerMoving = UtilityObjectEntity.moveObjectLeft(this,
                    offsetX, getBackgroundObject());
        }

        if (offsetY < Y_SPEED_MIDDLE) {
            playerMoving = UtilityObjectEntity.moveObjectUp(this,
                    mpm.getOffsetY(),
                    getBackgroundObject());
        } else if (offsetY > Y_SPEED_MIDDLE) {
            //playerMoving = true;
            playerMoving = UtilityObjectEntity.moveObjectDown(this,
                    mpm.getOffsetY(),
                    getBackgroundObject());
        }

        mpm.setCanDoMove(playerMoving);

        if (playerMoving) {
            setxSpeed(0);
        }

        if (mpm.getState() != PlayerState.NOTHING_CHANGE) {
            setState(mpm.getState());
            setySpeed(0);
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
        switch (type) {
            case INVENTORY_ITEM:
                messageInventoryItem((InventoryItemMessage) msg);
                break;
            case PLAYER_MOVE:
                messagePlayerMove((MovePlayerMessage) msg);
                break;
            case PLAYER_GET_POSITION:
                messagePlayerGetPosition((GetPlayerPositionMessage) msg);
                break;
            default:
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

    /**
     * Kill player.
     *
     * @param senderObj   object kill player (or hit)
     * @param senderBack  background kill player
     * @param nbLife      number life
     * @param typeOfDeath type of death
     */
    private void msgKill(final ObjectEntity senderObj,
            final BackgroundEntity senderBack,
            final int nbLife, final int typeOfDeath) {
        // senderObj was null when background
        if (!PalyerActionPerState.canDo(this.state,
                PlayerAction.INVINCIBLE)) {
            BackgroundEntity senderBack2 = senderBack;

            InventoryLifeMessage.STD_MESSAGE.setLife(nbLife);

            // In special case if sender is not null and typeOfDeath is other
            // force hit player
            if (senderObj != null &&
                    typeOfDeath == PlayerState.DIE_SUB_STATE_OTHER_BACK) {
                senderBack2 = getBackgroundObject()[
                        this.getX() / JillConst.getBlockSize()][
                        this.getY() / JillConst.getBlockSize()];
            } else {
                InventoryLifeMessage.STD_MESSAGE.setSender(senderObj);
            }

            // Send message to inventory to know if player dead
            this.messageDispatcher.sendMessage(EnumMessageType.INVENTORY_LIFE,
                    InventoryLifeMessage.STD_MESSAGE);

            if (InventoryLifeMessage.STD_MESSAGE.isPlayerDead()) {
                killPlayer(typeOfDeath, senderBack2);
            }
        }
    }

    @Override
    public void msgKill(final ObjectEntity sender, final int nbLife,
            final int typeOfDeath) {
        msgKill(sender, null, nbLife, typeOfDeath);
    }

    @Override
    public void msgKill(final BackgroundEntity sender,
            final int nbLife, final int typeOfDeath) {
        msgKill(null, sender, nbLife, typeOfDeath);
    }

    /**
     * Player must be dead.
     *
     * @param typeOfDeath type of death (@see PlayerState)
     * @param senderBack  back kill
     */
    protected abstract void killPlayer(final int typeOfDeath,
            final BackgroundEntity senderBack);
}
