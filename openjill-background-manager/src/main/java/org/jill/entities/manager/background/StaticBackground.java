package org.jill.entities.manager.background;

import org.jill.dma.DmaEntry;
import org.jill.jn.BackgroundLayer;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.BackgroundParam;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.sha.ShaFile;
import org.jill.sha.ShaTile;
import org.jill.sha.ShaTileSet;

import java.awt.image.BufferedImage;

public class StaticBackground implements BackgroundEntity {
    /**
     * Picture of background.
     */
    private BufferedImage picture;

    /**
     * Dma entry of current background.
     */
    private DmaEntry dmaEntry;

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

    @Override
    public void init(BackgroundParam backParameter) {
        dmaEntry = backParameter.getDmaEntry();

        final ShaFile shaFile = backParameter.getShaFile();

        final ShaTileSet[] shaTileset = shaFile.getShaTileSet();

        ShaTile[] currentShaTile = null;

        for (ShaTileSet tileset: shaTileset) {
            if (tileset.getTitleSetIndex() == dmaEntry.getTileset()) {
                currentShaTile = tileset.getShaTile();

                 break;
            }
        }

        if (dmaEntry.getTile() < currentShaTile.length) {
            // For background T16, T17, T18, T19, T20, tile are invalid !!!
            ShaTile currentTile = currentShaTile[dmaEntry.getTile()];

            switch (backParameter.getScreen()) {
                case CGA:
                    this.picture = currentTile.getPictureCga();
                    break;
                case EGA:
                    this.picture = currentTile.getPictureEga();
                    break;
                default:
                    this.picture = currentTile.getPictureVga();
                    break;
            }
        }
    }

    @Override
    public boolean isMsgTouch() {
        return dmaEntry.isMsgTouch();
    }

    @Override
    public boolean isMsgDraw() {
        // For this manager, always false
        //return dmaEntry.isMsgDraw();
        return false;
    }

    @Override
    public boolean isMsgUpdate() {
        // For this manager, always false
        //return dmaEntry.isMsgUpdate();
        return false;
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
