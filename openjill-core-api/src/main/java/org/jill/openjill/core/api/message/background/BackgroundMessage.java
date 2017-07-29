package org.jill.openjill.core.api.message.background;

/**
 * Background message to change background block.
 *
 * @author Emeric MARTINEAU
 */
public final class BackgroundMessage {
    /**
     * Map code.
     */
    private int mapCode;

    /**
     * Block x.
     */
    private int x;

    /**
     * Bloc y.
     */
    private int y;

    /**
     * Block name/
     */
    private String mapName = null;

    /**
     * Change background by map code.
     *
     * @param blockX x
     * @param blockY y
     * @param codeInMap  map code
     */
    public BackgroundMessage(final int blockX, final int blockY,
            final int codeInMap) {
        this.x = blockX;
        this.y = blockY;
        this.mapCode = codeInMap;
    }

        /**
     * Change background by map code.
     *
     * @param blockX x
     * @param blockY y
     * @param nameOfBack  map code
     */
    public BackgroundMessage(final int blockX, final int blockY,
            final String nameOfBack) {
        this.x = blockX;
        this.y = blockY;
        this.mapName = nameOfBack;
    }

    /**
     * Map code.
     *
     * @return  mpa code
     */
    public int getMapCode() {
        return mapCode;
    }

    /**
     * Set map code.
     *
     * @param codeInMap mapcode
     */
    public void setMapCode(final int codeInMap) {
        this.mapCode = codeInMap;
    }

    /**
     * Return x.
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * X.
     *
     * @param blockX x
     */
    public void setX(final int blockX) {
        this.x = blockX;
    }

    /**
     * Return y.
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Y.
     *
     * @param blockY y
     */
    public void setY(final int blockY) {
        this.y = blockY;
    }

    /**
     * Map name.
     *
     * @return  name
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Name of background.
     *
     * @param nameOfBack name of map
     */
    public void setMapName(final String nameOfBack) {
        this.mapName = nameOfBack;
    }
}
