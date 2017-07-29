package org.jill.game.entities.back.obj;

/**
 * Alow backgound to syncrhonize object creation.
 *
 * @author Eemric MARTINEAU
 */
public class ObjectSyncrhonizer {
    /**
     * Counter to create object.
     */
    private int counter;

    /**
     * Max counter value.
     */
    private int maxCounter;

    /**
     * Create object.
     *
     * @param maxCount max counter value to wait before create object.
     */
    public void setMaxCounter(final int maxCount) {
        this.maxCounter = maxCount;
    }

    /**
     * Update counter.
     *
     * @param currentCounter counter valeur of background.
     * @return new counter value
     */
    public int updateCounter(final int currentCounter) {
        if (currentCounter == this.counter) {
            this.counter++;

            if (this.counter > this.maxCounter) {
                this.counter = 0;
            }
        }

        return this.counter;
    }

    /**
     * If need create object.
     *
     * @param xBoard x of board
     * @return true if need create object
     */
    public boolean isCreateObject(final int xBoard) {
        int x = xBoard;

        // Decrease x check right value
        while (x > this.maxCounter) {
            x -= this.maxCounter;
        }

        return x == this.counter;
    }
}
