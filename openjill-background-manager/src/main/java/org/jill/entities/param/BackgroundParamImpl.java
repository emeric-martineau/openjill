package org.jill.entities.param;

import org.jill.dma.DmaEntry;
import org.jill.dma.DmaFile;
import org.jill.openjill.core.api.entities.BackgroundParam;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaFile;
import sun.awt.X11.Screen;


/**
 * Background parameter (same background for all background. Juste dmaEntry
 * field change).
 *
 * @author Emeric MARTINEAU
 */
public final class BackgroundParamImpl implements BackgroundParam {
    /**
     * Object in file.
     */
    private DmaEntry dmaEntry;

    /**
     * For picture.
     */
    private ShaFile shaFile;

    /**
     * Dma file.
     */
    private DmaFile dmaFile;

    /**
     * Screen type.
     */
    private EnumScreenType screen;

    @Override
    public void init(final ShaFile shaFile, final DmaFile dmaFile,
                     final DmaEntry dmaEntry, final EnumScreenType screen) {
        this.shaFile = shaFile;
        this.dmaEntry = dmaEntry;
        this.dmaFile = dmaFile;
        this.screen = screen;
    }

    /**
     * Dma entry.
     *
     * @return DmaEntry
     */
    public DmaEntry getDmaEntry() {
        return dmaEntry;
    }

    @Override
    public EnumScreenType getScreen() {
        return this.screen;
    }

    /**
     * Picture cache.
     *
     * @return picture cache
     */
    @Override
    public ShaFile getShaFile() {
        return shaFile;
    }

    @Override
    public DmaFile getDmaFile() {
        return this.dmaFile;
    }
}