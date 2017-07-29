package org.jill.jn;

import java.io.IOException;

import org.jill.file.FileAbstractByte;


/**
 * String entry.
 *
 * @author Emeric MARTINEAU
 * @version 1.0
 */
public class StringItemImpl implements StringItem {
    /**
     * String value.
     */
    private final String value;

    /**
     * Offset in file of this record.
     */
    private final int offset;

    /**
     * Constructor.
     *
     * @param jnFile file
     * @throws IOException if error
     */
    public StringItemImpl(final FileAbstractByte jnFile) throws IOException {
        this.offset = jnFile.getFilePointer();

        int strLen;
        StringBuilder name;

        strLen = jnFile.read16bitLE();

        name = new StringBuilder(strLen);

        for (int i = 0; i < strLen; i++) {
            name.append((char) jnFile.read8bitLE());
        }

        value = name.toString();

        // Read extra null terminate
        jnFile.read8bitLE();
    }

    /**
     * Level.
     *
     * @return level
     */
    @Override
    public final String getValue() {
        return value;
    }

    /**
     * Return offset in file for this object.
     *
     * @return offset
     */
    @Override
    public final int getOffset() {
        return offset;
    }

    @Override
    public final String toString() {
        return value;
    }

    /**
     * Return size in file.
     *
     * @return size
     */
    @Override
    public final int getSizeInFile() {
        return 2 /* size of string */ + 1 /* null terminate*/ + value.length();
    }
}
