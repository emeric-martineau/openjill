package org.jill.game.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jill.game.screen.conf.GameAreaConf;
import org.jill.game.screen.conf.ImagesConf;
import org.jill.game.screen.conf.RectangleConf;
import org.jill.game.screen.conf.StatusBarConf;
import org.jill.game.screen.conf.TextToDraw;
import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.manager.TileManager;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.InterfaceMessageGameHandler;
import org.jill.openjill.core.api.message.statusbar.StatusBarTextMessage;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.simplegame.SimpleGameConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Status bar.
 *
 * @author Emeric MARTINEAU
 */
public final class StatusBar implements InterfaceMessageGameHandler {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(
            StatusBar.class.getName());

    /**
     * Picture cache.
     */
    private final TileManager pictureCache;
    /**
     * Game screen.
     */
    private final BufferedImage gameScreen;
    /**
     * Graphic of status bar.
     */
    private final Graphics2D g2GameScreen;
    /**
     * Configuration.
     */
    private final StatusBarConf conf;
    /**
     * Background.
     */
    private BufferedImage statusBar;
    /**
     * Graphic of status bar.
     */
    private Graphics2D graphic2d;
    /**
     * Wait time before cleat text in bottom of screen.
     */
    private int waitTimeBeforeClearText = -1;

    /**
     * Create status bar.
     *
     * @param pictureCacheManager cache manager
     * @param screen              screen type
     */
    public StatusBar(final TileManager pictureCacheManager,
            final EnumScreenType screen) {
        this.pictureCache = pictureCacheManager;

        if (screen == EnumScreenType.VGA) {
            this.conf = readConf("status_bar_vga.json");
        } else {
            this.conf = readConf("status_bar.json");
        }

        createStatusBar();

        this.gameScreen = new BufferedImage(this.conf.getGameArea().getWidth(),
                this.conf.getGameArea().getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        this.g2GameScreen = gameScreen.createGraphics();
    }

    /**
     * Read config file.
     *
     * @param filename final name of config file
     * @return properties file
     */
    private static StatusBarConf readConf(final String filename) {

        final ObjectMapper mapper = new ObjectMapper();
        final InputStream is =
                StatusBar.class.getClassLoader().
                        getResourceAsStream(filename);

        StatusBarConf mc;

        // Load menu
        try {
            mc = mapper.readValue(is, StatusBarConf.class);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE,
                    String.format("Unable to load config for statusbar '%s'",
                            filename),
                    ex);

            throw new RuntimeException(String.format("Unable to load config for statusbar '%s'",
                    filename), ex);
        }

        return mc;
    }

    /**
     * Create status bar with inventory.
     */
    private void createStatusBar() {
        // Buffer image
        this.statusBar =
                new BufferedImage(SimpleGameConfig.getInstance().getGameWidth(),
                        SimpleGameConfig.getInstance().getGameHeight(),
                        BufferedImage.TYPE_INT_ARGB);
        // Graphic
        this.graphic2d = this.statusBar.createGraphics();

        for (ImagesConf ic : this.conf.getImages()) {
            drawOneTile(ic.getTileset(), ic.getTile(), ic.getX(), ic.getY(),
                    this.graphic2d);
        }

        drawMessageBar(this.graphic2d, Optional.empty(), 0);

        // Draw text
        for (TextToDraw ttd : this.conf.getText()) {
            this.pictureCache.getTextManager().drawSmallText(this.graphic2d,
                    ttd.getX(), ttd.getY(), ttd.getText(), ttd.getColor(),
                    TextManager.BACKGROUND_COLOR_NONE);
        }

        for (TextToDraw ttd : this.conf.getBigtext()) {
            this.pictureCache.getTextManager().drawBigText(this.graphic2d,
                    ttd.getX(), ttd.getY(), ttd.getText(), ttd.getColor(),
                    TextManager.BACKGROUND_COLOR_NONE);
        }

    }

    /**
     * Draw message bar.
     *
     * @param g2    graphic 2d
     * @param msg   message
     * @param color color
     */
    private void drawMessageBar(final Graphics2D g2, final Optional<String> msg,
            final int color) {
        final RectangleConf mb = this.conf.getMessageBar();
        final Color bgcolor = pictureCache.getColorMap()[
                Integer.valueOf(mb.getColor())];
        g2.setColor(new Color(bgcolor.getRGB()));

        g2.fillRect(mb.getX(), mb.getY(), mb.getWidth(), mb.getHeight());

        if (msg.isPresent()) {
            TextManager textManager = this.pictureCache.getTextManager();

            // Create picture
            BufferedImage bi = textManager.createSmallText(msg.get(), color,
                    TextManager.BACKGROUND_COLOR_NONE);

            final int x = (this.statusBar.getWidth() - bi.getWidth()) / 2;
            final int y = mb.getY() + ((mb.getHeight() - bi.getHeight()) / 2);

            g2.drawImage(bi, x, y, null);
        }
    }

    /**
     * Draw one tile on picture.
     *
     * @param tileSetIndex tileset index
     * @param tileIndex    tile index
     * @param x            x
     * @param y            y
     * @param g2           graphic 2d
     */
    private void drawOneTile(final int tileSetIndex, final int tileIndex,
            final int x, final int y, final Graphics2D g2) {
        // Left upper corner
        final BufferedImage tilePicture = pictureCache.getImage(
                tileSetIndex, tileIndex).get();
        g2.drawImage(tilePicture, x, y, null);
    }

    /**
     * Return status bar.
     *
     * @return picture
     */
    public BufferedImage getStatusBar() {
        if (waitTimeBeforeClearText > 0) {
            waitTimeBeforeClearText--;
        } else if (waitTimeBeforeClearText == 0) {
            // Clear text
            drawMessageBar(graphic2d, Optional.empty(), 0);
            waitTimeBeforeClearText--;
        }

        return statusBar;
    }

    /**
     * Return a new image with inventory size.
     *
     * @return picture
     */
    public BufferedImage createInventoryArea() {
        RectangleConf invArea = this.conf.getInventoryArea();

        BufferedImage bf = new BufferedImage(invArea.getWidth(),
                invArea.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        final Color baseColor = pictureCache.getColorMap()[
                Integer.valueOf(invArea.getColor())];

        Graphics2D g2BoxPicture = bf.createGraphics();

        g2BoxPicture.setColor(new Color(baseColor.getRGB()));

        g2BoxPicture.fillRect(0, 0,
                invArea.getWidth(), invArea.getHeight());

        List<ImagesConf> imagesInvenroy = this.conf.getImagesInvenroy();

        if (imagesInvenroy != null) {
            for (ImagesConf ic : imagesInvenroy) {
                drawOneTile(ic.getTileset(), ic.getTile(), ic.getX(), ic.getY(),
                        g2BoxPicture);
            }
        }

        return bf;
    }

    /**
     * Return a new image with inventory size.
     *
     * @return picture
     */
    public BufferedImage createControlArea() {
        return new BufferedImage(this.conf.getControlArea().getWidth(),
                this.conf.getControlArea().getHeight(),
                BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Create game screen.
     *
     * @return picutre
     */
    public BufferedImage createGameScreen() {
        return new BufferedImage(this.conf.getGameArea().getWidth(),
                this.conf.getGameArea().getHeight(),
                BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Draw picture in inventory area.
     *
     * @param inventory picture to draw
     */
    public void drawInventory(final BufferedImage inventory) {
        this.graphic2d.drawImage(inventory, this.conf.getInventoryArea().getX(),
                this.conf.getInventoryArea().getY(), null);
    }

    /**
     * Draw picture in inventory area.
     *
     * @param control picture to draw
     */
    public void drawControl(final BufferedImage control) {
        this.graphic2d.drawImage(control, this.conf.getControlArea().getX(),
                this.conf.getControlArea().getY(), null);
    }

    /**
     * Draw picture in game screen area.
     *
     * @param gS full picture
     * @param x  start position in gS picture to draw
     * @param y  start position in gS picture to draw
     */
    public void drawGameScreen(final BufferedImage gS, final int x,
            final int y) {
        // Copy sub portion of screen
        this.g2GameScreen.drawImage(gS, x, y, null);

        this.graphic2d.drawImage(this.gameScreen,
                this.conf.getGameArea().getX(),
                this.conf.getGameArea().getY(),
                null);
    }

    /**
     * Draw picture in game screen area.
     *
     * @param gS full picture
     */
    public void drawGameScreen(final BufferedImage gS) {
        this.graphic2d.drawImage(gS,
                this.conf.getGameArea().getX(),
                this.conf.getGameArea().getY(), null);
    }

    @Override
    public void recieveMessage(EnumMessageType type, Object msg) {
        switch (type) {
            case MESSAGE_STATUS_BAR:
                StatusBarTextMessage sbt = (StatusBarTextMessage) msg;
                drawMessageBar(graphic2d, Optional.of(sbt.getMessage()), sbt.getColor());
                this.waitTimeBeforeClearText = sbt.getDuration();
                break;
            default:
                // Nothing
        }
    }

    /**
     * Return control area conf.
     *
     * @return configuration
     */
    public RectangleConf getControlAreaConf() {
        return this.conf.getControlArea();
    }

    /**
     * Return game area conf.
     *
     * @return configuration
     */
    public GameAreaConf getGameAreaConf() {
        return this.conf.getGameArea();
    }
}
