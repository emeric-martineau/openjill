package org.jill.entities.manager.background;

import org.jill.openjill.core.api.entities.BackgroundParam;

public class StaticBackground extends AbstractBackground {
    @Override
    public void init(BackgroundParam backParameter) {
        dmaEntry = backParameter.getDmaEntry();

        picture = getPicture(backParameter.getShaFile(), dmaEntry.getTileset(), dmaEntry.getTile(),
                backParameter.getScreen());
    }
}
