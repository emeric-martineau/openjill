package org.jill.game.level.cfg;

import org.jill.file.FileAbstractByte;
import org.simplegame.InterfaceSimpleGameHandleInterface;

/**
 * Configuration of level.
 *
 * @author Emeric MARTINEAU
 */
public class LevelConfiguration {
    /**
     * name of DMA file.
     */
    private final String dmaFileName;

    /**
     * name of SHA file.
     */
    private final String shaFileName;

    /**
     * name of JN file.
     */
    private final String jnFileName;

    /**
     * name of VCL file.
     */
    private final String vclFileName;

    /**
     * name of CFG file.
     */
    private final String cfgFileName;

    /**
     * prefix of save file name.
     */
    private final String cfgSavePrefixe;

    /**
     * Start screen for this level.
     */
    private final Class<? extends InterfaceSimpleGameHandleInterface> startScreen;

    /**
     * Number of level for new level load (if restoreMap == false).
     */
    private final int levelNumber;

    /**
     * Level map data.
     */
    private final FileAbstractByte levelMapData;

    /**
     * Level data.
     */
    private final FileAbstractByte levelData;

    /**
     * If is restore map (true) or new level change (false).
     */
    private final boolean restoreMap;

    /**
     * Score.
     */
    private final int score;

    /**
     * Number of gem.
     */
    private final int numberGem;

    /**
     * Display begin message.
     */
    private boolean displayBeginMessage = true;

    /**
     * Level configuration.
     *
     * @param dmaName          dma file name
     * @param shaName          sha file name
     * @param jnName           jn file name
     * @param vclName          vcl file name
     * @param cfgName          cfg file name
     * @param savePrefixe      prefixe save
     * @param startScreenLevel start screen
     * @param levelNum         level number
     * @param restoringMap     if restore map
     * @param levelMapDataFile map data
     * @param levelDataFile    level data
     * @param scoreGame        score
     * @param numberGemLevel   gem
     */
    protected LevelConfiguration(
            final String dmaName, final String shaName,
            final String jnName, final String vclName,
            final String cfgName, final String savePrefixe,
            final Class<?
                    extends InterfaceSimpleGameHandleInterface>
                    startScreenLevel,
            final int levelNum, final boolean restoringMap,
            final FileAbstractByte levelMapDataFile,
            final FileAbstractByte levelDataFile,
            final int scoreGame, final int numberGemLevel) {
        this.dmaFileName = dmaName;
        this.shaFileName = shaName;
        this.jnFileName = jnName;
        this.vclFileName = vclName;
        this.cfgFileName = cfgName;
        this.cfgSavePrefixe = savePrefixe;
        this.startScreen = startScreenLevel;
        this.levelNumber = levelNum;
        this.restoreMap = restoringMap;
        this.levelMapData = levelMapDataFile;
        this.levelData = levelDataFile;
        this.score = scoreGame;
        this.numberGem = numberGemLevel;
    }

    /**
     * Constructor use in case of retoring game.
     *
     * @param dmaName          name of DMA file
     * @param shaName          name of SHA file
     * @param jnName           name of JN file
     * @param vclName          name of VCL file
     * @param cfgName          name of CFG file
     * @param savePrefixe      prefix of save file name
     * @param startScreenLevel Start screen for this level
     * @param levelMapDataFile level map data
     * @param levelDataFile    level data (if no filename)
     */
    public LevelConfiguration(
            final String dmaName, final String shaName,
            final String jnName, final String vclName,
            final String cfgName, final String savePrefixe,
            final Class<?
                    extends InterfaceSimpleGameHandleInterface>
                    startScreenLevel,
            final FileAbstractByte levelMapDataFile,
            final FileAbstractByte levelDataFile) {
        this(dmaName, shaName, jnName, vclName, cfgName,
                savePrefixe, startScreenLevel, -1, true, levelMapDataFile,
                levelDataFile, -1, -1);
    }

    /**
     * Constructor use in case of load new game.
     *
     * @param dmaName          name of DMA file
     * @param shaName          name of SHA file
     * @param jnName           name of JN file
     * @param vclName          name of VCL file
     * @param cfgName          name of CFG file
     * @param savePrefixe      prefix of save file name
     * @param startScreenLevel Start screen for this level
     * @param levelNum         number of level
     * @param levelMapDataFile level map data
     * @param levelDataFile    level data (if no filename)
     * @param scoreGame        score
     * @param numberGemLevel   gem
     * @param beginMsg         display begin message
     */
    public LevelConfiguration(
            final String dmaName, final String shaName,
            final String jnName, final String vclName,
            final String cfgName, final String savePrefixe,
            final Class<?
                    extends InterfaceSimpleGameHandleInterface>
                    startScreenLevel,
            final int levelNum,
            final FileAbstractByte levelMapDataFile,
            final FileAbstractByte levelDataFile,
            final int scoreGame, final int numberGemLevel,
            final boolean beginMsg) {
        this(dmaName, shaName, jnName, vclName, cfgName,
                savePrefixe, startScreenLevel, levelNum, false,
                levelMapDataFile, levelDataFile, scoreGame, numberGemLevel);
        this.displayBeginMessage = beginMsg;
    }

    /**
     * Constructor use in case of load new game.
     *
     * @param dmaName          name of DMA file
     * @param shaName          name of SHA file
     * @param jnName           name of JN file
     * @param vclName          name of VCL file
     * @param cfgName          name of CFG file
     * @param savePrefixe      prefix of save file name
     * @param startScreenLevel Start screen for this level
     * @param levelNum         number of level
     * @param levelMapDataFile level map data
     * @param levelDataFile    level data (if no filename)
     * @param scoreGame        score
     * @param numberGemLevel   gem
     */
    public LevelConfiguration(
            final String dmaName, final String shaName,
            final String jnName, final String vclName,
            final String cfgName, final String savePrefixe,
            final Class<?
                    extends InterfaceSimpleGameHandleInterface>
                    startScreenLevel,
            final int levelNum,
            final FileAbstractByte levelMapDataFile,
            final FileAbstractByte levelDataFile,
            final int scoreGame, final int numberGemLevel) {
        this(dmaName, shaName, jnName, vclName, cfgName,
                savePrefixe, startScreenLevel, levelNum, false,
                levelMapDataFile, levelDataFile, scoreGame, numberGemLevel);
    }

    /**
     * Dma file name.
     *
     * @return dma file name
     */
    public final String getDmaFileName() {
        return this.dmaFileName;
    }

    /**
     * Sha file name.
     *
     * @return sha file name.
     */
    public final String getShaFileName() {
        return this.shaFileName;
    }

    /**
     * Jn file name.
     *
     * @return Jn file name.
     */
    public final String getJnFileName() {
        return this.jnFileName;
    }

    /**
     * Vcl file name.
     *
     * @return vcl file name.
     */
    public final String getVclFileName() {
        return this.vclFileName;
    }

    /**
     * Cfg file name.
     *
     * @return cfg file name.
     */
    public final String getCfgFileName() {
        return this.cfgFileName;
    }

    /**
     * Prefixe of file save.
     *
     * @return prefixe
     */
    public final String getCfgSavePrefixe() {
        return this.cfgSavePrefixe;
    }

    /**
     * Start screen class.
     *
     * @return class
     */
    public final Class<?
            extends InterfaceSimpleGameHandleInterface> getStartScreen() {
        return this.startScreen;
    }

    /**
     * Level number.
     *
     * @return level
     */
    public final int getLevelNumber() {
        return this.levelNumber;
    }

    /**
     * Map file data.
     *
     * @return data
     */
    public final FileAbstractByte getLevelMapData() {
        return this.levelMapData;
    }

    /**
     * If load game or restore map.
     *
     * @return true => restore map, false => new map
     */
    public final boolean isRestoreMap() {
        return this.restoreMap;
    }

    /**
     * Map data.
     *
     * @return data
     */
    public final FileAbstractByte getLevelData() {
        return this.levelData;
    }

    /**
     * Return score.
     *
     * @return score
     */
    public final int getScore() {
        return this.score;
    }

    /**
     * Return gem.
     *
     * @return gem
     */
    public final int getNumberGem() {
        return this.numberGem;
    }

    /**
     * Display begin message.
     *
     * @return true/false
     */
    public final boolean getDisplayBeginMessage() {
        return this.displayBeginMessage;
    }
}
