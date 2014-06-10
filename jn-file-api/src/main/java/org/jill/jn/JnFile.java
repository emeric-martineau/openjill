/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jill.jn;

import java.io.IOException;
import java.util.List;
import org.jill.file.FileAbstractByte;

/**
 *
 * @author emeric_martineau
 */
public interface JnFile {
    /**
     * Constructor.
     *
     * @param jnFile file name
     *
     * @throws IOException if error
     */
    public void load(String jnFile) throws IOException;

    /**
     * Constructor.
     *
     * @param jnFile file
     *
     * @throws IOException if error
     */
    void load(FileAbstractByte jnFile) throws IOException;

    /**
     * Return background.
     *
     * @return backgroundLayer background
     */
    BackgroundLayer getBackgroundLayer();

    /**
     * Return list of object.
     *
     * @return objectLayer object list
     */
    List<ObjectItem> getObjectLayer();

    /**
     * Return save data.
     *
     * @return data of save
     */
    SaveData getSaveData();

    /**
     * String of object.
     *
     * @return stringStack string of object
     */
    List<StringItem> getStringStack();

}
