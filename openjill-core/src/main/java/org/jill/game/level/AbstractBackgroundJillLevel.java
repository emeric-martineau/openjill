package org.jill.game.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jill.dma.DmaEntry;
import org.jill.game.config.JillGameConfig;
import org.jill.game.config.ObjectInstanceFactory;
import org.jill.game.level.cfg.LevelConfiguration;
import org.jill.game.manager.background.BackgroundManager;
import org.jill.game.screen.StatusBar;
import org.jill.jn.BackgroundLayer;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.BackgroundParam;
import org.jill.openjill.core.api.jill.JillConst;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.InterfaceMessageGameHandler;
import org.jill.openjill.core.api.message.MessageDispatcher;
import org.jill.openjill.core.api.message.background.BackgroundMessage;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.simplegame.InterfaceSimpleGameHandleInterface;
import org.simplegame.SimpleGameConfig;

/**
 * This class manage all of background, screen, load files and message
 * dispatcher.
 *
 * @author Emeric MARTINEAU
 */
public abstract class AbstractBackgroundJillLevel
        extends AbstractBasicCacheLevel
        implements InterfaceSimpleGameHandleInterface,
        InterfaceMessageGameHandler {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(
            AbstractBackgroundJillLevel.class.getName());
    /**
     * Message dispatcher.
     */
    protected final MessageDispatcher messageDispatcher =
            ObjectInstanceFactory.getNewMsgDispatcher();
    /**
     * Background manager.
     */
    protected final BackgroundManager backgroundManager =
            BackgroundManager.getInstance();
    /**
     * Background.
     */
    protected BufferedImage background;
    /**
     * Background.
     */
    protected Graphics2D g2Background;
    /**
     * Background object.
     */
    protected BackgroundEntity[][] backgroundObject;
    /**
     * Status bar.
     */
    protected StatusBar statusBar;
    /**
     * Screen type.
     */
    protected EnumScreenType screenType;
    /**
     * Screen width.
     */
    protected int screenWidthBlock;
    /**
     * Screen height.
     */
    protected int screenHeightBlock;

    /**
     * Level configuration.
     *
     * @param cfgLevel configuration of level
     */
    public AbstractBackgroundJillLevel(final LevelConfiguration cfgLevel) {
        this.levelConfiguration = cfgLevel;
    }

    /**
     * Fill picture in black.
     *
     * @param image     picture
     * @param g2        graphic 2d object
     * @param backColor color
     */
    protected static void fillPicture(final BufferedImage image,
            final Graphics2D g2, final Color backColor) {
        // Fill screen to black
        final Rectangle rect = new Rectangle(0, 0, image.getWidth(),
                image.getHeight());

        // Draw black
        g2.setColor(backColor);

        g2.fill(rect);
    }

    /**
     * Create status bar with inventory.
     */
    protected final void createStatusBar() {
        this.statusBar = new StatusBar(this.pictureCache, this.screenType);

        messageDispatcher.addHandler(EnumMessageType.MESSAGE_STATUS_BAR,
                statusBar);

        screenWidthBlock =
                (this.statusBar.getGameAreaConf().getWidth()
                        / JillConst.getBlockSize()) + 1;
        screenHeightBlock =
                (this.statusBar.getGameAreaConf().getHeight()
                        / JillConst.getBlockSize()) + 1;

        messageDispatcher.addHandler(EnumMessageType.BACKGROUND,
                this);
    }

    /**
     * Load current level.
     *
     * @throws IOException                  if error
     */
    protected final void loadLevel() throws IOException {
        final String filePath =
                ((JillGameConfig) SimpleGameConfig.getInstance()).getFilePath();

        screenType = ((JillGameConfig)
                SimpleGameConfig.getInstance()).getTypeScreen();

        pictureCache = ObjectInstanceFactory.getNewTileMng();

        pictureCache.init(
                new File(filePath, this.levelConfiguration.getShaFileName()),
                new File(filePath, this.levelConfiguration.getDmaFileName()),
                screenType);

        dmaFile = pictureCache.getDmaFile();

        if (this.levelConfiguration.getLevelData().isPresent()) {
            // In case of restore map level
            jnFile = ObjectInstanceFactory.getNewJn();
            jnFile.load(this.levelConfiguration.getLevelData().get());
        } else {
            jnFile = getJnFile(this.levelConfiguration.getJnFileName().get(),
                    filePath);
        }

        vclFile = getVclFile(this.levelConfiguration.getVclFileName(),
                filePath);

        cfgFile = getCfgFile(this.levelConfiguration.getCfgFileName(),
                filePath, this.levelConfiguration.getCfgSavePrefixe());
    }

    /**
     * Create background.
     */
    protected final void createBackgound() {
        // Buffer image
        background =
                new BufferedImage(
                        BackgroundLayer.MAP_WIDTH * JillConst.getBlockSize(),
                        BackgroundLayer.MAP_HEIGHT * JillConst.getBlockSize(),
                        BufferedImage.TYPE_INT_ARGB);
        // Graphic
        g2Background = background.createGraphics();

        fillPicture(background, g2Background,
                pictureCache.getBackgroundColor());

        // Create array
        backgroundObject = new BackgroundEntity[BackgroundLayer.MAP_WIDTH]
                [BackgroundLayer.MAP_HEIGHT];
    }

    /**
     * Draw portion of background at the first time.
     *
     * @param startX block start
     * @param startY block start
     * @param width  block end
     * @param height block end
     */
    protected final void initBackgroundPicture(final int startX,
            final int startY, final int width, final int height) {
        // Background map
        final BackgroundLayer backgroundLayer = jnFile.getBackgroundLayer();

        final int endX = (startX + width);
        final int endY = (startY + height);

        // Background paramter
        BackgroundParam backParam;

        // Map code
        int mapCode;
        // Tile picture
        BufferedImage tilePicture;
        // Current background object
        BackgroundEntity backObj;
        // Dma entry
        Optional<DmaEntry> dmaEntry;
        DmaEntry de;

        for (int indexX = startX; indexX < endX; indexX++) {
            for (int indexY = startY; indexY < endY; indexY++) {
                mapCode = backgroundLayer.getMapCode(indexX, indexY);
                dmaEntry = dmaFile.getDmaEntry(mapCode);

                if (dmaEntry.isPresent()) {
                    de = dmaEntry.get();
                } else {
                    LOGGER.log(Level.SEVERE,
                            String.format(
                                    "DmaEntry '%d' not found at %d/%d", mapCode,
                                    indexX, indexY));

                    // Stange bug in map !
                    de = dmaFile.getDmaEntry(0).get();
                }

                backParam = ObjectInstanceFactory.getNewBackParam();
                backParam.init(this.backgroundObject, this.pictureCache,
                        this.messageDispatcher);
                backParam.setDmaEntry(de);
                backParam.setX(indexX);
                backParam.setY(indexY);

                backObj = backgroundManager.getJillBackground(backParam);

                backgroundObject[indexX][indexY] = backObj;
            }
        }

        for (int indexX = startX; indexX < endX; indexX++) {
            for (int indexY = startY; indexY < endY; indexY++) {
                backObj = backgroundObject[indexX][indexY];

// For debug only
//if (backObj.isMsgDraw() && (backObj instanceof StdBackgroundEntity)) {
//    System.out.println(
//            String.format("Do a special draw for %d/%d (%d/%d) = %d (%s)",
//                    indexX, indexY, indexX * JillConst.BLOCK_SIZE,
//                    indexY * JillConst.BLOCK_SIZE,
//                    backObj.getMapCode(), backObj.getName()));
//}
//
//if (backObj.isMsgUpdate() && (backObj instanceof StdBackgroundEntity)) {
//    System.out.println(
//            String.format("Do a special update for %d/%d = %d (%s)",
//                    indexX, indexY, backObj.getMapCode(), backObj.getName()));
//}

                backObj.msgDraw();

                tilePicture = backObj.getPicture();

                g2Background.drawImage(tilePicture,
                        indexX * JillConst.getBlockSize(),
                        indexY * JillConst.getBlockSize(), null);
            }
        }
    }

    /**
     * Draw background all map at the first time.
     */
    protected final void initBackgroundPicture() {
        initBackgroundPicture(0, 0, BackgroundLayer.MAP_WIDTH,
                BackgroundLayer.MAP_HEIGHT);
    }

    /**
     * Draw one tile on picture.
     *
     * @param tileSetIndex tile set index
     * @param tileIndex    tile index
     * @param x            x
     * @param y            y
     * @param g2           graphic 2d object
     */
    protected final void drawOneTile(final int tileSetIndex,
            final int tileIndex, final int x, final int y,
            final Graphics2D g2) {
        // Left upper corner
        final BufferedImage tilePicture = pictureCache.getImage(
                tileSetIndex, tileIndex).get();
        g2.drawImage(tilePicture, x, y, null);
    }

    @Override
    public void recieveMessage(final EnumMessageType type, final Object msg) {
        if (msg instanceof BackgroundMessage) {
            manageOneMessage((BackgroundMessage) msg);
        } else if (msg instanceof List) {
            ((List) msg).forEach(item -> {
                if (item instanceof BackgroundMessage) {
                    manageOneMessage((BackgroundMessage) item);
                }
            });
        }
    }

    /**
     * Manage one message of background.
     *
     * @param backMsg background message
     */
    private void manageOneMessage(final BackgroundMessage backMsg) {
        // Map code comme from message
        int mapCode = backMsg.getMapCode();
        int indexX = backMsg.getX();
        int indexY = backMsg.getY();
        String mapName = backMsg.getMapName();

        // Background paramter
        BackgroundParam backParam;

        // Tile picture
        BufferedImage tilePicture;
        // Current background object
        BackgroundEntity backObj;
        // Dma entry
        Optional<DmaEntry> dmaEntry;
        DmaEntry de;

        if (mapName == null) {
            dmaEntry = dmaFile.getDmaEntry(mapCode);
        } else {
            dmaEntry = dmaFile.getDmaEntry(mapName);
        }

        if (dmaEntry.isPresent()) {
            de = dmaEntry.get();
        } else {
            LOGGER.log(Level.SEVERE,
                    String.format(
                            "DmaEntry '%d' not found at %d/%d", mapCode,
                            indexX, indexY));

            // Strange bug in map !
            de = dmaFile.getDmaEntry(0).get();
        }

        backParam = ObjectInstanceFactory.getNewBackParam();
        backParam.init(this.backgroundObject, this.pictureCache,
                this.messageDispatcher);
        backParam.setDmaEntry(de);
        backParam.setX(indexX);
        backParam.setY(indexY);

        backObj = backgroundManager.getJillBackground(backParam);

        backgroundObject[indexX][indexY] = backObj;

        tilePicture = backObj.getPicture();

        // Position of block in background picture
        final int absoluteX = indexX * JillConst.getBlockSize();
        final int absoluteY = indexY * JillConst.getBlockSize();

        // Fill screen to black
        final Rectangle rect = new Rectangle(absoluteX, absoluteY,
                tilePicture.getWidth(),
                tilePicture.getHeight());

        // Draw black
        g2Background.setColor(pictureCache.getBackgroundColor());

        // Clear background
        g2Background.fill(rect);

        // Draw background
        g2Background.drawImage(tilePicture, absoluteX, absoluteY, null);
    }
}
