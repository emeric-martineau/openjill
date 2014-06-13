package org.jill.openjill.core.api.message.statusbar;

/**
 * Class permit display text in bottom of statusbar.
 *
 * @author Emeric MARTINEAU
 */
public final class StatusBarTextMessage {
    /**
     * Message to display.
     */
    private final String message;

    /**
     * Time to show massage.
     */
    private final int duration;

    /**
     * Time to show massage.
     */
    private final int color;

    /**
     * Send a message.
     *
     * @param msg message to display
     * @param time time to be display
     * @param textColor text color
     */
    public StatusBarTextMessage(final String msg, final int time,
            final int textColor) {
        this.message = msg;
        this.duration = time;
        this.color = textColor;
    }

    /**
     * Message.
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Time.
     *
     * @return time
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Color.
     *
     * @return color
     */
    public int getColor() {
        return color;
    }
}
