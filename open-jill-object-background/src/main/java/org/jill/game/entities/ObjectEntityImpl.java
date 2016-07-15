package org.jill.game.entities;

import org.jill.jn.ObjectItem;
import org.jill.jn.ObjectItemImpl;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.manager.TileManager;
import org.jill.openjill.core.api.message.MessageDispatcher;

/**
 * Background object for catch update/draw/... message
 *
 * @author Emeric MARTINEAU
 */
public abstract class ObjectEntityImpl extends ObjectItemImpl
    implements ObjectEntity {
    /**
     * Background objects e.g. torches, stalags.
     */
    protected static final int F_BACK = 512;

    /**
     * Fireball kills it (objs).
     */
    protected static final int F_FIREBALL = 4096;

    /**
     * Puts object in foreground.
     */
    protected static final int F_FRONT = 128;

    /**
     * Text inside (objs).
     */
    protected static final int F_INSIDE = 64;

    /**
     * Laser or rock kills it (objs).
     */
    protected static final int F_KILLABLE = 2048;

    //-- WORK
    //-- DON'T wORK OR NOT IMPLEMENTED
    /**
     * Touch routine? (blocks & objs).
     */
    protected static final int F_MSGTOUCH = 8;

    /**
     * Pad triggers it (objs).
     */
    protected static final int F_TRIGGER = 256;

    /**
     * Is a weapon (objs).
     */
    protected static final int F_WEAPON = 16384;

    /**
     * Max value (in original game -1).
     */
    protected static final int MAX_INT_VALUE = -1;

    /**
     * If object can e write on background and never update (write once,
     * kill after create).
     */
    protected boolean writeOnBackGround = false;

    /**
     * Picture manager.
     */
    protected TileManager pictureCache;

    /**
     * If object must be always on screen (like MAP/DEMO).
     */
    protected boolean alwaysOnScreen = false;

    /**
     * To dispatch message for any object in game.
     */
    protected MessageDispatcher messageDispatcher;

    /**
     * Remove object out of screen.
     */
    private boolean removeOutOfVisibleScreen = false;

    /**
     * True if player object.
     */
    protected boolean playerObject = false;

    /**
     * True if checkpoint.
     */
    private boolean checkpointObject = false;

    /**
     * True if object is killable.
     */
    private boolean killabgeObject = false;

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        this.pictureCache = objectParam.getPictureCache();
        this.messageDispatcher = objectParam.getMessageDispatcher();

        final ObjectItem object = objectParam.getObject();

        type = object.getType();
        x = object.getX();
        y = object.getY();
        xSpeed = object.getxSpeed();
        ySpeed = object.getySpeed();
        width = object.getWidth();
        height = object.getHeight();
        state = object.getState();
        subState = object.getSubState();
        stateCount = object.getStateCount();
        counter = object.getCounter();
        flags = object.getFlags();
        pointer = object.getPointer();
        info1 = object.getInfo1();
        zapHold = object.getZapHold();
        setStringStackEntry(object.getStringStackEntry());
    }

    /**
     * Write on background.
     *
     * @return writeOnBackGround
     */
    @Override
    public final boolean isWriteOnBackGround() {
        return writeOnBackGround;
    }


    /**
     * if olways on screen.
     *
     * @return alwaysOnScreen
     */
    @Override
    public final boolean isAlwaysOnScreen() {
        return alwaysOnScreen;
    }

    /**
     * Call to update.
     */
    @Override
    public void msgUpdate(KeyboardLayout keyboardLayout) {
        // Nothing
    }

    /**
     * Call when player touche object.
     *
     * @param obj object
     */
    @Override
    public void msgTouch(final ObjectEntity obj, KeyboardLayout keyboardLayout) {
        // Nothing
    }

    /**
     * When weapon kill object or ennemy kill player.
     *
     * @param sender sender
     * @param nbLife number life to decrease
     * @param typeOfDeath tyep of death (for player)
     */
    @Override
    public void msgKill(final ObjectEntity sender,
            final int nbLife, final int typeOfDeath) {
        // nothing
    }

    /**
     * When background kill object.
     *
     * @param sender sender
     * @param nbLife number life to decrease
     * @param typeOfDeath tyep of death (for player)
     */
    @Override
    public void msgKill(final BackgroundEntity sender,
            final int nbLife, final int typeOfDeath) {
        // nothing
    }

    /**
     * To know if object is checkpoint.

     * @return if is chack point
     */
    @Override
    public final boolean isCheckPoint() {
        return checkpointObject;
    }

    /**
     * Setup chackpoint status.
     *
     * @param checkpoint true if chackpoint
     */
    protected final void setCheckPoint(final boolean checkpoint) {
        this.checkpointObject = checkpoint;
    }

    /**
     * If player object.
     *
     * @return  true/false
     */
    @Override
    public final boolean isPlayer() {
        return playerObject;
    }

    /**
     * If object is killable.
     *
     * @return true/false
     */
    @Override
    public final boolean isKillableObject() {
        return killabgeObject;
    }

    /**
     * Killable.
     *
     * @param killabge killable
     */
    protected final void setKillabgeObject(final boolean killabge) {
        this.killabgeObject = killabge;
    }

    /**
     * Remove object out of screen.
     *
     * @param remove true to remove
     */
    protected final void setRemoveOutOfVisibleScreen(final boolean remove) {
        this.removeOutOfVisibleScreen = remove;
    }

    @Override
    public boolean isRemoveOutOfVisibleScreen() {
        return this.removeOutOfVisibleScreen;
    }
}
