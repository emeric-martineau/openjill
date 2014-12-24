package org.jill.game.level.handler.jill1;

import java.awt.Graphics;
import java.io.IOException;

import org.jill.game.level.AbstractObjectJillLevel;
import org.jill.game.level.cfg.JillLevelConfiguration;
import org.jill.openjill.core.api.jill.JillConst;

/**
 * Noismaker screen for level 1 of Jill Trilogy
 *
 * @author Emeric MARTINEAU
 */
public class NoisemakerScreenJill1Handler extends AbstractObjectJillLevel {

    /**
     * Default constructor of level
     *
     * @throws IOException if missing file
     * @throws ClassNotFoundException if missing class must be load
     * @throws IllegalAccessException if trouble when class must be load
     * @throws InstantiationException if trouble when class must be load
     */
    public NoisemakerScreenJill1Handler() throws IOException, ClassNotFoundException,
            IllegalAccessException, InstantiationException {
        super(new JillLevelConfiguration("JILL1.SHA", "INTRO.JN1", "JILL1.VCL",
                "JILL1.CFG", "JN1"));
        offsetX = - 62 * JillConst.getBlockSize();
        offsetY = - 0 * JillConst.getBlockSize();
    }

    /**
     * @see org.simplegame.InterfaceSimpleGameHandleInterface
     */
    @Override
    public void run() {
        if (keyboard.isEscape()) {
            changeScreenManager(StartMenuJill1Handler.class);
        }
    }

    /**
     * @see org.simplegame.InterfaceSimpleGameHandleInterface
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(background, offsetX, offsetY, null);
    }
}
