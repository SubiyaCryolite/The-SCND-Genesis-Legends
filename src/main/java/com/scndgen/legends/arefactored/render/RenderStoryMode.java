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
package com.scndgen.legends.arefactored.render;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.engine.JenesisLanguage;
import com.scndgen.legends.menus.RenderStageSelect;
import com.scndgen.legends.menus.StoryMode;
import com.scndgen.legends.windows.WindowMain;
import io.github.subiyacryolite.enginev1.JenesisGlassPane;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;

import javax.swing.*;
import java.awt.*;

/**
 * @author: Ifunga Ndana
 * @class: drawPrevChar
 * This class creates a graphical preview of the character and opponent
 */
public class RenderStoryMode extends StoryMode {

    private final Font headerFont, normalFont;
    private final JenesisImageLoader pix;
    private Image charBack, loading;
    private Image[] storyCap, storyCapUn, storyCapBlur;
    private Image storyPrev;

    /**
     * Teh constructorz XD
     */
    public RenderStoryMode() {
        scenes = 12;
        headerFont = LoginScreen.getInstance().getMyFont(LoginScreen.extraTxtSize);
        normalFont = LoginScreen.getInstance().getMyFont(LoginScreen.normalTxtSize);
        rows = (scenes / 3);
        storyCapBlur = new Image[scenes];
        storyCap = new Image[scenes];
        storyCapUn = new Image[scenes];
        hiddenStage = new boolean[scenes];
        mode = LoginScreen.getInstance().stage;
        for (int u = 0; u < hiddenStage.length; u++) {
            if (u <= mode)//if you are higher stage enable
            {
                hiddenStage[u] = true;
            } else {
                hiddenStage[u] = false;
            }
        }
        pix = new JenesisImageLoader();
        loadCaps();
        setBorder(BorderFactory.createEmptyBorder());
    }


    @Override
    public void paintComponent(Graphics g) {
        createBackBuffer();
        if (loadingNow) {
            g2d.setColor(Color.BLACK);

            g2d.drawImage(storyPrev, charXcap + x, charYcap, this);
            g2d.setComposite(makeComposite(0.7f));
            g2d.fillRect(0, 0, 852, 480);
            g2d.setComposite(makeComposite(1.0f));


            g2d.setComposite(makeComposite(0.5f));
            g2d.fillRect(200, 0, 452, 480);
            g2d.setComposite(makeComposite(1.0f));

            g2d.drawImage(loading, 316, 183, this); //yCord = 286 - icoHeight
            g2d.setColor(Color.WHITE);
            //g2d.drawString(lang.getLine(165), (852 - g2d.getFontMetrics().stringWidth(lang.getLine(165))) / 2, 200);

        } else if (LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.lanClient) == false) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 852, 480);

            g2d.drawImage(storyPrev, charXcap + x, charYcap, this);

            lastRow = 0;
            //all char caps in this segment
            {
                g2d.setComposite(makeComposite(0.7f));
                g2d.fillRect(0, 0, 852, 480);
                g2d.setComposite(makeComposite(1.0f));


                g2d.setComposite(makeComposite(0.5f));
                g2d.fillRect(200, 0, 452, 480);
                g2d.setComposite(makeComposite(1.0f));

                int col = 0;
                for (int i = 0; i < (storyCap.length / 3); i++) {
                    {
                        g2d.drawImage(storyCap[col], hPos, firstLine + (vSpacer * i), this);
                        if (!hiddenStage[col]) {
                            g2d.drawImage(storyCapBlur[col], hPos, firstLine + (vSpacer * i), this);
                        }
                        col++;
                    }

                    {
                        g2d.drawImage(storyCap[col], hPos + hSpacer, firstLine + (vSpacer * i), this);
                        if (!hiddenStage[col]) {
                            g2d.drawImage(storyCapBlur[col], hPos + hSpacer, firstLine + (vSpacer * i), this);
                        }
                        col++;
                    }

                    {
                        g2d.drawImage(storyCap[col], hPos + (hSpacer * 2), firstLine + (vSpacer * i), this);
                        if (!hiddenStage[col]) {
                            g2d.drawImage(storyCapBlur[col], hPos + (hSpacer * 2), firstLine + (vSpacer * i), this);
                        }
                        col++;
                    }
                    lastRow = i;
                }

                int rem = storyCap.length % 3;

                if (rem == 1) {
                    g2d.drawImage(storyCap[col], hPos, firstLine + (vSpacer * (lastRow + 1)), this);
                    if (!hiddenStage[col]) {
                        g2d.drawImage(storyCapBlur[col], hPos, firstLine + (vSpacer * (lastRow + 1)), this);
                    }
                } else if (rem == 2) {
                    {
                        g2d.drawImage(storyCap[col], hPos, firstLine + (vSpacer * (lastRow + 1)), this);
                        if (!hiddenStage[col]) {
                            g2d.drawImage(storyCapBlur[col], hPos, firstLine + (vSpacer * (lastRow + 1)), this);
                        }
                        col++;
                    }

                    {
                        g2d.drawImage(storyCap[col], hPos + hSpacer, firstLine + (vSpacer * (lastRow + 1)), this);
                        if (!hiddenStage[col]) {
                            g2d.drawImage(storyCapBlur[col], hPos + hSpacer, firstLine + (vSpacer * (lastRow + 1)), this);
                        }
                        col++;
                    }
                }

                g2d.setColor(Color.WHITE);
                g2d.setFont(headerFont);
                g2d.drawString(JenesisLanguage.getInstance().getLine(307), (852 - g2d.getFontMetrics().stringWidth(JenesisLanguage.getInstance().getLine(307))) / 2, 80);
                g2d.setFont(normalFont);
                g2d.drawString(JenesisLanguage.getInstance().getLine(368), (852 - g2d.getFontMetrics().stringWidth(JenesisLanguage.getInstance().getLine(368))) / 2, 380);
                showstoryName(storySelIndex);
                if (well() && hiddenStage[storySelIndex]) {
                    {
                        if (opacity < 0.98f) {
                            opacity = opacity + 0.02f;
                        }
                        g2d.setComposite(makeComposite(opacity));
                        g2d.drawImage(storyCapUn[storySelIndex], (hPos - hSpacer) + (hSpacer * hIndex), firstLine + (vSpacer * vIndex), this);
                        g2d.setComposite(makeComposite(1.0f));
                        g2d.drawImage(charBack, (hPos - hSpacer) + (hSpacer * hIndex), firstLine + (vSpacer * vIndex), this);
                    }
                }

            }
        }
        JenesisGlassPane.getInstance().overlay(g2d, this);
        g.drawImage(volatileImg, 0, 0, this);
    }

    private void loadCaps() {
        RenderStageSelect.selectedStage = false;
        try {
            for (int i = 0; i < scenes; i++) {
                storyCap[i] = pix.loadImageFromToolkitNoScale("images/Story/locked/x" + (i + 1) + ".png");
            }

            for (int i = 0; i < scenes; i++) {
                storyCapUn[i] = pix.loadImageFromToolkitNoScale("images/Story/x" + (i + 1) + ".png");
            }

            for (int i = 0; i < scenes; i++) {
                storyCapBlur[i] = pix.loadImageFromToolkitNoScale("images/Story/blur/t_" + i + ".png");
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        //charBack = imageLoader.loadImageFromToolkitNoScale("images/selstory.png");
        loading = pix.loadImageFromToolkitNoScale("images/appletprogress.gif");
        charBack = pix.loadImageFromToolkitNoScale("images/Story/frame.png");
        int x = (int) (Math.random() * 4);
        switch (x) {
            case 0: {
                storyPrev = pix.loadBufferedImageFromToolkit("images/Story/blur/s4.png");
            }
            break;

            case 1: {
                storyPrev = pix.loadBufferedImageFromToolkit("images/Story/blur/s5.png");
            }
            break;

            case 2: {
                storyPrev = pix.loadBufferedImageFromToolkit("images/Story/blur/s6.png");

            }
            break;

            default: {
                storyPrev = pix.loadBufferedImageFromToolkit("images/Story/blur/s6.png");

            }
            break;
        }
    }
}
