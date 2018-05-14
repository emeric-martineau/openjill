package org.jill.game.entities.obj;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Optional;

import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.InterfaceMessageGameHandler;

/**
 * Skull object.
 *
 * @author Emeric MARTINEAU
 */
public final class SkullManager extends AbstractParameterObjectEntity
        implements InterfaceMessageGameHandler {
    /**
     * Picture array.
     */
    private Optional<BufferedImage>[] images;

    /**
     * Picture array.
     */
    private Optional<BufferedImage> fixedImages;

    /**
     * Create copy of picture.
     *
     * @param currentSkull current picture
     * @return new pricture
     */
    private static BufferedImage copyPicture(final BufferedImage currentSkull) {
        final BufferedImage newPicture = new BufferedImage(
                currentSkull.getWidth(),
                currentSkull.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        drawFromImage(newPicture, currentSkull, 0, 0);

        return newPicture;
    }

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        int tileIndex = getConfInteger("fixedTile");
        int tileSetIndex = getConfInteger("fixedTileSet");

        this.fixedImages = this.pictureCache.getImage(tileSetIndex, tileIndex);

        tileIndex = getConfInteger("tile");
        tileSetIndex = getConfInteger("tileSet");

        int numberTileSet = getConfInteger("numberTileSet");
        int skullMax = getConfInteger("skullMax");

        loadSkullImage(numberTileSet, tileSetIndex, tileIndex, skullMax);

        drawEye(tileSetIndex, "eyeMinTile", "eyeMaxTile", "eyeLeftStart",
                "eyeLeftX", "eyeLeftY");
        drawEye(tileSetIndex, "eyeMinTile", "eyeMaxTile", "eyeRightStart",
                "eyeRightX", "eyeRightY");

        messageDispatcher.addHandler(EnumMessageType.TRIGGER, this);
    }

    private void drawEye(final int tileSetIndex, final String eyeMinTileStr,
            final String eyeMaxTileStr, final String eyeStartStr,
            final String eyeXstr, final String eyeYstr) {
        int eyeMinTile = getConfInteger(eyeMinTileStr);
        int eyeMaxTile = getConfInteger(eyeMaxTileStr);
        BufferedImage eye;
        int tileIncrement;

        int tileEye = getConfInteger(eyeStartStr);
        int eyeLeftX = getConfInteger(eyeXstr);
        int eyeLeftY = getConfInteger(eyeYstr);

        tileIncrement = 1;

        for (Optional<BufferedImage> image : this.images) {
            eye = this.pictureCache.getImage(tileSetIndex, tileEye).get();

            // Draw eye
            drawFromImage(image.get(), eye, eyeLeftX, eyeLeftY);

            if ((tileEye == eyeMaxTile && tileIncrement > 0)
                    || (tileEye == eyeMinTile && tileIncrement < 0)) {
                tileIncrement *= -1;
            }

            tileEye += tileIncrement;
        }

    }

    /**
     * Load skull image.
     *
     * @param numberTileSet number of image
     * @param tileSetIndex  tile set
     * @param tileIndex     tile to start
     * @param skullMax      skullMax
     */
    private void loadSkullImage(final int numberTileSet, final int tileSetIndex,
            final int tileIndex, final int skullMax) {
        this.images
                = new Optional[numberTileSet * 2];

        int tileIncrement = 1;
        int tileSkull = 0;

        BufferedImage currentSkull;

        for (int index = 0; index < this.images.length; index += 2) {
            currentSkull = this.pictureCache.getImage(tileSetIndex,
                    tileIndex + tileSkull).get();

            this.images[index] = Optional.of(copyPicture(currentSkull));

            this.images[index + 1] = Optional.of(copyPicture(currentSkull));

            if (tileSkull == skullMax) {
                tileIncrement *= -1;
            }

            tileSkull += tileIncrement;
        }
    }

    @Override
    public Optional<BufferedImage> msgDraw() {
        Optional<BufferedImage> img;

        if (getState() == 0) {
            img = this.fixedImages;
        } else {
            img = this.images[getStateCount()];
        }

        return img;
    }

    /**
     * Call to update.
     */
    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        if (getState() != 0) {
            setStateCount(getStateCount() + 1);

            if (getStateCount() >= this.images.length) {
                setStateCount(0);
            }
        }
    }

    @Override
    public void recieveMessage(EnumMessageType type, Object msg) {
        switch (type) {
            case TRIGGER:
                if (getySpeed() == 0) {
                    final ObjectEntity switchObj = (ObjectEntity) msg;
                    if (switchObj.getCounter() == this.counter) {
                        // Start animation
                        this.setState(1);
                    }
                }
                break;
            default:
        }
    }
}
