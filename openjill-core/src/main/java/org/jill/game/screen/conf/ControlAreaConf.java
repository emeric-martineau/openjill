package org.jill.game.screen.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Config class to control area.
 *
 * @author Emeric MARINEAU
 */
public final class ControlAreaConf extends AbstractLineTextConf {

    /**
     * Special key.
     */
    private List<TextToDraw> specialKey;

    /**
     * Turtle bullet.
     */
    private TextToDraw turtleBullet;

    /**
     * NoiseBullet.
     */
    private TextToDraw noiseBullet;

    /**
     * Alt key text by inventory.
     */
    private Map<String, KeysControlText> keysControlText;

    /**
     * Special key.
     *
     * @return special key
     */
    public List<TextToDraw> getSpecialKey() {
        return this.specialKey;
    }

    /**
     * Special key.
     *
     * @param sk special key
     */
    public void setSpecialKey(final List<TextToDraw> sk) {
        this.specialKey = sk;
    }

    /**
     * Turtle bullet.
     *
     * @return bullet
     */
    public TextToDraw getTurtleBullet() {
        return this.turtleBullet;
    }

    /**
     * Turtle bullet.
     *
     * @param tb bullet positon
     */
    public void setTurtleBullet(final TextToDraw tb) {
        this.turtleBullet = tb;
    }

    /**
     * Noise bullet.
     *
     * @return bullet
     */
    public TextToDraw getNoiseBullet() {
        return this.noiseBullet;
    }

    /**
     * Noise bullet.
     *
     * @param nb bullet positon
     */
    public void setNoiseBullet(final TextToDraw nb) {
        this.noiseBullet = nb;
    }

    /**
     * Alt text.
     *
     * @param key key to find
     *
     * @return alt text
     */
    public KeysControlText getKeysControlText(final String key) {
        return this.keysControlText.get(key);
    }

    /**
     * Alt text.
     *
     * @return alt text
     */
    public List<KeysControlText> getKeysControlText() {
        List<KeysControlText> types = new ArrayList<>(
                this.keysControlText.size());

        for (Entry<String, KeysControlText> entry
                : this.keysControlText.entrySet()) {
            types.add(entry.getValue());
        }

        return types;
    }

    /**
     * Alt text.
     *
     * @param altText alt text
     */
    public void setKeysControlText(final Map<String, KeysControlText> altText) {
        this.keysControlText = altText;
    }
}
