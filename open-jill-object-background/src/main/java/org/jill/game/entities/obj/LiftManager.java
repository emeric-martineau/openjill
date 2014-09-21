package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.game.entities.obj.player.PlayerState;
import org.jill.openjill.core.api.message.background.BackgroundMessage;
import org.jill.openjill.core.api.message.player.MovePlayerMessage;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.jill.JillConst;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;

/**
 * Object to draw text.
 *
 * @author Emeric MARTINEAU
 *
 */
public final class LiftManager extends AbstractParameterObjectEntity {

    /**
     * Static cause only one lift move player at one time.
     */
    private static final MovePlayerMessage MOVE_PLAYER_OBJECT
        = new MovePlayerMessage();

    /**
     * To know if message must be display.
     */
    private static boolean messageDisplayLiftMessage = true;

    /**
     * Name of block to move on.
     */
    private static String nameBlockNonBlock;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * Picture array.
     */
    private BufferedImage image;

    /**
     * To know if player is on lift.
     */
    private boolean playerIsOnLift = false;

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        // Init once
        if (nameBlockNonBlock == null) {
            nameBlockNonBlock = getConfString("noStopBlockName");
        }

        this.backgroundObject = objectParam.getBackgroundObject();

        final int tileIndex = getConfInteger("tile");
        final int tileSetIndex = getConfInteger("tileSet");

        this.image = this.pictureCache.getImage(tileSetIndex, tileIndex);
    }

    @Override
    public BufferedImage msgDraw() {
        return this.image;
    }

    @Override
    public void msgUpdate() {
        if (this.playerIsOnLift) {
            this.playerIsOnLift = false;
        } else if (this.counter != MAX_INT_VALUE) {
            // Check counter value. if != -1, return to initial position
            clearBlock();
        }
    }

    @Override
    public void msgTouch(final ObjectEntity obj) {
        if (obj.isPlayer()) {
            if (messageDisplayLiftMessage) {
                // Display open message
                sendMessage();

                messageDisplayLiftMessage = false;
            }
            this.playerIsOnLift = true;
        }
    }

    @Override
    public void msgKeyboard(final ObjectEntity obj,
        final KeyboardLayout keyboardLayout) {
        if (obj.isPlayer()) {
            if (keyboardLayout.isUp()) {
                keyboardLayout.setUp(false);

                MOVE_PLAYER_OBJECT.setOffsetX(0);
                MOVE_PLAYER_OBJECT.setOffsetY(-JillConst.BLOCK_SIZE);

                MOVE_PLAYER_OBJECT.setState(PlayerState.STAND);

                MOVE_PLAYER_OBJECT.setUp(true);

                this.messageDispatcher.sendMessage(
                    EnumMessageType.PLAYER_MOVE, MOVE_PLAYER_OBJECT);

                if (MOVE_PLAYER_OBJECT.isCanDoMove()) {
                    final int backX = this.x / JillConst.BLOCK_SIZE;
                    final int backY = this.y / JillConst.BLOCK_SIZE;

                    final BackgroundMessage backMsg
                        = new BackgroundMessage(backX, backY,
                            nameBlockNonBlock);

                    this.messageDispatcher.sendMessage(
                            EnumMessageType.BACKGROUND, backMsg);

                    this.y -= JillConst.BLOCK_SIZE;
                }

                MOVE_PLAYER_OBJECT.setUp(false);
            } else if (keyboardLayout.isDown()) {
                keyboardLayout.setDown(false);

                if (clearBlock()) {
                    MOVE_PLAYER_OBJECT.setOffsetX(0);
                    MOVE_PLAYER_OBJECT.setOffsetY(JillConst.BLOCK_SIZE);

                    MOVE_PLAYER_OBJECT.setState(PlayerState.STAND);

                    MOVE_PLAYER_OBJECT.setDown(true);

                    this.messageDispatcher.sendMessage(
                        EnumMessageType.PLAYER_MOVE, MOVE_PLAYER_OBJECT);

                    MOVE_PLAYER_OBJECT.setDown(false);

                }

                // if ELEVMID -> continue and replace by current graph
                // if ELEVBOT -> stop
            }
        }
    }

    /**
     * Clear current block to go down lift.
     *
     * @return if block have been clear
     */
    private boolean clearBlock() {
        // Down picture
        // Check if
        final int backX = this.x / JillConst.BLOCK_SIZE;
        int backY = this.y / JillConst.BLOCK_SIZE;

        // Get current back to duplicate
        final BackgroundEntity beToClone = this.backgroundObject[backX][backY];

        // Get back below object to know if can move it
        BackgroundEntity be;

        // Background message
        BackgroundMessage backMsg;

        // Get first background
        backY++;
        be = this.backgroundObject[backX][backY];

        final boolean result = nameBlockNonBlock.equals(be.getName());

        if (result) {
            // Decrease position
            this.y += JillConst.BLOCK_SIZE;

            backMsg
                = new BackgroundMessage(backX, backY,
                    beToClone.getMapCode());

            this.messageDispatcher.sendMessage(EnumMessageType.BACKGROUND,
                backMsg);
        }

        return result;
    }
}
