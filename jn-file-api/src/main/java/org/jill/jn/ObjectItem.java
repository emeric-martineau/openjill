package org.jill.jn;

import java.io.EOFException;
import org.jill.file.FileAbstractByte;

/**
 * Object of map.
 *
 * @author Emeric MARTINEAU
 */
public interface ObjectItem {
    /**
     * Size in file (fixed) without string.
     */
    int SIZE_IN_FILE = 31;

    /**
     * Counter.
     *
     * @return counter
     */
    int getCounter();

    /**
     * Flags.
     *
     * @return flags
     */
    int getFlags();

    /**
     * Height.
     *
     * @return height
     */
    int getHeight();

    /**
     * Index of object in file.
     *
     * @return index
     */
    int getIndex();

    /**
     * Unknow.
     *
     * @return info1
     */
    int getInfo1();

    /**
     * Return offset in file for this object.
     *
     * @return offset in file
     */
    int getOffset();

    /**
     * Pointer on string.
     *
     * @return pointer
     */
    int getPointer();

    /**
     * Return equivalent size in file (include string if set).
     *
     * @return size of this object in file
     */
    int getSizeInFile();

    /**
     * State.
     *
     * @return state
     */
    int getState();

    /**
     * State count.
     *
     * @return stateCount
     */
    int getStateCount();

    /**
     * String.
     *
     * @return stringStackEntry
     */
    StringItem getStringStackEntry();

    /**
     * SubState.
     *
     * @return subState
     */
    int getSubState();

    /**
     * Type (Jill play, apple...).
     *
     * @return type
     */
    int getType();

    /**
     * Width.
     *
     * @return width
     */
    int getWidth();

    /**
     * X.
     *
     * @return x
     */
    int getX();

    /**
     * Y.
     *
     * @return y
     */
    int getY();

    /**
     * Unknow.
     *
     * @return zapHold
     */
    int getZapHold();

    /**
     * X speed/direction.
     *
     * @return xSpeed
     */
    int getxSpeed();

    /**
     * Y speed/direction.
     *
     * @return ySpeed
     */
    int getySpeed();

    /**
     * setter of counter.
     *
     * @param counter value
     */
    void setCounter(int counter);

    /**
     * setter of flags.
     *
     * @param flags value
     */
    void setFlags(int flags);

    /**
     * setter of height.
     *
     * @param height value
     */
    void setHeight(int height);

    /**
     * setter of info1.
     *
     * @param info1 value
     */
    void setInfo1(int info1);

    /**
     * setter of pointer.
     *
     * @param pointer value
     */
    void setPointer(int pointer);

    /**
     * setter of state.
     *
     * @param state value
     */
    void setState(int state);

    /**
     * setter of state count.
     *
     * @param stateCount value
     */
    void setStateCount(int stateCount);

    /**
     * Sting.
     *
     * @param stringStackEntry string link to this object
     */
    void setStringStackEntry(StringItem stringStackEntry);

    /**
     * setter of substate.
     *
     * @param subState value
     */
    void setSubState(int subState);

    /**
     * setter of type.
     *
     * @param type value
     */
    void setType(int type);

    /**
     * setter of width.
     *
     * @param width value
     */
    void setWidth(int width);

    /**
     * setter of x.
     *
     * @param x value
     */
    void setX(int x);

    /**
     * setter of y.
     *
     * @param y value
     */
    void setY(int y);

    /**
     * setter of zaphold.
     *
     * @param zapHold value
     */
    void setZapHold(int zapHold);

    /**
     * setter of xSpeed.
     *
     * @param xSpeed value
     */
    void setxSpeed(int xSpeed);

    /**
     * setter of ySpeed.
     *
     * @param ySpeed vlaue
     */
    void setySpeed(int ySpeed);

    /**
     * Write object to file.
     *
     * @param fab file to write
     *
     * @throws EOFException if error
     */
    void writeToFile(FileAbstractByte fab) throws EOFException;

}
