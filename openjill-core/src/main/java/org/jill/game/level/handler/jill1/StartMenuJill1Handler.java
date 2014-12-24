package org.jill.game.level.handler.jill1;

import java.awt.image.BufferedImage;
import java.io.IOException;
import org.jill.game.gui.menu.ClassicMenu;
import org.jill.game.gui.menu.HighScoreMenu;
import org.jill.game.screen.conf.RectangleConf;
import org.jill.game.level.AbstractChangeLevel;
import org.jill.game.level.cfg.JillLevelConfiguration;
import org.jill.openjill.core.api.jill.JillConst;

/**
 * Start menu of credit for level 1 of Jill Trilogy.
 *
 * @author Emeric MARTINEAU
 */
public class StartMenuJill1Handler extends AbstractChangeLevel {
    /**
     * Default constructor of level.
     *
     * @throws IOException if missing file
     * @throws ClassNotFoundException if missing class must be load
     * @throws IllegalAccessException if trouble when class must be load
     * @throws InstantiationException if trouble when class must be load
     */
    public StartMenuJill1Handler() throws IOException, ClassNotFoundException,
        IllegalAccessException, InstantiationException {
        super(new JillLevelConfiguration("JILL1.SHA", "INTRO.JN1", "JILL1.VCL",
            "JILL1.CFG", "JN1", StartMenuJill1Handler.class, false));
        displayHome();

        infoBox.setContent(vclFile.getVclText().get(0).getText());

        this.menuLoadGame.setPreviousMenu(this.menu);
    }

    @Override
    protected void initMenu() {
        this.menuStd = new ClassicMenu("start_menu.json", pictureCache);
        this.menu = this.menuStd;
    }

    @Override
    protected void menuEntryValidate(final int value) {
        if (this.menu == this.menuLoadGame) {
            doMenuValidate();
        } else {
            switch (value) {
                case 0:
                    changeScreenManager(MapLevelHandler.class);
                    break;
                case 1:
                    this.menuLoadGame.setEnable(true);
                    this.menu = this.menuLoadGame;
                    break;
                case 2:
                    changeScreenManager(StoryScreenJill1Handler.class);
                    break;
                case 3:
                    infoBox.setEnable(true);
                    break;
                case 4:
                    changeScreenManager(OrderingInfoScreenJill1Handler.class);
                    break;
                case 5:
                    changeScreenManager(CreditScreenJill1Handler.class);
                    break;
                case 7:
                    changeScreenManager(NoisemakerScreenJill1Handler.class);
                    break;
                case 9:
                    System.exit(0);
                    break;
                default:
            }
        }
    }

    @Override
    protected void menuOtherKeyHandler(final char key) {
        if (key == 'R' || key == 'r') {
            menuEntryValidate(1);
        } else {
            super.menuOtherKeyHandler(key);
        }
    }

    /**
     * Display home page.
     */
    private void displayHome() {
        // Draw jill face
        final BufferedImage inventory = createJillFace();
        statusBar.drawInventory(inventory);

        statusBar.drawControl(createHigScore());

        centerScreen();
    }

    /**
     * Create highscore.
     *
     * @return picture
     */
    private BufferedImage createHigScore() {
        final BufferedImage highScore = statusBar.createControlArea();

        final RectangleConf controlAreaConf =
                this.statusBar.getControlAreaConf();

        new HighScoreMenu(highScore, pictureCache,
            cfgFile.getHighScore(), controlAreaConf.getX(),
            controlAreaConf.getY());

        return highScore;
    }

    @Override
    protected void centerScreen() {
        final int blocOffsetX = 112;
        final int blocOffsetY = 53;

        // Picture offset
        offsetX = -(blocOffsetX + 1) * JillConst.getBlockSize();
        offsetY = -(blocOffsetY + 1) * JillConst.getBlockSize();
    }

    @Override
    protected void doRunNext() {
        super.doRunNext();
        runGame = false;
        menu.setEnable(true);
    }

    @Override
    protected void doEscape() {
        if (this.menu == this.menuLoadGame) {
            this.menu.setEnable(false);
            this.menu = this.menuStd;
        } else {
            System.exit(0);
        }
    }
}
