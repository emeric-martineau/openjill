/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jill.vcl;

import java.io.IOException;
import java.util.List;
import org.jill.file.FileAbstractByte;

/**
 *
 * @author emeric_martineau
 */
public interface VclFile {
/**
     * Constructor of class ShaFile.
     *
     * @param vclFile file name
     *
     * @throws IOException if error
     */
    void load(String vclFile) throws IOException;

    /**
     * Constructor of class ShaFile.
     *
     * @param vclFile file data
     *
     * @throws IOException if error
     */
    void load(FileAbstractByte vclFile) throws IOException;

    /**
     * Text.
     *
     * @return vclText
     */
    List<VclTextEntry> getVclText();

}
