package org.simplegame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Game screen where game draw (use it because double buffered).
 *
 * @author Emeric MARTINEAU
 */
public class SimpleGameScreen extends JPanel {
    /**
     * serial.
     */
    private static final long serialVersionUID = 7510966811418023834L;

    /**
     * Game zoom.
     */
    private final int zoom;

    /**
     * Image without resize.
     */
    private transient BufferedImage initImage;

    /**
     * Image (initImage) resized.
     */
    private transient BufferedImage resizedImage;

    /**
     * Graphic for resize.
     */
    private transient Graphics2D g2Resize;

    /**
     * Graphic for normal size picture.
     */
    private transient Graphics2D g2Init;

    /**
     * Constructor of screen.
     *
     * @param clientWidth  basic size
     * @param clientHeight basic size
     * @param zoomValue    zoom
     */
    public SimpleGameScreen(final int clientWidth,
            final int clientHeight, final int zoomValue) {
        this.zoom = zoomValue;

        setDoubleBuffered(true);

        if (zoomValue > 1) {
            resizedImage = new BufferedImage(clientWidth * zoomValue,
                    clientHeight * zoomValue, BufferedImage.TYPE_INT_ARGB);
            g2Resize = resizedImage.createGraphics();

            initImage = new BufferedImage(clientWidth,
                    clientHeight, BufferedImage.TYPE_INT_ARGB);

            g2Init = initImage.createGraphics();
        }
    }

    @Override
    protected final void paintComponent(final Graphics g) {
        super.paintComponents(g);

        InterfaceSimpleGameHandleInterface currentHandler =
                SimpleGameHandler.getHandler();

        if (currentHandler != null) {
            if (zoom > 1) {
                currentHandler.paint(g2Init);

                g2Resize.drawImage(initImage, 0, 0, resizedImage.getWidth(),
                        resizedImage.getHeight(), null);

                g.drawImage(resizedImage, 0, 0, null);
            } else {
                currentHandler.paint(g);
            }
        }
    }
}
