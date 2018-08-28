package org.jill.game.gui.menu;

import org.jill.game.gui.menu.conf.MenuConf;
import org.jill.game.screen.conf.PictureConf;
import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.picture.PictureTools;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaFile;

import java.awt.*;
import java.awt.image.BufferedImage;

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
     * @param textManager   cache of picture
     */
    public ClassicMenu(
            final ShaFile shaFile,
            final EnumScreenType screen,
            final String configFilename,
            final TextManager textManager,
            final Color backgroundColor) {
        super(textManager);

        final MenuConf conf = readConf(configFilename);

        setTitle(conf.getTitle());
        createMenuItem(conf);

        setX(conf.getX());
        setY(conf.getY());
        setTextX(conf.getTextX());
        setTextY(conf.getTextY());

        setNbSpaceBefore(conf.getNbSpaceBefore());

        setRightUpperCorner(getImage(shaFile, screen, conf.getRightUpperCorner()));
        setLeftUpperCorner(getImage(shaFile, screen, conf.getLeftUpperCorner()));

        setRightLowerCorner(getImage(shaFile, screen, conf.getRightLowerCorner()));
        setLeftLowerCorner(getImage(shaFile, screen, conf.getLeftLowerCorner()));

        setUpperBar(getImage(shaFile, screen, conf.getUpperBar()));
        setLowerBar(getImage(shaFile, screen, conf.getLowerBar()));

        setLeftBar(getImage(shaFile, screen, conf.getLeftBar()));
        setRightBar(getImage(shaFile, screen, conf.getRightBar()));

        setBackImage(getImage(shaFile, screen, conf.getBackImage()));

        createBackground(backgroundColor);
    }

    /**
     * Get image.
     *
     * @param shaFile sha file
     * @param conf         configuration
     * @return picture
     */
    private BufferedImage getImage(final ShaFile shaFile, final EnumScreenType screen,
            final PictureConf conf) {
        return PictureTools.getPicture(shaFile, conf.getTileset(), conf.getTile(), screen).get();
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
