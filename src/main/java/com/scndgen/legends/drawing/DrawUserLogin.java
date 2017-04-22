/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (http://www.scndgen.sf.net).

 The SCND Genesis: Legends  © 2011 Ifunga Ndana.

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
package com.scndgen.legends.drawing;

import com.scndgen.legends.state.GameState;
import com.scndgen.legends.Language;
import com.scndgen.legends.LoginScreen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Calendar;

/**
 * @Author: Ifunga Ndana
 * @Class: screenDrawer
 * This class draws nd manipulates all sprites, images and effects used in the game
 */
public class DrawUserLogin extends Pane {

    private String stat1, stat2, stat3;
    Calendar cal;
    private int offset = 10;
    private Font font;

    public DrawUserLogin(LoginScreen p) {
        //font = p.getMyFont(LoginScreen.normalTxtSize);
        cal = Calendar.getInstance();
        setPrefSize(310, 100);
    }

    public void render(GraphicsContext gc, double x, double y) {
        gc.setFill(Color.BLACK);
        gc.fillRoundRect(0, 0, 310, 82, 15, 10); //54+lines x 14
        stat1 = Language.getInstance().get(118) + ": " + shortStr(GameState.getInstance().getLogin().getUserName());
        stat2 = Language.getInstance().get(119) + ": " + shortStr(GameState.getInstance().getLogin().getPoints() + "");
        cal.setTimeInMillis(GameState.getInstance().getLogin().getPlayTime() * 1000);
        stat3 = Language.getInstance().get(120) + ": " + timeCal(GameState.getInstance().getLogin().getPlayTime());
        gc.setFill(Color.WHITE);
        gc.setFont(font);
        gc.fillText(stat1, offset, 48 - 3);
        gc.fillText(stat2, offset, (48 - 3) + (14 * 1));
        gc.fillText(stat3, offset, (48 - 3) + (14 * 2));
    }

    private String shortStr(String message) {
        String returnThis;
        if (message.length() < 20) {
            returnThis = message;
        } else {
            returnThis = message.substring(0, 20) + "...";
        }
        return returnThis;
    }

    public String timeCal(int timeInt) {
        String moi = "";
        //minutes
        if (timeInt > -1 && timeInt <= 3600) {
            int minutes = timeInt / 60;
            int seconds = timeInt - (minutes * 60);
            moi = minutes + " minutes and " + seconds + " seconds";
        }
        if (timeInt > 3600 && timeInt <= 86400) {
            int hours = timeInt / 3600;
            int minutes = (timeInt - (hours * 3600)) / 60;
            int seconds = timeInt - ((minutes * 60) + (hours * 3600));
            moi = hours + " hours, " + minutes + " mins and " + seconds + " secs";
        }
        if (timeInt > 86400 && timeInt <= 604800) {
            int days = timeInt / 86400;
            int hours = (days * 86400) / 3600;
            int minutes = (timeInt - (hours * 3600) - (days * 86400)) / 60;
            int seconds = timeInt - ((minutes * 60) + (hours * 3600) + (days * 86400));
            moi = days + " days " + hours + "hrs, " + minutes + " mins and " + seconds + " secs";
        }
        return moi;
    }
}
