package org.jill.game.level.handler.jill1;

import java.awt.Graphics;
import java.io.IOException;
import java.util.Optional;

import org.jill.game.level.AbstractObjectJillLevel;
import org.jill.game.level.cfg.JillLevelConfiguration;
import org.jill.game.screen.conf.RectangleConf;
import org.jill.openjill.core.api.jill.JillConst;

/**
 * Screen of credit for level 1 of Jill Trilogy
 *
 * @author Emeric MARTINEAU
 */
public class CreditScreenJill1Handler extends AbstractObjectJillLevel {

    /**
     * Default constructor of level
     *
     * @throws IOException                  if missing file
     * @throws ReflectiveOperationException if missing class must be load
     */
    public CreditScreenJill1Handler() throws IOException, ReflectiveOperationException {
        super(new JillLevelConfiguration("JILL1.SHA", Optional.of("INTRO.JN1"), "JILL1.VCL",
                "JILL1.CFG", "JN1"));
        final RectangleConf offset
                = this.statusBar.getGameAreaConf().getOffset();

        offset.setX(
                -15 * JillConst.getBlockSize());
        offset.setY(
                0);
    }

    /**
     * @see org.simplegame.InterfaceSimpleGameHandleInterface
     */
    @Override
    public void run() {
        if (keyboard.isKeyPressed()) {
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
