/*
  Jill of the Jungle tool.
 */
package org.jill.sha;

import java.io.IOException;

import org.jill.file.FileAbstractByte;
import org.jill.file.FileAbstractByteImpl;

/**
 * Class to read SHA file who contain picture of game.
 *
 * SHA file format.
 * <pre>
 *   +-------------+
 *   | header      |
 *   +-------------+
 *   | Tileset #1  |
 *   |   header    |
 *   |   color map |
 *   |   image #1  |
 *   |   image #2  |
 *   +-------------+
 *   | Tileset #2  |
 *   |   header    |
 *   |   color map |
 *   |   image #1  |
 *   |   image #2  |
 *   +-------------+
 *   </pre>
 * <h2>Sha Header at 0000h</h2>
 * <table>
 * <tr>
 * <td>UINT32LE offsets[128]</td><td>Offset of each tileset</td>
 * </tr>
 * <tr>
 * <td>UINT16LE sizes[128]</td><td>Size of each tileset<td>
 * </tr>
 * </table>
 *
 * <p style="font-style:italic">
 * Note : first entry always unsused<br />
 * Note : if offset = 0 and size = 0 is unused entry<br />
 * Note : first tileset is at byte 768, i.e. ((128 * 4) + (128 * 2))
 * </p>
 *
 * <h2>Tileset header at 3000h</h2>
 * <table>
 * <tr>
 * <td>UINT8 numShapes</td><td>Number of tiles in the tile set</td>
 * </tr>
 * <tr>
 * <td>UINT16LE numRots</td><td>Doesn't seem to have any use and is
 * generally 1 (name taken from Xargon source)</td>
 * </tr>
 * <tr>
 * <td>UINT16LE lenCGA</td><td>How many bytes of memory will be used for
 * data in the respective video mode after decompression</td>
 * </tr>
 * <tr>
 * <td>UINT16LE lenEGA</td><td>&nbsp;</td>
 * </tr>
 * <tr>
 * <td>UINT16LE lenVGA</td><td>&nbsp;</td>
 * </tr>
 * <tr>
 * <td>UINT8 numColourBits</td><td>Bit depth of colour map
 * (see below)</td>
 * </tr>
 * <tr>
 * <td style="vertical-align:text-top;">UINT16LE flags</td>
 * <td>One or more values defining how the data should be treated:<br />
 * 0x0001 = SHM_FONTF (font)<br />
 * 0x0002 = unused<br />
 * 0x0004 = SHM_BLFLAG (level tile set)<br />
 * </td>
 * </tr>
 * </table>
 *
 * <p style="font-style:italic">
 * Note : some tileset have flag to 0. In this case, tileset is picture
 * (like equals SHM_BLFLAG).
 * </p>
 *
 * <h3>Color map</h3>
 * <p>
 * If tileset is not font, or numColourBits less than 8, color map are put
 * after tileset header.<br />
 * One color map exist for all tile in same tileset.<br />
 * <br />
 * If color map exist, the data picture is not color, but the number of entry
 * in color map.<br />
 * If picture is 8 bits color, the data is the video color. This picture is
 * not display if game run in CGA or EGA mode.<br />
 * <br />
 * Each entry in color map have 4 bytes : CGA color, EGA color, VGA color
 * and a byte alwas 0. Exemple :
 * <pre>
 *   CGA EGA VGA
 *    1   1   1   0
 *    2   5   F   0
 *   </pre>
 * Number entry can be calculate with : 1 &lt;&lt; numColourBits<br />
 * Size of color map is : number entry * 4.
 * </p>
 *
 * <p style="font-style:italic">
 * Note : sometime, in color map, vga value is out of bound of color map. In
 * this case, use video color, like 8 bits color picture.
 * </p>
 *
 * <h3>Image</h3>
 *
 * Each image have this header :
 * <table>
 * <tr>
 * <td>UINT8 width</td><td>Image width in bytes/pixels</td>
 * </tr>
 * <tr>
 * <td>UINT8 height</td><td>Image height in bytes/pixels<td>
 * </tr>
 * <tr>
 * <td>UINT8 type</td><td>Data format (always 0)<td>
 * </tr>
 * </table>
 *
 * <p>
 * After, data is store. First byte is x=0, y=0, second is x=1, y=0...
 * </p>
 * <p>
 * Original documentation from :*
 * <a href="http://www.shikadi.net/moddingwiki/SHA_Format">shikadi.net</a>
 * </p>
 *
 * @author emeric martineau
 * @version 1.0
 */
public class ShaFileImpl implements ShaFile {
    /**
     * Header.
     */
    private ShaHeader shaHeader;

    /**
     * Array of Tileset.
     */
    private ShaTileSet[] shaTileSet;

    /**
     * Constructor.
     */
    public ShaFileImpl() {
        // Nothing
    }

    /**
     * Count valid Tileset.
     *
     * @param shaHeader header
     * @return count of valid tileset
     */
    private static int countTileSet(final ShaHeader shaHeader) {
        int validTileSet = 0;

        for (int index = 0; index < ShaHeader.ENTRY_NUMBER; index++) {
            if (shaHeader.isValideEntry(index)) {
                validTileSet++;
            }
        }

        return validTileSet;
    }

    /**
     * Constructor of class ShaFile.java.
     *
     * @param shaFile file data
     * @throws IOException if error
     */
    @Override
    public void load(final String shaFile) throws IOException {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(shaFile);
        load(f);
    }

    /**
     * Constructor of class ShaFile.java.
     *
     * @param shaFile file data
     * @throws IOException if error
     */
    @Override
    public void load(final FileAbstractByte shaFile) throws IOException {
        shaHeader = new ShaHeaderImpl(shaFile);

        readTileSet(shaFile);
    }

    /**
     * Read Tileset information.
     *
     * @param shaFile file data
     * @throws IOException if error
     */
    private void readTileSet(final FileAbstractByte shaFile)
            throws IOException {
        // Count valid Tileset
        int validTileSet = countTileSet(shaHeader);

        // Init array
        shaTileSet = new ShaTileSetImpl[validTileSet];

        // Get data
        final int[] offset = shaHeader.getOffsetTileSet();
        final int[] size = shaHeader.getSizeTileSet();

        // Current Tileset
        ShaTileSetImpl currentTileSet;

        // Index of Tileset
        int indexTileSet = 0;

        for (int index = 0; index < ShaHeader.ENTRY_NUMBER; index++) {
            if (shaHeader.isValideEntry(index)) {
                currentTileSet = new ShaTileSetImpl(shaFile, index,
                        offset[index], size[index]);

                shaTileSet[indexTileSet] = currentTileSet;

                indexTileSet++;
            }
        }
    }

    /**
     * Return header.
     *
     * @return header of file
     */
    @Override
    public final ShaHeader getShaHeader() {
        return shaHeader;
    }

    /**
     * Return array of Tileset.
     *
     * @return tileset of file
     */
    @Override
    public final ShaTileSet[] getShaTileSet() {
        ShaTileSet[] desArray = new ShaTileSetImpl[shaTileSet.length];
        System.arraycopy(shaTileSet, 0, desArray, 0, shaTileSet.length);
        return desArray;
    }
}
