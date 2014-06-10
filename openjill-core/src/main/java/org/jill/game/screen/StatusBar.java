package org.jill.game.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jill.game.screen.conf.ImagesConf;
import org.jill.game.screen.conf.RectangleConf;
import org.jill.game.screen.conf.StatusBarConf;
import org.jill.game.screen.conf.TextToDraw;
import org.jill.openjill.core.api.manager.TextManager;
import org.jill.openjill.core.api.manager.TileManager;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.InterfaceMessageGameHandler;
import org.jill.openjill.core.api.message.statusbar.StatusBarTextMessage;
import org.simplegame.SimpleGameConfig;

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
     * Hexa base.
     */
    private static final int HEXA_BASE = 16;

    /**
     * Picture cache.
     */
    private final TileManager pictureCache;

    /**
     * Background.
     */
    private BufferedImage statusBar;

    /**
     * Graphic of status bar.
     */
    private Graphics2D graphic2d;

    /**
     * Game screen.
     */
    private final BufferedImage gameScreen;

    /**
     * Graphic of status bar.
     */
    private final Graphics2D g2GameScreen;

    /**
     * Wait time before cleat text in bottom of screen.
     */
    private int waitTimeBeforeClearText = -1;

    /**
     * Configuration.
     */
    private final StatusBarConf conf;

//    static {
//        try {
//            Properties conf = new Properties();
//            conf.load(
//                    StatusBar.class.
//                    getClassLoader().
//                    getResourceAsStream("status_bar.properties"));
//
//            controlText = conf.getProperty(CONTROL_KEY);
//            inventoryText = conf.getProperty(INVENTORY_KEY);
//            titleText = conf.getProperty(TITLE_KEY);
//        } catch (final IOException ex) {
//            LOGGER.log(Level.SEVERE,
//                "Can't load inventory config file !", ex);
//        }
//    }

    /**
     * Create status bar.
     *
     * @param pictureCacheManager cache manager
     */
    public StatusBar(final TileManager pictureCacheManager) {
        this.pictureCache = pictureCacheManager;

        this.conf = readConf("status_bar.json");

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
     *
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

            mc = null;
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

        //drawStatusBarUpperHorizontalBar(graphic2d);

        //drawStatusBarMiddleHorizontalBar(graphic2d);

        //drawStatusBarLowerHorizontalBar(graphic2d);

        //drawStatusBarRightVerticalBar(graphic2d);

        //drawStatusBarMiddleVerticalBar(graphic2d);

        //drawStatusBarLeftVerticalBar(graphic2d);

        for (ImagesConf ic : this.conf.getImages()) {
            drawOneTile(ic.getTileset(), ic.getTile(), ic.getX(), ic.getY(),
                    this.graphic2d);
        }

        drawMessageBar(this.graphic2d, null, 0);

        // Write CONTROLS
//        TextManager textManager = pictureCache.getTextManager();
//        textManager.drawSmallText(graphic2d, 16, 5, controlText,
//                TextManager.COLOR_DARK_BLUE,
//                TextManager.BACKGROUND_COLOR_NONE);
//        textManager.drawSmallText(graphic2d, 13, 179, inventoryText,
//                TextManager.COLOR_DARK_BLUE,
//                TextManager.BACKGROUND_COLOR_NONE);
//        textManager.drawBigText(graphic2d, 150, 4, titleText,
//                TextManager.COLOR_DARK_BLUE,
//                TextManager.BACKGROUND_COLOR_NONE);

        // Draw text
        for (TextToDraw ttd : this.conf.getText()) {
            this.pictureCache.getTextManager().drawSmallText(this.graphic2d,
                    ttd.getX(), ttd.getY(), ttd.getText(), ttd.getColor(),
                TextManager.BACKGROUND_COLOR_NONE);
        }
    }

    /**
     * Draw message bar.
     *
     * @param g2 graphic 2d
     * @param msg message
     * @param color  color
     */
    private void drawMessageBar(final Graphics2D g2, final String msg,
            final int color) {
        final RectangleConf mb = this.conf.getMessageBar();
        final Color bgcolor = new Color(
                            Integer.parseInt(mb.getColor(), HEXA_BASE));
        g2.setColor(bgcolor);

        g2.fillRect(mb.getX(), mb.getY(), mb.getWidth(), mb.getHeight());

        if (msg != null) {
            TextManager textManager = this.pictureCache.getTextManager();

            // Create picture
            BufferedImage bi = textManager.createSmallText(msg, color,
                    TextManager.BACKGROUND_COLOR_NONE);

            final int x = (this.statusBar.getWidth() - bi.getWidth()) / 2;
            final int y = mb.getY() + ((mb.getHeight() - bi.getHeight()) / 2);

            g2.drawImage(bi, x, y, null);
        }
    }

    /**
     * Draw upper horizontal bar.
     *
     * @param g2 graphic 2d
     */
    private void drawStatusBarUpperHorizontalBar(final Graphics2D g2) {
        //-[ Draw upper horizontal bar ]---------------------------------------
        // Left upper corner
        //drawOneTile(3, 2, 0, 0, g2);

        // Draw top bar
//        BufferedImage tilePicture = pictureCache.getImage(3, 4);
//
//        for (int index = 8; index < 320; index += tilePicture.getWidth()) {
//            System.out.println("x:"+index);
//            g2.drawImage(tilePicture, index, 0, null);
//        }
//
        drawOneTile(3, 3, 312, 0, g2);
//
        drawOneTile(3, 10, 72, 0, g2);
    }

    /**
     * Draw middle horizontal bar.
     *
     * @param g2 graphic 2d
     */
    private void drawStatusBarMiddleHorizontalBar(final Graphics2D g2) {
        //-[ Draw middle horizontal bar ]--------------------------------------
        BufferedImage tilePicture = pictureCache.getImage(3, 12);

        for(int index = 8; index < 72; index += tilePicture.getWidth()) {
            g2.drawImage(tilePicture, index, 96, null);
        }
    }

    /**
     * Draw lower horizontal bar.
     *
     * @param g2 graphic 2d
     */
    private void drawStatusBarLowerHorizontalBar(final Graphics2D g2) {
        //-[ Draw lower horizontal bar ]---------------------------------------
        drawOneTile(3, 5, 0, 176, g2);

        BufferedImage tilePicture = pictureCache.getImage(3, 6);

        for(int index = 8; index < 312; index += tilePicture.getWidth()) {
            g2.drawImage(tilePicture, index, 176, null);
        }
    }

    /**
     * Draw right vertical bar.
     *
     * @param g2 graphic 2d
     */
    private void drawStatusBarRightVerticalBar(final Graphics2D g2) {
        //-[ Draw right vertical bar ]-----------------------------------------
        // Draw jonction
        BufferedImage tilePicture = pictureCache.getImage(3, 9);

        for(int index = 16; index < 176; index += tilePicture.getHeight()) {
            g2.drawImage(tilePicture, 0, index, null);
        }

        drawOneTile(3, 13, 0, 96, g2);
    }

    /**
     * Draw middle vertical bar.
     *
     * @param g2 graphic 2d
     */
    private void drawStatusBarMiddleVerticalBar(final Graphics2D g2) {
        BufferedImage tilePicture = pictureCache.getImage(3, 9);

        //-[ Draw middle vertical bar ]----------------------------------------
        for(int index = 16; index < 176; index += tilePicture.getHeight()) {
System.out.println("        {");
System.out.println("            \"tileset\":3,");
System.out.println("            \"tile\":9,");
System.out.println("            \"x\":72,");
System.out.println("            \"y\":" + index);
System.out.println("        },");
            g2.drawImage(tilePicture, 72, index, null);
        }

        // Draw jonction
        drawOneTile(3, 11, 72, 96, g2);

        // Draw lower jonction
        drawOneTile(3, 8, 72, 176, g2);
    }

    /**
     * Draw left vertical bar.
     *
     * @param g2 graphic 2d
     */
    private void drawStatusBarLeftVerticalBar(final Graphics2D g2) {
        //-[ Draw left vertical bar ]------------------------------------------
        // Draw left bar
        drawOneTile(3, 1, 312, 16, g2);

        // Draw upper arrow left
        drawOneTile(3, 14, 312, 32, g2);

        // Draw left bar
        drawOneTile(3, 0, 312, 48, g2);
        drawOneTile(3, 1, 312, 64, g2);
        drawOneTile(3, 0, 312, 80, g2);
        drawOneTile(3, 1, 312, 96, g2);
        drawOneTile(3, 0, 312, 112, g2);
        drawOneTile(3, 1, 312, 128, g2);

        // Draw down arrow left
        drawOneTile(3, 15, 312, 144, g2);

        // Draw left bar
        drawOneTile(3, 0, 312, 160, g2);

        // Draw lower left corner
        drawOneTile(3, 7, 312, 176, g2);
    }


    /**
     * Draw one tile on picture.
     *
     * @param tileSetIndex tileset index
     * @param tileIndex tile index
     * @param x x
     * @param y y
     * @param g2 graphic 2d
     */
    private void drawOneTile(final int tileSetIndex, final int tileIndex,
            final int x, final int y, final Graphics2D g2) {
        // Left upper corner
        final BufferedImage tilePicture = pictureCache.getImage(
                tileSetIndex,tileIndex);
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
            drawMessageBar(graphic2d, null, 0);
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
        return new BufferedImage(this.conf.getInventoryArea().getWidth(),
                this.conf.getInventoryArea().getHeight(),
                BufferedImage.TYPE_INT_ARGB);
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
     * @param x start position in gS picture to draw
     * @param y start position in gS picture to draw
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
        switch(type) {
            case MESSAGE_STATUS_BAR :
                StatusBarTextMessage sbt = (StatusBarTextMessage) msg;
                drawMessageBar(graphic2d, sbt.getMessage(), sbt.getColor());
                this.waitTimeBeforeClearText = sbt.getDuration();
                break;
            default :
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
    public RectangleConf getGameAreaConf() {
        return this.conf.getGameArea();
    }
}
