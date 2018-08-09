package org.jill.dma;

/**
 * Entry of dma file.
 *
 * @author Emeric MARTINEAU
 * @version 1.0
 */
public class DmaEntryImpl implements DmaEntry {
    /**
     * Index of object in file.
     */
    private final int index;

    /**
     * ID used in map file.
     */
    private final int mapCode;

    /**
     * Index into the tileset for the image to use.
     */
    private final int tile;

    /**
     * Index of tileset containing this tile's image (ignore upper two bits).
     */
    private final int tileset;

    /**
     * Flags for this tile (can stand on, can hurt player, etc.).
     */
    private final int flags;

    /**
     * Name of entry.
     */
    private final String name;

    /**
     * Offset in file where entry can be found.
     */
    private final int offset;

    /**
     * Constructor.
     *
     * @param mpCode           map code
     * @param tileIndex        index of tile
     * @param tilesetIndex     index of tileset
     * @param flagsOfEntry     flags
     * @param nameOfBackground name of dma entry
     * @param indexInFile      index of entry in file
     * @param offsetInFile     offset of entry in file
     */
    public DmaEntryImpl(final int mpCode, final int tileIndex,
            final int tilesetIndex, final int flagsOfEntry,
            final String nameOfBackground, final int indexInFile,
            final int offsetInFile) {
        super();
        this.mapCode = mpCode;
        this.tile = tileIndex;
        this.tileset = tilesetIndex;
        this.flags = flagsOfEntry;
        this.name = nameOfBackground;
        this.index = indexInFile;
        this.offset = offsetInFile;
    }

    @Override
    public final int getMapCode() {
        return mapCode;
    }

    @Override
    public final int getTile() {
        return tile;
    }

    @Override
    public final int getTileset() {
        return tileset;
    }

    @Override
    public final int getFlags() {
        return flags;
    }

    @Override
    public final String getName() {
        return name;
    }


    @Override
    public final int getIndex() {
        return index;
    }

    @Override
    public final int getOffset() {
        return offset;
    }

    @Override
    public boolean isMsgTouch() {
        return (flags & 8) != 0;
    }

    @Override
    public boolean isMsgDraw() {
        return (flags & 16) != 0;
    }

    @Override
    public boolean isMsgUpdate() {
        return (flags & 32) != 0;
    }

    @Override
    public boolean isPlayerThru() {
        return (flags & 1) != 0;
    }

    @Override
    public boolean isStair() {
        // In flag, f_notstair 2
        return !((flags & 2) != 0);
    }

    @Override
    public boolean isVine() {
        // In flag, f_notvine 4
        return !((flags & 4) != 0);
    }

}
