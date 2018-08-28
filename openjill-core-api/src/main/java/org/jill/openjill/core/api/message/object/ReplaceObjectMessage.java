package org.jill.openjill.core.api.message.object;

import org.jill.jn.ObjectItem;
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
    private final ObjectItem objectOrigin;


    /**
     * Object to add/remove.
     */
    private final ObjectItem objectNew;

    /**
     * Constructor.
     *
     * @param objectOrigin object to replace
     * @param objectNew    new object
     */
    public ReplaceObjectMessage(final ObjectItem objectOrigin,
            final ObjectItem objectNew) {
        this.objectOrigin = objectOrigin;
        this.objectNew = objectNew;
    }

    public ObjectItem getObjectOrigin() {
        return objectOrigin;
    }

    public ObjectItem getObjectNew() {
        return objectNew;
    }
}
