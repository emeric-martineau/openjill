package org.jill.jn;

import java.io.EOFException;
import java.io.IOException;
import java.util.Optional;

import org.jill.file.FileAbstractByte;


/**
 * Object of map.
 *
 * @author Emeric MARTINEAU
 * @version 1.0
 */
public class ObjectItemImpl implements ObjectItem {

    /**
     * object type.
     */
    protected int type;
    /**
     * X-coordinate of object.
     */
    protected int x;
    /**
     * Y-coordinate of object.
     */
    protected int y;
    /**
     * object horizontal speed or x direction.
     */
    protected int xSpeed;
    /**
     * object vertical speed or y direction.
     */
    protected int ySpeed;
    /**
     * Width of object.
     */
    protected int width;
    /**
     * Height of object.
     */
    protected int height;
    /**
     * Object sub-type (e.g. what type of "point item"), or current "State"
     * (running, jumping, etc).
     */
    protected int state;
    /**
     * object-specific semantic.
     */
    protected int subState;
    /**
     * object-specific semantic, typically a frame counter.
     */
    protected int stateCount;
    /**
     * Various uses. Often used to link doors or switches to obstacles.
     */
    protected int counter;
    /**
     * internally used for rendering.
     */
    protected int flags;
    /**
     * Used internally as a pointer.
     * If this value is 0, there is no entry for this object in the
     * string stack.
     * Any non-zero value means there is an entry for this object in the string
     * stack.
     */
    protected int pointer;
    /**
     * Use for player to know way of player.
     */
    protected int info1;
    /**
     * Use for object to know if collision with player.
     */
    protected int zapHold;
    /**
     * Index of object in file.
     */
    private int index;
    /**
     * Offset in file of this record.
     */
    private int offset;

    /**
     * String associate to this object.
     */
    private Optional<StringItem> stringStackEntry = Optional.empty();

    /**
     * Constructor.
     *
     * @param jnFile file
     * @param idx    index object in file
     * @throws IOException si erreur
     */
    public ObjectItemImpl(final FileAbstractByte jnFile, final int idx)
            throws IOException {
        this.index = idx;
        this.offset = jnFile.getFilePointer();

        type = jnFile.read8bitLE();
        x = jnFile.read16bitLE();
        y = jnFile.read16bitLE();
        xSpeed = jnFile.readSigned16bitLE();
        ySpeed = jnFile.readSigned16bitLE();
        width = jnFile.read16bitLE();
        height = jnFile.read16bitLE();
        state = jnFile.readSigned16bitLE();
        subState = jnFile.read16bitLE();
        stateCount = jnFile.read16bitLE();
        counter = jnFile.readSigned16bitLE();
        flags = jnFile.read16bitLE();
        pointer = jnFile.read32bitLE();
        info1 = jnFile.readSigned16bitLE();
        zapHold = jnFile.read16bitLE();
    }

    /**
     * Empty constructor.
     */
    public ObjectItemImpl() {

    }

    /**
     * Index of object in file.
     *
     * @return index
     */
    @Override
    public int getIndex() {
        return index;
    }

    /**
     * X.
     *
     * @return x
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * setter of x.
     *
     * @param x value
     */
    @Override
    public void setX(final int x) {
        this.x = x;
    }

    /**
     * Y.
     *
     * @return y
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * setter of y.
     *
     * @param y value
     */
    @Override
    public void setY(final int y) {
        this.y = y;
    }

    /**
     * X speed/direction.
     *
     * @return xSpeed
     */
    @Override
    public int getxSpeed() {
        return xSpeed;
    }

    /**
     * setter of xSpeed.
     *
     * @param xSpeed value
     */
    @Override
    public void setxSpeed(final int xSpeed) {
        this.xSpeed = xSpeed;
    }

    /**
     * Y speed/direction.
     *
     * @return ySpeed
     */
    @Override
    public int getySpeed() {
        return ySpeed;
    }

    /**
     * setter of ySpeed.
     *
     * @param ySpeed vlaue
     */
    @Override
    public void setySpeed(final int ySpeed) {
        this.ySpeed = ySpeed;
    }

    /**
     * Width.
     *
     * @return width
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * setter of width.
     *
     * @param width value
     */
    @Override
    public void setWidth(final int width) {
        this.width = width;
    }

    /**
     * Height.
     *
     * @return height
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * setter of height.
     *
     * @param height value
     */
    @Override
    public void setHeight(final int height) {
        this.height = height;
    }

    /**
     * State.
     *
     * @return state
     */
    @Override
    public int getState() {
        return state;
    }

    /**
     * setter of state.
     *
     * @param state value
     */
    @Override
    public void setState(final int state) {
        this.state = state;
    }

    /**
     * SubState.
     *
     * @return subState
     */
    @Override
    public int getSubState() {
        return subState;
    }

    /**
     * setter of substate.
     *
     * @param subState value
     */
    @Override
    public void setSubState(final int subState) {
        this.subState = subState;
    }

    /**
     * State count.
     *
     * @return stateCount
     */
    @Override
    public int getStateCount() {
        return stateCount;
    }

    /**
     * setter of state count.
     *
     * @param stateCount value
     */
    @Override
    public void setStateCount(final int stateCount) {
        this.stateCount = stateCount;
    }

    /**
     * Counter.
     *
     * @return counter
     */
    @Override
    public int getCounter() {
        return counter;
    }

    /**
     * setter of counter.
     *
     * @param counter value
     */
    @Override
    public void setCounter(final int counter) {
        this.counter = counter;
    }

    /**
     * Flags.
     *
     * @return flags
     */
    @Override
    public int getFlags() {
        return flags;
    }

    /**
     * setter of flags.
     *
     * @param flags value
     */
    @Override
    public void setFlags(final int flags) {
        this.flags = flags;
    }

    /**
     * Pointer on string.
     *
     * @return pointer
     */
    @Override
    public int getPointer() {
        return pointer;
    }

    /**
     * setter of pointer.
     *
     * @param pointer value
     */
    @Override
    public void setPointer(final int pointer) {
        this.pointer = pointer;
    }

    /**
     * Unknow.
     *
     * @return info1
     */
    @Override
    public int getInfo1() {
        return info1;
    }

    /**
     * setter of info1.
     *
     * @param info1 value
     */
    @Override
    public void setInfo1(final int info1) {
        this.info1 = info1;
    }

    /**
     * Unknow.
     *
     * @return zapHold
     */
    @Override
    public int getZapHold() {
        return zapHold;
    }

    /**
     * setter of zaphold.
     *
     * @param zapHold value
     */
    @Override
    public void setZapHold(final int zapHold) {
        this.zapHold = zapHold;
    }

    /**
     * Type (Jill play, apple...).
     *
     * @return type
     */
    @Override
    public int getType() {
        return type;
    }

    /**
     * setter of type.
     *
     * @param type value
     */
    @Override
    public void setType(final int type) {
        this.type = type;
    }

    /**
     * Return offset in file for this object.
     *
     * @return offset in file
     */
    @Override
    public int getOffset() {
        return offset;
    }

    /**
     * String.
     *
     * @return stringStackEntry
     */
    @Override
    public Optional<StringItem> getStringStackEntry() {
        return stringStackEntry;
    }

    /**
     * Sting.
     *
     * @param stringStackEntry string link to this object
     */
    @Override
    public void setStringStackEntry(Optional<StringItem> stringStackEntry) {
        this.stringStackEntry = stringStackEntry;
    }

    /**
     * Return equivalent size in file (include string if set).
     *
     * @return size of this object in file
     */
    @Override
    public int getSizeInFile() {
        if (stringStackEntry.isPresent()) {
            return SIZE_IN_FILE + stringStackEntry.get().getSizeInFile();
        }

        return SIZE_IN_FILE;
    }

    /**
     * Write object to file.
     *
     * @param fab file to write
     * @throws EOFException if error
     */
    @Override
    public void writeToFile(final FileAbstractByte fab) throws EOFException {
        fab.writeByte(type);
        fab.write16bitLE(x);
        fab.write16bitLE(y);
        fab.writeSigned16bitLE(xSpeed);
        fab.writeSigned16bitLE(ySpeed);
        fab.write16bitLE(width);
        fab.write16bitLE(height);
        fab.writeSigned16bitLE(state);
        fab.write16bitLE(subState);
        fab.write16bitLE(stateCount);
        fab.writeSigned16bitLE(counter);
        fab.write16bitLE(flags);
        fab.write32bitLE(pointer);
        fab.writeSigned16bitLE(info1);
        fab.write16bitLE(zapHold);
    }
}
