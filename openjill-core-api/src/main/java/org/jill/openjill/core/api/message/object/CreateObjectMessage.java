package org.jill.openjill.core.api.message.object;

import org.jill.openjill.core.api.entities.ObjectEntity;

/**
 * Class permit display text in bottom of statusbar.
 *
 * @author Emeric MARTINEAU
 */
public final class CreateObjectMessage {
    /**
     * If add object. False = delete object
     */
    private final int type;

    /**
     * Object to add/remove.
     */
    private ObjectEntity object;

    /**
     * Constructor.
     *
     * @param objConf configuration of object to create
     */
    public CreateObjectMessage(final int objConf) {
        this.type = objConf;
    }

    /**
     * Object config.
     *
     * @return configuration of object
     */
    public int getType() {
        return type;
    }

    /**
     * Set object.
     *
     * @param obj object
     */
    public void setObject(final ObjectEntity obj) {
        this.object = obj;
    }

    /**
     * Object to add.
     *
     * @return  object
     */
    public ObjectEntity getObject() {
        return object;
    }
}
