package org.jill.openjill.core.api.message;

/**
 * Enum of message.
 *
 * @author Emeric MARTINEAU
 */
public enum EnumMessageType {

    /**
     * Move player.
     */
    PLAYER_MOVE,

    /**
     * Get player position.
     */
    PLAYER_GET_POSITION,

    /**
     * Add item in inventory.
     */
    INVENTORY_ITEM,
    /**
     * To change life value.
     */
    INVENTORY_LIFE,

    /**
     * Add point in score.
     */
    INVENTORY_POINT,

    /**
     * To display message in status bar.
     */
    MESSAGE_STATUS_BAR,

    /**
     * To add/remove object.
     */
    OBJECT,

    /**
     * Replace oject.
     */
    REPLACE_OBJECT,

    /**
     * To create object.
     */
    CREATE_OBJECT,

    /**
     * For object trigger (switch, wall...).
     */
    TRIGGER,

    /**
     * To change background.
     */
    BACKGROUND,

    /**
     * To change level.
     */
    CHECK_POINT_CHANGING_LEVEL,

    /**
     * Restore map level.
     */
    CHECK_POINT_CHANGING_LEVEL_PREVIOUS,

    /**
     * Restart level after player death.
     */
    DIE_RESTART_LEVEL,

    /**
     * Display message in box on screen.
     */
    MESSAGE_BOX,

    /**
     * Change player form.
     */
    CHANGE_PLAYER_CHARACTER
}
