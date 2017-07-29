package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.game.entities.obj.player.PlayerState;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.player.MovePlayerMessage;

/**
 * Check point.
 *
 * @author Emeric MARTINEAU
 */
public final class CheckPointManager extends AbstractParameterObjectEntity {
    /**
     * Static cause only one lift move player at one time.
     */
    private static final MovePlayerMessage MOVE_PLAYER_OBJECT
            = new MovePlayerMessage();

    /**
     * To know if checkpoint is load new level or juste play music.
     */
    private boolean isChangingLevel = false;

    /**
     * To know if load previous map.
     */
    private boolean isLoadPreviousMap = false;

    /**
     * To delete when map reload.
     */
    private boolean isToDelete = false;

    /**
     * To know if grap msgTouch (for example, if level load, check point with
     * counter = level, don't grap message).
     */
    private boolean grapMsgTouch = true;

    /**
     * To remove this object from object list.
     */
    private ObjectListMessage killme;

    private int stateSameLevel;

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        setCheckPoint(true);

        this.stateSameLevel = getConfInteger("stateSameLevel");

        // Ignore touch player if checkpoint is level cause just for
        // rententrence after player die
        grapMsgTouch = (objectParam.getLevel() != counter);

        if (this.getStringStackEntry() != null
                && !this.getStringStackEntry().getValue().isEmpty()) {
            switch (this.getStringStackEntry().getValue().charAt(0)) {
                case '*': // load and play this song from the beginning
                    Logger.getLogger(CheckPointManager.class.getName()).
                            log(Level.INFO, "Warning : 'load and play this "
                                    + "song from the beginning' not supported");
                    break;
                case '#': // keep on playing this song
                    Logger.getLogger(CheckPointManager.class.getName()).
                            log(Level.INFO, "Warning : 'keep on playing this "
                                    + "song' not supported");
                    break;
                case '!': // load previous map
                    isLoadPreviousMap = true;
                    grapMsgTouch = true;
                    break;
                default:
                    // Special case. Checkpoint at map level delete whe
                    this.isToDelete = objectParam.getLevel()
                            == this.getCounter();
                    this.isChangingLevel = !this.isToDelete;
            }
        }

        // Remove me from list of object (= kill me)
        killme = new ObjectListMessage(this, false);
    }

    @Override
    public BufferedImage msgDraw() {
        return null;
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        if (this.isToDelete) {
            this.messageDispatcher.sendMessage(
                    EnumMessageType.OBJECT, this.killme);
        }
    }

    @Override
    public void msgTouch(final ObjectEntity obj,
            final KeyboardLayout keyboardLayout) {
        if (obj.isPlayer() && this.grapMsgTouch) {
            if (this.stateSameLevel == getState()) {
                // Just set begin state of player
                int offsetX = this.getX() - obj.getX();

                MOVE_PLAYER_OBJECT.setOffsetX(offsetX);

                int offsetY = this.getY() + this.getHeight() - obj.getHeight();

                MOVE_PLAYER_OBJECT.setOffsetY(Math.abs(offsetY));

                MOVE_PLAYER_OBJECT.setState(PlayerState.BEGIN);

                this.messageDispatcher.sendMessage(
                        EnumMessageType.PLAYER_MOVE, MOVE_PLAYER_OBJECT);

                this.messageDispatcher.sendMessage(
                        EnumMessageType.OBJECT, this.killme);
            } else if (this.isChangingLevel) {
                this.messageDispatcher.sendMessage(
                        EnumMessageType.CHECK_POINT_CHANGING_LEVEL, this);
            } else if (isLoadPreviousMap) {
                this.messageDispatcher.sendMessage(
                        EnumMessageType.CHECK_POINT_CHANGING_LEVEL_PREVIOUS,
                        this);
            }
        }
    }
}
