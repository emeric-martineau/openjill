package org.jill.game.level;

import java.io.File;
import java.io.IOException;

import org.jill.cfg.CfgFile;
import org.jill.dma.DmaFile;
import org.jill.game.config.ObjectInstanceFactory;
import org.jill.game.level.cfg.LevelConfiguration;
import org.jill.jn.JnFile;
import org.jill.openjill.core.api.manager.TileManager;
import org.jill.vcl.VclFile;

/**
 * This class contains basic method for cache file to not reload between level
 * if same file.
 *
 * @author Emeric MARTINEAU
 */
public class AbstractBasicCacheLevel {
    /**
     * Current level configuration.
     */
    protected LevelConfiguration levelConfiguration;
    /**
     * Dma file (mapping between background and picture/flags).
     */
    protected DmaFile dmaFile = null;
    /**
     * Level file.
     */
    protected JnFile jnFile = null;
    /**
     * Level file.
     */
    protected VclFile vclFile = null;
    /**
     * Config file.
     */
    protected CfgFile cfgFile = null;
    /**
     * Picture cache.
     */
    protected TileManager pictureCache;
    /**
     * Name of file JN.
     */
    private String jnFileNameCache;

    /**
     * Return JN file if cached.
     *
     * @param baseFileName file name
     * @param filePath     file path
     * @return jn file
     * @throws IOException if error
     */
    protected final JnFile getJnFile(final String baseFileName,
            final String filePath) throws IOException {
        final JnFile jnFileCache;

        this.jnFileNameCache = baseFileName;
        jnFileCache = ObjectInstanceFactory.getNewJn();
        jnFileCache.load(new File(filePath,
                baseFileName).getAbsolutePath());

        return jnFileCache;
    }

    /**
     * Return Vcl file if cached.
     *
     * @param baseFileName file name
     * @param filePath     file path
     * @return vcl file
     * @throws IOException if error
     */
    protected final VclFile getVclFile(final String baseFileName,
            final String filePath) throws IOException {
        final VclFile vclFileCache;

        vclFileCache = ObjectInstanceFactory.getNewVcl();
        vclFileCache.load(new File(filePath,
                baseFileName).getAbsolutePath());

        return vclFileCache;
    }

    /**
     * Return Cfg file if cached.
     *
     * @param baseFileName file name
     * @param filePath     file path
     * @param cfgPrefixe   prefix of cfg file
     * @return vcl file
     * @throws IOException if error
     */
    protected final CfgFile getCfgFile(final String baseFileName,
            final String filePath, final String cfgPrefixe) throws IOException {
        final CfgFile cfgFileCache;

        cfgFileCache = ObjectInstanceFactory.getNewCfg();
        cfgFileCache.load(
                new File(filePath, baseFileName).getAbsolutePath(),
                cfgPrefixe);

        return cfgFileCache;
    }

    /**
     * Return current JnFile name.
     *
     * @return filename
     */
    protected final String getCurrentJnFileName() {
        return jnFileNameCache;
    }
}
