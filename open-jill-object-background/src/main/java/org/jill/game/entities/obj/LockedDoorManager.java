package org.jill.game.entities.obj;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.game.entities.obj.lockeddoor.LockedDoorConfig;
import org.jill.openjill.core.api.message.background.BackgroundMessage;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.statusbar.StatusBarTextMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.
        EnumInventoryObject;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryItemMessage;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.jill.JillConst;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.InterfaceMessageGameHandler;

/**
 * Rockkey for mapdoor.
 *
 * @author Emeric MARTINEAU
 */
public final class LockedDoorManager extends AbstractParameterObjectEntity
    implements InterfaceMessageGameHandler {
    /**
     * Map with background.
     */
    private static Map<String, LockedDoorConfig> listBackground;

    /**
     * Color of text.
     */
    private static int textColor;

    /**
     * Time to display message.
     */
    private static int textTime;

    /**
     * Current cnfiguration to check.
     */
    private LockedDoorConfig currentConfig;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * To know if key or gem get.
     */
    private int numberOfKey = 0;

    /**
     * Close mesage.
     */
    private StatusBarTextMessage closeMessage;

    /**
     * Open mesage.
     */
    private StatusBarTextMessage openMessage;

    /**
     * Remove inventory key/gem.
     */
    private InventoryItemMessage removeInventoryMessage;

    /**
     * To remove this object from object lis.
     */
    private ObjectListMessage killme;

    /**
     * List of background to remove.
     */
    private List<Point> backgroundToRemove;

    /**
     * Init all property.
     */
    private void initProperty() {
        // List type of background to manage
        final String backgroundString = getConfString("backgroundLocked");
        final String[] background = backgroundString.split(",");

        listBackground = new HashMap<>(background.length);

        String openMsg;
        String closeMsg;
        String inventory;

        for (String currentBack : background) {
            openMsg = getConfString("messageOpen_".concat(currentBack), false);
            closeMsg = getConfString("messageLock_".concat(currentBack), false);
            inventory = getConfString("inventory_".concat(currentBack), false);

            if (inventory != null) {
                listBackground.put(currentBack,
                    new LockedDoorConfig(openMsg, closeMsg,
                        EnumInventoryObject.valueOf(inventory)));
            } else {
                // Just to know that we manage this background
                listBackground.put(currentBack, null);
            }
        }

        textColor = getConfInteger("msgColor");
        textTime = getConfInteger("msgTime");
    }

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        if (listBackground == null) {
            initProperty();
        }

        messageDispatcher.addHandler(EnumMessageType.TRIGGER, this);
        messageDispatcher.addHandler(EnumMessageType.INVENTORY_ITEM, this);

        backgroundObject = objectParam.getBackgroundObject();

        // Get background to know config to get
        final BackgroundEntity back =
                backgroundObject[x / JillConst.BLOCK_SIZE]
                    [y / JillConst.BLOCK_SIZE];

        currentConfig = listBackground.get(back.getName());

        // Message
        closeMessage =
                new StatusBarTextMessage(currentConfig.getCloseMessage(),
                    textTime, textColor);

        openMessage =
                new StatusBarTextMessage(currentConfig.getOpenMessage(),
                    textTime, textColor);

        // Remove me from list of object (= kill me)
        killme = new ObjectListMessage(this, false);

        // Compute current position for background
        int xBack = this.x / JillConst.BLOCK_SIZE;
        int yBack = this.y / JillConst.BLOCK_SIZE;
        int yBackEnd = ((this.y + this.height) / JillConst.BLOCK_SIZE) + 1;

        backgroundToRemove = new ArrayList<>(yBackEnd - yBack);

        for (int index = yBack; index < yBackEnd; index++) {
            backgroundToRemove.add(new Point(xBack, index));
        }

        // Create remove inventory message
        removeInventoryMessage = new InventoryItemMessage(
                currentConfig.getInventory(), false);
    }

    @Override
    public BufferedImage msgDraw() {
        return null;
    }

    @Override
    public void recieveMessage(final EnumMessageType type, final Object msg) {
        switch (type) {
            case INVENTORY_ITEM :
                inventoryMessage(msg);
                break;
            case TRIGGER :
                triggerMessage(type, msg);
                break;
            default :
        }
    }

    /**
     * Manage TRIGGER message.
     *
     * @param type type
     * @param msg message
     */
    private void triggerMessage(final EnumMessageType type, final Object msg) {
        final ObjectEntity source = (ObjectEntity) msg;

        if (source.getCounter() == counter) {
            if (numberOfKey > 0) {
                // Display open message
                messageDispatcher.sendMessage(
                        EnumMessageType.MESSAGE_STATUS_BAR,
                        openMessage);

                // Remove object source and this
                messageDispatcher.sendMessage(
                        EnumMessageType.OBJECT, killme);
                messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                         new ObjectListMessage(source, false));
                messageDispatcher.sendMessage(EnumMessageType.INVENTORY_ITEM,
                    removeInventoryMessage);

                BackgroundEntity be;

                List<BackgroundMessage> listBackMsg =
                        new ArrayList<>(backgroundToRemove.size());

                BackgroundMessage backMsg;

                // Remove in back
                for (Point p : backgroundToRemove) {
                    // Background manager
                    // Left ?
                    be = backgroundObject[p.x - 1][p.y];

                    if (!be.isPlayerThru()) {
                        // No, Right ?
                        be = backgroundObject[p.x + 1][p.y];

                        if (!be.isPlayerThru()) {
                            // No, up ?
                            be = backgroundObject[p.x][p.y - 1];

                            if (!be.isPlayerThru()) {
                                // No, down ? ?
                                be = backgroundObject[p.x][p.y + 1];
                            }
                        }
                    }

                    backMsg = new BackgroundMessage(p.x, p.y, be.getMapCode());

                    listBackMsg.add(backMsg);
                }

                // Send list of point to remove
                messageDispatcher.sendMessage(EnumMessageType.BACKGROUND,
                         listBackMsg);

            } else if (currentConfig.isMessageDisplayCloseMessage()) {
                // Display close message
                messageDispatcher.sendMessage(
                        EnumMessageType.MESSAGE_STATUS_BAR,
                        closeMessage);

                currentConfig.setMessageDisplayCloseMessage(false);
            }
        }
    }

    /**
     * Manage INVENTORY message.
     *
     * @param msg message
     */
    private void inventoryMessage(final Object msg) {
        final InventoryItemMessage inventory = (InventoryItemMessage) msg;

        if (currentConfig.getInventory() == inventory.getObj()) {
            if (inventory.isAddObject()) {
                numberOfKey++;
            } else {
                numberOfKey--;
            }
        }

    }
}
