package org.jill.entities.manager.param;

import org.jill.dma.DmaFile;
import org.jill.jn.BackgroundLayer;
import org.jill.jn.ObjectItem;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.message.MessageDispatcher;
import org.jill.openjill.core.api.screen.EnumScreenType;
import org.jill.sha.ShaFile;

/**
 * Object parameter.
 *
 * @author Emeric MARTINEAU
 */
public class ObjectParamImpl implements ObjectParam {
    /**
     * Object in file.
     */
    private ObjectItem object;

    /**
     * Background map.
     */
    private BackgroundLayer background;

    /**
     * Message dispatcher.
     */
    private MessageDispatcher messageDispatcher;

    /**
     * Current level number.
     */
    private int level;

    /**
     * Sha file.
     */
    private ShaFile shaFile;

    /**
     * Dma file.
     */
    private DmaFile dmaFile;

    /**
     * Screen color.
     */
    private EnumScreenType screen;

    @Override
    public void init(final BackgroundLayer background, final MessageDispatcher messageDispatcherManager,
                     final ShaFile shaFile, final DmaFile dmaFile, final ObjectItem object, final EnumScreenType screen,
                     final int levelNumber) {
        this.background = background;
        this.messageDispatcher = messageDispatcherManager;
        this.shaFile = shaFile;
        this.dmaFile = dmaFile;
        this.object = object;
        this.screen = screen;
        this.level = levelNumber;
    }

    @Override
    public BackgroundLayer getBackground() {
        return background;
    }

    @Override
    public MessageDispatcher getMessageDispatcher() {
        return messageDispatcher;
    }

    @Override
    public ShaFile getShaFile() {
        return shaFile;
    }

    @Override
    public DmaFile getDmaFile() {
        return dmaFile;
    }

    @Override
    public ObjectItem getObject() {
        return object;
    }

    @Override
    public EnumScreenType getScreen() {
        return screen;
    }

    @Override
    public int getLevel() {
        return level;
    }
}
