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
public interface BackgroundLayer {
    /**
     * Size in file (fixed).
     */
    int SIZE_IN_FILE = 16384;

    /**
     * Height of map.
     */
    int MAP_HEIGHT = 64;

    /**
     * Height of map.
     */
    int MAP_WIDTH = 128;

    /**
     * Return map code value to seach it in dma file.
     *
     * @param x x-origin
     * @param y y-origine
     *
     * @return map code
     */
    int getMapCode(int x, int y);

}
