package org.jill.entities.manager.background;

import org.jill.dma.DmaEntry;
import org.jill.jn.BackgroundLayer;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaFile;
import org.jill.sha.ShaTile;
import org.jill.sha.ShaTileSet;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class AbstractBackground implements BackgroundEntity {
    /**
     * Picture of background.
     */
    protected BufferedImage picture;

    /**
     * Dma entry of current background.
     */
    protected DmaEntry dmaEntry;

    @Override
    public int getMapCode() {
        return dmaEntry.getMapCode();
    }

    @Override
    public String getName() {
        return dmaEntry.getName();
    }

    @Override
    public BufferedImage getPicture() {
        return picture;
    }

    @Override
    public int getTile() {
        return dmaEntry.getTile();
    }

    @Override
    public int getTileset() {
        return dmaEntry.getTileset();
    }

    protected BufferedImage getPicture(final ShaFile shaFile, final int tileSet, final int tile, final EnumScreenType screen) {
        final ShaTileSet[] shaTileset = shaFile.getShaTileSet();

        ShaTile[] currentShaTile = null;

        for (ShaTileSet tileset: shaTileset) {
            if (tileset.getTitleSetIndex() == tileSet) {
                currentShaTile = tileset.getShaTile();

                 break;
            }
        }

        if (tile < currentShaTile.length) {
            // For background T16, T17, T18, T19, T20, tile are invalid !!!
            ShaTile currentTile = currentShaTile[tile];

            switch (screen) {
                case CGA:
                    return currentTile.getPictureCga();
                case EGA:
                    return  currentTile.getPictureEga();
                default:
                    return currentTile.getPictureVga();
            }
        }

        return null;
    }

    /**
     * Short way to draw.
     *
     * @param g2d graphic
     * @param img image
     * @param x x
     * @param y y
     */
    public static void draw(Graphics g2d, Image img, int x, int y) {
        g2d.drawImage(img, x, y, null);
    }

    /**
     * Short way to draw.
     *
     * @param dest image
     * @param src
     * @param x x
     * @param y y
     */
    public static void drawFromImage(BufferedImage dest, Image src, int x, int y) {
        final Graphics2D g2d = dest.createGraphics();

        g2d.drawImage(src, x, y, null);

        g2d.dispose();
    }

    @Override
    public boolean isMsgDraw() {
        return dmaEntry.isMsgDraw();
    }

    @Override
    public boolean isMsgUpdate() {
        return dmaEntry.isMsgUpdate();
    }


    @Override
    public boolean isMsgTouch() {
        return dmaEntry.isMsgTouch();
    }

    @Override
    public boolean isPlayerThru() {
        return dmaEntry.isPlayerThru();
    }

    @Override
    public boolean isStair() {
        return dmaEntry.isStair();
    }

    @Override
    public boolean isVine() {
        return dmaEntry.isVine();
    }

    @Override
    public void msgDraw(BackgroundLayer background, int x, int y) {

    }

    @Override
    public void msgTouch(ObjectEntity obj) {

    }

    @Override
    public void msgUpdate(BackgroundLayer background, int x, int y) {

    }
}
