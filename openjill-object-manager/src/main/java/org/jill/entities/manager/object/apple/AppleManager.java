package org.jill.entities.manager.object.apple;

import org.jill.entities.manager.object.AbstractObject;
import org.jill.entities.manager.object.common.conf.PictureConf;
import org.jill.jn.ObjectItem;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.picture.PictureTools;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaFile;

import java.awt.image.BufferedImage;
import java.util.Optional;

public class AppleManager extends AbstractObject {
    /**
     * To know if message must be display.
     */
    private boolean messageDisplayAppleMessage = true;

    /**
     * Picture array.
     */
    private BufferedImage[] images;

    /**
     * Default index to extract image in jn-extractor.
     */
    private int defaultIndexImage;

    @Override
    public void init(final ObjectParam objectParam) {
        final AppleConf conf = readConf("apple.json", AppleConf.class);

        final ShaFile shaFile = objectParam.getShaFile();
        final EnumScreenType screen = objectParam.getScreen();

        int indexPicture = 0;

        images = new BufferedImage[conf.getPicture().length * 2];

        for (PictureConf pict: conf.getPicture()) {
            this.images[indexPicture] = PictureTools.getPicture(shaFile, pict.getTileset(), pict.getTile(), screen).get();
            this.images[indexPicture + 1] = this.images[indexPicture];

            indexPicture += 2;
        }

        defaultIndexImage = conf.getDefaultIndexImage();
    }

    @Override
    public Optional<BufferedImage> msgDraw(final ObjectItem object) {
        return Optional.of(images[defaultIndexImage * 2]);
    }

    @Override
    public void msgTouch(ObjectItem obj, KeyboardLayout keyboardLayout) {

    }

    @Override
    public void msgKill(ObjectItem sender, int nbLife, int typeOfDeath) {

    }

    @Override
    public void msgKill(BackgroundEntity sender, int nbLife, int typeOfDeath) {

    }

    @Override
    public void msgUpdate(KeyboardLayout keyboardLayout, final ObjectItem object) {

    }

    @Override
    public boolean isRemoveOutOfVisibleScreen() {
        return false;
    }

    @Override
    public Optional<BufferedImage> defaultPicture() {
        return Optional.of(images[defaultIndexImage * 2]);
    }
}
