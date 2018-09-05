package org.jill.entities.manager.background;

import org.jill.openjill.core.api.picture.PictureTools;
import org.jill.openjill.core.api.entities.BackgroundParam;

import java.awt.image.BufferedImage;
import java.util.Optional;

public class StaticBackground extends AbstractBackground {
    @Override
    public void init(BackgroundParam backParameter) {
        dmaEntry = backParameter.getDmaEntry();

        final Optional<BufferedImage> currentPicture = PictureTools.getPicture(backParameter.getShaFile(), dmaEntry.getTileset(),
                dmaEntry.getTile(), backParameter.getScreen());

        if (currentPicture.isPresent()) {
            picture = currentPicture.get();
        }
    }
}
