package org.jill.openjill.core.api.message.object;

import org.jill.openjill.core.api.entities.ObjectEntity;

/**
 * Class allow to replace object.
 *
 * @author Emeric MARTINEAU
 */
public final class ReplaceObjectMessage {
    /**
     * Object to add/remove.
     */
    private final ObjectEntity objectOrigin;


    /**
     * Object to add/remove.
     */
    private final ObjectEntity objectNew;

    /**
     * Constructor.
     *
     * @param objectOrigin object to replace
     * @param objectNew    new object
     */
    public ReplaceObjectMessage(final ObjectEntity objectOrigin,
            final ObjectEntity objectNew) {
        this.objectOrigin = objectOrigin;
        this.objectNew = objectNew;
    }

    public ObjectEntity getObjectOrigin() {
        return objectOrigin;
    }

    public ObjectEntity getObjectNew() {
        return objectNew;
    }
}
