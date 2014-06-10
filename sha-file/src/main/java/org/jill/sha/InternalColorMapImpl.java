package org.jill.sha;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Map colo for Jill.
 *
 * @author Emeric MARTINEAU
 */
public class InternalColorMapImpl implements ColorMap {
    /**
     * Vga color map.
     */
    public static final InternalColorMapImpl VGA_COLOR_MAP =
            new InternalColorMapImpl("/jill_color_map.properties");

    /**
     * Ega color map.
     */
    public static final InternalColorMapImpl EGA_COLOR_MAP =
            new InternalColorMapImpl("/jill_color_map.properties");

    /**
     * Cga color map.
     */
    public static final InternalColorMapImpl CGA_COLOR_MAP =
            new InternalColorMapImpl("/cga_jill_color_map.properties");

    /**
     * Max color in game screen.
     */
    private static final int MAX_COLOR_ENRTY = 256;

    /**
     * Hexa base.
     */
    private static final int HEXA_BASE = 16;

    /**
     * Color map.
     */
    private Color[] colorMap;

    /*
    public ColorMapImpl(final String fileName) throws IOException
    {
        readFile(new BufferedReader(new FileReader(fileName)));
    }*/

    /**
     * Constructor of class ColorMap.
     *
     * @param fileName file name of color map
     */
    public InternalColorMapImpl(final String fileName) {
        try (InputStream is = InternalColorMapImpl.class.getResourceAsStream(fileName);
                InputStreamReader isr = new InputStreamReader(is,
                        Charset.forName("ISO-8859-1"))) {
            readFile(new BufferedReader(isr));
        }
        catch (IOException e) {
            System.err.println("Error loading default color map");
        }
    }

    /**
     * Read file.
     *
     * @param br buffer reader
     *
     * @throws IOException if can't read file
     */
    private void readFile(final BufferedReader br) throws IOException {
        String ligne = br.readLine();
        List<Color> listColor = new ArrayList<>(MAX_COLOR_ENRTY);

        while (ligne != null) {
            ligne = ligne.trim();

            if (ligne.startsWith("!")) {
                // Transparency color
                listColor.add(
                        new Color(
                            Integer.parseInt(ligne.substring(1), HEXA_BASE),
                            true));
            } else if (!"".equals(ligne) && !ligne.startsWith("#")) {
                listColor.add(
                    new Color(
                        Integer.parseInt(ligne, HEXA_BASE)
                        ));
            }

            ligne = br.readLine();
        }

        br.close();

        colorMap = new Color[listColor.size()];

        listColor.toArray(colorMap);
    }

    /**
     * Accessor of colorMap.
     *
     * @return colorMap color map
     */
    public final Color[] getColorMap() {
        Color[] desArray = new Color[colorMap.length];
        System.arraycopy(colorMap, 0, desArray, 0, colorMap.length);
        return desArray;
    }
}
