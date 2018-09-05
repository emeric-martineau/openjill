package org.jill.game.level;

import org.jill.entities.manager.cache.ObjectManagerCache;
import org.jill.game.config.ObjectInstanceFactory;
import org.jill.game.level.cfg.LevelConfiguration;
import org.jill.jn.ObjectItem;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.CreateObjectMessage;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.object.ReplaceObjectMessage;
import org.simplegame.InterfaceSimpleGameHandleInterface;
import org.simplegame.SimpleGameHandler;
import org.simplegame.SimpleGameKeyHandler;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manage all of object.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractObjectJillLevel
        extends AbstractBackgroundJillLevel {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(
            AbstractObjectJillLevel.class.getName());

    /**
     * List of object.
     */
    protected final List<ObjectItem> listObject = new ArrayList<>();

    /**
     * List of object.
     */
    protected final List<ObjectItem> listObjectToRemove = new ArrayList<>();

    /**
     * List of object.
     */
    protected final List<ObjectItem> listObjectToAdd = new ArrayList<>();

    /**
     * List of object always display on screen.
     */
    protected final List<ObjectItem> listObjectAlwaysOnScreen = new ArrayList<>();

    /**
     * Keyboard.
     */
    protected SimpleGameKeyHandler keyboard;

    /**
     * Object cache.
     */
    protected ObjectManagerCache objectCache;

    /**
     * Level configuration.
     *
     * @param cfgLevel configuration of level
     * @throws IOException                  if error reading file
     * @throws ReflectiveOperationException if error create class of object
     */
    public AbstractObjectJillLevel(final LevelConfiguration cfgLevel)
            throws IOException, ReflectiveOperationException {
        super(cfgLevel);

        constructor();
    }

    /**
     * Construct object.
     *
     * @throws IOException                  if error reading file
     * @throws ReflectiveOperationException if error create class of object
     */
    private void constructor() throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        messageDispatcher.addHandler(EnumMessageType.OBJECT, this);
        messageDispatcher.addHandler(EnumMessageType.CREATE_OBJECT, this);
        messageDispatcher.addHandler(EnumMessageType.REPLACE_OBJECT, this);

        loadLevel();
        createBackgound();
        initBackgroundPicture();
        initObjectList();
        createStatusBar();

        keyboard = SimpleGameKeyHandler.getInstance();
    }

    /**
     * Create list of object.
     */
    private void initObjectList() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        // Create object manager
        objectCache = new ObjectManagerCache("std-openjill-object-manager.properties", shaFile,
                dmaFile, screenType);

        // List of object in file
        final List<ObjectItem> listObjectItem = jnFile.getObjectLayer();
        // Graphic
        final Graphics2D g2 = background.createGraphics();
        // Object parameter
        final ObjectParam objParam = ObjectInstanceFactory.getNewObjParam();
// TODO new architecture
//        objParam.init(this.backgroundObject,
//                this.pictureCache, this.messageDispatcher,
//                this.levelConfiguration.getLevelNumber());

        // Current object
        ObjectEntity obj;
        Optional<ObjectEntity> cacheObject;

        for (ObjectItem currentObject : listObjectItem) {
            cacheObject = Optional.ofNullable(objectCache.getManager(currentObject.getType()));
// TODO new architecture
//            objParam.setObject(currentObject);
//
//
            // Manager found for this object
            if (cacheObject.isPresent()) {
                listObject.add(currentObject);
            } else {
                LOGGER.warning(String.format("The object with type %d is not "
                        + "implemented", currentObject.getType()));
                continue;
            }
        }

        g2.dispose();
    }

    /**
     * Change level.
     *
     * @param newHandler handler
     */
    protected final void changeScreenManager(
            final Class<? extends InterfaceSimpleGameHandleInterface> newHandler) {
        try {
            SimpleGameHandler.setNewHandler(newHandler.newInstance());
        } catch (IllegalAccessException | InstantiationException ex) {
            LOGGER.log(Level.SEVERE, "Error switch screen", ex);
        }
    }

    @Override
    public void recieveMessage(final EnumMessageType type, final Object msg) {
        super.recieveMessage(type, msg);

        switch (type) {
            case OBJECT:
                recieveMessageListObject((ObjectListMessage) msg);
                break;
            case REPLACE_OBJECT:
                receiveMessageReplaceObject((ReplaceObjectMessage) msg);
                break;
            case CREATE_OBJECT:
                receiveMessageCreateObject((CreateObjectMessage) msg);
                break;
            default:
        }
    }

    private void receiveMessageReplaceObject(
            final ReplaceObjectMessage replaceObjectMessage) {
        final int indexObject = this.listObject.indexOf(
                replaceObjectMessage.getObjectOrigin());
// TODO new architecture
//        if (indexObject >= 0) {
//            this.listObject.set(indexObject,
//                    replaceObjectMessage.getObjectNew());
//        }
    }

    /**
     * List object message.
     *
     * @param olm message
     */
    private void recieveMessageListObject(final ObjectListMessage olm) {
        final ObjectEntity obj = olm.getObject();
// TODO new architecture
//        if (olm.isAdd()) {
//            if (obj == null) {
//                this.listObjectToAdd.addAll(olm.getListObject());
//            } else {
//                this.listObjectToAdd.add(obj);
//            }
//        } else {
//            if (obj == null) {
//                this.listObjectToRemove.addAll(olm.getListObject());
//            } else {
//                this.listObjectToRemove.add(obj);
//            }
//
//        }
    }

    /**
     * Player.
     *
     * @return the player
     */
    protected Optional<ObjectItem> getPlayer() {
        ObjectItem player = null;
        ObjectEntity objManager;
        for (ObjectItem currentObject : this.listObject) {
            objManager = objectCache.getManager(currentObject.getType());

            if (objManager.isPlayer()) {
                player = currentObject;

                break;
            }
        }

        return Optional.ofNullable(player);
    }

    /**
     * Create object message.
     *
     * @param com message
     */
    private void receiveMessageCreateObject(final CreateObjectMessage com) {
        final ObjectItem oe = ObjectInstanceFactory.getNewObjectItem();

        int type;
// TODO new architecture
//        if (com.getType() == CreateObjectMessage.CreateObjectType.CREATE_BY_CLASS_NAME) {
//            type = this.objectCache
//                    .findTypeByImplementationClassName(com.getClassName());
//        } else {
//            type = com.getObjectType();
//        }
//
//        oe.setType(type);

        // Object parameter
        final ObjectParam objParam = ObjectInstanceFactory.getNewObjParam();
// TODO new architecture
//        objParam.init(this.backgroundObject,
//                this.pictureCache, this.messageDispatcher,
//                this.levelConfiguration.getLevelNumber());
//
//        objParam.setObject(oe);
//
//        // Get jill object
//        final Optional<ObjectEntity> cacheObject = this.objectCache.getNewObject(objParam);
//
//        if (cacheObject.isPresent()) {
//            com.setObject(cacheObject.get());
//        } else {
//            LOGGER.severe(String.format("Can't find object type '%d' to create"
//                    + "it at runtime", oe.getType()));
//        }
    }
}
