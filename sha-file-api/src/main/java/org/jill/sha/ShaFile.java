/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jill.sha;

import java.io.IOException;
import org.jill.file.FileAbstractByte;

/**
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
    public void load(String shaFile) throws IOException;

    /**
     * Constructor of class ShaFile.java.
     *
     * @param shaFile file data
     *
     * @throws IOException if error
     */
    public void load(FileAbstractByte shaFile) throws IOException;

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
