package org.simplegame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;

/**
 * Window of game.
 *
 * @author Emeric MARTINEAU
 */
public class SimpleGameJFrame extends JFrame implements ActionListener {

    /**
     * UID.
     */
    private static final long serialVersionUID = -3157819943389365304L;

    /**
     * Splash screen.
     */
    private final SimpleGameScreen gameScreen;

    /**
     * Timer.
     */
    private final Timer timer;

    /**
     * Size width.
     */
    private final int clientWidth;

    /**
     * Size height.
     */
    private final int clientHeight;

    /**
     * If need to be resize to display full arrea.
     */
    private boolean needToBeResize = true;

    /**
     * Satic class.
     */
    private static class AppleI extends WindowAdapter {

        @Override
        public void windowClosing(final WindowEvent we) {
            SimpleGameKeyHandler.getInstance().escape();
        }
    }

    /**
     * Constructor.
     *
     * @param title game title
     * @param clWidth width
     * @param clHeight height
     * @param delay timing of game
     * @param zoom zoom
     */
    public SimpleGameJFrame(final String title, final int clWidth,
        final int clHeight, final int delay, final int zoom) {
        super(title);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        addWindowListener(new AppleI());

        setSize(clWidth * zoom, clHeight * zoom);
        setResizable(false);

        addKeyListener(SimpleGameKeyHandler.getInstance());
        addWindowFocusListener(SimpleGameKeyHandler.getInstance());

        this.clientWidth = clWidth * zoom;
        this.clientHeight = clHeight * zoom;

        gameScreen = new SimpleGameScreen(clWidth, clHeight, zoom);
        gameScreen.setVisible(true);

        add(gameScreen, BorderLayout.CENTER);
        timer = new Timer(delay, this);

        setVisible(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();

        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }

        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }

        this.setLocation(0,
            (screenSize.height - frameSize.height));
    }

    @Override
    public final void actionPerformed(final ActionEvent e) {
        InterfaceSimpleGameHandleInterface currentHandler
            = SimpleGameHandler.getHandler();

        if (currentHandler != null) {
            currentHandler.run();
        }

        repaint();
    }

    /**
     * Stop game sheduler.
     */
    public final void stop() {
        timer.stop();
    }

    /**
     * Start game sheduler.
     */
    public final void start() {
        timer.start();
    }

    @Override
    public final void paint(final Graphics g) {
        super.paint(g);

        if (needToBeResize) {
            // Use to resize windows with client size = screen size configured
            if ((gameScreen.getWidth() != clientWidth)
                || (gameScreen.getHeight() != clientHeight)) {
                setSize(getWidth() + (clientWidth - gameScreen.getWidth()),
                    getHeight() + (clientHeight - gameScreen.getHeight()));

            }

            needToBeResize = !needToBeResize;
        }
    }
}
