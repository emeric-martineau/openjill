package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.game.entities.obj.player.PlayerPositionSynchronizer;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.CreateObjectMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryPointMessage;

/**
 * Hive object.
 *
 * TODO when die, add bullet (object 36)
 *
 * @author Emeric MARTINEAU
 */
public final class HiveManager extends AbstractParameterObjectEntity {

    /**
     * Player position object.
     */
    private static final PlayerPositionSynchronizer PLAYER_POSITION
        = PlayerPositionSynchronizer.getInstance();

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
     * Counter value to create bees.
     */
    private int counterCreateBees;

    /**
     * Maximum random value.
     */
    private int maxRandomValue;

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        setKillabgeObject(true);

        this.counterCreateBees = getConfInteger("counterCreateBees");
        this.maxRandomValue = getConfInteger("maxRandomValue");

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

        // Remove me from list of object (= kill me)
        this.killme = new ObjectListMessage(this, false);
    }

    /**
     * Create object.
     */
    private void createBeesObject() {
        final CreateObjectMessage com = new CreateObjectMessage(
                getConfInteger("beesObject"));

        this.messageDispatcher.sendMessage(EnumMessageType.CREATE_OBJECT,
            com);

        ObjectEntity bees = com.getObject();
        bees.setY(getY());

        if (getxSpeed() >= X_SPEED_MIDDLE) {
            bees.setX(getX() + (getWidth() / 2));
        } else {
            bees.setX(getX() - (getWidth() / 2));
        }

        this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                new ObjectListMessage(bees, true));
    }

    @Override
    public BufferedImage msgDraw() {
        int pictureIndex;

        pictureIndex = getCounter();

        if (getCounter() == this.counterCreateBees
                && getxSpeed() > X_SPEED_MIDDLE) {
            pictureIndex++;
        }

        return this.images[pictureIndex];
    }

    /**
     * Call to update.
     */
    @Override
    public void msgUpdate() {

        if (getCounter() == 0) {
            // Random
            int a = (int) (Math.random() * this.maxRandomValue);

            if (a == 0) {
                setCounter(1);
            }
        } else {
            setCounter(getCounter() + 1);

            if (getCounter() > this.counterCreateBees) {
                // Add bees
                setCounter(0);

                createBeesObject();
            }
        }

        this.indexEtat = PLAYER_POSITION.updatePlayerPosition(
            this.messageDispatcher, this.indexEtat);

        final int xd = PLAYER_POSITION.getX() - this.x;
        final int yd = PLAYER_POSITION.getY() - this.y;

        int newXd;
        int newYd;

        if (xd != 0) {
            newXd = xd / Math.abs(xd);
        } else {
            newXd = getxSpeed();
        }

        if (yd != 0) {
            newYd = yd / Math.abs(yd);
        } else {
            newYd = getySpeed();
        }

        setxSpeed(newXd);
        setySpeed(newYd);
    }

    @Override
    public void msgKill(final ObjectEntity sender,
        final int nbLife, final int typeOfDeath) {
        this.messageDispatcher.sendMessage(EnumMessageType.INVENTORY_POINT,
            new InventoryPointMessage(getConfInteger("point"), true,
                    this, sender));
        this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
            this.killme);
    }
}
