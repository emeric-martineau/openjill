/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jill.sha;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author emeric_martineau
 */
public interface ShaTile {

    /**
     * Data format.
     *
     * @return data format
     */
    int getDataFormat();

    /**
     * Draw font.
     * USE ONLY IF FONT !
     *
     * @param mapColor color map
     *
     * @return image
     */
    BufferedImage getFont(Color[] mapColor);

    /**
     * Height.
     *
     * @return height
     */
    int getHeight();

    /**
     * Accesseur de imageIndex.
     *
     * @return imageIndex
     */
    int getImageIndex();

    /**
     * Accesseur de offset.
     *
     * @return offset
     */
    int getOffset();

    /**
     * Get picture in CGA mode.
     *
     * @return picture
     */
    BufferedImage getPictureCga();

    /**
     * Get picture in EGA mode.
     *
     * @return picture
     */
    BufferedImage getPictureEga();

    /**
     * Get picture in VGA mode.
     *
     * @return picture
     */
    BufferedImage getPictureVga();

    /**
     * Accesseur de size.
     *
     * @return size
     */
    int getSize();

    /**
     * Accesseur de titleSetIndex.
     *
     * @return titleSetIndex
     */
    int getTitleSetIndex();

    /**
     * Width.
     *
     * @return width
     */
    int getWidth();

}
