package org.jill.sha;

import java.awt.Color;

/**
 * Vga Color map.
 *
 * @author emeric_martineau
 */
public final class CgaColorMapImpl implements ColorMap {
    @Override
    public Color[] getColorMap() {
        return InternalColorMapImpl.CGA_COLOR_MAP.getColorMap();
    }
}
