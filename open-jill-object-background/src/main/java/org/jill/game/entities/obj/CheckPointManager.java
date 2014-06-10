package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jill.game.entities.ObjectEntityImpl;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.message.EnumMessageType;

/**
 * Check point.
 *
 * @author Emeric MARTINEAU
 */
public final class CheckPointManager extends ObjectEntityImpl {
    /**
     * To know if checkpoint is load new level or juste play music.
     */
    private boolean isChangingLevel = false;

    /**
     * To know if load previous map.
     */
    private boolean isLoadPreviousMap = false;

    /**
     * To know if grap msgTouch (for example, if level load, check point with
     * counter = level, don't grap message).
     */
    private boolean grapMsgTouch = true;

    /**
     * To remove this object from object list.
     */
    private ObjectListMessage killme;

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        setCheckPoint(true);

        // Ignore touch player if checkpoint is level cause just for
        // rententrence after player die
        grapMsgTouch = (objectParam.getLevel() != counter);

        if (this.getStringStackEntry() != null
                && !this.getStringStackEntry().getValue().isEmpty()) {
            switch (this.getStringStackEntry().getValue().charAt(0)) {
                case '*' : // load and play this song from the beginning
                    Logger.getLogger(CheckPointManager.class.getName()).
                            log(Level.INFO, "Warning : 'load and play this "
                            + "song from the beginning' not supported");
                    break;
                case '#' : // keep on playing this song
                    Logger.getLogger(CheckPointManager.class.getName()).
                            log(Level.INFO, "Warning : 'keep on playing this "
                            + "song' not supported");
                    break;
                case '!' : // load previous map
                    isLoadPreviousMap = true;
                    grapMsgTouch = true;
                    break;
                default :
                    isChangingLevel = true;
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
    public void msgTouch(final ObjectEntity obj) {
        if (obj.isPlayer() && this.grapMsgTouch) {
            if (this.isChangingLevel) {
                this.messageDispatcher.sendMessage(
                        EnumMessageType.OBJECT, this.killme);
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
