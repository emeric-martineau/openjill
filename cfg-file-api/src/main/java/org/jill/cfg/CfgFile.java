/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jill.cfg;

import java.io.IOException;
import java.util.List;
import org.jill.file.FileAbstractByte;

/**
 *
 * @author emeric_martineau
 */
public interface CfgFile {

    /**
     * Constructor of class.
     *
     * @param cfgFile file name
     * @param prefixSave prefixe of save file
     *
     * @throws IOException if error
     */
    void load(String cfgFile, String prefixSave)
            throws IOException;

    /**
     * Constructor of class.
     *
     * @param cfgFile file name
     * @param prefixSave prefixe of save file
     *
     * @throws IOException if error
     */
    void load(FileAbstractByte cfgFile, String prefixSave)
            throws IOException;

    /**
     * Add new high score.
     *
     * @param name name of player
     * @param score score
     */
    void addNewHighScore(String name, int score);

    /**
     * Add new save game.
     *
     * @param name name of player
     * @param number save number
     *
     * @return save item
     */
    SaveGameItem addNewSaveGame(String name, int number);

    /**
     * Return display mode.
     *
     * @return boolean
     */
    int getDisplayMode();

    /**
     * Return higscore list.
     *
     * @return  hiscore
     */
    List<HighScoreItem> getHighScore();

    /**
     * Return joystick.
     *
     * @return boolean
     */
    int getJoystickCenterX();

    /**
     * Return joystick.
     *
     * @return boolean
     */
    int getJoystickCenterY();

    /**
     * Return joystick.
     *
     * @return boolean
     */
    int getJoystickLeftX();

    /**
     * Return joystick.
     *
     * @return boolean
     */
    int getJoystickLeftY();

    /**
     * Return joystick.
     *
     * @return boolean
     */
    int getJoystickRightX();

    /**
     * Return joystick.
     *
     * @return boolean
     */
    int getJoystickRightY();

    /**
     * Return save list.
     *
     * @return  save list
     */
    List<SaveGameItem> getSaveGame();

    /**
     * Return joystick.
     *
     * @return boolean
     */
    boolean isJoystick();

    /**
     * Return music.
     *
     * @return boolean
     */
    boolean isMusic();

    /**
     * Return setup.
     *
     * @return boolean
     */
    boolean isSetup();

    /**
     * Return sound.
     *
     * @return boolean
     */
    boolean isSound();

    /**
     * Save file to disk.
     *
     * @throws IOException if error
     */
    void save() throws IOException;

    /**
     * Save file to disk.
     *
     * @param filename file name to save
     *
     * @throws IOException if error
     */
    void save(String filename) throws IOException;

}
