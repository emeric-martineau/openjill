package org.jill.sha;

import java.io.IOException;
import org.jill.file.FileAbstractByte;

/**
 * Read SHA (picture) file.
 * 
 * @author emeric_martineau
 */
public interface ShaFile {

    /**
     * Constructor of class ShaFile.java.
     *
     * @param shaFile file data
     *
     * @throws IOException if error
     */
    void load(String shaFile) throws IOException;

    /**
     * Constructor of class ShaFile.java.
     *
     * @param shaFile file data
     *
     * @throws IOException if error
     */
    void load(FileAbstractByte shaFile) throws IOException;

    /**
     * Return header.
     *
     * @return header of file
     */
    ShaHeader getShaHeader();

    /**
     * Return array of Tileset.
     *
     * @return tileset of file
     */
    ShaTileSet[] getShaTileSet();

}
