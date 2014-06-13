package org.jill.vcl;

import java.io.IOException;
import java.util.List;
import org.jill.file.FileAbstractByte;

/**
 * Class to read VCL file who contain sound and text.
 *
 * @author emeric martineau
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
