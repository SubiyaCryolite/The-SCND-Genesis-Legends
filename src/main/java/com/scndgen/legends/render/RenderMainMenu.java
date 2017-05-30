package com.scndgen.legends.render;

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.mode.MainMenu;
import com.scndgen.legends.ui.Event;
import com.scndgen.legends.ui.UiItem;
import com.scndgen.legends.windows.WindowAbout;
import com.scndgen.legends.windows.WindowControls;
import com.scndgen.legends.windows.WindowOptions;
import io.github.subiyacryolite.enginev1.AudioPlayback;
import io.github.subiyacryolite.enginev1.Loader;
import io.github.subiyacryolite.enginev1.Overlay;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.swing.*;


/**
 * Created by ifunga on 15/04/2017.
 */
public class RenderMainMenu extends MainMenu {

    private static RenderMainMenu instance;
    private String strStoryMode;
    private String strQuickMatch;
    private String strHostLanMatch;
    private String strJoinLanMatch;
    private String strAchievementLocker;
    private String strYourStats;
    private String strOptions;
    private String strControls;
    private String strAbout;
    private String strExit;
    private String strTutorial;
    private final UiItem uiStoryMode;
    private final UiItem uiQuickMatch;
    private final UiItem uiHostLanMatch;
    private final UiItem uiTutorial;
    private final UiItem uiJoinLanMatch;
    private final UiItem uiAchievementLocker;
    private final UiItem uiYourStats;
    private final UiItem uiOptions;
    private final UiItem uiControls;
    private final UiItem uiAbout;
    private final UiItem uiExit;
    private Loader loader = new Loader();
    private Image menuLogo, gameLogo;
    private Image foregroundPixelated, particlesLayer1, backgroundPixelated, particlesLayer2;
    private AudioPlayback menuMusic;

    public static synchronized RenderMainMenu getInstance() {
        if (instance == null)
            instance = new RenderMainMenu();
        return instance;
    }

    public RenderMainMenu() {
        strTutorial = Language.getInstance().get(319).toLowerCase();
        strStoryMode = Language.getInstance().get(307).toLowerCase();
        strQuickMatch = Language.getInstance().get(308).toLowerCase();
        strHostLanMatch = Language.getInstance().get(309).toLowerCase();
        strJoinLanMatch = Language.getInstance().get(310).toLowerCase();
        strAchievementLocker = Language.getInstance().get(316).toLowerCase();
        strYourStats = Language.getInstance().get(311).toLowerCase();
        strOptions = Language.getInstance().get(312).toLowerCase();
        strControls = Language.getInstance().get(313).toLowerCase();
        strAbout = Language.getInstance().get(314).toLowerCase();
        strExit = Language.getInstance().get(315).toLowerCase();


        (uiTutorial = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                strTutorial = strTutorial.toUpperCase();
            }

            @Override
            public void onLeave() {
                strTutorial = strTutorial.toLowerCase();
            }

            @Override
            public void onAccept() {
                if (getOverlay() != com.scndgen.legends.enums.Overlay.TUTORIAL) {
                    setOverlay(com.scndgen.legends.enums.Overlay.TUTORIAL);
                    tutorial.beginTutorial();
                } else {
                    tutorial.onAccept();
                }
            }

            @Override
            public void onBackCancel() {
                if (getOverlay() != com.scndgen.legends.enums.Overlay.TUTORIAL) return;
                tutorial.onBackCancel();
            }

            @Override
            public void onLeft() {
                if (getOverlay() == com.scndgen.legends.enums.Overlay.TUTORIAL) return;
                tutorial.onLeft();
            }

            @Override
            public void onRight() {
                if (getOverlay() == com.scndgen.legends.enums.Overlay.TUTORIAL) return;
                tutorial.onRight();
            }

            @Override
            public void onDown() {
                setActiveItem(source.getDown());
            }

            @Override
            public void onUp() {
                setActiveItem(source.getUp());
            }
        });

        (uiStoryMode = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                strStoryMode = strStoryMode.toUpperCase();
            }

            @Override
            public void onLeave() {
                strStoryMode = strStoryMode.toLowerCase();
            }

            @Override
            public void onAccept() {
                ScndGenLegends.getInstance().setSubMode(SubMode.STORY_MODE);
                ScndGenLegends.getInstance().loadMode(ModeEnum.STORY_SELECT_SCREEN);
            }

            @Override
            public void onDown() {
                setActiveItem(source.getDown());
            }

            @Override
            public void onUp() {
                setActiveItem(source.getUp());
            }
        });

        (uiQuickMatch = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                strQuickMatch = strQuickMatch.toUpperCase();
            }

            @Override
            public void onLeave() {
                strQuickMatch = strQuickMatch.toLowerCase();
            }

            @Override
            public void onAccept() {
                ScndGenLegends.getInstance().setSubMode(SubMode.SINGLE_PLAYER);
                ScndGenLegends.getInstance().loadMode(ModeEnum.CHAR_SELECT_SCREEN);
            }

            @Override
            public void onDown() {
                setActiveItem(source.getDown());
            }

            @Override
            public void onUp() {
                setActiveItem(source.getUp());
            }
        });

        (uiHostLanMatch = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                strHostLanMatch = strHostLanMatch.toUpperCase();
            }

            @Override
            public void onLeave() {
                strHostLanMatch = strHostLanMatch.toLowerCase();
            }

            @Override
            public void onAccept() {
                ScndGenLegends.getInstance().setSubMode(SubMode.LAN_HOST);
                primaryNotice(Language.getInstance().get(107));
                ScndGenLegends.getInstance().loadMode(ModeEnum.CHAR_SELECT_SCREEN);
            }

            @Override
            public void onDown() {
                setActiveItem(source.getDown());
            }

            @Override
            public void onUp() {
                setActiveItem(source.getUp());
            }
        });

        (uiJoinLanMatch = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                strJoinLanMatch = strJoinLanMatch.toUpperCase();
            }

            @Override
            public void onLeave() {
                strJoinLanMatch = strJoinLanMatch.toLowerCase();
            }

            @Override
            public void onAccept() {
                ScndGenLegends.getInstance().setSubMode(SubMode.LAN_CLIENT);
                primaryNotice(Language.getInstance().get(107));
                ScndGenLegends.getInstance().loadMode(ModeEnum.CHAR_SELECT_SCREEN);
            }

            @Override
            public void onDown() {
                setActiveItem(source.getDown());
            }

            @Override
            public void onUp() {
                setActiveItem(source.getUp());
            }
        });

        (uiAchievementLocker = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                strAchievementLocker = strAchievementLocker.toUpperCase();
            }

            @Override
            public void onLeave() {
                strAchievementLocker = strAchievementLocker.toLowerCase();
            }

            @Override
            public void onAccept() {
                if (getOverlay() == com.scndgen.legends.enums.Overlay.ACHIEVEMENT_LOCKER)
                    achievementLocker.onAccept();
                else
                    setOverlay(com.scndgen.legends.enums.Overlay.ACHIEVEMENT_LOCKER);
            }

            @Override
            public void onBackCancel() {
                if (getOverlay() == com.scndgen.legends.enums.Overlay.ACHIEVEMENT_LOCKER)
                    achievementLocker.onBackCancel();
            }

            @Override
            public void onDown() {
                if (getOverlay() == com.scndgen.legends.enums.Overlay.ACHIEVEMENT_LOCKER)
                    achievementLocker.onDown();
                else {
                    setActiveItem(source.getDown());
                }
            }

            @Override
            public void onUp() {
                if (getOverlay() == com.scndgen.legends.enums.Overlay.ACHIEVEMENT_LOCKER)
                    achievementLocker.onUp();
                else {
                    setActiveItem(source.getUp());
                }
            }
        });

        (uiYourStats = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onHover() {
                strYourStats = strYourStats.toUpperCase();
            }

            @Override
            public void onLeave() {
                strYourStats = strYourStats.toLowerCase();
            }

            @Override
            public void onAccept() {
                if (getOverlay() == com.scndgen.legends.enums.Overlay.STATISTICS)
                    achievementLocker.onAccept();
                else
                    setOverlay(com.scndgen.legends.enums.Overlay.STATISTICS);
            }

            @Override
            public void onBackCancel() {
                if (getOverlay() == com.scndgen.legends.enums.Overlay.STATISTICS)
                    achievementLocker.onBackCancel();
            }

            @Override
            public void onDown() {
                if (getOverlay() == com.scndgen.legends.enums.Overlay.STATISTICS)
                    achievementLocker.onDown();
                else {
                    setActiveItem(source.getDown());
                }
            }

            @Override
            public void onUp() {
                if (getOverlay() == com.scndgen.legends.enums.Overlay.STATISTICS)
                    achievementLocker.onUp();
                else {
                    setActiveItem(source.getUp());
                }
            }
        });
        (uiOptions = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onAccept() {
                new WindowOptions();
            }

            @Override
            public void onHover() {
                strOptions = strOptions.toUpperCase();
            }

            @Override
            public void onLeave() {
                strOptions = strOptions.toLowerCase();
            }

            @Override
            public void onDown() {
                setActiveItem(source.getDown());
            }

            @Override
            public void onUp() {
                setActiveItem(source.getUp());
            }
        });
        (uiControls = new UiItem()).addJenesisEvent(new Event() {
            @Override
            public void onAccept() {
                new WindowControls();
            }

            @Override
            public void onHover() {
                strControls = strControls.toUpperCase();
            }

            @Override
            public void onLeave() {
                strControls = strControls.toLowerCase();
            }

            @Override
            public void onDown() {
                setActiveItem(source.getDown());
            }

            @Override
            public void onUp() {
                setActiveItem(source.getUp());
            }
        });
        (uiAbout = new UiItem()).addJenesisEvent(new Event() {

            @Override
            public void onAccept() {
                new WindowAbout();
            }

            @Override
            public void onHover() {
                strAbout = strAbout.toUpperCase();
            }

            @Override
            public void onLeave() {
                strAbout = strAbout.toLowerCase();
            }

            @Override
            public void onDown() {
                setActiveItem(source.getDown());
            }

            @Override
            public void onUp() {
                setActiveItem(source.getUp());
            }
        });
        (uiExit = new UiItem()).addJenesisEvent(new Event() {

            @Override
            public void onAccept() {
                ScndGenLegends.getInstance().exit();
            }

            @Override
            public void onHover() {
                strExit = strExit.toUpperCase();
            }

            @Override
            public void onLeave() {
                strExit = strExit.toLowerCase();
            }

            @Override
            public void onDown() {
                setActiveItem(source.getDown());
            }

            @Override
            public void onUp() {
                setActiveItem(source.getUp());
            }
        });
        uiTutorial.setDown(uiStoryMode);
        uiStoryMode.setDown(uiQuickMatch);
        uiQuickMatch.setDown(uiHostLanMatch);
        uiHostLanMatch.setDown(uiJoinLanMatch);
        uiJoinLanMatch.setDown(uiAchievementLocker);
        uiAchievementLocker.setDown(uiYourStats);
        uiYourStats.setDown(uiOptions);
        uiOptions.setDown(uiControls);
        uiControls.setDown(uiAbout);
        uiAbout.setDown(uiExit);
        uiExit.setDown(uiTutorial);
    }

    public void onBackCancel() {
        activeItem.backCancel();
    }

    public void onUp() {
        activeItem.up();
    }

    public void onDown() {
        activeItem.down();
    }

    public void onRight() {
        activeItem.right();
    }

    public void onLeft() {
        activeItem.left();
    }

    public void onAccept() {
        activeItem.accept();
    }

    @Override
    public void newInstance() {
        super.newInstance();
    }

    public void onEnterMode() {
        menuMusic = new AudioPlayback(AudioConstants.menuMus(), AudioType.MUSIC, true);
        menuMusic.play();
    }

    public void onLeaveMode() {
        menuMusic.stop(2000);
    }

    @Override
    public void loadAssetsIml() {
        if (!loadAssets) return;
        menuFont = loadFont(fontSize);
        gameLogo = loader.load("logo/gameLogo");
        menuLogo = loader.load("images/sglogo.png");
        if (time >= 0 && time <= 9) {
            backgroundPixelated = loader.load("images/blur/bgBG1.png");
            foregroundPixelated = loader.load("images/blur/bgBG1fg.png");
            particlesLayer1 = loader.load("images/blur/bgBG1a.png");
            particlesLayer2 = loader.load("images/blur/bgBG1b.png");
        } else if (time > 9 && time <= 16) {
            backgroundPixelated = loader.load("images/blur/bgBG6.png");
            foregroundPixelated = loader.load("images/blur/bgBG6fg.png");
            particlesLayer1 = loader.load("images/blur/bgBG6a.png");
            particlesLayer2 = loader.load("images/blur/bgBG6b.png");
        } else if (time > 16 && time <= 24) {
            backgroundPixelated = loader.load("images/blur/bgBG5.png");
            foregroundPixelated = loader.load("images/blur/bgBG5fg.png");
            particlesLayer1 = loader.load("images/blur/bgBG5a.png");
            particlesLayer2 = loader.load("images/blur/bgBG5b.png");
        }
        loadAssets = false;
        setActiveItem(uiTutorial);
    }

    @Override
    public void cleanAssets() {
        menuLogo = null;
        gameLogo = null;
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
        if (overlay == com.scndgen.legends.enums.Overlay.PRIMARY_MENU) {
            menuItemIndex = 0;
            ///////////////////////////////////////////
            fillText(gc, strTutorial, xMenu, yMenu + (fontSize * menuItemIndex), uiTutorial);
            menuItemIndex++;
            fillText(gc, strStoryMode, xMenu, yMenu + (fontSize * menuItemIndex), uiStoryMode);
            menuItemIndex++;
            fillText(gc, strQuickMatch, xMenu, yMenu + (fontSize * menuItemIndex), uiQuickMatch);
            menuItemIndex++;
            fillText(gc, strHostLanMatch, xMenu, yMenu + (fontSize * menuItemIndex), uiHostLanMatch);
            menuItemIndex++;
            fillText(gc, strJoinLanMatch, xMenu, yMenu + (fontSize * menuItemIndex), uiJoinLanMatch);
            menuItemIndex++;
/////////////////////////////////////////////////////
            fillText(gc, strAchievementLocker, xMenu, yMenu + (fontSize * menuItemIndex), uiAchievementLocker);
            menuItemIndex++;
            fillText(gc, strYourStats, xMenu, yMenu + (fontSize * menuItemIndex), uiYourStats);
            menuItemIndex++;
            fillText(gc, strOptions, xMenu, yMenu + (fontSize * menuItemIndex), uiOptions);
            menuItemIndex++;
            fillText(gc, strControls, xMenu, yMenu + (fontSize * menuItemIndex), uiControls);
            menuItemIndex++;
            fillText(gc, strAbout, xMenu, yMenu + (fontSize * menuItemIndex), uiAbout);
            menuItemIndex++;
            fillText(gc, strExit, xMenu, yMenu + (fontSize * menuItemIndex), uiExit);
            menuItemIndex++;
        }
        Overlay.getInstance().overlay(gc, w, h);
        gc.fillText("The SCND Genesis: Legends " + RenderGamePlay.getInstance().getVersionStr() + " | copyright © " + WindowAbout.year() + " Ifunga Ndana.", 10, screenHeight - 10);
        gc.fillText(mess = "Press 'F' to provide Feedback", 590, 14);
        gc.fillText(mess = "Press 'B' to visit our Blog", 590, 30);
        gc.fillText(mess = "Press 'L' to like us on Facebook", 590, 46);
        gc.setGlobalAlpha((1.0f));
        gc.setFill(Color.WHITE);
        if (overlay == com.scndgen.legends.enums.Overlay.STATISTICS) {
            achievementLocker.drawStats(gc, w, h);
        }
        if (overlay == com.scndgen.legends.enums.Overlay.ACHIEVEMENT_LOCKER) {
            achievementLocker.drawAch(gc, w, h);
        }
        if (overlay == com.scndgen.legends.enums.Overlay.TUTORIAL) {
            tutorial.draw(gc, w, h);
        }

        if (opacity > 0.0f) {
            gc.setGlobalAlpha((1));
            if (opacity <= 1.0f) {
                gc.setGlobalAlpha((opacity));
            }
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, 852, 480);
            if (opacity > 2.0f) {
                gc.setGlobalAlpha((1.0f));
            } else if (opacity <= 2.0f && opacity > 1.0f) {
                gc.setGlobalAlpha((opacity - 1.0f));//TODO this one
            } else {
                gc.setGlobalAlpha((0f));
            }
            gc.drawImage(gameLogo, 0, 0);
            opacity -= 0.0125f;
        }
    }

    protected void update(final long delta) {
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

    public Image[] getPics() {
        return new Image[]{backgroundPixelated, particlesLayer1, particlesLayer2, foregroundPixelated};
    }

    public void exit() {
        int exit = JOptionPane.showConfirmDialog(null, Language.getInstance().get(110), "Exit", JOptionPane.YES_NO_OPTION);
        if (exit == JOptionPane.YES_OPTION) {
            int seriously = JOptionPane.showConfirmDialog(null, Language.getInstance().get(111), "Seriously", JOptionPane.YES_NO_OPTION);
            if (seriously == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, Language.getInstance().get(112), "Later", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
    }

}
