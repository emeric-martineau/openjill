package org.jill.game.gui.menu;

import org.jill.cfg.SaveGameItem;
import org.jill.game.gui.tools.LimitedString;
import org.jill.openjill.core.api.manager.TextManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

/**
 * Save game menu.
 *
 * @author Emeric MARTINEAU
 */
public final class SaveGameMenu extends AbstractLoadGameMenu {
    /**
     * Name of high score.
     */
    private final LimitedString nameSave = new LimitedString(
            SaveGameItem.LEN_SAVE_NAME);

    /**
     * Constructor.
     *
     * @param menuScreen          object to draw high score
     * @param textManager cache of picture
     * @param saveGameList        list of high score (can be modified !)
     * @param positionToDrawMenuX x to draw
     * @param positionToDrawMenuY y to draw
     */
    public SaveGameMenu(final BufferedImage menuScreen,
            final TextManager textManager,
            final List<SaveGameItem> saveGameList,
            final int positionToDrawMenuX,
            final int positionToDrawMenuY) {
        this(menuScreen, textManager, saveGameList,
                positionToDrawMenuX,
                positionToDrawMenuY, Optional.empty());
    }

    /**
     * Constructor.
     *
     * @param menuScreen          object to draw high score
     * @param textManager cache of picture
     * @param saveGameList        list of high score (can be modified !)
     * @param positionToDrawMenuX x to draw
     * @param positionToDrawMenuY y to draw
     * @param nextMenuObj         next menu to draw
     */
    public SaveGameMenu(final BufferedImage menuScreen,
            final TextManager textManager,
            final List<SaveGameItem> saveGameList,
            final int positionToDrawMenuX,
            final int positionToDrawMenuY,
            final Optional<MenuInterface> nextMenuObj) {
        super(menuScreen, textManager, saveGameList,
                positionToDrawMenuX, positionToDrawMenuY, nextMenuObj);
    }

    /**
     * Return name of file configuration.
     *
     * @return file name (json format)
     */
    @Override
    protected String getConfigFileName() {
        return "save_game_menu.json";
    }

    /**
     * Enable keyboard grap.
     *
     * @param editMode true to change save name
     */
    @Override
    public void setEditorMode(final boolean editMode) {
        eraseCursor(getSaveGameScreen());

        if (editMode) {
            for (Point p : this.cursorPositionBySubMenuIndex) {
                p.setLocation(getConf().getStartCursorEdit().getX(), p.y);
            }

            // Grap current name of save
            final String currentName =
                    getListSaveGame().get(this.currentMenuPos).getName();

            for (int index = 0; index < currentName.length(); index++) {
                this.nameSave.add(currentName.charAt(index));
            }
        } else {
            for (Point p : this.cursorPositionBySubMenuIndex) {
                p.setLocation(getConf().getStartCursor().getX(), p.y);
            }

            this.currentMenuPos = 0;
            this.cursorIndex = 0;

            this.nameSave.clear();
        }

        super.setEditorMode(editMode);
    }

    /**
     * Return picture to display menu.
     *
     * @return picture
     */
    @Override
    public BufferedImage getPicture() {
        BufferedImage basicPicture;

        // Update cursor position
        final Point cursorPos =
                this.cursorPositionBySubMenuIndex.get(this.currentMenuPos);

        if (getEditMode() && (this.nameSave.size() > 0)) {
            // Create text picture to calculate cursor position
            BufferedImage editTextPicture =
                    textManager.createSmallText(
                            this.nameSave.toString(),
                            getConf().getEditmode().getTextColor(),
                            getBackgroundColor());

            final int oldX = cursorPos.x;

            cursorPos.setLocation(cursorPos.x + editTextPicture.getWidth(),
                    cursorPos.y);

            // Draw basic picture
            basicPicture = super.getPicture();

            Graphics2D g2 = basicPicture.createGraphics();

            // Remove old cursor and letter if change
            g2.setColor(textManager.getColorMap()[getBackgroundColor()]);

            g2.fillRect(cursorPos.x + this.oldCursorBackground.getWidth(),
                    cursorPos.y,
                    getSaveGameScreen().getWidth(),
                    editTextPicture.getHeight());

            // Restore cursor position
            cursorPos.setLocation(oldX, cursorPos.y);

            g2.drawImage(editTextPicture, oldX, cursorPos.y, null);

            g2.dispose();
        } else if (getEditMode()) {
            basicPicture = super.getPicture();

            Graphics2D g2 = basicPicture.createGraphics();

            // Remove old cursor and letter if change
            g2.setColor(textManager.getColorMap()[getBackgroundColor()]);

            g2.fillRect(cursorPos.x + this.oldCursorBackground.getWidth(),
                    cursorPos.y,
                    getSaveGameScreen().getWidth(),
                    this.oldCursorBackground.getHeight());

            g2.dispose();
        } else {
            basicPicture = super.getPicture();
        }

        return basicPicture;
    }

    @Override
    public boolean keyEvent(final char consumeOtherKey) {
        if (this.getEditMode()) {
            this.nameSave.add(consumeOtherKey);
        }

        return super.keyEvent(consumeOtherKey);
    }

    /**
     * Return current name of player that have enter in keyboard.
     *
     * @return name of save selected
     */
    public final String getNameSave() {
        return this.nameSave.toString();
    }

    @Override
    public void left() {
        if (getEditMode()) {
            keyEvent('\b');
        } else {
            up();
        }
    }

    @Override
    public void right() {
        down();
    }

    @Override
    public void setEnable(final boolean en) {
        super.setEnable(en);

        setEditorMode(false);
    }
}
