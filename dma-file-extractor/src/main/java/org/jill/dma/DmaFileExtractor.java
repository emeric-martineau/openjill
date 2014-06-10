/**
 * Extract
 */
package org.jill.dma;

import java.io.IOException;
import java.util.Iterator;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.jill.file.FileAbstractByte;
import org.jill.file.FileAbstractByteImpl;

/**
 * Extractor tool
 *
 * @author Emeric MARTINEAU
 */
public class DmaFileExtractor {
    /**
     * automatically generate the help statement
     */
    private static final HelpFormatter formatter = new HelpFormatter() ;

    /**
     * create Options object
     */
    private static final Options options = new Options() ;

    static
    {
        createOptions() ;
    }

    public static void main(String[] args) throws ParseException, IOException
    {
        // http://commons.apache.org/cli/usage.html
        CommandLineParser parser = new PosixParser() ;
        CommandLine cmd = parser.parse(options, args) ;

        // Commands
        if (!cmd.hasOption('f'))
        {
            // automatically generate the help statement
            formatter.printHelp(DmaFileExtractor.class.getCanonicalName(),
                    options) ;
        }
        else
        {
            extract(cmd.getOptionValue('f')) ;
        }

    }

    /**
     * Create options of command line
     */
    private static void createOptions()
    {
        // add t option
        options.addOption("f", "file", true, "file to read") ;
        options.addOption("h", "help", false, "print this message") ;
    }


    /**
     * Ectract font or picture
     *
     * @param param parameter
     * @throws IOException
     */
    private static void extract(final String param) throws IOException
    {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(param);

        final DmaFileImpl dmaFile = new DmaFileImpl();
        dmaFile.load(f);
        final Iterator<Integer> it = dmaFile.getDmaEntryIterator() ;
        DmaEntryImpl currentEntry ;

//        String tileFileName ;

        final DmaEntryImpl[] arrayDma = new DmaEntryImpl[dmaFile.getDmaEntryCount()] ;

        while (it.hasNext())
        {
            currentEntry = dmaFile.getDmaEntry(it.next()) ;

            arrayDma[currentEntry.getIndex()] = currentEntry ;
        }

        System.out.println("+--------+------+---------+------+----------+-----") ;
        System.out.println("| Offset |  Id  | Tileset | Tile |  Flags   | Name") ;
        System.out.println("+--------+------+---------+------+----------+-----") ;
        for(int index = 0; index < arrayDma.length; index++)
        {
            currentEntry = arrayDma[index] ;

//            tileFileName = String.format(tileFileNamePattern,
//                    new Object[] {
//                        Integer.valueOf(currentEntry.getTileset()),
//                        Integer.valueOf(currentEntry.getTile()),
//                        "vga"})  ;

            System.out.println(
                String.format("|  %04X  | %04X |   %04X  | %04X | %08X | %s",
                    new Object[] {
                        Integer.valueOf(currentEntry.getOffset()),
                        Integer.valueOf(currentEntry.getMapCode()),
                        Integer.valueOf(currentEntry.getTileset()),
                        Integer.valueOf(currentEntry.getTile()),
                        Integer.valueOf(currentEntry.getFlags()),
                        currentEntry.getName()
//                        tileFileName
                        })
                        ) ;
        }
    }
}