package org.jill.vcl;

/**
 * Entry of dma file.
 *
 * @author Emeric MARTINEAU
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
