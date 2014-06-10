/**
 * Jill of the Jungle tool.
 */
package org.jill.sha.ui.button;

import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.jill.sha.ui.AbstractButton;
import org.jill.sha.ui.directory.FileExplorer;
import org.jill.sha.ui.filter.FileExtension;

/**
 * Select directory button
 *
 * @author emeric martineau
 * @version 1.0
 */
public class SelectDirButton extends AbstractButton {

    /**
     * File chooser
     */
    private FileExplorer fe ;
    
    /**
     * Text area
     */
    private JTextField textBox ;
    
    /**
     * Creates a button with text.
     *
     * @param text  the text of the button
     */
    public SelectDirButton(final String text, final JTextField textField) {
        super(text) ;
        this.textBox = textField ;
    }    
    
    /**
     * If click on button
     */
    @Override
    public void onClick(final ActionEvent e) {
        fe = new FileExplorer(".") ;
        
        textBox.setText(fe.getSelectedDir()) ;
        
//        int returnVal = fc.showOpenDialog(this) ;
//
//        if ((returnVal == JFileChooser.APPROVE_OPTION) && (textBox != null)) {
//            //Create a file chooser
//            
//            textBox.setText(fc.getSelectedFile().getAbsolutePath()) ;
//        } 
    }
}
