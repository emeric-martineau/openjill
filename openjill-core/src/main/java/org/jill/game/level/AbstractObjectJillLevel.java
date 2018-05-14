package org.jill.game.level;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jill.game.config.ObjectInstanceFactory;
import org.jill.game.level.cfg.LevelConfiguration;
import org.jill.game.manager.object.ObjectManager;
import org.jill.jn.ObjectItem;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.jill.JillConst;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.CreateObjectMessage;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.object.ReplaceObjectMessage;
import org.simplegame.InterfaceSimpleGameHandleInterface;
import org.simplegame.SimpleGameHandler;
import org.simplegame.SimpleGameKeyHandler;

import javax.swing.text.html.Option;

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
    protected final List<ObjectEntity> listObject = new ArrayList<>();
    /**
     * List of object.
     */
    protected final List<ObjectEntity> listObjectToRemove = new ArrayList<>();
    /**
     * List of object.
     */
    protected final List<ObjectEntity> listObjectToAdd = new ArrayList<>();
    /**
     * List of object always display on screen.
     */
    protected final List<ObjectEntity> listObjectAlwaysOnScreen = new ArrayList<>();
    /**
     * List of object on draw background (keep reference for save file).
     */
    protected final List<ObjectEntity> listObjectDrawOnBackground = new ArrayList<>();
    /**
     * Keyboard.
     */
    protected SimpleGameKeyHandler keyboard;
    /**
     * Object cache.
     */
    protected ObjectManager objectCache;

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
    private void constructor() throws IOException, ReflectiveOperationException {
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
    private void initObjectList() {
        // Create object manager
        this.objectCache = ObjectManager.getInstance();
        // List of object in file
        final List<ObjectItem> listObjectItem = this.jnFile.getObjectLayer();
        // Graphic
        final Graphics2D g2 = this.background.createGraphics();
        // Object parameter
        final ObjectParam objParam = ObjectInstanceFactory.getNewObjParam();
        objParam.init(this.backgroundObject,
                this.pictureCache, this.messageDispatcher,
                this.levelConfiguration.getLevelNumber());

        // Current object
        ObjectEntity obj;
        Optional<ObjectEntity> cacheObject;

        for (ObjectItem currentObject : listObjectItem) {

            objParam.setObject(currentObject);

            // Get jill object
            cacheObject = this.objectCache.getNewObject(objParam);

            // No manager found for this object
            if (cacheObject.isPresent()) {
                obj = cacheObject.get();
            } else {
                LOGGER.warning(String.format("The object with type %d is not "
                        + "implemented", currentObject.getType()));
                continue;
            }

            if (obj.isWriteOnBackGround() && obj.msgDraw().isPresent()
                    && checkIfNotUpdatableBackground(obj)) {
                listObjectDrawOnBackground.add(obj);

                // Object draw in background only if object can this and not
                // background with update msg support.
                // Grap picture and don't store it in list
                g2.drawImage(obj.msgDraw().get(), obj.getX(), obj.getY(), null);
            } else if (obj.isAlwaysOnScreen()) {
                listObjectAlwaysOnScreen.add(obj);
            } else {
                // In original game engine, player is the first object
                // Level can contain more than one player but only first is
                // playable.
                listObject.add(obj);
            }
        }

        g2.dispose();
    }

    /**
     * Check if background have update.
     *
     * @param obj object
     * @return true if background not updatable
     */
    protected final boolean checkIfNotUpdatableBackground(
            final ObjectEntity obj) {
        final int blockSize = JillConst.getBlockSize();

        final int startX = obj.getX() / blockSize;
        final int endX = (obj.getX() + obj.getWidth()) / blockSize;
        final int startY = obj.getY() / blockSize;
        final int endY = (obj.getY() + obj.getHeight()) / blockSize;

        for (int indexX = startX; indexX <= endX; indexX++) {
            for (int indexY = startY; indexY <= endY; indexY++) {
                if (backgroundObject[indexX][indexY].isMsgUpdate()) {
                    return false;
                }
            }
        }

        return true;
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

        if (indexObject >= 0) {
            this.listObject.set(indexObject,
                    replaceObjectMessage.getObjectNew());
        }
    }

    /**
     * List object message.
     *
     * @param olm message
     */
    private void recieveMessageListObject(final ObjectListMessage olm) {
        final ObjectEntity obj = olm.getObject();

        if (olm.isAdd()) {
            if (obj == null) {
                this.listObjectToAdd.addAll(olm.getListObject());
            } else {
                this.listObjectToAdd.add(obj);
            }
        } else {
            if (obj == null) {
                this.listObjectToRemove.addAll(olm.getListObject());
            } else {
                this.listObjectToRemove.add(obj);
            }

        }
    }

    /**
     * Player.
     *
     * @return the player
     */
    protected Optional<ObjectEntity> getPlayer() {
        ObjectEntity oe = null;

        for (ObjectEntity currentObject : this.listObject) {
            if (currentObject.isPlayer()) {
                oe = currentObject;

                break;
            }
        }

        return Optional.ofNullable(oe);
    }

    /**
     * Create object message.
     *
     * @param com message
     */
    private void receiveMessageCreateObject(final CreateObjectMessage com) {
        final ObjectItem oe = ObjectInstanceFactory.getNewObjectItem();

        int type;

        if (com.getType() == CreateObjectMessage.CreateObjectType.CREATE_BY_CLASS_NAME) {
            type = this.objectCache
                    .findTypeByImplementationClassName(com.getClassName());
        } else {
            type = com.getObjectType();
        }

        oe.setType(type);

        // Object parameter
        final ObjectParam objParam = ObjectInstanceFactory.getNewObjParam();

        objParam.init(this.backgroundObject,
                this.pictureCache, this.messageDispatcher,
                this.levelConfiguration.getLevelNumber());

        objParam.setObject(oe);

        // Get jill object
        final Optional<ObjectEntity> cacheObject = this.objectCache.getNewObject(objParam);

        if (cacheObject.isPresent()) {
            com.setObject(cacheObject.get());
        } else {
            LOGGER.severe(String.format("Can't find object type '%d' to create"
                    + "it at runtime", oe.getType()));
        }
    }
}
