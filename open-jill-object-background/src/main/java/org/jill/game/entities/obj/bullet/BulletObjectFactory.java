package org.jill.game.entities.obj.bullet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.MessageDispatcher;
import org.jill.openjill.core.api.message.object.CreateObjectMessage;
import org.jill.openjill.core.api.message.object.ObjectListMessage;

/**
 * Create bullet object.
 *
 * @author emeric martineau
 */
public final class BulletObjectFactory {
    /**
     * Object type of bullet.
     */
    private static String bulletObject;

    /**
     * State range.
     */
    private static int stateRange;

    /**
     * XD range.
     */
    private static int xdRange;

    /**
     * XD range substract.
     */
    private static int xdRangeSubstract;

    /**
     * YD range.
     */
    private static int ydRange;

    /**
     * YD range substract.
     */
    private static int ydRangeSubstract;

    static {
        Properties bulletProperties = new Properties();

        try {
            bulletProperties.load(BulletObjectFactory.class.getClassLoader().
                    getResourceAsStream("bullet_factory.properties"));

            bulletObject =
                    bulletProperties.getProperty("bulletObject");
            stateRange = Integer.valueOf(
                    bulletProperties.getProperty("stateRange"));
            xdRange = Integer.valueOf(
                    bulletProperties.getProperty("xdRange"));
            xdRangeSubstract = Integer.valueOf(
                    bulletProperties.getProperty("xdRangeSubstract"));
            ydRange = Integer.valueOf(
                    bulletProperties.getProperty("ydRange"));
            ydRangeSubstract = Integer.valueOf(
                    bulletProperties.getProperty("ydRangeSubstract"));

        } catch (IOException ex) {
            Logger.getLogger(BulletObjectFactory.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    public static void explode(final ObjectEntity objSrc,
            final int nbBullet, final MessageDispatcher messageDispatcher) {
        final List<ObjectEntity> listBullet = new ArrayList<>(nbBullet);

        ObjectEntity obj;

        final CreateObjectMessage com
                = CreateObjectMessage.buildFromClassName(bulletObject);

        for (int index = 0; index < nbBullet; index++) {
            messageDispatcher.sendMessage(EnumMessageType.CREATE_OBJECT, com);

            obj = com.getObject();

            obj.setX(objSrc.getX());
            obj.setY(objSrc.getY());
            obj.setState((int) (Math.random() * stateRange));
            obj.setxSpeed((int) (Math.random() * xdRange) - xdRangeSubstract);
            obj.setySpeed((int) (Math.random() * ydRange) - ydRangeSubstract);

            listBullet.add(obj);
        }

        messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                new ObjectListMessage(listBullet, true)) ;
    }
}
