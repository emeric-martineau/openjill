/*
  Code from http://java.developpez.com/sources/?page=tableauxArbres#JTreeExplorer
 */
package org.jill.sha.ui.directory;

import java.io.*; 
import java.util.*; 
import javax.swing.event.*; 
import javax.swing.tree.*; 

import org.jill.sha.ui.filter.DirectoryFilter;

public class FileTreeModel implements TreeModel{
    private final File root;
    public FileTreeModel(File file){
        root = file;    
    }    
    public List<File> getFichiers(Object parent){
        File fileParent = (File)parent;
        File[] fichiers = fileParent.listFiles(new DirectoryFilter());

        if (fichiers == null) {
            return new ArrayList<File>();
        }

        Arrays.sort(fichiers, new Comparator<File>(){
            public int compare(File f1,File f2){
                boolean dirf1 = f1.isDirectory();
                boolean dirf2 = f2.isDirectory();
                if(dirf1&&!dirf2){return -1;}
                if(!dirf1&&dirf2){return 1;}
                return f1.getPath().compareTo(f2.getPath());
            }
        });

        return Arrays.asList(fichiers);
    }
    public Object getRoot(){
        return root;
    }
    public Object getChild(Object parent, int index){
        return getFichiers(parent).get(index);
    } 
    public int getChildCount(Object parent){
        return getFichiers(parent).size();
    } 
    public int getIndexOfChild(Object parent, Object child){
        return getFichiers(parent).indexOf(child);
    } 
    
    // If it's node
    public boolean isLeaf(Object node){
        File f = (File)node;
        return !f.isDirectory();
    }  
    public void valueForPathChanged(TreePath path, Object newValue){}  
    public void addTreeModelListener(TreeModelListener l){}  
    public void removeTreeModelListener(TreeModelListener l){} 
}
