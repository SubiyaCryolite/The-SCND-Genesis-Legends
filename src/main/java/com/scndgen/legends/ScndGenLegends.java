/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([http://www.scndgen.com]))).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <http://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends;

import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.network.NetworkManager;
import com.scndgen.legends.render.*;
import io.github.subiyacryolite.enginev1.AudioPlayback;
import io.github.subiyacryolite.enginev1.Engine;
import io.github.subiyacryolite.enginev1.FxDialogs;
import io.github.subiyacryolite.enginev1.Game;
import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.WindowEvent;

/**
 * Created by ifunga on 15/04/2017.
 */
public class ScndGenLegends extends Game {

    private static ScndGenLegends instance;
    private SubMode subMode;
    private ModeEnum modeEnum;
    private double mouseX;
    private double mouseY;
    private String targetIp = "192.168.1.103";

    public static ScndGenLegends get() {
        return instance;
    }

    public static void main(String[] main) {
        Engine.gameClass = ScndGenLegends.class;
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
                    RenderMainMenu.get().newInstance();
                    setMode(RenderMainMenu.get());
                    break;
                case STORY_SELECT_SCREEN:
                    RenderStoryMenu.get().newInstance();
                    setMode(RenderStoryMenu.get());
                    break;
                case CHAR_SELECT_SCREEN:
                    RenderCharacterSelection.get().newInstance();
                    switch (getSubMode()) {
                        case LAN_HOST:
                            NetworkManager.get().asHost();
                            break;
                        case LAN_CLIENT:
                            targetIp = FxDialogs.input("title", "header", "content", targetIp);
                            NetworkManager.get().asClient(targetIp);
                            break;
                    }
                    setMode(RenderCharacterSelection.get());
                    break;
                case STAGE_SELECT_SCREEN:
                    RenderStageSelect.get().newInstance();
                    setMode(RenderStageSelect.get());
                    break;
                case STANDARD_GAMEPLAY_START:
                    RenderGamePlay.get().newInstance();
                    setMode(RenderGamePlay.get());
                    RenderGamePlay.get().startFight();
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
    public void onKeyReleased(KeyEvent keyEvent) {
        if (this.getMode() != null && !isSwitchingModes())
            this.getMode().keyReleased(keyEvent);
    }

    @Override
    public void onKeyPressed(KeyEvent keyEvent) {
        if (this.getMode() != null && !isSwitchingModes())
            this.getMode().keyPressed(keyEvent);
    }

    @Override
    public void onMouseMoved(MouseEvent mouseEvent) {
        setMouseX(mouseEvent.getX());
        setMouseY(mouseEvent.getY());
        if (this.getMode() != null && !isSwitchingModes())
            this.getMode().mouseMoved(mouseEvent);
    }

    @Override
    public void onMouseClicked(MouseEvent mouseEvent) {
        if (this.getMode() != null && !isSwitchingModes())
            this.getMode().mouseClicked(mouseEvent);
    }

    public void onScroll(ScrollEvent scrollEvent) {
        if (this.getMode() != null && !isSwitchingModes())
            this.getMode().mouseScrolled(scrollEvent);
    }

    public void shutDown() {
        NetworkManager.get().close();
        AudioPlayback.closeAll();
    }

    public void onCloseRequest(WindowEvent closeRequest) {
        shutDown();
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
