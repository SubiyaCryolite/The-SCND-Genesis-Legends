package com.scndgen.legends.render;

import com.scndgen.legends.enums.Overlay;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.scene.MainMenu;
import com.scndgen.legends.windows.WindowAbout;
import io.github.subiyacryolite.enginev1.JenesisOverlay;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


/**
 * Created by ifunga on 15/04/2017.
 */
public class RenderMainMenu extends MainMenu {

    private static RenderMainMenu instance;
    private JenesisImageLoader imageLoader = new JenesisImageLoader();
    private Image menuLogo, gameLogo;
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
        menuFont = getMyFont(fontSize);
        gameLogo = imageLoader.loadImage("logo/gameLogo");
        menuLogo = imageLoader.loadImage("images/sglogo.png");
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
        menuLogo = null;
        gameLogo = null;
        pointer = null;
        foregroundPixelated = null;
        particlesLayer1 = null;
        backgroundPixelated = null;
        particlesLayer2 = null;
        loadAssets = true;
    }

    @Override
    public void render(GraphicsContext gc, final double w, final double h) {
        loadAssets();
        //gc.setGlobalAlpha((1));
        gc.drawImage(backgroundPixelated, 0, 0);
        gc.drawImage(foregroundPixelated, 0, 0);
        gc.drawImage(particlesLayer2, cloudOnePositionX, yCordCloud);
        gc.drawImage(particlesLayer1, cloudTwoPositionX, yCordCloud2);
        gc.setFill(Color.BLACK);
        gc.setGlobalAlpha((0.50f));
        gc.fillRect(0, 0, screenWidth, screenHeight);
        gc.setGlobalAlpha((1.0f));
        gc.drawImage(menuLogo, 0, 0);
        gc.setFill(Color.WHITE);
        gc.setFont(menuFont);
        if (overlay == Overlay.PRIMARY_MENU) {
            menuItemIndex = 0;
            if (hoveredMenuIndex == menuItemIndex) {
                menuItmStr = SubMode.STORY_MODE;
                gc.drawImage(pointer, xMenu - 18, yMenu - 15);
                gc.fillText(menuItem[1], xMenu, yMenu);
            } else {
                gc.fillText(menuItem[0], xMenu, yMenu);
            }
            menuItemIndex++;
            if (hoveredMenuIndex == menuItemIndex) {
                menuItmStr = SubMode.SINGLE_PLAYER;
                gc.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15);
                gc.fillText(menuItem[3], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                gc.fillText(menuItem[2], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;
            if (hoveredMenuIndex == menuItemIndex) {
                menuItmStr = SubMode.LAN_HOST;
                gc.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15);
                gc.fillText(menuItem[7], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                gc.fillText(menuItem[6], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;
            if (hoveredMenuIndex == menuItemIndex) {
                menuItmStr = SubMode.LAN_CLIENT;
                gc.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15);
                gc.fillText(menuItem[9], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                gc.fillText(menuItem[8], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;

            if (hoveredMenuIndex == menuItemIndex) {
                menuItmStr = SubMode.STATS;
                gc.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15);
                gc.fillText(menuItem[11], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                gc.fillText(menuItem[10], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;

            if (hoveredMenuIndex == menuItemIndex) {
                menuItmStr = SubMode.ACH;
                gc.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15);
                gc.fillText(menuItem[21], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                gc.fillText(menuItem[20], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;

            if (hoveredMenuIndex == menuItemIndex) {
                menuItmStr = SubMode.TUTORIAL;
                gc.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15);
                gc.fillText(menuItem[25], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                gc.fillText(menuItem[24], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;

            if (hoveredMenuIndex == menuItemIndex) {
                menuItmStr = SubMode.OPTIONS;
                gc.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15);
                gc.fillText(menuItem[13], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                gc.fillText(menuItem[12], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;

            if (hoveredMenuIndex == menuItemIndex) {
                menuItmStr = SubMode.CONTROLS;
                gc.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15);
                gc.fillText(menuItem[15], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                gc.fillText(menuItem[14], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;

            if (hoveredMenuIndex == menuItemIndex) {
                menuItmStr = SubMode.LOGOUT;
                gc.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15);
                gc.fillText(menuItem[23], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                gc.fillText(menuItem[22], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;

            if (hoveredMenuIndex == menuItemIndex) {
                menuItmStr = SubMode.ABOUT;
                gc.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15);
                gc.fillText(menuItem[17], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                gc.fillText(menuItem[16], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;
            if (hoveredMenuIndex == menuItemIndex) {
                menuItmStr = SubMode.EXIT;
                gc.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItemIndex) - 15);
                gc.fillText(menuItem[19], xMenu, yMenu + (fontSize * menuItemIndex));
            } else {
                gc.fillText(menuItem[18], xMenu, yMenu + (fontSize * menuItemIndex));
            }
            menuItemIndex++;
        }
        JenesisOverlay.getInstance().overlay(gc, w, h);
        gc.fillText("The SCND Genesis: Legends " + RenderGameplay.getInstance().getVersionStr() + " | copyright © " + WindowAbout.year() + " Ifunga Ndana.", 10, screenHeight - 10);
        gc.fillText(mess = "Press 'F' to provide Feedback", 590, 14);
        gc.fillText(mess = "Press 'B' to visit our Blog", 590, 30);
        gc.fillText(mess = "Press 'L' to like us on Facebook", 590, 46);
        gc.setGlobalAlpha((1.0f));
        gc.setFill(Color.WHITE);
        if (overlay == Overlay.STATISTICS) {
            achachievementLocker.drawStats(gc, w, h);
        }
        if (overlay == Overlay.ACHIEVEMENTS) {
            achachievementLocker.drawAch(gc, w, h);
        }
        if (overlay == Overlay.TUTORIAL) {
            tutorial.draw(gc, w, h);
        }

        if (opactity > 0.0f) {
            gc.setGlobalAlpha((1));
            if (opactity <= 1.0f) {
                gc.setGlobalAlpha((opactity));
            }
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, 852, 480);
            if (opactity > 2.0f) {
                gc.setGlobalAlpha((1.0f));
            } else if (opactity <= 2.0f && opactity > 1.0f) {
                gc.setGlobalAlpha((opactity - 1.0f));//TODO this one
            } else {
                gc.setGlobalAlpha((0f));
            }
            gc.drawImage(gameLogo, 0, 0);
            opactity -= 0.0125f;
        }
    }

    protected void logic(final long delta) {
        if (!isDelta60fps()) return;
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
