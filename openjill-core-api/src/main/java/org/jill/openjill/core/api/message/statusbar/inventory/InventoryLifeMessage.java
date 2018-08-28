package org.jill.openjill.core.api.message.statusbar.inventory;

import org.jill.jn.ObjectItem;
import org.jill.openjill.core.api.entities.ObjectEntity;

import java.util.Optional;

/**
 * Life message.
 *
 * @author Emeric MARTINEAU
 */
public final class InventoryLifeMessage {
    /**
     * Dead message for background.
     */
    public static final int DEAD_MESSAGE = Integer.MIN_VALUE;

    /**
     * Standard message to kill player.
     */
    public static final InventoryLifeMessage STD_MESSAGE =
            new InventoryLifeMessage(-1);

    /**
     * Life to add/remove.
     */
    private int life;

    /**
     * Sender of message.
     */
    private Optional<ObjectItem> sender = Optional.empty();

    /**
     * If player dead.
     */
    private boolean playerDead;

    /**
     * Constructor.
     *
     * @param lifePt life point
     */
    public InventoryLifeMessage(final int lifePt) {
        this.life = lifePt;
    }

    /**
     * Return number of life to add/remove.
     *
     * @return life
     */
    public int getLife() {
        return this.life;
    }

    /**
     * Set life.
     *
     * @param l life
     */
    public void setLife(final int l) {
        this.life = l;
    }

    /**
     * Sender.
     *
     * @return sneder
     */
    public Optional<ObjectItem> getSender() {
        return sender;
    }

    /**
     * Sender.
     *
     * @param senderObj sender
     */
    public void setSender(final Optional<ObjectItem> senderObj) {
        this.sender = senderObj;
    }

    /**
     * If player dead.
     *
     * @return true/false
     */
    public boolean isPlayerDead() {
        return playerDead;
    }

    /**
     * Set if player dead.
     *
     * @param dead if player dead
     */
    public void setPlayerDead(final boolean dead) {
        this.playerDead = dead;
    }
}
