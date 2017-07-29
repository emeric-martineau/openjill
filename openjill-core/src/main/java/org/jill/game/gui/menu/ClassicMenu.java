package org.jill.game.gui.menu;

import java.awt.image.BufferedImage;

import org.jill.game.gui.menu.conf.MenuConf;
import org.jill.game.screen.conf.PictureConf;
import org.jill.openjill.core.api.manager.TileManager;

/**
 * Classic menu game (start menu, exit menu...).
 *
 * @author Emeric Martineau
 */
public final class ClassicMenu extends AbstractStdMenu {
    /**
     * Constructor.
     *
     * @param configFilename config file
     * @param pictureCache   cache of picture
     */
    public ClassicMenu(
            final String configFilename,
            final TileManager pictureCache) {
        super(pictureCache);

        final MenuConf conf = readConf(configFilename);

        setTitle(conf.getTitle());
        createMenuItem(conf);

        setX(conf.getX());
        setY(conf.getY());
        setTextX(conf.getTextX());
        setTextY(conf.getTextY());

        setNbSpaceBefore(conf.getNbSpaceBefore());

        setRightUpperCorner(getImage(pictureCache, conf.getRightUpperCorner()));
        setLeftUpperCorner(getImage(pictureCache, conf.getLeftUpperCorner()));

        setRightLowerCorner(getImage(pictureCache, conf.getRightLowerCorner()));
        setLeftLowerCorner(getImage(pictureCache, conf.getLeftLowerCorner()));

        setUpperBar(getImage(pictureCache, conf.getUpperBar()));
        setLowerBar(getImage(pictureCache, conf.getLowerBar()));

        setLeftBar(getImage(pictureCache, conf.getLeftBar()));
        setRightBar(getImage(pictureCache, conf.getRightBar()));

        setBackImage(getImage(pictureCache, conf.getBackImage()));

        createBackground(pictureCache);
    }

    /**
     * Get image.
     *
     * @param pictureCache cache manager
     * @param conf         configuration
     * @return picture
     */
    private BufferedImage getImage(final TileManager pictureCache,
            final PictureConf conf) {
        return pictureCache.getImage(conf.getTileset(), conf.getTile());
    }

    /**
     * Create menu item (sorted by name of key).
     *
     * @param conf propertie
     */
    private void createMenuItem(final MenuConf conf) {
        conf.getItem().forEach(this::addItem);
    }
}
