package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.game.entities.obj.util.UtilityObjectEntity;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;

/**
 * Apple object.
 *
 * @author Emeric MARTINEAU
 */
public final class BladeManager extends AbstractParameterObjectEntity {
    /**
     * To remove this object from object lis.
     */
    private ObjectListMessage killme;

    /**
     * Picture array.
     */
    private BufferedImage[] images;
    
    /**
     * SubState value to remove blade.
     */
    private int subStateToRemoveMe;
    
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

        int tileIndex = getConfInteger("tile");
        int tileSetIndex = getConfInteger("tileSet");

        int numberTileSet = getConfInteger("numberTileSet");
        
        this.subStateToRemoveMe = getConfInteger("subStateToRemoveMe");

        // Load picture for each object. Don't use cache cause some picture
        // change between jill episod.
        this.images
            = new BufferedImage[numberTileSet];

        for (int index = 0; index < numberTileSet; index++) {
            this.images[index]
                = this.pictureCache.getImage(tileSetIndex, tileIndex
                    + index);
        }

        this.backgroundObject = objectParam.getBackgroundObject();
        
        // Remove me from list of object (= kill me)
        this.killme = new ObjectListMessage(this, false);
        
        setRemoveOutOfVisibleScreen(true);
    }

    
    @Override
    public void msgTouch(final ObjectEntity obj,
            final KeyboardLayout keyboardLayout) {
        if (obj.isPlayer()) {
            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT, killme);
        } else if (obj.isKillableObject()) {
            obj.msgKill(this, 0, 0);
        }
    }

    @Override
    public BufferedImage msgDraw() {
        return this.images[this.counter];
    }

    /**
     * Call to update.
     */
    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        setCounter(getCounter() - 1);
        setSubState(getSubState() + 1);

        if (getCounter() == -1) {
            setCounter(this.images.length - 1);
        }
     
        // Remove blade
        if (getSubState() >= this.subStateToRemoveMe) {
            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT, killme);
        }
        
        moveLeftRight();
                
        moveUpDown();
    }

    /**
     * Move blade up or down.
     */
    private void moveUpDown() {
        // If blade can't move fully, way not change.
        // Way change only when blade don't move.
        setySpeed(getySpeed() + 1);
        
        // Move blade
        if (getySpeed() > Y_SPEED_MIDDLE) {
            // Move down
            if (!UtilityObjectEntity.moveObjectDown(this, getySpeed(),
                    backgroundObject)) {
                setySpeed(getySpeed() * -1);
            }
        } else {
            // Move up
            if (!UtilityObjectEntity.moveObjectUp(this, getySpeed(),
                    backgroundObject)) {
                setySpeed(getySpeed() * -1);
            }
        }
    }

    /**
     * Move blade left or right.
     */
    private void moveLeftRight() {
        // If blade can't move fully, way not change.
        // Way change only when blade don't move.
        if ((getxSpeed() > X_SPEED_MIDDLE
                && !UtilityObjectEntity.moveObjectRight(this, getxSpeed(),
                    backgroundObject))
                || (getxSpeed() < X_SPEED_MIDDLE
                && !UtilityObjectEntity.moveObjectLeft(this, getxSpeed(),
                        backgroundObject))) {
                setxSpeed(getxSpeed() * -1);
        }
    }
}
