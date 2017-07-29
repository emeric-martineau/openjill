package org.simplegame;

import java.awt.Graphics;

/**
 * Interface on game timer handler and key handler.
 *
 * @author Emeric MARTINEAU
 */
public interface InterfaceSimpleGameHandleInterface {
    /**
     * Call each timer tick count.
     */
    void run();

    /**
     * Call for draw screen.
     *
     * @param g Graphics to draw
     */
    void paint(Graphics g);
}
