/**
 *
 */
package org.jill.jn.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.jill.jn.BackgroundLayer;
import org.jill.dma.DmaFile;
import org.jill.jn.JnFile;
import org.jill.jn.ObjectItem;
import org.jill.jn.draw.cache.NameObjectCache;
import org.jill.jn.draw.cache.PictureCache;
import org.jill.sha.CgaColorMapImpl;
import org.jill.sha.ColorMap;
import org.jill.sha.EgaColorMapImpl;
import org.jill.sha.ShaFile;
import org.jill.sha.VgaColorMapImpl;

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
    private static final int BLOCK_SIZE = 16 ;

    /**
     * File format to export
     */
    private static final String FILE_EXPORT_FORMAT = "png" ;

    private Color backgroundColor = Color.BLACK ;

    /**
     * Cache of picture
     */
    private PictureCache pictureCache ;

    /**
     * Cache between object type and name/description
     */
    private NameObjectCache namdeObjectCache = new NameObjectCache() ;

    public DrawFile(final ShaFile shaFile,
            final DmaFile dmaFile, final ScreenType screen)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        pictureCache = new PictureCache(shaFile, dmaFile, screen) ;

        Color[] colorMap ;

        if (ScreenType.CGA == screen)
        {
            colorMap = CGA_COLOR_MAP.getColorMap() ;
        }
        else if (ScreenType.EGA == screen)
        {
            colorMap = EGA_COLOR_MAP.getColorMap() ;
        }
        else
        {
            colorMap = VGA_COLOR_MAP.getColorMap() ;
        }

        // Recreate color but without alpha chanel
        backgroundColor = new Color(colorMap[0].getRGB()) ;
    }

    /**
     * REturn ???? if null
     *
     * @param text
     * @return
     */
    private static String nullOrWhat(final String text)
    {
        if (text == null)
        {
            return "????" ;
        }

        return text ;
    }

    /**
     * Fill picture in black
     * @param image
     * @param g2
     */
    private void fillPictureBlack(final BufferedImage image, final Graphics2D g2)
    {
        // Fill screen to black
        final Rectangle rect = new Rectangle(0, 0, image.getWidth(), image.getHeight()) ;

        // Draw black
        g2.setColor(backgroundColor) ;

        g2.fill(rect) ;
    }

    /**
     * Draw dashed rectangle for unknow object type
     *
     * @param g2 graphic 2d
     * @param x x to start
     * @param y y to start
     * @param width width of square
     * @param height height of square
     * @param str label to draw
     */
    private static void drawDashedRectFilled(final Graphics2D g2, final int x, final int y, final int width, final int height, final String str)
    {
        Color oldColor = g2.getColor() ;
        g2.setColor(Color.CYAN) ;

        Rectangle rect = new Rectangle(x, y, width, height) ;
        float[] dash = { 5F, 5F } ;
        Stroke dashedStroke = new BasicStroke( 2F, BasicStroke.CAP_SQUARE,
        BasicStroke.JOIN_MITER, 3F, dash, 0F );
        g2.fill( dashedStroke.createStrokedShape( rect ) );

        g2.drawString(str, x - BLOCK_SIZE, y) ;

        g2.setColor(oldColor) ;
    }

    /**
     * Return tile
     *
     * @param object
     * @return
     */
    private BufferedImage getTile(final ObjectItem object)
    {
        final BufferedImage image = pictureCache.getObjectPicture(object) ;

        if (image == null)
        {
            System.out.println(
                    String.format("WARNING : Object type %d (%s) is ignored at %d/%d",
                            new Object[] {
                                object.getType(),
                                nullOrWhat(namdeObjectCache.getDescription(object.getType())),
                                object.getX(),
                                object.getY()
                                })) ;
        }

        return image ;
    }

    /**
     * Create picture
     *
     * @return new picture
     */
    public BufferedImage createPicture()
    {
        // Buffer image
        final BufferedImage image =
            new BufferedImage(BackgroundLayer.MAP_WIDTH * BLOCK_SIZE, BackgroundLayer.MAP_HEIGHT * BLOCK_SIZE,
                    BufferedImage.TYPE_INT_ARGB) ;
        // Graphic
        final Graphics2D g2 = image.createGraphics() ;

        fillPictureBlack(image, g2) ;

        g2.dispose() ;

        return image ;
    }

    /**
     * Write background
     *
     * @param g2
     * @param jnFile
     */
    public void writeBackground(final Graphics2D g2, final JnFile jnFile)
    {
        // Background map
        final BackgroundLayer background = jnFile.getBackgroundLayer() ;

        // Map code
        int mapCode ;
        // Tile picture
        BufferedImage tilePicture ;

        System.out.println("Starting write background in piture") ;

        for(int indexX = 0; indexX < BackgroundLayer.MAP_WIDTH; indexX++)
        {
            for(int indexY = 0; indexY < BackgroundLayer.MAP_HEIGHT; indexY++)
            {
                mapCode = background.getMapCode(indexX, indexY) ;

                tilePicture = pictureCache.getBackgroundPicture(mapCode) ;

                g2.drawImage(tilePicture, indexX * BLOCK_SIZE, indexY * BLOCK_SIZE, null) ;
            }
        }

        System.out.println("Done") ;
    }

    /**
     * Write background
     *
     * @param g2
     * @param jnFile
     * @param drawUnknowObject
     */
    public void writeObject(final Graphics2D g2, final JnFile jnFile, final boolean drawUnknowObject)
    {
        // Background map
        final List<ObjectItem> objectList = jnFile.getObjectLayer() ;

        // Tile picture
        BufferedImage tilePicture ;

        System.out.println("Starting write object in piture") ;

        for(ObjectItem object : objectList)
        {
            tilePicture = getTile(object) ;

            if ((tilePicture == null) && drawUnknowObject)
            {
                drawDashedRectFilled(g2, object.getX(), object.getY(), object.getWidth(),
                        object.getHeight(), String.valueOf(object.getType())) ;
            }
            else if (tilePicture != null)
            {
                g2.drawImage(tilePicture, object.getX(), object.getY(), null) ;
            }
        }

        System.out.println("Done") ;
    }

    /**
     * Write file to disk
     *
     * @param bi picture
     * @param tileFileName filename
     *
     * @throws IOException
     */
    public void writeFile(final BufferedImage bi, final String tileFileName) throws IOException
    {
        File outputfile ;

        outputfile = new File(tileFileName) ;
        ImageIO.write(bi, FILE_EXPORT_FORMAT, outputfile) ;
    }
}
