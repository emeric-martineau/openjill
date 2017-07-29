/*
  Jill of the Jungle tool.
 */
package org.jill.sha.ui.filter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Class to filter file
 *
 * @author emeric martineau
 * @version 1.0
 */
public class FileExtension extends FileFilter {
    /**
     * Extension of file
     */
    private String extension ;
    
    /**
     * Description
     */
    private String description ;
    
    /**
     * Constructor
     */
    public FileExtension(final String extension, final String description)
    {
        if (extension != null)
        {
            this.extension = extension.toLowerCase() ;
        }
        
        this.description = description ;
    }
    
    /* (non-Javadoc)
     * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
     */
    @Override
    public boolean accept(File f) {
        return (testFile(f.getAbsolutePath()) || f.isDirectory()) ;
    }

    /* (non-Javadoc)
     * @see javax.swing.filechooser.FileFilter#getDescription()
     */
    @Override
    public String getDescription() {
        return description ;
    }

    /**
     * Test file name.
     * Return false if extension is empty or null for return only directory
     * 
     * @param fileName
     * 
     * @return
     */
    private boolean testFile(final String fileName)
    {
        return ((extension != null) && (!"".equals(extension)) &&
                fileName.toLowerCase().endsWith(extension)) ;
    }
}
