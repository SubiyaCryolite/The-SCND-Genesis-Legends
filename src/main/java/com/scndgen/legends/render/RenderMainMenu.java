package com.scndgen.legends.render;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.enums.Overlay;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.scene.MainMenu;
import com.scndgen.legends.windows.WindowAbout;
import io.github.subiyacryolite.enginev1.JenesisGlassPane;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Created by ifunga on 15/04/2017.
 */
public class RenderMainMenu extends MainMenu {

    private static RenderMainMenu instance;
    private JenesisImageLoader imageLoader = new JenesisImageLoader();
    private Image gameLogo, companyLogo;
    private Image pointer;
    private Image foregroundPixelated, particlesLayer1, backgroundPixelated, particlesLayer2;

    public static synchronized RenderMainMenu getInstance() {
        if (instance == null)
            instance = new RenderMainMenu();
        return instance;
    }

    @Override
    public void newInstance() {
        super.newInstance();
    }

    @Override
    public void loadAssets() {
        if (!loadAssets) return;
        menuFont = LoginScreen.getInstance().getMyFont(fontSize);
        companyLogo = imageLoader.loadImage("logo/ndana_sol.png");
        gameLogo = imageLoader.loadImage("images/sglogo.png");
        pointer = imageLoader.loadImage("images/pointer.png");
        if (time >= 0 && time <= 9) {
            backgroundPixelated = imageLoader.loadImage("images/blur/bgBG1.png");
            foregroundPixelated = imageLoader.loadImage("images/blur/bgBG1fg.png");
            particlesLayer1 = imageLoader.loadImage("images/blur/bgBG1a.png");
            particlesLayer2 = imageLoader.loadImage("images/blur/bgBG1b.png");
        } else if (time > 9 && time <= 16) {
            backgroundPixelated = imageLoader.loadImage("images/blur/bgBG6.png");
            foregroundPixelated = imageLoader.loadImage("images/blur/bgBG6fg.png");
            particlesLayer1 = imageLoader.loadImage("images/blur/bgBG6a.png");
            particlesLayer2 = imageLoader.loadImage("images/blur/bgBG6b.png");
        } else if (time > 16 && time <= 24) {
            backgroundPixelated = imageLoader.loadImage("images/blur/bgBG5.png");
            foregroundPixelated = imageLoader.loadImage("images/blur/bgBG5fg.png");
            particlesLayer1 = imageLoader.loadImage("images/blur/bgBG5a.png");
            particlesLayer2 = imageLoader.loadImage("images/blur/bgBG5b.png");
        }
        loadAssets = false;
    }

    @Override
    public void cleanAssets() {
        gameLogo.flush();
        companyLogo.flush();
        pointer.flush();
        foregroundPixelated.flush();
        particlesLayer1.flush();
        backgroundPixelated.flush();
        particlesLayer2.flush();
        loadAssets = true;
    }

    @Override
    public void paintComponent(Graphics2D g2d, ImageObserver io) {
        loadAssets();
        g2d.setComposite(makeComposite(1));
        if (fadeOutFeedback && (logoFadeOpacity > 0.0f)) {
            logoFadeOpacity = logoFadeOpacity - 0.025f;
        }
        g2d.drawImage(backgroundPixelated, 0, 0, io);
        g2d.drawImage(foregroundPixelated, 0, 0, io);
        g2d.drawImage(particlesLayer2, cloudOnePositionX, yCordCloud, io);
        g2d.drawImage(particlesLayer1, cloudTwoPositionX, yCordCloud2, io);
        g2d.setColor(Color.BLACK);
        g2d.setComposite(makeComposite(0.50f));
        g2d.fillRect(0, 0, screenWidth, screenHeight);
        g2d.setComposite(makeComposite(1.0f));
        g2d.drawImage(gameLogo, 0, 0, io);
        g2d.setColor(Color.WHITE);
        g2d.setFont(menuFont);
        if (overlay == Overlay.PRIMARY) {
            menuItemIndex = 0;
            if (menuIndex == menuItemIndex) {
                menuItmStr = SubMode.STORY_MODE;
                g2d.drawImage(pointer, xMenu - 18, yMenu - 15, io);
                g2d.drawString(menuItem[1], xMenu, yMenu);
            } else {
                g2d.drawString(menuItem[0], xMenu, yMenu);
            }
            menuItemIndex++;
            if (menuIndex == menuItemIndex) {
                menuItmStr = SubMode.SINGLE_PLAYER;
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15, io);
                g2d.drawString(menuItem[3], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                g2d.drawString(menuItem[2], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;
            if (menuIndex == menuItemIndex) {
                menuItmStr = SubMode.LAN_HOST;
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15, io);
                g2d.drawString(menuItem[7], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                g2d.drawString(menuItem[6], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;
            if (menuIndex == menuItemIndex) {
                menuItmStr = SubMode.LAN_CLIENT;
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15, io);
                g2d.drawString(menuItem[9], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                g2d.drawString(menuItem[8], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;

            if (menuIndex == menuItemIndex) {
                menuItmStr = SubMode.STATS;
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15, io);
                g2d.drawString(menuItem[11], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                g2d.drawString(menuItem[10], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;

            if (menuIndex == menuItemIndex) {
                menuItmStr = SubMode.ACH;
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15, io);
                g2d.drawString(menuItem[21], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                g2d.drawString(menuItem[20], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;

            if (menuIndex == menuItemIndex) {
                menuItmStr = SubMode.TUTORIAL;
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15, io);
                g2d.drawString(menuItem[25], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                g2d.drawString(menuItem[24], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;

            if (menuIndex == menuItemIndex) {
                menuItmStr = SubMode.OPTIONS;
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15, io);
                g2d.drawString(menuItem[13], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                g2d.drawString(menuItem[12], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;

            if (menuIndex == menuItemIndex) {
                menuItmStr = SubMode.CONTROLS;
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15, io);
                g2d.drawString(menuItem[15], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                g2d.drawString(menuItem[14], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;

            if (menuIndex == menuItemIndex) {
                menuItmStr = SubMode.LOGOUT;
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15, io);
                g2d.drawString(menuItem[23], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                g2d.drawString(menuItem[22], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;

            if (menuIndex == menuItemIndex) {
                menuItmStr = SubMode.ABOUT;
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15, io);
                g2d.drawString(menuItem[17], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                g2d.drawString(menuItem[16], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;
            if (menuIndex == menuItemIndex) {
                menuItmStr = SubMode.EXIT;
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15, io);
                g2d.drawString(menuItem[19], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                g2d.drawString(menuItem[18], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;
        }

        JenesisGlassPane.getInstance().overlay(g2d, io);
        g2d.drawString("The SCND Genesis: Legends " + RenderGameplay.getInstance().getVersionStr() + " | copyright © " + WindowAbout.year() + " Ifunga Ndana.", 10, screenHeight - 10);
        g2d.setComposite(makeComposite(logoFadeOpacity));
        mess = "Press 'F' to provide Feedback";
        g2d.drawString(mess, 590, 14);
        mess = "Press 'B' to visit our Blog";
        g2d.drawString(mess, 590, 30);
        mess = "Press 'L' to like us on Facebook";
        g2d.drawString(mess, 590, 46);
        g2d.drawLine(590 - 5, 0, 590 - 5, 46);
        g2d.setComposite(makeComposite(1.0f));
        g2d.setColor(Color.WHITE);
        if (overlay == Overlay.STATISTICS) {
            achachievementLocker.drawStats(g2d, io);
        }
        if (overlay == Overlay.ACHIEVEMENTS) {
            achachievementLocker.drawAch(g2d, io);
        }
        if (overlay == Overlay.TUTORIAL) {
            tutorial.draw(g2d, io);
        }
        if (cloudOnePositionX < -960) {
            cloudOnePositionX = screenWidth;
        } else {
            cloudOnePositionX = cloudOnePositionX - 1;
        }
        if (cloudTwoPositionX < -960) {
            cloudTwoPositionX = screenWidth;
        } else {
            cloudTwoPositionX = cloudTwoPositionX - 2;
        }
        if (cloudThreePositionX < -960) {
            cloudThreePositionX = screenWidth;
        } else {
            cloudThreePositionX = cloudThreePositionX - 3;
        }
        if (opactity > 0.0f) {
            if (opactity <= 1.0f) {
                g2d.setComposite(makeComposite(opactity));
            }
            g2d.setColor(Color.white);
            g2d.fillRect(0, 0, 852, 480);
            if (opactity > 2.0f) {
                g2d.setComposite(makeComposite(1.0f));
            } else if (opactity <= 2.0f && opactity > 1.0f) {
                g2d.setComposite(makeComposite(opactity - 1.0f));
            } else {
                g2d.setComposite(makeComposite(0f));
            }
            g2d.drawImage(companyLogo, 0, 0, io);
            opactity = opactity - 0.0125f;
        }
    }


    /**
     * Get menu images for use in characterEnum select screen
     *
     * @return pictures
     */
    public Image[] getPics() {
        return new Image[]{backgroundPixelated, particlesLayer1, particlesLayer2, foregroundPixelated};
    }
}
