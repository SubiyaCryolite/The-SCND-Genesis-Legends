package com.scndgen.legends;

import com.scndgen.legends.enums.Mode;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderMainMenu;
import com.scndgen.legends.render.RenderStageSelect;
import com.scndgen.legends.render.RenderStoryMenu;
import io.github.subiyacryolite.enginev1.JenesisEngine;
import io.github.subiyacryolite.enginev1.JenesisGame;
import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Created by ifung on 15/04/2017.
 */
public class ScndGenLegends extends JenesisGame {

    private static ScndGenLegends instance;
    private SubMode subMode;

    public static ScndGenLegends getInstance() {
        return instance;
    }

    public static void main(String[] main) {
        JenesisEngine.applicationStage = ScndGenLegends.class;
        Application.launch(JenesisEngine.class);
    }

    public ScndGenLegends() {
        instance = this;
        setSize(852, 480);
        switchMode(Mode.MAIN_MENU);
    }

    public void switchMode(Mode mode) {
        setSwitchingModes(true);
        try {
            switch (mode) {
                case MAIN_MENU:
                    RenderMainMenu.getInstance().newInstance();
                    setMode(RenderMainMenu.getInstance());
                    break;
                case STORY_SELECT_SCREEN:
                    RenderStoryMenu.getInstance().newInstance();
                    setMode(RenderStoryMenu.getInstance());
                    break;
                case CHAR_SELECT_SCREEN:
                    RenderCharacterSelectionScreen.getInstance().newInstance();
                    setMode(RenderCharacterSelectionScreen.getInstance());
                    break;
                case STAGE_SELECT_SCREEN:
                    RenderStageSelect.getInstance().newInstance();
                    setMode(RenderStageSelect.getInstance());
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            setSwitchingModes(false);
        }
    }

    public SubMode getGameMode() {
        return this.subMode;
    }

    public void setGameMode(SubMode subMode) {
        this.subMode=subMode;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (getMode() != null && !isSwitchingModes())
            getMode().keyReleased(keyEvent);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (getMode() != null && !isSwitchingModes())
            getMode().keyPressed(keyEvent);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        if (getMode() != null && !isSwitchingModes())
            getMode().mouseMoved(mouseEvent);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (getMode() != null && !isSwitchingModes())
            getMode().mouseClicked(mouseEvent);
    }

}
