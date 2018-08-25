package org.jill.entities.manager.object.player.std;

import org.jill.entities.manager.object.common.conf.PictureConf;

public class StdPlayerConf {
    /**
     * Stand picture.
     */
    private PictureConf[] stand;

    /**
     * Default index to extract image in jn-extractor.
     */
    private int defaultIndexImage;

    /**
     * Get stand picture config.
     *
     * @return array of conf
     */
    public PictureConf[] getStand() {
        return stand;
    }

    /**
     * Default index image to return for jn-extractor.
     *
     * @return default index
     */
    public int getDefaultIndexImage() {
        return defaultIndexImage;
    }
}
