package org.jill.sha;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.imageio.ImageIO;
import junit.framework.TestCase;
import org.jill.file.FileAbstractByte;
import org.jill.file.FileAbstractByteImpl;

/**
 * Unit test for simple App.
 */
public class AppTest
    extends TestCase
{
    private String homePath ;

    private String tempPath ;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName ) throws IOException
    {
        super( testName );

        final Properties prop = new Properties();

        prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties")) ;
        homePath = prop.getProperty("home") ;
        tempPath = prop.getProperty("temp") ;
    }

    /**
     * Rigourous Test :-)
     */
    public void testHeader() throws IOException
    {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(homePath + "JILL1.SHA");
        final ShaFile shaFile = new ShaFileImpl();
        shaFile.load(f);
        final ShaHeader shaHeader = shaFile.getShaHeader() ;
        final int[] offset = shaHeader.getOffsetTileSet() ;
        final int[] size = shaHeader.getSizeTileSet() ;

        System.out.println("+-----------------+---------------+--------+") ;
        System.out.println("| Tileset offset  | Tileset size  | Valid? |") ;
        System.out.println("+-----------------+---------------+--------+") ;

        for(int index = 0; index < ShaHeaderImpl.ENTRY_NUMBER; index++)
        {
            System.out.println(
                String.format("|     %08X    |      %04X     |  %5b |",
                    new Object[] {
                        offset[index], size[index],shaHeader.isValideEntry(index)})
                        ) ;
            System.out.println("+-----------------+---------------+--------+") ;
        }
    }

    /**
     * Rigourous Test :-)
     */
    public void testTileSet() throws IOException
    {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(homePath + "JILL1.SHA");
        final ShaFile shaFile = new ShaFileImpl();
        shaFile.load(f);

        final ShaTileSet[] shaTileset = shaFile.getShaTileSet() ;
        ShaTileSet currentTileset ;

        System.out.println("+-------+----------+------+----------+----------+----------+----------+----------+-------+---------+") ;
        System.out.println("| Index |  Offset  | Size | Nb pict  | Cga size | Ega size | Vga size | bt color | Font  | Picutre |") ;
        System.out.println("+-------+----------+------+----------+----------+----------+----------+----------+-------+---------+") ;

        for (ShaTileSet shaTileset1 : shaTileset) {
            currentTileset = shaTileset1;
            System.out.println(String.format("|  %3d  | %08X | %04X |  %5d   |  %5d   |  %5d   |   %5d  |    %3d   | %5b |  %5b  |", new Object[]{currentTileset.getTitleSetIndex(), currentTileset.getOffset(), currentTileset.getSize(), currentTileset.getNumberTile(), currentTileset.getCgaSize(), currentTileset.getEgaSize(), currentTileset.getVgaSize(), currentTileset.getBitColor(), currentTileset.isFont(), currentTileset.isTileset()}));
            System.out.println("+-------+----------+------+----------+----------+----------+----------+----------+-------+---------+") ;
        }

        System.out.println("Cga/Ega/Vga size is size in video memory after decompress") ;
        System.out.println("Nb color is bit depth of colour map");
    }

    /**
     * Rigourous Test :-)
     */
    public void testSaveFont() throws IOException
    {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(homePath + "JILL1.SHA");
        final ShaFile shaFile = new ShaFileImpl();
        shaFile.load(f);
        final ShaTileSet[] shaTileset = shaFile.getShaTileSet() ;
        ShaTileSet currentTileset ;
        ShaTile[] shaTile ;
        BufferedImage bi ;
        File outputfile ;
        String fileName ;

        for (ShaTileSet shaTileset1 : shaTileset) {
            currentTileset = shaTileset1;
            // Font only
            if (currentTileset.isFont()) {
                shaTile = currentTileset.getShaTile() ;
                for (int indexTile = 0; indexTile < shaTile.length; indexTile++) {
                    bi = shaTile[indexTile].getPictureVga() ;
                    fileName = String.format("%stileset%d_tile%d.png", tempPath, currentTileset.getTitleSetIndex(), indexTile);
                    outputfile = new File(fileName) ;
                    ImageIO.write(bi, "png", outputfile);
                }
            }
        }
    }

    /**
     * Rigourous Test :-)
     */
    public void testSaveTileWith256Colors() throws IOException
    {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(homePath + "JILL1.SHA");
        final ShaFile shaFile = new ShaFileImpl();
        shaFile.load(f);
        final ShaTileSet[] shaTileset = shaFile.getShaTileSet() ;
        ShaTileSet currentTileset ;
        ShaTile[] shaTile ;

        for (ShaTileSet shaTileset1 : shaTileset) {
            currentTileset = shaTileset1;
            // Font only
            if (currentTileset.isTileset() && (currentTileset.getBitColor() == 8)) {
                shaTile = currentTileset.getShaTile() ;
                displayShaTileInformation(currentTileset, shaTile);
            }
        }
    }

    /**
     * Rigourous Test :-)
     */
    public void testSaveTileWithxColors() throws IOException
    {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(homePath + "JILL1.SHA");
        final ShaFile shaFile = new ShaFileImpl();
        shaFile.load(f);
        final ShaTileSet[] shaTileset = shaFile.getShaTileSet() ;
        ShaTileSet currentTileset ;
        ShaTile[] shaTile ;

        for (ShaTileSet shaTileset1 : shaTileset) {
            currentTileset = shaTileset1;
            // Font only
            if (currentTileset.isTileset() && (currentTileset.getBitColor() != 8)) {
                shaTile = currentTileset.getShaTile() ;
                displayShaTileInformation(currentTileset, shaTile);
            }
        }
    }

    private void displayShaTileInformation(final ShaTileSet currentTileset, final ShaTile[] shaTile) throws IOException {
        BufferedImage bi;
        String fileName;
        File outputfile;

        for (int indexTile = 0; indexTile < shaTile.length; indexTile++) {
            bi = shaTile[indexTile].getPictureVga() ;
            System.out.println(String.format("Offset : %04X Size : %d", new Object[]{shaTile[indexTile].getOffset(), shaTile[indexTile].getSize()}));
            fileName = String.format("%stileset%d_tile%d.png", tempPath, currentTileset.getTitleSetIndex(), indexTile);
            outputfile = new File(fileName) ;
            ImageIO.write(bi, "png", outputfile);
        }
    }

    public void testDisplayDefaultCgaColorMap()
    {
        int red, green, blue ;

        for (int i = 0; i < 16; i++)
        {
            red   = ((i & 4) != 0) ? (((i & 8) != 0) ? 0xFF : 0xAA) : (((i & 8) != 0) ? 0x55 : 0x00);
            green = ((i & 2) != 0) ? (((i & 8) != 0) ? 0xFF : 0xAA) : (((i & 8) != 0) ? 0x55 : 0x00);
            blue  = ((i & 1) != 0) ? (((i & 8) != 0) ? 0xFF : 0xAA) : (((i & 8) != 0) ? 0x55 : 0x00);

            if (i == 6) {
                green = 0x55;
            }

            System.out.println(
                String.format("%02X%02X%02X",
                    new Object[] {
                        red, green, blue})
                );
        }
    }

    public void testDisplayDefaultVgaColorMap()
    {
        testDisplayDefaultCgaColorMap() ;

        System.out.println("000000") ;
        System.out.println("151515") ;
        System.out.println("222222") ;
        System.out.println("2E2E2E") ;
        System.out.println("3B3B3B") ;
        System.out.println("444444") ;
        System.out.println("555555") ;
        System.out.println("666666") ;
        System.out.println("777777") ;
        System.out.println("888888") ;
        System.out.println("999999") ;
        System.out.println("AAAAAA") ;
        System.out.println("BFBFBF") ;
        System.out.println("CCCCCC") ;
        System.out.println("EEEEEE") ;
        System.out.println("FFFFFF") ;

        // Next lot of 72 colours is repeated three times at different intensities
        // Doesn't seem to be any way to calculate these either
        int block[] = {
            0x00, 0x00, 0x3F,
            0x10, 0x00, 0x3F,
            0x1F, 0x00, 0x3F,
            0x2F, 0x00, 0x3F,
            0x3F, 0x00, 0x3F,
            0x3F, 0x00, 0x2F,
            0x3F, 0x00, 0x1F,
            0x3F, 0x00, 0x10,
            0x3F, 0x00, 0x00,
            0x3F, 0x10, 0x00,
            0x3F, 0x1F, 0x00,
            0x3F, 0x2F, 0x00,
            0x3F, 0x3F, 0x00,
            0x2F, 0x3F, 0x00,
            0x1F, 0x3F, 0x00,
            0x10, 0x3F, 0x00,
            0x00, 0x3F, 0x00,
            0x00, 0x3F, 0x10,
            0x00, 0x3F, 0x1F,
            0x00, 0x3F, 0x2F,
            0x00, 0x3F, 0x3F,
            0x00, 0x2F, 0x3F,
            0x00, 0x1F, 0x3F,
            0x00, 0x10, 0x3F,
            0x1F, 0x1F, 0x3F,
            0x27, 0x1F, 0x3F,
            0x2F, 0x1F, 0x3F,
            0x37, 0x1F, 0x3F,
            0x3F, 0x1F, 0x3F,
            0x3F, 0x1F, 0x37,
            0x3F, 0x1F, 0x2F,
            0x3F, 0x1F, 0x27,
            0x3F, 0x1F, 0x1F,
            0x3F, 0x27, 0x1F,
            0x3F, 0x2F, 0x1F,
            0x3F, 0x37, 0x1F,
            0x3F, 0x3F, 0x1F,
            0x37, 0x3F, 0x1F,
            0x2F, 0x3F, 0x1F,
            0x27, 0x3F, 0x1F,
            0x1F, 0x3F, 0x1F,
            0x1F, 0x3F, 0x27,
            0x1F, 0x3F, 0x2F,
            0x1F, 0x3F, 0x37,
            0x1F, 0x3F, 0x3F,
            0x1F, 0x37, 0x3F,
            0x1F, 0x2F, 0x3F,
            0x1F, 0x27, 0x3F,
            0x2D, 0x2D, 0x3F,
            0x31, 0x2D, 0x3F,
            0x36, 0x2D, 0x3F,
            0x3A, 0x2D, 0x3F,
            0x3F, 0x2D, 0x3F,
            0x3F, 0x2D, 0x3A,
            0x3F, 0x2D, 0x36,
            0x3F, 0x2D, 0x31,
            0x3F, 0x2D, 0x2D,
            0x3F, 0x31, 0x2D,
            0x3F, 0x36, 0x2D,
            0x3F, 0x3A, 0x2D,
            0x3F, 0x3F, 0x2D,
            0x3A, 0x3F, 0x2D,
            0x36, 0x3F, 0x2D,
            0x31, 0x3F, 0x2D,
            0x2D, 0x3F, 0x2D,
            0x2D, 0x3F, 0x31,
            0x2D, 0x3F, 0x36,
            0x2D, 0x3F, 0x3A,
            0x2D, 0x3F, 0x3F,
            0x2D, 0x3A, 0x3F,
            0x2D, 0x36, 0x3F,
            0x2D, 0x31, 0x3F
        };

        double multiplier = 1.0 ;

        for (int b = 0; b < 3; b++) {
            switch (b) {
                case 0: multiplier = 1.0; break;   // Normal
                case 1: multiplier = 0.46; break;  // Dim
                case 2: multiplier = 0.259; break; // Really dim
                default :
            }

            for (int base = 0; base < 72; base++) {
                int red = (int) (block[base * 3] * multiplier) ;
                int green = (int) (block[base * 3 + 1] * multiplier) ;
                int blue = (int) (block[base * 3 + 2] * multiplier) ;


                System.out.println(
                    String.format("%02X%02X%02X",
                        new Object[] {
                            (red << 2) | (red >> 2), (green << 2) | (green >> 2), (blue << 2) | (blue >> 2)})
                    );
            }
        }
    }

    /**
     * Rigourous Test :-)
     */
    public void testSaveAllTileInVGA() throws IOException
    {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(homePath + "JILL1.SHA");
        final ShaFile shaFile = new ShaFileImpl();
        shaFile.load(f);
        final ShaTileSet[] shaTileset = shaFile.getShaTileSet() ;
        ShaTileSet currentTileset ;
        ShaTile[] shaTile ;
        BufferedImage bi ;
        File outputfile ;
        String fileName ;

        for (ShaTileSet shaTileset1 : shaTileset) {
            currentTileset = shaTileset1;
            shaTile = currentTileset.getShaTile() ;
            for (int indexTile = 0; indexTile < shaTile.length; indexTile++) {
                bi = shaTile[indexTile].getPictureVga() ;
                System.out.println(String.format("Index tileset : %d Index tile : %d Offset : %04X Size : %d", new Object[]{currentTileset.getTitleSetIndex(), indexTile, shaTile[indexTile].getOffset(), shaTile[indexTile].getSize()}));
                fileName = String.format("%stileset%d_tile%d.png", tempPath, currentTileset.getTitleSetIndex(), indexTile);
                outputfile = new File(fileName) ;
                ImageIO.write(bi, "png", outputfile);
            }
        }
    }

    /**
     * Rigourous Test :-)
     */
    public void testSaveOneTile() throws IOException
    {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(homePath + "JILL1.SHA");
        final ShaFile shaFile = new ShaFileImpl();
        shaFile.load(f);
        final ShaTileSet[] shaTileset = shaFile.getShaTileSet() ;
        ShaTileSet currentTileset ;
        ShaTile[] shaTile ;
        BufferedImage bi ;
        File outputfile ;
        String fileName ;

        int indexTitleSet = 7 ;
        int indexTile = 16 ;


        currentTileset = shaTileset[indexTitleSet] ;

        shaTile = currentTileset.getShaTile() ;

        bi = shaTile[indexTile].getPictureCga() ;

        System.out.println(
            String.format("Index tileset : %d Index tile : %d Offset : %04X Size : %d",
                new Object[] {
                    currentTileset.getTitleSetIndex(), indexTile,
                    shaTile[indexTile].getOffset(),
                    shaTile[indexTile].getSize()})
            );

        fileName = String.format("%stileset%d_tile%d.png",
                tempPath,
                currentTileset.getTitleSetIndex(), indexTile) ;

        outputfile = new File(fileName) ;
        ImageIO.write(bi, "png", outputfile);

    }

    /**
     * Rigourous Test :-)
     */
    public void testSaveAllTileInEGA() throws IOException
    {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(homePath + "JILL1.SHA");
        final ShaFile shaFile = new ShaFileImpl();
        shaFile.load(f);
        final ShaTileSet[] shaTileset = shaFile.getShaTileSet() ;
        ShaTileSet currentTileset ;
        ShaTile[] shaTile ;
        BufferedImage bi ;
        File outputfile ;
        String fileName ;

        for (ShaTileSet shaTileset1 : shaTileset) {
            currentTileset = shaTileset1;
            shaTile = currentTileset.getShaTile() ;
            // If picture is 8 bits color, there not display in EGA
            if (currentTileset.getBitColor() != 8) {
                for (int indexTile = 0; indexTile < shaTile.length; indexTile++) {
                    bi = shaTile[indexTile].getPictureEga() ;
                    System.out.println(String.format("Index tileset : %d Index tile : %d Offset : %04X Size : %d", new Object[]{currentTileset.getTitleSetIndex(), indexTile, shaTile[indexTile].getOffset(), shaTile[indexTile].getSize()}));
                    fileName = String.format("%stileset%d_tile%d.png", tempPath, currentTileset.getTitleSetIndex(), indexTile);
                    outputfile = new File(fileName) ;
                    ImageIO.write(bi, "png", outputfile);
                }
            }
        }
    }

    /**
     * Rigourous Test :-)
     */
    public void testSaveAllTileInCGA() throws IOException
    {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(homePath + "JILL1.SHA");
        final ShaFile shaFile = new ShaFileImpl();
        shaFile.load(f);
        final ShaTileSet[] shaTileset = shaFile.getShaTileSet() ;
        ShaTileSet currentTileset ;
        ShaTile[] shaTile ;
        BufferedImage bi ;
        File outputfile ;
        String fileName ;

        for (ShaTileSet shaTileset1 : shaTileset) {
            currentTileset = shaTileset1;
            shaTile = currentTileset.getShaTile() ;
            // If picture is 8 bits color, there not display in CGA
            if (currentTileset.getBitColor() != 8) {
                for (int indexTile = 0; indexTile < shaTile.length; indexTile++) {
                    bi = shaTile[indexTile].getPictureCga() ;
                    System.out.println(String.format("Index tileset : %d Index tile : %d Offset : %04X Size : %d", new Object[]{currentTileset.getTitleSetIndex(), indexTile, shaTile[indexTile].getOffset(), shaTile[indexTile].getSize()}));
                    fileName = String.format("%stileset%d_tile%d.png", tempPath, currentTileset.getTitleSetIndex(), indexTile);
                    outputfile = new File(fileName) ;
                    ImageIO.write(bi, "png", outputfile);
                }
            }
        }
    }

    public void testBidon() {
        System.out.println(1 << 4) ;
    }
}
