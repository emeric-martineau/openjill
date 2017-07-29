/*

 */
package org.jill.jn.dump;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import org.jill.jn.BackgroundLayer;
import org.jill.jn.JnFile;
import org.jill.jn.ObjectItem;
import org.jill.jn.SaveData;
import org.jill.jn.StringItem;
import org.jill.jn.draw.cache.NameObjectCache;

/**
 * Class to dump information from file
 *
 * @author Emeric MARTINEAU
 */
public class DumpFile {
    /**
     * Cache between object type and name/description
     */
    private final NameObjectCache namdeObjectCache = new NameObjectCache() ;

    /**
     * Stream where print
     */
    private PrintStream  out ;

    /**
     * Open file or text console
     *
     * @param filenameOut file where write dump information
     *
     * @return PrintStream where print (console or file)
     *
     * @throws FileNotFoundException if open file
     */
    private static PrintStream openTextFile(final String filenameOut)
            throws FileNotFoundException
    {
        PrintStream ps ;
        if (filenameOut == null)
        {
            ps = System.out ;
        }
        else
        {
            try {
                ps = new PrintStream(filenameOut,
                        Charset.defaultCharset().name()) ;
            } catch (UnsupportedEncodingException ex) {
                System.out.println("Unsupported encoding " +
                        Charset.defaultCharset().name()) ;
                ps = System.out ;
            }
        }

        return ps ;
    }

    public DumpFile(final String filenameOut) throws FileNotFoundException {
        out = openTextFile(filenameOut) ;
    }

    /**
     * Close out stream
     */
    public void close() {
        if ((out != null) && (out != System.out))
        {
            out.close() ;
        }
    }

    /**
     * Extract background information
     *
     * @param jnFile read file
     */
    public void extractBackgroundInTextFile(final JnFile jnFile)
    {
        final BackgroundLayer background = jnFile.getBackgroundLayer() ;

        out.println("Background layer :") ;
        out.println() ;

        for(int indexY = 0; indexY < BackgroundLayer.MAP_HEIGHT; indexY++)
        {

               for(int indexX = 0; indexX < BackgroundLayer.MAP_WIDTH; indexX++)
            {
                out.print(
                        String.format("%04X ", background.getMapCode(indexX, indexY))) ;
            }

            out.println() ;
        }

        out.println() ;
    }

    /**
     * Print properties of object
     *
     * @param name name of properties
     * @param value value of properties
     */
    private void printProperties(final String name, final String value)
    {
        out.print("    ") ;
        out.print(name) ;
        out.print(" = ") ;
        out.println(value) ;
    }

    /**
     * Extract object information
     *
     * @param jnFile read file
     */
    public void extractObjectInTextFile(final JnFile jnFile)
    {
        final List<ObjectItem> objectLayer = jnFile.getObjectLayer() ;
        final int nbObject = objectLayer.size() ;

        ObjectItem currentObject ;
        String typeDescription ;

        out.println("Object layer (" + String.valueOf(nbObject) + ") :") ;
        out.println() ;

        try {
            for (ObjectItem anObjectLayer : objectLayer) {
                currentObject = anObjectLayer;

                out.println(
                        String.format("Object #%d (0x%04X)",
                                new Object[]{
                                        currentObject.getIndex(),
                                        currentObject.getOffset()}
                        ));

                typeDescription = namdeObjectCache.getDescription(currentObject.getType());

                if (typeDescription == null) {
                    printProperties("type", String.valueOf(currentObject.getType()) + " (????)");
                } else {
                    printProperties("type", String.valueOf(currentObject.getType()) + " (" + typeDescription + ")");
                }

                printProperties("x", String.valueOf(currentObject.getX()));
                printProperties("y", String.valueOf(currentObject.getY()));

                printProperties("width", String.valueOf(currentObject.getWidth()));
                printProperties("height", String.valueOf(currentObject.getHeight()));

                printProperties("xSpeed", String.valueOf(currentObject.getxSpeed()));
                printProperties("ySpeed", String.valueOf(currentObject.getySpeed()));

                printProperties("state", String.valueOf(currentObject.getState()));
                printProperties("subState", String.valueOf(currentObject.getSubState()));
                printProperties("stateCount", String.valueOf(currentObject.getStateCount()));

                printProperties("counter", String.valueOf(currentObject.getCounter()));
                printProperties("flags", String.valueOf(currentObject.getFlags()));

                printProperties("info1", String.valueOf(currentObject.getInfo1()));
                printProperties("zaphold", String.valueOf(currentObject.getZapHold()));

                if (currentObject.getPointer() == 0) {
                    printProperties("pointer", String.valueOf(currentObject.getPointer()));
                } else {
                    out.print("    ");
                    out.print("pointer");
                    out.print(" = ");
                    out.print(String.valueOf(currentObject.getPointer()));
                    out.print(" to '");
                    out.print(String.valueOf(currentObject.getStringStackEntry()));
                    out.println("'");
                }
            }

            out.println() ;
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extract save data information
     *
     * @param jnFile read file
     */
    public void extractSaveDataInTextFile(final JnFile jnFile)
    {
        final SaveData saveData = jnFile.getSaveData() ;

        out.println(
                String.format("Save data layer : (0x%04X)", saveData.getOffset())) ;
        out.println() ;

        final int level = saveData.getLevel() ;

        if (level == SaveData.MAP_LEVEL)
        {
            out.println("Level : MAP") ;
        }
        else
        {
            out.println(
                String.format("Level : %d", level)) ;
        }

        out.println(
            String.format("Health : %d", saveData.getHealth())) ;

        out.println(
                String.format("Score : %d", saveData.getScore())) ;

        final List<Integer> inventory = saveData.getInventory() ;

        out.println(
                String.format("Inventory (%d)", inventory.size())) ;

        for(int index = 0; index < inventory.size(); index++)
        {
            out.print("    ");
            out.println(
                    String.format("#%d : %d",
                            new Object[] {
                                index,
                                inventory.get(index) }
                    )) ;
        }

        out.println() ;
    }

    /**
     * Extract string stack information
     *
     * @param jnFile read file
     */
    public void extractStringStackSaveDataInTextFile(final JnFile jnFile)
    {
        final List<StringItem> stringStack = jnFile.getStringStack() ;
        final Iterator<StringItem> it = stringStack.iterator() ;
        StringItem string ;

        out.println("String stack :") ;
        out.println() ;

        while(it.hasNext())
        {
            string = it.next() ;

            out.println(
                String.format("    0x%04X : '%s'",
                        new Object[] { string.getOffset(),
                        string.getValue() }
                )) ;
        }
        out.println() ;
    }
}
