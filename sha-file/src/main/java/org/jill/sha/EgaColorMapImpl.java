package org.jill.sha;

import java.awt.Color;

/**
 * Vga Color map.
 *
 * @author emeric_martineau
 */
public final class EgaColorMapImpl implements ColorMap {
    @Override
    public Color[] getColorMap() {
        return InternalColorMapImpl.EGA_COLOR_MAP.getColorMap();
    }
}
