package org.jill.game.level.handler.jill1;


import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.jill.game.level.AbstractExecutingStdPlayerLevel;
import org.jill.game.level.cfg.JillLevelConfiguration;
import org.jill.openjill.core.api.jill.JillConst;
import org.simplegame.SimpleGameConfig;

/**
 * Ordering screen for level 1 of Jill Trilogy.
 *
 * @author Emeric MARTINEAU
 */
public class OrderingInfoScreenJill1Handler
    extends AbstractExecutingStdPlayerLevel {
    /**
     * List of screens.
     */
    private final Point[] screens = new Point[4];

    /**
     * Current screen position.
     */
    private int currentScreen = 0;

    /**
     * Default constructor of level.
     *
     * @throws IOException if missing file
     * @throws ClassNotFoundException if missing class must be load
     * @throws IllegalAccessException if trouble when class must be load
     * @throws InstantiationException if trouble when class must be load
     */
    public OrderingInfoScreenJill1Handler() throws IOException,
            ClassNotFoundException, IllegalAccessException,
            InstantiationException {
        super(new JillLevelConfiguration("JILL1.SHA", "INTRO.JN1", "JILL1.VCL",
                "JILL1.CFG", "JN1"));

        screens[0] = newScreen(14, 15);
        screens[1] = newScreen(0, 35);
        screens[2] = newScreen(36, 15);
        screens[3] = newScreen(20, 35);

        // Must set offsetX and offsetY before select object to display.
        centerScreen();

        setUpdateObject(false);
    }

    /**
     * Create a new point.
     *
     * @param x X in block
     * @param y Y in block
     *
     * @return a point converting in pixel size
     */
    private static Point newScreen(final int x, final int y) {
        return new Point(x * -JillConst.BLOCK_SIZE, y * -JillConst.BLOCK_SIZE);
    }

    /**
     * @see org.jill.game.level.AbstractExecutingStdLevel
     */
    @Override
    protected BufferedImage createGameScreen() {
        return new BufferedImage(
                SimpleGameConfig.getInstance().getGameWidth(),
                SimpleGameConfig.getInstance().getGameHeight(),
                    BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Overrid method to center screen.
     * To do this, just set offsetX and offsetY
     *
     * @see org.jill.game.level.AbstractExecutingStdLevel
     */
    @Override
    protected void centerScreen() {
        offsetX = screens[currentScreen].x;
        offsetY = screens[currentScreen].y;
    }

    /**
     * @see org.jill.game.level.AbstractMenuJillLevel
     */
    @Override
    public void doRun() {
        if (keyboard.isKeyPressed()) {
            keyboard.clear();

            currentScreen++;

            if (currentScreen >= screens.length) {
                changeScreenManager(StartMenuJill1Handler.class);
            } else {
                offsetX = screens[currentScreen].x;
                offsetY = screens[currentScreen].y;
            }

            this.runGame = true;
        } else if (this.runGame) {
            currentDisplayScreen = drawingScreen;
            // Display only at first time
            doRunNext();
            //doRunNext();
            this.runGame = false;
        }
    }

    /**
     * @see org.jill.game.level.AbstractExecutingStdLevel
     */
    @Override
    protected void saveGame() {
    }

    /**
     * @see org.jill.game.level.AbstractExecutingStdLevel
     */
    @Override
    protected void loadGame() {
    }

    @Override
    protected void doEscape() {

    }
}
