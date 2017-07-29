package org.jill.file;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * @author emeric_martineau
 */
public interface FileAbstractByte {

    /**
     * Create a virtual file.
     *
     * @param size size of file (file cannot be extend)
     */
    void load(int size);

    /**
     * Load from file.
     *
     * @param raFile file to read
     * @throws IOException file cannont be read
     */
    void load(RandomAccessFile raFile) throws IOException;

    /**
     * Load form file.
     *
     * @param file file
     * @throws IOException file cannont be read
     */
    void load(File file) throws IOException;

    /**
     * Load form file.
     *
     * @param filename name of file
     * @throws IOException file cannont be read
     */
    void load(String filename) throws IOException;

    /**
     * Returns the current offset in this file.
     *
     * @return the offset from the beginning of the file, in bytes,
     * at which the next read or write occurs.
     */
    int getFilePointer();

    /**
     * Return length of file.
     *
     * @return size of file (not capacity)
     */
    int length();

    /**
     * Read 16 bits Little Endian.
     *
     * @return return unsigned 16bits
     * @throws EOFException if end of file
     */
    int read16bitLE() throws EOFException;

    /**
     * Read 32 bits Little Endian.
     *
     * @return return unsignedd 32 bit
     * @throws EOFException if end of file
     */
    int read32bitLE() throws EOFException;

    /**
     * Read 8 bits Little Endian.
     *
     * @return unsignedbyte
     * @throws EOFException if end of file
     */
    int read8bitLE() throws EOFException;

    /**
     * Read signed byte.
     *
     * @return return signed byte
     * @throws EOFException if end of file
     */
    byte readByte() throws EOFException;

    /**
     * Read 16 bits Little Endian.
     *
     * @return signed 16 bit
     * @throws EOFException if end of file
     */
    int readSigned16bitLE() throws EOFException;

    /**
     * Read 32 bits Little Endian.
     *
     * @return signed 32 bit
     * @throws EOFException if end of file
     */
    int readSigned32bitLE() throws EOFException;

    /**
     * Read 8 bits Little Endian.
     *
     * @return signed 8 bit
     * @throws EOFException if end of file
     */
    int readSigned8bitLE() throws EOFException;

    /**
     * Read unsigned byte.
     *
     * @return return byte
     * @throws EOFException if end of file
     */
    int readUnsignedByte() throws EOFException;

    /**
     * Write data in stream.
     *
     * @param fos output stream
     * @throws IOException if error
     */
    void saveToFile(OutputStream fos) throws IOException;

    /**
     * Write data in file.
     *
     * @param filename file name
     * @throws IOException if error
     */
    void saveToFile(String filename) throws IOException;

    /**
     * Write data in file.
     *
     * @param filename file
     * @throws IOException if error
     */
    void saveToFile(File filename) throws IOException;

    /**
     * Sets the file-pointer offset, measured from the beginning of this
     * file, at which the next read or write occurs.  The offset may be
     * set beyond the end of the file. Setting the offset beyond the end
     * of the file does not change the file length.  The file length will
     * change only by writing after the offset has been set beyond the end
     * of the file.
     *
     * @param pos the offset position, measured in bytes from the
     *            beginning of the file, at which to set the file
     *            pointer.
     */
    void seek(int pos);

    /**
     * Skip bytes.
     *
     * @param n skip byte
     * @return number of skiped byte
     */
    int skipBytes(int n);

    /**
     * Write 16 bits Little Endian.
     *
     * @param b 16 bits
     * @throws EOFException if end of file
     */
    void write16bitLE(int b) throws EOFException;

    /**
     * Write 32 bits Little Endian.
     *
     * @param b data
     * @throws EOFException if end of file
     */
    void write32bitLE(int b) throws EOFException;

    /**
     * write signed byte.
     *
     * @param b byte
     * @throws EOFException if end of file
     */
    void writeByte(int b) throws EOFException;

    /**
     * Write 16 bits Little Endian.
     *
     * @param b 16bit
     * @throws EOFException if end of file
     */
    void writeSigned16bitLE(int b) throws EOFException;

    /**
     * Write 32 bits Little Endian.
     *
     * @param b data
     * @throws EOFException if end of file
     */
    void writeSigned32bitLE(int b) throws EOFException;

}
