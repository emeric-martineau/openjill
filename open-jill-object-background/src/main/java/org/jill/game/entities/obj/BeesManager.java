package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.jill.game.entities.obj.abs.AbstractHitPlayerObjectEntity;
import org.jill.game.entities.obj.bees.MoveSizeAndInterval;
import org.jill.game.entities.obj.player.PlayerPositionSynchronizer;
import org.jill.game.entities.obj.util.UtilityObjectEntity;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.ObjectListMessage;

/**
 * Bees object.
 *
 * @author Emeric MARTINEAU
 */
public final class BeesManager extends AbstractHitPlayerObjectEntity {

    /**
     * Player position object.
     */
    private static final PlayerPositionSynchronizer PLAYER_POSITION
        = PlayerPositionSynchronizer.getInstance();

    /**
     * Offset to calculate from start.
     */
    private static final int START_OFFSET = 7;

    /**
     * Size of movement in cycle.
     */
    private static final int SIZE_OF_MVT = 32;

    /**
     * To remove this object from object lis.
     */
    private ObjectListMessage killme;

    /**
     * To get player position.
     */
    private int indexEtat = 0;

    /**
     * Picture array.
     */
    private BufferedImage[] images;

    /**
     * Index in picture array for right picture.
     */
    private int indexRight;

    /**
     * Move X size.
     */
    private List<MoveSizeAndInterval> moveX;

    /**
     * Move Y size.
     */
    private List<MoveSizeAndInterval> moveY;

    /**
     * Value of state die.
     */
    private int stateDie;

    /**
     * Background map.
     */
    private BackgroundEntity[][] backgroundObject;

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        setKillabgeObject(false);

        int tileIndex = getConfInteger("tile");
        int tileSetIndex = getConfInteger("tileSet");

        int numberTileSet = getConfInteger("numberTileSet");

        // Load picture for each object. Don't use cache cause some picture
        // change between jill episod.
        this.images
            = new BufferedImage[numberTileSet];

        for (int index = 0; index < numberTileSet; index++) {
            this.images[index]
                = this.pictureCache.getImage(tileSetIndex, tileIndex
                    + index);
        }

        setWidth(this.images[0].getWidth());
        setHeight(this.images[0].getHeight());

        this.indexRight = numberTileSet / 2;

        // Remove me from list of object (= kill me)
        this.killme = new ObjectListMessage(this, false);

        this.stateDie = getConfInteger("stateDie");

        this.backgroundObject = objectParam.getBackgroundObject();

        this.moveX = populateMove("moveX");
        this.moveY = populateMove("moveY");
    }

    @Override
    public BufferedImage msgDraw() {
        this.indexEtat = PLAYER_POSITION.updatePlayerPosition(
            this.messageDispatcher, this.indexEtat);

        final int xd = PLAYER_POSITION.getX() - getX();
        BufferedImage bf;

        if (xd >= X_SPEED_MIDDLE) {
            bf = this.images[this.counter + this.indexRight];
        } else {
            bf = this.images[this.counter];
        }

        return bf;
    }

    /**
     * Call to update.
     */
    @Override
    public void msgUpdate() {
        int newState = getState() + 1;

        if (newState == this.stateDie) {
            // Die
            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                this.killme);
        } else {
            setState(newState);

            updateCounter();

            int c = calculateCvalue(newState);

            this.indexEtat = PLAYER_POSITION.updatePlayerPosition(
                this.messageDispatcher, this.indexEtat);

            final int xdSign = PLAYER_POSITION.getX() - getX();
            final int ydSign = PLAYER_POSITION.getY() - getY();

            moveBeesOnX(c, xdSign);

            moveBeesOnY(c, ydSign);
        }
    }

    /**
     * Move bees on X.
     *
     * @param c current state value
     * @param xdSign difference between player.x and bees.x (if 0 do nothing).
     *      Use to know direction to move.
     */
    private void moveBeesOnX(final int c, final int xdSign) {
        int xd = getxSpeed();

        if (xd < X_SPEED_MIDDLE) {
            UtilityObjectEntity.moveObjectLeft(this, xd,
                    this.backgroundObject);
        } else {
            UtilityObjectEntity.moveObjectRight(this, xd,
                    this.backgroundObject);
        }

        int newXD;

        if (xdSign != X_SPEED_MIDDLE) {
            newXD = moveXorY(c, this.moveX);

            // Change way
            if (xdSign < X_SPEED_MIDDLE) {
                newXD *= -1;
            }
        } else {
            newXD = 0;
        }

        setxSpeed(newXD);
    }

    /**
     * Caluculate value to get move size.
     *
     * @param newState state of object
     *
     * @return c value
     */
    private int calculateCvalue(final int newState) {
        int a = (int) ((newState + START_OFFSET) / SIZE_OF_MVT);
        int b = a * SIZE_OF_MVT;
        int c = (newState + START_OFFSET) - b;
        return c;
    }

    /**
     * Update counter value.
     */
    private void updateCounter() {
        int newCounter = getCounter() + 1;

        if (newCounter >= this.indexRight) {
            newCounter = 0;
        }

        setCounter(newCounter);
    }

    /**
     * Get speed X.
     *
     * @param c state value
     * @param moveXY movement list
     *
     * @return size of move
     */
    private int moveXorY(final int c, final List<MoveSizeAndInterval> moveXY) {
        int xd = 0;

        for (MoveSizeAndInterval currentMoveX : moveXY) {
            if (c < currentMoveX.getBound()) {
                xd = currentMoveX.get();
                break;
            }
        }

        return xd;
    }

    /**
     * Move bees on Y.
     *
     * @param c current state value
     * @param ydSign difference between player.y and bees.y (if 0 do nothing).
     *      Use to know direction to move.
     */
    private void moveBeesOnY(final int c, final int ydSign) {
        int yd = getySpeed();

        if (yd < Y_SPEED_MIDDLE) {
            UtilityObjectEntity.moveObjectUp(this, yd,
                    this.backgroundObject);
        } else {
            UtilityObjectEntity.moveObjectDownWithIgnoreStair(this, yd,
                    this.backgroundObject);
        }

        int newYD;

        newYD = moveXorY(c, this.moveY);

        if (ydSign == Y_SPEED_MIDDLE) {
            // When bees are on same Y that player. Sign change +/-
            if (getySpeed() > Y_SPEED_MIDDLE) {
                newYD *= -1;
            }
        } else if (ydSign < Y_SPEED_MIDDLE) {
            newYD *= -1;
        }

        setySpeed(newYD);
    }

    /**
     * Populate list of movement.
     *
     * @param keyName name of key in config file
     *
     * @return list of movement
     */
    private List<MoveSizeAndInterval> populateMove(final String keyName) {
       // Read moveX
        String mvtX = getConfString(keyName);
        // Split #
        String[] arrayMoveX = mvtX.split("#");

        final List<MoveSizeAndInterval> listMvt =
                new ArrayList<>(arrayMoveX.length);

        for (String currentMove : arrayMoveX) {
            listMvt.add(new MoveSizeAndInterval(currentMove));
        }

        return listMvt;
    }

    @Override
    public void msgTouch(final ObjectEntity obj) {
        if (obj.isPlayer()) {
            hitPlayer(obj);
        }
    }
}
