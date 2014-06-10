/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jill.jn;

/**
 *
 * @author emeric_martineau
 */
public interface StringItem {

    /**
     * Return offset in file for this object.
     *
     * @return offset
     */
    int getOffset();

    /**
     * Return size in file.
     *
     * @return size
     */
    int getSizeInFile();

    /**
     * Level.
     *
     * @return level
     */
    String getValue();
}
