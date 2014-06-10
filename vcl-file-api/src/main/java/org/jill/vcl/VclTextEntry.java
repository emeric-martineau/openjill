/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jill.vcl;

/**
 *
 * @author emeric_martineau
 */
public interface VclTextEntry {

    /**
     * Offset in file where entry can be found.
     *
     * @return offset
     */
    int getOffset();

    /**
     * Text.
     *
     * @return text
     */
    String getText();

}
