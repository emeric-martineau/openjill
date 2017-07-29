package org.jill.game.config;

/**
 * Class to store information for dynamic build.
 *
 * @author emeric_martineau
 */
public final class ObjectInstance {
    /**
     * Name of interface.
     */
    private String interfaceClass;

    /**
     * Name of implementation.
     */
    private String implementationClass;

    /**
     * Is singleton.
     */
    private boolean singleton;

    /**
     * Object is singleton.
     */
    private Object singletonInstance;

    /**
     * Class information.
     */
    private Class clazz;

    /**
     * Return name of interface.
     *
     * @return the interfaceClass
     */
    public String getInterfaceClass() {
        return interfaceClass;
    }

    /**
     * Set interface.
     *
     * @param intClass the interfaceClass to set
     */
    public void setInterfaceClass(final String intClass) {
        this.interfaceClass = intClass;
    }

    /**
     * Return name of implementation class.
     *
     * @return the implementationClass
     */
    public String getImplementationClass() {
        return implementationClass;
    }

    /**
     * Set implementation class.
     *
     * @param implClass the implementationClass to set
     */
    public void setImplementationClass(final String implClass) {
        this.implementationClass = implClass;
    }

    /**
     * Return is simpleton.
     *
     * @return the singleton
     */
    public boolean isSingleton() {
        return singleton;
    }

    /**
     * Set singleton.
     *
     * @param sing the singleton to set
     */
    public void setSingleton(final boolean sing) {
        this.singleton = sing;
    }

    /**
     * Return singleton instance.
     *
     * @return the singletonInstance
     */
    public Object getSingletonInstance() {
        return singletonInstance;
    }

    /**
     * St singleton instance.
     *
     * @param singletonInst the singletonInstance to set
     */
    public void setSingletonInstance(final Object singletonInst) {
        this.singletonInstance = singletonInst;
    }

    /**
     * Return class.
     *
     * @return the clazz
     */
    public Class getClazz() {
        return clazz;
    }

    /**
     * Set class.
     *
     * @param clazzz the clazz to set
     */
    public void setClazz(final Class clazzz) {
        this.clazz = clazzz;
    }
}
