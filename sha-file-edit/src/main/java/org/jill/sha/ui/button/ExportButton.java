/**
 * Jill of the Jungle tool.
 */
package org.jill.sha.ui.button;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jill.sha.export.ShaFileExportTool;
import org.jill.sha.ui.AbstractButton;

/**
 * Export button
 *
 * @author emeric martineau
 * @version 1.0
 */
public class ExportButton extends AbstractButton {
    /**
     * Text area
     */
    private JTextField fileToRead ;
    
    /**
     * Text area
     */    
    private JTextField dirToWrite ;
    
    /**
     * Creates a button with text.
     *
     * @param text  the text of the button
     */
    public ExportButton(final String text, final JTextField fileToRead, final JTextField dirToWrite) {
        super(text) ;
        
        this.fileToRead = fileToRead ;
        this.dirToWrite = dirToWrite ;
    }    
    
    /**
     * If click on button
     */
    @Override
    public void onClick(final ActionEvent e) {
        final String value1 = fileToRead.getText() ;
        final String value2 = dirToWrite.getText() ; 
        
        if (checkValue(value1, "File must be set !") &&
                checkValue(value2, "Destination directory must be set !") &&
                checkExistsFile(value1, "File doesn't exits !"))
        {
            try {
                ShaFileExportTool.exportPictureFromShaFile(value1, value2) ;
            } catch (IOException e1) {
                // TODO Bloc catch auto-généré
                e1.printStackTrace();
            }
        }
    }
    
    /**
     * Check if value done
     * 
     * @param value
     * @param msg message to display
     * 
     * @return true false
     */
    private boolean checkValue(final String value, final String msg)
    {
        if (value == null || "".equals(value.trim()))
        {
            JOptionPane.showMessageDialog(this, msg) ;
            return false ;
        }
        
        return true ;
    }
    
    /**
     * Check if file exist
     * 
     * @param file
     * @param msg message to display
     * 
     * @return true false
     */    
    private boolean checkExistsFile(final String file, final String msg)
    {
        if (! (new File(file).exists()))
        {
            JOptionPane.showMessageDialog(this, msg) ;
            return false ;
        }
        
        return true ;
    }
}
