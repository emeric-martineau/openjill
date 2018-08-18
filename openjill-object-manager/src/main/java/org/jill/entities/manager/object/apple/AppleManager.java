package org.jill.entities.manager.object.apple;

import org.jill.entities.manager.object.AbstractObject;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
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
     * Configuration of manager.
     */
    private AppleConf conf;

    @Override
    public void init(final ObjectParam objectParam) {
        conf = readConf("apple.json", AppleConf.class);

        final int numberTileSet = conf.getNumberTileSet();
        final int tileIndex = conf.getTile();
        final int tileSetIndex = conf.getTileset();

        final ShaFile shaFile = objectParam.getShaFile();
        final EnumScreenType screen = objectParam.getScreen();

        images = new BufferedImage[numberTileSet * 2];

        int indexArray = (numberTileSet * 2) - 1;

        for (int index = 0; index < numberTileSet; index++) {
            this.images[indexArray]
                    = getPicture(shaFile, tileSetIndex, tileIndex + index, screen).get();
            this.images[indexArray - 1] = this.images[indexArray];

            indexArray -= 2;
        }
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
        return Optional.of(images[conf.getDefaultIndexImage() * 2]);
    }
}
