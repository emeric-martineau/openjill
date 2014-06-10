/**
 * Jill of the Jungle tool.
 */
package org.jill.sha.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Abstract button class
 *
 * @author emeric martineau
 * @version 1.0
 */
public abstract class AbstractButton extends JButton implements ActionListener {
    /**
     * Creates a button with text.
     *
     * @param text  the text of the button
     */
    public AbstractButton(String text) {
        super(text, null) ;
        
        addActionListener(this) ;
    }
    
    /**
     * If click on button
     */
    public void actionPerformed(final ActionEvent e) {
        onClick(e) ;
    }
    
    /**
     * Call when button click
     * 
     * @param e
     */
    public abstract void onClick(final ActionEvent e) ;
}
