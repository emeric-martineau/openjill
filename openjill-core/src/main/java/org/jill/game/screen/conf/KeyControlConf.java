package org.jill.game.screen.conf;

/**
 * Key control in inventory.
 *
 * @author Emeric MARTINEAU
 */
public final class KeyControlConf {
    /**
     * Key.
     */
    private String key;

    /**
     * Text.
     */
    private String text;

    /**
     * Key.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Key.
     *
     * @param k the key to set
     */
    public void setKey(final String k) {
        this.key = k;
    }

    /**
     * Text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Text.
     *
     * @param t the text to set
     */
    public void setText(final String t) {
        this.text = t;
    }


}
