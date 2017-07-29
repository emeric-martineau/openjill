package org.jill.game.level.cfg;

import org.jill.file.FileAbstractByte;
import org.simplegame.InterfaceSimpleGameHandleInterface;

/**
 * Configuration for level for Jill game.
 *
 * @author Emeric MARTINEAU
 */
public class JillLevelConfiguration extends LevelConfiguration {
    /**
     * Constructor use in case of restoring game (via Restore game menu).
     *
     * @param shaFileName name of SHA file
     * @param jnFileName name of JN file
     * @param vclFileName name of VCL file
     * @param cfgFileName name of CFG file
     * @param cfgSavePrefixe prefix of save file name
     * @param startScreen Start screen for this level
     * @param levelMapData level map data
     * @param levelData level data (if no filename)
     */
    public JillLevelConfiguration(final String shaFileName,
            final String jnFileName, final String vclFileName,
            final String cfgFileName, final String cfgSavePrefixe,
            final Class<?
                    extends InterfaceSimpleGameHandleInterface> startScreen,
            final FileAbstractByte levelMapData,
            final FileAbstractByte levelData) {
        super("JILL.DMA", shaFileName, jnFileName, vclFileName, cfgFileName,
                cfgSavePrefixe, startScreen, levelMapData, levelData);
    }

    /**
     * Constructor use in case of load new game (Map->Level or Level->Map)
     *
     * @param shaFileName name of SHA file
     * @param jnFileName name of JN file
     * @param vclFileName name of VCL file
     * @param cfgFileName name of CFG file
     * @param cfgSavePrefixe prefix of save file name
     * @param startScreen Start screen for this level
     * @param levelNumber number of level
     * @param levelMapData level map data
     * @param levelData level data (if no filename)
     * @param scoreGame score
     * @param numberGemLevel gem
     */
    public JillLevelConfiguration(final String shaFileName,
            final String jnFileName, final String vclFileName,
            final String cfgFileName, final String cfgSavePrefixe,
            final Class<?
                    extends InterfaceSimpleGameHandleInterface> startScreen,
            final int levelNumber, final FileAbstractByte levelMapData,
            final FileAbstractByte levelData,
            final int scoreGame, final int numberGemLevel) {
        super("JILL.DMA", shaFileName, jnFileName, vclFileName, cfgFileName,
                cfgSavePrefixe, startScreen, levelNumber, levelMapData,
                levelData, scoreGame, numberGemLevel);
    }

    /**
     * Constructor use in case of new screen.
     *
     * @param shaFileName name of SHA file
     * @param jnFileName name of JN file
     * @param vclFileName name of VCL file
     * @param cfgFileName name of CFG file
     * @param cfgSavePrefixe prefix of save file name
     */
    public JillLevelConfiguration(final String shaFileName,
            final String jnFileName, final String vclFileName,
            final String cfgFileName, final String cfgSavePrefixe) {
        super("JILL.DMA", shaFileName, jnFileName, vclFileName, cfgFileName,
                cfgSavePrefixe, null, -1, null, null, -1, -1);
    }

    /**
     * Constructor use in case of new screen.
     *
     * @param shaFileName name of SHA file
     * @param jnFileName name of JN file
     * @param vclFileName name of VCL file
     * @param cfgFileName name of CFG file
     * @param cfgSavePrefixe prefix of save file name
     * @param startScreen Start screen for this level
     * @param beginMsg display begin message
     */
    public JillLevelConfiguration(final String shaFileName,
            final String jnFileName, final String vclFileName,
            final String cfgFileName, final String cfgSavePrefixe,
            final Class<?
                    extends InterfaceSimpleGameHandleInterface> startScreen,
            final boolean beginMsg) {
        super("JILL.DMA", shaFileName, jnFileName, vclFileName, cfgFileName,
                cfgSavePrefixe, startScreen, -1, null, null, -1, -1, beginMsg);
    }

    /**
     * Constructor use in case of new screen.
     *
     * @param shaFileName name of SHA file
     * @param jnFileName name of JN file
     * @param vclFileName name of VCL file
     * @param cfgFileName name of CFG file
     * @param cfgSavePrefixe prefix of save file name
     * @param startScreen Start screen for this level
     */
    public JillLevelConfiguration(final String shaFileName,
            final String jnFileName, final String vclFileName,
            final String cfgFileName, final String cfgSavePrefixe,
            final Class<?
                    extends InterfaceSimpleGameHandleInterface> startScreen) {
        super("JILL.DMA", shaFileName, jnFileName, vclFileName, cfgFileName,
                cfgSavePrefixe, startScreen, -1, null, null, -1, -1);
    }

    /**
     * Constructor use in case of new screen.
     *
     * @param shaFileName name of SHA file
     * @param jnFileName name of JN file
     * @param vclFileName name of VCL file
     * @param cfgFileName name of CFG file
     * @param cfgSavePrefixe prefix of save file name
     * @param startScreen Start screen for this level
     * @param levelNumber number of level
     */
    public JillLevelConfiguration(final String shaFileName,
            final String jnFileName, final String vclFileName,
            final String cfgFileName, final String cfgSavePrefixe,
            final Class<?
                    extends InterfaceSimpleGameHandleInterface> startScreen,
            final int levelNumber) {
        super("JILL.DMA", shaFileName, jnFileName, vclFileName, cfgFileName,
                cfgSavePrefixe, startScreen, levelNumber, null, null, 0, -1);
    }
}
