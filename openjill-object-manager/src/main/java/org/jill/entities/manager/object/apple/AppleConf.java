package org.jill.entities.manager.object.apple;

import org.jill.entities.manager.object.common.conf.PictureConf;

public class AppleConf {
    /**
     * Gamecount before change background.
     */
    private int gamecount;

    /**
     * Point when player get apple.
     */
    private int point;

    /**
     * Tileset of picture.
     */
    private PictureConf[] picture;

    /**
     * Number of life point.
     */
    private int life;

    /**
     * Message to display on screen.
     */
    private String msg;

    /**
     * Time to display message.
     */
    private int msgTime;

    /**
     * Message color.
     */
    private int msgColor;

    /**
     * Offset of message box.
     */
    private int boxMsgOffset;

    /**
     * Message mask.
     */
    private int boxMsgMask;

    /**
     * Default index to extract image in jn-extractor.
     */
    private int defaultIndexImage;

    /**
     * Picture.
     *
     * @return all picture conf
     */
    public PictureConf[] getPicture() {
        return picture;
    }

    /**
     * Gamecount before change background.
     *
     * @return number of cycle
     */
    public int getGamecount() {
        return gamecount;
    }

    /**
     * Point when player hit apple.
     *
     * @return nb of point to add
     */
    public int getPoint() {
        return point;
    }

    /**
     * Life to increase.
     *
     * @return nb of life
     */
    public int getLife() {
        return life;
    }

    /**
     * Message to display on screen.
     *
     * @return message
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Time of display message.
     *
     * @return time in ms
     */
    public int getMsgTime() {
        return msgTime;
    }

    /**
     * Message color.
     *
     * @return text color
     */
    public int getMsgColor() {
        return msgColor;
    }

    /**
     * Offset.
     *
     * @return offset of message box.
     */
    public int getBoxMsgOffset() {
        return boxMsgOffset;
    }

    /**
     * Mask.
     *
     * @return message of mask
     */
    public int getBoxMsgMask() {
        return boxMsgMask;
    }

    /**
     * Default index image to return for jn-extractor.
     *
     * @return default index
     */
    public int getDefaultIndexImage() {
        return defaultIndexImage;
    }
}
