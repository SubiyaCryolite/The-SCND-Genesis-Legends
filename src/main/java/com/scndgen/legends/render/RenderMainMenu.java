package com.scndgen.legends.render;

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.constants.GeneralConstants;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.enums.MainMenuOverlay;
import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.mode.MainMenu;
import com.scndgen.legends.ui.Event;
import com.scndgen.legends.ui.UiItem;
import io.github.subiyacryolite.enginev2.Audio;
import io.github.subiyacryolite.enginev2.DesignViewport;
import io.github.subiyacryolite.enginev2.DrawContext;
import io.github.subiyacryolite.enginev2.NvgImage;
import io.github.subiyacryolite.enginev2.Rgba;
import io.github.subiyacryolite.enginev2.nuklear.AboutOverlay;
import io.github.subiyacryolite.enginev2.nuklear.ControlsOverlay;
import io.github.subiyacryolite.enginev2.nuklear.NkDialogs;
import io.github.subiyacryolite.enginev2.nuklear.OptionsOverlay;


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
    private NvgImage menuLogo, gameLogo;
    private NvgImage foregroundPixelated, particlesLayer1, backgroundPixelated, particlesLayer2;
    private Audio menuMusic;

    public static synchronized RenderMainMenu get() {
        if (instance == null)
            instance = new RenderMainMenu();
        return instance;
    }

    public RenderMainMenu() {
        strTutorial = Language.get().get(319).toLowerCase();
        strStoryMode = Language.get().get(307).toLowerCase();
        strQuickMatch = Language.get().get(308).toLowerCase();
        strHostLanMatch = Language.get().get(309).toLowerCase();
        strJoinLanMatch = Language.get().get(310).toLowerCase();
        strAchievementLocker = Language.get().get(316).toLowerCase();
        strYourStats = Language.get().get(311).toLowerCase();
        strOptions = Language.get().get(312).toLowerCase();
        strControls = Language.get().get(313).toLowerCase();
        strAbout = Language.get().get(314).toLowerCase();
        strExit = Language.get().get(315).toLowerCase();


        (uiTutorial = new UiItem()).addJenesisEvent(Event.of(action -> {
            switch (action) {
                case HOVER -> strTutorial = strTutorial.toUpperCase();
                case LEAVE -> strTutorial = strTutorial.toLowerCase();
                case ACCEPT -> {
                    if (getMainMenuOverlay() != MainMenuOverlay.TUTORIAL) {
                        setMainMenuOverlay(MainMenuOverlay.TUTORIAL);
                        tutorial.beginTutorial();
                    } else {
                        tutorial.onAccept();
                    }
                }
                case BACK_CANCEL -> {
                    if (getMainMenuOverlay() != MainMenuOverlay.TUTORIAL) {
                        return;
                    }
                    tutorial.onBackCancel();
                }
                case LEFT -> {
                    if (getMainMenuOverlay() == MainMenuOverlay.TUTORIAL) {
                        return;
                    }
                    tutorial.onLeft();
                }
                case RIGHT -> {
                    if (getMainMenuOverlay() == MainMenuOverlay.TUTORIAL) {
                        return;
                    }
                    tutorial.onRight();
                }
                case DOWN -> setActiveItem(uiTutorial.getDown());
                case UP -> setActiveItem(uiTutorial.getUp());
            }
        }));

        (uiStoryMode = new UiItem()).addJenesisEvent(Event.of(action -> {
            switch (action) {
                case HOVER -> strStoryMode = strStoryMode.toUpperCase();
                case LEAVE -> strStoryMode = strStoryMode.toLowerCase();
                case ACCEPT -> {
                    var scndGenLegends = ScndGenLegends.get();
                    scndGenLegends.setSubMode(SubMode.STORY_MODE);
                    scndGenLegends.loadMode(ModeEnum.STORY_SELECT_SCREEN);
                }
                case DOWN -> setActiveItem(uiStoryMode.getDown());
                case UP -> setActiveItem(uiStoryMode.getUp());
                default -> {
                }
            }
        }));

        (uiQuickMatch = new UiItem()).addJenesisEvent(Event.of(action -> {
            switch (action) {
                case HOVER -> strQuickMatch = strQuickMatch.toUpperCase();
                case LEAVE -> strQuickMatch = strQuickMatch.toLowerCase();
                case ACCEPT -> {
                    var scndGenLegends = ScndGenLegends.get();
                    scndGenLegends.setSubMode(SubMode.SINGLE_PLAYER);
                    scndGenLegends.loadMode(ModeEnum.CHAR_SELECT_SCREEN);
                }
                case DOWN -> setActiveItem(uiQuickMatch.getDown());
                case UP -> setActiveItem(uiQuickMatch.getUp());
                default -> {
                }
            }
        }));

        (uiHostLanMatch = new UiItem()).addJenesisEvent(Event.of(action -> {
            switch (action) {
                case HOVER -> strHostLanMatch = strHostLanMatch.toUpperCase();
                case LEAVE -> strHostLanMatch = strHostLanMatch.toLowerCase();
                case ACCEPT -> {
                    var scndGenLegends = ScndGenLegends.get();
                    scndGenLegends.setSubMode(SubMode.LAN_HOST);
                    scndGenLegends.loadMode(ModeEnum.CHAR_SELECT_SCREEN);
                }
                case DOWN -> setActiveItem(uiHostLanMatch.getDown());
                case UP -> setActiveItem(uiHostLanMatch.getUp());
                default -> {
                }
            }
        }));

        (uiJoinLanMatch = new UiItem()).addJenesisEvent(Event.of(action -> {
            switch (action) {
                case HOVER -> strJoinLanMatch = strJoinLanMatch.toUpperCase();
                case LEAVE -> strJoinLanMatch = strJoinLanMatch.toLowerCase();
                case ACCEPT -> {
                    var scndGenLegends = ScndGenLegends.get();
                    scndGenLegends.setSubMode(SubMode.LAN_CLIENT);
                    scndGenLegends.loadMode(ModeEnum.CHAR_SELECT_SCREEN);
                }
                case DOWN -> setActiveItem(uiJoinLanMatch.getDown());
                case UP -> setActiveItem(uiJoinLanMatch.getUp());
                default -> {
                }
            }
        }));

        (uiAchievementLocker = new UiItem()).addJenesisEvent(Event.of(action -> {
            switch (action) {
                case HOVER -> strAchievementLocker = strAchievementLocker.toUpperCase();
                case LEAVE -> strAchievementLocker = strAchievementLocker.toLowerCase();
                case ACCEPT -> {
                    if (getMainMenuOverlay() == MainMenuOverlay.ACHIEVEMENT_LOCKER) {
                        achievementLocker.onAccept();
                    } else {
                        setMainMenuOverlay(MainMenuOverlay.ACHIEVEMENT_LOCKER);
                    }
                }
                case BACK_CANCEL -> {
                    if (getMainMenuOverlay() == MainMenuOverlay.ACHIEVEMENT_LOCKER) {
                        achievementLocker.onBackCancel();
                    }
                }
                case DOWN -> {
                    if (getMainMenuOverlay() == MainMenuOverlay.ACHIEVEMENT_LOCKER) {
                        achievementLocker.onDown();
                    } else {
                        setActiveItem(uiAchievementLocker.getDown());
                    }
                }
                case UP -> {
                    if (getMainMenuOverlay() == MainMenuOverlay.ACHIEVEMENT_LOCKER) {
                        achievementLocker.onUp();
                    } else {
                        setActiveItem(uiAchievementLocker.getUp());
                    }
                }
                default -> {
                }
            }
        }));

        (uiYourStats = new UiItem()).addJenesisEvent(Event.of(action -> {
            switch (action) {
                case HOVER -> strYourStats = strYourStats.toUpperCase();
                case LEAVE -> strYourStats = strYourStats.toLowerCase();
                case ACCEPT -> {
                    if (getMainMenuOverlay() == MainMenuOverlay.STATISTICS) {
                        achievementLocker.onAccept();
                    } else {
                        setMainMenuOverlay(MainMenuOverlay.STATISTICS);
                    }
                }
                case BACK_CANCEL -> {
                    if (getMainMenuOverlay() == MainMenuOverlay.STATISTICS) {
                        achievementLocker.onBackCancel();
                    }
                }
                case DOWN -> {
                    if (getMainMenuOverlay() == MainMenuOverlay.STATISTICS) {
                        achievementLocker.onDown();
                    } else {
                        setActiveItem(uiYourStats.getDown());
                    }
                }
                case UP -> {
                    if (getMainMenuOverlay() == MainMenuOverlay.STATISTICS) {
                        achievementLocker.onUp();
                    } else {
                        setActiveItem(uiYourStats.getUp());
                    }
                }
                default -> {
                }
            }
        }));
        (uiOptions = new UiItem()).addJenesisEvent(Event.of(action -> {
            switch (action) {
                case ACCEPT -> engine().ui().push(new OptionsOverlay(engine()));
                case HOVER -> strOptions = strOptions.toUpperCase();
                case LEAVE -> strOptions = strOptions.toLowerCase();
                case DOWN -> setActiveItem(uiOptions.getDown());
                case UP -> setActiveItem(uiOptions.getUp());
                default -> {
                }
            }
        }));
        (uiControls = new UiItem()).addJenesisEvent(Event.of(action -> {
            switch (action) {
                case ACCEPT -> engine().ui().push(new ControlsOverlay());
                case HOVER -> strControls = strControls.toUpperCase();
                case LEAVE -> strControls = strControls.toLowerCase();
                case DOWN -> setActiveItem(uiControls.getDown());
                case UP -> setActiveItem(uiControls.getUp());
                default -> {
                }
            }
        }));
        (uiAbout = new UiItem()).addJenesisEvent(Event.of(action -> {
            switch (action) {
                case ACCEPT -> engine().ui().push(new AboutOverlay());
                case HOVER -> strAbout = strAbout.toUpperCase();
                case LEAVE -> strAbout = strAbout.toLowerCase();
                case DOWN -> setActiveItem(uiAbout.getDown());
                case UP -> setActiveItem(uiAbout.getUp());
                default -> {
                }
            }
        }));
        (uiExit = new UiItem()).addJenesisEvent(Event.of(action -> {
            switch (action) {
                case ACCEPT -> engine().ui().push(NkDialogs.yesNo(
                        Language.get().get(422),
                        Language.get().get(110),
                        "",
                        answer -> {
                            if (answer == NkDialogs.Answer.YES) {
                                engine().ui().push(NkDialogs.yesNo(
                                        Language.get().get(423),
                                        Language.get().get(111),
                                        "",
                                        confirm -> {
                                            if (confirm == NkDialogs.Answer.YES) {
                                                ScndGenLegends.get().exit();
                                            }
                                        }
                                ));
                            }
                        }
                ));
                case HOVER -> strExit = strExit.toUpperCase();
                case LEAVE -> strExit = strExit.toLowerCase();
                case DOWN -> setActiveItem(uiExit.getDown());
                case UP -> setActiveItem(uiExit.getUp());
                default -> {
                }
            }
        }));
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
        super.onEnterMode();
        menuMusic = new Audio(AudioConstants.menuMus(), AudioType.MUSIC, true);
        menuMusic.play();
    }

    public void onLeaveMode() {
        menuMusic.stop(2000);
        super.onLeaveMode();
    }

    @Override
    public void loadAssetsIml() {
        if (!loadAssets) return;
        gameLogo = bag().loadImage("logo/gameLogo.png");
        menuLogo = bag().loadImage("images/sglogo.png");
        if (time >= 0 && time <= 9) {
            backgroundPixelated = bag().loadImage("images/blur/bgBG1.png");
            foregroundPixelated = bag().loadImage("images/blur/bgBG1fg.png");
            particlesLayer1 = bag().loadImage("images/blur/bgBG1a.png");
            particlesLayer2 = bag().loadImage("images/blur/bgBG1b.png");
        } else if (time > 9 && time <= 16) {
            backgroundPixelated = bag().loadImage("images/blur/bgBG6.png");
            foregroundPixelated = bag().loadImage("images/blur/bgBG6fg.png");
            particlesLayer1 = bag().loadImage("images/blur/bgBG6a.png");
            particlesLayer2 = bag().loadImage("images/blur/bgBG6b.png");
        } else if (time > 16 && time <= 24) {
            backgroundPixelated = bag().loadImage("images/blur/bgBG5.png");
            foregroundPixelated = bag().loadImage("images/blur/bgBG5fg.png");
            particlesLayer1 = bag().loadImage("images/blur/bgBG5a.png");
            particlesLayer2 = bag().loadImage("images/blur/bgBG5b.png");
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
        if (achievementLocker != null) {
            achievementLocker.freeImages();
        }
        if (tutorial != null) {
            tutorial.freeImages();
        }
        super.cleanAssets();
    }

    @Override
    public void render(DrawContext draw) {
        loadAssets();
        draw.drawImage(backgroundPixelated, 0, 0);
        draw.drawImage(foregroundPixelated, 0, 0);
        draw.drawImage(particlesLayer2, cloudOnePositionX, yCordCloud);
        draw.drawImage(particlesLayer1, cloudTwoPositionX, yCordCloud2);
        draw.setFill(Rgba.BLACK);
        draw.setGlobalAlpha(0.50f);
        draw.fillRect(0, 0, DesignViewport.DESIGN_WIDTH, DesignViewport.DESIGN_HEIGHT);
        draw.setGlobalAlpha(1.0f);
        draw.drawImage(menuLogo, 0, 0);
        draw.setFill(Rgba.WHITE);
        setFont(draw, fontSize);
        if (mainMenuOverlay == MainMenuOverlay.PRIMARY_MENU) {
            menuItemIndex = 0;
            fillText(draw, strTutorial, xMenu, yMenu + (fontSize * menuItemIndex), uiTutorial);
            menuItemIndex++;
            fillText(draw, strStoryMode, xMenu, yMenu + (fontSize * menuItemIndex), uiStoryMode);
            menuItemIndex++;
            fillText(draw, strQuickMatch, xMenu, yMenu + (fontSize * menuItemIndex), uiQuickMatch);
            menuItemIndex++;
            fillText(draw, strHostLanMatch, xMenu, yMenu + (fontSize * menuItemIndex), uiHostLanMatch);
            menuItemIndex++;
            fillText(draw, strJoinLanMatch, xMenu, yMenu + (fontSize * menuItemIndex), uiJoinLanMatch);
            menuItemIndex++;
            fillText(draw, strAchievementLocker, xMenu, yMenu + (fontSize * menuItemIndex), uiAchievementLocker);
            menuItemIndex++;
            fillText(draw, strYourStats, xMenu, yMenu + (fontSize * menuItemIndex), uiYourStats);
            menuItemIndex++;
            fillText(draw, strOptions, xMenu, yMenu + (fontSize * menuItemIndex), uiOptions);
            menuItemIndex++;
            fillText(draw, strControls, xMenu, yMenu + (fontSize * menuItemIndex), uiControls);
            menuItemIndex++;
            fillText(draw, strAbout, xMenu, yMenu + (fontSize * menuItemIndex), uiAbout);
            menuItemIndex++;
            fillText(draw, strExit, xMenu, yMenu + (fontSize * menuItemIndex), uiExit);
            menuItemIndex++;
        }
        draw.fillText("The SCND Genesis: Legends RMX | copyright © " + GeneralConstants.years() + " Ifunga Ndana.", 10, DesignViewport.DESIGN_HEIGHT - 10);
        draw.fillText(mess = "Press 'F' to provide Feedback", 590, 14);
        draw.fillText(mess = "Press 'B' to visit our Blog", 590, 30);
        draw.fillText(mess = "Press 'L' to like us on Facebook", 590, 46);
        draw.setGlobalAlpha(1.0f);
        draw.setFill(Rgba.WHITE);
        if (mainMenuOverlay == MainMenuOverlay.STATISTICS) {
            achievementLocker.drawStats(draw);
        }
        if (mainMenuOverlay == MainMenuOverlay.ACHIEVEMENT_LOCKER) {
            achievementLocker.drawAch(draw);
        }
        if (mainMenuOverlay == MainMenuOverlay.TUTORIAL) {
            tutorial.draw(draw);
        }

        if (opacity > 0.0f) {
            draw.setGlobalAlpha(1);
            if (opacity <= 1.0f) {
                draw.setGlobalAlpha(opacity);
            }
            draw.setFill(Rgba.WHITE);
            draw.fillRect(0, 0, 852, 480);
            if (opacity > 2.0f) {
                draw.setGlobalAlpha(1.0f);
            } else if (opacity <= 2.0f && opacity > 1.0f) {
                draw.setGlobalAlpha(opacity - 1.0f);
            } else {
                draw.setGlobalAlpha(0f);
            }
            draw.drawImage(gameLogo, 0, 0);
            opacity -= 0.0125f;
        }
    }

    @Override
    protected void update(double deltaSeconds) {
        super.update(deltaSeconds);
        while (tick60.consume()) {
            if (cloudOnePositionX < -960) {
                cloudOnePositionX = DesignViewport.DESIGN_WIDTH;
            } else {
                cloudOnePositionX = cloudOnePositionX - 1;
            }
            if (cloudTwoPositionX < -960) {
                cloudTwoPositionX = DesignViewport.DESIGN_WIDTH;
            } else {
                cloudTwoPositionX = cloudTwoPositionX - 2;
            }
            if (cloudThreePositionX < -960) {
                cloudThreePositionX = DesignViewport.DESIGN_WIDTH;
            } else {
                cloudThreePositionX = cloudThreePositionX - 3;
            }
        }
        if (tutorial != null && getMainMenuOverlay() == MainMenuOverlay.TUTORIAL) {
            tutorial.tick(deltaSeconds);
        }
    }

    public NvgImage[] getPics() {
        return new NvgImage[]{backgroundPixelated, particlesLayer1, particlesLayer2, foregroundPixelated};
    }
}
