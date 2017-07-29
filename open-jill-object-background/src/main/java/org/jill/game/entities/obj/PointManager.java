package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;

import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.ObjectListMessage;

/**
 * Display point.
 *
 * @author Emeric MARTINEAU
 */
public class PointManager extends AbstractParameterObjectEntity {
    /**
     * Mask for color.
     */
    private static final int MASK = 3;

    /**
     * Text manager to draw text.
     */
    private TextManager textManager;

    /**
     * xd value when create.
     */
    private int xdValue;

    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        setRemoveOutOfVisibleScreen(true);

        this.textManager = objectParam.getPictureCache().getTextManager();

        setCounter(getConfInteger("counter"));

        setySpeed(getConfInteger("yd"));

        this.xdValue = getConfInteger("xd");
    }

    @Override
    public void msgUpdate(KeyboardLayout keyboardLayout) {
        setCounter(getCounter() - 1);

        if (getCounter() <= 0) {
            // Delete this object
            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                    new ObjectListMessage(this, false));
        }

        setX(getX() + getxSpeed());
        setY(getY() + getySpeed());

        if (getxSpeed() > 0) {
            setxSpeed(getxSpeed() - 1);
        }

        setySpeed(getySpeed() - 1);
    }

    @Override
    public BufferedImage msgDraw() {
        int color = (getCounter() & MASK) + 1;

        return this.textManager.createSmallNumber(getState(), color,
                TextManager.BACKGROUND_COLOR_NONE);
    }

    @Override
    public void setState(final int st) {
        super.setState(st);

        // Create image to get width/height
        BufferedImage bf = this.textManager.createSmallNumber(getState(), 0,
                TextManager.BACKGROUND_COLOR_NONE);

        setWidth(bf.getWidth());
        setHeight(bf.getHeight());
    }

    @Override
    public void setxSpeed(final int xd) {
        if (xd < X_SPEED_MIDDLE) {
            super.setxSpeed(-this.xdValue);
        } else if (xd > X_SPEED_MIDDLE) {
            super.setxSpeed(this.xdValue);
        }
    }
}
