package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.game.entities.obj.player.PlayerState;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.jill.JillConst;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.background.BackgroundMessage;
import org.jill.openjill.core.api.message.player.MovePlayerMessage;

/**
 * Object to draw text.
 *
 * @author Emeric MARTINEAU
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
     * Use lift only for player...
     */
    private List<String> onlyFor;

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

        String[] className = getConfString("onlyForObject").split(",");

        this.onlyFor = new ArrayList<>(Arrays.asList(className));
    }

    @Override
    public BufferedImage msgDraw() {
        return this.image;
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        if (this.playerIsOnLift) {
            this.playerIsOnLift = false;
        } else if (this.counter != MAX_INT_VALUE) {
            // Check counter value. if != -1, return to initial position
            clearBlock();
        }
    }

    @Override
    public void msgTouch(final ObjectEntity obj,
            final KeyboardLayout keyboardLayout) {
        if (obj.isPlayer()
                && this.onlyFor.contains(obj.getClass().getSimpleName())) {
            if (messageDisplayLiftMessage) {
                // Display open message
                sendMessage();

                messageDisplayLiftMessage = false;
            }

            msgKeyboard(keyboardLayout);

            this.playerIsOnLift = true;
        }
    }

    public void msgKeyboard(final KeyboardLayout keyboardLayout) {

        if (keyboardLayout.isUp()) {
            //keyboardLayout.setUp(false);

            MOVE_PLAYER_OBJECT.setOffsetX(0);
            MOVE_PLAYER_OBJECT.setOffsetY(-JillConst.getBlockSize());

            MOVE_PLAYER_OBJECT.setState(PlayerState.STAND);

            this.messageDispatcher.sendMessage(
                    EnumMessageType.PLAYER_MOVE, MOVE_PLAYER_OBJECT);

            if (MOVE_PLAYER_OBJECT.isCanDoMove()) {
                final int backX = this.x / JillConst.getBlockSize();
                final int backY = this.y / JillConst.getBlockSize();

                final BackgroundMessage backMsg
                        = new BackgroundMessage(backX, backY,
                        nameBlockNonBlock);

                this.messageDispatcher.sendMessage(
                        EnumMessageType.BACKGROUND, backMsg);

                this.y -= JillConst.getBlockSize();
            }
        } else if (keyboardLayout.isDown()) {
            //keyboardLayout.setDown(false);

            if (clearBlock()) {
                MOVE_PLAYER_OBJECT.setOffsetX(0);
                MOVE_PLAYER_OBJECT.setOffsetY(JillConst.getBlockSize());

                MOVE_PLAYER_OBJECT.setState(PlayerState.STAND);

                this.messageDispatcher.sendMessage(
                        EnumMessageType.PLAYER_MOVE, MOVE_PLAYER_OBJECT);
            }

            // if ELEVMID -> continue and replace by current graph
            // if ELEVBOT -> stop
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
        final int backX = this.x / JillConst.getBlockSize();
        int backY = this.y / JillConst.getBlockSize();

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
            this.y += JillConst.getBlockSize();

            backMsg
                    = new BackgroundMessage(backX, backY,
                    beToClone.getMapCode());

            this.messageDispatcher.sendMessage(EnumMessageType.BACKGROUND,
                    backMsg);
        }

        return result;
    }
}
