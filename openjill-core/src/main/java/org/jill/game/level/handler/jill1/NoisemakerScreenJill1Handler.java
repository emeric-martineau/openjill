package org.jill.game.level.handler.jill1;

import java.awt.Graphics;
import java.io.IOException;

import org.jill.game.level.AbstractObjectJillLevel;
import org.jill.game.level.cfg.JillLevelConfiguration;
import org.jill.game.screen.conf.RectangleConf;
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
     * @throws ReflectiveOperationException if missing class must be load
     */
    public NoisemakerScreenJill1Handler() throws IOException, ReflectiveOperationException {
        super(new JillLevelConfiguration("JILL1.SHA", "INTRO.JN1", "JILL1.VCL",
                "JILL1.CFG", "JN1"));
        final RectangleConf offset
                = this.statusBar.getGameAreaConf().getOffset();
        
        offset.setX(
                - 62 * JillConst.getBlockSize());
        offset.setY(
                0);
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
        final RectangleConf offset
                = this.statusBar.getGameAreaConf().getOffset();
        
        g.drawImage(background, offset.getX(),
                offset.getY(), null);
    }
}
