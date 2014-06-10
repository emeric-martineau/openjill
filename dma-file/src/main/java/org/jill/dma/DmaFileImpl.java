/**
 * Jill of the Jungle tool.
 */
package org.jill.dma;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.jill.file.FileAbstractByteImpl;
import org.jill.file.FileAbstractByte;


/**
 * Class to read DMA file who contain mapping between map and picture of game.
 *
 * @author emeric martineau
 * @version 1.0
 */
public class DmaFileImpl implements DmaFile {
    /**
     * In file, upper bit not used.
     */
    private static final int MASK_OF_TILESET = 0x3F;

    /**
     * Size of entry in dma file.
     */
    private static final int SIZE_OF_ENTRY = 7;

    /**
     * Dma entries.
     */
    private final Map<Integer, DmaEntryImpl> dmaMap = new HashMap<>();

    /**
     * Dma entries by name.
     */
    private final Map<String, DmaEntryImpl> dmaMapByName = new HashMap();

    /**
     * Constructor of class ShaFile.java.
     */
    public DmaFileImpl() {
        // Nothing
    }

    /**
     * Constructor of class ShaFile.java.
     *
     * @param dmaFile file name
     *
     * @throws IOException if error
     */
    @Override
    public void load(final String dmaFile) throws IOException {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(dmaFile);

        readDmaEntry(f);
    }

    /**
     * Constructor of class ShaFile.java.
     *
     * @param dmaFile file data
     *
     * @throws IOException if error
     */
    @Override
    public void load(final FileAbstractByte dmaFile) throws IOException {
           readDmaEntry(dmaFile);
    }

    /**
     * Read Tileset information.
     *
     * @param dmaFile dma data file
     *
     * @throws IOException if error
     */
    private void readDmaEntry(final FileAbstractByte dmaFile)
            throws IOException {
        // Get size of file
        final long lenght = dmaFile.length();
        long index = 0;
        int count = 0;

        int mapCode;
        int tile;
        int tileset;
        int flags;
        int strLen;
        int offset;
        StringBuilder name;
        DmaEntryImpl currentDmaEntry;

        while (index < lenght) {
            offset = (int) dmaFile.getFilePointer();
            mapCode = dmaFile.read16bitLE();
            tile = dmaFile.read8bitLE();
            tileset = (dmaFile.read8bitLE() & MASK_OF_TILESET);
            flags = dmaFile.read16bitLE();
            strLen = dmaFile.read8bitLE();

            // Next entry
            index = index + SIZE_OF_ENTRY + strLen;

            name = new StringBuilder(strLen);

            for (int i = 0; i < strLen; i++) {
                name.append((char) dmaFile.read8bitLE());
            }

            currentDmaEntry = new DmaEntryImpl(mapCode, tile, tileset, flags,
                    name.toString(), count, offset);

            dmaMap.put(mapCode, currentDmaEntry);
            dmaMapByName.put(currentDmaEntry.getName(), currentDmaEntry);

            count++;
        }
    }

    /**
     * Get Dma Entry.
     *
     * @param id id of entry
     *
     * @return dma
     */
    @Override
    public final DmaEntryImpl getDmaEntry(final int id) {
        return dmaMap.get(id);
    }

    /**
     * Get Dma Entry.
     *
     * @param name name of entry
     *
     * @return dma
     */
    @Override
    public final DmaEntry getDmaEntry(final String name) {
        return dmaMapByName.get(name);
    }

    /**
     * Return id entry map iterator.
     *
     * @return iterator of map code for all dma entry
     */
    @Override
    public final Iterator<Integer> getDmaEntryIterator() {
        return dmaMap.keySet().iterator();
    }

    /**
     * Dma count.
     *
     * @return number of dma in file
     */
    @Override
    public final int getDmaEntryCount() {
        return dmaMap.size();
    }
}