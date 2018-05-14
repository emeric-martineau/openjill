package org.jill.game.entities.picutre;

import java.awt.image.BufferedImage;
import java.util.Optional;

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
     * Array of picture.
     *
     * @param maxCounter maximum display counter
     * @param idxPicture index to start picture
     */
    public PictureSynchronizer(final int maxCounter, final int idxPicture) {
        this(maxCounter);
        this.indexPicture = idxPicture;
    }

    /**
     * Update index.
     *
     * @param currentIndex index of object
     * @param pictureLength lenght of array picture
     * @return new index
     */
    public int updatePictureIndex(final int currentIndex,
            final int pictureLength) {
        if (currentIndex == this.indexEtat) {
            this.indexEtat++;

            // Need to update picture
            if (this.displayCounter == 0) {
                this.indexPicture++;

                if (this.indexPicture == pictureLength) {
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
     * @return index picutre
     */
    public int getIndexPicture() {
        return this.indexPicture;
    }
}
