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
package com.scnd_genesis.drawing;

import com.scnd_genesis.LoginScreen;
import com.scnd_genesis.engine.*;
import com.scnd_genesis.menus.MenuGameRender;
import com.scnd_genesis.windows.WindowAbout;
import com.scnd_genesis.windows.WindowMain;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.VolatileImage;
import java.io.File;
import java.util.Calendar;

/**
 * @Author: Ifunga Ndana
 * @Class: screenDrawer
 * This class draws nd manipulates all sprites, images and effects used in the game
 */
public class SpecialDrawModeMenu extends JenesisMenu {

    private static final int fontSize = 16;
    public static Graphics2D g2d;
    private static boolean animThread = true;
    private static VolatileImage volatileImg;
    private static float opac = 10;
    private static Font font;
    private static int menuIndex = 0;
    private static int xMenu = 500;
    private static int place = 0, menuItem, menuEntries = 11;
    private static int yMenu = ((576 - fontSize) - (fontSize * (menuEntries + 1))) / 2; //centered, multiply fontSize with number of menu items+1
    private static Image fg, foreGroundA, pic1, foreGroundB;
    private static int xCordCloud = 0, yCordCloud = 0, xCordCloud2 = 0, yCordCloud2 = 20, xCordCloud3 = 0, yCordCloud3 = 40;
    private static String menuItmStr, stat1, stat2, stat3,
            stat4, stat5, stat6, stat7, ach1, ach2, ach3,
            ach4, ach5, stat13, ach6, stat15, stat16, ach7, ach8, text2 = "", stat17;
    private static int timeInt = 0;
    private static int spacer = 12, time;
    RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //anti aliasing, kill jaggies
    private GraphicsEnvironment ge;
    private Color bg = new Color(214, 217, 223);
    private int valCode, screenWidth = 852, screenHeight = 480;
    private GraphicsConfiguration gc;
    private SpecialDrawAchievementLocker achDraw;
    private String mess;
    private boolean runNew = true, fadeOutFeedback;
    private float feedBackOpac = 1.0f;
    private JenesisImage pix = new JenesisImage();
    private Image sgLogo, ndanaSol;
    private Image pointer;
    private String[] itemz;
    private int offset = 10;
    private Calendar cal;
    private Font txt, font2 = new Font("SansSerif", Font.PLAIN, spacer);
    private String[] style = {"Newbie", "Cool!", "Awesome!!", "EPIC!!!"};
    private Image[] achs;
    private float gWin, gLoss, denom, progression;
    private JenesisLanguage langz;
    private JenesisTutorial tut;
    //---blur op
    private int size;
    private float[] data;
    private float sigma, openOpac;
    private float twoSigmaSquare;
    private float sigmaRoot;
    private float total;
    private Kernel kernel;
    //---blur op

    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    public SpecialDrawModeMenu() {
        screenWidth = 852;
        screenHeight = 480;
        openOpac = 3.0f;

        txt = LoginScreen.getLoginScreen().getMyFont(fontSize);


        feedBackOpac = 1.0f;
        fadeOutFeedback = false;

        langz = LoginScreen.getLoginScreen().getLangInst();
        over1 = new JenesisGlassPane();
        itemz = new String[(menuEntries + 2) * 2];
        achDraw = new SpecialDrawAchievementLocker();
        cal = Calendar.getInstance();
        time = (cal.get(Calendar.HOUR_OF_DAY));
        System.out.println("Hour: " + time);
        loadPix();
        font = LoginScreen.getLoginScreen().getMyFont(fontSize - 2);

        ndanaSol = pix.loadImageFromToolkitNoScale("logo/ndana_sol.png");

        sgLogo = pix.loadImageFromToolkitNoScale("images/sglogo.png");
        pointer = pix.loadImageFromToolkitNoScale("images/pointer.png");

        //0 to 8hrs :: Morning
        if (time >= 0 && time <= 9) {
            pic1 = pix.loadImageFromToolkitNoScale("images/blur/bgBG1.png");
            fg = pix.loadImageFromToolkitNoScale("images/blur/bgBG1fg.png");
            foreGroundA = pix.loadImageFromToolkitNoScale("images/blur/bgBG1a.png");
            foreGroundB = pix.loadImageFromToolkitNoScale("images/blur/bgBG1b.png");
        } //9hrs to 16hrs :: Afternoon
        else if (time > 9 && time <= 16) {
            pic1 = pix.loadImageFromToolkitNoScale("images/blur/bgBG6.png");
            fg = pix.loadImageFromToolkitNoScale("images/blur/bgBG6fg.png");
            foreGroundA = pix.loadImageFromToolkitNoScale("images/blur/bgBG6a.png");
            foreGroundB = pix.loadImageFromToolkitNoScale("images/blur/bgBG6b.png");
        } //17 to 24hrs :: Evening
        else if (time > 16 && time <= 24) {
            pic1 = pix.loadImageFromToolkitNoScale("images/blur/bgBG5.png");
            fg = pix.loadImageFromToolkitNoScale("images/blur/bgBG5fg.png");
            foreGroundA = pix.loadImageFromToolkitNoScale("images/blur/bgBG5a.png");
            foreGroundB = pix.loadImageFromToolkitNoScale("images/blur/bgBG5b.png");
        }

        itemz[0] = langz.getLine(307);
        itemz[1] = "STORY MODE";
        itemz[2] = langz.getLine(308);
        itemz[3] = "QUICK MATCH";
        itemz[4] = "Quick Match (2 vs 2)";
        itemz[5] = "QUICK MATCH (2 vs 2)";
        itemz[6] = langz.getLine(309);
        itemz[7] = "HOST A LAN MATCH";
        itemz[8] = langz.getLine(310);
        itemz[9] = "JOIN A LAN MATCH";
        itemz[10] = langz.getLine(311);
        itemz[11] = "YOUR STATS";
        itemz[12] = langz.getLine(312);
        itemz[13] = "OPTIONS";
        itemz[14] = langz.getLine(313);
        itemz[15] = "VIEW CONTROLS";
        itemz[16] = langz.getLine(314);
        itemz[17] = "ABOUT";
        itemz[18] = langz.getLine(315);
        itemz[19] = "EXIT";
        itemz[20] = langz.getLine(316);
        itemz[21] = "ACHIEVEMENT LOCKER";
        itemz[22] = langz.getLine(317);
        itemz[23] = "ONLINE LEADER BOARDS";
        itemz[22] = langz.getLine(318);
        itemz[23] = "LOG OUT";
        itemz[24] = langz.getLine(319);
        itemz[25] = "TUTORIAL";

        new Thread() {

            @Override
            @SuppressWarnings("static-access")
            public void run() {
                try {
                    fadeOutFeedback = false;
                    feedBackOpac = 1.0f;
                    this.sleep(15000);
                    fadeOutFeedback = true;
                } catch (Exception e) {
                }
            }
        }.start();
    }

    /**
     * Get time, dynamic menu
     *
     * @return time
     */
    public static int getTime() {
        return time;
    }

    private static String shortStr(String message) {
        String returnThis;

        if (message.length() < 20) {
            returnThis = message;
        } else {
            returnThis = message.substring(0, 20) + "...";
        }

        return returnThis;
    }

    /**
     * Get menu images for use in character select screen
     *
     * @return pictures
     */
    public static Image[] getPics() {
        return new Image[]{pic1, foreGroundA, foreGroundB, fg};
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(screenWidth, screenHeight); //480p, 16:9 widescreen enhanced definition, max resolution of Nintendo Wii too :D
    }

    /**
     * Refresh achievement stats
     */
    public void refreshStats() {
        achDraw.refreshStats();
    }

    @Override
    public void paintComponent(Graphics g) {
        createBackBuffer();
        //Check if Image is valid
        valCode = volatileImg.validate(gc);
        //if not create new vi, only affects windows apparently
        if (valCode == VolatileImage.IMAGE_INCOMPATIBLE) {
            createBackBuffer();
        }

        /*
        if (openOpac > 0.0f) {
        g2d.setComposite(makeComposite(1));


        //-----

        } else */
        {
            g2d.setComposite(makeComposite(1));
            if (fadeOutFeedback && (feedBackOpac > 0.0f)) {
                feedBackOpac = feedBackOpac - 0.025f;
            }
            g2d.drawImage(pic1, 0, 0, this);
            g2d.drawImage(fg, 0, 0, this);
            g2d.drawImage(foreGroundB, xCordCloud, yCordCloud, this);
            g2d.drawImage(foreGroundA, xCordCloud2, yCordCloud2, this);

            g2d.setColor(Color.BLACK);
            g2d.setComposite(makeComposite(0.50f));
            g2d.fillRect(0, 0, 853, 480);
            g2d.setComposite(makeComposite(1.0f));


            g2d.drawImage(sgLogo, 0, 0, this);

            g2d.setColor(Color.WHITE);
            g2d.setFont(txt);


            if (place == 0) {

                menuItem = 0;
                if (menuIndex == menuItem) {
                    menuItmStr = WindowMain.storyMode;
                    g2d.drawImage(pointer, xMenu - 18, yMenu - 15, this);
                    g2d.drawString(itemz[1], xMenu, yMenu);
                } else {
                    g2d.drawString(itemz[0], xMenu, yMenu);
                }
                menuItem++;

                if (menuIndex == menuItem) {
                    menuItmStr = "vs1";
                    g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                    g2d.drawString(itemz[3], xMenu, yMenu + (fontSize * menuItem));
                } else {
                    g2d.drawString(itemz[2], xMenu, yMenu + (fontSize * menuItem));
                }
                menuItem++;

                /*
                if (menuIndex == menuItem) {
                menuItmStr="vs2";
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                g2d.drawString(itemz[5], xMenu, yMenu + (fontSize * menuItem));
                } else {
                g2d.drawString(itemz[4], xMenu, yMenu + (fontSize * menuItem));
                }
                menuItem++;
                 */

                if (menuIndex == menuItem) {
                    menuItmStr = WindowMain.lanHost;
                    g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                    g2d.drawString(itemz[7], xMenu, yMenu + (fontSize * menuItem));
                } else {
                    g2d.drawString(itemz[6], xMenu, yMenu + (fontSize * menuItem));
                }
                menuItem++;

                if (menuIndex == menuItem) {
                    menuItmStr = WindowMain.lanClient;
                    g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                    g2d.drawString(itemz[9], xMenu, yMenu + (fontSize * menuItem));
                } else {
                    g2d.drawString(itemz[8], xMenu, yMenu + (fontSize * menuItem));
                }
                menuItem++;

                /*
                if (menuIndex == menuItem) {
                menuItmStr = "leaders";
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                g2d.drawString(itemz[23], xMenu, yMenu + (fontSize * menuItem));
                } else {
                g2d.drawString(itemz[22], xMenu, yMenu + (fontSize * menuItem));
                }
                menuItem++;
                 */

                if (menuIndex == menuItem) {
                    menuItmStr = "stats";
                    g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                    g2d.drawString(itemz[11], xMenu, yMenu + (fontSize * menuItem));
                } else {
                    g2d.drawString(itemz[10], xMenu, yMenu + (fontSize * menuItem));
                }
                menuItem++;

                if (menuIndex == menuItem) {
                    menuItmStr = "ach";
                    g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                    g2d.drawString(itemz[21], xMenu, yMenu + (fontSize * menuItem));
                } else {
                    g2d.drawString(itemz[20], xMenu, yMenu + (fontSize * menuItem));
                }
                menuItem++;

                if (menuIndex == menuItem) {
                    menuItmStr = "tutorial";
                    g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                    g2d.drawString(itemz[25], xMenu, yMenu + (fontSize * menuItem));
                } else {
                    g2d.drawString(itemz[24], xMenu, yMenu + (fontSize * menuItem));
                }
                menuItem++;

                if (menuIndex == menuItem) {
                    menuItmStr = "options";
                    g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                    g2d.drawString(itemz[13], xMenu, yMenu + (fontSize * menuItem));
                } else {
                    g2d.drawString(itemz[12], xMenu, yMenu + (fontSize * menuItem));
                }
                menuItem++;


                if (menuIndex == menuItem) {
                    menuItmStr = "controls";
                    g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                    g2d.drawString(itemz[15], xMenu, yMenu + (fontSize * menuItem));
                } else {
                    g2d.drawString(itemz[14], xMenu, yMenu + (fontSize * menuItem));
                }
                menuItem++;

                if (menuIndex == menuItem) {
                    menuItmStr = "logout";
                    g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                    g2d.drawString(itemz[23], xMenu, yMenu + (fontSize * menuItem));
                } else {
                    g2d.drawString(itemz[22], xMenu, yMenu + (fontSize * menuItem));
                }
                menuItem++;

                if (menuIndex == menuItem) {
                    menuItmStr = "about";
                    g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                    g2d.drawString(itemz[17], xMenu, yMenu + (fontSize * menuItem));
                } else {
                    g2d.drawString(itemz[16], xMenu, yMenu + (fontSize * menuItem));
                }
                menuItem++;

                if (menuIndex == menuItem) {
                    menuItmStr = "exit";
                    g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                    g2d.drawString(itemz[19], xMenu, yMenu + (fontSize * menuItem));
                } else {
                    g2d.drawString(itemz[18], xMenu, yMenu + (fontSize * menuItem));
                }
                menuItem++;
            }

            over1.overlay(g2d, this);

            g2d.drawString("The SCND Genesis: Legends " + MenuGameRender.getVersionStr() + " | copyright © " + WindowAbout.year() + " Ifunga Ndana.", 10, screenHeight - 10);
            g2d.setComposite(makeComposite(feedBackOpac));
            mess = "Press 'F' to provide Feedback";
            g2d.drawString(mess, 590, 14);

            mess = "Press 'B' to visit our Blog";
            g2d.drawString(mess, 590, 30);

            mess = "Press 'L' to like us on Facebook";
            g2d.drawString(mess, 590, 46);

            g2d.drawLine(590 - 5, 0, 590 - 5, 46);
            g2d.setComposite(makeComposite(1.0f));

            g2d.setColor(Color.WHITE);

            if (place == 1) {
                achDraw.drawStats(g2d, this);
            }

            if (place == 2) {
                achDraw.drawAch(g2d, this);
            }

            if (place == 3) {
                tut.draw(g2d, this);
            }


            if (xCordCloud < -960) {
                xCordCloud = screenWidth;
            } else {
                xCordCloud = xCordCloud - 1;
            }

            if (xCordCloud2 < -960) {
                xCordCloud2 = screenWidth;
            } else {
                xCordCloud2 = xCordCloud2 - 2;
            }

            if (xCordCloud3 < -960) {
                xCordCloud3 = screenWidth;
            } else {
                xCordCloud3 = xCordCloud3 - 3;
            }
        }
        if (openOpac > 0.0f) {
            if (openOpac <= 1.0f) {
                g2d.setComposite(makeComposite(openOpac));
            }
            g2d.setColor(Color.white);
            g2d.fillRect(0, 0, 852, 480);
            if (openOpac > 2.0f) {
                g2d.setComposite(makeComposite(1.0f));
            } else if (openOpac <= 2.0f && openOpac > 1.0f) {
                g2d.setComposite(makeComposite(openOpac - 1.0f));
            } else {
                g2d.setComposite(makeComposite(0f));
            }
            g2d.drawImage(ndanaSol, 0, 0, this);
            openOpac = openOpac - 0.0125f;
        }
        g.drawImage(volatileImg, 0, 0, this);
    }

    public void stopTut() {
        tut.stopTut();
    }

    public void backTut() {
        tut.backTut();
    }

    public void forwarTut() {
        tut.forwarTut();
    }

    public void startTut() {
        tut.startTut();
    }

    public void sktpToTut(int n) {
        tut.skipToSection(n - 1);
    }

    /**
     * Get the string representation of a mode
     *
     * @return
     */
    public String getMenuModeStr() {
        return menuItmStr;
    }

    /**
     * Hardware acceleration
     */
    private void createBackBuffer() {
        if (runNew) {
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            System.out.println("Accelerateable memory!!!!!!!!!!! " + ge.getDefaultScreenDevice().getAvailableAcceleratedMemory());
            gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
            //optimise, clear unused memory
            if (volatileImg != null) {
                volatileImg.flush();
                volatileImg = null;
            }
            volatileImg = gc.createCompatibleVolatileImage(LoginScreen.getLoginScreen().getGameWidth(), LoginScreen.getLoginScreen().getGameHeight());
            volatileImg.setAccelerationPriority(1.0f);
            g2d = volatileImg.createGraphics();
            g2d.setRenderingHints(renderHints); //activate aliasing
            runNew = false;
        }
    }

    public void StopRepaint() {
        animThread = false;
    }

    public int getXMenu() {
        return xMenu;
    }

    public int getYMenu() {
        return yMenu;
    }

    public int getSpacer() {
        return fontSize;
    }

    public void setMenuPos(int where) {
        menuIndex = where;
    }

    public void goDown() {
        if (menuIndex < menuEntries && place == 0) {
            menuIndex = menuIndex + 1;
        } else if (place == 2) {
            achDraw.scrollDown();
        } else {
            menuIndex = 0;
        }
    }

    public void goUp() {
        if (menuIndex > 0 && place == 0) {
            menuIndex = menuIndex - 1;
        } else if (place == 2) {
            achDraw.scrollUp();
        } else {
            menuIndex = menuEntries;
        }
    }

    public int getMenuPosition() {
        return menuIndex;
    }

    private void loadPix() {
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int here) {
        if (here == 3) {
            tut = new JenesisTutorial();
        }
        place = here;
    }

    private ConvolveOp getGaussianBlurFilter(int radius, boolean horizontal) {
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be >= 1");
        }

        size = radius * 2 + 1;
        data = new float[size];
        sigma = radius / 3.0f;
        twoSigmaSquare = 2.0f * sigma * sigma;
        sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        total = 0.0f;
        for (int i = -radius; i <= radius; i++) {
            float distance = i * i;
            int index = i + radius;
            data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
            total += data[index];
        }

        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }
        kernel = null;
        if (horizontal) {
            kernel = new Kernel(size, 1, data);
        } else {
            kernel = new Kernel(1, size, data);
        }
        return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    }

    /**
     * Gets screenshot
     */
    public void captureScreenShot() {
        try {
            BufferedImage dudeC = volatileImg.getSnapshot();

            File file;

            if (!new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots").exists()) {
                new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots").mkdirs();
            }
            file = new File(System.getProperty("user.home") + File.separator + ".config" + File.separator + "scndgen" + File.separator + "screenshots" + File.separator + DrawGame.generateUID() + ".png");
            ImageIO.write(dudeC, "png", file);
            systemNotice(langz.getLine(170));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
