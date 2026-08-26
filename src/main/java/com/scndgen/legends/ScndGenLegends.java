package com.scndgen.legends;

import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.enums.SubMode;
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
        if (State.get().getLogins().isEmpty()) {
            State.get().createLogin("Player");
            try {
                State.get().saveConfigFile();
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
                    if (newInstance) {
                        RenderMainMenu.get().newInstance();
                    }
                    setMode(RenderMainMenu.get());
                }
                case STORY_SELECT_SCREEN -> {
                    if (newInstance) {
                        RenderStoryMenu.get().newInstance();
                    }
                    setMode(RenderStoryMenu.get());
                }
                case CHAR_SELECT_SCREEN -> {
                    if (newInstance) {
                        RenderCharacterSelection.get().newInstance();
                    }
                    switch (getSubMode()) {
                        case LAN_HOST -> {
                            if (!NetworkManager.get().isServer()) {
                                NetworkManager.get().asHost();
                            }
                            setMode(RenderCharacterSelection.get());
                        }
                        case LAN_CLIENT -> engine.ui().push(NkDialogs.input(
                                Language.get().get(450),
                                Language.get().get(451),
                                targetIp,
                                value -> {
                                    if (value != null && !value.isBlank()) {
                                        targetIp = value.trim();
                                        if (!NetworkManager.get().isClient()) {
                                            NetworkManager.get().asClient(targetIp);
                                        }
                                    }
                                    setMode(RenderCharacterSelection.get());
                                }
                        ));
                        default -> setMode(RenderCharacterSelection.get());
                    }
                }
                case STAGE_SELECT_SCREEN -> {
                    if (newInstance) {
                        RenderStageSelect.get().newInstance();
                    }
                    setMode(RenderStageSelect.get());
                }
                case STANDARD_GAMEPLAY_START -> {
                    if (newInstance) {
                        RenderGamePlay.get().newInstance();
                    }
                    setMode(RenderGamePlay.get());
                    RenderGamePlay.get().startFight();
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
