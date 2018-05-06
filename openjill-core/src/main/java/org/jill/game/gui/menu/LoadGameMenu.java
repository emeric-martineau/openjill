package org.jill.game.gui.menu;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

import org.jill.cfg.SaveGameItem;
import org.jill.openjill.core.api.manager.TileManager;

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
     * @param pictureCacheManager cache of picture
     * @param saveGameList        list of high score (can be modified !)
     * @param positionToDrawMenuX x to draw
     * @param positionToDrawMenuY y to draw
     */
    public LoadGameMenu(final BufferedImage menuScreen,
            final TileManager pictureCacheManager,
            final List<SaveGameItem> saveGameList,
            final int positionToDrawMenuX,
            final int positionToDrawMenuY) {
        this(menuScreen, pictureCacheManager, saveGameList,
                positionToDrawMenuX,
                positionToDrawMenuY, Optional.empty());
    }

    /**
     * Constructor.
     *
     * @param menuScreen          object to draw high score
     * @param pictureCacheManager cache of picture
     * @param saveGameList        list of high score (can be modified !)
     * @param positionToDrawMenuX x to draw
     * @param positionToDrawMenuY y to draw
     * @param nextMenuObj         next menu to draw
     */
    public LoadGameMenu(final BufferedImage menuScreen,
            final TileManager pictureCacheManager,
            final List<SaveGameItem> saveGameList,
            final int positionToDrawMenuX,
            final int positionToDrawMenuY,
            final Optional<MenuInterface> nextMenuObj) {
        super(menuScreen, pictureCacheManager, saveGameList,
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
