/**
 * Code from http://java.developpez.com/sources/?page=tableauxArbres#JTreeExplorer
 */
package org.jill.sha.ui.directory;

import javax.swing.*; 
import javax.swing.tree.*; 
import javax.swing.filechooser.*; 
import java.awt.*; 
import java.io.*; 

public class FileRenderer extends DefaultTreeCellRenderer{
    public FileRenderer(){
        super();    
    }    
    public Component getTreeCellRendererComponent(JTree tree,Object value
            ,boolean selected,boolean expanded
            ,boolean leaf,int row,boolean hasFocus){
        JLabel label = (JLabel)super.getTreeCellRendererComponent(tree,value,selected,expanded,leaf,row,hasFocus);
        File fichier = (File)value;
        FileSystemView sys = FileSystemView.getFileSystemView();
        label.setText(sys.getSystemDisplayName(fichier));
        label.setIcon(sys.getSystemIcon(fichier));
        return label;                
    }    
}