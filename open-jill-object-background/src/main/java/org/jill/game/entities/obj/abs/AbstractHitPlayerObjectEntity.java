package org.jill.game.entities.obj.abs;

import org.jill.game.entities.obj.player.PlayerState;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;

/**
 * Abstract object with parameter file.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractHitPlayerObjectEntity
    extends AbstractParameterObjectEntity {

    /**
     * Kill force.
     */
    private int killForce;

    /**
     * Constructor.
     *
     * @param objParam object parameter
     */
    @Override
    public void init(final ObjectParam objParam) {
        super.init(objParam);

        this.killForce = getConfInteger("killForce");
    }

    /**
     * Hit player.
     *
     * @param playerObj player object
     */
    protected final void hitPlayer(final ObjectEntity playerObj) {
        playerObj.msgKill(this, this.killForce,
            PlayerState.DIE_SUB_STATE_ENNEMY);
    }

    /**
     * Hit player.
     *
     * @param playerObj player object
     */
    protected final void hitPlayerRock(final ObjectEntity playerObj) {
        playerObj.msgKill(this, this.killForce,
            PlayerState.DIE_SUB_STATE_OTHER_BACK);
    }
}
