package org.jill.cfg;

/**
 * Save game file.
 *
 * @author Emeric MARTIEAU
 */
public class SaveGameItemImpl implements SaveGameItem {
    /**
     * File save pattern.
     */
    private static final String SAVE_FILENAME_PATTERN = "%sSAVE.%d";

    /**
     * File map pattern.
     */
    private static final String MAP_FILENAME_PATTERN = "%sSAVEM.%d";

    /**
     * Name of save game file.
     */
    private final String saveGameFile;

    /**
     * Name of save map file.
     */
    private final String saveMapFile;

    /**
     * Saved game name.
     */
    private String name;

    /**
     * Constructor.
     *
     * @param prefixe save file prefix
     * @param index   index of save
     * @param nm      name of save
     */
    public SaveGameItemImpl(final String prefixe, final int index,
            final String nm) {
        this.name = nm;

        saveGameFile = String.format(SAVE_FILENAME_PATTERN, prefixe, index);
        saveMapFile = String.format(MAP_FILENAME_PATTERN, prefixe, index);
    }

    /**
     * Name.
     *
     * @return name
     */
    @Override
    public final String getName() {
        return name;
    }

    /**
     * Set name of save entry.
     *
     * @param nm name
     */
    @Override
    public final void setName(final String nm) {
        this.name = nm;
    }

    /**
     * Save file name.
     *
     * @return filename
     */
    @Override
    public final String getSaveGameFile() {
        return saveGameFile;
    }

    /**
     * Map file name.
     *
     * @return filename
     */
    @Override
    public final String getSaveMapFile() {
        return saveMapFile;
    }
}
