/*
  Extract
 */
package org.jill.jn;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.jill.dma.DmaFile;
import org.jill.dma.DmaFileImpl;
import org.jill.jn.draw.DrawFile;
import org.jill.jn.draw.ScreenType;
import org.jill.jn.dump.DumpFile;
import org.jill.sha.ShaFile;
import org.jill.sha.ShaFileImpl;

/**
 * Extractor tool
 *
 * @author Emeric MARTINEAU
 */
public class JnFileExtractor {
    /**
     * automatically generate the help statement
     */
    private static final HelpFormatter FORMATTER = new HelpFormatter();

    /**
     * create Options object
     */
    private static final Options OPTIONS = new Options();

    static {
        createOptions();
    }

    public static void main(String[] args) throws ParseException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy k:m:s:S");
//
//        System.out.println(dateFormat.format( new Date() ) );

        // http://commons.apache.org/cli/usage.html
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse(OPTIONS, args);

        boolean dumpText = false;
        boolean drawMap = false;

        final ExtractParameter param = new ExtractParameter();

        // Commands
        if (!cmd.hasOption('f')) {
            // automatically generate the help statement
            FORMATTER.printHelp(JnFileExtractor.class.getCanonicalName(), OPTIONS);

            return;
        }

        if (cmd.hasOption("db") || cmd.hasOption("do") || cmd.hasOption("dsv") || cmd.hasOption("ds")) {
            param.setDumpback(cmd.hasOption("db"));
            param.setDumpobject(cmd.hasOption("do"));
            param.setDumpsave(cmd.hasOption("dsv"));
            param.setDumpstring(cmd.hasOption("ds"));

            dumpText = true;
        }

        if (cmd.hasOption("drb") || cmd.hasOption("dro")) {
            param.setDrawback(cmd.hasOption("drb"));
            param.setDrawobject(cmd.hasOption("dro"));

            drawMap = true;
        }

        param.setFile(cmd.getOptionValue('f'));
        param.setFileDma(cmd.getOptionValue("fd"));
        param.setFileSha(cmd.getOptionValue("fs"));
        param.setOutText(cmd.getOptionValue("ot"));
        param.setOutDraw(cmd.getOptionValue("od"));
        param.setDrawunknowobject(cmd.hasOption("dru"));

        if (cmd.hasOption("c") || cmd.hasOption("e") || cmd.hasOption("v")) {
            param.setCga(cmd.hasOption("c"));
            param.setEga(cmd.hasOption("e"));
            param.setVga(cmd.hasOption("v"));
        }

        if (dumpText) {
            extractDump(param);
        }

        if (drawMap) {
            if (!cmd.hasOption("fd")) {
                System.err.println("ERROR : Missing option 'file-dma'. DMA file required for draw map.");

                return;
            }

            if (!cmd.hasOption("fs")) {
                System.err.println("ERROR : Missing option 'file-sha'. SHA file required for draw map.");

                return;
            }

            extractMap(param);
        }

//        System.out.println(dateFormat.format( new Date() ) );
    }

    /**
     * Create options of command line
     */
    private static void createOptions() {
        // add t option
        OPTIONS.addOption("f", "file", true, "file to read (map) to read (*.jn?).");
        OPTIONS.addOption("fs", "file-sha", true, "file to read for get picture (*.sha).");
        OPTIONS.addOption("fd", "file-dma", true, "file to read for get map picture (*.dma).");
        OPTIONS.addOption("ot", "out-text", true, "output file write dump data.");
        OPTIONS.addOption("od", "out-draw", true, "output file write draw map.");
        OPTIONS.addOption("drb", "draw-back", false, "draw background layer.");
        OPTIONS.addOption("dro", "draw-object", false, "draw object layer.");
        OPTIONS.addOption("dru", "draw-object-unknow", false, "draw all unknow object in object layer.");
        OPTIONS.addOption("db", "dump-back", false, "dump background layer.");
        OPTIONS.addOption("do", "dump-object", false, "dump object layer.");
        OPTIONS.addOption("dsv", "dump-save", false, "dump save data layer.");
        OPTIONS.addOption("ds", "dump-string", false, "dump string stack layer.");
        OPTIONS.addOption("c", "cga", false, "draw in CGA mode.");
        OPTIONS.addOption("e", "ega", false, "draw in EGA mode.");
        OPTIONS.addOption("v", "vga", false, "draw in VGA mode.");

        OPTIONS.addOption("h", "help", false, "print this message.");
    }


    private static void extractDump(final ExtractParameter param) {
        // File to read
        JnFile jnFile = null;
        // Dump utility
        DumpFile dumpFile = null;

        try {
            dumpFile = new DumpFile(param.getOutText());

            jnFile = new JnFileImpl();
            jnFile.load(param.getFile());

            if (param.isDumpback()) {
                dumpFile.extractBackgroundInTextFile(jnFile);
            }

            if (param.isDumpobject()) {
                dumpFile.extractObjectInTextFile(jnFile);
            }

            if (param.isDumpsave()) {
                dumpFile.extractSaveDataInTextFile(jnFile);
            }

            if (param.isDumpstring()) {
                dumpFile.extractStringStackSaveDataInTextFile(jnFile);
            }
        } catch (final IOException e) {
            if (jnFile == null) {
                System.err.println(
                        "Can't open read file : ".concat(param.getFile()));
            } else {
                System.err.println(
                        "Can't create or write file : ".concat(
                                param.getOutText()));
            }
        } finally {
            if (dumpFile != null) {
                dumpFile.close();
            }
        }
    }

    /**
     * Extract map into graphical file.
     *
     * @param param {@link ExtractParameter}
     * @throws InstantiationException if error
     * @throws IllegalAccessException if error
     * @throws ClassNotFoundException if can't find class associate to item
     */
    private static void extractMap(final ExtractParameter param) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        // File to read
        JnFile jnFile;
        ShaFile shaFile = null;
        DmaFile dmaFile = null;

        try {
            ScreenType typeScreen = ScreenType.VGA;

            if (param.isCga()) {
                typeScreen = ScreenType.CGA;
            } else if (param.isEga()) {
                typeScreen = ScreenType.EGA;
            }

            jnFile = new JnFileImpl();
            jnFile.load(param.getFile());
            shaFile = new ShaFileImpl();
            shaFile.load(param.getFileSha());
            dmaFile = new DmaFileImpl();
            dmaFile.load(param.getFileDma());

            final DrawFile drawFile = new DrawFile(shaFile,
                    dmaFile, typeScreen);

            final BufferedImage image = drawFile.createPicture();

            final Graphics2D g2 = image.createGraphics();

            // Draw background
            if (param.isDrawback()) {
                drawFile.writeBackground(g2, jnFile);
            }

            // Draw object
            if (param.isDrawobject()) {
                drawFile.writeObject(g2, jnFile, param.isDrawunknowobject());
            }

            g2.dispose();

            drawFile.writeFile(image, param.getOutDraw());

        } catch (final IOException e) {
            System.err.print("Can't open read file : ");

            if (shaFile == null) {
                System.err.print(param.getFileSha());
            } else if (dmaFile == null) {
                System.err.print(param.getFileDma());
            } else {
                e.printStackTrace();
            }
        }
    }

}