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

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.engine.JenesisLanguage;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

/**
 * @Author: Ifunga Ndana
 * @Class: screenDrawer
 * This class draws nd manipulates all sprites, images and effects used in the game
 */
public class DrawUserLogin extends JPanel {

    public static Graphics2D g2d;
    private static String stat1, stat2, stat3, text2 = "";
    private static int timeInt = 0;
    RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //anti aliasing, kill jaggies
    Calendar cal;
    private int offset = 10;
    private Font font;

    public DrawUserLogin(LoginScreen p) {
        font = p.getMyFont(LoginScreen.normalTxtSize);
        cal = Calendar.getInstance();
        setBorder(BorderFactory.createEmptyBorder());
    }

    public static void setStr(String thisText) {
        text2 = thisText;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(310, 100);
    }

    @Override
    public void paintComponent(Graphics g) {
        g2d = (Graphics2D) g;
        g2d.setRenderingHints(renderHints); //activate aliasing

        g2d.setColor(Color.black);
        g2d.fillRoundRect(0, 0, 310, 82, 15, 10); //54+lines x 14

        stat1 = JenesisLanguage.getInstance().getLine(118) + ": " + shortStr(LoginScreen.getInstance().strUser);
        stat2 = JenesisLanguage.getInstance().getLine(119) + ": " + shortStr(LoginScreen.getInstance().strPoint);
        cal.setTimeInMillis(Integer.parseInt(LoginScreen.getInstance().strPlayTime) * 1000);
        stat3 = JenesisLanguage.getInstance().getLine(120) + ": " + timeCal(LoginScreen.getInstance().strPlayTime);


        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString(stat1, offset, 48 - 3);
        g2d.drawString(stat2, offset, (48 - 3) + (14 * 1));
        g2d.drawString(stat3, offset, (48 - 3) + (14 * 2));

        if (LoginScreen.getInstance().isDownloadingMusic) {
            g2d.setColor(Color.black);
            g2d.fillRoundRect(0, 85, 310, 82, 15, 10);
            g2d.setColor(Color.white);
            g2d.drawString("Downloading :: " + LoginScreen.getInstance().currentFile, 10, 120);
            g2d.drawString("Size :: " + LoginScreen.getInstance().trackSize / 1024 + "kb", 10, 132);
            g2d.drawString("Percentage :: " + LoginScreen.getInstance().musicPerc + "%", 10, 144);
        }
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

    public String timeCal(String time) {
        timeInt = Integer.parseInt(time);
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
