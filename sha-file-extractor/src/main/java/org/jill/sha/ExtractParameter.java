/*

 */
package org.jill.sha;

/**
 * Parameter of extract
 *
 * @authorEmeric MARTINEAU
 */
public class ExtractParameter {
    /**
     * Extract font
     */
    private boolean font = true ;

    /**
     * Extract picture
     */
    private boolean picture = true ;

    /**
     * Extract picture in CGA mode
     */
    private boolean cgaMode = true ;

    /**
     * Extract picture in EGA mode
     */
    private boolean egaMode = true ;

    /**
     * Extract picture in VGA mode
     */
    private boolean vgaMode = true ;

    /**
     * File name of sha file
     */
    private String fileName ;

    /**
     * Output dir
     */
    private String dirName ;

    /**
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return dirName
     */
    public String getDirName() {
        return dirName;
    }

    /**
     * @param dirName dirName � d�finir
     */
    public void setDirName(String dirName) {
        this.dirName = dirName;
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
