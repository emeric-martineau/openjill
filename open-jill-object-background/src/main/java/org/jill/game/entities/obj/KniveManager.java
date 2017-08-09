package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;

import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.game.entities.obj.player.PlayerPositionSynchronizer;
import org.jill.game.entities.obj.util.UtilityObjectEntity;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.EnumInventoryObject;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryItemMessage;

/**
 * Knive item manager.
 *
 * @author Emeric MARTINEAU
 */
public final class KniveManager extends AbstractParameterObjectEntity {

    /**
     * Player position object.
     */
    private static final PlayerPositionSynchronizer PLAYER_POSITION
            = PlayerPositionSynchronizer.getInstance();

    /**
     * To know if message must be display.
     */
    private static boolean messageDisplaySwitchMessage = true;

    /**
     * To get player position.
     */
    private int indexEtat = 0;

    /**
     * Maximum value to speed Y.
     */
    private int downMaxMoveY;

    /**
     * Maximum value to speed Y.
     */
    private int upMaxMoveY;

    /**
     * Maximum value to speed X.
     */
    private int leftMaxMoveX;

    /**
     * Maximum value to speed X.
     */
    private int rightMaxMoveX;

    /**
     * Move down size.
     */
    private int moveDown;

    /**
     * State count value to indicate falling down.
     */
    private int moveFallingDown;

    /**
     * Statecount to no hit ennemy and no fall.
     */
    private int statecountNoMoveNoHit;

    /**
     * Statecount to start launch.
     */
    private int statecountLaunchStart;

    /**
     * Statecount to end launch.
     */
    private int statecountLaunchEnd;

    /**
     * Statecount to stop follow player and fall.
     */
    private int statecountFollowPlayerMax;

    /**
     * Picture array.
     */
    private BufferedImage images;

    /**
     * Inventory object to add.
     */
    private InventoryItemMessage inventory;

    /**
     * To remove this object from object list.
     */
    private ObjectListMessage killme;

    /**
     * Background to check block.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        // Init list of picture
        final int tileIndex = getConfInteger("tile");
        final int tileSetIndex = getConfInteger("tileSet");

        this.images = this.pictureCache.getImage(tileSetIndex, tileIndex);

        final int initY = getConfInteger("initY");
        this.downMaxMoveY = getConfInteger("downMaxMoveY");
        this.upMaxMoveY = getConfInteger("upMaxMoveY");
        this.leftMaxMoveX = getConfInteger("leftMaxMoveX");
        this.rightMaxMoveX = getConfInteger("rightMaxMoveX");
        this.moveDown = getConfInteger("moveDown");
        this.moveFallingDown = getConfInteger("moveFallingDown");
        this.statecountNoMoveNoHit =
                getConfInteger("statecountNoMoveNoHit");
        this.statecountLaunchStart =
                getConfInteger("statecountLaunchStart");
        this.statecountLaunchEnd = getConfInteger("statecountLaunchEnd");
        this.statecountFollowPlayerMax
                = getConfInteger("statecountFollowPlayerMax");

        this.inventory = new InventoryItemMessage(
                EnumInventoryObject.getEnumList()[
                        getConfInteger("inventoryIndex")], true);

        // Remove me from list of object (= kill me)
        this.killme = new ObjectListMessage(this, false);

        // Knife can be create by player, check width height
        if (this.width == 0 || this.height == 0) {
            this.width = this.images.getWidth();
            this.height = this.images.getHeight();

            // Knife have not same y tha player
            this.y += initY;
            // Statecount to launch knife
            this.stateCount = this.statecountLaunchStart;
            // this.xSpeed is -1 or 1 to know way to go
            this.xSpeed = this.leftMaxMoveX * this.info1;

            setInfo1(0);
        }

        this.backgroundObject = objectParam.getBackgroundObject();
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        if (this.stateCount >= this.statecountLaunchStart
                && this.stateCount <= this.statecountLaunchEnd) {
            // Knife launch
            moveLeftRight();
        } else if (this.stateCount > this.statecountLaunchEnd
                && this.stateCount <= this.statecountFollowPlayerMax) {
            // Follow player
            followPlayer();
        } else if (this.stateCount == this.moveFallingDown) {
            // Knife must fall down
            moveDown();
        }

        // Stop follow player, next time knife fall
        if (this.stateCount > this.statecountFollowPlayerMax) {
            this.stateCount = moveFallingDown;
        }
    }

    @Override
    public BufferedImage msgDraw() {
        return this.images;
    }

    @Override
    public void msgTouch(final ObjectEntity obj,
            final KeyboardLayout keyboardLayout) {
        if (obj.isPlayer() && !(this.stateCount >= this.statecountLaunchStart
                && this.stateCount <= this.statecountLaunchEnd)) {
            if (messageDisplaySwitchMessage) {
                sendMessage();

                messageDisplaySwitchMessage = false;
            }

            this.messageDispatcher.sendMessage(EnumMessageType.INVENTORY_ITEM,
                    this.inventory);
            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                    this.killme);
        } else if (obj.isKillableObject()
                && this.stateCount != this.statecountNoMoveNoHit) {
            obj.msgKill(this, 0, 0);
            setStateCount(this.statecountLaunchEnd + 1);
        }
    }

    /**
     * Move knife at new position.
     */
    private void moveLeftRight() {
        if (this.xSpeed > X_SPEED_MIDDLE) {
            UtilityObjectEntity.moveObjectRight(this, this.xSpeed,
                    this.backgroundObject);
        } else {
            UtilityObjectEntity.moveObjectLeft(this, this.xSpeed,
                    this.backgroundObject);
        }

        this.stateCount++;
    }

    /**
     * Move knife at new position.
     */
    private void moveUpDown() {
        if (this.ySpeed > X_SPEED_MIDDLE) {
            UtilityObjectEntity.moveObjectDown(this, this.ySpeed,
                    this.backgroundObject);
        } else {
            UtilityObjectEntity.moveObjectUp(this, this.ySpeed,
                    this.backgroundObject);
        }
    }

    /**
     * Move knif down.
     */
    private void moveDown() {
        this.ySpeed = this.moveDown;

        if (this.getY() == 1013) {
            System.out.println("error");
        }

        if (!UtilityObjectEntity.moveObjectDown(this, this.ySpeed,
                this.backgroundObject)) {
            // Stop down
            this.stateCount = 0;
        }
    }

    /**
     * Follow player.
     */
    private void followPlayer() {
        this.indexEtat = PLAYER_POSITION.updatePlayerPosition(
                this.messageDispatcher, this.indexEtat);

        if (this.x < PLAYER_POSITION.getX()
                && this.xSpeed < this.leftMaxMoveX) {
            this.xSpeed++;
        } else if (this.x > PLAYER_POSITION.getX()
                && this.xSpeed > this.rightMaxMoveX) {
            this.xSpeed--;
        }

        if (this.y < PLAYER_POSITION.getY()
                && this.ySpeed < this.downMaxMoveY) {
            this.ySpeed++;
        } else if (this.y > PLAYER_POSITION.getY()
                && this.ySpeed > this.upMaxMoveY) {
            this.ySpeed--;
        }

        // Always add
        moveLeftRight();
        moveUpDown();
    }
}
