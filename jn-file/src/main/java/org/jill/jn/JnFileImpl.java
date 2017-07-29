package org.jill.jn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jill.file.FileAbstractByteImpl;
import org.jill.file.FileAbstractByte;

/**
 * Class to read JN file that contient map and object.
 *
 * @author emeric martineau
 * @version 1.0
 */
public class JnFileImpl implements JnFile {
    /**
     * Dma entries.
     */
    private BackgroundLayer backgroundLayer;

    /**
     * Object layer.
     */
    private List<ObjectItem> objectLayer;

    /**
     * Save data object.
     */
    private SaveData saveData;

    /**
     * List of string.
     */
    private List<StringItem> stringStack;

    /**
     * Constructor.
     */
    public JnFileImpl() {

    }

    /**
     * Constructor.
     *
     * @param jnFile file name
     *
     * @throws IOException if error
     */
    @Override
    public void load(final String jnFile) throws IOException {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(jnFile);

        load(f);
    }

    /**
     * Constructor.
     *
     * @param jnFile file
     *
     * @throws IOException if error
     */
    @Override
    public void load(final FileAbstractByte jnFile) throws IOException {
        backgroundLayer = new BackgroundLayerImpl(jnFile);

        readObjectsLayer(jnFile);

        saveData = new SaveDataImpl(jnFile);

        readStringStack(jnFile);
    }

    /**
     * Read object layer.
     *
     * @param jnFile file data
     *
     * @throws IOException if error
     */
    private void readObjectsLayer(final FileAbstractByte jnFile)
            throws IOException {
        final int nbObject = jnFile.read16bitLE();
        objectLayer = new ArrayList<>(nbObject);

        for (int indexObject = 0; indexObject < nbObject; indexObject++) {
            objectLayer.add(new ObjectItemImpl(jnFile, indexObject));
        }
    }

    /**
     * Return next object item with string pointer.
     *
     * @param itObject iterator
     *
     * @return null if object not found
     */
    private static ObjectItem getNextObjectForString(
            final Iterator<ObjectItem> itObject) {
        ObjectItem obj;

        while (itObject.hasNext()) {
            obj = itObject.next();

            if (obj.getPointer() != 0) {
                return obj;
            }
        }

        return null;
    }

    /**
     * Read string stack.
     *
     * @param jnFile file
     *
     * @throws IOException if error
     */
    private void readStringStack(final FileAbstractByte jnFile)
            throws IOException {
        stringStack = new ArrayList<>();

        final long sizeOfFile = jnFile.length();

        //int indexOfObject = nextObjectNeedString(0);

        // Iterator to object
        final Iterator<ObjectItem> itObject = objectLayer.iterator();

        StringItem string;

        ObjectItem obj;

        while (jnFile.getFilePointer() < sizeOfFile) {
            string = new StringItemImpl(jnFile);

            stringStack.add(string);

            obj = getNextObjectForString(itObject);

            if (obj != null) {
                obj.setStringStackEntry(string);
            }
        }
    }

    /**
     * Return background.
     *
     * @return backgroundLayer background
     */
    @Override
    public final BackgroundLayer getBackgroundLayer() {
        return backgroundLayer;
    }

    /**
     * Return list of object.
     *
     * @return objectLayer object list
     */
    @Override
    public final List<ObjectItem> getObjectLayer() {
        return objectLayer;
    }

    /**
     * Return save data.
     *
     * @return data of save
     */
    @Override
    public final SaveData getSaveData() {
        return saveData;
    }

    /**
     * String of object.
     *
     * @return stringStack string of object
     */
    @Override
    public final List<StringItem> getStringStack() {
        return stringStack;
    }
}