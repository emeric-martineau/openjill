package org.jill.vcl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jill.file.FileAbstractByte;
import org.jill.file.FileAbstractByteImpl;


/**
 * Class to read VCL file who contain sound and text.
 *
 * @author emeric martineau
 * @version 1.0
 */
public class VclFileImpl implements VclFile {
    /**
     * Number of text entry.
     */
    private static final int TEXT_ENRTY_NUMBER = 40;

    /**
     * Sound entry skip.
     */
    private static final int SOUND_ENTRY_SKIP = 400;

    /**
     * Text entries.
     */
    private final List<VclTextEntry> vclText = new ArrayList<>();

    /**
     * Constructor.
     */
    public VclFileImpl() {
        // Nothing
    }

    /**
     * Constructor of class ShaFile.
     *
     * @param vclFile file name
     * @throws IOException if error
     */
    @Override
    public void load(final String vclFile) throws IOException {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(vclFile);

        readTextEntry(f);
    }

    /**
     * Constructor of class ShaFile.
     *
     * @param vclFile file data
     * @throws IOException if error
     */
    @Override
    public void load(final FileAbstractByte vclFile) throws IOException {
        readTextEntry(vclFile);
    }

    /**
     * Read text data in VCL file.
     *
     * @param vclFile file data
     * @throws IOException if error
     */
    private void readTextEntry(final FileAbstractByte vclFile)
            throws IOException {
        // 400     Text Offsets (x40)
        // Each entry is a UINT32. The value is the offset where the text is
        // located.
        final int[] textOffset = new int[TEXT_ENRTY_NUMBER];
        // 560     Text Lengths (x40)
        // Each entry is a UINT16. This is the length, in bytes, of the text.
        final int[] textLength = new int[TEXT_ENRTY_NUMBER];

        vclFile.seek(SOUND_ENTRY_SKIP);

        for (int indexOffset = 0; indexOffset < textOffset.length;
             indexOffset++) {
            textOffset[indexOffset] = vclFile.read32bitLE();
        }

        for (int indexLength = 0; indexLength < textOffset.length;
             indexLength++) {
            textLength[indexLength] = vclFile.read16bitLE();
        }

        final StringBuilder txt = new StringBuilder();


        for (int indexOffset = 0; indexOffset < textOffset.length;
             indexOffset++) {
            if (textLength[indexOffset] > 0) {
                vclFile.seek(textOffset[indexOffset]);

                // Clear string builder data
                txt.delete(0, txt.length());

                for (int indexTxt = 0; indexTxt < textLength[indexOffset];
                     indexTxt++) {
                    txt.append((char) vclFile.read8bitLE());
                }

                // Add string entry
                vclText.add(new VclTextEntryImpl(txt.toString(),
                        textOffset[indexOffset]));
            }
        }
    }

    /**
     * Text.
     *
     * @return vclText
     */
    @Override
    public final List<VclTextEntry> getVclText() {
        return vclText;
    }
}