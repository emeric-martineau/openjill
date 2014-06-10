package org.jill.game.entities.picutre;

import java.awt.image.BufferedImage;

/**
 * Class to synchronize picutre.
 *
 * @author emeric MARTINEAU
 */
public final class PictureSynchronizer {
    /**
     * Index of current picture.
     */
    private int indexPicture = 0;

    /**
     * Counter to change picture.
     */
    private int displayCounter = 0;

    /**
     * Maximum counter for display.
     */
    private int maxDisplayCounter = 0;

    /**
     * Index.
     */
    private int indexEtat = 0;

    /**
     * Array of picture.
     *

     * @param maxCounter maximum display counter
     */
    public PictureSynchronizer(
            final int maxCounter) {
        this.maxDisplayCounter = maxCounter;
    }

    /**
     * Update index.
     *
     * @param currentIndex index of object
     * @param bufferPictre picture
     *
     * @return new index
     */
    public int updatePictureIndex(final int currentIndex,
            final BufferedImage[] bufferPictre) {
        if (currentIndex == this.indexEtat) {
            this.indexEtat++;

            // Need to update picture
            if (this.displayCounter == 0) {
                this.indexPicture++;

                if (this.indexPicture == bufferPictre.length) {
                    this.indexPicture = 0;
                }
            }

            this.displayCounter++;

            if (this.displayCounter > this.maxDisplayCounter) {
                this.displayCounter = 0;
            }

        }

        return this.indexEtat;
    }

    /**
     * Return index picture.
     *
     * @return  index picutre
     */
    public int getIndexPicture() {
        return this.indexPicture;
    }
}
