package org.jill.vcl;

/**
 * Entry of dma file.
 *
 * @author Emeric MARTINEAU
 * @version 1.0
 */
public class VclTextEntryImpl implements VclTextEntry {
    /**
     * Name of entry.
     */
    private final String text;

    /**
     * Offset in file where entry can be found.
     */
    private final int offset;

    /**
     * Constructor.
     *
     * @param txt text
     * @param off offset in file
     */
    public VclTextEntryImpl(final String txt, final int off) {
        super();

        this.text = txt;
        this.offset = off;
    }

    /**
     * Text.
     *
     * @return text
     */
    @Override
    public final String getText() {
        return text;
    }

    /**
     * Offset in file where entry can be found.
     *
     * @return offset
     */
    @Override
    public final int getOffset() {
        return offset;
    }
}
