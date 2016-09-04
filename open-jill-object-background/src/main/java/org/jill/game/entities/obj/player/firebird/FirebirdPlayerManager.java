package org.jill.game.entities.obj.player.firebird;

import java.awt.image.BufferedImage;
import org.jill.game.entities.obj.player.AbstractPlayerInteractionManager;
import org.jill.game.entities.obj.player.PalyerActionPerState;
import org.jill.game.entities.obj.player.PlayerAction;
import org.jill.game.entities.obj.player.PlayerManager;
import org.jill.game.entities.obj.util.UtilityObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.CreateObjectMessage;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.object.ReplaceObjectMessage;

/**
 * Firebird for player.
 *
 *
 * mvt x (+/-4 first, then +/-8 px)
 * mvt y (yd = -6 px)
 * when firebird touch water (all sprite) die
 * when restart level after die, restore std player.
 * when firebird die, restore player to die
 * subState = 4/8 (right), -8/-4 (left), 0 first when change way => last yd
 * info1 = -/+ 1 (always depend from previous value)
 * test with lift (firebird cannot take lift)
 * test with spark (same as standard player)
 *
 * @author Emeric MARTINEAU
 */
public final class FirebirdPlayerManager
        extends AbstractPlayerInteractionManager {

    /**
     * Number of picture to remove from left/right.
     */
    private static final int NUMBER_PICTURE_TO_REMOVE = 2;

    /**
     * Number of side.
     */
    private static final int NUMBER_OF_SIDE = 2;

    /**
     * Picture array.
     */
    private BufferedImage[] leftImages;

    /**
     * Picture array.
     */
    private BufferedImage[] rightImages;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * Maximum falling player speed.
     */
    private int maxYSpeed;

    /**
     * Minimum X speed.
     */
    private int minXSpeed;

    /**
     * Maximum X speed.
     */
    private int maxXSpeed;

    /**
     * Init value of YD when jump.
     */
    private int jumpInitSpeed;

    /**
     * Object when fire.
     */
    private String fireObject;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        playerObject = true;

        // Init list of picture
        final int tileIndex = getConfInteger("tile");
        final int tileSetIndex = getConfInteger("tileSet");

        final int baseTileNumber = getConfInteger("baseTileNumber");

        // Number picture for one side
        final int numberPicturePerSide = (baseTileNumber * NUMBER_OF_SIDE)
                - NUMBER_PICTURE_TO_REMOVE;

        // Alloc array of picture
        this.leftImages = new BufferedImage[numberPicturePerSide];
        this.rightImages = new BufferedImage[numberPicturePerSide];

        // Init Right
        initPicture(this.rightImages, baseTileNumber, tileSetIndex,
                tileIndex);

        // Init Left
        initPicture(this.leftImages, baseTileNumber, tileSetIndex,
                tileIndex + baseTileNumber);

        // Search block
        this.backgroundObject = objectParam.getBackgroundObject();

        this.maxYSpeed = getConfInteger("maxYSpeed");

        this.minXSpeed = getConfInteger("minXSpeed");

        this.maxXSpeed = getConfInteger("maxXSpeed");

        this.jumpInitSpeed = getConfInteger("jumpInitSpeed");

        this.fireObject = getConfString("fireObject");

        if (getWidth() == 0) {
            // Object create during game
            setWidth(this.leftImages[0].getWidth());
            setHeight(this.leftImages[0].getHeight());
        }
    }



    /**
     * Init picture level.
     *
     * @param images picture array
     * @param baseTileNumber number
     * @param tileSetIndex tileset
     * @param tileIndex tile
     */
    private void initPicture(final BufferedImage[] images,
        final int baseTileNumber, final int tileSetIndex,
        final int tileIndex) {
        for (int index = 0; index < baseTileNumber; index++) {
            images[index] = this.pictureCache.getImage(tileSetIndex,
                tileIndex + index);
        }

        int indexArray = baseTileNumber;

        for (int index = baseTileNumber - NUMBER_PICTURE_TO_REMOVE; index > 0;
            index--) {
            images[indexArray] = images[index];
            indexArray++;
        }
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        move(keyboardLayout);

        // Go to left
        this.counter++;

        // Turn picture, but we want continue to move.
        if (this.counter ==
                this.leftImages.length) {
            this.counter = 0;
        }
    }

    @Override
    public BufferedImage msgDraw() {
        BufferedImage[] currentPictureArray;

        if (this.getSubState() < 0) {
            // Right
            currentPictureArray = this.leftImages;
        } else {
            // Left
            currentPictureArray = this.rightImages;
        }

        return currentPictureArray[this.counter];
    }

    /**
     * Move player.
     *
     * @param keyboardLayout keyboard oject
     */
    protected final void move(final KeyboardLayout keyboardLayout) {
        if (!PalyerActionPerState.canDo(
            getState(), PlayerAction.CANMOVE)) {
            // Player can't move
            return;
        }

        movePlayerUpDownStand(keyboardLayout);
        movePlayerLeftRightStand(keyboardLayout);

        // When firebird fire, it's also jump. Jump only if not jumping.
        if (keyboardLayout.isFire()) {
            if (!keyboardLayout.isJump()) {
                jump();
            }

            createWeapon();
        }
    }

    /**
     * Move player to left or right.
     *
     * @param keyboardLayout keyboard
     */
    private void movePlayerLeftRightStand(final KeyboardLayout keyboardLayout) {
        if (keyboardLayout.isRight() && getSubState() > X_SPEED_MIDDLE) {
            // If go right and substate positiv
            setxSpeed(this.maxXSpeed);
            setSubState(this.maxXSpeed);
            setInfo1(X_SPEED_RIGHT);
        } else if (keyboardLayout.isLeft() && getSubState() < X_SPEED_MIDDLE) {
            // If go left and substate negativ
            setxSpeed(this.maxXSpeed * X_SPEED_LEFT);
            setSubState(this.maxXSpeed * X_SPEED_LEFT);
            setInfo1(X_SPEED_LEFT);
        } else if (keyboardLayout.isRight() && getSubState() <= X_SPEED_MIDDLE) {
            // If go right and substate negativ
            setxSpeed(this.minXSpeed);
            setSubState(this.minXSpeed);
            setInfo1(X_SPEED_RIGHT);
        } else if (keyboardLayout.isLeft() && getSubState() >= X_SPEED_MIDDLE) {
            // If go left and substate positiv
            setxSpeed(this.minXSpeed * X_SPEED_LEFT);
            setSubState(this.minXSpeed* X_SPEED_LEFT);
            setInfo1(X_SPEED_LEFT);
        } else {
            // No key pressed
            setxSpeed(X_SPEED_MIDDLE);
        }

        if (getxSpeed() < ObjectEntity.X_SPEED_MIDDLE) {
            UtilityObjectEntity.moveObjectLeft(this, getxSpeed(),
                this.backgroundObject);
        } else if (this.xSpeed > ObjectEntity.X_SPEED_MIDDLE) {
            UtilityObjectEntity.moveObjectRight(this, getxSpeed(),
                this.backgroundObject);
        }
    }

    /**
     * Move player to up and down.
     *
     * @param keyboardLayout keyboard
     */
    private void movePlayerUpDownStand(final KeyboardLayout keyboardLayout) {
        if (getySpeed() < Y_SPEED_MIDDLE) {
            // Go up
            UtilityObjectEntity.moveObjectUp(this, getySpeed(),
                    this.backgroundObject);
        } else {
            // Go down
            UtilityObjectEntity.moveObjectDownWithIgnoreStair(this, getySpeed(),
                    this.backgroundObject);
        }

        int newYSpeed = getySpeed() + 1;

        if (newYSpeed > this.maxYSpeed) {
            newYSpeed = this.maxYSpeed;
        }

        setySpeed(newYSpeed);

        if (keyboardLayout.isJump()) {
            jump();
        }
    }

    private void jump() {
        setySpeed(this.jumpInitSpeed);
    }

    @Override
    protected BackgroundEntity[][] getBackgroundObject() {
        return this.backgroundObject;
    }

    @Override
    protected void addHighJump(final int value) {
        // Nothing
    }

    @Override
    protected void killPlayer(final int typeOfDeath,
            final BackgroundEntity senderBack) {
        final ObjectEntity stdPlayer = createPlayer();

        stdPlayer.setX(getX());
        stdPlayer.setY(getY());

        // Create std player and call msgKill
        final ReplaceObjectMessage rom = new ReplaceObjectMessage(this,
                stdPlayer);

        this.messageDispatcher.sendMessage(EnumMessageType.REPLACE_OBJECT, rom);

        // Simulate kill message
        stdPlayer.msgKill(senderBack, 0, typeOfDeath);
    }

    /**
     * Create standard player to return jil form or when die.
     *
     * @return jill std player object
     */
    private ObjectEntity createPlayer() {
        final CreateObjectMessage com = CreateObjectMessage.buildFromClassName(
                PlayerManager.class.getName());
        this.messageDispatcher.sendMessage(EnumMessageType.CREATE_OBJECT, com);

        return com.getObject();
    }

    @Override
    public boolean canFire() {
        return false;
    }

    private void createWeapon() {
        ObjectEntity weapon;

        final CreateObjectMessage com
                = CreateObjectMessage.buildFromClassName(this.fireObject);

        this.messageDispatcher.sendMessage(EnumMessageType.CREATE_OBJECT, com);

        int weaponInfo1;

        if (getSubState() >= X_SPEED_MIDDLE) {
            weaponInfo1 = X_SPEED_RIGHT;
        } else {
            weaponInfo1 = X_SPEED_LEFT;
        }

        weapon = com.getObject();

        weapon.setX(getX());
        weapon.setY(getY());
        weapon.setInfo1(weaponInfo1);

        this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                new ObjectListMessage(weapon, true)) ;
    }
}
