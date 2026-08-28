package com.scndgen.legends;

import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.command.GameCommandBus;
import com.scndgen.legends.network.NetworkManager;
import com.scndgen.legends.render.RenderCharacterSelection;
import com.scndgen.legends.render.RenderGamePlay;
import com.scndgen.legends.render.RenderMainMenu;
import com.scndgen.legends.render.RenderStageSelect;
import com.scndgen.legends.render.RenderStoryMenu;
import com.scndgen.legends.state.State;
import io.github.subiyacryolite.enginev2.AssetLoader;
import io.github.subiyacryolite.enginev2.Audio;
import io.github.subiyacryolite.enginev2.DesignViewport;
import io.github.subiyacryolite.enginev2.DrawContext;
import io.github.subiyacryolite.enginev2.GlfwEngine;
import io.github.subiyacryolite.enginev2.Mode;
import io.github.subiyacryolite.enginev2.Overlay;
import io.github.subiyacryolite.enginev2.nuklear.NkDialogs;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * GLFW scene router: owns {@link ModeEnum} / {@link SubMode} and the active {@link Mode}.
 */
public final class ScndGenLegends implements GlfwEngine.Scene {

    private static ScndGenLegends instance;

    private GlfwEngine engine;
    private AssetLoader loader;
    private Mode mode;
    private ModeEnum modeEnum = ModeEnum.EMPTY;
    private SubMode subMode = SubMode.MAIN_MENU;
    private boolean switchingModes;
    private float mouseX;
    private float mouseY;
    private String targetIp = "192.168.1.103";

    public static ScndGenLegends get() {
        return instance;
    }

    public static void main(String[] args) {
        var state = State.get();
        if (state.getLogins().isEmpty()) {
            state.createLogin("Player");
            try {
                state.saveConfigFile();
            } catch (Exception ignored) {
            }
        }
        var game = new ScndGenLegends();
        try (var glfw = new GlfwEngine("The SCND Genesis: Legends", game)) {
            glfw.run();
        } finally {
            game.shutDown();
        }
    }

    public ScndGenLegends() {
        instance = this;
    }

    @Override
    public void init(GlfwEngine engine, AssetLoader loader, DrawContext draw) {
        this.engine = engine;
        this.loader = loader;
        engine.retainFont("menu", "font/Sawasdee.ttf");
        loadMode(ModeEnum.MAIN_MENU);
    }

    @Override
    public void update(double deltaSeconds) {
        if (switchingModes || mode == null) {
            return;
        }
        GameCommandBus.get().drainAndApply();
        mode.tick(deltaSeconds);
    }

    @Override
    public void render(MemoryStack stack, DrawContext draw) {
        float w = DesignViewport.DESIGN_WIDTH;
        float h = DesignViewport.DESIGN_HEIGHT;
        if (switchingModes || mode == null) {
            draw.setFill(1f, 1f, 1f);
            draw.fillRect(0, 0, w, h);
            return;
        }
        mode.render(draw);
        Overlay.get().overlay(draw, w, h);
    }

    @Override
    public void onKey(int key, int action, int mods) {
        if (mode == null || switchingModes) {
            return;
        }
        switch (action) {
            case GLFW_PRESS -> mode.keyPressed(key);
            case GLFW_RELEASE -> mode.keyReleased(key);
            default -> {
            }
        }
    }

    @Override
    public void onMouseMove(float designX, float designY) {
        mouseX = designX;
        mouseY = designY;
        if (mode != null && !switchingModes) {
            mode.mouseMoved(designX, designY);
        }
    }

    @Override
    public void onMouseButton(int button, int action, float designX, float designY) {
        mouseX = designX;
        mouseY = designY;
        if (mode != null && !switchingModes && action == GLFW_PRESS) {
            mode.mouseClicked(designX, designY, button);
        }
    }

    @Override
    public void onScroll(double yOffset) {
        if (mode != null && !switchingModes) {
            mode.mouseScrolled(yOffset);
        }
    }

    public void loadMode(ModeEnum modeEnum) {
        loadMode(modeEnum, true);
    }

    public void loadMode(ModeEnum modeEnum, boolean newInstance) {
        this.modeEnum = modeEnum;
        switchingModes = true;
        try {
            switch (modeEnum) {
                case MAIN_MENU -> {
                    var renderMainMenu = RenderMainMenu.get();
                    if (newInstance) {
                        renderMainMenu.newInstance();
                    }
                    setMode(renderMainMenu);
                }
                case STORY_SELECT_SCREEN -> {
                    var renderStoryMenu = RenderStoryMenu.get();
                    if (newInstance) {
                        renderStoryMenu.newInstance();
                    }
                    setMode(renderStoryMenu);
                }
                case CHAR_SELECT_SCREEN -> {
                    var renderCharacterSelection = RenderCharacterSelection.get();
                    if (newInstance) {
                        renderCharacterSelection.newInstance();
                    }
                    switch (getSubMode()) {
                        case LAN_HOST -> {
                            var networkManager = NetworkManager.get();
                            if (!networkManager.isServer()) {
                                networkManager.asHost();
                            }
                            setMode(renderCharacterSelection);
                        }
                        case LAN_CLIENT -> {
                            var language = Language.get();
                            engine.ui().push(NkDialogs.input(
                                    language.get(450),
                                    language.get(451),
                                    targetIp,
                                    value -> {
                                        if (value != null && !value.isBlank()) {
                                            targetIp = value.trim();
                                            var networkManager = NetworkManager.get();
                                            if (!networkManager.isClient()) {
                                                networkManager.asClient(targetIp);
                                            }
                                        }
                                        setMode(renderCharacterSelection);
                                    }
                            ));
                        }
                        default -> setMode(renderCharacterSelection);
                    }
                }
                case STAGE_SELECT_SCREEN -> {
                    var renderStageSelect = RenderStageSelect.get();
                    if (newInstance) {
                        renderStageSelect.newInstance();
                    }
                    setMode(renderStageSelect);
                }
                case STANDARD_GAMEPLAY_START -> {
                    var renderGamePlay = RenderGamePlay.get();
                    if (newInstance) {
                        renderGamePlay.newInstance();
                    }
                    setMode(renderGamePlay);
                    renderGamePlay.startFight();
                }
                default -> {
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            switchingModes = false;
        }
    }

    private void setMode(Mode next) {
        if (next == null || this.mode == next) {
            return;
        }
        if (this.mode != null) {
            this.mode.onLeaveMode();
        }
        this.mode = next;
        this.mode.bind(engine, loader);
        this.mode.loadAssets();
        this.mode.onEnterMode();
    }

    public Mode getMode() {
        return mode;
    }

    public ModeEnum getModeEnum() {
        return modeEnum;
    }

    public boolean isSwitchingModes() {
        return switchingModes;
    }

    public SubMode getSubMode() {
        return subMode;
    }

    public void setSubMode(SubMode subMode) {
        this.subMode = subMode;
    }

    public float getMouseX() {
        return mouseX;
    }

    public float getMouseY() {
        return mouseY;
    }

    public GlfwEngine engine() {
        return engine;
    }

    public AssetLoader loader() {
        return loader;
    }

    public void exit() {
        shutDown();
        if (engine != null) {
            engine.requestClose();
        }
    }

    public void shutDown() {
        try {
            NetworkManager.get().close();
        } catch (Exception ignored) {
        }
        try {
            Audio.closeAll();
        } catch (Exception ignored) {
        }
    }
}
