package org.jill.game.entities.obj.player;

import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.message.statusbar.StatusBarTextMessage;

/**
 * Constante for wait state.
 *
 * @author Emeric MARTINEAU
 */
public interface PlayerWaitConst {
    /**
     * Message to display.
     */
    StatusBarTextMessage[] WAIT_MESSAGES = {
        new StatusBarTextMessage("Have you seen Jill anywhere?",
                47, TextManager.COLOR_GREEN),
        new StatusBarTextMessage("Look, an airplane!",
                47, TextManager.COLOR_GREEN),
        new StatusBarTextMessage("Hey, your shoes are untied.",
                47, TextManager.COLOR_GREEN),
        new StatusBarTextMessage("Are you just gonna sit there?",
                47, TextManager.COLOR_GREEN)};

    /**
     * Wait anlimation.
     */
    int HAVE_YOU_SEEN_JILL_ANYWHERE = 0;

    /**
     * Wait anlimation.
     */
    int LOOK_AN_AIREPLANE = 1;

    /**
     * Wait anlimation.
     */
    int HEY_YOUR_SHOES_ARE_UNTIED = 2;

    /**
     * Wait anlimation.
     */
    int ARE_YOU_JUST_GONNA_SIT_THERE = 3;

    /**
     * Div to calaculate image to display.
     */
    int HAVE_YOU_SEEN_JILL_ANYWHERE_DIV = 6;


    /**
     * Just for checkstyle and PMD.
     */
    void nothing();
}
