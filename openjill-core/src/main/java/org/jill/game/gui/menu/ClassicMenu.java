package org.jill.game.gui.menu;

import org.jill.game.gui.menu.conf.MenuConf;
import org.jill.openjill.core.api.manager.TileManager;

/**
 * Classic menu game (start menu, exit menu...).
 *
 * @author Emeric Martineau
 */
public final class ClassicMenu extends StdMenu {
    /**
     * Constructor.
     *
     * @param configFilename config file
     * @param pictureCache cache of picture
     */
    public ClassicMenu(
            final String configFilename,
            final TileManager pictureCache) {
        super(pictureCache);

        final MenuConf conf = readConf(configFilename);

        setTitle(conf.getTitle());
        createMenuItem(conf);

        this.positionToDrawMenuX = conf.getPositionX();
        this.positionToDrawMenuY = conf.getPositionY();
    }

    /**
     * Create menu item (sorted by name of key).
     *
     * @param conf propertie
     */
    private void createMenuItem(final MenuConf conf) {
        for (SubMenu sb : conf.getItem()) {
            this.addItem(sb);
        }
    }
}
