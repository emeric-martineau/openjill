/*
  Jill of the Jungle tool.
 */
package org.jill.cfg;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jill.file.FileAbstractByte;
import org.jill.file.FileAbstractByteImpl;

/**
 * Class to read CFG file who contain configuration for Jill Trilogy.
 *
 * @author emeric martineau
 * @version 1.0
 */
public class CfgFileImpl implements CfgFile {
    /**
     * Start of asci readble.
     */
    private static final int ASCII_START = 31;

    /**
     * End of asci readble.
     */
    private static final int ASCII_END = 128;

    /**
     * Number of highscore.
     */
    private static final int NUM_HIGH_SCORE_NAME = 10;

    /**
     * Length of highscore.
     */
    private static final int LEN_HIGH_SCORE_NAME = 10;

    /**
     * Number of save.
     */
    private static final int NUM_SAVE_NAME = 6;

    /**
     * Length of save.
     */
    private static final int LEN_SAVE_NAME = 12;

    /**
     * File space.
     */
    private static final byte FILE_SPACE_FILLER = '\0';

    /**
     * Size of CFG file.
     */
    private static final int CFG_FILE_SIZE = 254;

    /**
     * size of whole in file.
     */
    private static final int CFG_FILE_WHOLE = 20;
    /**
     * List of high score.
     */
    private final List<HighScoreItem> highScore =
            new ArrayList<>(NUM_HIGH_SCORE_NAME);
    /**
     * List of save name.
     */
    private final List<SaveGameItem> saveGame = new ArrayList<>(NUM_SAVE_NAME);
    /**
     * If this is set to 1, no setup avaible.
     * Must display setup screen.
     */
    private boolean setup;
    /**
     * Joystick enabled if this is nonzero.
     */
    private boolean joystick;
    /**
     * Joystick X left value.
     */
    private int joystickLeftX;
    /**
     * Joystick X right value.
     */
    private int joystickRightX;
    /**
     * Joystick X center value.
     */
    private int joystickCenterX;
    /**
     * Joystick Y left value.
     */
    private int joystickLeftY;
    /**
     * Joystick X right value.
     */
    private int joystickRightY;
    /**
     * Joystick Y center value.
     */
    private int joystickCenterY;
    /**
     * Display configuration. For Jill: 1=CGA, 2=EGA, 4=VGA.
     */
    private int displayMode;
    /**
     * Music configuration. Nonzero value means it is enabled.
     */
    private boolean music;
    /**
     * Digital sound configuration. Nonzero value means it is enabled.
     */
    private boolean sound;
    /**
     * Current file name.
     */
    private String originalFilename = null;

    /**
     * Constructor of class.
     */
    public CfgFileImpl() {
        // Nothing
    }

    /**
     * Constructor of class.
     *
     * @param cfgFile    file name
     * @param prefixSave prefixe of save file
     * @throws IOException if error
     */
    @Override
    public void load(final String cfgFile, final String prefixSave)
            throws IOException {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(cfgFile);

        load(f, prefixSave);

        originalFilename = cfgFile;
    }


    /**
     * Constructor of class.
     *
     * @param cfgFile    file name
     * @param prefixSave prefixe of save file
     * @throws IOException if error
     */
    @Override
    public void load(final FileAbstractByte cfgFile, final String prefixSave)
            throws IOException {
        final List<String> highScoreName = readHighScoreName(cfgFile);

        // Skip hole byte
        cfgFile.skipBytes(CFG_FILE_WHOLE);

        populateHighScore(cfgFile, highScoreName);

        readSave(cfgFile, prefixSave);

        readCommonConfigurationBlock(cfgFile);
    }

    /**
     * Read high score name.
     *
     * @param cfgFile file
     * @return liste of hiscore
     * @throws IOException if error
     */
    private List<String> readHighScoreName(final FileAbstractByte cfgFile)
            throws IOException {
        final List<String> highScoreName = new ArrayList<>(NUM_HIGH_SCORE_NAME);
        final StringBuilder sb = new StringBuilder(LEN_HIGH_SCORE_NAME);

        int indexChar;
        char c;

        for (int indexName = 0; indexName < NUM_HIGH_SCORE_NAME; indexName++) {
            for (indexChar = 0; indexChar < LEN_HIGH_SCORE_NAME; indexChar++) {
                c = (char) cfgFile.readByte();

                if (c > ASCII_START && c < ASCII_END) {
                    sb.append(c);
                }
            }

            highScoreName.add(sb.toString());

            sb.delete(0, LEN_HIGH_SCORE_NAME);
        }

        return highScoreName;
    }

    /**
     * Read high score.
     *
     * @param cfgFile       file
     * @param highScoreName list hiscore
     * @throws IOException if error
     */
    private void populateHighScore(final FileAbstractByte cfgFile,
            final List<String> highScoreName) throws IOException {
        final String[] hiName = highScoreName.toArray(
                new String[highScoreName.size()]);

        for (int indexName = 0; indexName < NUM_HIGH_SCORE_NAME; indexName++) {
            highScore.add(new HighScoreItemImpl(hiName[indexName],
                    cfgFile.readSigned32bitLE()));
        }
    }

    /**
     * Read save data game.
     *
     * @param cfgFile    file
     * @param prefixSave prefixe of save
     * @throws IOException if error
     */
    private void readSave(final FileAbstractByte cfgFile,
            final String prefixSave) throws IOException {
        final StringBuilder sb = new StringBuilder(LEN_SAVE_NAME);

        int indexChar;
        char c;

        for (int indexName = 0; indexName < NUM_SAVE_NAME; indexName++) {
            for (indexChar = 0; indexChar < LEN_SAVE_NAME; indexChar++) {
                c = (char) cfgFile.readByte();

                if (c > ASCII_START && c < ASCII_END) {
                    sb.append(c);
                } else {
                    // -1 cause current byte is already read
                    cfgFile.skipBytes(LEN_SAVE_NAME - indexChar - 1);
                    break;
                }
            }

            saveGame.add(new SaveGameItemImpl(
                    prefixSave, indexName, sb.toString()));

            sb.delete(0, LEN_SAVE_NAME);
        }
    }

    /**
     * Read common configuration block information.
     *
     * @param cfgFile file
     * @throws IOException if error
     */
    private void readCommonConfigurationBlock(final FileAbstractByte cfgFile)
            throws IOException {
        int value;

        value = cfgFile.readSigned16bitLE();

        setup = value == 1;

        value = cfgFile.readSigned16bitLE();

        joystick = value != 0;

        joystickLeftX = cfgFile.readSigned16bitLE();
        joystickCenterX = cfgFile.readSigned16bitLE();
        joystickRightX = cfgFile.readSigned16bitLE();

        joystickLeftY = cfgFile.readSigned16bitLE();
        joystickCenterY = cfgFile.readSigned16bitLE();
        joystickRightY = cfgFile.readSigned16bitLE();

        displayMode = cfgFile.readSigned16bitLE();

        value = cfgFile.readSigned16bitLE();

        music = value != 0;

        value = cfgFile.readSigned16bitLE();

        sound = value != 0;
    }

    /**
     * Add new high score.
     *
     * @param name  name of player
     * @param score score
     */
    @Override
    public final void addNewHighScore(final String name, final int score) {
        String oldName;
        int oldScore;
        String newName = name;
        int newScore = score;

        for (HighScoreItem hsi : highScore) {
            if (hsi.getScore() < score) {
                oldScore = hsi.getScore();
                oldName = hsi.getName();

                hsi.setScore(newScore);
                hsi.setName(newName);

                newScore = oldScore;
                newName = oldName;
            }
        }
    }

    /**
     * Add new save game.
     *
     * @param name   name of player
     * @param number save number
     * @return save item
     */
    @Override
    public final SaveGameItem addNewSaveGame(final String name,
            final int number) {
        SaveGameItem newSave = this.saveGame.get(number);

        newSave.setName(name);

        return newSave;
    }

    /**
     * Write high score name.
     *
     * @param cfgFile file
     * @throws IOException if error
     */
    private void writeHighScoreName(final FileAbstractByte cfgFile)
            throws IOException {
        for (HighScoreItem hsi : highScore) {
            writeNameHighScoreInFile(cfgFile, hsi.getName());
        }
    }

    /**
     * Write name of high score in file.
     *
     * @param cfgFile file to write
     * @param name    name to write
     * @throws EOFException if end of file
     */
    private void writeNameHighScoreInFile(final FileAbstractByte cfgFile,
            final String name) throws EOFException {
        final char[] byteName = name.toCharArray();
        int indexChar;

        for (indexChar = 0; indexChar < byteName.length; indexChar++) {
            cfgFile.writeByte(byteName[indexChar]);
        }

        for (; indexChar < LEN_HIGH_SCORE_NAME; indexChar++) {
            cfgFile.writeByte(FILE_SPACE_FILLER);
        }
    }

    /**
     * Read high score.
     *
     * @param cfgFile file
     * @throws IOException if error
     */
    private void writeHighScore(final FileAbstractByte cfgFile)
            throws IOException {
        for (HighScoreItem hsi : highScore) {
            cfgFile.writeSigned32bitLE(hsi.getScore());
        }
    }

    /**
     * Write save name.
     *
     * @param cfgFile file
     * @throws IOException if error
     */
    private void writeSaveName(final FileAbstractByte cfgFile)
            throws IOException {
        for (SaveGameItem sgi : saveGame) {
            writeNameSaveInFile(cfgFile, sgi.getName());
        }
    }

    /**
     * Write name of save in file.
     *
     * @param cfgFile file to write
     * @param name    name to write
     * @throws EOFException if error
     */
    private void writeNameSaveInFile(final FileAbstractByte cfgFile,
            final String name) throws EOFException {
        char[] byteName = name.toCharArray();
        int indexChar;

        // Start by to
//        cfgFile.writeByte(FILE_SPACE_FILLER);
//        cfgFile.writeByte(FILE_SPACE_FILLER);

        for (indexChar = 0; indexChar < byteName.length; indexChar++) {
            cfgFile.writeByte(byteName[indexChar]);
        }

        // Add first two 0-byte
        //indexChar += 2;

        for (; indexChar < LEN_SAVE_NAME; indexChar++) {
            cfgFile.writeByte(FILE_SPACE_FILLER);
        }
    }

    /**
     * write common configuration block information.
     *
     * @param cfgFile config file
     * @throws IOException if error
     */
    private void writeCommonConfigurationBlock(final FileAbstractByte cfgFile)
            throws IOException {
        if (setup) {
            cfgFile.writeSigned16bitLE(1);
        } else {
            cfgFile.writeSigned16bitLE(0);
        }

        if (joystick) {
            cfgFile.writeSigned16bitLE(1);
        } else {
            cfgFile.writeSigned16bitLE(0);
        }
        cfgFile.writeSigned16bitLE(joystickLeftX);
        cfgFile.writeSigned16bitLE(joystickCenterX);
        cfgFile.writeSigned16bitLE(joystickRightX);

        cfgFile.writeSigned16bitLE(joystickLeftY);
        cfgFile.writeSigned16bitLE(joystickCenterY);
        cfgFile.writeSigned16bitLE(joystickRightY);

        cfgFile.writeSigned16bitLE(displayMode);

        if (music) {
            cfgFile.writeSigned16bitLE(1);
        } else {
            cfgFile.writeSigned16bitLE(0);
        }

        if (sound) {
            cfgFile.writeSigned16bitLE(1);
        } else {
            cfgFile.writeSigned16bitLE(0);
        }
    }

    /**
     * Save file to disk.
     *
     * @throws IOException if error
     */
    @Override
    public final void save() throws IOException {
        save(originalFilename);
    }

    /**
     * Save file to disk.
     *
     * @param filename file name to save
     * @throws IOException if error
     */
    @Override
    public final void save(final String filename) throws IOException {
        if (filename != null) {
            final FileAbstractByte cfgFile = new FileAbstractByteImpl();

            cfgFile.load(CFG_FILE_SIZE);

            writeHighScoreName(cfgFile);

            // Skip hole byte
            cfgFile.skipBytes(CFG_FILE_WHOLE);

            writeHighScore(cfgFile);

            writeSaveName(cfgFile);

            writeCommonConfigurationBlock(cfgFile);

            cfgFile.saveToFile(filename);
        }
    }

    /**
     * Return setup.
     *
     * @return boolean
     */
    @Override
    public final boolean isSetup() {
        return setup;
    }

    /**
     * Return joystick.
     *
     * @return boolean
     */
    @Override
    public final boolean isJoystick() {
        return joystick;
    }

    /**
     * Return joystick.
     *
     * @return boolean
     */
    @Override
    public final int getJoystickLeftX() {
        return joystickLeftX;
    }

    /**
     * Return joystick.
     *
     * @return boolean
     */
    @Override
    public final int getJoystickRightX() {
        return joystickRightX;
    }

    /**
     * Return joystick.
     *
     * @return boolean
     */
    @Override
    public final int getJoystickCenterX() {
        return joystickCenterX;
    }

    /**
     * Return joystick.
     *
     * @return boolean
     */
    @Override
    public final int getJoystickLeftY() {
        return joystickLeftY;
    }

    /**
     * Return joystick.
     *
     * @return boolean
     */
    @Override
    public final int getJoystickRightY() {
        return joystickRightY;
    }

    /**
     * Return joystick.
     *
     * @return boolean
     */
    @Override
    public final int getJoystickCenterY() {
        return joystickCenterY;
    }

    /**
     * Return display mode.
     *
     * @return boolean
     */
    @Override
    public final int getDisplayMode() {
        return displayMode;
    }

    /**
     * Return music.
     *
     * @return boolean
     */
    @Override
    public final boolean isMusic() {
        return music;
    }

    /**
     * Return sound.
     *
     * @return boolean
     */
    @Override
    public final boolean isSound() {
        return sound;
    }

    /**
     * Return higscore list.
     *
     * @return hiscore
     */
    @Override
    public final List<HighScoreItem> getHighScore() {
        return highScore;
    }

    /**
     * Return save list.
     *
     * @return save list
     */
    @Override
    public final List<SaveGameItem> getSaveGame() {
        return saveGame;
    }
}