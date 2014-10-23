package org.jill.game.level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.jill.game.gui.InformationBox;
import org.jill.game.gui.menu.MenuInterface;
import org.jill.game.level.cfg.LevelConfiguration;

/**
 * This class manage display menu.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractMenuJillLevel extends AbstractObjectJillLevel {

    /**
     * Info box width.
     */
    private static final int INFO_BOX_WIDTH = 190;

    /**
     * Info box height.
     */
    private static final int INFO_BOX_HEIGHT = 130;

    /**
     * Menu to display.
     */
    protected MenuInterface menu;

    /**
     *
     */
    protected InformationBox infoBox;

    /**
     * Current display screen.
     */
    protected BufferedImage currentDisplayScreen;

    /**
     * Level configuration.
     *
     * @param cfgLevel configuration of level
     *
     * @throws IOException if error of reading file
     * @throws ClassNotFoundException if not class found
     * @throws IllegalAccessException if error
     * @throws InstantiationException if error
     */
    public AbstractMenuJillLevel(final LevelConfiguration cfgLevel)
        throws IOException, IllegalAccessException, InstantiationException,
        ClassNotFoundException {
        super(cfgLevel);

        constructor();
    }

    /**
     * Construct object.
     */
    private void constructor() {
        initMenu();

        this.infoBox = new InformationBox(this.pictureCache);
    }

    @Override
    public void run() {
        if (this.infoBox.isEnable()) {
            if (this.keyboard.isUp()) {
                this.infoBox.up();
            } else if (this.keyboard.isDown()) {
                this.infoBox.down();
            } else if (this.keyboard.isEnter()) {
                this.infoBox.setEnable(false);
            } else if (this.keyboard.isEscape()) {
                this.infoBox.setEnable(false);
            }
        } else if (this.menu.isEnable()) {
            if (this.keyboard.isUp()) {
                this.menu.up();
            } else if (this.keyboard.isDown()) {
                this.menu.down();
            } else if (this.keyboard.isEnter()) {
                // Clear key before switch
                this.keyboard.clear();

                menuEntryValidate(this.menu.getCursorValue());
            } else if (this.keyboard.isEscape()) {
                doEscape();
            } else if (this.keyboard.isOtherKey()) {
                menuOtherKeyHandler(this.keyboard.consumeOtherKey());
            }
        } else if (this.keyboard.isEscape()) {
            doEscape();
        } else {
            doRun();
        }
    }

    /**
     * Excape key pressed with menu.
     */
    protected abstract void doEscape();

    @Override
    public void paint(Graphics g) {
        g.drawImage(this.currentDisplayScreen, 0, 0, null);

        if (this.menu.isEnable()) {
            this.menu.draw(g);
        }

        if (this.infoBox.isEnable()) {
            g.drawImage(this.infoBox.getBox(), this.infoBox.getX(),
                this.infoBox.getY(), null);
        }
    }

    /**
     * Call to run game.
     */
    protected abstract void doRun();

    /**
     * Create menu.
     */
    protected abstract void initMenu();

    /**
     * Call when menu display and user press enter.
     *
     * @param value value
     */
    protected abstract void menuEntryValidate(final int value);

    /**
     * Default handler void.
     */
    protected void menuOtherKeyHandler(final char key) {
        this.menu.keyEvent(key);
    }
}
