package org.jill.file;

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * Load file content in array and simulate a file function.
 * File can't be langer than Integer.MAX_VALUE
 *
 * @author Emeric MARTINEAU
 */
public final class FileAbstractByteImpl implements FileAbstractByte {
    /**
     * Mask to convert into unsigned byte.
     */
    private static final int UNSIGNED_BYTE = 0xff;

    /**
     * Shift byte 1.
     */
    private static final int BYTE_1 = 8;

    /**
     * Shift byte 2.
     */
    private static final int BYTE_2 = 16;

    /**
     * Shift byte 3.
     */
    private static final int BYTE_3 = 24;

    /**
     * Content of file.
     */
    private byte[] content;

    /**
     * Data pointer.
     */
    private int dataPointer = 0;

    /**
     * Create a virtual file.
     */
    public FileAbstractByteImpl() {
        // Nothing
    }

    /**
     * Create a virtual file.
     *
     * @param size size of file (file cannot be extend)
     */
    @Override
    public void load(final int size) {
        content = new byte[size];
    }

    /**
     * Load from file.
     *
     * @param raFile file to read
     *
     * @throws IOException file cannont be read
     */
    @Override
    public void load(final RandomAccessFile raFile)
            throws IOException {
        content = new byte[(int) raFile.length()];

        raFile.readFully(content);
    }

    /**
     * Load form file.
     *
     * @param file file
     *
     * @throws IOException file cannont be read
     */
    @Override
    public void load(final File file)
            throws IOException {
        load(new RandomAccessFile(getRealFileName(file), "r"));
    }

    /**
     * Load form file.
     *
     * @param filename name of file
     *
     * @throws IOException file cannont be read
     */
    @Override
    public void load(final String filename)
            throws IOException {
        load(new File(filename));
    }

    /**
     * Return file to work under windows or unix.
     *
     * @param fileToRead file to read (case unsensitive)
     *
     * @return real file name (with case sensitive)
     */
    private static File getRealFileName(final File fileToRead) {
        File path = fileToRead.getParentFile();

        // filename without directory
        if (path == null) {
            path = new File("./");
        }

        File[] listOfFile = path.listFiles();

        final String fileName = fileToRead.getName();

        for (File currentFile : listOfFile) {
            if (fileName.equalsIgnoreCase(currentFile.getName())) {
                return currentFile;
            }
        }

        return null;
    }

    /**
     * check file size.
     *
     * @throws EOFException if end of file
     */
    private void checkFileSize() throws EOFException {
        if (dataPointer == content.length) {
            throw new EOFException(
                    String.format(
                    "End of file ! Current lenght of file %d, "
                    + "internal pointer position %d",
                    content.length, dataPointer));
        }
    }

    /**
     * Read unsigned byte.
     *
     * @return return byte
     *
     * @throws EOFException if end of file
     */
    @Override
    public int readUnsignedByte() throws EOFException {
        checkFileSize();

        int b = (content[dataPointer] & UNSIGNED_BYTE);
        dataPointer++;

        return b;
    }

    /**
     * Read signed byte.
     *
     * @return return signed byte
     *
     * @throws EOFException if end of file
     */
    @Override
    public byte readByte() throws EOFException {
        checkFileSize();

        int b = content[dataPointer];
        dataPointer++;

        return (byte) (b);
    }

    /**
     * Read 32 bits Little Endian.
     *
     * @return return unsignedd 32 bit
     *
     * @throws EOFException if end of file
     */
    @Override
    public int read32bitLE() throws EOFException {
        int byte0 = readUnsignedByte();
        int byte1 = readUnsignedByte();
        int byte2 = readUnsignedByte();
        int byte3 = readUnsignedByte();

        return byte0 | (byte1 << BYTE_1) | (byte2 << BYTE_2)
                | (byte3 << BYTE_3);
    }

    /**
     * Read 16 bits Little Endian.
     *
     * @return return unsigned 16bits
     *
     * @throws EOFException if end of file
     */
    @Override
    public int read16bitLE() throws EOFException {
        int byte0 = readUnsignedByte();
        int byte1 = readUnsignedByte();

        return byte0 | (byte1 << BYTE_1);
    }

    /**
     * Read 8 bits Little Endian.
     *
     * @return unsignedbyte
     *
     * @throws EOFException if end of file
     */
    @Override
    public int read8bitLE() throws EOFException {
        int byte0 = readUnsignedByte();

        return byte0;
    }

    /**
     * Read 32 bits Little Endian.
     *
     * @return signed 32 bit
     *
     * @throws EOFException if end of file
     */
    @Override
    public int readSigned32bitLE() throws EOFException {
        int byte0 = readByte();
        int byte1 = readByte();
        int byte2 = readByte();
        int byte3 = readByte();

        long tmp = (byte0 & UNSIGNED_BYTE) | ((byte1 & UNSIGNED_BYTE) << BYTE_1)
                 | ((byte2 & UNSIGNED_BYTE) << BYTE_2)
                | ((byte3 & UNSIGNED_BYTE) << BYTE_3);

        return (int) tmp;
    }

    /**
     * Read 16 bits Little Endian.
     *
     * @return signed 16 bit
     *
     * @throws EOFException if end of file
     */
    @Override
    public int readSigned16bitLE() throws EOFException {
        int byte0 = readByte();
        int byte1 = readByte();

        int tmp = (byte0 & UNSIGNED_BYTE)
                | ((byte1 & UNSIGNED_BYTE) << BYTE_1);

        return (short) tmp;
    }

    /**
     * Read 8 bits Little Endian.
     *
     * @return signed 8 bit
     *
     * @throws EOFException if end of file
     */
    @Override
    public int readSigned8bitLE() throws EOFException {
        int byte0 = readByte();

        return byte0;
    }

    /**
     * Returns the current offset in this file.
     *
     * @return     the offset from the beginning of the file, in bytes,
     *             at which the next read or write occurs.
     */
    @Override
    public int getFilePointer() {
        return dataPointer;
    }

    /**
     * Sets the file-pointer offset, measured from the beginning of this
     * file, at which the next read or write occurs.  The offset may be
     * set beyond the end of the file. Setting the offset beyond the end
     * of the file does not change the file length.  The file length will
     * change only by writing after the offset has been set beyond the end
     * of the file.
     *
     * @param      pos   the offset position, measured in bytes from the
     *                   beginning of the file, at which to set the file
     *                   pointer.
     */
    @Override
    public void seek(final int pos) {
        if (pos < content.length) {
            dataPointer = pos;
        } else {
            dataPointer = content.length;
        }
    }

    /**
     * Return length of file.
     *
     * @return size of file (not capacity)
     */
    @Override
    public int length() {
        return content.length;
    }

    /**
     * Skip bytes.
     *
     * @param n skip byte
     *
     * @return number of skiped byte
     */
    @Override
    public int skipBytes(final int n) {
        int byteSkiped;

        if (dataPointer + n < content.length) {
            dataPointer += n;

            byteSkiped = n;
        } else {
            byteSkiped = content.length - dataPointer - 1;

            dataPointer = content.length;
        }

        return byteSkiped;
    }

    /**
     * write signed byte.
     *
     * @param b byte
     *
     * @throws EOFException if end of file
     */
    @Override
    public void writeByte(final int b) throws EOFException {
        checkFileSize();

        content[dataPointer] = (byte) b;
        dataPointer++;
    }


    /**
     * Write 16 bits Little Endian.
     *
     * @param b 16bit
     *
     * @throws EOFException if end of file
     */
    @Override
    public void writeSigned16bitLE(final int b) throws EOFException {
        int byte0 = b & UNSIGNED_BYTE;
        int byte1 = (b >> BYTE_1) & UNSIGNED_BYTE;

        writeByte(byte0);
        writeByte(byte1);
    }

    /**
     * Write 16 bits Little Endian.
     *
     * @param b 16 bits
     *
     * @throws EOFException if end of file
     */
    @Override
    public void write16bitLE(final int b) throws EOFException {
        writeSigned16bitLE(b);
    }

    /**
     * Write 32 bits Little Endian.
     *
     * @param b data
     *
     * @throws EOFException if end of file
     */
    @Override
    public void write32bitLE(final int b) throws EOFException {
        int byte0 = b & UNSIGNED_BYTE;
        int byte1 = (b >> BYTE_1) & UNSIGNED_BYTE;
        int byte2 = (b >> BYTE_2) & UNSIGNED_BYTE;
        int byte3 = (b >> BYTE_3) & UNSIGNED_BYTE;

        writeByte(byte0);
        writeByte(byte1);
        writeByte(byte2);
        writeByte(byte3);
    }

    /**
     * Write 32 bits Little Endian.
     *
     * @param b data
     *
     * @throws EOFException if end of file
     */
    @Override
    public void writeSigned32bitLE(final int b) throws EOFException {
        write32bitLE(b);
    }


    /**
     * Write data in stream.
     *
     * @param fos output stream
     *
     * @throws IOException if error
     */
    @Override
    public void saveToFile(final OutputStream fos) throws IOException {
        fos.write(content);
    }

    /**
     * Write data in file.
     *
     * @param filename file name
     *
     * @throws IOException if error
     */
    @Override
    public  void saveToFile(final String filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            saveToFile(fos);
        }
    }

    /**
     * Write data in file.
     *
     * @param filename file
     *
     * @throws IOException if error
     */
    @Override
    public void saveToFile(final File filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            saveToFile(fos);
        }
    }
}
