package org.jill.game.entities.obj;

import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.background.BackgroundMessage;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.jill.JillConst;
import org.jill.openjill.core.api.message.InterfaceMessageGameHandler;
import org.jill.openjill.core.api.message.object.ObjectListMessage;

/**
 * Wall.
 *
 * @author Emeric MARTINEAU
 */
public final class ToggleWallManager extends AbstractParameterObjectEntity
    implements InterfaceMessageGameHandler {

    /**
     * Background to create wall/floor.
     */
    private List<BackgroundMessage> listBackgroundBlock;

    /**
     * Background to replace.
     */
    private List<BackgroundMessage> listBackgroundPassthru;

    /**
     * X position to get unwall background.
     */
    private int xBlock;

    /**
     * Y position to get unwall background.
     */
    private int yBlock;

    /**
     * Background.
     */
    private BackgroundEntity[][] background;

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @SuppressWarnings("LeakingThisInConstructor")
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        this.messageDispatcher.addHandler(EnumMessageType.TRIGGER, this);

        this.background = objectParam.getBackgroundObject();

        if (this.ySpeed == 1) {
            initWall();
        } else {
            initFloor();
        }
    }

    /**
     * Init wall config.
     */
    private void initWall() {
        // X col to scan
        this.xBlock = this.x / JillConst.BLOCK_SIZE;
        // Y col to scan
        this.yBlock = this.y / JillConst.BLOCK_SIZE;
        int yScanEnd = this.yBlock;

        // Name of back to be wall. If object is restore (by save game),
        // background not be ELEVBOT.
        final String nameBack = this.background[this.xBlock][
            this.yBlock].getName();

        // Search limit of wall
        for (int indexBack = this.yBlock; indexBack < this.background[0].length;
            indexBack++) {
            if (!nameBack.equals(
                this.background[this.xBlock][indexBack].getName())) {
                // Stop scan
                yScanEnd = indexBack;
                break;
            }
        }

        final String nameOfBackgroundBlock
            = getConfString("wallBackgroundName");

        // Search block to replace.
        // Another wall can be next to this wall
        String nameOfBackgroundReplace = null;
        String tmpName;

        for (int indexBlockX = this.xBlock - 1; indexBlockX > -1;
            indexBlockX--) {
            tmpName = this.background[indexBlockX][this.yBlock].getName();

            if (!tmpName.equals(nameOfBackgroundBlock)) {
                nameOfBackgroundReplace = tmpName;
                break;
            }
        }

        // Create list of point to be change
        this.listBackgroundBlock = new ArrayList<>(yScanEnd - this.yBlock);

        this.listBackgroundPassthru = new ArrayList<>(yScanEnd - this.yBlock);

        for (int indexBack = this.yBlock; indexBack < yScanEnd; indexBack++) {
            this.listBackgroundBlock.add(
                new BackgroundMessage(this.xBlock, indexBack,
                    nameOfBackgroundBlock));
            this.listBackgroundPassthru.add(
                new BackgroundMessage(this.xBlock, indexBack,
                    nameOfBackgroundReplace));
        }
    }

    /**
     * Init floor config.
     */
    private void initFloor() {
        // X col to scan
        this.xBlock = this.x / JillConst.BLOCK_SIZE;
        // Y col to scan
        this.yBlock = this.y / JillConst.BLOCK_SIZE;
        int xScanEnd = this.xBlock;

        // Name of back to be wall. If object is restore (by save game),
        // background not be BRIDGE.
        final String nameBack = this.background[this.xBlock][
            this.yBlock].getName();

        // Search limit of floor
        for (int indexBack = this.xBlock; indexBack < this.background.length;
            indexBack++) {
            if (!nameBack.equals(
                this.background[indexBack][this.yBlock].getName())) {
                // Stop scan
                xScanEnd = indexBack;
                break;
            }
        }

        final String nameOfBackgroundBlock = getConfString(
            "floorBackgroundName");

        // Search block to replace.
        // Another floor can be next to this floor
        String nameOfBackgroundReplace = null;
        String tmpName;

        for (int indexBlockY = this.yBlock; indexBlockY > -1; indexBlockY--) {
            tmpName = this.background[this.xBlock][indexBlockY].getName();

            if (!tmpName.equals(nameOfBackgroundBlock)) {
                nameOfBackgroundReplace = tmpName;
                break;
            }
        }

        // Create list of point to be change
        this.listBackgroundBlock = new ArrayList<>(xScanEnd - this.xBlock);

        this.listBackgroundPassthru = new ArrayList<>(xScanEnd - this.xBlock);

        for (int indexBack = this.xBlock; indexBack < xScanEnd; indexBack++) {
            this.listBackgroundBlock.add(
                new BackgroundMessage(indexBack, this.yBlock,
                    nameOfBackgroundBlock));
            this.listBackgroundPassthru.add(
                new BackgroundMessage(indexBack, this.yBlock,
                    nameOfBackgroundReplace));
        }
    }

    @Override
    public BufferedImage msgDraw() {
        return null;
    }

    /**
     * Manage TAGGLE_WALL message.
     *
     * @param msg message
     */
    private void toggleWallMessage(final Object msg) {
        final ObjectEntity switchObj = (ObjectEntity) msg;

        if (switchObj.getCounter() == this.counter) {
            // If switch.state = 1 wall is off (player can be passthru)
            if (switchObj.getState() == 1) {
                // Clear wall
                this.messageDispatcher.sendMessage(EnumMessageType.BACKGROUND,
                    this.listBackgroundPassthru);
            } else {
                // Set wall
                this.messageDispatcher.sendMessage(EnumMessageType.BACKGROUND,
                    this.listBackgroundBlock);
            }

            // In case of toggle wall, remove source message
            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                    new ObjectListMessage(switchObj, false));
        }
    }

    @Override
    public void recieveMessage(final EnumMessageType type, final Object msg) {
        if (type == EnumMessageType.TRIGGER) {
            toggleWallMessage(msg);
        }
    }
}
