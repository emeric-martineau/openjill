/*
  Jill of the Jungle tool.
 */
package org.jill.sha.ui.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * Class to filter file
 *
 * @author emeric martineau
 * @version 1.0
 */
public class DirectoryFilter implements FileFilter {
    /* (non-Javadoc)
     * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
     */
    public boolean accept(File f) {
        return f.isDirectory() ;
    }
}
