package org.jill.game.entities.obj.player;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;
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
 * TODO object 36 : bullet colored when player die.
 * TODO for better compatibility. msgUpdate don't set picture. It's msgDraw.
 *
 * @author Emeric MARTINEAU
 */
public final class PlayerManager extends AbstractPlayerInteractionManager {

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
    private final BufferedImage[] stDie0
        = new BufferedImage[PlayerDie0Const.IMAGE_NUMBER];

    /**
     * Die 2.
     */
    private final BufferedImage[] stDie2
        = new BufferedImage[PlayerDie2Const.IMAGE_NUMBER];

    /**
     * Current picture to display.
     */
    private BufferedImage currentPicture;

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

        stateCount = 0;
        counter = 0;
        subState = 0;

        currentPicture = stStandPicture[1];
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
    public void msgUpdate() {
        switch (getState()) {
            case PlayerState.BEGIN:
                this.stateCount++;

                msgUpdateBegin();
                break;
            case PlayerState.JUMPING:
                this.stateCount++;
                this.subState++;

                msgUpdateJumping();
                break;
            case PlayerState.STAND:
                this.stateCount++;

                msgUpdateStand();
                break;
            case PlayerState.CLIMBING:
                this.stateCount++;

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
        final int indexPicture;

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

            setState(PlayerState.STAND);
        }

        if (indexPicture < DIRECTION_IMAGE_NUMBER) {
            // Get image
            final BufferedImage baseImage = this.stBegin[indexPicture];

            this.currentPicture = new BufferedImage(baseImage.getWidth(),
                baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

            final int reduction = baseImage.getHeight() - this.stateCount;

            // Draw picutre
            final Graphics2D g2 = this.currentPicture.createGraphics();

            g2.drawImage(baseImage, 0, reduction, null);

            g2.dispose();
        }
    }

    /**
     * Manage update message at begin state.
     */
    private void msgUpdateJumping() {
        if (getSubState() >= PlayerStandConst.SUBSTATE_VALUE_TO_FALL) {
            // Animation picture done
            if (getySpeed() > Y_SPEED_MIDDLE) {
                // Down
                this.currentPicture =
                    this.stJumpingToStandPicture[this.xSpeed + 1][0];
            } else {
                this.currentPicture =
                    stJumpingPicture[this.xSpeed + 1][2];
            }
        } else {
            this.currentPicture =
                this.stJumpingPicture[this.xSpeed + 1][this.subState];
        }
    }

    /**
     * Display climb picture.
     */
    private void msgUpdateClimb() {
        // subState update in AbstractPlayerManager.moveStdPlayerUpClimb()
        currentPicture = stClimbPicture[subState];
    }

    /**
     * Player stand.
     */
    private void msgUpdateStand() {
        // Down
        if (this.stateCount
                > PlayerStandConst.HIT_FLOOR_ANIMATION_STATECOUNT) {
            msgUpdateStandHitFloorAnimation();
        } else {
            if (this.xSpeed == X_SPEED_MIDDLE) {
                if (this.ySpeed < Y_SPEED_MIDDLE) {
                    // Head up
                    this.currentPicture
                            = stBegin[PlayerBeginConst.PICTURE_HEAD_UP];
                } else if (this.ySpeed >= PlayerStandConst.Y_SPEED_SQUAT_DOWN) {
                    // Jill squat
                    this.currentPicture = stStandJillSquat;
                } else if (this.ySpeed > Y_SPEED_MIDDLE) {
                    // Head down
                    this.currentPicture
                            = stBegin[PlayerBeginConst.PICTURE_HEAD_DOWN];
                } else {
                    msgUpdateStandWait();
                }
            } else {
                if (this.xSpeed < X_SPEED_MIDDLE) {
                    // Player running.
                    this.currentPicture
                            = this.stStandLeftRunning[this.subState];
                } else {
                    this.currentPicture
                            = this.stStandRightRunning[this.subState];
                }
            }
        }
    }

    /**
     * Player no move.
     */
    private void msgUpdateStandWait() {
        if (this.info1 != X_SPEED_MIDDLE
                && this.stateCount
                >= PlayerStandConst.STATECOUNT_LEFT_RIGHT_TO_FACE
                && this.stateCount
                < PlayerStandConst.STATECOUNT_WAIT_ARM) {
            //this.info1 = X_SPEED_MIDDLE;
            this.currentPicture = this.stStandPicture[1];
        } else if (this.stateCount
                >= PlayerStandConst.STATECOUNT_WAIT_ARM
                && this.stateCount
                < PlayerStandConst.STATECOUNT_WAIT_MSG) {
            this.currentPicture = this.stStandJillWaitWithArm;
        } else if (this.stateCount
                == PlayerStandConst.STATECOUNT_WAIT_MSG) {
            msgUpdateStandWatDisplayMessage();
        } else if (this.stateCount
                >= PlayerStandConst.STATECOUNT_WAIT_MSG
                && this.stateCount
                < PlayerStandConst.STATECOUNT_WAIT_ANIMATION) {
            this.currentPicture = this.stStandJillWaitWithArm;
        } else if (this.stateCount
                >= PlayerStandConst.STATECOUNT_WAIT_ANIMATION
                && this.stateCount
                < PlayerStandConst.STATECOUNT_WAIT_END) {
            msgUpdateStandWaitDisplayAnimation();
        } else if (this.stateCount
                >= PlayerStandConst.STATECOUNT_WAIT_END) {
            // Reinit
            setStateCount(0);
        } else {
            this.currentPicture = this.stStandPicture[this.info1 + 1];
        }
    }

    /**
     * Display wait animation.
     */
    private void msgUpdateStandWaitDisplayAnimation() {
        switch(this.waitAnimationIndex - 1) {
            case PlayerWaitConst.HAVE_YOU_SEEN_JILL_ANYWHERE:
                int reste = (int) Math.IEEEremainder(this.stateCount
                        - PlayerStandConst.STATECOUNT_WAIT_ANIMATION,
                        PlayerWaitConst.HAVE_YOU_SEEN_JILL_ANYWHERE_DIV);
                switch (reste) {
                    case 0:
                    case 1:
                        this.currentPicture
                            = stBegin[PlayerBeginConst.PICTURE_HEAD_UP];
                        break;
                    case 2:
                    case 3:
                        this.currentPicture
                            = stBegin[PlayerBeginConst.PICTURE_HEAD_NORMAL];
                        break;
                    default:
                        this.currentPicture
                            = stBegin[PlayerBeginConst.PICTURE_HEAD_DOWN];
                }
                break;
            case PlayerWaitConst.LOOK_AN_AIREPLANE:
                this.currentPicture
                        = stBegin[PlayerBeginConst.PICTURE_HEAD_UP];
                break;
            case PlayerWaitConst.HEY_YOUR_SHOES_ARE_UNTIED:
                this.currentPicture
                        = stBegin[PlayerBeginConst.PICTURE_HEAD_DOWN];
                break;
            case PlayerWaitConst.ARE_YOU_JUST_GONNA_SIT_THERE:
                this.currentPicture = stStandJillWaitWithArm;
                break;
            default:
                // From restore game
                this.currentPicture = this.stStandPicture[1];
        }
    }

    /**
     * Display message and set index of wait animation.
     */
    private void msgUpdateStandWatDisplayMessage() {
        this.currentPicture = this.stStandJillWaitWithArm;

        // Generate wait animation index
        final int max = PlayerWaitConst.WAIT_MESSAGES.length;
        // 0 value is reserved when reload game
        this.waitAnimationIndex = (int) (Math.random() * max) + 1;

        this.messageDispatcher.sendMessage(
                EnumMessageType.MESSAGE_STATUS_BAR,
                PlayerWaitConst.WAIT_MESSAGES[this.waitAnimationIndex - 1]);
    }

    /**
     * Stand hit florr animation.
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
        } else {
            // Hit floor animation
            int indexPicture = this.stateCount
                    - PlayerStandConst.HIT_FLOOR_ANIMATION_STATECOUNT - 1;

            if (indexPicture
                    >= this.stJumpingToStandPicture[
                    this.xSpeed + 1].length) {
                indexPicture = 0;
            }

            this.currentPicture =
                    this.stJumpingToStandPicture[
                    this.xSpeed + 1][indexPicture];
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
            int indexPicture = this.stateCount
                / PlayerDie0Const.STATECOUNT_STEP_TO_CHANGE_PICTURE;
            this.currentPicture = this.stDie0[indexPicture];

            this.stateCount++;
        }
    }

    /**
     * Message update for die 2.
     */
    private void msgUpdateDiedOther() {
        if (this.ySpeed > PlayerDie2Const.YD_MAX_TO_CHANGE) {
            if (this.stateCount
                < PlayerDie2Const.STATECOUNT_MAX_TO_FIRST_ANIMATION) {
                this.currentPicture = this.stDie2[(this.stateCount % 2) + 1];
            } else if (this.stateCount
                < PlayerDie2Const.STATECOUNT_MAX_TO_RESTART_GAME) {
                this.currentPicture
                    = this.stDie2[PlayerDie2Const.LAST_PICTURE];
            } else {
                this.messageDispatcher.sendMessage(
                    EnumMessageType.DIE_RESTART_LEVEL, null);
            }

            // Realign player on background
            if (this.stateCount == 0) {
                // Align on block under kill background.
                this.y = (((this.y / JillConst.BLOCK_SIZE))
                    * JillConst.BLOCK_SIZE) + JillConst.BLOCK_SIZE
                    + this.currentPicture.getHeight();

                // If player out of screen
                if ((this.y + this.currentPicture.getHeight())
                    > JillConst.MAX_HEIGHT) {
                    this.y = JillConst.MAX_HEIGHT
                        - this.currentPicture.getHeight();
                }
            }

            this.stateCount++;
        } else {
            this.y += this.ySpeed;
            this.ySpeed += 2;

            this.currentPicture = this.stDie2[PlayerDie2Const.FIRST_PICTURE];
        }
    }


    @Override
    public void msgKeyboard(final ObjectEntity obj,
        final KeyboardLayout keyboardLayout) {
        move(keyboardLayout);
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
            InventoryLifeMessage.STD_MESSAGE.setLife(nbLife);
            InventoryLifeMessage.STD_MESSAGE.setSender(senderObj);

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
                        this.y = (senderBack.getY() + 1) * JillConst.BLOCK_SIZE
                            - this.stDie2[
                                    PlayerDie2Const.FIRST_PICTURE].getHeight();
                        break;
                    case PlayerState.DIE_SUB_STATE_ENNEMY:
                        setySpeed(PlayerDie0Const.START_YD);
                    default:
                    // ennemy death
                }

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
        return this.currentPicture.getWidth();
    }

    @Override
    public int getHeight() {
        return this.currentPicture.getHeight();
    }

    /**
     * Init die picture.
     */
    private void initDiePicture() {
        // Just for checkstyle, sorry
        int i = 0;
        stDie2[i++] = pictureCache.getImage(PlayerDie2Const.TILESET_INDEX,
                PlayerDie2Const.TILE0);
        stDie2[i++] = pictureCache.getImage(PlayerDie2Const.TILESET_INDEX,
                PlayerDie2Const.TILE1);
        stDie2[i++] = pictureCache.getImage(PlayerDie2Const.TILESET_INDEX,
                PlayerDie2Const.TILE2);
        stDie2[i++] = pictureCache.getImage(PlayerDie2Const.TILESET_INDEX,
                PlayerDie2Const.TILE3);

        for (int index = 0; index < PlayerDie0Const.IMAGE_NUMBER; index++) {
            this.stDie0[index] = pictureCache.getImage(
                PlayerDie0Const.TILESET_INDEX,
                PlayerDie0Const.TILE_INDEX + index);
        }
    }
}
