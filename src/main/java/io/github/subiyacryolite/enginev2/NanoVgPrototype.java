package io.github.subiyacryolite.enginev2;

import com.scndgen.legends.Language;
import com.scndgen.legends.constants.GeneralConstants;
import com.scndgen.legends.state.State;
import io.github.subiyacryolite.enginev2.nuklear.AboutOverlay;
import io.github.subiyacryolite.enginev2.nuklear.ControlsOverlay;
import io.github.subiyacryolite.enginev2.nuklear.NkDialogs;
import io.github.subiyacryolite.enginev2.nuklear.OptionsOverlay;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.nanovg.NanoVG.nvgRestore;
import static org.lwjgl.nanovg.NanoVG.nvgSave;
import static org.lwjgl.nanovg.NanoVG.nvgScale;
import static org.lwjgl.nanovg.NanoVG.nvgTranslate;

/**
 * Migrated main-menu scene: NanoVG for game art, Nuklear for Options/Controls/About/dialogs.
 *
 * <p>Run with: {@code ./gradlew runNanoVgPrototype}
 */
public final class NanoVgPrototype implements GlfwEngine.Scene {
    private static final int DESIGN_WIDTH = 852;
    private static final int DESIGN_HEIGHT = 480;
    private static final String[] MENU_ITEMS = {
            "tutorial",
            "story mode",
            "quick match",
            "host lan match",
            "join lan match",
            "achievement locker",
            "your stats",
            "options",
            "controls",
            "about",
            "exit"
    };

    private GlfwEngine engine;
    private NvgImage background;
    private NvgImage foreground;
    private NvgImage particles1;
    private NvgImage particles2;
    private NvgImage menuLogo;
    private NvgImage gameLogo;

    private float cloudOneX;
    private float cloudTwoX;
    private float introOpacity = 2.2f;
    private int selectedIndex;
    private double mouseX;
    private double mouseY;
    private int windowWidth = DESIGN_WIDTH;
    private int windowHeight = DESIGN_HEIGHT;
    private boolean showIntro = true;

    public static void main(String[] args) {
        // Ensure a save profile exists for Options overlay.
        if (State.get().getLogins().isEmpty()) {
            State.get().createLogin("Player");
            try {
                State.get().saveConfigFile();
            } catch (Exception ignored) {
            }
        }
        try (GlfwEngine engine = new GlfwEngine(
                DESIGN_WIDTH,
                DESIGN_HEIGHT,
                "SCND Genesis — NanoVG + Nuklear (Esc to quit)",
                new NanoVgPrototype()
        )) {
            engine.run();
        }
    }

    @Override
    public void init(GlfwEngine engine, AssetLoader loader, DrawContext draw) {
        this.engine = engine;
        engine.retainFont("menu", "font/Sawasdee.ttf");
        background = loader.loadImage("images/blur/bgBG1.png");
        foreground = loader.loadImage("images/blur/bgBG1fg.png");
        particles1 = loader.loadImage("images/blur/bgBG1a.png");
        particles2 = loader.loadImage("images/blur/bgBG1b.png");
        menuLogo = loader.loadImage("images/sglogo.png");
        gameLogo = loader.loadImage("logo/gameLogo.png");
        // Localize labels when translations are available.
        MENU_ITEMS[0] = Language.get().get(319).toLowerCase();
        MENU_ITEMS[1] = Language.get().get(307).toLowerCase();
        MENU_ITEMS[2] = Language.get().get(308).toLowerCase();
        MENU_ITEMS[3] = Language.get().get(309).toLowerCase();
        MENU_ITEMS[4] = Language.get().get(310).toLowerCase();
        MENU_ITEMS[5] = Language.get().get(316).toLowerCase();
        MENU_ITEMS[6] = Language.get().get(311).toLowerCase();
        MENU_ITEMS[7] = Language.get().get(312).toLowerCase();
        MENU_ITEMS[8] = Language.get().get(313).toLowerCase();
        MENU_ITEMS[9] = Language.get().get(314).toLowerCase();
        MENU_ITEMS[10] = Language.get().get(315).toLowerCase();
    }

    @Override
    public void update(double deltaSeconds) {
        cloudOneX -= (float) (24.0 * deltaSeconds);
        cloudTwoX -= (float) (48.0 * deltaSeconds);
        if (cloudOneX < -DESIGN_WIDTH) {
            cloudOneX = 0;
        }
        if (cloudTwoX < -DESIGN_WIDTH) {
            cloudTwoX = 0;
        }
        if (showIntro) {
            introOpacity -= (float) (0.45 * deltaSeconds);
            if (introOpacity <= 0f) {
                introOpacity = 0f;
                showIntro = false;
            }
        }

        float scale = Math.min(
                windowWidth / (float) DESIGN_WIDTH,
                windowHeight / (float) DESIGN_HEIGHT
        );
        float offsetX = (windowWidth - DESIGN_WIDTH * scale) * 0.5f;
        float offsetY = (windowHeight - DESIGN_HEIGHT * scale) * 0.5f;
        float designX = (float) ((mouseX - offsetX) / scale);
        float designY = (float) ((mouseY - offsetY) / scale);

        float xMenu = 600f;
        float yMenu = 270f;
        float fontSize = 16f;
        if (designX >= xMenu && designX <= DESIGN_WIDTH - 20) {
            int hovered = (int) Math.floor((designY - (yMenu - fontSize)) / fontSize);
            if (hovered >= 0 && hovered < MENU_ITEMS.length) {
                selectedIndex = hovered;
            }
        }
    }

    @Override
    public void render(MemoryStack stack, DrawContext draw, int width, int height) {
        windowWidth = width;
        windowHeight = height;

        float scale = Math.min(width / (float) DESIGN_WIDTH, height / (float) DESIGN_HEIGHT);
        float offsetX = (width - DESIGN_WIDTH * scale) * 0.5f;
        float offsetY = (height - DESIGN_HEIGHT * scale) * 0.5f;

        nvgSave(draw.vg());
        nvgTranslate(draw.vg(), offsetX, offsetY);
        nvgScale(draw.vg(), scale, scale);

        draw.setGlobalAlpha(1f);
        draw.drawImage(background, 0, 0);
        draw.drawImage(foreground, 0, 0);
        draw.drawImage(particles2, cloudOneX, 0);
        draw.drawImage(particles2, cloudOneX + DESIGN_WIDTH, 0);
        draw.drawImage(particles1, cloudTwoX, 0);
        draw.drawImage(particles1, cloudTwoX + DESIGN_WIDTH, 0);

        draw.setFill(0f, 0f, 0f);
        draw.setGlobalAlpha(0.50f);
        draw.fillRect(0, 0, DESIGN_WIDTH, DESIGN_HEIGHT);
        draw.setGlobalAlpha(1f);
        draw.drawImage(menuLogo, 0, 0);

        draw.setFont("menu", 16f);
        float xMenu = 600f;
        float yMenu = 270f;
        for (int i = 0; i < MENU_ITEMS.length; i++) {
            boolean selected = i == selectedIndex;
            draw.setFontSize(selected ? 18f : 16f);
            draw.setFill(selected ? 1f : 0.85f, selected ? 0.92f : 0.85f, selected ? 0.55f : 0.85f);
            draw.fillText(MENU_ITEMS[i], xMenu, yMenu + (16f * i));
        }

        draw.setFontSize(14f);
        draw.setFill(1f, 1f, 1f);
        draw.fillText("The SCND Genesis: Legends RMX | © " + GeneralConstants.years() + " Ifunga Ndana.", 10, DESIGN_HEIGHT - 10);
        draw.fillText("Nuklear UI · Enter opens · Esc quits", 560, 24);

        if (showIntro || introOpacity > 0f) {
            float overlay = Math.min(1f, introOpacity);
            draw.setFill(0f, 0f, 0f);
            draw.setGlobalAlpha(overlay);
            draw.fillRect(0, 0, DESIGN_WIDTH, DESIGN_HEIGHT);
            float logoAlpha = introOpacity > 1f ? introOpacity - 1f : 0f;
            draw.setGlobalAlpha(logoAlpha);
            draw.drawImage(gameLogo, 0, 0);
            draw.setGlobalAlpha(1f);
        }

        nvgRestore(draw.vg());
    }

    @Override
    public void onKey(int key, int action, int mods) {
        if (action != GLFW_PRESS) {
            return;
        }
        if (key == GLFW_KEY_UP || key == GLFW_KEY_W) {
            selectedIndex = (selectedIndex + MENU_ITEMS.length - 1) % MENU_ITEMS.length;
        } else if (key == GLFW_KEY_DOWN || key == GLFW_KEY_S) {
            selectedIndex = (selectedIndex + 1) % MENU_ITEMS.length;
        } else if (key == GLFW_KEY_ENTER) {
            activateSelected();
        }
    }

    @Override
    public void onMouseMove(double x, double y) {
        mouseX = x;
        mouseY = y;
    }

    @Override
    public void onMouseButton(int button, int action, double x, double y) {
        if (action == GLFW_PRESS) {
            if (showIntro) {
                showIntro = false;
                introOpacity = 0f;
            } else {
                activateSelected();
            }
        }
    }

    private void activateSelected() {
        showIntro = false;
        introOpacity = 0f;
        switch (selectedIndex) {
            case 7 -> engine.ui().push(new OptionsOverlay());
            case 8 -> engine.ui().push(new ControlsOverlay());
            case 9 -> engine.ui().push(new AboutOverlay());
            case 10 -> engine.ui().push(NkDialogs.yesNo(
                    Language.get().get(422),
                    Language.get().get(110),
                    "",
                    answer -> {
                        if (answer == NkDialogs.Answer.YES) {
                            engine.ui().push(NkDialogs.yesNo(
                                    Language.get().get(423),
                                    Language.get().get(111),
                                    "",
                                    confirm -> {
                                        if (confirm == NkDialogs.Answer.YES) {
                                            engine.requestClose();
                                        }
                                    }
                            ));
                        }
                    }
            ));
            default -> engine.ui().push(NkDialogs.message(
                    "Not migrated yet",
                    MENU_ITEMS[selectedIndex],
                    "This scene is still on the JavaFX path. Main menu + Nuklear overlays are live."
            ));
        }
    }
}
