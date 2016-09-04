package org.jill.game.screen.conf;

import java.util.List;
import java.util.Map;

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
    private Map<String, String> altKeyText;

    /**
     * Ctrl key text by inventory.
     */
    private Map<String, String> ctrlKeyText;

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
     * @return alt text
     */
    public Map<String, String> getAltKeyText() {
        return this.altKeyText;
    }

    /**
     * Alt text.
     *
     * @param altText alt text
     */
    public void setAltKeyText(final Map<String, String> altText) {
        this.altKeyText = altText;
    }

    /**
     * Ctrl text.
     *
     * @return alt text
     */
    public Map<String, String> getCtrlKeyText() {
        return this.ctrlKeyText;
    }

    /**
     * Ctrl text.
     *
     * @param ctrlText alt text
     */
    public void setCtrlKeyText(final Map<String, String> ctrlText) {
        this.ctrlKeyText = ctrlText;
    }
}
