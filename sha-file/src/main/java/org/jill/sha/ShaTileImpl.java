package org.jill.sha;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;

import org.jill.file.FileAbstractByte;

/**
 * Class to read SHA file who contain picture of game.
 *
 * @author emeric martineau
 * @version 1.0
 */
public class ShaTileImpl implements ShaTile {
    /**
     * 256 color bit.
     */
    static final int BIT_256_COLOR = 8;
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
     * Number byte per color.
     */
    private static final int NB_COLOR_PER_ITEM = 4;
    /**
     * Vga color.
     */
    private static final int CGA_COLOR = 0;
    /**
     * Vga color.
     */
    private static final int EGA_COLOR = 1;
    /**
     * Vga color.
     */
    private static final int VGA_COLOR = 2;
    /**
     * Num of title set.
     */
    private final int titleSetIndex;

    /**
     * Width.
     */
    private final int width;

    /**
     * Height.
     */
    private final int height;

    /**
     * Date format.
     */
    private final int dataFormat;

    /**
     * Row data.
     */
    private final int[] rawData;

    /**
     * Color map only in picture width numcolor < 8.
     */
    private final Optional<int[]> colorMap;

    /**
     * Offset of picture.
     */
    private final int offset;

    /**
     * Size of picture in file.
     */
    private final int size;

    /**
     * Image index.
     */
    private final int imageIndex;

    /**
     * Number of color.
     */
    private final int bitColor;

    /**
     * Constructor of class ShaTile.
     *
     * @param shaFile        file data
     * @param tsIndex        index of tileset
     * @param tIndex         index of tile
     * @param numberBitColor number of color
     * @param usedColorMap   map color
     * @throws IOException if error
     */
    public ShaTileImpl(final FileAbstractByte shaFile, final int tsIndex,
            final int tIndex, final int numberBitColor,
            final Optional<int[]> usedColorMap) throws IOException {
        if (usedColorMap.isPresent()) {
            final int[] currentColorMap = usedColorMap.get();

            int[] desArray = new int[currentColorMap.length];
            System.arraycopy(currentColorMap, 0, desArray, 0, currentColorMap.length);

            this.colorMap = Optional.of(desArray);
        } else {
            this.colorMap = Optional.empty();
        }

        this.imageIndex = tIndex;
        this.titleSetIndex = tsIndex;
        this.bitColor = numberBitColor;

        this.width = shaFile.read8bitLE();
        this.height = shaFile.read8bitLE();
        this.dataFormat = shaFile.read8bitLE();

        this.size = this.width * this.height;

        this.offset = shaFile.getFilePointer();

        this.rawData = new int[this.size];

        for (int index = 0; index < this.rawData.length; index++) {
            this.rawData[index] = shaFile.read8bitLE();
        }
    }


    /**
     * Get picture when tile is font.
     *
     * @param colorOffset start color in color map
     * @param mapColor    map color
     * @return picture
     */
    private BufferedImage getPicture(final int colorOffset,
            final Color[] mapColor) {
        final BufferedImage image =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = image.createGraphics();

        // Current color to draw
        Color color;
        // Color byte in picture
        int colorByte;
        // Color index for mapping
        int colorMapping;
        // Offset in map
        int offsetMap;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                colorByte = rawData[x + (y * width)];

                if (this.colorMap.isPresent()) {
                    int[] currentColorMap = this.colorMap.get();

                    offsetMap = (colorByte * NB_COLOR_PER_ITEM) + colorOffset;

                    if (offsetMap < currentColorMap.length) {
                        colorMapping = currentColorMap[offsetMap];

                        color = mapColor[colorMapping];
                    } else {
                        // Sometime, in file, value is not in map color.
                        // If there, use Jill default map color.
                        color = mapColor[colorByte];
                    }
                } else {
                    color = mapColor[colorByte];
                }

                g2.setColor(color);

                g2.drawLine(x, y, x, y);
            }
        }

        g2.dispose();

        return image;
    }

    /**
     * Get picture in VGA mode.
     *
     * @return picture
     */
    @Override
    public final BufferedImage getPictureVga() {
        return getPicture(VGA_COLOR, VGA_COLOR_MAP.getColorMap());
    }

    /**
     * Get picture in CGA mode.
     *
     * @return picture
     */
    @Override
    public final BufferedImage getPictureCga() {
        BufferedImage image;

        if (bitColor != BIT_256_COLOR) {
            image = getPicture(CGA_COLOR,
                    CGA_COLOR_MAP.getColorMap());
        } else {
            image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_ARGB);
        }

        return image;
    }

    /**
     * Get picture in EGA mode.
     *
     * @return picture
     */
    @Override
    public final BufferedImage getPictureEga() {
        BufferedImage image;
        if (bitColor != BIT_256_COLOR) {
            image = getPicture(EGA_COLOR, EGA_COLOR_MAP.getColorMap());
        } else {
            image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_ARGB);
        }

        return image;
    }

    /**
     * Draw font.
     * USE ONLY IF FONT !
     *
     * @param mapColor color map
     * @return image
     */
    @Override
    public final BufferedImage getFont(final Color[] mapColor) {
        return getPicture(CGA_COLOR, mapColor);
    }

    /**
     * Width.
     *
     * @return width
     */
    @Override
    public final int getWidth() {
        return width;
    }

    /**
     * Height.
     *
     * @return height
     */
    @Override
    public final int getHeight() {
        return height;
    }

    /**
     * Data format.
     *
     * @return data format
     */
    @Override
    public final int getDataFormat() {
        return dataFormat;
    }

    /**
     * Accesseur de offset.
     *
     * @return offset
     */
    @Override
    public final int getOffset() {
        return offset;
    }

    /**
     * Accesseur de size.
     *
     * @return size
     */
    @Override
    public final int getSize() {
        return size;
    }

    /**
     * Accesseur de titleSetIndex.
     *
     * @return titleSetIndex
     */
    @Override
    public final int getTitleSetIndex() {
        return titleSetIndex;
    }

    /**
     * Accesseur de imageIndex.
     *
     * @return imageIndex
     */
    @Override
    public final int getImageIndex() {
        return imageIndex;
    }
}
