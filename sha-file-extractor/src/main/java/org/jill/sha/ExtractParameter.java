/*

 */
package org.jill.sha;

import java.util.Optional;

/**
 * Parameter of extract
 *
 * @author Emeric MARTINEAU
 */
public class ExtractParameter {
    /**
     * Extract font
     */
    private boolean font = true;

    /**
     * Extract picture
     */
    private boolean picture = true;

    /**
     * Extract picture in CGA mode
     */
    private boolean cgaMode = true;

    /**
     * Extract picture in EGA mode
     */
    private boolean egaMode = true;

    /**
     * Extract picture in VGA mode
     */
    private boolean vgaMode = true;

    /**
     * File name of sha file
     */
    private Optional<String> fileName;

    /**
     * Output dir
     */
    private Optional<String> dirName;

    /**
     * @return fileName
     */
    public Optional<String> getFileName() {
        return fileName;
    }

    /**
     * @param fileName fileName
     */
    public void setFileName(String fileName) {
        if (fileName == null || fileName.trim().length() == 0) {
            this.fileName = Optional.empty();
        } else {
            this.fileName = Optional.of(fileName);
        }
    }

    /**
     * @return dirName
     */
    public Optional<String> getDirName() {
        return dirName;
    }

    /**
     * @param dirName dirName � d�finir
     */
    public void setDirName(String dirName) {
        if (dirName == null || dirName.trim().length() == 0) {
            this.dirName = Optional.empty();
        } else {
            this.dirName = Optional.of(dirName);
        }
    }

    /**
     * @return cgaMode
     */
    public boolean isCgaMode() {
        return cgaMode;
    }

    /**
     * @param cgaMode cgaMode � d�finir
     */
    public void setCgaMode(boolean cgaMode) {
        this.cgaMode = cgaMode;
    }

    /**
     * @return egaMode
     */
    public boolean isEgaMode() {
        return egaMode;
    }

    /**
     * @param egaMode egaMode
     */
    public void setEgaMode(boolean egaMode) {
        this.egaMode = egaMode;
    }

    /**
     * @return vgaMode
     */
    public boolean isVgaMode() {
        return vgaMode;
    }

    /**
     * @param vgaMode vgaMode � d�finir
     */
    public void setVgaMode(boolean vgaMode) {
        this.vgaMode = vgaMode;
    }

    /**
     * @return font
     */
    public boolean isFont() {
        return font;
    }

    /**
     * @param font font
     */
    public void setFont(boolean font) {
        this.font = font;
    }

    /**
     * @return picture
     */
    public boolean isPicture() {
        return picture;
    }

    /**
     * @param picture picture
     */
    public void setPicture(boolean picture) {
        this.picture = picture;
    }
}
