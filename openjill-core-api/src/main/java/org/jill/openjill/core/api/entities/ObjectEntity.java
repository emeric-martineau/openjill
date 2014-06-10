package org.jill.openjill.core.api.entities;

import java.awt.image.BufferedImage;
import org.jill.jn.ObjectItem;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;

/**
 * Object entity interface.
 *
 * @author Emeric MARTINEAU
 */
public interface ObjectEntity extends ObjectItem {
    /**
     * Value to left of speed x.
     */
    int X_SPEED_LEFT = -1;

    /**
     * Value to middle of speed x.
     */
    int X_SPEED_MIDDLE = 0;

    /**
     * Value to right of speed x.
     */
    int X_SPEED_RIGHT = 1;

    /**
     * Value to down of speed y.
     */
    int Y_SPEED_DOWN = 1;

    /**
     * Value to middle of speed y.
     */
    int Y_SPEED_MIDDLE = 0;

    /**
     * Value to up of speed y.
     */
    int Y_SPEED_UP = -1;

    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    void init(ObjectParam objectParam);

    /**
     * if olways on screen.
     *
     * @return alwaysOnScreen
     */
    boolean isAlwaysOnScreen();

    /**
     * To know if object is checkpoint.
     * @return if is chack point
     */
    boolean isCheckPoint();

    /**
     * If object is killable.
     *
     * @return true/false
     */
    boolean isKillableObject();

    /**
     * If player object.
     *
     * @return  true/false
     */
    boolean isPlayer();

    /**
     * Write on background.
     *
     * @return writeOnBackGround
     */
    boolean isWriteOnBackGround();

    /**
     * Return graphic to draw.
     *
     * @return picture
     */
    BufferedImage msgDraw();

    /**
     * Call when collision with player to grap keyboard key (e.g. for lift).
     *
     * @param obj object
     * @param keyboardLayout keyboard object
     */
    void msgKeyboard(ObjectEntity obj, KeyboardLayout keyboardLayout);

    /**
     * When weapon kill object or ennemy kill player.
     *
     * @param sender sender
     * @param nbLife number life to decrease
     * @param typeOfDeath tyep of death (for player)
     */
    void msgKill(ObjectEntity sender, int nbLife, int typeOfDeath);

    /**
     * When background kill object.
     *
     * @param sender sender
     * @param nbLife number life to decrease
     * @param typeOfDeath tyep of death (for player)
     */
    void msgKill(BackgroundEntity sender, int nbLife, int typeOfDeath);

    /**
     * Call when player touche object.
     *
     * @param obj object
     */
    void msgTouch(ObjectEntity obj);

    /**
     * Call to update.
     */
    void msgUpdate();
}
