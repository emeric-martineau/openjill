package org.jill.game.level.handler.jill1;

import java.awt.Graphics;
import java.io.IOException;

import org.jill.game.level.AbstractObjectJillLevel;
import org.jill.game.level.cfg.JillLevelConfiguration;
import org.jill.openjill.core.api.jill.JillConst;

/**
 * Screen of story for level 1 of Jill Trilogy
 *
 * @author Emeric MARTINEAU
 */
public class StoryScreenJill1Handler extends AbstractObjectJillLevel {

    /**
     * Default constructor of level
     *
     * @throws IOException if missing file
     * @throws ClassNotFoundException if missing class must be load
     * @throws IllegalAccessException if trouble when class must be load
     * @throws InstantiationException if trouble when class must be load
     */
    public StoryScreenJill1Handler() throws IOException, ClassNotFoundException,
            IllegalAccessException, InstantiationException {
        super(new JillLevelConfiguration("JILL1.SHA", "INTRO.JN1", "JILL1.VCL",
                "JILL1.CFG", "JN1"));


        offsetX = - 36 * JillConst.BLOCK_SIZE;
        offsetY = - 2 * JillConst.BLOCK_SIZE;
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
        g.drawImage(background, offsetX, offsetY, null);
    }
}
