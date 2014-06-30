package org.jill.game.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jill.cfg.CfgFile;
import org.jill.dma.DmaFile;
import org.jill.file.FileAbstractByte;
import org.jill.jn.JnFile;
import org.jill.jn.ObjectItem;
import org.jill.openjill.core.api.entities.BackgroundParam;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.manager.TileManager;
import org.jill.openjill.core.api.message.MessageDispatcher;
import org.jill.sha.ColorMap;
import org.jill.sha.ShaFile;
import org.jill.vcl.VclFile;

/**
 *
 * @author emeric_martineau
 */
public final class ObjectInstanceFactory {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(
                    ObjectInstanceFactory.class.getName());

    /**
     * Instance.
     */
    private static final ObjectInstanceFactory INSTANCE =
        new ObjectInstanceFactory();

    /**
     * Instance of object configuration.
     */
    private final Map<String, ObjectInstance> mapObjectInstance
            = new HashMap<>();

    /**
     * Constructor.
     */
    private ObjectInstanceFactory() {
        final ObjectMapper mapper = new ObjectMapper();
        final InputStream is = ObjectInstanceFactory.class.getClassLoader().
                    getResourceAsStream("object_instance_factory.json");

        try {
            final List<ObjectInstance> mapOI = mapper.readValue(is,
                    new TypeReference<List<ObjectInstance>>() { });

            for (ObjectInstance oi : mapOI) {
                this.mapObjectInstance.put(oi.getInterfaceClass(), oi);
            }
        } catch (IOException ex) {
            Logger.getLogger(ObjectInstanceFactory.class.getName()
            ).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Create class.
     *
     * @param name class name
     * @return object class
     */
    private Class createClass(final String name) {
        Class c = null;

        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ObjectInstanceFactory.class.getName()
            ).log(Level.SEVERE, null, ex);
        }

        return c;
    }

    /**
     * Create class.
     *
     * @param className classe name.
     *
     * @return object
     */
    private Object createObject(final String className) {
        Object o = null;

        final ObjectInstance oi = this.mapObjectInstance.get(className);

        Class c;

        try {
            if (oi == null) {
                LOGGER.log(Level.SEVERE,
                        String.format("Can't find implementation class for %s",
                                className));
            } else {
                if (oi.isSingleton()) {
                    o = oi.getSingletonInstance();

                    // Object not initialized
                    if (o == null) {
                        c = getClazz(oi);
                        o = c.getConstructor().newInstance();
                        oi.setSingletonInstance(o);

                        // Clear memory
                        oi.setImplementationClass(null);
                        oi.setInterfaceClass(null);
                    }
                } else {
                    c = getClazz(oi);
                    o = c.getConstructor().newInstance();
                }
            }
        } catch (IllegalArgumentException | SecurityException |
                InvocationTargetException | NoSuchMethodException |
                IllegalAccessException | InstantiationException ex) {
            LOGGER.log(Level.SEVERE,
                "Create jill object error !", ex);
        }

        return o;
    }

    /**
     * Get class.
     *
     * @param oi insotance object
     *
     * @return class
     */
    private Class getClazz(final ObjectInstance oi) {
        // Get class
        Class c = oi.getClazz();

        if (c == null) {
            c = createClass(oi.getImplementationClass());
            oi.setClazz(c);
        }
        return c;
    }

    /**
     * Create Vcl file.
     *
     * @return vcl file
     */
    public static VclFile getNewVcl() {
        return (VclFile) INSTANCE.createObject(VclFile.class.getName());
    }

    /**
     * Create Cfg file.
     *
     * @return cfg file
     */
    public static CfgFile getNewCfg() {
        return (CfgFile) INSTANCE.createObject(CfgFile.class.getName());
    }

    /**
     * Create FileAbstractByte file.
     *
     * @return byte file
     */
    public static FileAbstractByte getNewFileByte()  {
        return (FileAbstractByte) INSTANCE.createObject(
                FileAbstractByte.class.getName());
    }

    /**
     * Create jn file.
     *
     * @return byte file
     */
    public static JnFile getNewJn()  {
        return (JnFile) INSTANCE.createObject(
                JnFile.class.getName());
    }

    /**
     * Create dma file.
     *
     * @return byte file
     */
    public static DmaFile getNewDma()  {
        return (DmaFile) INSTANCE.createObject(
                DmaFile.class.getName());
    }

    /**
     * Create sha file.
     *
     * @return byte file
     */
    public static ShaFile getNewSha()  {
        return (ShaFile) INSTANCE.createObject(
                ShaFile.class.getName());
    }

    /**
     * Create object file.
     *
     * @return byte file
     */
    public static ObjectItem getNewObjectItem()  {
        return (ObjectItem) INSTANCE.createObject(
                ObjectItem.class.getName());
    }

    /**
     * Return Message dispatcher.
     *
     * @return instance
     */
    public static MessageDispatcher getNewMsgDispatcher() {
        return (MessageDispatcher) INSTANCE.createObject(
                MessageDispatcher.class.getName());
    }

    /**
     * Return back parameter.
     *
     * @return instance
     */
    public static BackgroundParam getNewBackParam() {
        return (BackgroundParam) INSTANCE.createObject(
                BackgroundParam.class.getName());
    }

    /**
     * Return back parameter.
     *
     * @return instance
     */
    public static ObjectParam getNewObjParam() {
        return (ObjectParam) INSTANCE.createObject(
                ObjectParam.class.getName());
    }

    /**
     * Return text manager.
     *
     * @return instance
     */
    public static TextManager getNewTxtMng() {
        return (TextManager) INSTANCE.createObject(
            TextManager.class.getName());
    }

    /**
     * Return tile manager.
     *
     * @return instance
     */
    public static TileManager getNewTileMng() {
        return (TileManager) INSTANCE.createObject(
            TileManager.class.getName());
    }

    /**
     * Create color file.
     *
     * @return byte file
     */
    public static ColorMap getCgaColor()  {
        return (ColorMap) INSTANCE.createObject("ColorMapCga");
    }

    /**
     * Create color file.
     *
     * @return byte file
     */
    public static ColorMap getEgaColor()  {
        return (ColorMap) INSTANCE.createObject("ColorMapEga");
    }

    /**
     * Create color file.
     *
     * @return byte file
     */
    public static ColorMap getVgaColor()  {
        return (ColorMap) INSTANCE.createObject("ColorMapVga");
    }

    /**
     * Instance.
     *
     * @return  instance.
     */
    public static ObjectInstanceFactory getInstance() {
        return INSTANCE;
    }
}
