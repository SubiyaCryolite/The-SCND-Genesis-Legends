/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (https://www.scndgen.com).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <https://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.render;

import java.util.Calendar;
import java.util.HashMap;

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.UiConstants;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.characters.Raila;
import com.scndgen.legends.command.GameCommand;
import com.scndgen.legends.command.GameCommandBus;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.enums.PlayerType;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.mode.CharacterSelection;
import com.scndgen.legends.network.NetworkManager;
import com.scndgen.legends.ui.Event;
import static com.scndgen.legends.ui.UiAction.HOVER;
import com.scndgen.legends.ui.UiItem;

import io.github.subiyacryolite.enginev2.Audio;
import io.github.subiyacryolite.enginev2.DrawContext;
import io.github.subiyacryolite.enginev2.NvgImage;
import io.github.subiyacryolite.enginev2.nuklear.NkDialogs;

/**
 * @author: Ifunga Ndana
 * @class: drawPrevChar
 * This class creates a graphical preview of the characterEnum and opponent
 */
public class RenderCharacterSelection extends CharacterSelection {

    private static RenderCharacterSelection instance;
    private final String[] charDesc = new String[numOfCharacters];
    private final NvgImage[] thumbnailNormal = new NvgImage[numOfCharacters];
    private final NvgImage[] thumbnailBlurred = new NvgImage[numOfCharacters];
    private final NvgImage[] portrait = new NvgImage[numOfCharacters];
    private final NvgImage[] portraitFlipped = new NvgImage[numOfCharacters];
    private final NvgImage[] caption = new NvgImage[numOfCharacters];
    private final HashMap<Integer, UiItem> uiElements = new HashMap<>();
    private final UiItem subiya;
    private final UiItem raila;
    private final UiItem lynx;
    private final UiItem aisha;
    private final UiItem ade;
    private final UiItem ravage;
    private final UiItem jonah;
    private final UiItem adam;
    private final UiItem novaAdam;
    private final UiItem azaria;
    private final UiItem sorrowe;
    private final UiItem thing;
    private NvgImage fg1, fg2, fg3, bg3;
    private NvgImage charBack, oppBack, charHold, p1, p2, fight, charDescPic, oppDescPic;
    private CharacterEnum hoveredCharacter;
    private Audio menuMusic;

    public void onEnterMode() {
        menuMusic = new Audio("audio/scotty/Scotty Zepplin - Rays.ogg", AudioType.MUSIC, true);
        menuMusic.play();
    }

    public void onLeaveMode() {
        menuMusic.stop(2000);
    }

    public RenderCharacterSelection() {
        opacInc = 0.025f;
        loadAssets = true;
        uiElements.clear();
        Event commonEvent = Event.of(action -> {
            switch (action) {
                case HOVER -> animatePortratit();
                case ACCEPT -> {
                    var networkManager = NetworkManager.get();
                    boolean regularMode = networkManager.isOffline() && (!selectedCharacter || !selectedOpponent);
                    boolean onlineMode = networkManager.isOnline() && !selectedCharacter;
                    if (regularMode || onlineMode) {
                        var type = selectedCharacter ? PlayerType.PLAYER2 : PlayerType.PLAYER1;
                        GameCommandBus.get().dispatch(new GameCommand.SelectCharacter(hoveredCharacter, type));
                    } else if (networkManager.isOffline()
                            || (networkManager.isServer() && selectedOpponent && selectedCharacter)) {
                        GameCommandBus.get().dispatch(new GameCommand.GoToStageSelect());
                    }
                }
                case BACK_CANCEL -> {
                    var networkManager = NetworkManager.get();
                    if (selectedOpponent && networkManager.isOffline()) {
                        selectedOpponent = false;
                    } else if (selectedCharacter) {
                        selectedCharacter = false;
                        if (networkManager.isOnline()) {
                            GameCommandBus.get().publish(new GameCommand.DeselectOpponentSlot());
                        }
                    } else if (networkManager.isOnline()) {
                        ScndGenLegends.get().engine().ui().push(NkDialogs.yesNo(
                                "Yikes",
                                "Are you sure you want to cancel this network session?",
                                "There's no going back!",
                                answer -> {
                                    switch (answer) {
                                        case YES -> {
                                            GameCommandBus.get().publish(new GameCommand.CancelConnectivity());
                                            networkManager.close();
                                        }
                                        default -> {
                                        }
                                    }
                                }
                        ));
                    } else {
                        GameCommandBus.get().dispatch(new GameCommand.LoadMode(ModeEnum.MAIN_MENU, true));
                    }
                }
                case RIGHT -> setActiveItem(activeItem.getRight());
                case LEFT -> setActiveItem(activeItem.getLeft());
                case UP -> setActiveItem(activeItem.getUp());
                case DOWN -> setActiveItem(activeItem.getDown());
                default -> {
                }
            }
        });

        bindCharacter(subiya = new UiItem(), CharacterEnum.SUBIYA, commonEvent);
        bindCharacter(raila = new UiItem(), CharacterEnum.RAILA, commonEvent);
        bindCharacter(lynx = new UiItem(), CharacterEnum.LYNX, commonEvent);
        bindCharacter(aisha = new UiItem(), CharacterEnum.AISHA, commonEvent);
        bindCharacter(ade = new UiItem(), CharacterEnum.ADE, commonEvent);
        bindCharacter(ravage = new UiItem(), CharacterEnum.RAVAGE, commonEvent);
        bindCharacter(jonah = new UiItem(), CharacterEnum.JONAH, commonEvent);
        bindCharacter(adam = new UiItem(), CharacterEnum.ADAM, commonEvent);
        bindCharacter(novaAdam = new UiItem(), CharacterEnum.NOVA_ADAM, commonEvent);
        bindCharacter(azaria = new UiItem(), CharacterEnum.AZARIA, commonEvent);
        bindCharacter(sorrowe = new UiItem(), CharacterEnum.SORROWE, commonEvent);
        bindCharacter(thing = new UiItem(), CharacterEnum.THING, commonEvent);

        uiElements.put(CharacterEnum.SUBIYA.index(), subiya);
        uiElements.put(CharacterEnum.RAILA.index(), raila);
        uiElements.put(CharacterEnum.LYNX.index(), lynx);
        uiElements.put(CharacterEnum.AISHA.index(), aisha);
        uiElements.put(CharacterEnum.ADE.index(), ade);
        uiElements.put(CharacterEnum.RAVAGE.index(), ravage);
        uiElements.put(CharacterEnum.JONAH.index(), jonah);
        uiElements.put(CharacterEnum.ADAM.index(), adam);
        uiElements.put(CharacterEnum.NOVA_ADAM.index(), novaAdam);
        uiElements.put(CharacterEnum.AZARIA.index(), azaria);
        uiElements.put(CharacterEnum.SORROWE.index(), sorrowe);
        uiElements.put(CharacterEnum.THING.index(), thing);

        //set up down, left right
        int total = uiElements.size();
        for (int index = 0; index < total; index++) {
            if (index > 0)
                uiElements.get(index).setLeft(uiElements.get(index - 1));
            if ((index + columns) < total)
                uiElements.get(index).setDown(uiElements.get(index + columns));
        }
    }

    public static synchronized RenderCharacterSelection get() {
        if (instance == null) {
            instance = new RenderCharacterSelection();
        }
        return instance;
    }

    @Override
    public void newInstance() {
        super.newInstance();
        Characters.get().resetCharacters();
        setActiveItem(uiElements.get(0));
    }

    @Override
    public void loadAssetsIml() {
        loadCaps();
        loadDesc();
        loadAssets = false;
    }

    public void cleanAssets() {
        java.util.Arrays.fill(thumbnailNormal, null);
        java.util.Arrays.fill(thumbnailBlurred, null);
        java.util.Arrays.fill(portrait, null);
        java.util.Arrays.fill(portraitFlipped, null);
        java.util.Arrays.fill(caption, null);
        fg1 = null;
        fg2 = null;
        fg3 = null;
        bg3 = null;
        charBack = null;
        oppBack = null;
        charHold = null;
        p1 = null;
        p2 = null;
        fight = null;
        charDescPic = null;
        oppDescPic = null;
        super.cleanAssets();
    }

    @Override
    public void render(final DrawContext draw) {
        loadAssets();
        setFont(draw, UiConstants.NORMAL_TXT_SIZE);
        draw.setFill(1f, 1f, 1f);
        draw.fillRect(0, 0, 852, 480);
        draw.drawImage(bg3, 0, 0);
        draw.drawImage(fg1, xCordCloud, 0);
        draw.drawImage(fg2, xCordCloud2, 0);
        draw.drawImage(fg3, 0, 0);
        var networkManager = NetworkManager.get();
        var scndGenLegends = ScndGenLegends.get();
        if (networkManager.isOffline() || (networkManager.isOnline() && networkManager.isConnectedToPartner())) {
            if (p1Opac < (1.0f - opacInc)) {
                p1Opac += opacInc;
            }
            if (opacChar < (1.0f - (opacInc * 2))) {
                opacChar += (opacInc * 2);
            }
            draw.setFill(0f, 0f, 0f);
            draw.setGlobalAlpha(0.70f);
            draw.fillRect(0, 0, 853, 480);
            draw.setGlobalAlpha(1.0f);
            //characterEnum preview DYNAMIC change
            if (!selectedCharacter) {
                draw.setGlobalAlpha(p1Opac);
                draw.drawImage(portrait[hoveredCharacter.index()], charXcap + x, charYcap);
                draw.setGlobalAlpha(1.0f);
                draw.drawImage(caption[hoveredCharacter.index()], 40 - x, 400);
            }
            //opponent preview DYNAMIC change, only show if quick match, should change sprites
            if (selectedCharacter && !selectedOpponent && scndGenLegends.getSubMode() == SubMode.SINGLE_PLAYER) {
                draw.setGlobalAlpha(p1Opac);
                draw.drawImage(portraitFlipped[hoveredCharacter.index()], 512 - x, charYcap);
                draw.setGlobalAlpha(1.0f);
                draw.drawImage(caption[hoveredCharacter.index()], 553 + x, 400);
            }
            //if characterEnum selected draw FIXED prev
            if (selectedCharacter) {
                draw.drawImage(portrait[charPrevLoc], charXcap, charYcap);
                draw.drawImage(caption[selectedCharIndex], 40, 380);
            }
            //if opp selected, draw FIXED prev
            if (selectedOpponent) {
                draw.drawImage(portraitFlipped[oppPrevLoc], 512, charYcap);
                draw.drawImage(caption[selectedOppIndex], 553, 380);
            }
            draw.drawImage(charHold, 311, 0);
            for (int row = 0; row <= (thumbnailNormal.length / columns); row++) {
                for (int column = 0; column < columns; column++) {
                    int computedPosition = (columns * row) + column;
                    if (computedPosition >= numOfCharacters) continue;
                    boolean characterOpenToSelection = (selectedCharIndex != computedPosition || selectedOppIndex != computedPosition);
                    boolean notAllCharactersSelect = bothArentSelected();
                    if (notAllCharactersSelect && uiElements.get(computedPosition).isHovered() && characterOpenToSelection)//clear
                    {
                        if (!selectedCharacter) {
                            draw.drawImage(charBack, hPos + (hSpacer * column), firstLine + (vSpacer * row));
                        }
                        if (selectedCharacter && !selectedOpponent && scndGenLegends.getSubMode() == SubMode.SINGLE_PLAYER) {
                            draw.drawImage(oppBack, hPos + (hSpacer * column), firstLine + (vSpacer * row));
                        }
                    }
                    draw.setGlobalAlpha(opacChar);
                    drawImage(draw, thumbnailNormal[computedPosition], hPos + (hSpacer * column), firstLine + (vSpacer * row), uiElements.get(computedPosition));
                    draw.setGlobalAlpha(1.0f);
                }
            }
            if (selectedCharacter && selectedOpponent) {
                draw.drawImage(fight, 0, 0);
                setFont(draw, UiConstants.EXTRA_LARGE_TXT_SIZE);
                draw.setFill(1f, 1f, 1f);
                String line146 = "<< " + Language.get().get(146) + " >>";
                String line147 = "<< " + Language.get().get(147) + " >>";
                draw.fillText(line146, (852 - draw.measureText(line146)) / 2, 360);
                draw.fillText(line147, (852 - draw.measureText(line147)) / 2, 390);
            }
            setFont(draw, UiConstants.NORMAL_TXT_SIZE);
            draw.setFill(1f, 1f, 1f);
            if (!selectedCharacter) {
                //select character
                draw.drawImage(charDescPic, 0, 0);
                draw.fillText(characterDescription[hoveredCharacter.index()], 4 + x, 18);
            }
            if (selectedCharacter && !selectedOpponent) {
                //select opponent
                draw.drawImage(oppDescPic, 452, 450);
                String desc = characterDescription[hoveredCharacter.index()];
                draw.fillText(desc, 852 - draw.measureText(desc) + x, 468);
            }
            draw.drawImage(p1, 0, 180);
            draw.drawImage(p2, 812, 180);
            if (x < 0) {
                x = x + 2;
            }
        } else if (networkManager.isServer()) {
            draw.setGlobalAlpha(1.0f);
            draw.setFill(1f, 1f, 1f);
            draw.fillText(Language.get().get(167), 20, 300);
            draw.fillText(networkManager.getHostName(), 20, 314);
            draw.fillText(Language.get().get(452), 20, 328);
            draw.fillText(networkManager.getHostAddress(), 20, 346);
            draw.fillText(Language.get().get(168), 20, 360);
            draw.fillText(Language.get().get(169), 20, 376);
        } else if (networkManager.isClient()) {
            draw.setGlobalAlpha(1.0f);
            draw.setFill(1f, 1f, 1f);
            draw.fillText("Waiting for host to respond", 553 + x, 400);
        }
    }

    private void loadCaps() {
        oppDescPic = bag().loadImage("images/charInfoO.png");
        charDescPic = bag().loadImage("images/charInfoC.png");
        loadUiContent(CharacterEnum.RAILA);
        loadUiContent(CharacterEnum.SUBIYA);
        loadUiContent(CharacterEnum.LYNX);
        loadUiContent(CharacterEnum.AISHA);
        loadUiContent(CharacterEnum.RAVAGE);
        loadUiContent(CharacterEnum.ADE);
        loadUiContent(CharacterEnum.JONAH);
        loadUiContent(CharacterEnum.NOVA_ADAM);
        loadUiContent(CharacterEnum.ADAM);
        loadUiContent(CharacterEnum.AZARIA);
        loadUiContent(CharacterEnum.SORROWE);
        loadUiContent(CharacterEnum.THING);
        charBack = bag().loadImage("images/selChar.png");
        oppBack = bag().loadImage("images/selOpp.png");
        charHold = bag().loadImage("images/charHold.png");
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour <= 9) {
            bg3 = bag().loadImage("images/blur/bgBG1.png");
            fg1 = bag().loadImage("images/blur/bgBG1a.png");
            fg2 = bag().loadImage("images/blur/bgBG1b.png");
            fg3 = bag().loadImage("images/blur/bgBG1fg.png");
        } else if (hour > 9 && hour <= 16) {
            bg3 = bag().loadImage("images/blur/bgBG6.png");
            fg1 = bag().loadImage("images/blur/bgBG6a.png");
            fg2 = bag().loadImage("images/blur/bgBG6b.png");
            fg3 = bag().loadImage("images/blur/bgBG6fg.png");
        } else {
            bg3 = bag().loadImage("images/blur/bgBG5.png");
            fg1 = bag().loadImage("images/blur/bgBG5a.png");
            fg2 = bag().loadImage("images/blur/bgBG5b.png");
            fg3 = bag().loadImage("images/blur/bgBG5fg.png");
        }
        p1 = bag().loadImage("images/player1.png");
        p2 = bag().loadImage("images/player2.png");
        fight = bag().loadImage("images/fight.png");
        charDesc[0] = Raila.class.getName();
    }

    public void loadUiContent(CharacterEnum characterEnum) {
        thumbnailNormal[characterEnum.index()] = bag().loadImage("images/" + characterEnum.data() + "/cap.png");
        thumbnailBlurred[characterEnum.index()] = bag().loadImage("images/" + characterEnum.data() + "/capB.png");
        caption[characterEnum.index()] = bag().loadImage("images/" + characterEnum.data() + "/name.png");
        portrait[characterEnum.index()] = bag().loadImage("images/" + characterEnum.data() + "/Prev.png");
        portraitFlipped[characterEnum.index()] = bag().loadImage("images/" + characterEnum.data() + "/PrevO.png");
    }


    private void loadDesc() {
        characterDescription = new String[CharacterEnum.values().length];
        characterDescription[CharacterEnum.RAILA.index()] = Language.get().get(134);
        characterDescription[CharacterEnum.SUBIYA.index()] = Language.get().get(135);
        characterDescription[CharacterEnum.LYNX.index()] = Language.get().get(136);
        characterDescription[CharacterEnum.AISHA.index()] = Language.get().get(137);
        characterDescription[CharacterEnum.RAVAGE.index()] = Language.get().get(138);
        characterDescription[CharacterEnum.ADE.index()] = Language.get().get(139);
        characterDescription[CharacterEnum.JONAH.index()] = Language.get().get(140);
        characterDescription[CharacterEnum.ADAM.index()] = Language.get().get(141);
        characterDescription[CharacterEnum.NOVA_ADAM.index()] = Language.get().get(142);
        characterDescription[CharacterEnum.AZARIA.index()] = Language.get().get(143);
        characterDescription[CharacterEnum.SORROWE.index()] = Language.get().get(144);
        characterDescription[CharacterEnum.THING.index()] = Language.get().get(145);
    }

    private void bindCharacter(UiItem item, CharacterEnum character, Event commonEvent) {
        item.addJenesisEvent(Event.on(HOVER, () -> hoveredCharacter = character));
        item.addJenesisEvent(commonEvent);
    }
}
