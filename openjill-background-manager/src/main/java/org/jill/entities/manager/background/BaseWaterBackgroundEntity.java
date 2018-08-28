package org.jill.entities.manager.background;


import org.jill.entities.manager.background.config.basetreewater.BaseTreeWaterConfig;

/**
 * Water.
 *
 * @author Emeric MARTINEAU
 */
public final class BaseWaterBackgroundEntity
        extends AbstractBaseBackgroundEntity<BaseTreeWaterConfig> {
    @Override
    protected Class<BaseTreeWaterConfig> getConfigClass() {
        return BaseTreeWaterConfig.class;
    }

    @Override
    protected String getConfigFilename() {
        return "lake.json";
    }
}