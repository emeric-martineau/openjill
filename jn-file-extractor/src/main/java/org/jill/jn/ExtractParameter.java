/*

 */
package org.jill.jn;

/**
 * Command line parameter
 * 
 * @author Emeric MARTINEAU
 *
 */
public class ExtractParameter {
    private String file ;
    private String fileSha ;
    private String fileDma ;
    private String outText ;
    private String outDraw ;
    private boolean drawback = false ;
    private boolean drawobject = false ;
    private boolean dumpback = false ;
    private boolean dumpobject = false ;
    private boolean dumpsave = false ;
    private boolean dumpstring = false ;
    private boolean drawunknowobject = false ;
    private boolean ega = false ;
    private boolean cga = false ;
    private boolean vga = true ;
    
    /**
     * @return file
     */
    public String getFile() {
        return file;
    }
    /**
     * @param file file à définir
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * @return drawback
     */
    public boolean isDrawback() {
        return drawback;
    }
    /**
     * @param drawback drawback à définir
     */
    public void setDrawback(boolean drawback) {
        this.drawback = drawback;
    }
    /**
     * @return drawobject
     */
    public boolean isDrawobject() {
        return drawobject;
    }
    /**
     * @param drawobject drawobject à définir
     */
    public void setDrawobject(boolean drawobject) {
        this.drawobject = drawobject;
    }
    /**
     * @return dumpback
     */
    public boolean isDumpback() {
        return dumpback;
    }
    /**
     * @param dumpback dumpback à définir
     */
    public void setDumpback(boolean dumpback) {
        this.dumpback = dumpback;
    }
    /**
     * @return dumpobject
     */
    public boolean isDumpobject() {
        return dumpobject;
    }
    /**
     * @param dumpobject dumpobject à définir
     */
    public void setDumpobject(boolean dumpobject) {
        this.dumpobject = dumpobject;
    }
    /**
     * @return dumpsave
     */
    public boolean isDumpsave() {
        return dumpsave;
    }
    /**
     * @param dumpsave dumpsave à définir
     */
    public void setDumpsave(boolean dumpsave) {
        this.dumpsave = dumpsave;
    }
    /**
     * @return dumpstring
     */
    public boolean isDumpstring() {
        return dumpstring;
    }
    /**
     * @param dumpstring dumpstring à définir
     */
    public void setDumpstring(boolean dumpstring) {
        this.dumpstring = dumpstring;
    }
    public void setOutText(String outText) {
        this.outText = outText;
    }
    public String getOutText() {
        return outText;
    }
    public void setOutDraw(String outDraw) {
        this.outDraw = outDraw;
    }
    public String getOutDraw() {
        return outDraw;
    }
    public void setFileSha(String fileSha) {
        this.fileSha = fileSha;
    }
    public String getFileSha() {
        return fileSha;
    }
    public void setFileDma(String fileDma) {
        this.fileDma = fileDma;
    }
    public String getFileDma() {
        return fileDma;
    }
    public boolean isDrawunknowobject() {
        return drawunknowobject;
    }
    public void setDrawunknowobject(boolean drawunknowobject) {
        this.drawunknowobject = drawunknowobject;
    }
    public boolean isEga() {
        return ega;
    }
    public void setEga(boolean ega) {
        this.ega = ega;
    }
    public boolean isCga() {
        return cga;
    }
    public void setCga(boolean cga) {
        this.cga = cga;
    }
    public boolean isVga() {
        return vga;
    }
    public void setVga(boolean vga) {
        this.vga = vga;
    }
}
