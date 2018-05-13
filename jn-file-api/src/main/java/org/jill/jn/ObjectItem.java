package org.jill.jn;

import java.io.EOFException;
import java.util.Optional;

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
     * setter of counter.
     *
     * @param counter value
     */
    void setCounter(int counter);

    /**
     * Flags.
     *
     * @return flags
     */
    int getFlags();

    /**
     * setter of flags.
     *
     * @param flags value
     */
    void setFlags(int flags);

    /**
     * Height.
     *
     * @return height
     */
    int getHeight();

    /**
     * setter of height.
     *
     * @param height value
     */
    void setHeight(int height);

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
     * setter of info1.
     *
     * @param info1 value
     */
    void setInfo1(int info1);

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
     * setter of pointer.
     *
     * @param pointer value
     */
    void setPointer(int pointer);

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
     * setter of state.
     *
     * @param state value
     */
    void setState(int state);

    /**
     * State count.
     *
     * @return stateCount
     */
    int getStateCount();

    /**
     * setter of state count.
     *
     * @param stateCount value
     */
    void setStateCount(int stateCount);

    /**
     * String.
     *
     * @return stringStackEntry
     */
    Optional<StringItem> getStringStackEntry();

    /**
     * Sting.
     *
     * @param stringStackEntry string link to this object
     */
    void setStringStackEntry(Optional<StringItem> stringStackEntry);

    /**
     * SubState.
     *
     * @return subState
     */
    int getSubState();

    /**
     * setter of substate.
     *
     * @param subState value
     */
    void setSubState(int subState);

    /**
     * Type (Jill play, apple...).
     *
     * @return type
     */
    int getType();

    /**
     * setter of type.
     *
     * @param type value
     */
    void setType(int type);

    /**
     * Width.
     *
     * @return width
     */
    int getWidth();

    /**
     * setter of width.
     *
     * @param width value
     */
    void setWidth(int width);

    /**
     * X.
     *
     * @return x
     */
    int getX();

    /**
     * setter of x.
     *
     * @param x value
     */
    void setX(int x);

    /**
     * Y.
     *
     * @return y
     */
    int getY();

    /**
     * setter of y.
     *
     * @param y value
     */
    void setY(int y);

    /**
     * Unknow.
     *
     * @return zapHold
     */
    int getZapHold();

    /**
     * setter of zaphold.
     *
     * @param zapHold value
     */
    void setZapHold(int zapHold);

    /**
     * X speed/direction.
     *
     * @return xSpeed
     */
    int getxSpeed();

    /**
     * setter of xSpeed.
     *
     * @param xSpeed value
     */
    void setxSpeed(int xSpeed);

    /**
     * Y speed/direction.
     *
     * @return ySpeed
     */
    int getySpeed();

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
     * @throws EOFException if error
     */
    void writeToFile(FileAbstractByte fab) throws EOFException;

}
