package org.jill.game.entities.obj;

import java.awt.image.BufferedImage;
import java.util.Optional;

import org.jill.game.entities.obj.abs.AbstractParameterObjectEntity;
import org.jill.game.entities.obj.bullet.BulletObjectFactory;
import org.jill.openjill.core.api.entities.ObjectEntity;
import org.jill.openjill.core.api.entities.ObjectParam;
import org.jill.openjill.core.api.keyboard.KeyboardLayout;
import org.jill.openjill.core.api.message.EnumMessageType;
import org.jill.openjill.core.api.message.object.CreateObjectMessage;
import org.jill.openjill.core.api.message.object.ObjectListMessage;
import org.jill.openjill.core.api.message.object.ReplaceObjectMessage;
import org.jill.openjill.core.api.message.statusbar.inventory.EnumInventoryObject;
import org.jill.openjill.core.api.message.statusbar.inventory.InventoryItemMessage;

/**
 * Bonus item manager.
 *
 * @author Emeric MARTINEAU
 */
public final class BonusManager extends AbstractParameterObjectEntity {
    /**
     * Array to know if message is already send.
     */
    private static boolean[] displayMessage = null;

    /**
     * Picture array.
     */
    private BufferedImage images;

    /**
     * Inventory object to add.
     */
    private InventoryItemMessage inventory;

    /**
     * To remove this object from object list.
     */
    private Optional<ObjectListMessage> killme;

    /**
     * Message to display.
     */
    private String msg;

    /**
     * New player.
     */
    private Optional<String> newPlacerClass;

    /**
     * Nb bullet when player change.
     */
    private int nbColoredBullet;

    /**
     * Default constructor.
     *
     * @param objectParam object paramter
     */
    @Override
    public void init(final ObjectParam objectParam) {
        super.init(objectParam);

        final EnumInventoryObject[] enumList
                = EnumInventoryObject.getEnumList();

        // If array of message is not create, create it
        if (displayMessage == null) {
            displayMessage = new boolean[enumList.length];

            for (int index = 0; index < displayMessage.length; index++) {
                displayMessage[index] = true;
            }
        }

        final String nameOfInventoryItem = enumList[counter].toString();

        final String key = getConfString(nameOfInventoryItem);
        final String[] keySplit = key.split(",");

        if (keySplit.length > 2) {
            this.msg = keySplit[2];
        } else {
            // Disable message when bonus havn't message
            displayMessage[counter] = false;
        }

        if (keySplit.length > 3) {
            this.newPlacerClass = Optional.of(keySplit[3]);
            this.nbColoredBullet = getConfInteger("nbColoredBullet");
        } else {
            this.newPlacerClass = Optional.empty();
        }

        // Init list of picture
        final int tileIndex = Integer.valueOf(keySplit[1]);
        final int tileSetIndex = Integer.valueOf(keySplit[0]);

        this.images = this.pictureCache.getImage(tileSetIndex, tileIndex).get();

        final String dontRemove = getConfString("dontRemove");

        if (dontRemove.contains(nameOfInventoryItem)) {
            this.inventory = new InventoryItemMessage(enumList[this.counter],
                    true, true);
            this.killme = Optional.empty();
        } else {
            this.inventory = new InventoryItemMessage(enumList[this.counter],
                    true);
            // Remove me from list of object (= kill me)
            this.killme = Optional.of(new ObjectListMessage(this, false));
        }
    }

    @Override
    public BufferedImage msgDraw() {
        return this.images;
    }

    @Override
    public void msgTouch(final ObjectEntity obj,
            final KeyboardLayout keyboardLayout) {
        if (obj.isPlayer()) {
            sendItem();

            if (this.newPlacerClass.isPresent()) {
                replacePlayer(obj);
            } else {
                this.messageDispatcher.sendMessage(
                        EnumMessageType.INVENTORY_ITEM, this.inventory);
            }
        }
    }

    /**
     * Create player.
     *
     * @param className class name of player
     * @return player object
     */
    private ObjectEntity createPlayer(final String className) {
        final CreateObjectMessage com = CreateObjectMessage
                .buildFromClassName(className);

        this.messageDispatcher.sendMessage(EnumMessageType.CREATE_OBJECT,
                com);

        return com.getObject();
    }

    /**
     * Replace player by object.
     *
     * @param obj new player object
     */
    private void replacePlayer(final ObjectEntity obj) {
        if (!obj.getClass().getName().equals(this.newPlacerClass.get())) {
            final ObjectEntity player = createPlayer(this.newPlacerClass.get());

            player.setX(obj.getX());
            player.setY(obj.getY()
                    + (obj.getHeight() - player.getHeight()));
            player.setInfo1(obj.getInfo1());

            // Create std player and call msgKill
            final ReplaceObjectMessage rom = new ReplaceObjectMessage(obj,
                    player);

            this.messageDispatcher.sendMessage(EnumMessageType.REPLACE_OBJECT,
                    rom);

            BulletObjectFactory.explode(player, this.nbColoredBullet,
                    messageDispatcher);
        }
    }

    /**
     * Send item.
     */
    private void sendItem() {
        if (displayMessage[getCounter()]) {
            // Disable message
            displayMessage[getCounter()] = false;
            // Send message
            sendMessage(this.msg);
        }

        // If don't remove, don't send it.
        if (this.killme.isPresent()) {
            this.messageDispatcher.sendMessage(EnumMessageType.OBJECT,
                    this.killme.get());
        }
    }
}
