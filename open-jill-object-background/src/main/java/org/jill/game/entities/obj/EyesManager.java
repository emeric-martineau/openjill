package org.jill.game.entities.obj;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.game.entities.obj.player.PlayerPositionSynchronizer;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;

/**
 * Apple object.
 *
 * @author Emeric MARTINEAU
 */
public final class EyesManager extends AbstractParameterObjectEntity {
    /**
     * Player position object.
     */
    private static final PlayerPositionSynchronizer PLAYER_POSITION
        = PlayerPositionSynchronizer.getInstance();
    
    /**
     * Ray circle.
     */
    private double raySize; //Math.sqrt(3 * 3 + 1);
    
    /**
     * Eyes picture.
     */
    private BufferedImage eyesImage;

    /**
     * Lens picture.
     */
    private BufferedImage lensImage;    

    /**
     * Lens X.
     */
    private int lensOriginX;
    
    /**
     * Lens Y.
     */
    private int lensOriginY;
    
    /**
     * To get player position.
     */
    private int indexEtat = 0;

    /**
     * Maximum move size verticaly.
     */
    private int maxMoveY;
    
    /**
     * Maximum move size horizontaly left.
     */
    private int maxMoveXleft;
    
    /**
     * Maximum move size horizontaly right.
     */
    private int maxMoveXright;
    
    /**
     * Default constructor.
     *
     * @param objectParam object parameter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        int tileEyes = getConfInteger("eyesTile");
        int tileLens = getConfInteger("lensTile");
        int tileSetIndex = getConfInteger("tileSet");

        this.lensOriginX = getConfInteger("lensOriginX");
        this.lensOriginY = getConfInteger("lensOriginY");

        this.eyesImage = this.pictureCache.getImage(tileSetIndex, tileEyes);
        
        this.lensImage = this.pictureCache.getImage(tileSetIndex, tileLens);
        
        this.raySize = getConfInteger("raySize");
        this.maxMoveY = getConfInteger("maxMoveY");
        this.maxMoveXleft = getConfInteger("maxMoveXleft");
        this.maxMoveXright = getConfInteger("maxMoveXright");
    }

    @Override
    public BufferedImage msgDraw() {
        BufferedImage currentPicture = new BufferedImage(getWidth(),
                getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g2d = currentPicture.createGraphics();
        
        g2d.drawImage(this.eyesImage, 0, 0, null);

        g2d.drawImage(this.lensImage, this.lensOriginX + getxSpeed(),
                    this.lensOriginY + getySpeed(), null);
        
        g2d.dispose();
        
        return currentPicture;
    }

    @Override
    public void msgUpdate(final KeyboardLayout keyboardLayout) {
        this.indexEtat = PLAYER_POSITION.updatePlayerPosition(
            this.messageDispatcher, this.indexEtat);
        
        final int playerX = PLAYER_POSITION.getX();
        final int playerY = PLAYER_POSITION.getY();
        
        final int ex = getX() + this.lensOriginX;
        final int ey = getY() + this.lensOriginY;
        
        final int relativeX = playerX - ex;
        final int relativeY = playerY - ey;
        
        final double corner = Math.atan((double) Math.abs(relativeY) / Math.abs(relativeX));
        //final double corner = Math.atan2(relativeY, relativeX);
        
        final double dx = (double) this.raySize * Math.cos(corner);
        final double dy = (double) this.raySize * Math.sin(corner);
        
        int newX = (int) Math.round(dx);
        int newY = (int) Math.round(dy);
        
        newY = Math.min(newY, this.maxMoveY);
        
        // dy is always 1 or -1
        if (relativeY < Y_SPEED_MIDDLE) {
            newY *= -1;
        }
        
        if (relativeX < X_SPEED_MIDDLE) {
            // When lens is on right, limit to 2
            newX = Math.min(newX, this.maxMoveXright);
            
            newX *= -1;
        } else if (newX > 3) {
            newX = Math.min(newX, this.maxMoveXleft);
        }
        
        setxSpeed(newX);
        setySpeed(newY);
    }
}
