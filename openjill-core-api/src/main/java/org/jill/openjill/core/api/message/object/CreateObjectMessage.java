package org.jill.openjill.core.api.message.object;

import org.jill.openjill.core.api.entities.ObjectEntity;

/**
 * Class to create object.
 *
 * @author Emeric MARTINEAU
 */
public final class CreateObjectMessage {
    /**
     * Create object type.
     */
    public static enum CreateObjectType {
        CREATE_BY_TYPE, CREATE_BY_CLASS_NAME
    };

    /**
     * If add object. False = delete object
     */
    private final int objectType;

    /**
     * Creation type.
     */
    private final CreateObjectType type;

    /**
     * Class name of object.
     */
    private final String className;

    /**
     * Object to add/remove.
     */
    private ObjectEntity object;

    /**
     * Constructor.
     *
     * @param objConf configuration of object to create
     */
    private CreateObjectMessage(final CreateObjectType type,
            final int objConf) {
        this.type = type;
        this.objectType = objConf;
        this.className = null;
    }

        /**
     * Constructor.
     *
     * @param className class name of object to create
     */
    private CreateObjectMessage(final CreateObjectType type,
            final String className) {
        this.type = type;
        this.objectType = -1;
        this.className = className;
    }

    /**
     * Create object from type.
     *
     * @param type object type
     *
     * @return a new object
     */
    public static CreateObjectMessage buildFromObjectType(final int type) {
        return new CreateObjectMessage(CreateObjectType.CREATE_BY_TYPE, type);
    }

    /**
     * Create object from class name.
     *
     * @param className class name of object.
     *
     * @return a new object
     */
    public static CreateObjectMessage buildFromClassName(
            final String className) {
        return new CreateObjectMessage(CreateObjectType.CREATE_BY_CLASS_NAME,
                className);
    }

    /**
     * Object config.
     *
     * @return configuration of object
     */
    public int getObjectType() {
        return objectType;
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

    /**
     * Type of create object.
     *
     * @return @see CreateObjectType
     */
    public CreateObjectType getType() {
        return type;
    }

    /**
     * Class name of object to create.
     *
     * @return class name
     */
    public String getClassName() {
        return className;
    }


}
