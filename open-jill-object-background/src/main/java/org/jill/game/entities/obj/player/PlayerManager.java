package org.jill.game.entities.obj.player;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;
import org.jill.game.entities.obj.bullet.BulletObjectFactory;
import org.jill.openjill.core.api.message.statusbar.StatusBarTextMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryLifeMessage;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.jill.JillConst;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.message.EnumMessageType;

/**
 * Final player class.
 *
 * @author Emeric MARTINEAU
 */
public final class PlayerManager extends AbstractPlayerManager {

    /**
     * Logger.
     */
    protected static final Logger LOGGER = Logger.getLogger(
        PlayerManager.class.getName());

    /**
     * To know if message must be display.
     */
    private static boolean messageDisplayHitFloorMessage = true;

    /**
     * Size of array for right/middle/leflt.
     */
    private static final int DIRECTION_IMAGE_NUMBER = 3;

    /**
     * Begin player level picture.
     */
    private final BufferedImage[] stBegin
        = new BufferedImage[PlayerBeginConst.PICTURE_NUMBER];

    /**
     * Jump player to stand.
     */
    private final BufferedImage[][] stJumpingToStandPicture
        = new BufferedImage[DIRECTION_IMAGE_NUMBER][];

    /**
     * Jump players.
     */
    private final BufferedImage[][] stJumpingPicture
        = new BufferedImage[PlayerJumpingConst.PICTURE_NUMBER][];

    /**
     * Run left.
     */
    private final BufferedImage[] stStandLeftRunning
        = new BufferedImage[PlayerStandConst.PICTURE_RUNNING_NUMBER];

    /**
     * Run right.
     */
    private final BufferedImage[] stStandRightRunning
        = new BufferedImage[PlayerStandConst.PICTURE_RUNNING_NUMBER];

    /**
     * Full picture face.
     */
    private final BufferedImage[] stStandPicture
        = new BufferedImage[DIRECTION_IMAGE_NUMBER];

    /**
     * Full picture face.
     */
    private final BufferedImage[] stClimbPicture
        = new BufferedImage[PlayerClimbConst.PICTURE_NUMBER];

    /**
     * Die 0.
     */
    private final BufferedImage[] stDie0Enemy
        = new BufferedImage[PlayerDie0Const.IMAGE_NUMBER];

    /**
     * Die 1.
     */
    private final BufferedImage[] stDie1Water
        = new BufferedImage[PlayerDie1Const.IMAGE_NUMBER];

    /**
     * Die 2.
     */
    private final BufferedImage[] stDie2Other
        = new BufferedImage[PlayerDie2Const.IMAGE_NUMBER];

    /**
     * Current picture to display.
     */
    private BufferedImage currentPlayerPicture;

    /**
     * Current picture to display.
     */
    private BufferedImage stStandJillWaitWithArm;

    /**
     * Current picture to display.
     */
    private BufferedImage stStandJillSquat;

    /**
     * Value of curent wait animation.
     */
    private int waitAnimationIndex = 0;

    /**
     * Constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        stBegin[PlayerBeginConst.PICTURE_HEAD_DOWN]
            = pictureCache.getImage(PlayerBeginConst.TILESET_INDEX,
                PlayerBeginConst.TILE_HEAD_DOWN_INDEX);
        stBegin[PlayerBeginConst.PICTURE_HEAD_NORMAL]
            = pictureCache.getImage(PlayerBeginConst.TILESET_INDEX,
                PlayerBeginConst.TILE_HEAD_NORMAL_INDEX);
        stBegin[PlayerBeginConst.PICTURE_HEAD_UP]
            = pictureCache.getImage(PlayerBeginConst.TILESET_INDEX,
                PlayerBeginConst.TILE_HEAD_UP_INDEX);

        initJumpingPicture();

        initJumpingToStandPicture();

        for (int index = 0; index < stStandLeftRunning.length; index++) {
            stStandLeftRunning[index] = pictureCache.getImage(
                    PlayerStandConst.TILESET_INDEX, index);
        }

        for (int index = 0; index < stStandLeftRunning.length; index++) {
            stStandRightRunning[index] = pictureCache.getImage(
                    PlayerStandConst.TILESET_INDEX, index
                            + PlayerStandConst.TILE_LEFT_RUNNING_INDEX);
        }

        // Stand picture
        stStandPicture[0] = pictureCache.getImage(
                PlayerStandConst.TILESET_INDEX,
                PlayerStandConst.TILE_RIGHT_INDEX);
        stStandPicture[1] = pictureCache.getImage(
                PlayerStandConst.TILESET_INDEX,
                PlayerStandConst.TILE_MIDDLE_INDEX);
        stStandPicture[2] = pictureCache.getImage(
                PlayerStandConst.TILESET_INDEX,
                PlayerStandConst.TILE_LEFT_INDEX);

        initClimbPicture();

        stStandJillWaitWithArm = pictureCache.getImage(
                PlayerStandConst.TILESET_INDEX,
                PlayerStandConst.TILE_ARM_INDEX);

        initDiePicture();

        this.currentPlayerPicture = this.stStandPicture[1];
    }

    /**
     * Init climb picutre.
     */
    private void initClimbPicture() {
        // Climb picture
        int i = 0;
        final int i2 = 3;
        stClimbPicture[i++] = pictureCache.getImage(
                PlayerClimbConst.TILESET_INDEX, PlayerClimbConst.TILE_ONE);
        stClimbPicture[i++] = stClimbPicture[0];
        stClimbPicture[i++] = pictureCache.getImage(
                PlayerClimbConst.TILESET_INDEX, PlayerClimbConst.TILE_TWO);
        stClimbPicture[i++] = pictureCache.getImage(
                PlayerClimbConst.TILESET_INDEX, PlayerClimbConst.TILE_THREE);
        stClimbPicture[i++] = stClimbPicture[i2];
        stClimbPicture[i++] = stClimbPicture[2];
    }

    /**
     * Init jumping picture.
     */
    private void initJumpingPicture() {
        final BufferedImage[] stJumping
            = new BufferedImage[PlayerJumpingConst.PICTURE_NUMBER];
        final BufferedImage[] stJumpingLeft
            = new BufferedImage[PlayerJumpingConst.PICTURE_NUMBER];
        final BufferedImage[] stJumpingRight
            = new BufferedImage[PlayerJumpingConst.PICTURE_NUMBER];

        for (int index = 0; index < stJumping.length; index++) {
            stJumping[index] = pictureCache.getImage(
                    PlayerJumpingConst.TILESET_INDEX, index
                            + PlayerJumpingConst.TILE_MIDDLE_INDEX);
        }

        for (int index = 0; index < stJumpingLeft.length; index++) {
            stJumpingLeft[index] = pictureCache.getImage(
                    PlayerJumpingConst.TILESET_INDEX, index
                            + PlayerJumpingConst.TILE_LEFT_INDEX);
        }

        for (int index = 0; index < stJumpingRight.length; index++) {
            stJumpingRight[index] = pictureCache.getImage(
                    PlayerJumpingConst.TILESET_INDEX, index
                        + PlayerJumpingConst.TILE_RIGHT_INDEX);
        }

        stJumpingPicture[0] = stJumpingLeft;
        stJumpingPicture[1] = stJumping;
        stJumpingPicture[2] = stJumpingRight;
    }

    /**
     * Init jumping picture.
     */
    private void initJumpingToStandPicture() {
        final BufferedImage[] stJumpingToStandLeft
            = new BufferedImage[PlayerStandConst.PICTURE_NUMBER];
        final BufferedImage[] stJumpingToStandRight
            = new BufferedImage[PlayerStandConst.PICTURE_NUMBER];

        stStandJillSquat = pictureCache.getImage(
                PlayerStandConst.TILESET_INDEX,
                PlayerStandConst.TILE_DOWN_INDEX);

        BufferedImage temp = pictureCache.getImage(
                PlayerStandConst.TILESET_INDEX,
                PlayerStandConst.TILE_FALL_INDEX);

        final BufferedImage[] stJumpingToStand = {temp,
            stStandJillSquat, stStandJillSquat, temp, temp};

        int i = 0;
        stJumpingToStandLeft[i++] = pictureCache.getImage(
                PlayerStandConst.TILESET_INDEX,
                PlayerStandConst.TILE_LEFT_HIT_FLOOR_INDEX0);
        stJumpingToStandLeft[i++] = stJumpingToStandLeft[0];
        stJumpingToStandLeft[i++] = pictureCache.getImage(
                PlayerStandConst.TILESET_INDEX,
                PlayerStandConst.TILE_LEFT_HIT_FLOOR_INDEX2);
        stJumpingToStandLeft[i++] = stJumpingToStandLeft[2];
        stJumpingToStandLeft[i++] = pictureCache.getImage(
                PlayerStandConst.TILESET_INDEX,
                PlayerStandConst.TILE_LEFT_HIT_FLOOR_INDEX1);

        i = 0;

        stJumpingToStandRight[i++] = pictureCache.getImage(
                PlayerStandConst.TILESET_INDEX,
                PlayerStandConst.TILE_RIGHT_HIT_FLOOR_INDEX0);
        stJumpingToStandRight[i++] = stJumpingToStandRight[0];
        stJumpingToStandRight[i++] = pictureCache.getImage(
                PlayerStandConst.TILESET_INDEX,
                PlayerStandConst.TILE_RIGHT_HIT_FLOOR_INDEX2);
        stJumpingToStandRight[i++] = stJumpingToStandRight[2];
        stJumpingToStandRight[i++] = pictureCache.getImage(
                PlayerStandConst.TILESET_INDEX,
                PlayerStandConst.TILE_RIGHT_HIT_FLOOR_INDEX1);

        stJumpingToStandPicture[0] = stJumpingToStandLeft;
        stJumpingToStandPicture[1] = stJumpingToStand;
        stJumpingToStandPicture[2] = stJumpingToStandRight;

        drawPieceOfLand(stJumpingToStandPicture);
    }

    @Override
    public BufferedImage msgDraw() {
        BufferedImage currentPicture;

        switch (getState()) {
            case PlayerState.BEGIN:
                currentPicture = msgDrawBegin();
                break;
            case PlayerState.JUMPING:
                currentPicture = msgDrawJumping();
                break;
            case PlayerState.STAND:
                currentPicture = msgDrawStand();
                break;
            case PlayerState.CLIMBING:
                currentPicture = msgDrawClimb();
                break;
            case PlayerState.DIE:
                currentPicture = msgDrawDied();
                break;
            default:
                currentPicture = null;
                LOGGER.severe(
                    String.format("The state %d is unknow for player !",
                        getState()));
        }

        this.currentPlayerPicture = currentPicture;

        return currentPicture;
    }

    @Override
    public void setState(final int state) {
//        // Don't change state if same
//        if (state == this.state && subState != 0) {
//            return;
//        }
        switch(state) {
            case PlayerState.STAND:
                setSubState(0);
                break;
            case PlayerState.BEGIN:
                setSubState(PlayerState.BEGIN_SUB_STATE);
                setStateCount(X_SPEED_MIDDLE);
                break;
            case PlayerState.JUMPING:
                setSubState(X_SPEED_MIDDLE);
                break;
            default:
                break;
        }

        // State Die managed in msgKill()
        super.setState(state);
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        move(keyboardLayout);

        switch (getState()) {
            case PlayerState.BEGIN:
                msgUpdateBegin();
                break;
            case PlayerState.JUMPING:
                msgUpdateJumping();
                break;
            case PlayerState.STAND:
                msgUpdateStand();
                break;
            case PlayerState.CLIMBING:
                msgUpdateClimb();
                break;
            case PlayerState.DIE:
                msgUpdateDied();
                break;
            default:
                LOGGER.severe(
                    String.format("The state %d is unknow for player !",
                        getState()));
        }
    }

    /**
     * Manage update message at begin state.
     */
    private void msgUpdateBegin() {
        this.stateCount++;

        if (this.stateCount
            >= PlayerBeginConst.PICTURE_HEAD_DOWN_STATECOUNT) {
            setState(PlayerState.STAND);
        }
    }

    /**
     * Manage update message at begin state.
     */
    private void msgUpdateJumping() {
        this.stateCount++;
        this.subState++;
    }

    /**
     * Display climb picture.
     */
    private void msgUpdateClimb() {
        this.stateCount++;
    }

    /**
     * Player stand.
     */
    private void msgUpdateStand() {
        this.stateCount++;
        // Down
        if (this.stateCount
                > PlayerStandConst.HIT_FLOOR_ANIMATION_STATECOUNT) {
            msgUpdateStandHitFloorAnimation();
        } else if (this.xSpeed == X_SPEED_MIDDLE
                && this.ySpeed == Y_SPEED_MIDDLE) {
            msgUpdateStandWait();
        }
    }

    /**
     * Player no move.
     */
    private void msgUpdateStandWait() {
        if (this.stateCount
                == PlayerStandConst.STATECOUNT_WAIT_MSG) {
            msgUpdateStandWatDisplayMessage();
        } else if (this.stateCount
                >= PlayerStandConst.STATECOUNT_WAIT_END) {
            // Reinit
            setStateCount(0);
        }
    }

    /**
     * Display message and set index of wait animation.
     */
    private void msgUpdateStandWatDisplayMessage() {
        // Generate wait animation index
        final int max = PlayerWaitConst.WAIT_MESSAGES.length;
        // 0 value is reserved when reload game
        this.waitAnimationIndex = (int) (Math.random() * max) + 1;

        this.messageDispatcher.sendMessage(
                EnumMessageType.MESSAGE_STATUS_BAR,
                PlayerWaitConst.WAIT_MESSAGES[this.waitAnimationIndex - 1]);
    }

    /**
     * Stand hit floor animation.
     */
    private void msgUpdateStandHitFloorAnimation() {
        // Hit floor animation
        if (this.counter == 0) {
            // End hit animation
            if (messageDisplayHitFloorMessage) {
                messageDisplayHitFloorMessage = false;
                final StatusBarTextMessage msg =
                        new StatusBarTextMessage(
                                "Hey,  your shoes are untied.", 10,
                                TextManager.COLOR_GREEN);

                this.messageDispatcher.sendMessage(
                        EnumMessageType.MESSAGE_STATUS_BAR, msg);
            }

            setStateCount(1);
        }

        this.counter--;
    }

    /**
     * Message update for die.
     */
    private void msgUpdateDied() {
        switch (this.subState) {
            case PlayerState.DIE_SUB_STATE_ENNEMY:
                msgUpdateDiedEnnemy();
                break;
            case PlayerState.DIE_SUB_STATE_WATER_BACK:
                msgUpdateDiedWater();
                break;
            case PlayerState.DIE_SUB_STATE_OTHER_BACK:
                msgUpdateDiedOther();
                break;
            default:
        }
    }

    /**
     * Message update for die 0.
     */
    private void msgUpdateDiedEnnemy() {
        if (this.stateCount >= PlayerDie0Const.STATECOUNT_MAX_TO_RESTART_GAME) {
            this.messageDispatcher.sendMessage(
                EnumMessageType.DIE_RESTART_LEVEL, null);
        } else {
            this.stateCount++;
        }
    }

    /**
     * Message update for die .
     */
    private void msgUpdateDiedWater() {
        if (this.stateCount >= PlayerDie1Const.STATECOUNT_MAX_TO_RESTART_GAME) {
            this.messageDispatcher.sendMessage(
                EnumMessageType.DIE_RESTART_LEVEL, null);
        } else {
            this.stateCount++;
        }
    }

    /**
     * Message update for die 2.
     */
    private void msgUpdateDiedOther() {
        if (this.ySpeed > PlayerDie2Const.YD_MAX_TO_CHANGE) {
            if (this.stateCount
                > PlayerDie2Const.STATECOUNT_MAX_TO_RESTART_GAME) {
                this.messageDispatcher.sendMessage(
                    EnumMessageType.DIE_RESTART_LEVEL, null);
            }

            // Realign player on background
            if (this.stateCount == 0) {
                final BufferedImage currentPicture = getDieOtherPicture();

                // Align on block under kill background.
                this.y = (((this.y / JillConst.getBlockSize()))
                    * JillConst.getBlockSize()) + JillConst.getBlockSize()
                    + currentPicture.getHeight();

                // If player out of screen
                if ((this.y + currentPicture.getHeight())
                    > JillConst.getMaxHeight()) {
                    this.y = JillConst.getMaxHeight()
                        - currentPicture.getHeight();
                }
            }

            this.stateCount++;
        } else {
            this.y += this.ySpeed;
            this.ySpeed += 2;

//            this.currentPicture = this.stDie2[PlayerDie2Const.FIRST_PICTURE];
        }
    }

    /**
     * Kill player.
     *
     * @param senderObj object kill player (or hit)
     * @param senderBack background kill player
     * @param nbLife number life
     * @param typeOfDeath type of death
     */
    private void msgKill(final ObjectEntity senderObj,
        final BackgroundEntity senderBack,
        final int nbLife, final int typeOfDeath) {
        // senderObj was null when background
        if (!PalyerActionPerState.canDo(this.state,
            PlayerAction.INVINCIBLE)) {
            BackgroundEntity senderBack2 = senderBack;

            InventoryLifeMessage.STD_MESSAGE.setLife(nbLife);

            // In special case if sender is not null and typeOfDeath is other
            // force hit player
            if (senderObj != null &&
                    typeOfDeath == PlayerState.DIE_SUB_STATE_OTHER_BACK) {
                senderBack2 = getBackgroundObject()[
                        this.getX() / JillConst.getBlockSize()][
                        this.getY() / JillConst.getBlockSize()];
            } else {
                InventoryLifeMessage.STD_MESSAGE.setSender(senderObj);
            }

            // Send message to inventory to know if player dead
            this.messageDispatcher.sendMessage(EnumMessageType.INVENTORY_LIFE,
                InventoryLifeMessage.STD_MESSAGE);

            if (InventoryLifeMessage.STD_MESSAGE.isPlayerDead()) {
                setState(PlayerState.DIE);
                setSubState(typeOfDeath);
                setStateCount(0);

                switch (typeOfDeath) {
                    case PlayerState.DIE_SUB_STATE_OTHER_BACK:
                        setySpeed(PlayerDie2Const.START_YD);

                        // Align player on bottom of background
                        this.y = (senderBack2.getY() + 1) * JillConst.getBlockSize()
                            - this.stDie2Other[
                                    PlayerDie2Const.FIRST_PICTURE].getHeight();
                        break;
                    case PlayerState.DIE_SUB_STATE_WATER_BACK:
                        setySpeed(PlayerDie1Const.START_YD);

                        // Align player on bottom of background
                        this.y = senderBack2.getY() * JillConst.getBlockSize()
                            - this.stDie1Water[
                                    PlayerDie1Const.FIRST_PICTURE].getHeight();
                        break;
                    case PlayerState.DIE_SUB_STATE_ENNEMY:
                        setySpeed(PlayerDie0Const.START_YD);
                    default:
                    // ennemy death
                }

                BulletObjectFactory.explode(this,
                        PlayerDie0Const.NB_COLORED_BULLET,
                        this.messageDispatcher);
            }
        }
    }

    @Override
    public void msgKill(final ObjectEntity sender, final int nbLife,
        final int typeOfDeath) {
        msgKill(sender, null, nbLife, typeOfDeath);
    }

    @Override
    public void msgKill(final BackgroundEntity sender,
        final int nbLife, final int typeOfDeath) {
        msgKill(null, sender, nbLife, typeOfDeath);
    }

    @Override
    public int getWidth() {
        if (this.currentPlayerPicture == null) {
            this.currentPlayerPicture = msgDraw();
        }

        return this.currentPlayerPicture.getWidth();
    }

    @Override
    public int getHeight() {
        if (this.currentPlayerPicture == null) {
            this.currentPlayerPicture = msgDraw();
        }

        return this.currentPlayerPicture.getHeight();
    }

    /**
     * Init die picture.
     */
    private void initDiePicture() {
        // Just for checkstyle, sorry
        int i = 0;
        stDie2Other[i++] = pictureCache.getImage(PlayerDie2Const.TILESET_INDEX,
                PlayerDie2Const.TILE0);
        stDie2Other[i++] = pictureCache.getImage(PlayerDie2Const.TILESET_INDEX,
                PlayerDie2Const.TILE1);
        stDie2Other[i++] = pictureCache.getImage(PlayerDie2Const.TILESET_INDEX,
                PlayerDie2Const.TILE2);
        stDie2Other[i++] = pictureCache.getImage(PlayerDie2Const.TILESET_INDEX,
                PlayerDie2Const.TILE3);

        for (int index = 0; index < PlayerDie0Const.IMAGE_NUMBER; index++) {
            this.stDie0Enemy[index] = pictureCache.getImage(
                PlayerDie0Const.TILESET_INDEX,
                PlayerDie0Const.TILE_INDEX + index);
        }

        for (int index = 0; index < PlayerDie1Const.IMAGE_NUMBER; index++) {
            this.stDie1Water[index] = pictureCache.getImage(
                PlayerDie1Const.TILESET_INDEX,
                PlayerDie1Const.TILE_INDEX + index);
        }
    }

    /**
     * Manage draw at begin state.
     *
     * @return picture to draw
     */
    private BufferedImage msgDrawBegin() {
        final int indexPicture;
        BufferedImage currentPicture;

        // Get good picture
        if (this.stateCount < PlayerBeginConst.PICTURE_HEAD_UP_STATECOUNT) {
            indexPicture = PlayerBeginConst.PICTURE_HEAD_UP;
        } else if (this.stateCount
            < PlayerBeginConst.PICTURE_HEAD_NORMAL_STATECOUNT) {
            indexPicture = PlayerBeginConst.PICTURE_HEAD_NORMAL;
        } else if (this.stateCount
            < PlayerBeginConst.PICTURE_HEAD_DOWN_STATECOUNT) {
            indexPicture = PlayerBeginConst.PICTURE_HEAD_DOWN;
        } else {
            indexPicture = DIRECTION_IMAGE_NUMBER;
        }

        if (indexPicture < DIRECTION_IMAGE_NUMBER) {
            // Get image
            final BufferedImage baseImage = this.stBegin[indexPicture];

            currentPicture = new BufferedImage(baseImage.getWidth(),
                baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

            final int reduction = baseImage.getHeight() - this.stateCount;

            // Draw picutre
            final Graphics2D g2 = currentPicture.createGraphics();

            g2.drawImage(baseImage, 0, reduction, null);

            g2.dispose();
        } else {
            currentPicture = null;
        }

        return currentPicture;
    }

    /**
     * Manage update message at begin state.
     *
     * @return picture to draw
     */
    private BufferedImage msgDrawJumping() {
        BufferedImage currentPicture;

        if (getSubState() >= PlayerStandConst.SUBSTATE_VALUE_TO_FALL) {
            // Animation picture done
            if (getySpeed() > Y_SPEED_MIDDLE) {
                // Down
                currentPicture =
                    this.stJumpingToStandPicture[this.xSpeed + 1][0];
            } else {
                currentPicture =
                    stJumpingPicture[this.xSpeed + 1][2];
            }
        } else {
            currentPicture =
                this.stJumpingPicture[this.xSpeed + 1][this.subState];
        }

        return currentPicture;
    }

    /**
     * Player stand.
     *
     * @return picture to draw
     */
    private BufferedImage msgDrawStand() {
        BufferedImage currentPicture;

        // Down
        if (this.stateCount
                > PlayerStandConst.HIT_FLOOR_ANIMATION_STATECOUNT) {
            currentPicture = msgDrawStandHitFloorAnimation();
        } else {
            if (this.xSpeed == X_SPEED_MIDDLE) {
                if (this.ySpeed < Y_SPEED_MIDDLE) {
                    // Head up
                    currentPicture
                            = stBegin[PlayerBeginConst.PICTURE_HEAD_UP];
                } else if (this.ySpeed >= PlayerStandConst.Y_SPEED_SQUAT_DOWN) {
                    // Jill squat
                    currentPicture = stStandJillSquat;
                } else if (this.ySpeed > Y_SPEED_MIDDLE) {
                    // Head down
                    currentPicture
                            = stBegin[PlayerBeginConst.PICTURE_HEAD_DOWN];
                } else {
                    currentPicture = msgDrawStandWait();
                }
            } else {
                if (this.xSpeed < X_SPEED_MIDDLE) {
                    // Player running.
                    currentPicture
                            = this.stStandLeftRunning[this.subState];
                } else {
                    currentPicture
                            = this.stStandRightRunning[this.subState];
                }
            }
        }

        return currentPicture;
    }

    /**
     * Stand hit floor animation.
     *
     * @return picture to draw
     */
    private BufferedImage msgDrawStandHitFloorAnimation() {
        BufferedImage currentPicture;

        // Hit floor animation
        int indexPicture = this.stateCount
                - PlayerStandConst.HIT_FLOOR_ANIMATION_STATECOUNT - 1;

        if (indexPicture
                >= this.stJumpingToStandPicture[
                this.xSpeed + 1].length) {
            indexPicture = 0;
        }

        currentPicture =
                this.stJumpingToStandPicture[
                this.xSpeed + 1][indexPicture];

        return currentPicture;
    }

    /**
     * Player no move.
     *
     * @return picture to draw
     */
    private BufferedImage msgDrawStandWait() {
        BufferedImage currentPicture;

        if (this.info1 != X_SPEED_MIDDLE
                && this.stateCount
                >= PlayerStandConst.STATECOUNT_LEFT_RIGHT_TO_FACE
                && this.stateCount
                < PlayerStandConst.STATECOUNT_WAIT_ARM) {
            currentPicture = this.stStandPicture[1];
        } else if (this.stateCount
                >= PlayerStandConst.STATECOUNT_WAIT_ARM
                && this.stateCount
                < PlayerStandConst.STATECOUNT_WAIT_MSG) {
            currentPicture = this.stStandJillWaitWithArm;
        } else if (this.stateCount
                == PlayerStandConst.STATECOUNT_WAIT_MSG) {
            currentPicture = this.stStandJillWaitWithArm;
        } else if (this.stateCount
                >= PlayerStandConst.STATECOUNT_WAIT_MSG
                && this.stateCount
                < PlayerStandConst.STATECOUNT_WAIT_ANIMATION) {
            currentPicture = this.stStandJillWaitWithArm;
        } else if (this.stateCount
                >= PlayerStandConst.STATECOUNT_WAIT_ANIMATION
                && this.stateCount
                < PlayerStandConst.STATECOUNT_WAIT_END) {
            currentPicture = msgDrawStandWaitDisplayAnimation();
        } else {
            currentPicture = this.stStandPicture[this.info1 + 1];
        }

        return currentPicture;
    }

    /**
     * Display wait animation.
     *
     * @return picture to draw
     */
    private BufferedImage msgDrawStandWaitDisplayAnimation() {
        BufferedImage currentPicture;

        switch(this.waitAnimationIndex - 1) {
            case PlayerWaitConst.HAVE_YOU_SEEN_JILL_ANYWHERE:
                int reste = (int) Math.IEEEremainder(this.stateCount
                        - PlayerStandConst.STATECOUNT_WAIT_ANIMATION,
                        PlayerWaitConst.HAVE_YOU_SEEN_JILL_ANYWHERE_DIV);
                switch (reste) {
                    case 0:
                    case 1:
                        currentPicture
                            = stBegin[PlayerBeginConst.PICTURE_HEAD_UP];
                        break;
                    case 2:
                    case 3:
                        currentPicture
                            = stBegin[PlayerBeginConst.PICTURE_HEAD_NORMAL];
                        break;
                    default:
                        currentPicture
                            = stBegin[PlayerBeginConst.PICTURE_HEAD_DOWN];
                }
                break;
            case PlayerWaitConst.LOOK_AN_AIREPLANE:
                currentPicture
                        = stBegin[PlayerBeginConst.PICTURE_HEAD_UP];
                break;
            case PlayerWaitConst.HEY_YOUR_SHOES_ARE_UNTIED:
                currentPicture
                        = stBegin[PlayerBeginConst.PICTURE_HEAD_DOWN];
                break;
            case PlayerWaitConst.ARE_YOU_JUST_GONNA_SIT_THERE:
                currentPicture = stStandJillWaitWithArm;
                break;
            default:
                // From restore game
                currentPicture = this.stStandPicture[1];
        }

        return currentPicture;
    }

    /**
     * Display climb picture.
     *
     * @return picture to draw
     */
    private BufferedImage msgDrawClimb() {
        // subState update in AbstractPlayerManager.moveStdPlayerUpClimb()
        return stClimbPicture[subState];
    }

    /**
     * Message draw for die.
     *
     * @return picture to draw
     */
    private BufferedImage msgDrawDied() {
        BufferedImage currentPicture;

        switch (this.subState) {
            case PlayerState.DIE_SUB_STATE_ENNEMY:
                currentPicture = msgDrawDiedEnnemy();
                break;
            case PlayerState.DIE_SUB_STATE_WATER_BACK:
                currentPicture = msgDrawDiedWater();
                break;
            case PlayerState.DIE_SUB_STATE_OTHER_BACK:
                currentPicture = msgDrawDiedOther();
                break;
            default:
                currentPicture = null;
        }

        return currentPicture;
    }

    /**
     * Message draw for die 0.
     *
     * @return picture to draw
     */
    private BufferedImage msgDrawDiedEnnemy() {
        final int indexPicture = this.stateCount
                / PlayerDie0Const.STATECOUNT_STEP_TO_CHANGE_PICTURE;
        return this.stDie0Enemy[indexPicture];
    }

    /**
     * Message draw for die 1.
     *
     * @return picture to draw
     */
    private BufferedImage msgDrawDiedWater() {
        BufferedImage currentPicture;

        if (this.stateCount == 0) {
            currentPicture = this.stDie1Water[
                    PlayerDie1Const.FIRST_PICTURE];
        } else {
            int indexPicture = this.stateCount
                / PlayerDie1Const.STATECOUNT_STEP_TO_CHANGE_PICTURE;

            if (indexPicture < this.stDie1Water.length) {
                currentPicture = this.stDie1Water[indexPicture];
            } else {
                currentPicture = null;
            }
        }

        return currentPicture;
    }

    /**
     * Message update for die 2.
     *
     * @return picture to draw
     */
    private BufferedImage msgDrawDiedOther() {
        BufferedImage currentPicture;

        if (this.ySpeed > PlayerDie2Const.YD_MAX_TO_CHANGE) {
            currentPicture = getDieOtherPicture();
        } else {
            this.y += this.ySpeed;
            this.ySpeed += 2;

            currentPicture = this.stDie2Other[PlayerDie2Const.FIRST_PICTURE];
        }

        return currentPicture;
    }

    /**
     * Return pictue of die other. Call to update object and draw object.
     *
     * @return picture
     */
    private BufferedImage getDieOtherPicture() {
        BufferedImage currentPicture;
        if (this.stateCount
                < PlayerDie2Const.STATECOUNT_MAX_TO_FIRST_ANIMATION) {
            currentPicture = this.stDie2Other[(this.stateCount % 2) + 1];
        } else {
            currentPicture
                    = this.stDie2Other[PlayerDie2Const.LAST_PICTURE];
        }

        return currentPicture;
    }
}
