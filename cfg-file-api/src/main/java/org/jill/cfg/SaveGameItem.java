package org.jill.cfg;

/**
 * Class represent save entry.
 *
 * @author Emeric MARTINEAU
 */
public interface SaveGameItem {
    /**
     * Maximal save len of name.
     */
    int LEN_SAVE_NAME = 7;

    /**
     * Name.
     *
     * @return name
     */
    String getName();

    /**
     * Save file name.
     *
     * @return filename
     */
    String getSaveGameFile();

    /**
     * Map file name.
     *
     * @return filename
     */
    String getSaveMapFile();

    /**
     * Set name of save entry.
     *
     * @param nm name
     */
    void setName(String nm);
}
