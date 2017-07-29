package org.jill.game.gui.tools;

/**
 * Create limited string.
 *
 * @author Emeric MARTINEAU
 */
public final class LimitedString {
    /**
     * Maximum size.
     */
    private final int maxSize;
    /**
     * String content.
     */
    private final char[] buffer;
    /**
     * Current len of string.
     */
    private int currentLen;

    /**
     * Create string of x char.
     *
     * @param size size of string
     */
    public LimitedString(final int size) {
        this.maxSize = size;

        buffer = new char[size];
    }

    /**
     * Add char in buffer. Auto manage delete char "\b"
     *
     * @param c char to add
     */
    public void add(final char c) {
        if (c == '\b') {
            delete();
        } else if (currentLen < maxSize) {
            buffer[currentLen] = c;

            currentLen++;
        }
    }

    /**
     * Delete last char.
     *
     * Replace current char by space
     */
    public void delete() {
        if (currentLen > 0) {
            currentLen--;
        }
    }

    /**
     * Return max size of string.
     *
     * @return capacity
     */
    public int getCapacity() {
        return maxSize;
    }

    /**
     * Get current size of string.
     *
     * @return current size
     */
    public int size() {
        return currentLen;
    }

    /**
     * Clear data.
     */
    public void clear() {
        this.currentLen = 0;
    }

    @Override
    public String toString() {
        return new String(buffer, 0, currentLen);
    }
}
