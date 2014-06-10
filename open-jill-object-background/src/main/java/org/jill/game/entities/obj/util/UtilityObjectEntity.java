package org.jill.game.entities.obj.util;

import org.jill.jn.BackgroundLayer;
import org.jill.openjill.core.api.entities.BackgroundEntity;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.jill.JillConst;

/**
 * Object utilility class.
 *
 * @author Emeric MARTINEAU
 */
public final class UtilityObjectEntity {

    /**
     * Private constructor.
     */
    private UtilityObjectEntity() {
        // Nothing
    }

    /**
     * Check if object can goto this/there block(s).
     *
     * @param startX block x start
     * @param endX block x end
     * @param startY block y start
     * @param endY block end y end
     * @param backgroundObject background map
     *
     * @return true if object can move
     */
    private static BackgroundEntity checkObjectHitFloor(final int startX,
        final int endX, final int startY, final int endY,
        final BackgroundEntity[][] backgroundObject) {
        return checkObjectHitBlockOrStair(startX, endX, startY, endY,
            backgroundObject, true);
    }

    /**
     * Check if object can goto this/there block(s).
     *
     * @param startX block x start
     * @param endX block x end
     * @param startY block y start
     * @param endY block end y end
     * @param backgroundObject background map
     *
     * @return true if object can move
     */
    private static BackgroundEntity checkObjectHitBlock(final int startX,
        final int endX, final int startY, final int endY,
        final BackgroundEntity[][] backgroundObject) {
        return checkObjectHitBlockOrStair(startX, endX, startY, endY,
            backgroundObject, false);
    }

    /**
     * Check if object can goto this/there block(s).
     *
     * @param startX block x start
     * @param endX block x end
     * @param startY block y start
     * @param endY block end y end
     * @param backgroundObject background map
     * @param checkStair check also stair
     *
     * @return true if object can move
     */
    private static BackgroundEntity checkObjectHitBlockOrStair(final int startX,
        final int endX, final int startY, final int endY,
        final BackgroundEntity[][] backgroundObject, final boolean checkStair) {
        boolean objectCanMove;

        // Index of background for check move
        int indexBackX;
        int indexBackY;

        final int lEndX = Math.min(endX, BackgroundLayer.MAP_WIDTH - 1);
        final int lEndY = Math.min(endY, BackgroundLayer.MAP_HEIGHT - 1);

        // Check if floor
        for (indexBackX = startX; indexBackX <= lEndX; indexBackX++) {
            for (indexBackY = startY; indexBackY <= lEndY; indexBackY++) {
                objectCanMove
                    = backgroundObject[indexBackX][indexBackY].isPlayerThru();

                if (checkStair && objectCanMove) {
                    objectCanMove =
                        !backgroundObject[indexBackX][indexBackY].isStair();
                }

                if (!objectCanMove) {
                    return backgroundObject[indexBackX][indexBackY];
                }
            }
        }

        return null;
    }

    /**
     * Check if object can goto this/there block(s).
     *
     * @param startX block x start
     * @param endX block x end
     * @param startY block y start
     * @param endY block end y end
     * @param backgroundObject background map
     *
     * @return true if object can move
     */
    private static BackgroundEntity checkObjectHitVine(final int startX,
        final int endX, final int startY, final int endY,
        final BackgroundEntity[][] backgroundObject) {
        boolean isVine;

        // Index of background for check move
        int indexBackX;
        int indexBackY;

        final int lEndX = Math.min(endX, BackgroundLayer.MAP_WIDTH - 1);
        final int lEndY = Math.min(endY, BackgroundLayer.MAP_HEIGHT - 1);

        // Check if floor
        for (indexBackX = startX; indexBackX <= lEndX; indexBackX++) {
            for (indexBackY = startY; indexBackY <= lEndY; indexBackY++) {
                isVine
                    = backgroundObject[indexBackX][indexBackY].isVine();

                if (isVine) {
                    return backgroundObject[indexBackX][indexBackY];
                }
            }
        }

        return null;
    }

    /**
     * Check if floor ?
     *
     * @param obj object
     * @param backgroundObject background map
     *
     * @return true if can move
     */
    public static boolean checkIfFloorUnderObject(final ObjectEntity obj,
        final BackgroundEntity[][] backgroundObject) {
        return !moveObjectDown(obj, 1, backgroundObject, false);
    }

    /**
     * Move object down.
     *
     * @param obj object
     * @param mvtSize movement size
     * @param backgroundObject background map
     *
     * @return true if can move
     */
    public static boolean moveObjectDown(final ObjectEntity obj,
        final int mvtSize, final BackgroundEntity[][] backgroundObject) {
        return moveObjectDown(obj, mvtSize, backgroundObject, true);
    }

    /**
     * Move object down.
     *
     * @param obj object
     * @param mvtSize movement size
     * @param backgroundObject background map
     * @param updateObj update object position ?
     *
     * @return true if can move
     */
    private static boolean moveObjectDown(final ObjectEntity obj,
        final int mvtSize, final BackgroundEntity[][] backgroundObject,
        final boolean updateObj) {
        boolean canMove;

        // Calculate number of case X
        final int startBlockX = obj.getX() / JillConst.BLOCK_SIZE;
        final int endBlockX = (obj.getX()
            + obj.getWidth() - 1) / JillConst.BLOCK_SIZE;

        // Object is jumping
        int newPosY = obj.getY() + obj.getHeight() + mvtSize;

        if (newPosY > JillConst.MAX_HEIGHT) {
            // Hit top border of screen
            newPosY = JillConst.MAX_HEIGHT - obj.getHeight();
        }

        //final int objPosY = obj.getY() + obj.getHeight();
        final int newStartY = (obj.getY() + obj.getHeight())
            / JillConst.BLOCK_SIZE;
        final int newEndY = newPosY / JillConst.BLOCK_SIZE;

        final BackgroundEntity block = checkObjectHitFloor(
            startBlockX, endBlockX, newStartY, newEndY, backgroundObject);

        if (block == null) {
            if (updateObj) {
                obj.setY(obj.getY() + mvtSize);
            }

            canMove = true;
        } else {
            if (updateObj) {
                // Jump size are to big and object hit a block
                // Set Y to the below block
                obj.setY((block.getY() * JillConst.BLOCK_SIZE)
                        - obj.getHeight());
            }

            canMove = false;
        }

        return canMove;
    }

    /**
     * Move object right.
     *
     * @param obj object
     * @param mvtSize movement size
     * @param backgroundObject background map
     *
     * @return true if can move
     */
    public static boolean moveObjectRight(final ObjectEntity obj,
        final int mvtSize, final BackgroundEntity[][] backgroundObject) {
        boolean canMove;

        // Calculate number of case Y
        final int startBlockY = obj.getY() / JillConst.BLOCK_SIZE;
        final int endBlockY = (obj.getY()
            + obj.getHeight() - 1) / JillConst.BLOCK_SIZE;

        // Calculate X
        int newPosX = obj.getX() + obj.getWidth() + mvtSize;
        int endBlockX = (newPosX - 1) / JillConst.BLOCK_SIZE;

        // Check if can move
        final BackgroundEntity back = checkObjectHitBlock(endBlockX, endBlockX,
            startBlockY, endBlockY, backgroundObject);

        if (back == null) {
            // Object can't out of map
            obj.setX(
                Math.min(obj.getX() + mvtSize,
                    JillConst.MAX_WIDTH - obj.getWidth()));

            canMove = true;
        } else {
            obj.setX((back.getX() * JillConst.BLOCK_SIZE) - obj.getWidth());

            canMove = false;
        }

        return canMove;
    }

    /**
     * Move object left.
     *
     * @param obj object
     * @param mvtSize movement size
     * @param backgroundObject background map
     *
     * @return true if can move
     */
    public static boolean moveObjectLeft(final ObjectEntity obj,
        final int mvtSize, final BackgroundEntity[][] backgroundObject) {
        boolean canMove;

        // Calculate number of case Y
        final int startBlockY = obj.getY() / JillConst.BLOCK_SIZE;
        final int endBlockY = (obj.getY()
            + obj.getHeight() - 1) / JillConst.BLOCK_SIZE;

        // Calculate number of case X
        int newPosX = obj.getX() + mvtSize;
        int startBlockX = newPosX / JillConst.BLOCK_SIZE;

        // Check if can move
        final BackgroundEntity back = checkObjectHitBlock(startBlockX,
            startBlockX, startBlockY, endBlockY, backgroundObject);

        if (back == null) {
            // Object can't have negative X
            obj.setX(
                Math.max(newPosX, 0));

            canMove = true;
        } else {
            obj.setX((back.getX() + 1) * JillConst.BLOCK_SIZE);

            canMove = false;
        }

        return canMove;
    }

    /**
     * Move object up.
     *
     * @param obj object
     * @param mvtSize movement size
     * @param backgroundObject background map
     *
     * @return true if can move
     */
    public static boolean moveObjectUp(final ObjectEntity obj,
        final int mvtSize, final BackgroundEntity[][] backgroundObject) {
        boolean canMove;
        boolean hitBorder = false;

        // Calculate number of case X
        final int startBlockX = obj.getX() / JillConst.BLOCK_SIZE;
        final int endBlockX = (obj.getX()
            + obj.getWidth() - 1) / JillConst.BLOCK_SIZE;

        // Object is jumping
        int newPosY = obj.getY() + mvtSize;

        if (newPosY < 0) {
            // Hit top border of screen
            newPosY = 0;
            hitBorder = true;
        }

        final int newStartY = newPosY / JillConst.BLOCK_SIZE;
        final int newEndY = obj.getY() / JillConst.BLOCK_SIZE;

        final BackgroundEntity block = checkObjectHitBlock(
            startBlockX, endBlockX, newStartY, newEndY, backgroundObject);

        if (block == null) {
            obj.setY(newPosY);

            canMove = !hitBorder;
        } else {
            // Jump size are to big and object hit a block
            // Set Y to the below block
            obj.setY((block.getY() + 1) * JillConst.BLOCK_SIZE);

            canMove = false;
        }

        return canMove;
    }

    /**
     * Check if climb background.
     *
     * @param obj object
     * @param backgroundObject background map
     *
     * @return true if can move
     */
    public static boolean isClimbing(final ObjectEntity obj,
        final BackgroundEntity[][] backgroundObject) {

        final BackgroundEntity block;

        // Now check player is on same position that block
        final int modX = obj.getX() % JillConst.BLOCK_SIZE;

        if (modX == 0) {
            // Calculate number of case X
            final int startBlockX = obj.getX() / JillConst.BLOCK_SIZE;
            final int endBlockX = (obj.getX()
                + obj.getWidth() - 1) / JillConst.BLOCK_SIZE;

            final int newStartY = obj.getY() / JillConst.BLOCK_SIZE;
            final int newEndY = (obj.getY() + obj.getHeight() - 1)
                    / JillConst.BLOCK_SIZE;

            block = checkObjectHitVine(
                startBlockX, endBlockX, newStartY, newEndY, backgroundObject);
        } else {
            block = null;
        }

        return block != null;
    }
}
