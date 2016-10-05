package org.jill.game.entities.back;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jill.game.entities.back.abs.AbstractParameterBackgroundEntity;
import org.jill.game.entities.obj.player.PlayerState;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.message.statusbar.inventory.
        InventoryLifeMessage;
import org.jill.game.entities.back.abs.AbstractAnimateBackgroundEntity;

/**
 * Kill 2 background.
 *
 * @author Emeric MARTINEAU
 */
public final class KillWaterBackgroundEntity
        extends AbstractAnimateBackgroundEntity {
    /**
     * Config file name.
     */
    private static final String CONFIG_FILENAME = "background_water_conf.json";

    /**
     * Configuration.
     */
    private static Map<String, Map<String, Boolean>> conf;

    static {
        final ObjectMapper mapper = new ObjectMapper();
        final InputStream is = AbstractParameterBackgroundEntity.class.
                getClassLoader().getResourceAsStream(CONFIG_FILENAME);

        try {
            conf = mapper.readValue(is,
                    new TypeReference<Map<String, Map<String, Boolean>>>() { });
        } catch (IOException e) {
            Logger.getLogger(
                AbstractParameterBackgroundEntity.class.getName()).
                log(Level.SEVERE,
                    "Can't load background config file !", e);
        }
    }

    @Override
    public void msgTouch(final ObjectEntity obj) {
        final Map<String, Boolean> config
                = conf.get(obj.getClass().getSimpleName());

        if (config != null) {
            final Boolean killPlayer = config.get(getName());

            if (killPlayer != null && killPlayer) {
                obj.msgKill(this, InventoryLifeMessage.DEAD_MESSAGE,
                    PlayerState.DIE_SUB_STATE_WATER_BACK);
            }
        }
    }

    @Override
    public void msgDraw() {
        // Nothing
    }
}