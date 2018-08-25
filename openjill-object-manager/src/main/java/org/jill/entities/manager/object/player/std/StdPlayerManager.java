package org.jill.entities.manager.object.player.std;

import org.jill.entities.manager.object.AbstractObject;
import org.jill.entities.manager.object.common.conf.PictureConf;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaFile;

import java.awt.image.BufferedImage;
import java.util.Optional;

public class StdPlayerManager extends AbstractObject {
    /**
     * Picture array.
     */
    private BufferedImage[] stStandPicture;

    /**
     * Default index to extract image in jn-extractor.
     */
    private int defaultIndexImage;

    @Override
    public void init(final ObjectParam objectParam) {
        final StdPlayerConf conf = readConf("stdplayer.json", StdPlayerConf.class);

        final ShaFile shaFile = objectParam.getShaFile();
        final EnumScreenType screen = objectParam.getScreen();

        int indexPicture = 0;

        stStandPicture = new BufferedImage[conf.getStand().length];

        for (PictureConf pict: conf.getStand()) {
            this.stStandPicture[indexPicture] = getPicture(shaFile, pict.getTileset(), pict.getTile(), screen).get();

            indexPicture++;
        }

        defaultIndexImage = conf.getDefaultIndexImage();
    }

    @Override
    public Optional<BufferedImage> msgDraw() {
        return Optional.empty();
    }

    @Override
    public void msgTouch(ObjectEntity obj, KeyboardLayout keyboardLayout) {

    }

    @Override
    public void msgKill(ObjectEntity sender, int nbLife, int typeOfDeath) {

    }

    @Override
    public void msgKill(BackgroundEntity sender, int nbLife, int typeOfDeath) {

    }

    @Override
    public void msgUpdate(KeyboardLayout keyboardLayout) {

    }

    @Override
    public boolean isRemoveOutOfVisibleScreen() {
        return false;
    }

    @Override
    public Optional<BufferedImage> defaultPicture() {
        return Optional.of(stStandPicture[defaultIndexImage]);
    }
}
