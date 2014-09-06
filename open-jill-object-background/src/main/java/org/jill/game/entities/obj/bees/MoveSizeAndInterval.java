package org.jill.game.entities.obj.bees;

/**
 * Move and size by interval.
 *
 * @author Emeric MARTINEAU
 */
public final class MoveSizeAndInterval {
    /**
     * State bound in config.
     */
    private static final int STATE_BOUND = 0;

    /**
     * Range value.
     */
    private static final int RANGE_VALUE = 1;

    /**
     * State value.
     */
    private final int stateBound;

    /**
     * If is range.
     */
    private final boolean range;

    /**
     * Range value up.
     */
    private final int rangeUp;

    /**
     * Range value down.
     */
    private final int rangeDown;

    /**
     * Move and size interval.
     *
     * @param cfg configuration from file
     */
    public MoveSizeAndInterval(final String cfg) {
        //32:2-4 -> range random
        String[] items = cfg.split(":");

        this.stateBound = Integer.valueOf(items[STATE_BOUND]);

        this.range = items[RANGE_VALUE].contains("-");

        if (this.range) {
            String[] items2 = items[RANGE_VALUE].split("-");

            this.rangeDown = Integer.valueOf(items2[0]);
            this.rangeUp = Integer.valueOf(items2[1]);
        } else {
            this.rangeDown = Integer.valueOf(items[RANGE_VALUE]);
            this.rangeUp = Integer.MAX_VALUE;
        }
    }

    /**
     * Get value.
     *
     * @return value
     */
    public int get() {
        int value;

        if (this.range) {
            value = ((int) (Math.random() * (this.rangeUp - this.rangeDown)))
                    + this.rangeDown;
        } else {
            value = this.rangeDown;
        }

        return value;
    }

    /**
     * Bound of state.
     *
     * @return bound
     */
    public int getBound() {
        return this.stateBound;
    }
}
