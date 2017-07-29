package org.jill.dma;

import java.io.IOException;
import java.util.Iterator;
import org.jill.file.FileAbstractByte;

/**
 * Class to read DMA file who contain mapping between map and picture of game.
 * @author Emeric MARTINEAU
 */
public interface DmaFile {

    /**
     * Constructor of class ShaFile.java.
     *
     * @param dmaFile file name
     *
     * @throws IOException if error
     */
    void load(String dmaFile) throws IOException;

    /**
     * Constructor of class ShaFile.java.
     *
     * @param dmaFile file data
     *
     * @throws IOException if error
     */
    void load(FileAbstractByte dmaFile) throws IOException;

    /**
     * Get Dma Entry.
     *
     * @param id id of entry
     *
     * @return dma
     */
    DmaEntry getDmaEntry(int id);

    /**
     * Get Dma Entry.
     *
     * @param name name of entry
     *
     * @return dma
     */
    DmaEntry getDmaEntry(String name);

    /**
     * Dma count.
     *
     * @return number of dma in file
     */
    int getDmaEntryCount();

    /**
     * Return id entry map iterator.
     *
     * @return iterator of map code for all dma entry
     */
    Iterator<Integer> getDmaEntryIterator();

}
