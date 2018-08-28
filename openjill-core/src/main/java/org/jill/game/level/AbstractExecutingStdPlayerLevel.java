package org.jill.game.level;

import org.jill.game.config.JillGameConfig;
import org.jill.game.level.cfg.LevelConfiguration;
import org.jill.game.screen.conf.GameAreaBorderConf;
import org.jill.game.screen.conf.GameAreaConf;
import org.jill.game.screen.conf.RectangleConf;
import org.jill.jn.ObjectItem;
import org.jill.openjill.core.api.jill.JillConst;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.statusbar.StatusBarTextMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.EnumInventoryObject;
import org.simplegame.SimpleGameConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract class of level of Jill trilogy.
 *
 * This class manage all of player
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractExecutingStdPlayerLevel
        extends AbstractExecutingStdLevel {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(
            AbstractExecutingStdPlayerLevel.class.getName());
    /**
     * Minimum size between player and border.
     */
    private static final int BORDER_SCREEN_PLAYER_Y = 3
            * JillConst.getBlockSize();
    /**
     * Move screen state of player.
     */
    private final int playerStateMoveScreen;
    /**
     * Yd value to move up.
     */
    private final int playerYdUpMoveScreen;
    /**
     * Yd value to move down.
     */
    private final int playerYdDownMoveScreen;
    /**
     * Type of player for head up/down.
     */
    private final int playerTypeMoveScreen;
    /**
     * Begin level message.
     */
    private StatusBarTextMessage beginMessage;

    /**
     * Level configuration.
     *
     * @param cfgLevel configuration of level
     * @throws IOException                  if error of reading file
     * @throws ReflectiveOperationException if not class found
     */
    public AbstractExecutingStdPlayerLevel(final LevelConfiguration cfgLevel)
            throws IOException, ReflectiveOperationException {
        super(cfgLevel);

        if (cfgLevel.getDisplayBeginMessage()) {
            beginMessage();
        }

        this.playerStateMoveScreen
                = ((JillGameConfig) SimpleGameConfig.getInstance()).
                getPlayerMoveScreenState();

        this.playerYdUpMoveScreen
                = ((JillGameConfig) SimpleGameConfig.getInstance()).
                getPlayerMoveScreenYdUp();

        this.playerYdDownMoveScreen
                = ((JillGameConfig) SimpleGameConfig.getInstance()).
                getPlayerMoveScreenYdDown();

        this.playerTypeMoveScreen
                = ((JillGameConfig) SimpleGameConfig.getInstance()).
                getPlayerMoveScreenType();

        initCenterScreen();
// TODO new architecture
//        this.messageDispatcher.sendMessage(
//                EnumMessageType.CHANGE_PLAYER_CHARACTER,
//                this.objectCache.getInvetoryName(getPlayer().get().getClass()));
    }

    /**
     * Create message for status bar.
     *
     * @param prop propertoies
     * @param key  key to search
     * @return status bar
     */
    private static StatusBarTextMessage createSatusBarMessage(
            final Properties prop,
            final String key) {
        return new StatusBarTextMessage(prop.getProperty(key + ".msg"),
                Integer.valueOf(prop.getProperty(key + ".duration")),
                Integer.valueOf(prop.getProperty(key + ".color")));
    }

    /**
     * Init center of screen at load.
     */
    protected void initCenterScreen() {
        final GameAreaConf gameScreen = this.statusBar.getGameAreaConf();
        final RectangleConf offset
                = gameScreen.getOffset();
        final RectangleConf gameLevelStart = gameScreen.getLevelStart();

        final ObjectItem player = getPlayer().get();

        // init center screen
        int offsetX = Math.max(
                player.getX()
                        - gameLevelStart.getX(),
                // To don't have negative value
                0);

        offsetX = Math.min(offsetX, JillConst.getMaxWidth()
                - gameScreen.getWidth());

        offset.setX(-1 * offsetX);

        int offsetY = Math.max(
                player.getY()
                        - gameLevelStart.getY(),
                // To don't have negative value
                0);

        offsetY = Math.min(offsetY, JillConst.getMaxHeight()
                - gameScreen.getHeight());

        offset.setY(-1 * offsetY);
    }

    /**
     * Display begin message.
     */
    private void beginMessage() {
        try {
            Properties prop = new Properties();
            InputStream is =
                    AbstractExecutingStdPlayerLevel.class.getClassLoader().
                            getResourceAsStream("messages.properties");

            prop.load(is);

            this.beginMessage = createSatusBarMessage(prop, "beginLevel");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error at loading", ex);
        }

        this.messageDispatcher.sendMessage(EnumMessageType.MESSAGE_STATUS_BAR,
                this.beginMessage);
    }

    @Override
    protected void movePlayer() {
        // Move player

        // NOTE : Disable in 0.0.28 cause, new collision method do this
        //player.msgKeyboard(player, keyboardLayout);
        //player.msgUpdate();

        computeMoveScreen();

        centerScreen();

        keyboardLayout.clear();

        final ObjectItem player = getPlayer().get();

        // Send msgTouch to background
        final int playerX = player.getX();
        final int playerY = player.getY();
        final int playerWidth = player.getWidth();
        final int playerHeight = player.getHeight();
        final int blockSize = JillConst.getBlockSize();

        final int startBlockX = playerX / blockSize;
        final int endBlockX = (playerX + playerWidth) / blockSize;

        final int startBlockY = playerY / blockSize;
        final int endBlockY = (playerY + playerHeight) / blockSize;

// TODO new architecture
//        for (int indexX = startBlockX; indexX < endBlockX; indexX++) {
//            for (int indexY = startBlockY; indexY < endBlockY; indexY++) {
//                this.backgroundObject[indexX][indexY].msgTouch(player);
//            }
//        }

    }

    /**
     * Compute the special offset of screen when player no move and up/down.
     */
    private void computeMoveScreen() {
        final ObjectItem player = getPlayer().get();

        int ySpeed = player.getySpeed();

        // Player is Stand and not move, and plalyer in down or head up
        if (player.getState() == this.playerStateMoveScreen
                && ySpeed != 0
                && player.getType() == this.playerTypeMoveScreen) {
            final GameAreaConf gameScreen = this.statusBar.getGameAreaConf();

            int specialScreenOffset = gameScreen.getSpecialScreenShift();

            if (ySpeed >= this.playerYdDownMoveScreen
                    && specialScreenOffset < BORDER_SCREEN_PLAYER_Y) {
                specialScreenOffset++;
            } else if (ySpeed <= this.playerYdUpMoveScreen
                    && specialScreenOffset > 0) {
                specialScreenOffset--;
            }

            specialScreenOffset = Math.max(0, specialScreenOffset);

            gameScreen.setSpecialScreenShift(specialScreenOffset);
        }
    }

    /**
     * Center screen with player position.
     */
    protected void centerScreen() {
        final ObjectItem player = getPlayer().get();

        final GameAreaConf gameScreen = this.statusBar.getGameAreaConf();
        final GameAreaBorderConf border = gameScreen.getBorder();
        final RectangleConf offset
                = gameScreen.getOffset();

        // If player is below limit right
        final int playerX = player.getX();
        final int rightOffset = playerX + offset.getX();
        final int gameWidth = gameScreen.getWidth();

        if (rightOffset < border.getRight()) {
            final int newRightOffset = Math.max(playerX - border.getRight(), 0);
            offset.setX(-1 * newRightOffset);
        } else if (rightOffset > (gameWidth - border.getLeft())) {
            final int newRightOffset = Math.min(
                    playerX - gameWidth + border.getLeft(),
                    JillConst.getMaxWidth() - gameWidth);
            offset.setX(-1 * newRightOffset);
        }

        final int playerY = player.getY();
        final int topOffset = playerY + offset.getY();
        final int gameHeight = gameScreen.getHeight();

        if (topOffset < border.getTop()) {
            final int newTopOffset = Math.max(playerY - border.getTop(), 0);
            offset.setY(-1 * newTopOffset);
        } else if (topOffset > (gameHeight - border.getBottom())) {
            final int newTopOffset = Math.min(
                    playerY - gameHeight + border.getBottom(),
                    JillConst.getMaxHeight() - gameHeight);
            offset.setY(-1 * newTopOffset);
        }
    }


    @Override
    protected final void doPlayerFire() {
// TODO new architecture
//        final ObjectItem player = getPlayer().get();
//
//        // Check if player can fire !
//        if (player.canFire()) {
//            // Get inventory
//            final List<EnumInventoryObject> listInv =
//                    this.inventoryArea.getObjects();
//
//            // Get weapon
//            final ObjectMappingWeapon[] weaponsList =
//                    this.objectCache.getTypeOfInventoryWeapon();
//
//            // Current weapon
//            ObjectMappingWeapon currentWeapon;
//            // Current inventory
//            EnumInventoryObject currentInventory;
//
//            // Search weapon from end to start
//            for (int indexWeapon = weaponsList.length - 1; indexWeapon >= 0;
//                 indexWeapon--) {
//                currentWeapon = weaponsList[indexWeapon];
//
//                currentInventory = EnumInventoryObject.valueOf(
//                        currentWeapon.getInventoryKey());
//
//                // Check if weapon found in inventory
//                if (listInv.contains(currentInventory)) {
//                    // Weapon is in inventory
//                    // Cheack if can fire with this weapon
//                    if (checkWeapon(listInv, currentInventory, currentWeapon)) {
//                        createWeapon(currentWeapon.getType(),
//                                currentInventory, currentWeapon);
//
//                        break;
//                    }
//                }
//            }
//        }
    }

    /**
     * Check if weapon knife.
     *
     * @param listInv          list of inventory
     * @param currentInventory weapon inventory
     * @param currentWeapon    current setup of weapon
     * @return true if can create weapon
     */
    private boolean checkWeapon(
            final List<EnumInventoryObject> listInv,
            final EnumInventoryObject currentInventory,
            final Object currentWeapon) {
// TODO new architecture
//        boolean canFireThisWeapon;
//
//        if (getPlayer().get().getInfo1()
//                == ObjectEntity.X_SPEED_MIDDLE) {
//            canFireThisWeapon = false;
//        } else {
//            // Check if last objet to clear ALT text
//            final int nbInvWeaponIteam = Collections.frequency(listInv,
//                    currentInventory);
//            final int nbItemPetInv = currentWeapon.getNumberItemPerInventory();
//
//            if (nbItemPetInv == -1) {
//                canFireThisWeapon = true;
//            } else {
//                // Nb object match for weapon item
//                int nbObjWeaponItem = 0;
//
//                // If remove inventory, don't compute
//                if (!currentWeapon.isRemoveInInventory()) {
//                    for (ObjectItem currentObj : this.listObject) {
//                        if (currentObj.getType() == currentWeapon.getType()) {
//                            nbObjWeaponItem++;
//                        }
//                    }
//                }
//
//                final int nbMaxObj = nbInvWeaponIteam
//                        * nbItemPetInv;
//
//                canFireThisWeapon = (currentWeapon.isRemoveInInventory()
//                        || nbMaxObj > nbObjWeaponItem);
//            }
//        }
//
//        return canFireThisWeapon;

        return true;
    }

    /**
     * Create weapon.
     *
     * @param typeWeapon       weapon object type
     * @param currentInventory weapon inventory
     * @param currentWeapon    current setup of weapon
     */
    private void createWeapon(final int typeWeapon,
            final EnumInventoryObject currentInventory,
            final Object currentWeapon) {

        // TODO Alt key text need update by inventory cause, if blade remove,
        // next weapon is display.
// TODO new architecture
//        if (currentWeapon.isRemoveInInventory()) {
//            // Remove object in inventory list
//            this.messageDispatcher.sendMessage(
//                    EnumMessageType.INVENTORY_ITEM,
//                    new InventoryItemMessage(currentInventory, false,
//                            currentWeapon.isRemoveInInventory(), false));
//        }
// TODO new architecture
//
//        // Object parameter
//        final ObjectParam objParam = ObjectInstanceFactory.getNewObjParam();
//        objParam.init(this.backgroundObject,
//                this.pictureCache, this.messageDispatcher,
//                this.levelConfiguration.getLevelNumber());
//
//        BackgroundLayer background, MessageDispatcher messageDispatcherManager, ShaFile shaFile, DmaFile dmaFile,
//                ObjectItem object, EnumScreenType screen, int levelNumber
//
//        final ObjectItem weapon = ObjectInstanceFactory.getNewObjectItem();
//
//        weapon.setType(typeWeapon);
//
//        objParam.setObject(weapon);
//
//        final ObjectItem player = getPlayer().get();
//
//        weapon.setX(player.getX());
//        weapon.setY(player.getY());
//        weapon.setInfo1(player.getInfo1());
//
//        // Get jill object
//        final Optional<ObjectEntity> cacheObject = this.objectCache.getNewObject(objParam);
//
//        // Add object in list
//        if (cacheObject.isPresent()) {
//            this.listObject.add(cacheObject.get());
//        }
    }
}