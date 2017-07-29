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
     * @param mpCode map code
     * @param tileIndex index of tile
     * @param tilesetIndex index of tileset
     * @param flagsOfEntry flags
     * @param nameOfBackground name of dma entry
     * @param indexInFile index of entry in file
     * @param offsetInFile offset of entry in file
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

    /**
     * Return map code.
     *
     * @return mapCode code of background
     */
    @Override
    public final int getMapCode() {
        return mapCode;
    }

    /**
     * Tile.
     *
     * @return tile index of tile
     */
    @Override
    public final int getTile() {
        return tile;
    }

    /**
     * Tileset.
     *
     * @return tileset index of tileset
     */
    @Override
    public final int getTileset() {
        return tileset;
    }

    /**
     * Flags.
     *
     * @return flags flags value
     */
    @Override
    public final int getFlags() {
        return flags;
    }

    /**
     * Name.
     *
     * @return name name of background in file
     */
    @Override
    public final String getName() {
        return name;
    }

    /**
     * Index.
     *
     * @return index index of this entry in file
     */
    @Override
    public final int getIndex() {
        return index;
    }

    /**
     * Offset in file where entry can be found.
     *
     * @return offset  offset of this entry in file
     */
    @Override
    public final int getOffset() {
        return offset;
    }
}
