package org.jill.game.entities.obj;

import java.awt.Color;
import java.awt.Graphics2D;
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
 * Locked door for mapdoor or other door.
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
     * Picture of door top.
     */
    private BufferedImage doortPicutre;

    /**
     * Picture of door bottom.
     */
    private BufferedImage doorbPicutre;

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

        // If door already remove
        if (this.currentConfig != null) {
            // Message
            closeMessage =
                    new StatusBarTextMessage(currentConfig.getCloseMessage(),
                        textTime, textColor);

            openMessage =
                    new StatusBarTextMessage(currentConfig.getOpenMessage(),
                        textTime, textColor);

            // Create remove inventory message
            removeInventoryMessage = new InventoryItemMessage(
                currentConfig.getInventory(), false);
        }

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

        // Load door image for animation
        int doortTile = getConfInteger("DOORT_tile");
        int doortTileset = getConfInteger("DOORT_tileset");
        int doorbTile = getConfInteger("DOORB_tile");
        int doorbTileset = getConfInteger("DOORB_tileset");

        final Color backColor = this.pictureCache.getBackgroundColor();

        BufferedImage lDoortPicutre =
                this.pictureCache.getImage(doortTileset, doortTile);
        BufferedImage lDoorbPicutre =
                this.pictureCache.getImage(doorbTileset, doorbTile);

        this.doortPicutre = createDoorPicture(lDoortPicutre, backColor);
        this.doorbPicutre = createDoorPicture(lDoorbPicutre, backColor);


    }

    /**
     * Create door picture.
     *
     * @param doorPicture door picture
     * @param backColor background color
     *
     * @return  new picture
     */
    private BufferedImage createDoorPicture(final BufferedImage doorPicture,
            final Color backColor) {
        BufferedImage bf = new BufferedImage(
                doorPicture.getWidth(),
                doorPicture.getHeight(),
                BufferedImage.TYPE_INT_ARGB);


        Graphics2D g2d = bf.createGraphics();

        g2d.setColor(backColor);

        g2d.fillRect(0, 0, bf.getWidth(), bf.getHeight());

        g2d.drawImage(doorPicture, 0, 0, null);

        return bf;
    }

    @Override
    public BufferedImage msgDraw() {
        BufferedImage currentPictureDoor = null;

        if (getState() > 0) {
            currentPictureDoor = new BufferedImage(
                    this.doorbPicutre.getWidth(),
                    this.doorbPicutre.getHeight()
                            + this.doorbPicutre.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2CurrentPictureDoor = currentPictureDoor.createGraphics();

            int startY = this.getState() * -1;

            g2CurrentPictureDoor.drawImage(this.doortPicutre, 0,
                    startY, null);

            startY = this.doortPicutre.getHeight() + this.getState();

            g2CurrentPictureDoor.drawImage(this.doorbPicutre, 0,
                    startY, null);
        }

        return currentPictureDoor;
    }

    @Override
    public void msgUpdate() {
        if (getState() > 0) {
            setState(getState() + 1);

            if (getState() >= JillConst.BLOCK_SIZE) {
                // Remove object source and this
                messageDispatcher.sendMessage(
                    EnumMessageType.OBJECT, killme);
            }
        }
    }

    @Override
    public void recieveMessage(final EnumMessageType type, final Object msg) {
        switch (type) {
            case INVENTORY_ITEM :
                inventoryMessage(msg);
                break;
            case TRIGGER :
                triggerMessage(msg);
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
    private void triggerMessage(final Object msg) {
        if (getState() == 0) {
            final ObjectEntity source = (ObjectEntity) msg;

            // If object receive message, 2 possibility ;
            // - mapdoor and object are not delete (open map door)
            // - door and object are not open or open animation (state = 0)
            if (source.getCounter() == counter) {
                if (numberOfKey > 0) {
                    // Remove key from inventory
                    messageDispatcher.sendMessage(
                            EnumMessageType.INVENTORY_ITEM,
                            removeInventoryMessage);

                    // Replace background
                    replaceBackground();
                } else if (currentConfig.isMessageDisplayCloseMessage()) {
                    // Display close message
                    messageDispatcher.sendMessage(
                            EnumMessageType.MESSAGE_STATUS_BAR,
                            closeMessage);

                    currentConfig.setMessageDisplayCloseMessage(false);
                }
            }
        }
    }

    /**
     * Replace background.
     *
     * @param source object to send message.
     */
    private void replaceBackground() {
        // Display open message
        messageDispatcher.sendMessage(
                EnumMessageType.MESSAGE_STATUS_BAR,
                openMessage);

        BackgroundEntity be;

        List<BackgroundMessage> listBackMsg =
                new ArrayList<>(backgroundToRemove.size());

        BackgroundMessage backMsg;

        boolean isDoor = false;
        String doorBackName = getConfString("doorBackStartName");

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

            isDoor = backgroundObject[p.x][p.y].getName()
                    .startsWith(doorBackName);

            backMsg = new BackgroundMessage(p.x, p.y, be.getMapCode());

            listBackMsg.add(backMsg);
        }

        // Send list of point to remove
        messageDispatcher.sendMessage(EnumMessageType.BACKGROUND,
                listBackMsg);

        if (isDoor) {
            setState(1);
        } else {
            // Remove object source and this
            messageDispatcher.sendMessage(
                EnumMessageType.OBJECT, killme);
        }
    }

    /**
     * Manage INVENTORY message.
     *
     * @param msg message
     */
    private void inventoryMessage(final Object msg) {
        final InventoryItemMessage inventory = (InventoryItemMessage) msg;

        if (currentConfig != null &&
                currentConfig.getInventory() == inventory.getObj()) {
            if (inventory.isAddObject()) {
                numberOfKey++;
            } else {
                numberOfKey--;
            }
        }

    }
}
