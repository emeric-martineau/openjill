/*
  Jill of the Jungle tool.
 */
package org.jill.sha.export;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class to export picture
 *
 * @author emeric martineau
 * @version 1.0
 */
public class ShaFileExportTool {

    /**
     * Constructor
     */
    private ShaFileExportTool() {
    }
    
    /**
     * Export picture.
     * 
     * @param fileToRead file sha to read
     * @param dirToSave directory where save picture
     *
     * @throws IOException if can't read file
     */
    public static void exportPictureFromShaFile(final String fileToRead, final String dirToSave) throws IOException
    {
        final ShaFile shaFile = new ShaFile(fileToRead) ;
        final ShaTileSet[] shaTileset = shaFile.getShaTileSet() ;
        ShaTileSet currentTileset ;
        ShaTile[] shaTile ;
        BufferedImage bi ;
        File outputfile ;
        final String fileName = dirToSave.concat("/tileset_%d_tile_%d_%s.png") ;
        
        for (ShaTileSet shaTileset1 : shaTileset) {
            currentTileset = shaTileset1;
            shaTile = currentTileset.getShaTile() ;
            if (currentTileset.getBitColor() == 8) {
                // Only VGA
                for (int indexTile = 0; indexTile < shaTile.length; indexTile++) {
                    bi = shaTile[indexTile].getPictureVga() ;
                    outputfile = new File(String.format(fileName, new Object[]{Integer.valueOf(currentTileset.getTitleSetIndex()), indexTile, "vga"}));
                    ImageIO.write(bi, "png", outputfile);
                }
            }
            /*
            // If picture is 8 bits color, there not display in CGA
            if (currentTileset.getBitColor() != 8)
            {
            for(int indexTile = 0; indexTile < shaTile.length; indexTile++)
            {
            bi = shaTile[indexTile].getPictureCga() ;
            System.out.println(
            String.format("Index tileset : %d Index tile : %d Offset : %04X Size : %d",
            new Object[] {
            Integer.valueOf(currentTileset.getTitleSetIndex()),
            Integer.valueOf(indexTile),
            Integer.valueOf(shaTile[indexTile].getOffset()),
            Integer.valueOf(shaTile[indexTile].getSize())
            })
            );
            fileName = String.format("/tmp/tileset%d_tile%d.png",
            new Object[] {Integer.valueOf(currentTileset.getTitleSetIndex()), Integer.valueOf(indexTile)}) ;
            outputfile = new File(fileName) ;
            ImageIO.write(bi, "png", outputfile);
            }
            }*/
        }        
    }

}
