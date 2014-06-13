package org.jill.jn;

/**
 * String entry.
 *
 * @author Emeric MARTINEAU
 */
public interface StringItem {

    /**
     * Return offset in file for this object.
     *
     * @return offset
     */
    int getOffset();

    /**
     * Return size in file.
     *
     * @return size
     */
    int getSizeInFile();

    /**
     * Level.
     *
     * @return level
     */
    String getValue();
}
