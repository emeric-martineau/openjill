package org.jill.game.screen;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jill.game.screen.conf.InventoryAreaConf;
import org.jill.game.screen.conf.ItemConf;
import org.jill.game.screen.conf.PictureConf;
import org.jill.game.screen.conf.TextToDraw;
import org.jill.jn.SaveData;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.jill.JillConst;
import org.jill.openjill.core.api.manager.
        TextManager;
import org.jill.openjill.core.api.manager.
        TileManager;
import org.jill.openjill.core.api.message.
        EnumMessageType;
import org.jill.openjill.core.api.message.
        InterfaceMessageGameHandler;
import org.jill.openjill.core.api.message.
        MessageDispatcher;
import org.jill.openjill.core.api.message.object.CreateObjectMessage;
import org.jill.openjill.core.api.message.object.
        ObjectListMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.
        EnumInventoryObject;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryItemMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryLifeMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryPointMessage;

/**
 * Control area on screen and manage state.
 *
 * @author Emeric MARTINEAU
 */
public final class InventoryArea implements InterfaceMessageGameHandler {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(
                    InventoryArea.class.getName());

    /**
     * Key to fin map/score in text to draw.
     */
    private static final String MAP_KEY = "map";

    /**
     * Current back index color.
     */
    private int currentBackIndexColor;

    /**
     * Picture cache.
     */
    private final TileManager pictureCache;

    /**
     * Inventory picture.
     */
    private final BufferedImage inventoryPicture;

    /**
     * Graphic object to draw inventory.
     */
    private final Graphics2D g2Inventory;

    /**
     * Lifebar.
     */
    private final BufferedImage lifebar;

    /**
     * Lifebar end.
     */
    private final BufferedImage lifebarEnd;

    /**
     * Score.
     */
    private int score = 0;

    /**
     * Level.
     */
    private int level;

    /**
     * Life.
     */
    private int life;

    /**
     * List of item.
     */
    private final List<EnumInventoryObject> objects = new ArrayList<>();

    /**
     * Map with iventory item and picture.
     */
    private final Map<EnumInventoryObject, BufferedImage> listItem
            = new HashMap<>();

    /**
     * Configuration.
     */
    private final InventoryAreaConf conf;

    /**
     * Need redraw inventory arrea ? (for hit player)
     */
    private boolean needRedraw = false;

    /**
     * To dispatch message for any object in game.
     */
    private final MessageDispatcher messageDispatcher;

    /**
     * Constructor.
     *
     * @param pictureCacheManager picture cache
     * @param statusBar status bar
     * @param msgDispatcher message dispatcher
     */
    public InventoryArea(final TileManager pictureCacheManager,
            final StatusBar statusBar, final MessageDispatcher msgDispatcher) {
        this.conf = readConf("inventory_conf.json");

        this.pictureCache = pictureCacheManager;

        this.life = this.conf.getDefaultLife();
        this.lifebar = getImageByConfig(this.conf.getLifebarPictureStart());
        this.lifebarEnd = getImageByConfig(this.conf.getLifebarPictureEnd());

        this.inventoryPicture = statusBar.createInventoryArea();
        this.g2Inventory = inventoryPicture.createGraphics();

        this.currentBackIndexColor = this.conf.getBackgroundColor();

        this.messageDispatcher = msgDispatcher;

        initListItem();
    }

    /**
     * Read config file.
     *
     * @param filename final name of config file
     *
     * @return properties file
     */
    private static InventoryAreaConf readConf(final String filename) {

        final ObjectMapper mapper = new ObjectMapper();
        final InputStream is =
                ControlArea.class.getClassLoader().
                        getResourceAsStream(filename);

        InventoryAreaConf mc;

        // Load menu
        try {
            mc = mapper.readValue(is, InventoryAreaConf.class);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE,
                String.format("Unable to load config for inventory '%s'",
                        filename),
                ex);

            mc = null;
        }

        return mc;
    }

    /**
     * Draw inventory panel.
     *
     * @return picture
     */
    public BufferedImage drawInventory() {
        Color inventoryBackgroundColor =
                this.pictureCache.getColorMap()[this.currentBackIndexColor];

        // Draw background and clear
        this.g2Inventory.setColor(inventoryBackgroundColor);
        this.g2Inventory.fillRect(0, 0, this.inventoryPicture.getWidth(),
                this.inventoryPicture.getHeight());

        String text;

        for (TextToDraw ttd : this.conf.getText()) {
            text = ttd.getText();

            if (MAP_KEY.equals(text) && this.level != SaveData.MAP_LEVEL) {
                // Draw level not MAP text
                text = String.valueOf(this.level);
            }

            this.pictureCache.getTextManager().drawSmallText(this.g2Inventory,
                    ttd.getX(), ttd.getY(), text, ttd.getColor(),
                TextManager.BACKGROUND_COLOR_NONE);
        }

        // Draw score
        final BufferedImage[] scoreLetter = this.pictureCache.getTextManager().
                grapSmallLetter(String.valueOf(this.score),
                this.conf.getScore().getColor(),
                TextManager.BACKGROUND_COLOR_NONE);

        int offsetX = this.conf.getScore().getX();

        for (int indexScore = scoreLetter.length - 1; indexScore >= 0;
                indexScore--) {
            this.g2Inventory.drawImage(scoreLetter[indexScore], offsetX,
                    this.conf.getScore().getY(), null);
            offsetX -= scoreLetter[indexScore].getWidth();
        }

        drawInventoryItem();

        if (this.currentBackIndexColor
                == this.conf.getBackgroundHitPlayerColor()) {
            // Reset for next time
            this.currentBackIndexColor = this.conf.getBackgroundColor();
            this.needRedraw = true;
        } else {
            this.needRedraw = false;
        }

        drawLifeBar();

        return this.inventoryPicture;
    }

    /**
     * Draw lifebar.
     */
    private void drawLifeBar() {
        // Draw live status bar
        int indexLife = this.life - 1;
        int offsetX = this.conf.getLifebar().getX();
        int sizeBar = this.conf.getLifeBarStepSize();

        while (indexLife > 0) {
            this.g2Inventory.drawImage(this.lifebar, offsetX,
                    this.conf.getLifebar().getY(), null);

            offsetX += sizeBar;
            indexLife--;
        }

        offsetX = this.conf.getLifebarEnd().getX()
                + ((this.life - 1) * sizeBar);

        this.g2Inventory.drawImage(this.lifebarEnd, offsetX,
                this.conf.getLifebarEnd().getY(), null);
    }

    /**
     * Return a image by config.
     *
     * @param prop propterties to read
     *
     * @return picture
     */
    private BufferedImage getImageByConfig(final PictureConf prop) {
        return this.pictureCache.getImage(prop.getTileset(), prop.getTile());
    }

    /**
     * Init list item map.
     */
    private void initListItem() {
        Map<String, PictureConf> listItems = this.conf.getItems();
        PictureConf pic;

        // Init map item
        for (EnumInventoryObject io : EnumInventoryObject.values()) {
            pic = listItems.get(io.toString());

            this.listItem.put(io, getImageByConfig(pic));
        }
    }

    /**
     * Draw item of iventory.
     */
    private void drawInventoryItem() {
        // Initial position
        final ItemConf ic = this.conf.getItemConf();
        final int maxCol = ic.getNbCol();
        final int maxRow = ic.getNbRow();

        int x = ic.getX();
        int y = ic.getY();

        // var to calculation
        int col = 0;
        int row = 0;

        BufferedImage image;

        // Draw item
        for (EnumInventoryObject o : getObjects()) {
            image = this.listItem.get(o);

            this.g2Inventory.drawImage(image, x, y, null);

            row++;
            y += image.getHeight() + 2;

            if (row == maxRow) {
                row = 0;
                col++;
                x += image.getWidth() + 2;
                y = ic.getY();

                if (col == maxCol) {
                    break;
                }
            }
        }
    }

    /**
     * Score.
     *
     * @return score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Score.
     *
     * @param point score
     */
    public void setScore(final int point) {
        this.score = point;
    }

    /**
     * Level.
     *
     * @return level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Level.
     *
     * @param lvl level
     */
    public void setLevel(final int lvl) {
        this.level = lvl;
    }

    /**
     * Life.
     *
     * @return life
     */
    public int getLife() {
        return this.life;
    }

    /**
     * Life.
     *
     * @param health life
     */
    public void setLife(final int health) {
        this.life = health;
    }

    /**
     * Manage item message.
     *
     * @param msg message
     */
    private void messageItem(final InventoryItemMessage msg) {
        final EnumInventoryObject obj = msg.getObj();

        final boolean add = (!msg.isAlone()
                || (msg.isAlone() && !this.objects.contains(obj)));

        if (msg.isAddObject()) {
            if (add) {
                this.objects.add(obj);
            }

            if (obj == EnumInventoryObject.INVINCIBILITY) {
                this.life = this.conf.getMaxLife();
            }
        } else {
            this.objects.remove(obj);
        }
    }

    /**
     * Manage point message.
     *
     * @param msg message
     */
    private void messagePoint(final InventoryPointMessage msg) {
        if (msg.isAddObject()) {
            // Create point object
            CreateObjectMessage com = new CreateObjectMessage(
                    this.conf.getTypeObjectPoint());

            this.messageDispatcher.sendMessage(EnumMessageType.CREATE_OBJECT,
                com);

            ObjectEntity obj = com.getObject();

            obj.setState(msg.getPoint());

            obj.setX(msg.getObjToKill().getX());
            obj.setY(msg.getObjToKill().getY());

            // Set yd et xd
            obj.setxSpeed(msg.getObjWhoKill().getxSpeed());

            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                    new ObjectListMessage(obj, true));

            this.score += msg.getPoint();
        } else {
            this.score -= msg.getPoint();
        }
    }

    /**
     * Message poitn.
     *
     * @param msg message
     */
    private void messageLife(final InventoryLifeMessage msg) {
        final int nbLife = msg.getLife();
        final ObjectEntity sender = msg.getSender();

        if (sender == null || sender.getZapHold() == 0) {
            if ((nbLife < 0)
                    && ((sender == null) || !this.objects.contains(
                            EnumInventoryObject.INVINCIBILITY))) {
                // If decrease live, check player are not invincible
                this.life += nbLife;

                // Change background color
                this.currentBackIndexColor =
                        this.conf.getBackgroundHitPlayerColor();

                this.needRedraw = true;
            } else if (nbLife > 0) {
               // Add life
               this.life += nbLife;
               this.needRedraw = true;
            }

            if (this.life > this.conf.getMaxLife()) {
                this.life = this.conf.getMaxLife();
                msg.setPlayerDead(false);
            } else if (this.life < 1) {
                this.life = 0;
                msg.setPlayerDead(true);
            } else {
                msg.setPlayerDead(false);
            }
        }

        if (sender != null) {
            sender.setZapHold(JillConst.ZAPHOLD_VALUE_AFTER_TOUCH_PLAYER);
        }
    }

    @Override
    public void recieveMessage(final EnumMessageType type,
        final Object msg) {
        switch(type) {
            case INVENTORY_ITEM :
                messageItem((InventoryItemMessage) msg);
                this.needRedraw = true;
                break;
            case INVENTORY_POINT :
                messagePoint((InventoryPointMessage) msg);
                this.needRedraw = true;
                break;
            case INVENTORY_LIFE :
                messageLife((InventoryLifeMessage) msg);
                break;
            default:
                // Nothing
        }
    }

    /**
     * Inventory object.
     *
     * @return inventory
     */
    public List<EnumInventoryObject> getObjects() {
        return this.objects;
    }

    /**
     * Clear hit player.
     *
     * @return true if need redraw
     */
    public boolean isNeedRedraw() {
        return this.needRedraw;
    }

    /**
     * Return default value of life.
     *
     * @return default value of life
     */
    public int getDefaultLife() {
        return this.conf.getDefaultLife();
    }
}
