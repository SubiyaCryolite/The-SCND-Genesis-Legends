package com.scndgen.legends;

import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.render.*;
import io.github.subiyacryolite.enginev1.Engine;
import io.github.subiyacryolite.enginev1.Game;
import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Created by ifunga on 15/04/2017.
 */
public class ScndGenLegends extends Game {

    private static ScndGenLegends instance;
    private SubMode subMode;
    private ModeEnum modeEnum;
    private double mouseX;
    private double mouseY;

    public static ScndGenLegends getInstance() {
        return instance;
    }

    public static void main(String[] main) {
        Engine.applicationStage = ScndGenLegends.class;
        Application.launch(Engine.class);
    }

    public ScndGenLegends() {
        instance = this;
        setSize(852, 480);
        loadMode(ModeEnum.MAIN_MENU);
    }

    public void loadMode(ModeEnum modeEnum) {
        this.modeEnum = modeEnum;
        setSwitchingModes(true);
        try {
            switch (modeEnum) {
                case MAIN_MENU:
                    RenderMainMenu.getInstance().newInstance();
                    setMode(RenderMainMenu.getInstance());
                    break;
                case STORY_SELECT_SCREEN:
                    RenderStoryMenu.getInstance().newInstance();
                    setMode(RenderStoryMenu.getInstance());
                    break;
                case CHAR_SELECT_SCREEN:
                    RenderCharacterSelection.getInstance().newInstance();
                    setMode(RenderCharacterSelection.getInstance());
                    break;
                case STAGE_SELECT_SCREEN:
                    RenderStageSelect.getInstance().newInstance();
                    setMode(RenderStageSelect.getInstance());
                    break;
                case STANDARD_GAMEPLAY_START:
                    //stopBackgroundMusic();
                    RenderGamePlay.getInstance().newInstance();
                    setMode(RenderGamePlay.getInstance());
                    RenderGamePlay.getInstance().startFight();
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            setSwitchingModes(false);
        }
    }

    public SubMode getSubMode() {
        return this.subMode;
    }


    public void setSubMode(SubMode subMode) {
        this.subMode = subMode;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (this.getMode() != null && !isSwitchingModes())
            this.getMode().keyReleased(keyEvent);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (this.getMode() != null && !isSwitchingModes())
            this.getMode().keyPressed(keyEvent);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        setMouseX(mouseEvent.getX());
        setMouseY(mouseEvent.getY());
        if (this.getMode() != null && !isSwitchingModes())
            this.getMode().mouseMoved(mouseEvent);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (this.getMode() != null && !isSwitchingModes())
            this.getMode().mouseClicked(mouseEvent);
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }
}
