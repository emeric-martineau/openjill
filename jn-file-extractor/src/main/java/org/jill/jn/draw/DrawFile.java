/*

 */
package org.jill.jn.draw;

import org.jill.dma.DmaEntry;
import org.jill.dma.DmaFile;
import org.jill.entities.manager.cache.BackgroundManagerCache;
import org.jill.entities.manager.cache.ObjectManagerCache;
import org.jill.jn.BackgroundLayer;
import org.jill.jn.JnFile;
import org.jill.jn.ObjectItem;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Class to draw picture from file
 *
 * @author Emeric MARTINEAU
 */
public class DrawFile {
    /**
     * Color map.
     */
    private static final ColorMap VGA_COLOR_MAP = new VgaColorMapImpl();

    /**
     * Color map.
     */
    private static final ColorMap EGA_COLOR_MAP = new EgaColorMapImpl();

    /**
     * Color map.
     */
    private static final ColorMap CGA_COLOR_MAP = new CgaColorMapImpl();

    /**
     * Size (width/height) for a block
     */
    private static final int BLOCK_SIZE = 16;

    /**
     * File format to export
     */
    private static final String FILE_EXPORT_FORMAT = "png";

    /**
     * Default background color.
     */
    private Color backgroundColor;

    /**
     * Dma file.
     */
    private final DmaFile dmaFile;

    /**
     * Sha file.
     */
    private final ShaFile shaFile;

    /**
     * Background manager.
     */
    private final BackgroundManagerCache bckManagerCache;

    /**
     * Object manager.
     */
    private final ObjectManagerCache objManagerCache;

    public DrawFile(final ShaFile shaFile,
            final DmaFile dmaFile, final EnumScreenType screen)
            throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Color[] colorMap;

        if (EnumScreenType.CGA == screen) {
            colorMap = CGA_COLOR_MAP.getColorMap();
        } else if (EnumScreenType.EGA == screen) {
            colorMap = EGA_COLOR_MAP.getColorMap();
        } else {
            colorMap = VGA_COLOR_MAP.getColorMap();
        }

        // Recreate color but without alpha chanel
        this.backgroundColor = new Color(colorMap[0].getRGB());

        this.dmaFile = dmaFile;
        this.shaFile = shaFile;

        this.bckManagerCache = new BackgroundManagerCache("std-openjill-background-manager.properties",
                shaFile, dmaFile, screen);

        this.objManagerCache = new ObjectManagerCache("std-openjill-object-manager.properties", shaFile,
                dmaFile, screen);
    }

    /**
     * Draw dashed rectangle for unknow object type
     *
     * @param g2     graphic 2d
     * @param x      x to start
     * @param y      y to start
     * @param width  width of square
     * @param height height of square
     * @param str    label to draw
     */
    private static void drawDashedRectFilled(final Graphics2D g2, final int x, final int y, final int width, final int height, final String str) {
        Color oldColor = g2.getColor();
        g2.setColor(Color.CYAN);

        Rectangle rect = new Rectangle(x, y, width, height);
        float[] dash = {5F, 5F};
        Stroke dashedStroke = new BasicStroke(2F, BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_MITER, 3F, dash, 0F);
        g2.fill(dashedStroke.createStrokedShape(rect));

        g2.drawString(str, x - BLOCK_SIZE, y);

        g2.setColor(oldColor);
    }

    /**
     * Fill picture in black.
     *
     * @param image picture
     * @param g2    graphic object to draw
     */
    private void fillPictureBlack(final BufferedImage image, final Graphics2D g2) {
        // Fill screen to black
        final Rectangle rect = new Rectangle(0, 0, image.getWidth(), image.getHeight());

        // Draw black
        g2.setColor(backgroundColor);

        g2.fill(rect);
    }

    /**
     * Create picture
     *
     * @return new picture
     */
    public BufferedImage createPicture() {
        // Buffer image
        final BufferedImage image =
                new BufferedImage(BackgroundLayer.MAP_WIDTH * BLOCK_SIZE, BackgroundLayer.MAP_HEIGHT * BLOCK_SIZE,
                        BufferedImage.TYPE_INT_ARGB);
        // Graphic
        final Graphics2D g2 = image.createGraphics();

        fillPictureBlack(image, g2);

        g2.dispose();

        return image;
    }

    /**
     * Write background.
     *
     * @param g2     graphic object to draw
     * @param jnFile jill map file
     */
    public void writeBackground(final Graphics2D g2, final JnFile jnFile) {
        // Background map
        final BackgroundLayer background = jnFile.getBackgroundLayer();

        System.out.println("Starting write background in picture");

        for (int x = 0; x < BackgroundLayer.MAP_WIDTH; x++) {
            for (int y = 0; y < BackgroundLayer.MAP_HEIGHT; y++) {
                final int mapCode = background.getMapCode(x, y);

                final Optional<DmaEntry> dmaEntry = this.dmaFile.getDmaEntry(mapCode);

                if (dmaEntry.isPresent()) {
                    final DmaEntry dma = dmaEntry.get();

                    final BackgroundEntity manager = this.bckManagerCache.getManager(dma.getName());

                    manager.msgDraw(background, x, y);

                    final BufferedImage tilePicture = manager.getPicture(x, y);

                    g2.drawImage(tilePicture, x * BLOCK_SIZE, y * BLOCK_SIZE, null);
                } else {
                    System.err.println(String.format("Error ! A unknow background found (id: %d).", mapCode));
                }
            }
        }

        System.out.println("Done");
    }

    /**
     * Write background
     *
     * @param g2               graphic object to draw
     * @param jnFile           jill map file
     * @param drawUnknowObject if draw outline if object unknow
     */
    public void writeObject(final Graphics2D g2, final JnFile jnFile, final boolean drawUnknowObject) {
        // Background map
        final List<ObjectItem> objectList = jnFile.getObjectLayer();

        // Tile picture
        Optional<BufferedImage> tilePicture;
        ObjectEntity manager;

        System.out.println("Starting write object in piture");

        for (ObjectItem object : objectList) {

            manager = objManagerCache.getManager(object.getType());

            if (manager == null && drawUnknowObject) {
                drawDashedRectFilled(g2, object.getX(), object.getY(), object.getWidth(),
                        object.getHeight(), String.valueOf(object.getType()));
            } else {
                tilePicture = manager.defaultPicture();

                if (tilePicture.isPresent()) {
                    g2.drawImage(tilePicture.get(), object.getX(), object.getY(), null);
                } else if (drawUnknowObject) {
                    drawDashedRectFilled(g2, object.getX(), object.getY(), object.getWidth(),
                            object.getHeight(), String.valueOf(object.getType()));
                }
            }
        }

        System.out.println("Done");
    }

    /**
     * Write file to disk
     *
     * @param bi           picture
     * @param tileFileName filename
     * @throws IOException when can't read file
     */
    public void writeFile(final BufferedImage bi, final String tileFileName) throws IOException {
        File outputfile;

        outputfile = new File(tileFileName);
        ImageIO.write(bi, FILE_EXPORT_FORMAT, outputfile);
    }
}
