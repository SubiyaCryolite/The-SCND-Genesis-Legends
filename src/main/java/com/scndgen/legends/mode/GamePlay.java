/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([http://www.scndgen.com]))).

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
 along with The SCND Genesis: Legends. If not, see <http://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.mode;

import com.scndgen.legends.Achievement;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.characters.Character;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.constants.NetworkConstants;
import com.scndgen.legends.enums.*;
import com.scndgen.legends.network.NetworkManager;
import com.scndgen.legends.render.RenderCharacterSelection;
import com.scndgen.legends.render.RenderStageSelect;
import com.scndgen.legends.state.State;
import io.github.subiyacryolite.enginev1.FxDialogs;
import io.github.subiyacryolite.enginev1.Mode;
import io.github.subiyacryolite.enginev1.Overlay;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static com.scndgen.legends.constants.GeneralConstants.INFINITE_TIME;

/**
 * This class draws and manipulates all sprites, images and effects used in the game
 *
 * @Author: Ifunga Ndana
 * @Class: GamePlay
 */
public abstract class GamePlay extends Mode {
    protected final String[] physicalAttacks = new String[]{"", "", "", ""}, celestiaAttacks = new String[]{"", "", "", ""}, itemAttacks = new String[]{"", "", "", ""};
    protected boolean triggerCharacterAttack;
    protected boolean triggerOpponentAttack;
    protected LinkedList<Integer> characterAttacks = new LinkedList<>();
    protected LinkedList<Integer> opponentAttacks = new LinkedList<>();
    protected int startDrawing;
    protected Color currentColor = Color.RED;
    protected boolean safeToSelect;
    protected int unlockedAchievementInstance;
    protected int charXcord, charYcord, oppYcord, statIndex;
    protected int ambientForegroundX, ambientForegroundY, ambientBackgroundX, ambientBackgroundY;
    protected float foreGroundPositionX, foreGroundPositionY, foreGroundXIncrement, foreGroundYIncrement, animationLoops;
    protected AnimationDirection ambientDirection = AnimationDirection.VERTICAL;
    protected AnimationDirection foregroundDirection = AnimationDirection.VERTICAL;
    protected int playerDamageXLoc, opponentDamageXLoc;
    protected String[] storyPicArrStr;
    protected CharacterEnum[] charNames = LoginScreen.charNames;
    protected int ambSpeed1, ambSpeed2, paneCord;
    protected StringBuilder battleInformation = new StringBuilder("");
    protected AmbientMode ambientMode;
    protected boolean loadedUpdaters;
    protected float daNum, daNum2;
    protected long lifePlain, lifeTotalPlain, lifePlain2, lifeTotalPlain2;
    protected int fancyBWAnimeEffect;     //toggle fancy effect when HP low
    protected boolean fancyBWAnimeEffectEnabled;
    protected boolean gameOver, showBrag;
    protected float characterHp, characterMaximumHp, opponentHp, opponentMaximumHp;
    protected float damageDoneToCharacter, damageDoneToOpponent;
    protected float opponentLifePercentage, characterLifePercentage;
    protected Object source;
    protected int lifeBarShakeIterations, lifeBarShakeInnerIterations;
    protected int x2, comX, comY;
    protected int y2;
    protected int oppXcord;
    protected int numOfComicPics;
    protected float characterHpAsPercent, opponentHpAsPercent;
    protected int lbx2;
    protected int lby2;
    protected int charMeleeSpriteStatus, oppMeleeSpriteStatus, charCelestiaSpriteStatus, oppCelestiaSpriteStatus;
    protected int spacer, randSoundIntChar, randSoundIntOpp, randSoundIntOppHurt, randSoundIntCharHurt;
    protected int x;
    protected float opacityTxt, opacityPic;
    protected int comboPicArrayPosOpp;
    protected int statIndexOpp, statIndexChar, statusEffectCharacterYCoord, statusEffectOpponentYCoord, uiShakeEffectOffsetCharacter = 1, uiShakeEffectOffsetOpponent = 1, basicY = 0;
    protected int itemX, itemY;
    protected AttackType characterAttackType = AttackType.REGULAR;
    protected AttackType opponentAttackType = AttackType.REGULAR;
    protected float statusEffectCharacterOpacity, statusEffectOpponentOpacity;
    protected int furyBarY;
    protected String attackStr;
    protected PlayerType runningFury;
    protected String[] achievementName, achievementDescription, achievementClass, achievementPoints;
    protected int activeAttack;
    protected int oppBarYOffset, attackMenuXPos, attackMenuTextXPos, attackMenuTextYPos, y;
    protected boolean limitRunning;
    protected float charPointInc;
    protected float opponentDamageOpacity, playerDamageOpacity, comicBookTextOpacity, furyComboOpacity;
    protected String manipulateThis;
    protected int one, two, three, four, oneO, twoO, threeO, fourO;
    protected int comicBookTextPositionY, opponentDamageYLoc, playerDamageYCoord;
    protected float opac;
    protected float damageLayerOpacity;
    protected int comicBookTextIndex;
    protected int furyLevel;
    protected boolean isCharacterAttacking;
    protected int columnIndex;
    protected int rowIndex;
    private int currentCharacterQueLoop, currentOpponentQueLoop;
    private int lastCharacterQueLoop, lastOpponentQueLoop;
    private int characterUiLoop, opponentUiLoop;
    private long characterQueDelta, opponentQueDelta;
    private long opponentAiTimeout, opponentAiDelta;
    private int furyBarCoolDownFactor;
    private final float maxAtb = 290f;
    private long timerDelta;
    public int timeLimit, count2;
    public boolean playingCutscene;
    private boolean characterAtb = true, opponentAtb = true;
    public boolean isRunning = false;
    public String timeStr;//, scene;
    public int time1, time2, time3;
    private float characterAtbValue;
    private float opponentAtbValue;
    private float secondCount;
    private int matchDuration, playTimeCounter;
    private long animationLoopADelta;
    private int animationLoopA;
    private long animationLoopBDelta;
    private int animationLoopB;
    private long animationLoopCDelta;
    private long animationLoopDDelta;
    private int animationLoopD;
    private long animationLoopEDelta;
    private long achievementDelta;
    protected final int FURY_BAR_MAX = 1000;


    protected GamePlay() {
        furyBarCoolDownFactor = 30 - (8 + (State.get().getLogin().resolveDifficultyInt() * 2));
    }

    /**
     * Set stat index
     *
     * @param dex
     */
    public void setStatIndex(int dex) {
        statIndex = dex;
    }

    public void setStatusPic(PlayerType playerType) {
        if (playerType == PlayerType.PLAYER1) {
            statusEffectCharacterOpacity = 1.0f;
            statusEffectCharacterYCoord = 0;
            statIndexChar = statIndex;
        }
        if (playerType == PlayerType.PLAYER2) {
            statusEffectOpponentOpacity = 1.0f;
            statusEffectOpponentYCoord = 0;
            statIndexOpp = statIndex;
        }
    }

    public void setNumOfBoards(int i) {
        /*
        numberOfStoryPix = i;
        storyPicArr = new VolatileImage[numberOfStoryPix];
        storyPicArrStr = new String[numberOfStoryPix];*/
    }

    public void setPicAt(int i, String s) {
        storyPicArrStr[i] = s;
    }

    /**
     * Draws Achievements
     */
    public void drawAchievements() {
        int unlocks = Achievement.get().getNumberOfAchievements();
        achievementName = new String[unlocks];
        achievementDescription = new String[unlocks];
        achievementClass = new String[unlocks];
        achievementPoints = new String[unlocks];
        for (int iteration = 0; iteration < unlocks; iteration++) {
            String[] info = Achievement.get().getInfo(iteration);
            achievementName[iteration] = info[0]; //getInfo
            achievementDescription[iteration] = info[1]; //desc
            achievementClass[iteration] = info[2]; //class
            achievementPoints[iteration] = info[3]; //points
        }
    }

    /**
     * Get the CharacterEnum
     */
    protected void setCharMoveset() {
        Characters.get().getCharacter().setCharacterAttackArrays();
    }

    /**
     * Set STATS
     *
     * @param physicalAttacks
     * @param celestiaAttacks
     * @param itemAttacks
     */
    public void setCharacterAttackArrays(String[] physicalAttacks, String[] celestiaAttacks, String[] itemAttacks) {
        System.arraycopy(physicalAttacks, 0, this.physicalAttacks, 0, physicalAttacks.length);
        System.arraycopy(celestiaAttacks, 0, this.celestiaAttacks, 0, celestiaAttacks.length);
        System.arraycopy(itemAttacks, 0, this.itemAttacks, 0, itemAttacks.length);
    }

    /**
     * Get the move selected by the player
     */
    protected String getAttack(int move) {
        String txt = Characters.get().getOpponent().getMoveQued(move);
        return txt;
    }

    /**
     * Get hurtChar type
     *
     * @return hurtChar type
     */
    public AttackType getAttackType(PlayerType playerType) {
        AttackType result = AttackType.REGULAR;
        if (playerType == PlayerType.PLAYER1) {
            result = characterAttackType;
        } else if (playerType == PlayerType.PLAYER2) {
            result = opponentAttackType;
        }
        return result;
    }

    /**
     * set hurtChar type, normal or fury
     */
    public void setAttackType(AttackType attackType, PlayerType playerType) {
        if (playerType == PlayerType.PLAYER1) {
            characterAttackType = attackType;
        }
        if (playerType == PlayerType.PLAYER2) {
            opponentAttackType = attackType;
        }
    }

    public String getFavChar(int here) {
        return charNames[here].name();
    }

    public boolean shakeCharacterLifeBar(int loop) {
        int computedPosition = -1;//so that we start from zero
        for (int iteration = 0; iteration < lifeBarShakeIterations; iteration++) // shakes opponents LifeBar in a cool way as isWithinRange as Black n White flashy Anime effect
        {
            for (int i = 0; i < lifeBarShakeInnerIterations; i++) {
                computedPosition++;
                if (loop == computedPosition) {
                    uiShakeEffectOffsetCharacter += 1;
                    return true;
                }
            }
            for (int i = 0; i < lifeBarShakeInnerIterations; i++) {
                computedPosition++;
                if (loop == computedPosition) {
                    uiShakeEffectOffsetCharacter -= 1;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean shakeOpponentLifeBar(int loop) {
        int computedPosition = -1;//so that we start from zero
        for (int iteration = 0; iteration < lifeBarShakeIterations; iteration++) // shakes opponents LifeBar in a cool way as isWithinRange as Black n White flashy Anime effect
        {
            for (int i = 0; i < lifeBarShakeInnerIterations; i++) {
                computedPosition++;
                if (loop == computedPosition) {
                    uiShakeEffectOffsetOpponent += 1;
                    return true;
                }
            }
            for (int i = 0; i < lifeBarShakeInnerIterations; i++) {
                computedPosition++;
                if (loop == computedPosition) {
                    uiShakeEffectOffsetOpponent -= 1;
                    return true;
                }
            }
        }
        return false;
    }

    protected void checkStatus() {
        if (characterAttacks.size() == 4) {
            attack();
        }
    }

    /**
     * assigns pic to array index
     */
    public void setSprites(PlayerType playerType, int meleeSprite, int statusSprite) {
        if (playerType == PlayerType.PLAYER1) {
            charMeleeSpriteStatus = meleeSprite;
            charCelestiaSpriteStatus = statusSprite;
        }
        if (playerType == PlayerType.PLAYER2) {
            oppMeleeSpriteStatus = meleeSprite;
            oppCelestiaSpriteStatus = statusSprite;
        }
    }

    /**
     * Makes text white, meaning its OK to select a move
     */
    protected void enableSelection() {
        currentColor = Color.BLACK;
        safeToSelect = true;
    }

    /**
     * Makes text red, meaning its NOT OK to select a move
     */
    protected void disableSelection() {
        currentColor = javafx.scene.paint.Color.RED;
        safeToSelect = false;
    }

    /**
     * Attacks the opponent
     */
    public void attack() {
        if (characterAttacks.isEmpty()) return;
        disableSelection();
        prepareCharacterAttack();
        if (NetworkManager.get().isOnline()) {
            NetworkManager.get().send(attackStr = String.format("%s:%s:%s:%s:%s", characterAttacks.get(0), characterAttacks.get(1), characterAttacks.get(2), characterAttacks.get(3), NetworkConstants.ATTACK_POSTFIX));
        }
    }

    private void prepareCharacterAttack() {
        triggerCharacterAttack = true;
        currentCharacterQueLoop = 0;
        lastCharacterQueLoop = -1;
        characterQueDelta = -1;
        characterUiLoop = 0;
    }

    private void prepareOpponentAttack() {
        triggerOpponentAttack = true;
        currentOpponentQueLoop = 0;
        lastOpponentQueLoop = -1;
        opponentQueDelta = -1;
        opponentUiLoop = 0;
    }

    public void update(long delta) {
        super.update(delta);
        if (loadAssets) return;
        if (playingCutscene) return;
        if (paused) return;
        if (!gameOver) {
            if (!showBrag) {
                if (ScndGenLegends.get().getSubMode() != SubMode.STORY_MODE) {
                    musNotice();
                    Overlay.get().primaryNotice(Characters.get().getOpponent().getBraggingRights(RenderCharacterSelection.get().getSelectedCharIndex()));
                } else {
                    Overlay.get().primaryNotice("");
                    Overlay.get().secondaryNotice("");
                }
                showBrag = true;
            }
            if (getCharacterAtbPercent() >= 1.0f)
                enableSelection();
            else
                disableSelection();
            handleOpponentAi(delta);
            handleOpponentAttacks(delta);
            handleCharacterAttacks(delta);
            animateTimer(delta);
            animateForeground(delta);
            animateLoopB(delta);
            animateAmbientLayers(delta);
            animateLoopD(delta);
            updateMatchStatus();
            if ((delta - animationLoopEDelta) > MS1320) {
                animationLoopEDelta = delta;
                if (!isFuryBarFull())
                    incrementFuryLevel(-furyBarCoolDownFactor);
            }
        } else {
            if ((delta - achievementDelta) > MS1320) {
                achievementDelta = delta;
                if (unlockedAchievementInstance < achievementClass.length - 1)
                    unlockedAchievementInstance++;
                else
                    unlockedAchievementInstance = 0;
            }
        }
    }

    private void handleOpponentAi(long delta) {
        boolean singlePlayerOrStoryMode = ScndGenLegends.get().getSubMode() == SubMode.SINGLE_PLAYER || ScndGenLegends.get().getSubMode() == SubMode.STORY_MODE;
        if (singlePlayerOrStoryMode && !triggerOpponentAttack && !playingCutscene && !NetworkManager.get().isOnline()) {
            if (singlePlayerOrStoryMode && (delta - opponentAiDelta) > MS16) {
                opponentAiDelta = delta;
                if (opponentAiTimeout < State.get().getLogin().getDifficultyDynamic()) {
                    opponentAiTimeout += 16;
                } else if (isOpponentAtbFull()) {
                    opponentAttacks.clear();
                    opponentAiTimeout = 0;
                    for (int i : updateAndGetOpponentAttackQue()) {
                        if (opponentAttacks.size() < 4)
                            opponentAttacks.add(i + 1);
                    }
                    prepareOpponentAttack();
                }
            }
        }
    }

    private void handleOpponentAttacks(long delta) {
        if (limitRunning && runningFury == PlayerType.PLAYER2) {
            if (currentOpponentQueLoop < 9) {
                setSprites(PlayerType.PLAYER2, currentOpponentQueLoop + 1, 11);
                setSprites(PlayerType.PLAYER1, 0, 11);
                if ((delta - characterQueDelta) > MS33) {
                    characterQueDelta = delta;
                    if (shakeOpponentLifeBar(characterUiLoop)) {
                        characterUiLoop++;
                    } else {
                        furySound();
                        hurtSoundOpp();
                        lifePhysUpdateSimple(PlayerType.PLAYER1, 100);
                        currentOpponentQueLoop++;
                        comboPicArrayPosOpp = currentOpponentQueLoop;
                        furyComboOpacity = 1.0f;
                        characterUiLoop = 0;
                    }
                }
            } else {
                comboPicArrayPosOpp = 8;
                resumeOpponentAtb();
                setSprites(PlayerType.PLAYER2, 9, 11);
                setSprites(PlayerType.PLAYER1, 9, 11);
                resetBreak();
                setAttackType(AttackType.REGULAR, PlayerType.PLAYER1);
                limitRunning = false;
            }
        } else if (triggerOpponentAttack) {
            if (lastOpponentQueLoop == -1) {
                pauseOpponentAtb();
                setOpponentAtbValue(0);
            }
            if (lastOpponentQueLoop != currentOpponentQueLoop && !opponentAttacks.isEmpty()) {
                opponentQueDelta = delta;
                lastOpponentQueLoop = currentOpponentQueLoop;
                setAttackSpritesAndTrigger(opponentAttacks.pop(), PlayerType.PLAYER2, PlayerType.PLAYER1, this, Characters.get().getOpponent());//add mode reference here
            }
            if ((delta - opponentQueDelta) > MS33) {
                opponentQueDelta = delta;
                if (shakeCharacterLifeBar(opponentUiLoop)) {
                    opponentUiLoop++;
                } else {
                    currentOpponentQueLoop++;
                    opponentUiLoop = 0;
                    if (opponentAttacks.isEmpty()) {
                        revertToDefaultSprites();
                        resumeOpponentAtb();
                        triggerOpponentAttack = false;
                    }
                }
            }
        }
    }

    private void handleCharacterAttacks(long delta) {
        if (limitRunning && runningFury == PlayerType.PLAYER1) {
            if (currentCharacterQueLoop < 9) {
                setSprites(PlayerType.PLAYER1, currentCharacterQueLoop + 1, 11);
                setSprites(PlayerType.PLAYER2, 0, 11);
                if ((delta - characterQueDelta) > MS33) {
                    characterQueDelta = delta;
                    if (shakeOpponentLifeBar(characterUiLoop)) {
                        characterUiLoop++;
                    } else {
                        furySound();
                        hurtSoundOpp();
                        lifePhysUpdateSimple(PlayerType.PLAYER2, 100);
                        currentCharacterQueLoop++;
                        comboPicArrayPosOpp = currentCharacterQueLoop;
                        furyComboOpacity = 1.0f;
                        characterUiLoop = 0;
                    }
                }
            } else {
                comboPicArrayPosOpp = 8;
                resumeCharacterAtb();
                setSprites(PlayerType.PLAYER1, 9, 11);
                setSprites(PlayerType.PLAYER2, 9, 11);
                resetBreak();
                setAttackType(AttackType.REGULAR, PlayerType.PLAYER1);
                limitRunning = false;
            }
        } else if (triggerCharacterAttack) {
            if (lastCharacterQueLoop == -1) {
                pauseCharacterAtb();
                setCharacterAtbValue(0);
            }
            if (lastCharacterQueLoop != currentCharacterQueLoop && !characterAttacks.isEmpty()) {
                characterQueDelta = delta;
                lastCharacterQueLoop = currentCharacterQueLoop;
                setAttackSpritesAndTrigger(characterAttacks.pop(), PlayerType.PLAYER1, PlayerType.PLAYER2, this, Characters.get().getCharacter());//add mode reference here
            }
            if ((delta - characterQueDelta) > MS33) {
                characterQueDelta = delta;
                if (shakeOpponentLifeBar(characterUiLoop)) {
                    characterUiLoop++;
                } else {
                    currentCharacterQueLoop++;
                    characterUiLoop = 0;
                    if (characterAttacks.isEmpty()) {
                        revertToDefaultSprites();
                        resumeCharacterAtb();
                        triggerCharacterAttack = false;
                    }
                }
            }
        }
    }

    private void animateLoopD(long delta) {
        if ((delta - animationLoopDDelta) > MS33) {
            animationLoopDDelta = delta;
            if (animateLoopDLogic(animationLoopD)) {
                animationLoopD++;
            } else {
                animationLoopD = 0;
            }
        }
    }

    private void animateAmbientLayers(long delta) {
        if ((delta - animationLoopCDelta) > MS16) {
            animationLoopCDelta = delta;
            if (getAmbientDirection() == AnimationDirection.HORIZONTAL) {
                setAmbientForegroundX(getAmbientForegroundX() - getAmbSpeed1());
                setAmbientBackgroundX(getAmbientBackgroundX() - getAmbSpeed2());
                if (getAmbientForegroundX() < -960) {
                    setAmbientForegroundX(852);
                }
                if (getAmbientBackgroundX() < (-960)) {
                    setAmbientBackgroundX(852);
                }
            } else if (getAmbientDirection() == AnimationDirection.VERTICAL) {
                setAmbientForegroundY(getAmbientForegroundY() + getAmbSpeed1());
                setAmbientBackgroundY(getAmbientBackgroundY() + getAmbSpeed2());
                if (getAmbientForegroundY() > 480) {
                    setAmbientForegroundY(-480);
                }
                if (getAmbientBackgroundY() > 480) {
                    setAmbientBackgroundY(-480);
                }
            }
        }
    }

    private void animateForeground(long delta) {
        if ((delta - animationLoopADelta) > MS33) {
            animationLoopADelta = delta;
            if (animateForegroundImpl(animationLoopA)) {
                animationLoopA++;
            } else {
                animationLoopA = 0;
            }
        }
    }

    private void animateLoopB(long delta) {
        if ((delta - animationLoopBDelta) > MS33) {
            animationLoopBDelta = delta;
            if (animateLoopBLogic(animationLoopB)) {
                animationLoopB++;
            } else {
                animationLoopB = 0;
            }
        }
    }

    private void animateTimer(long delta) {
        if ((delta - timerDelta) > MS16) {
            timerDelta = delta;
            Achievement.get().scan(this);
            if (characterAtbValue <= maxAtb && characterAtb) {
                characterAtbValue += Characters.get().getCharRecoverySpeed();
            }
            if (opponentAtbValue <= maxAtb && opponentAtb && !playingCutscene) {
                opponentAtbValue += Characters.get().getOppRecoverySpeed();
            }
            if (timeLimit <= INFINITE_TIME && !playingCutscene) {
                if (secondCount < 1000) //continue till we make a second
                {
                    secondCount += 16.67f;
                } else {
                    try {
                        if (timeLimit < INFINITE_TIME && timeLimit > 0) {
                            timeLimit -= 1;
                            timeStr = "" + timeLimit;
                            secondCount = 0.0f;
                            switch (timeStr.length()) {
                                case 1:
                                    time1 = 10;
                                    time2 = Integer.parseInt(String.valueOf(timeStr.charAt(0)));
                                    time3 = 10;
                                    break;
                                case 2:
                                    time1 = 10;
                                    time2 = Integer.parseInt(String.valueOf(timeStr.charAt(0)));
                                    time3 = Integer.parseInt(String.valueOf(timeStr.charAt(1)));
                                    break;
                                case 3:
                                    time1 = Integer.parseInt(String.valueOf(timeStr.charAt(0)));
                                    time2 = Integer.parseInt(String.valueOf(timeStr.charAt(1)));
                                    time3 = Integer.parseInt(String.valueOf(timeStr.charAt(2)));
                                    break;
                            }
                        }
                    } catch (Exception nfe) {
                        nfe.printStackTrace(System.err);
                    }
                }
            }
        }
    }

    private boolean animateLoopDLogic(int loop) {
        int computedPosition = -1;
        for (int iteration = 0; iteration <= 10; iteration++) {
            computedPosition++;
            if (loop == computedPosition) {
                setCharYcord(getCharYcord() + 1);
                setOppYcord(getOppYcord() + 1);
                return true;
            }
        }
        for (int iteration = 0; iteration <= 10; iteration++) {
            computedPosition++;
            if (loop == computedPosition) {
                setCharYcord(getCharYcord() - 1);
                setOppYcord(getOppYcord() - 1);
                return true;
            }
        }
        return false;
    }

    private boolean animateForegroundImpl(int loop) {
        int computedPosition = -1;//so that we start from zero
        switch (getForegroundDirection()) {
            case ROTATION:
                for (int iteration = 0; iteration <= getAnimationLoops(); iteration++) {
                    computedPosition++;
                    if (loop == computedPosition) {
                        setForeGroundPositionX(getForeGroundPositionX() + getForeGroundXIncrement());
                        setForeGroundPositionY(getForeGroundPositionY() + getForeGroundYIncrement());
                        return true;
                    }
                }
                for (int iteration = 0; iteration <= getAnimationLoops(); iteration++) {
                    computedPosition++;
                    if (loop == computedPosition) {
                        setForeGroundPositionX(getForeGroundPositionX() - getForeGroundXIncrement());
                        setForeGroundPositionY(getForeGroundPositionY() + getForeGroundYIncrement());
                        return true;
                    }
                }
                for (int iteration = 0; iteration <= getAnimationLoops(); iteration++) {
                    computedPosition++;
                    if (loop == computedPosition) {
                        setForeGroundPositionX(getForeGroundPositionX() - getForeGroundXIncrement());
                        setForeGroundPositionY(getForeGroundPositionY() - getForeGroundYIncrement());
                        return true;
                    }
                }
                for (int iteration = 0; iteration <= getAnimationLoops(); iteration++) {
                    computedPosition++;
                    if (loop == computedPosition) {
                        setForeGroundPositionX(getForeGroundPositionX() + getForeGroundXIncrement());
                        setForeGroundPositionY(getForeGroundPositionY() - getForeGroundYIncrement());
                        return true;
                    }
                }
                break;
            default:
                for (int inner = 0; inner <= getAnimationLoops(); inner++) {
                    computedPosition++;
                    if (loop == computedPosition) {
                        switch (getForegroundDirection()) {
                            case VERTICAL_HORIZONTAL:
                                setForeGroundPositionX(getForeGroundPositionX() + getForeGroundXIncrement());
                                setForeGroundPositionY(getForeGroundPositionY() + getForeGroundYIncrement());
                                break;
                            case HORIZONTAL:
                                setForeGroundPositionX(getForeGroundPositionX() + getForeGroundXIncrement());
                                break;
                            case VERTICAL:
                                setForeGroundPositionY(getForeGroundPositionY() + getForeGroundYIncrement());
                                break;
                        }
                        return true;
                    }
                }
                for (int inner = 0; inner <= getAnimationLoops(); inner++) {
                    computedPosition++;
                    if (loop == computedPosition) {
                        switch (getForegroundDirection()) {
                            case VERTICAL_HORIZONTAL:
                                setForeGroundPositionX(getForeGroundPositionX() - getForeGroundXIncrement());
                                setForeGroundPositionY(getForeGroundPositionY() - getForeGroundYIncrement());
                                break;
                            case HORIZONTAL:
                                setForeGroundPositionX(getForeGroundPositionX() - getForeGroundXIncrement());
                                break;
                            case VERTICAL:
                                setForeGroundPositionY(getForeGroundPositionY() - getForeGroundYIncrement());
                                break;
                        }
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    private boolean animateLoopBLogic(int loop) {
        return false;
    }

    public void newInstance() {
        gameOver = false;
        showBrag = false;
        characterAtb = true;
        opponentAtb = true;
        setCharacterAtbValue(0);
        setOpponentAtbValue(0);
        setFuryLevel(0);
        characterAttacks.clear();
        opponentAttacks.clear();
        triggerCharacterAttack = false;
        triggerOpponentAttack = false;
        currentCharacterQueLoop = 0;
        currentOpponentQueLoop = 0;
        revertToDefaultSprites();
        revertToDefaultSprites();
        opponentAiTimeout = 0;
        unlockedAchievementInstance = 0;
        achievementName = new String[0];
        achievementDescription = new String[0];
        achievementClass = new String[0];
        achievementPoints = new String[0];
        limitRunning = false;//incase match ended in fury
        loadAssets = true;
        opponentDamageXLoc = 150;
        playerDamageXLoc = 575;
        statusEffectOpponentOpacity = 0.0f;
        statusEffectCharacterOpacity = 0.0f;
        statIndexChar = 0;
        statIndexOpp = 0;
        oppBarYOffset = 435;
        paneCord = 306;
        furyBarY = 130;
        itemX = 215;
        itemY = 360;
        ambientForegroundX = 0;
        ambientForegroundY = 0;
        ambientBackgroundX = 0;
        ambientBackgroundX = 0;
        foreGroundPositionY = 0;
        foreGroundPositionX = 0;
        setForeGroundPositionX(0);
        setForeGroundPositionY(0);
        Achievement.get().newInstance();
        if (ScndGenLegends.get().getSubMode() == SubMode.STORY_MODE) {
            playingCutscene = true;
            timeLimit = StoryMode.get().timeLimit;
        } else if (ScndGenLegends.get().getSubMode() == SubMode.LAN_CLIENT) {
            timeLimit = NetworkManager.get().hostTimeLimit;
        } else {
            timeLimit = State.get().getLogin().getTimeLimit();
        }
        recordPlayTime();
        count2 = 0;
        //===========================================
        charXcord = 10;
        charYcord = 10;
        oppYcord = 10;
        opac = 1.0f;
        lifeBarShakeIterations = 2;
        lifeBarShakeInnerIterations = 4;
        x2 = 560;
        comX = 380;
        comY = 100;
        y2 = 435;
        oppXcord = 10;
        numOfComicPics = 9;
        characterHpAsPercent = 100;
        opponentHpAsPercent = 100;
        lbx2 = 500;
        lby2 = 420;
        charMeleeSpriteStatus = 9;
        oppMeleeSpriteStatus = 9;
        charCelestiaSpriteStatus = 11;
        oppCelestiaSpriteStatus = 11;
        spacer = 27;
        x = 2;
        opacityTxt = 10;
        comboPicArrayPosOpp = 8;
        uiShakeEffectOffsetCharacter = 1;
        uiShakeEffectOffsetOpponent = 1;
        characterAtb = true;
        opponentAtb = true;
        isRunning = false;
        time1 = 10;
        time2 = 10;
        time3 = 10;
        secondCount = 1000.0f;
    }

    protected abstract void playBGMusic();

    /**
     * Set player 1 maximum characterHp
     *
     * @param Life - value
     */
    public void setMaxLife(int Life) {
        characterMaximumHp = Life;
    }

    /**
     * Legacy awesomeness
     *
     * @return is effect on?
     */
    public boolean isFancyEffect() {
        if (fancyBWAnimeEffect == 1) {
            fancyBWAnimeEffectEnabled = true;
        }
        if (fancyBWAnimeEffect != 1) {
            fancyBWAnimeEffectEnabled = false;
        }
        return fancyBWAnimeEffectEnabled;
    }

    /**
     * Get the characterEnum multiplier
     *
     * @return the damage multiplier
     */
    public float getCharacterStrengthMultiplier() {
        return Characters.get().getCharacter().getStrengthMultiplier();
    }

    /**
     * Get the opponent multiplier
     *
     * @return the damage multiplier
     */
    public float getOpponentStrengthMultiplier() {
        return Characters.get().getOpponent().getStrengthMultiplier();
    }

    /**
     * Determines if match has reached game over state
     */
    public void updateMatchStatus() {
        if (!gameOver) {
            if (opponentHp < 0 || characterHp < 0 || (timeLimit <= 0 && State.get().getLogin().isTimeLimited())) {
                if (opponentHp / opponentMaximumHp > characterHp / characterMaximumHp || opponentHp / opponentMaximumHp < characterHp / characterMaximumHp) {
                    gameOver();
                }
            }
            opponentLifePercentage = opponentHp / opponentMaximumHp;
            characterLifePercentage = characterHp / characterMaximumHp;
        }
    }

    /**
     * Get opponent 1 characterHp
     *
     * @return value
     */
    public float getOpponentHp() {
        return opponentHp;
    }

    /**
     * Set opponent 1 characterHp
     *
     * @param Life - value
     */
    public void setOpponentHp(int Life) {
        opponentHp = Life;
    }

    /**
     * Get opponent 1's maximum characterHp
     *
     * @return value
     */
    public float getOpponentMaximumHp() {
        return opponentMaximumHp;
    }

    /**
     * Set opponent 1's maximum characterHp
     *
     * @param Life
     */
    public void setOpponentMaximumHp(int Life) {
        opponentMaximumHp = Life;
    }

    /**
     * Resets the game after a match is done or cancelled
     */
    public void resetGame() {
        characterHp = characterMaximumHp;
        opponentHp = opponentMaximumHp;
        furyLevel = 5;
        Characters.get().getCharacter().setStrengthMultiplier(12.0f);
        Characters.get().getOpponent().setStrengthMultiplier(12.0f);
    }

    /**
     * Starts an actual fight
     */
    public void startFight() {
        newInstance();
        resetGame();
        startDrawing = 1;
        characterHpAsPercent = 100;
        opponentHpAsPercent = 100;
    }


    /**
     * Remove move from que
     */
    public void unQueMove() {
        if (!characterAttacks.isEmpty() && safeToSelect) {
            int moi = characterAttacks.removeLast();
            Characters.get().alterPoints2(moi);
            System.out.println("UNQUEUED " + moi);
        }
    }

    /**
     * logic PlayerType 1 characterHp
     *
     * @param value - value
     */
    public void updatePlayerLife(int value) {
        int scaled = Characters.get().getCharacter().getCelestiaMultiplier() * value;
        characterHp += scaled;
        daNum = (getCharacterHp() / getCharacterMaximumHp()) * 100; //perc characterHp x characterHp bar length
        lifePlain = Math.round(daNum); // round off
        lifeTotalPlain = Math.round(getCharacterHp()); // for text
        characterHpAsPercent = Math.round(lifePlain);
        Characters.get().setCurrLifeOpp(opponentHpAsPercent);
        Characters.get().setCurrLifeChar(characterHpAsPercent);
    }

    /**
     * logic opponent 1 characterHp
     *
     * @param value - value
     */
    public void updateOpponentLife(int value) {
        int scaled = Characters.get().getOpponent().getCelestiaMultiplier() * value;
        opponentHp += scaled;
        daNum2 = ((getOpponentHp() / getOpponentMaximumHp()) * 100); //perc characterHp x characterHp bar length
        lifePlain2 = Math.round(daNum2); // round off
        lifeTotalPlain2 = Math.round(getOpponentHp()); // for text
        opponentHpAsPercent = Math.round(lifePlain2);
        Characters.get().setCurrLifeOpp(opponentHpAsPercent);
        Characters.get().setCurrLifeChar(characterHpAsPercent);
    }

    /**
     * Get the CharacterEnum characterHp, these methods should be float as they are used in divisions
     *
     * @return CharacterEnum characterHp
     */
    public float getCharacterHp() {
        return (float) characterHp;
    }

    /**
     * Set player 1 characterHp
     *
     * @param Life - value
     */
    public void setCharacterHp(int Life) {
        characterHp = Life;
    }

    /**
     * Get the CharacterEnum max characterHp, these methods should be float as they are used in divisions
     *
     * @return CharacterEnum maximum characterHp
     */
    public float getCharacterMaximumHp() {
        return characterMaximumHp;
    }

    /**
     * Alter damage multipliers, used to strengthen/weaken attacks
     *
     * @param playerType the person calling the method
     * @param thisMuch   the number to alter by
     */
    public void alterStrength(PlayerType playerType, int thisMuch) {
        if (playerType == PlayerType.PLAYER1 && Characters.get().getOpponent().getStrengthMultiplier() > 0 && Characters.get().getOpponent().getStrengthMultiplier() < 20) {
            Characters.get().getOpponent().setStrengthMultiplier(Characters.get().getOpponent().getStrengthMultiplier() + thisMuch);
        }
        if (playerType == PlayerType.PLAYER2 && Characters.get().getCharacter().getStrengthMultiplier() > 0 && Characters.get().getCharacter().getStrengthMultiplier() < 20) {
            Characters.get().getCharacter().setStrengthMultiplier(Characters.get().getCharacter().getStrengthMultiplier() + thisMuch);
        }
    }

    /**
     * Determines if the player has won
     *
     * @return match status
     */
    public boolean hasWon() {
        return characterLifePercentage > opponentLifePercentage;
    }


    /**
     * Increment limit
     */
    private void incrementFuryBarLevel(final float increment) {
        new Thread() {
            @Override
            public void run() {
                for (int o = 0; o < increment; o++) {
                    if (furyLevel < FURY_BAR_MAX) {
                        try {
                            furyLevel += 1;
                            this.sleep(16);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace(System.err);
                        }
                    }
                }
            }
        }.start();
    }

    public void triggerFury(PlayerType playerType) {
        if (gameOver) return;
        if (playingCutscene) return;
        if (paused) return;
        if (limitRunning) return;//one at a time folks :)
        switch (playerType) {
            case PLAYER1:
                if (!isCharacterAtbFull() || !isFuryBarFull()) return;
                if (NetworkManager.get().isOnline()) {
                    NetworkManager.get().send(NetworkConstants.FURY_ATTACK);
                }
                setAttackType(AttackType.FURY, PlayerType.PLAYER1);
                pauseCharacterAtb();
                setCharacterAtbValue(0);
                prepareCharacterAttack();
                runningFury = playerType;
                limitRunning = true;
                break;
            case PLAYER2:
                if (!isOpponentAtbFull() || !isFuryBarFull()) return;
                setAttackType(AttackType.FURY, PlayerType.PLAYER2);
                pauseOpponentAtb();
                setOpponentAtbValue(0);
                prepareOpponentAttack();
                runningFury = playerType;
                limitRunning = true;
                break;
        }
    }


    /**
     * Sets limit onBackCancel to initial value
     */
    public void resetBreak() {
        furyLevel = 5;
    }

    /**
     * Updates the characterHp of CharacterEnum
     *
     * @param playerType the person affected
     * @param damageDone the characterHp to add/subtract
     */
    public void lifePhysUpdateSimple(PlayerType playerType, int damageDone) {
        switch (playerType) {
            case PLAYER1:
                damageDoneToCharacter = damageDone * getOpponentStrengthMultiplier();
                incrementFuryBarLevel(damageDoneToCharacter / 10);
                guiScreenChaos(damageDoneToCharacter, PlayerType.PLAYER2);
                for (int m = 0; m < damageDoneToCharacter; m++)
                    if (characterHp >= 0)
                        characterHp -= 1;
                daNum = ((getCharacterHp() / getCharacterMaximumHp()) * 100);
                lifePlain = Math.round(daNum);
                lifeTotalPlain = Math.round(getCharacterHp());
                characterHpAsPercent = Math.round(lifePlain);
                break;
            case PLAYER2:
            case BOSS:
                damageDoneToOpponent = damageDone * getCharacterStrengthMultiplier();
                incrementFuryBarLevel(damageDoneToOpponent / 10);
                guiScreenChaos(damageDoneToOpponent, PlayerType.PLAYER1);
                for (int m = 0; m < damageDoneToOpponent; m++)
                    if (opponentHp >= 0)
                        opponentHp -= 1;
                daNum2 = ((getOpponentHp() / getOpponentMaximumHp()) * 100);
                lifePlain2 = Math.round(daNum2);
                lifeTotalPlain2 = Math.round(getOpponentHp());
                opponentHpAsPercent = Math.round(lifePlain2);
                break;
        }
    }

    /**
     * Get the break status
     *
     * @return break status
     */
    public int getFuryLevel() {
        return furyLevel;
    }

    public void setFuryLevel(int level) {
        furyLevel = level;
    }

    public void incrementFuryLevel(int change) {
        furyLevel += change;
    }

    protected abstract void guiScreenChaos(float damageAmount, PlayerType who);

    protected abstract void furySound();

    protected abstract void hurtSoundChar();

    protected abstract void hurtSoundOpp();

    protected abstract void attackSoundOpp();

    public AnimationDirection getAmbientDirection() {
        return ambientDirection;
    }

    public AnimationDirection getForegroundDirection() {
        return foregroundDirection;
    }

    public float getCharacterHpAsPercent() {
        return characterHpAsPercent;
    }

    public float getOpponentHpAsPercent() {
        return opponentHpAsPercent;
    }

    public int getCharYcord() {
        return charYcord;
    }

    public void setCharYcord(int value) {
        charYcord = value;
    }

    public int getOppYcord() {
        return oppYcord;
    }

    public void setOppYcord(int value) {
        oppYcord = value;
    }

    public float getAnimationLoops() {
        return animationLoops;
    }

    public float getForeGroundPositionX() {
        return foreGroundPositionX;
    }

    public void setForeGroundPositionX(float foreGroundPositionX) {
        this.foreGroundPositionX = foreGroundPositionX;
    }

    public float getForeGroundPositionY() {
        return foreGroundPositionY;
    }

    public void setForeGroundPositionY(float foreGroundPositionY) {
        this.foreGroundPositionY = foreGroundPositionY;
    }

    public float getForeGroundXIncrement() {
        return foreGroundXIncrement;
    }

    public float getForeGroundYIncrement() {
        return foreGroundYIncrement;
    }


    public int getCharacterQueuedAttacks() {
        return characterAttacks.size();
    }

    public int getAmbSpeed1() {
        return ambSpeed1;
    }

    public int getAmbSpeed2() {
        return ambSpeed2;
    }

    public int getAmbientForegroundX() {
        return ambientForegroundX;
    }

    public void setAmbientForegroundX(int ambientForegroundX) {
        this.ambientForegroundX = ambientForegroundX;
    }

    public int getAmbientBackgroundX() {
        return ambientBackgroundX;
    }

    public void setAmbientBackgroundX(int ambientBackgroundX) {
        this.ambientBackgroundX = ambientBackgroundX;
    }

    public int getAmbientForegroundY() {
        return ambientForegroundY;
    }

    public void setAmbientForegroundY(int ambientForegroundY) {
        this.ambientForegroundY = ambientForegroundY;
    }

    public int getAmbientBackgroundY() {
        return ambientBackgroundY;
    }

    public void setAmbientBackgroundY(int ambientBackgroundY) {
        this.ambientBackgroundY = ambientBackgroundY;
    }

    /**
     * Flashy text at bottom of screen
     *
     * @param message
     */
    public void storyText(String message) {
        opacityTxt = 0.0f;
        battleInformation = new StringBuilder(message);
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY:
                onAccept();
                break;
            case MIDDLE:
                triggerFury(PlayerType.PLAYER1);
                break;
            case SECONDARY:
                onBackCancel();
                break;
        }
    }

    protected void cancelMatch() {
        ButtonBar.ButtonData firstPrompt = FxDialogs.yesNo("Confirmation", "Dude!?", "Are you sure you wanna quit?");
        if (firstPrompt == ButtonBar.ButtonData.YES) {
            if (playingCutscene)
                StoryMode.get().exitCinematic(true);
            if (NetworkManager.get().isOffline()) {
                if (isPaused()) {
                    onTogglePause();
                }
                terminateGameplay();
                RenderStageSelect.get().setStageSelected(false);
                if (ScndGenLegends.get().getSubMode() == SubMode.STORY_MODE) {
                    ScndGenLegends.get().loadMode(ModeEnum.MAIN_MENU);
                } else {
                    ScndGenLegends.get().loadMode(ModeEnum.CHAR_SELECT_SCREEN);
                }
            } else {
                ButtonBar.ButtonData secondPrompt = FxDialogs.yesNo("Are you sure?", "This will terminate the current network session", "Nuke from orbit?");
                if (secondPrompt == ButtonBar.ButtonData.YES) {
                    if (isPaused()) {
                        onTogglePause();
                    }
                    terminateGameplay();
                    NetworkManager.get().send(NetworkConstants.CANCEL_CONNECTIVITY);
                    NetworkManager.get().close();
                }
            }
        }
    }

    public void isCharacterAttacking(boolean value) {
        isCharacterAttacking = value;
    }

    /**
     * Animate attacks in graphics context
     */
    public void revertToDefaultSprites() {
        setSprites(PlayerType.PLAYER2, 9, 11);
        setSprites(PlayerType.PLAYER1, 9, 11);
        setSprites(PlayerType.BOSS, 9, 11);
    }

    /**
     * Get char AI
     *
     * @return AI - Personality
     */
    public int[] updateAndGetOpponentAttackQue() {
        int[] array = {};
        //when doing isWithinRange, all attacks
        if (getOpponentHp() / getOpponentMaximumHp() >= 1.00) {
            array = Characters.get().getOpponent().getAiProfile1();
        } //when doing isWithinRange, all attacks + 2 buffs
        else if (getOpponentHp() / getOpponentMaximumHp() >= 0.75 && getOpponentHp() / getOpponentMaximumHp() < 1.00) {
            array = Characters.get().getOpponent().getAiProfile2();
        } //when doing isWithinRange, 4 attacks + 2 buffs
        else if (getOpponentHp() / getOpponentMaximumHp() >= 0.50 && getOpponentHp() / getOpponentMaximumHp() < 0.75) {
            if (isFuryBarFull() && !limitRunning) {
                triggerFury(PlayerType.PLAYER2);
                array = new int[]{};
            } else {
                array = Characters.get().getOpponent().getAiProfile3();
            }
        } //when doing isWithinRange, 4 buffs + 2 moves
        else if (getOpponentHp() / getOpponentMaximumHp() >= 0.25 && getOpponentHp() / getOpponentMaximumHp() < 0.50) {
            if (isFuryBarFull() && !limitRunning) {
                triggerFury(PlayerType.PLAYER2);
                array = new int[]{};
            } else {
                array = Characters.get().getOpponent().getAiProfile4();
            }
        } //first fury, when doing isWithinRange, 4 buffs + 2 moves
        else {
            if (isFuryBarFull() && !limitRunning) {
                triggerFury(PlayerType.PLAYER2);
                array = new int[]{};
            } else {
                array = Characters.get().getOpponent().getAiProfile5();
            }
        }
        shuffleArray(array);
        return array;
    }

    private static void shuffleArray(int[] array) {
        int index, temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    /**
     * Attack sorter
     *
     * @param attack - the move to execute
     */
    public void setAttackSpritesAndTrigger(int attack, PlayerType source, PlayerType destination, GamePlay gamePlay, Character character) {
        String attackIdentifier = "";
        switch (attack) {
            case 0:
                gamePlay.setSprites(source, 9, 11);
                gamePlay.setSprites(destination, 9, 11);
                break;
            case 1:
                attackIdentifier = "01";
                break;
            case 2:
                attackIdentifier = "02";
                break;
            case 3:
                attackIdentifier = "03";
                break;
            case 4:
                attackIdentifier = "04";
                break;
            case 5:
                attackIdentifier = "05";
                break;
            case 6:
                attackIdentifier = "06";
                break;
            case 7:
                attackIdentifier = "07";
                break;
            case 8:
                attackIdentifier = "08";
                break;
            case 9:
                attackIdentifier = "09";
                break;
            case 10:
                attackIdentifier = "10";
                break;
            case 11:
                attackIdentifier = "11";
                break;
            case 12:
                attackIdentifier = "12";
                break;
        }
        setAttackSprites(gamePlay, source, destination, attack);
        triggerAttack(gamePlay, attackIdentifier, character, destination);
    }

    /**
     * call specific attack
     */
    public void setAttackSprites(GamePlay gamePlay, PlayerType source, PlayerType destination, int attack) {
        gamePlay.isCharacterAttacking(source == PlayerType.PLAYER1);
        //status moves use 10 (pose sprite)
        gamePlay.setSprites(source, attack > 9 ? 10 : attack, 11); //attack
        gamePlay.setSprites(destination, 0, 11); //defend
    }

    /**
     * ATTAAAAAAACK!!!!!!!
     */
    public void triggerAttack(GamePlay gamePlay, String attackIdentifier, Character character, PlayerType destination) {
        character.attack(attackIdentifier, destination, gamePlay);
    }

    //////////////////////////////////
    //////////////////////////////////
    //////////////////////////////////
    //////////////////////////////////

    public float getCharacterAtbValue() {
        return characterAtbValue;
    }

    public float getCharacterAtbPercent() {
        return (characterAtbValue / maxAtb);
    }

    public void setCharacterAtbValue(float thisNum) {
        characterAtbValue = thisNum;
    }

    public boolean isOpponentAtbFull() {
        return opponentAtbValue >= maxAtb;
    }

    public boolean isCharacterAtbFull() {
        return characterAtbValue >= maxAtb;
    }

    public float getOpponentAtbValue() {
        return opponentAtbValue;
    }

    public void setOpponentAtbValue(int thisNum2) {
        opponentAtbValue = thisNum2;
    }

    public void gameOver() {
        gameOver = true;
        closeAudio();
        State.get().getLogin().setPlayTime(playTimeCounter);
        Achievement.get().scan(this);
        //if not playStory scene, increment char usage
        if (ScndGenLegends.get().getSubMode() == SubMode.STORY_MODE == false) {
            State.get().getLogin().setCharacterUsage(RenderCharacterSelection.get().getCharName());
        }
        if (hasWon()) {
            showWinLabel();
        } else {
            showLoseLabel();
        }
        RenderStageSelect.get().newInstance();
        RenderCharacterSelection.get().newInstance();
        drawAchievements();
    }

    protected abstract void closeAudio();

    protected abstract void showWinLabel();

    protected abstract void showLoseLabel();

    private void recordPlayTime() {
        matchDuration = 0;
        playTimeCounter = State.get().getLogin().getPlayTime();
        new Thread() {
            @Override
            @SuppressWarnings("static-access")
            public void run() {
                do {
                    try {
                        this.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace(System.err);
                    }
                    playTimeCounter++;
                    matchDuration++;
                } while (!gameOver);
            }
        }.start();
    }

    public int getMatchTime() {
        return matchDuration;
    }

    /**
     * This thread executes when a game is over, designed to free unused memory
     */
    public void closingThread(boolean goToCharacterSelect) {
        if (goToCharacterSelect) {
            ScndGenLegends.get().loadMode(ModeEnum.CHAR_SELECT_SCREEN);
        } else {
            ScndGenLegends.get().loadMode(ModeEnum.MAIN_MENU);
        }
    }

    protected void updatePlayerProfile() {
        //logic profile operations
        incrementWinsOrLosses();
        State.get().getLogin().setPoints(Achievement.get().getNewUserPoints());
        //save profile
        State.get().saveConfigFile();
    }

    private void incrementWinsOrLosses() {
        State.get().getLogin().setNumberOfMatches(State.get().getLogin().getNumberOfMatches() + 1);
        if (getCharacterHp() < getOpponentHp()) {
            State.get().getLogin().setLosses(State.get().getLogin().getLosses() + 1);
            State.get().getLogin().setConsecutiveWins(0);
        } else {
            State.get().getLogin().setWins(State.get().getLogin().getWins() + 1);
            State.get().getLogin().setConsecutiveWins(State.get().getLogin().getConsecutiveWins() + 1);
        }
    }

    /**
     * Cancel the game mid fight
     */
    public void terminateGameplay() {
        gameOver = true;
        RenderCharacterSelection.get().newInstance();
        RenderStageSelect.get().newInstance();
        closeAudio();
    }

    public void pauseCharacterAtb() {
        characterAtb = false;
    }

    public void resumeCharacterAtb() {
        characterAtb = true;
    }

    public void pauseOpponentAtb() {
        opponentAtb = false;
    }

    public void resumeOpponentAtb() {
        opponentAtb = true;
    }

    public void musNotice() {
        io.github.subiyacryolite.enginev1.Overlay.get().secondaryNotice(RenderStageSelect.get().getAmbientMusicMetaData()[RenderStageSelect.get().getAmbientMusicIndex()]);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void opponentAttack(List<String> attackList) {
        opponentAttacks.clear();
        opponentAiTimeout = 0;
        for (String i : attackList) {
            if (opponentAttacks.size() < 4)
                opponentAttacks.add(Integer.parseInt(i));
        }
        prepareOpponentAttack();
    }

    protected boolean isFuryBarFull() {
        return getFuryLevel() >= FURY_BAR_MAX;
    }
}
