package org.jill.game.level;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import org.jill.game.gui.menu.ClassicMenu;
import org.jill.game.gui.menu.MenuInterface;
import org.jill.game.level.cfg.LevelConfiguration;
import org.jill.game.manager.object.weapon.ObjectMappingWeapon;
import org.jill.game.screen.ControlArea;
import org.jill.game.screen.InventoryArea;
import org.jill.game.screen.conf.RectangleConf;
import org.jill.jn.BackgroundLayer;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.jill.JillConst;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.statusbar.inventory.EnumInventoryObject;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryItemMessage;
import org.jill.vcl.VclTextEntry;
import org.simplegame.InterfaceSimpleGameHandleInterface;

/**
 * This class manage all of execution method of game (run, pause, cheat, key,
 * update background, update object...).
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractExecutingStdLevel extends AbstractMenuJillLevel {
    /**
     * Number of pressed key to activate cheat code.
     */
    private static final int CHEAT_CODE_NUMBER = 3;
    /**
     * Object rectangle (only for optimization).
     */
    protected final Rectangle objRect = new Rectangle();
    /**
     * Player rectangle (only for optimization).
     */
    protected final Rectangle obj2Rect = new Rectangle();
    /**
     * Object to draw.
     */
    protected final List<ObjectEntity> listObjectToDraw = new ArrayList<>();
    /**
     * Current list of oject currently on screen.
     */
    private final List<ObjectEntity> listObjectCurrentlyDisplayedOnScreen =
            new ArrayList<>();
    /**
     * Turtle swith.
     */
    protected boolean turtleSwitch = false;
    /**
     * Load game.
     */
    protected MenuInterface menuStd;
    /**
     * Control area.
     */
    protected ControlArea controlArea;
    /**
     * Inventory area.
     */
    protected InventoryArea inventoryArea;
    /**
     * Screen to draw.
     */
    protected BufferedImage drawingScreen;
    /**
     * G2 for drawing screen.
     */
    protected Graphics2D g2DrawingScreen;
    /**
     * Screen rectangle.
     */
    protected Rectangle updateObjectScreenRect;
    /**
     * Visible screen rectangle.
     */
    protected Rectangle visibleScreenRect;
    /**
     * If need update inventory screen.
     */
    protected boolean updateInventoryScreen = false;
    /**
     * Keyboard object share between object (include player).
     */
    protected KeyboardLayout keyboardLayout;
    /**
     * Cheat count.
     */
    protected int cheatCount;
    /**
     * Cheat count for gem.
     */
    protected int gemCheatCount;
    /**
     * Cheat for high jump.
     */
    protected int highJumpCheatCount;
    /**
     * Cheat code for invisible object.
     */
    protected int displayInvisibleObject;
    /**
     * True if player invicible.
     */
    protected boolean invincibility;
    /**
     * Show invisible object.
     */
    protected boolean showInvisible;
    /**
     * False if pause game.
     */
    protected boolean runGame;
    /**
     * By default, send update message t object on visible screen.
     */
    private boolean updateObject = true;

    /**
     * Level configuration.
     *
     * @param cfgLevel configuration of level
     * @throws IOException                  if error of reading file
     * @throws ReflectiveOperationException if not class found
     */
    public AbstractExecutingStdLevel(final LevelConfiguration cfgLevel)
            throws IOException, ReflectiveOperationException {
        super(cfgLevel);

        constructor();

        // To preserve add 2 invincibility after load game
        this.inventoryArea.getObjects()
                .stream()
                .filter(item -> item == EnumInventoryObject.INVINCIBILITY)
                .forEach(item -> this.invincibility = true);
    }

    /**
     * Construct this object.
     */
    private void constructor() {
        keyboardLayout = new KeyboardLayout();

        controlArea = new ControlArea(pictureCache, statusBar);

        inventoryArea = new InventoryArea(pictureCache, statusBar,
                this.messageDispatcher);

        inventoryArea.setLevel(
                this.levelConfiguration.getLevelNumber());

        messageDispatcher.addHandler(EnumMessageType.INVENTORY_ITEM,
                inventoryArea);
        messageDispatcher.addHandler(EnumMessageType.INVENTORY_LIFE,
                inventoryArea);
        messageDispatcher.addHandler(EnumMessageType.INVENTORY_POINT,
                inventoryArea);
        messageDispatcher.addHandler(EnumMessageType.CHANGE_PLAYER_CHARACTER,
                inventoryArea);

        messageDispatcher.addHandler(EnumMessageType.INVENTORY_ITEM,
                controlArea);
        messageDispatcher.addHandler(EnumMessageType.CHANGE_PLAYER_CHARACTER,
                controlArea);

        messageDispatcher.addHandler(EnumMessageType.MESSAGE_BOX, this);
        messageDispatcher.addHandler(EnumMessageType.CHANGE_PLAYER_CHARACTER,
                this);

        final RectangleConf offset
                = this.statusBar.getGameAreaConf().getOffset();

        offset.setX(0);
        offset.setY(0);

        drawingScreen = createGameScreen();

        g2DrawingScreen = drawingScreen.createGraphics();

        messageDispatcher.addHandler(EnumMessageType.INVENTORY_ITEM,
                this);
        messageDispatcher.addHandler(EnumMessageType.INVENTORY_LIFE,
                this);
        messageDispatcher.addHandler(EnumMessageType.INVENTORY_POINT,
                this);

        cheatCount = 0;

        gemCheatCount = 0;

        highJumpCheatCount = 0;

        invincibility = false;

        runGame = true;

        // '* 2' because two border
        updateObjectScreenRect = new Rectangle(0, 0,
                this.statusBar.getGameAreaConf().getWidth()
                        + JillConst.getxUpdateScreenBorder() * 2,
                this.statusBar.getGameAreaConf().getHeight()
                        + JillConst.getyUpdateScreenBorder() * 2);

        visibleScreenRect = new Rectangle(0, 0,
                this.statusBar.getGameAreaConf().getWidth(),
                this.statusBar.getGameAreaConf().getHeight());
    }

    /**
     * Create game screen for draw background and object.
     *
     * @return picture
     */
    protected BufferedImage createGameScreen() {
        return statusBar.createGameScreen();
    }

    /**
     * Draw control.
     */
    protected void drawControl() {
        statusBar.drawControl(controlArea.drawControl());
    }

    /**
     * Draw inventory & control area.
     */
    protected void drawInventory() {
        statusBar.drawInventory(inventoryArea.drawInventory());
        statusBar.drawControl(controlArea.drawControl());
        updateInventoryScreen = false;
    }

    @Override
    protected void doRun() {
        if (keyboard.isOtherKey()) {
            switch (keyboard.consumeOtherKey()) {
                case 'p':
                case 'P':
                    runGame = !runGame;
                    break;
                case 'n':
                case 'N':
                    // TODO noise
                    break;
                case 'q':
                case 'Q':
                    menu.setEnable(true);
                    return;
                case 's':
                case 'S':
                    saveGame();
                    break;
                case 'r':
                case 'R':
                    loadGame();
                    break;
                case 't':
                case 'T':
                    controlArea.setTurtleMode(!controlArea.isTurtleMode());
                    drawControl();
                    break;
                case 'x':
                case 'X':
                    if (invincibility) {
                        break;
                    }

                    cheatCount++;

                    if (cheatCount == CHEAT_CODE_NUMBER) {
                        messageDispatcher.sendMessage(
                                EnumMessageType.INVENTORY_ITEM,
                                new InventoryItemMessage(
                                        EnumInventoryObject.INVINCIBILITY, true));
                        messageDispatcher.sendMessage(
                                EnumMessageType.INVENTORY_ITEM,
                                new InventoryItemMessage(
                                        EnumInventoryObject.RED_KEY, true));

                        cheatCount = 0;

                        invincibility = true;
                    }
                    break;
                case 'g':
                case 'G':
                    gemCheatCount++;

                    if (gemCheatCount == CHEAT_CODE_NUMBER) {
                        messageDispatcher.sendMessage(
                                EnumMessageType.INVENTORY_ITEM,
                                new InventoryItemMessage(
                                        EnumInventoryObject.GEM, true));
                        gemCheatCount = 0;
                    }
                    break;
                case 'h':
                case 'H':
                    highJumpCheatCount++;

                    if (highJumpCheatCount == CHEAT_CODE_NUMBER) {
                        messageDispatcher.sendMessage(
                                EnumMessageType.INVENTORY_ITEM,
                                new InventoryItemMessage(
                                        EnumInventoryObject.HIGH_JUMP, true));
                        highJumpCheatCount = 0;
                    }
                    break;
                case 'i':
                case 'I':
                    displayInvisibleObject++;

                    if (displayInvisibleObject == CHEAT_CODE_NUMBER) {
                        showInvisible = true;
                    }
                    break;
                default:
            }
        }

        if (keyboard.isUp()) {
            //offsetY += JillConst.BLOCK_SIZE;
            keyboardLayout.setUp(true);
        }

        if (keyboard.isDown()) {
            //offsetY -= JillConst.BLOCK_SIZE;
            keyboardLayout.setDown(true);
        }

        if (keyboard.isRight()) {
            //offsetX -= JillConst.BLOCK_SIZE;
            keyboardLayout.setRight(true);
        }

        if (keyboard.isLeft()) {
            //offsetX += JillConst.BLOCK_SIZE;
            keyboardLayout.setLeft(true);
        }

        if (keyboard.isFire()) {
            keyboardLayout.setFire(true);
        }

        if (keyboard.isJump()) {
            keyboardLayout.setJump(true);
        }

        currentDisplayScreen = statusBar.getStatusBar();

        if (runGame) {
            doRunNext();
        }
    }

    /**
     * Run.
     */
    protected void doRunNext() {
        boolean turtleMode = controlArea.isTurtleMode();

        if (!turtleMode || turtleSwitch) {
            // Execute if not in turtle mode or turtle mode and execute cycle

            if (keyboardLayout.isFire()) {
                doPlayerFire();
            }

            // Update background
            updateBackground();

            // Draw object list
            updateObject();

            // Move player
            movePlayer();

            if (updateInventoryScreen) {
                drawInventory();
            }

            final RectangleConf offset
                    = this.statusBar.getGameAreaConf().getOffset();
            final int specialScreenShift = this.statusBar.getGameAreaConf()
                    .getSpecialScreenShift();

            // Copy background screen on
            g2DrawingScreen.drawImage(background,
                    offset.getX(),
                    offset.getY() - specialScreenShift,
                    null);

            // Draw object
            drawObject();

            // Draw background
            statusBar.drawGameScreen(drawingScreen);
        }

        turtleSwitch = !turtleSwitch;
    }

    /**
     * Draw object if displayed.
     */
    private void updateObject() {
        final RectangleConf offset
                = this.statusBar.getGameAreaConf().getOffset();

        int lOffsetX = Math.abs(offset.getX());
        int lOffsetY = Math.abs(offset.getY());

        updateObjectScreenRect.setLocation(lOffsetX
                        - JillConst.getxUpdateScreenBorder(),
                lOffsetY - JillConst.getyUpdateScreenBorder());

        visibleScreenRect.setLocation(lOffsetX, lOffsetY);

        final ObjectEntity player = getPlayer();

        // Set player bounds
        obj2Rect.setBounds(player.getX(), player.getY(), player.getWidth(),
                player.getHeight());

        // Grap list of object on screen
        Iterator<ObjectEntity> itObj = this.listObject.iterator();
        ObjectEntity obj;

        while (itObj.hasNext()) {
            obj = itObj.next();

            objRect.setBounds(obj.getX(), obj.getY(), obj.getWidth(),
                    obj.getHeight());

            if (updateObjectScreenRect.intersects(objRect)) {
                listObjectCurrentlyDisplayedOnScreen.add(obj);
            }
        }

        // Update and object touch
        itObj = listObjectCurrentlyDisplayedOnScreen.iterator();

        while (itObj.hasNext()) {
            obj = itObj.next();

            objRect.setBounds(obj.getX(), obj.getY(), obj.getWidth(),
                    obj.getHeight());

            if (obj.isRemoveOutOfVisibleScreen()
                    && !visibleScreenRect.intersects(objRect)) {
                listObjectToRemove.add(obj);

                itObj.remove();
            } else {
                checkUpdatedObjectCollision(obj);
            }
        }

        // Reset last object. Check if need redraw inventory for backcolor.
        this.updateInventoryScreen = this.inventoryArea.isNeedRedraw()
                || this.updateInventoryScreen;

        listObjectCurrentlyDisplayedOnScreen.clear();

        // Remove object from list
        for (ObjectEntity obj1 : listObjectToRemove) {
            listObject.remove(obj1);
            listObjectToDraw.remove(obj1);
        }

        listObjectToRemove.clear();

        // Add object from list
        listObject.addAll(listObjectToAdd);

        listObjectToAdd.clear();
    }

    /**
     * Check update object collision.
     *
     * @param obj current object
     */
    private void checkUpdatedObjectCollision(final ObjectEntity obj) {
        int zaphold;
        // Decreate touch player flag
        zaphold = obj.getZapHold();
        if (zaphold > 0) {
            obj.setZapHold(zaphold - 1);
        }

        if (this.updateObject) {
            obj.msgUpdate(this.keyboardLayout);
        }

        listObjectToDraw.add(obj);

        for (ObjectEntity obj2 : listObjectCurrentlyDisplayedOnScreen) {
            obj2Rect.setBounds(obj2.getX(), obj2.getY(),
                    obj2.getWidth(), obj2.getHeight());

            // Check object collision.
            // Skip if same object
            // Skip if obj1 is player because, msgKeyboard call after and
            // if don't skip when object collision state of player update
            // twice
            if (obj != obj2
                    && obj2Rect.intersects(objRect)) {
                obj.msgTouch(obj2, this.keyboardLayout);
            }
        }
    }

    /**
     * Update background.
     */
    private void updateBackground() {
        final int blockSize = JillConst.getBlockSize();
        final RectangleConf offset
                = this.statusBar.getGameAreaConf().getOffset();

        final int startX =
                Math.abs(offset.getX())
                        / blockSize;
        final int startY =
                Math.abs(offset.getY())
                        / blockSize;
        final int endX = Math.min(startX + screenWidthBlock,
                BackgroundLayer.MAP_WIDTH);
        final int endY = Math.min(startY + screenHeightBlock,
                BackgroundLayer.MAP_HEIGHT);

        BackgroundEntity back;

        BufferedImage tilePicture;

        for (int indexBackX = startX; indexBackX < endX; indexBackX++) {
            for (int indexBackY = startY; indexBackY < endY; indexBackY++) {
                back = backgroundObject[indexBackX][indexBackY];

                if (back.isMsgUpdate()) {
                    back.msgUpdate();
                    tilePicture = back.getPicture();

                    g2Background.drawImage(tilePicture,
                            indexBackX * blockSize,
                            indexBackY * blockSize, null);
                }
            }
        }
    }

    /**
     * Draw objet on screen.
     * Start draw by last object.
     */
    private void drawObject() {
        for (ObjectEntity currentObject : listObjectAlwaysOnScreen) {
            g2DrawingScreen.drawImage(currentObject.msgDraw(),
                    currentObject.getX(), currentObject.getY(), null);
        }

        ListIterator<ObjectEntity> itDraw
                = listObjectToDraw.listIterator(listObjectToDraw.size());
        ObjectEntity currentObject;

        final RectangleConf offset
                = this.statusBar.getGameAreaConf().getOffset();
        final int specialScreenShift = this.statusBar.getGameAreaConf()
                .getSpecialScreenShift();

        BufferedImage currentPicture;

        while (itDraw.hasPrevious()) {
            currentObject = itDraw.previous();

            currentPicture = currentObject.msgDraw();

            if (this.showInvisible && currentPicture == null) {
                drawDashedRectFilled(g2DrawingScreen, currentObject, offset);
            } else {
                g2DrawingScreen.drawImage(currentPicture,
                        currentObject.getX()
                                + offset.getX(),
                        currentObject.getY()
                                + offset.getY() - specialScreenShift,
                        null);
            }
        }

        // NOTE : Disable in 0.0.28 cause, new collision method do this
        //g2DrawingScreen.drawImage(player.msgDraw(), player.getX() + offsetX,
        //        player.getY() + offsetY, null);

        listObjectToDraw.clear();
    }

    /**
     * Draw dashed rectangle for unknow object type
     *
     * @param g2     graphic 2d
     * @param object current object
     * @param offset offset
     */
    private void drawDashedRectFilled(final Graphics2D g2,
            final ObjectEntity object, final RectangleConf offset) {
        final int specialScreenShift = this.statusBar.getGameAreaConf()
                .getSpecialScreenShift();

        final Color oldColor = g2.getColor();
        g2.setColor(Color.CYAN);

        final Rectangle rect = new Rectangle(object.getX() + offset.getX(),
                object.getY() + offset.getY() - specialScreenShift,
                object.getWidth(), object.getWidth());

        float[] dash = {5F, 5F};

        final Stroke dashedStroke = new BasicStroke(2F, BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_MITER, 3F, dash, 0F);

        g2.fill(dashedStroke.createStrokedShape(rect));

        g2.setColor(oldColor);
    }

    @Override
    protected void initMenu() {
        this.menuStd = new ClassicMenu("exit_menu.json", pictureCache);
        this.menu = menuStd;
    }

    @Override
    protected void menuEntryValidate(final int value) {
        if (value == 0) {
            doMenuValidate();
        } else {
            menu.setEnable(false);
        }
    }

    @Override
    protected void doEscape() {
        menu.setEnable(true);
        keyboard.unescape();
    }

    /**
     * Return start screen class.
     *
     * @return class
     */
    protected Optional<Class<?
                extends InterfaceSimpleGameHandleInterface>> getStartScreenClass() {
        return this.levelConfiguration.getStartScreen();
    }

    /**
     * Move player.
     */
    protected abstract void movePlayer();

    @Override
    public void recieveMessage(final EnumMessageType type, final Object msg) {
        super.recieveMessage(type, msg);

        switch (type) {
            case MESSAGE_BOX:
                int msgId = (Integer) msg;

                if (msgId >= 0) {
                    final List<VclTextEntry> messages = vclFile.getVclText();

                    if (msgId > messages.size() - 1) {
                        msgId = (msgId % messages.size()) - 1;
                    }

                    this.infoBox.setContent(messages.get(msgId).getText());
                    this.infoBox.setEnable(true);
                }

                break;
            case CHANGE_PLAYER_CHARACTER:
                // Get weapon
                final Map<String, ObjectMappingWeapon> mapWeapon
                        = this.objectCache.getMapOfWeapon();

                final List<EnumInventoryObject> inventory
                        = this.inventoryArea.getObjects();
                final ListIterator<EnumInventoryObject> invIt
                        = inventory.listIterator(inventory.size());

                EnumInventoryObject currentInventoryItem;
                ObjectMappingWeapon currentWeapon;

                while (invIt.hasPrevious()) {
                    currentInventoryItem = invIt.previous();
                    currentWeapon = mapWeapon.get(
                            currentInventoryItem.toString());

                    if (currentWeapon != null) {
                        this.controlArea.recieveMessage(
                                EnumMessageType.INVENTORY_ITEM,
                                new InventoryItemMessage(
                                        currentInventoryItem, false));
                    }
                }

                this.updateInventoryScreen = true;
                break;
        }
    }

    /**
     * Save current game.
     */
    protected abstract void saveGame();

    /**
     * load new game.
     */
    protected abstract void loadGame();

    /**
     * Call when menu is validate.
     */
    protected void doMenuValidate() {
        if (getStartScreenClass().isPresent()) {
            changeScreenManager(getStartScreenClass().get());
        }
    }

    /**
     * Player fire.
     */
    protected abstract void doPlayerFire();

    /**
     * Set to send or not update message to object on visible screen.
     *
     * @param upObj tre/false
     */
    protected void setUpdateObject(final boolean upObj) {
        this.updateObject = upObj;
    }
}