package org.jill.game.level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.jill.game.gui.InformationBox;
import org.jill.game.gui.LevelMessageBox;
import org.jill.game.gui.menu.MenuInterface;
import org.jill.game.level.cfg.LevelConfiguration;

/**
 * This class manage display menu.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractMenuJillLevel extends AbstractObjectJillLevel {
    /**
     * Menu to display.
     */
    protected MenuInterface menu;

    /**
     * Display information, hint...
     */
    protected InformationBox infoBox;

    /**
     * Box to display message when level change.
     */
    protected LevelMessageBox levelMessageBox;

    /**
     * Current display screen.
     */
    protected BufferedImage currentDisplayScreen;

    /**
     * To know if key is always pressed.
     */
    private boolean keyReleased = false;

    /**
     * Level configuration.
     *
     * @param cfgLevel configuration of level
     * @throws IOException                  if error of reading file
     * @throws ReflectiveOperationException if not class found
     */
    public AbstractMenuJillLevel(final LevelConfiguration cfgLevel)
            throws IOException, ReflectiveOperationException {
        super(cfgLevel);

        constructor(cfgLevel);
    }

    /**
     * Construct object.
     */
    private void constructor(final LevelConfiguration cfgLevel) {
        initMenu();

        this.infoBox = new InformationBox(this.pictureCache);

        this.levelMessageBox = new LevelMessageBox(this.pictureCache,
                cfgLevel.getCfgSavePrefixe(), this.screenType);
    }

    @Override
    public void run() {
        if (this.levelMessageBox.isEnable()) {
            if (this.keyReleased) {
                this.levelMessageBox.setCanchange(this.keyboard.isKeyPressed());
            } else {
                this.keyReleased = !this.keyboard.isKeyPressed();
            }
        } else if (this.infoBox.isEnable()) {
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
            } else if (this.keyboard.isLeft()) {
                this.menu.left();
            } else if (this.keyboard.isRight()) {
                this.menu.right();
            } else if (this.keyboard.isEnter()) {
                validateMenu();
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
     * Validate menu.
     */
    private void validateMenu() {
        // Clear key before switch
        this.keyboard.clear();

        menuEntryValidate(this.menu.getCursorValue());
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

        if (this.levelMessageBox.isEnable()) {
            g.drawImage(this.levelMessageBox.getBox(),
                    this.levelMessageBox.getX(), this.levelMessageBox.getY(),
                    null);
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
     *
     * @param key kay of keyboard
     */
    private void menuOtherKeyHandler(final char key) {
        if (this.menu.keyEvent(key)) {
            validateMenu();
        }
    }
}
