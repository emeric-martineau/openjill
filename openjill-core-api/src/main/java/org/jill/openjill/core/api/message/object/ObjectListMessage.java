package org.jill.openjill.core.api.message.object;

import org.jill.openjill.core.api.entities.ObjectEntity;

/**
 * Class permit display text in bottom of statusbar.
 *
 * @author Emeric MARTINEAU
 */
public final class ObjectListMessage {
    /**
     * If add object. False = delete object
     */
    private final boolean add;

    /**
     * Object to add/remove.
     */
    private final ObjectEntity object;

    /**
     * Constructor.
     *
     * @param obj object
     * @param addObject add object
     */
    public ObjectListMessage(final ObjectEntity obj, final boolean addObject) {
        this.object = obj;
        this.add = addObject;
    }

    /**
     * If add object.
     *
     * @return  false = delete object from inventory
     */
    public boolean isAdd() {
        return add;
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
