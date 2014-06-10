/**
 * Jill of the Jungle tool.
 */
package org.jill.sha.ui.button;

import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.jill.sha.ui.AbstractButton;
import org.jill.sha.ui.filter.FileExtension;

/**
 * Select file button
 *
 * @author emeric martineau
 * @version 1.0
 */
public class SelectFileButton extends AbstractButton {

    /**
     * File chooser
     */
    private JFileChooser fc ;
    
    /**
     * Text area
     */
    private JTextField textBox ;
    
    /**
     * Creates a button with text.
     *
     * @param text  the text of the button
     */
    public SelectFileButton(final String text, final JTextField textField) {
        super(text) ;
        
        //Create a file chooser
        fc = new JFileChooser() ;
        
        fc.resetChoosableFileFilters();
        fc.addChoosableFileFilter(new FileExtension("sha", "Fichier SHA"));
        
        this.textBox = textField ;
    }    
    
    /**
     * If click on button
     */
    @Override
    public void onClick(final ActionEvent e) {
        int returnVal = fc.showOpenDialog(this) ;

        if ((returnVal == JFileChooser.APPROVE_OPTION) && (textBox != null)) {
            textBox.setText(fc.getSelectedFile().getAbsolutePath()) ;
        } 
    }
}
