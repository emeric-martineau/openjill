package org.jill.openjill.core.api.message.object;

import java.util.List;
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
     * List object to add.
     */
    private final List<ObjectEntity> listObject;

    /**
     * Constructor.
     *
     * @param obj object
     * @param addObject add object
     */
    public ObjectListMessage(final ObjectEntity obj, final boolean addObject) {
        this.object = obj;
        this.add = addObject;
        this.listObject = null;
    }

    /**
     * Constructor.
     *
     * @param listObj object
     * @param addObject add object
     */
    public ObjectListMessage(final List<ObjectEntity> listObj,
            final boolean addObject) {
        this.object = null;
        this.add = addObject;
        this.listObject = listObj;
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

    /**
     * Object to add.
     *
     * @return  object
     */
    public List<ObjectEntity> getListObject() {
        return listObject;
    }
}
