package org.jill.game.gui.menu;

import org.jill.cfg.SaveGameItem;
import org.jill.openjill.core.api.manager.TextManager;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

/**
 * Class to display load menu.
 *
 * @author Emeric MARTINEAU
 */
public final class LoadGameMenu extends AbstractLoadGameMenu {
    /**
     * Constructor.
     *
     * @param menuScreen          object to draw high score
     * @param textManager cache of picture
     * @param saveGameList        list of high score (can be modified !)
     * @param positionToDrawMenuX x to draw
     * @param positionToDrawMenuY y to draw
     */
    public LoadGameMenu(final BufferedImage menuScreen,
            final TextManager textManager,
            final List<SaveGameItem> saveGameList,
            final int positionToDrawMenuX,
            final int positionToDrawMenuY) {
        this(menuScreen, textManager, saveGameList,
                positionToDrawMenuX,
                positionToDrawMenuY, Optional.empty());
    }

    /**
     * Constructor.
     *
     * @param menuScreen          object to draw high score
     * @param textManager cache of picture
     * @param saveGameList        list of high score (can be modified !)
     * @param positionToDrawMenuX x to draw
     * @param positionToDrawMenuY y to draw
     * @param nextMenuObj         next menu to draw
     */
    public LoadGameMenu(final BufferedImage menuScreen,
            final TextManager textManager,
            final List<SaveGameItem> saveGameList,
            final int positionToDrawMenuX,
            final int positionToDrawMenuY,
            final Optional<MenuInterface> nextMenuObj) {
        super(menuScreen, textManager, saveGameList,
                positionToDrawMenuX, positionToDrawMenuY, nextMenuObj);
    }

    /**
     * Return name of file configuration.
     *
     * @return file name (json format)
     */
    @Override
    protected String getConfigFileName() {
        return "load_game_menu.json";
    }

    @Override
    public void left() {
        up();
    }

    @Override
    public void right() {
        down();
    }
}
