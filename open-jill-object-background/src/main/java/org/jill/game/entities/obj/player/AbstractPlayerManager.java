package org.jill.game.entities.obj.player;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.jill.game.entities.obj.util.UtilityObjectEntity;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;

/**
 * Class to manage player movement.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractPlayerManager
        extends AbstractPlayerInteractionManager {

    /**
     * Background object.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * Number of jump.
     */
    private int highJumpSize;

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        playerObject = true;

        backgroundObject = objectParam.getBackgroundObject();
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

        switch (getState()) {
            case PlayerState.STAND:
                moveStdPlayerUpDownStand(keyboardLayout);
                break;
            case PlayerState.JUMPING:
                moveStdPlayerUpDownJumping();
                break;
            case PlayerState.CLIMBING:
                moveStdPlayerUpDownClimbing(keyboardLayout);
                break;
            default:
        }

        switch (getState()) {
            case PlayerState.STAND:
                moveStdPlayerLeftRightStand(keyboardLayout);
                break;
            case PlayerState.JUMPING:
                moveStdPlayerLeftRightJumping(keyboardLayout);
                break;
            case PlayerState.CLIMBING:
                moveStdPlayerLeftRightClimbing(keyboardLayout);
                break;
            default:
        }
    }

    /**
     * Add high jump value.
     *
     * @param value value
     */
    @Override
    protected final void addHighJump(final int value) {
        this.highJumpSize += value;
    }

    /**
     * Move player on floor.
     *
     * @param keyboardLayout keyboard
     */
    private void moveStdPlayerUpDownStand(
            final KeyboardLayout keyboardLayout) {
        // Check if floor under player
        if (UtilityObjectEntity.checkIfFloorUnderObject(this,
                this.backgroundObject)) {
            int yd;

            if (keyboardLayout.isJump()) {
                yd = changeToJumpState();
            } else if (keyboardLayout.isUp()) {
                yd = moveStdPlayerUpStand();
            } else if (keyboardLayout.isDown()) {
                yd = moveStdPlayerDownStand();
            } else {
                yd = Y_SPEED_MIDDLE;
            }

            setySpeed(yd);
        } else {
            // Ok no floor, fall player
            setState(PlayerState.JUMPING);
            setSubState(0);
            setySpeed(Y_SPEED_MIDDLE);

            // Check climb
            if (UtilityObjectEntity.isClimbing(
                    this, this.backgroundObject)) {
                changeToClimbState();

                if (keyboardLayout.isLeft()) {
                    setySpeed(Y_SPEED_UP);
                } else if (keyboardLayout.isRight()) {
                    setySpeed(Y_SPEED_DOWN);
                }
            }
        }
    }

    /**
     * Set jump state.
     *
     * @return yd nex value
     */
    private int changeToJumpState() {
        int yd;

        if (getState() == PlayerState.CLIMBING) {
            yd = -(PlayerJumpingConst.JUMP_INIT_SIZE_FOR_CLIMB);
            setySpeed(yd);
        } else {
            yd = -(PlayerJumpingConst.JUMP_INIT_SIZE + highJumpSize);
        }

        setState(PlayerState.JUMPING);

        return yd;
    }

    /**
     * Move head down.
     *
     * @return yd
     */
    private int moveStdPlayerDownStand() {
        int yd = getySpeed();

        if (yd >= PlayerStandConst.Y_SPEED_HEAD_DOWN) {
            yd = PlayerStandConst.Y_SPEED_SQUAT_DOWN;
        } else {
            yd = PlayerStandConst.Y_SPEED_HEAD_DOWN;
        }

        setStateCount(0);

        return yd;
    }

    /**
     * Move head up.
     *
     * @return yd
     */
    private int moveStdPlayerUpStand() {
        int yd;
        if (UtilityObjectEntity.isClimbing(
                this, this.backgroundObject)) {
            changeToClimbState();

            yd = getySpeed();
        } else {
            yd = PlayerStandConst.Y_SPEED_HEAD_UP;
            setStateCount(0);
        }
        return yd;
    }

    /**
     * Move in jumping.
     */
    private void moveStdPlayerUpDownJumping() {
        boolean playerMove;
        boolean playerCanUpDown = true;

        if (this.ySpeed > Y_SPEED_MIDDLE) {
            playerMove = UtilityObjectEntity.moveObjectDown(this, this.ySpeed,
                    this.backgroundObject);
            // Down.
            // subState check don't need cause
            if (!playerMove) {
                // Block or floor hit
                setState(PlayerState.STAND);
                setStateCount(
                        PlayerStandConst.HIT_FLOOR_ANIMATION_STATECOUNT);
                setCounter(PlayerStandConst.HIT_FLOOR_ANIMATION_COUNT_END);

                // Block or floor hit
                setySpeed(Y_SPEED_MIDDLE);

                playerCanUpDown = false;
            }
        } else if (this.ySpeed < Y_SPEED_MIDDLE
                && getSubState() >= PlayerStandConst.SUBSTATE_VALUE_TO_FALL) {
            final int previousY = getY();

            playerMove = UtilityObjectEntity.moveObjectUp(this, this.ySpeed
                            + PlayerJumpingConst.JUMP_INCREMENT_VALUE,
                    this.backgroundObject);

            // above -1 cause skip a ySpeed due to previous update
            // Up
            if (!playerMove) {
                // Set 0 only if player have same position (play have been moved
                // but not totaly).
                if (previousY == getY()) {
                    setySpeed(Y_SPEED_MIDDLE);
                    playerCanUpDown = false;
                }
            }
        } else {
            playerMove = false;
        }

        // Check climb
        if (playerMove && UtilityObjectEntity.isClimbing(this,
                this.backgroundObject)) {
            changeToClimbState();
        }

        // Player fall but with speed limit
        if (playerCanUpDown
                && getSubState() >= PlayerStandConst.SUBSTATE_VALUE_TO_FALL
                && this.ySpeed < PlayerJumpingConst.JUMP_FALLING_SPEED_LIMIT) {
            this.ySpeed += PlayerJumpingConst.JUMP_INCREMENT_VALUE;
        }
    }

    /**
     * Move in jumping.
     *
     * @param keyboardLayout keyboard
     */
    private void moveStdPlayerLeftRightJumping(
            final KeyboardLayout keyboardLayout) {
        // Can't move in jump animation
        if (getSubState() > PlayerStandConst.SUBSTATE_VALUE_TO_FALL) {
            if (keyboardLayout.isLeft()) {
                moveStdPlayerLeftRightJumpingCommon(X_SPEED_LEFT);
            } else if (keyboardLayout.isRight()) {
                moveStdPlayerLeftRightJumpingCommon(X_SPEED_RIGHT);
            } else {
                // When jump and no key press, return to middle
                setxSpeed(X_SPEED_MIDDLE);
            }
        }
    }

    /**
     * Common move left/right.
     *
     * @param playerWay player way.
     */
    private void moveStdPlayerLeftRightJumpingCommon(final int playerWay) {
        if (getxSpeed() == playerWay) {
            // We already turn, now move
            if (playerWay == X_SPEED_RIGHT) {
                UtilityObjectEntity.moveObjectRight(this,
                        PlayerStandConst.PLAYER_MOVE_SIZE,
                        this.backgroundObject);
            } else {
                UtilityObjectEntity.moveObjectLeft(this,
                        -PlayerStandConst.PLAYER_MOVE_SIZE,
                        this.backgroundObject);
            }

            setxSpeed(playerWay);

            // Check climb
            if (UtilityObjectEntity.isClimbing(this,
                    this.backgroundObject)) {
                changeToClimbState();
            }
        } else if (getxSpeed() == X_SPEED_MIDDLE) {
            // Just turn picture
            setxSpeed(playerWay);
            setInfo1(playerWay);
        } else {
            setxSpeed(X_SPEED_MIDDLE);
            setInfo1(X_SPEED_MIDDLE);
        }
    }

    /**
     * Move in jumping.
     *
     * @param keyboardLayout keyboard
     */
    private void moveStdPlayerLeftRightStand(
            final KeyboardLayout keyboardLayout) {
        if (keyboardLayout.isLeft()) {
            moveStdPlayerLeftRightStandCommon(X_SPEED_LEFT);
        } else if (keyboardLayout.isRight()) {
            moveStdPlayerLeftRightStandCommon(X_SPEED_RIGHT);
        } else {
            setxSpeed(X_SPEED_MIDDLE);
        }
    }

    /**
     * Common move left/right.
     *
     * @param playerWay player way.
     */
    private void moveStdPlayerLeftRightStandCommon(final int playerWay) {
        if (getInfo1() != playerWay) {
            setInfo1(playerWay);
            setxSpeed(X_SPEED_MIDDLE);
        } else if (getxSpeed() == playerWay) {
            if (this.subState
                    >= PlayerStandConst.PICTURE_RUNNING_NUMBER - 1) {
                this.subState = 0;
            } else {
                this.subState++;
            }

            // Move
            if (playerWay < X_SPEED_MIDDLE) {
                UtilityObjectEntity.moveObjectLeft(this,
                        -PlayerStandConst.PLAYER_MOVE_SIZE,
                        this.backgroundObject);
            } else {
                UtilityObjectEntity.moveObjectRight(this,
                        PlayerStandConst.PLAYER_MOVE_SIZE,
                        this.backgroundObject);
            }
        } else {
            // Set up direction
            setxSpeed(playerWay);
            // Reinit index of image
            setSubState(0);
        }

        // Reinit statecount for diseable wait animation
        setStateCount(0);
    }

    /**
     * Change state to climb state.
     */
    private void changeToClimbState() {
        int newSubState;
        int indexMove;

        if (getState() == PlayerState.JUMPING) {
            newSubState = PlayerClimbConst.SUBSTATE_JUMP_UP;
            indexMove = PlayerClimbConst.SUBSTATE_JUMP_STOP;
        } else {
            indexMove = PlayerClimbConst.SUBSTATE_TO_CLIMB_MOVE;
            newSubState = PlayerClimbConst.SUBSTATE_JUMP_UP;
        }

        setY(getY() + PlayerClimbConst.PLAYER_MOVE_SIZE_CLIMB_UP[indexMove]);
        setSubState(newSubState);
        setState(PlayerState.CLIMBING);
    }

    /**
     * Return background.
     *
     * @return background
     */
    @Override
    protected final BackgroundEntity[][] getBackgroundObject() {
        return backgroundObject;
    }

    /**
     * Move player in jumping state.
     *
     * @param keyboardLayout keyboard.
     */
    private void moveStdPlayerUpDownClimbing(
            final KeyboardLayout keyboardLayout) {
        boolean playerMove;

        if (keyboardLayout.isUp()) {
            int lSubState = getSubState();

            lSubState++;

            if (lSubState
                    >= PlayerClimbConst.PLAYER_MOVE_SIZE_CLIMB_UP.length) {
                lSubState = 1;
            }

            setSubState(lSubState);

            playerMove = UtilityObjectEntity.moveObjectUp(this,
                    PlayerClimbConst.PLAYER_MOVE_SIZE_CLIMB_UP[lSubState],
                    this.backgroundObject);
        } else if (keyboardLayout.isDown()) {
            setSubState(PlayerClimbConst.PLAYER_SUBSTATE_DOWN);

            playerMove = UtilityObjectEntity.moveObjectDown(this,
                    PlayerClimbConst.PLAYER_MOVE_SIZE_CLIMB_DOWN,
                    this.backgroundObject);
        } else {
            playerMove = false;
        }

        // Check if climb, if not, go to stand state
        if (playerMove && !UtilityObjectEntity.isClimbing(this,
                this.backgroundObject)) {
            // Goto stand
            setState(PlayerState.STAND);
        }
    }

    /**
     * Move left/right when climb.
     *
     * @param keyboardLayout keyboard
     */
    private void moveStdPlayerLeftRightClimbing(
            final KeyboardLayout keyboardLayout) {
        boolean playerMove;

        int xd = getxSpeed();

        if (keyboardLayout.isLeft() && xd >= X_SPEED_MIDDLE) {
            playerMove = UtilityObjectEntity.moveObjectLeft(this,
                    -PlayerStandConst.PLAYER_MOVE_SIZE, this.backgroundObject);
        } else if (keyboardLayout.isRight() && xd <= X_SPEED_MIDDLE) {
            playerMove = UtilityObjectEntity.moveObjectRight(this,
                    PlayerStandConst.PLAYER_MOVE_SIZE, this.backgroundObject);
        } else if (xd != X_SPEED_MIDDLE
                && !(keyboardLayout.isRight() || keyboardLayout.isLeft())) {
            playerMove = false;

            setxSpeed(X_SPEED_MIDDLE);
            setSubState(PlayerClimbConst.SUBSTATE_JUMP_STOP);
        } else {
            playerMove = false;
        }

        if (playerMove) {
            if (keyboardLayout.isJump()) {
                changeToJumpState();
            } else {
                setState(PlayerState.STAND);
            }
        }
    }


    /**
     * Add piece of land a end of picture.
     *
     * @param image    picture picture source
     * @param imagePol picture to write (piece of land)
     * @return new merge picture
     */
    protected BufferedImage addPieceOfLand(final BufferedImage image,
            final BufferedImage imagePol) {
        final BufferedImage newImage = new BufferedImage(image.getWidth(),
                image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        final Graphics2D g2 = newImage.createGraphics();

        g2.drawImage(image, null, 0, 0);

        g2.drawImage(imagePol, null, 0,
                image.getHeight() - imagePol.getHeight());

        g2.dispose();

        return newImage;
    }

    /**
     * Draw piece of land on all image.
     *
     * @param pictureSrc source picture
     */
    protected void drawPieceOfLand(final BufferedImage[][] pictureSrc) {
        final BufferedImage[] pieceOfLand = new BufferedImage[
                PlayerPieceOfLandConst.PICTURE_NUMBER];

        for (int index = 0; index < pieceOfLand.length; index++) {
            pieceOfLand[index] = pictureCache.getImage(
                    PlayerPieceOfLandConst.TILESET_INDEX,
                    PlayerPieceOfLandConst.TILE_INDEX + index);
        }

        for (BufferedImage[] pictureSrc1 : pictureSrc) {
            for (int indexImg = 0; indexImg < pieceOfLand.length; indexImg++) {
                pictureSrc1[indexImg + 1] =
                        addPieceOfLand(pictureSrc1[indexImg + 1],
                                pieceOfLand[indexImg]);
            }
        }
    }
}
