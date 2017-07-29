package org.jill.sha;

import java.io.IOException;

import org.jill.file.FileAbstractByte;

/**
 * Class to read SHA file who contain picture of game.
 *
 * @author emeric martineau
 * @version 1.0
 */
public class ShaTileSetImpl implements ShaTileSet {

    /**
     * Number to obtain number of color.
     */
    private static final int MULTIPLE_FOR_BIT_COLOR = 4;

    /**
     * Num of title set.
     */
    private final int titleSetIndex;
    /**
     * Number of Tile in Tile set.
     */
    private final int numberTile;
    /**
     * Size for CGA display in video memory after decompress.
     */
    private final int cgaSize;
    /**
     * Size for EGA display in video memory after decompress.
     */
    private final int egaSize;
    /**
     * Size for VGA display in video memory after decompress.
     */
    private final int vgaSize;
    /**
     * Number of color.
     */
    private final int bitColor;
    /**
     * Type of data.
     */
    private final int flags;
    /**
     * If is font.
     */
    private final boolean font;
    /**
     * If is tileset.
     */
    private final boolean tileset;
    /**
     * Picture.
     */
    private final ShaTile[] shaTile;
    /**
     * Offset in file.
     */
    private int offset;
    /**
     * Size in file.
     */
    private int size;

    /**
     * Constructor.
     *
     * @param shaFile      file data
     * @param tsIndex      tilset index
     * @param offsetInFile offset's tile in file
     * @param sizeInFile   size's tile in file
     * @throws IOException if any error on reading data
     */
    public ShaTileSetImpl(final FileAbstractByte shaFile, final int tsIndex,
            final int offsetInFile, final int sizeInFile) throws IOException {
        this.titleSetIndex = tsIndex;
        this.offset = offsetInFile;
        this.size = sizeInFile;

        shaFile.seek(offsetInFile);

        //  Number of tiles in the tile set
        numberTile = shaFile.read8bitLE();

        // numRots  : Doesn't seem to have any use and is generally 1
        // (name taken from Xargon source)
        shaFile.read16bitLE();

        // Size for CGA display in video memory after decompress
        cgaSize = shaFile.read16bitLE();

        // Size for EGA display in video memory after decompress
        egaSize = shaFile.read16bitLE();

        // Size for VGA display in video memory after decompress
        vgaSize = shaFile.read16bitLE();

        // Bit depth of colour map
        bitColor = shaFile.read8bitLE();

        // Flags
        flags = shaFile.read16bitLE();

        font = (flags & SHM_FONTF) != 0;

        tileset = (flags & SHM_BLFLAG) != 0;

        shaTile = new ShaTileImpl[numberTile];

        // Read each picture
        readTile(shaFile);
    }

    /**
     * Read tile.
     *
     * @param shaFile file data
     * @throws IOException if error
     */
    private void readTile(final FileAbstractByte shaFile) throws IOException {
        int[] colorMap;

        if (!font && bitColor < ShaTileImpl.BIT_256_COLOR) {
            // Read map color
            // Number of color is :
            // bitColor=1 -> 4 colors
            // bitColor=2 -> 8 colors
            // bitColor=8 -> 256 colors
            colorMap = new int[(1 << bitColor) * MULTIPLE_FOR_BIT_COLOR];

            for (int indexColor = 0; indexColor < colorMap.length;
                 indexColor++) {
                colorMap[indexColor] = shaFile.read8bitLE();
            }
        } else {
            colorMap = null;
        }

        for (int index = 0; index < numberTile; index++) {
            shaTile[index] = new ShaTileImpl(shaFile, titleSetIndex,
                    index, bitColor, colorMap);
        }
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
     * Mutateur de offset.
     *
     * @param offsetInFile offset
     */
    @Override
    public final void setOffset(final int offsetInFile) {
        this.offset = offsetInFile;
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
     * Mutateur de size.
     *
     * @param sizeInFile size
     */
    @Override
    public final void setSize(final int sizeInFile) {
        this.size = sizeInFile;
    }

    /**
     * Number tile.
     *
     * @return index of this tile in file
     */
    @Override
    public final int getNumberTile() {
        return numberTile;
    }

    /**
     * Size for CGA display in video memory after decompress.
     *
     * @return cga size of picture
     */
    @Override
    public final int getCgaSize() {
        return cgaSize;
    }

    /**
     * Size for EGA display in video memory after decompress.
     *
     * @return ega size of picture
     */
    @Override
    public final int getEgaSize() {
        return egaSize;
    }

    /**
     * Size for VGA display in video memory after decompress.
     *
     * @return vga size of picture
     */
    @Override
    public final int getVgaSize() {
        return vgaSize;
    }

    /**
     * Bit color.
     *
     * @return bit color
     */
    @Override
    public final int getBitColor() {
        return bitColor;
    }

    /**
     * Flasgs.
     *
     * @return flags.
     */
    @Override
    public final int getFlags() {
        return flags;
    }

    /**
     * Font.
     *
     * @return true if tile is font
     */
    @Override
    public final boolean isFont() {
        return font;
    }

    /**
     * Tileset.
     *
     * @return true if tileset (picture)
     */
    @Override
    public final boolean isTileset() {
        return tileset;
    }

    /**
     * Accessor of shaTile.
     *
     * @return shaTile
     */
    @Override
    public final ShaTile[] getShaTile() {
        ShaTile[] desArray = new ShaTileImpl[shaTile.length];
        System.arraycopy(shaTile, 0, desArray, 0, shaTile.length);
        return desArray;
    }

    /**
     * The number of this tileset in entry.
     *
     * @return tileset index
     */
    @Override
    public final int getTitleSetIndex() {
        return titleSetIndex;
    }
}
