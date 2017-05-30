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
import com.scndgen.legends.render.RenderGamePlay;
import com.scndgen.legends.render.RenderStageSelect;
import com.scndgen.legends.state.GameState;
import io.github.subiyacryolite.enginev1.Mode;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.scndgen.legends.constants.GeneralConstants.INFINITE_TIME;

/**
 * This class draws and manipulates all sprites, images and effects used in the game
 *
 * @Author: Ifunga Ndana
 * @Class: GamePlay
 */
public abstract class GamePlay extends Mode {
    protected final String[] physicalAttacks = new String[]{"", "", "", ""}, celestiaAttacks = new String[]{"", "", "", ""}, itemAttacks = new String[]{"", "", "", ""};
    protected float characterHpAsPercent = 100, opponentHpAsPercent = 100;
    protected boolean triggerCharacterAttack;
    protected boolean triggerOpponentAttack;
    protected int done = 0; // if gameover
    protected LinkedList<Integer> characterAttacks = new LinkedList<>();
    protected LinkedList<Integer> opponentAttacks = new LinkedList<>();
    protected boolean threadsNotRunningYet = true, playATBFile = false;
    protected StringBuilder StatusText = new StringBuilder();
    protected int startDrawing = 0, menuBarY;
    protected Color currentColor = Color.RED;
    protected float angRot = 20;
    protected boolean safeToSelect;
    protected int unlockedAchievementInstance;
    protected int charXcord = 10, charYcord = 10, oppYcord = 10, statIndex = 0;
    protected int particlesLayer1PositionX = 0, particlesLayer1PositionY = 0, particlesLayer2PositionX = 0, particlesLayer2PositionY = 0;
    protected int numOfComicPics = 9;
    protected int foreGroundPositionX, foreGroundPositionY, foreGroundXIncrement, foreGroundYIncrement, animationLoops, delay;
    protected AnimationDirection animationDirection = AnimationDirection.VERTICAL;
    protected int oppXcord = 10;
    protected int playerDamageXLoc, opponentDamageXLoc;
    protected String scenePic = "images/bgBG2.png";
    protected String attackPicSrc = "images/trans.png";
    protected String[] storyPicArrStr;
    protected CharacterEnum[] charNames = LoginScreen.charNames;
    protected String attackPicOppSrc = "images/trans.png";
    protected int ambSpeed1, ambSpeed2, paneCord;
    protected StringBuilder battleInformation = new StringBuilder("");
    protected int count = 0, fpsInt = 0, fpsIntStat;
    protected StageAnimation animLayer;
    protected boolean loadedUpdaters;
    protected float daNum, daNum2;
    protected long lifePlain, lifeTotalPlain, lifePlain2, lifeTotalPlain2;
    protected int fancyBWAnimeEffect = 0;     //toggle fancy effect when HP low
    protected boolean fancyBWAnimeEffectEnabled;
    protected boolean isMoveQued, gameOver;
    protected int thisInt; //max damage that can be dealt by Celestia Physics
    protected int damageC, damageO;
    protected float characterHp, characterMaximumHp, opponentHp, opponentMaximumHp;
    protected int damageDoneToCharacter, damageDoneToOpponent;
    protected int limitTop = 1000;
    protected String versionString = " 2K17 RMX";
    protected int versioInt = 20120630; // yyyy-mm-dd
    protected float opponentLifePercentage, characterLifePercentage;
    protected Object source;
    protected int lifeBarShakeIterations = 2, lifeBarShakeInnerIterations = 4;
    protected int x2 = 560, comX = 380, comY = 100;
    protected int xLocal = 470;
    protected int y2 = 435;
    protected int statIndexOpp, statIndexChar, statusEffectCharacterYCoord, statusEffectOpponentYCoord, uiShakeEffectOffsetCharacter = 1, uiShakeEffectOffsetOpponent = 1, basicY = 0;
    protected boolean shaky1 = true;
    protected int animTime = 400, itemX = 0, itemY = 0;
    protected boolean runNew = true, effectChar = false;
    protected int bgX = 0;
    protected int numberOfStoryPix, lbx2 = 500;
    protected int lby2 = 420;
    protected AttackType characterAttackType = AttackType.PHYSICAL;
    protected AttackType opponentAttackType = AttackType.PHYSICAL;
    protected String statusChar = "", statusOpp = "";
    protected int charMeleeSpriteStatus = 9, oppMeleeSpriteStatus = 9, charCelestiaSpriteStatus = 11, oppCelestiaSpriteStatus = 11;
    protected float statusEffectCharacterOpacity, statusEffectOpponentOpacity;
    protected int furyBarY = 0;
    protected int[] fontSizes = {LoginScreen.bigTxtSize, LoginScreen.normalTxtSize, LoginScreen.normalTxtSize, LoginScreen.normalTxtSize};
    protected int attackInt = 0;
    protected String attackStr;
    protected boolean lagFactor = true;
    protected float currentXShear = 0, currentYShear = 0;
    protected boolean isFree = true, isFree2 = true;
    protected Player runningFury;
    protected String sysNot = "";
    protected float sysNotOpac = 0, sysNotOpacInc = (float) 0.1;
    protected String[] achievementName, achievementDescription, achievementClass, achievementPoints;
    protected int activeAttack = 0;
    protected int InfoBarYPose, spacer = 27, randSoundIntChar, randSoundIntOpp, randSoundIntOppHurt, randSoundIntCharHurt, YOffset = 15;
    protected int x = 2;
    protected int oppBarYOffset, attackMenuXPos, attackMenuTextXPos, attackMenuTextYPos, y = 0;
    protected float opacityTxt = 10, opacityPic = 0.0f;
    protected boolean limitRunning;
    protected float angleRaw, charPointInc;
    protected int result;
    protected float opponentDamageOpacity, playerDamageOpacity, comicBookTextOpacity, furyComboOpacity;
    protected int comboPicArrayPosOpp = 8;
    protected String manipulateThis;
    protected int one, two, three, four, oneO, twoO, threeO, fourO;
    protected int comicBookTextPositionY, opponentDamageYLoc, playerDamageYCoord;
    protected float opac = 1.0f;
    protected float damageLayerOpacity;
    protected int charOp = 10, comicBookTextIndex = 0;
    protected int limitBreak;
    protected boolean isCharacterAttacking;
    protected int columnIndex;
    protected int rowIndex;
    private int currentCharacterQueLoop, currentOpponentQueLoop;
    private int lastCharacterQueLoop, lastOpponentQueLoop;
    private int characterUiLoop, opponentUiLoop;
    private long characterQueDelta, opponentQueDelta;
    private long opponentAiTimeout, opponentAiDelta;
    private int furyBarCoolDownFactor;


    protected GamePlay() {
        furyBarCoolDownFactor = 30 - (8 + (GameState.getInstance().getLogin().resolveDifficulty() * 2));
    }

    /**
     * Set stat index
     *
     * @param dex
     */
    public void setStatIndex(int dex) {
        statIndex = dex;
    }

    public void setStatusPic(Player player) {
        if (player == Player.CHARACTER) {
            statusEffectCharacterOpacity = 1.0f;
            statusEffectCharacterYCoord = 0;
            statIndexChar = statIndex;
        }
        if (player == Player.OPPONENT) {
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
        int unlocks = Achievement.getInstance().getNumberOfAchievements();
        achievementName = new String[unlocks];
        achievementDescription = new String[unlocks];
        achievementClass = new String[unlocks];
        achievementPoints = new String[unlocks];
        for (int iteration = 0; iteration < unlocks; iteration++) {
            String[] info = Achievement.getInstance().getInfo(iteration);
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
        Characters.getInstance().getCharacter().setCharacterAttackArrays();
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
        String txt = Characters.getInstance().getOpponent().getMoveQued(move);
        return txt;
    }

    /**
     * Get hurtChar type
     *
     * @return hurtChar type
     */
    public AttackType getAttackType(Player player) {
        AttackType result = AttackType.PHYSICAL;
        if (player == Player.CHARACTER) {
            result = characterAttackType;
        } else if (player == Player.OPPONENT) {
            result = opponentAttackType;
        }
        return result;
    }

    /**
     * set hurtChar type, normal or fury
     */
    public void setAttackType(AttackType attackType, Player player) {
        if (player == Player.CHARACTER) {
            characterAttackType = attackType;
        }
        if (player == Player.OPPONENT) {
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
    public void setSprites(Player player, int oneA, int Magic) {
        if (player == Player.CHARACTER) {
            charMeleeSpriteStatus = oneA;
            charCelestiaSpriteStatus = Magic;
        }
        if (player == Player.OPPONENT) {
            oppMeleeSpriteStatus = oneA;
            oppCelestiaSpriteStatus = Magic;
        }
    }

    /**
     * Makes text white, meaning its OK to select a move
     */
    protected void enableSelection() {
        currentColor = javafx.scene.paint.Color.WHITE;
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
        if (!characterAttacks.isEmpty()) {
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER || ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE) {
                disableSelection();
                prepareCharacterAttack();
            } else if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
                //clear active combos
                setSprites(Player.CHARACTER, 9, 11);
                setSprites(Player.OPPONENT, 9, 11);
                //broadcast hurtChar on net
                attackStr = String.format("%s:%s:%s:%s:%s:", characterAttacks.get(0), characterAttacks.get(1), characterAttacks.get(2), characterAttacks.get(3), NetworkConstants.ATTACK_POSTFIX);
                NetworkManager.getInstance().send(attackStr);
                //attack on local
                disableSelection();
                prepareCharacterAttack();
            } else if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                //clear active combos
                setSprites(Player.CHARACTER, 9, 11);
                setSprites(Player.OPPONENT, 9, 11);
                //broadcast hurtChar on net
                attackStr = String.format("%s:%s:%s:%s:%s:", characterAttacks.get(0), characterAttacks.get(1), characterAttacks.get(2), characterAttacks.get(3), NetworkConstants.ATTACK_POSTFIX);
                NetworkManager.getInstance().send(attackStr);
                //attack on local
                disableSelection();
                prepareCharacterAttack();
                setCharacterAtbValue(0);
            }
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

    private float maxAtb = 290f;
    private long timerDelta;
    public int time, count2;
    public boolean playingCutscene;
    private boolean characterAtb = true, opponentAtb = true;
    public boolean isRunning = false;
    public String timeStr;//, scene;
    public int time1 = 10, time2 = 10, time3 = 10;
    private boolean newMatch;
    private float characterAtbValue;
    private float opponentAtbValue;
    private float secondCount = 1000.0f;
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

    public void update(long delta) {
        super.update(delta);
        if (loadAssets) return;
        if (playingCutscene) return;
        if (!gameOver) {
            handleOpponentAi(delta);
            handleOpponentAttacks(delta);
            handleCharacterAttacks(delta);
            animateTimer(delta);
            animateLoopA(delta);
            animateLoopB(delta);
            animateLoopC(delta);
            animateLoopD(delta);
            updateMatchStatus();
            if ((delta - animationLoopEDelta) > MS1320) {
                animationLoopEDelta = delta;
                if (getBreak() > 5 && getBreak() < 999) {
                    setBreak(-furyBarCoolDownFactor);
                }
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
        boolean singlePlayerOrStoryMode = ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER || ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE;
        if (singlePlayerOrStoryMode && !triggerOpponentAttack && !playingCutscene && !NetworkManager.getInstance().isOnline()) {
            if (singlePlayerOrStoryMode && (delta - opponentAiDelta) > MS16) {
                opponentAiDelta = delta;
                if (opponentAiTimeout < GameState.getInstance().getLogin().getDifficultyDynamic()) {
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
        if (limitRunning && runningFury == Player.OPPONENT) {
            if (currentOpponentQueLoop < 9) {
                setSprites(Player.OPPONENT, currentOpponentQueLoop, 11);
                setSprites(Player.CHARACTER, 0, 11);
                if ((delta - opponentQueDelta) > MS33) {
                    opponentQueDelta = delta;
                    if (shakeCharacterLifeBar(opponentUiLoop)) {
                        opponentUiLoop++;
                    } else {
                        furySound();
                        hurtSoundChar();
                        lifePhysUpdateSimple(Player.CHARACTER, 100);
                        comboPicArrayPosOpp = opponentUiLoop;
                        furyComboOpacity = 1.0f;
                        currentOpponentQueLoop++;
                        opponentUiLoop = 0;
                    }
                }
            } else {
                comboPicArrayPosOpp = 8;
                resumeOpponentAtb();
                setSprites(Player.OPPONENT, 9, 11);
                setSprites(Player.CHARACTER, 9, 11);
                resetBreak();
                setAttackType(AttackType.PHYSICAL, Player.OPPONENT);
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
                setAttackSpritesAndTrigger(opponentAttacks.pop(), Player.OPPONENT, Player.CHARACTER, this, Characters.getInstance().getCharacter());//add mode reference here
            }
            if ((delta - opponentQueDelta) > MS33) {
                opponentQueDelta = delta;
                if (shakeCharacterLifeBar(opponentUiLoop)) {
                    opponentUiLoop++;
                } else {
                    currentOpponentQueLoop++;
                    opponentUiLoop = 0;
                    if (opponentAttacks.isEmpty()) {
                        revertToDefaultSprites(Player.OPPONENT);
                        resumeOpponentAtb();
                        triggerOpponentAttack = false;
                    }
                }
            }
        }
    }

    private void handleCharacterAttacks(long delta) {
        if (limitRunning && runningFury == Player.CHARACTER) {
            if (currentCharacterQueLoop < 9) {
                setSprites(Player.CHARACTER, currentCharacterQueLoop, 11);
                setSprites(Player.OPPONENT, 0, 11);
                if ((delta - characterQueDelta) > MS33) {
                    characterQueDelta = delta;
                    if (shakeOpponentLifeBar(characterUiLoop)) {
                        characterUiLoop++;
                    } else {
                        furySound();
                        hurtSoundOpp();
                        lifePhysUpdateSimple(Player.OPPONENT, 100);
                        currentCharacterQueLoop++;
                        comboPicArrayPosOpp = currentCharacterQueLoop;
                        furyComboOpacity = 1.0f;
                        characterUiLoop = 0;
                    }
                }
            } else {
                comboPicArrayPosOpp = 8;
                resumeCharacterAtb();
                setSprites(Player.CHARACTER, 9, 11);
                setSprites(Player.OPPONENT, 9, 11);
                resetBreak();
                setAttackType(AttackType.PHYSICAL, Player.CHARACTER);
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
                setAttackSpritesAndTrigger(characterAttacks.pop(), Player.CHARACTER, Player.OPPONENT, this, Characters.getInstance().getOpponent());//add mode reference here
            }
            if ((delta - characterQueDelta) > MS33) {
                characterQueDelta = delta;
                if (shakeOpponentLifeBar(characterUiLoop)) {
                    characterUiLoop++;
                } else {
                    currentCharacterQueLoop++;
                    characterUiLoop = 0;
                    if (characterAttacks.isEmpty()) {
                        revertToDefaultSprites(Player.CHARACTER);
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

    private void animateLoopC(long delta) {
        if ((delta - animationLoopCDelta) > MS16) {
            animationLoopCDelta = delta;
            if (getAnimationDirection() != AnimationDirection.VERTICAL) {
                setParticlesLayer1PositionX(getParticlesLayer1PositionX() - getAmbSpeed1());
                setParticlesLayer2PositionX(getParticlesLayer2PositionX() - getAmbSpeed2());
                if (getParticlesLayer1PositionX() < -960) {
                    setParticlesLayer1PositionX(852);
                }
                if (getParticlesLayer2PositionX() < (-960)) {
                    setParticlesLayer2PositionX(852);
                }
            } else {
                setParticlesLayer1PositionY(getParticlesLayer1PositionY() + getAmbSpeed1());
                setParticlesLayer2PositionY(getParticlesLayer2PositionY() + getAmbSpeed2());
                if (getParticlesLayer1PositionY() > 480) {
                    setParticlesLayer1PositionY(-480);
                }
                if (getParticlesLayer2PositionY() > 480) {
                    setParticlesLayer2PositionY(-480);
                }
            }
        }
    }

    private void animateLoopA(long delta) {
        if ((delta - animationLoopADelta) > MS33) {
            animationLoopADelta = delta;
            if (animateLoopALogic(animationLoopA)) {
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
            Achievement.getInstance().scan(this);
            if (characterAtbValue <= maxAtb && characterAtb) {
                characterAtbValue += Characters.getInstance().getCharRecoverySpeed();
            }
            if (opponentAtbValue <= maxAtb && opponentAtb && !playingCutscene) {
                opponentAtbValue += Characters.getInstance().getOppRecoverySpeed();
            }
            if (time <= INFINITE_TIME && !playingCutscene) {
                if (secondCount < 1000) //continue till we make a second
                {
                    secondCount += 16.67f;
                } else {
                    try {
                        if (time < 999 && time > 0) {
                            time -= 1;
                            timeStr = "" + time;
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

    private boolean animateLoopALogic(int loop) {
        int computedPosition = -1;//so that we start from zero
        switch (getAnimationDirection()) {
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
                        switch (getAnimationDirection()) {
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
                        switch (getAnimationDirection()) {
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
        menuBarY = 360;
        threadsNotRunningYet = false;
        furyBarY = 130;
        itemX = 215;
        itemY = 360;
        setForeGroundPositionX(0);
        setForeGroundPositionY(0);
        Achievement.getInstance().newInstance();
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE) {
            playingCutscene = true;
            time = StoryMode.getInstance().timeLimit;
        } //if LAN, client uses hosts time preset
        else if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
            time = NetworkManager.getInstance().hostTime;
        } else {
            time = GameState.getInstance().getLogin().getTimeLimit();
        }
        recordPlayTime();
        characterAtbValue = 00;
        opponentAtbValue = 00;
        maxAtb = 290;
        gameOver = false;
        newMatch = true;

        if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE == false) {
            musNotice();
            io.github.subiyacryolite.enginev1.Overlay.getInstance().primaryNotice(Characters.getInstance().getOpponent().getBraggingRights(RenderCharacterSelection.getInstance().getSelectedCharIndex()));
        }
        count2 = 0;
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
     * Gets the games current version
     *
     * @return version
     */
    public String getVersionStr() {
        return versionString;
    }

    /**
     * Legacy awesomeness
     *
     * @return is effect on?
     */
    public boolean isFancyEffect() {
        {
            if (fancyBWAnimeEffect == 1) {
                fancyBWAnimeEffectEnabled = true;
            }
            if (fancyBWAnimeEffect != 1) {
                fancyBWAnimeEffectEnabled = false;
            }
        }
        return fancyBWAnimeEffectEnabled;
    }

    /**
     * Get the characterEnum multiplier
     *
     * @return the damage multiplier
     */
    public float getDamageMultiplierChar() {
        return Characters.getInstance().getCharacter().getDamageMultiplier();
    }

    /**
     * Get the opponent multiplier
     *
     * @return the damage multiplier
     */
    public float getDamageMultiplierOpp() {
        return Characters.getInstance().getOpponent().getDamageMultiplier();
    }

    /**
     * Determines if match has reached game over state
     */
    public void updateMatchStatus() {
        if (!gameOver) {
            if (opponentHp < 0 || characterHp < 0 || (time <= 0 && GameState.getInstance().getLogin().isTimeLimited())) {
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
        limitBreak = 5;
        Characters.getInstance().getCharacter().setDamageMultiplier(Characters.getInstance().getDamageMultiplier(Player.CHARACTER));
        Characters.getInstance().getOpponent().setDamageMultiplier(Characters.getInstance().getDamageMultiplier(Player.OPPONENT));
    }

    /**
     * Starts an actual fight
     */
    public void startFight() {
        resetGame();
        newInstance();
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
            Characters.getInstance().alterPoints2(moi);
            System.out.println("UNQUEUED " + moi);
        }
    }

    /**
     * logic Player 1 characterHp
     *
     * @param value - value
     */
    public void updatePlayerLife(int value) {
        int scaled = Characters.getInstance().getCharacter().getCelestiaMultiplier() * value;
        characterHp += scaled;
        daNum = (getCharacterHp() / getCharacterMaximumHp()) * 100; //perc characterHp x characterHp bar length
        lifePlain = Math.round(daNum); // round off
        lifeTotalPlain = Math.round(getCharacterHp()); // for text
        characterHpAsPercent = Math.round(lifePlain);
        Characters.getInstance().setCurrLifeOpp(opponentHpAsPercent);
        Characters.getInstance().setCurrLifeChar(characterHpAsPercent);
    }

    /**
     * logic opponent 1 characterHp
     *
     * @param value - value
     */
    public void updateOpponentLife(int value) {
        int scaled = Characters.getInstance().getOpponent().getCelestiaMultiplier() * value;
        opponentHp += scaled;
        daNum2 = ((getOpponentHp() / getOpponentMaximumHp()) * 100); //perc characterHp x characterHp bar length
        lifePlain2 = Math.round(daNum2); // round off
        lifeTotalPlain2 = Math.round(getOpponentHp()); // for text
        opponentHpAsPercent = Math.round(lifePlain2);
        Characters.getInstance().setCurrLifeOpp(opponentHpAsPercent);
        Characters.getInstance().setCurrLifeChar(characterHpAsPercent);
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
     * @param per      the person calling the method
     * @param thisMuch the number to alter by
     */
    public void alterDamageCounter(Player per, int thisMuch) {
        if (per == Player.CHARACTER && Characters.getInstance().getOpponent().getDamageMultiplier() > 0 && Characters.getInstance().getOpponent().getDamageMultiplier() < 20) {
            Characters.getInstance().getOpponent().setDamageMultiplier(Characters.getInstance().getOpponent().getDamageMultiplier() + thisMuch);
        }

        if (per == Player.OPPONENT && Characters.getInstance().getCharacter().getDamageMultiplier() > 0 && Characters.getInstance().getCharacter().getDamageMultiplier() < 20) {
            Characters.getInstance().getCharacter().setDamageMultiplier(Characters.getInstance().getCharacter().getDamageMultiplier() + thisMuch);
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
    private void incLImit(int ThisMuch) {
        final int inc = ThisMuch;
        new Thread() {
            //add one, make sure we dont go over 2000

            @SuppressWarnings("static-access")
            @Override
            public void run() {
                int icrement = inc;
                setName("Fury bar increment lastStoryScene");
                for (int o = 0; o < icrement; o++) {
                    if (limitBreak < limitTop) {
                        try {
                            limitBreak = limitBreak + 1;
                            this.sleep(15);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(RenderGamePlay.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }.start();
    }

    public void triggerFury(Player who) {
        limitBreak(who);
    }


    /**
     * Sets limit onBackCancel to initial value
     */
    public void resetBreak() {
        limitBreak = 5;
    }

    /**
     * limit break, wee!!!
     */
    public void limitBreak(Player player) {
        if (limitRunning) return;//one at a time folks :)
        runningFury = player;
        limitRunning = true;
        switch (runningFury) {
            case CHARACTER:
                if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT || ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                    NetworkManager.getInstance().send(NetworkConstants.FURY_ATTACK);
                }
                setAttackType(AttackType.FURY, Player.CHARACTER);
                pauseCharacterAtb();
                setCharacterAtbValue(0);
                prepareCharacterAttack();
                break;
            case OPPONENT:
                setAttackType(AttackType.FURY, Player.OPPONENT);
                pauseOpponentAtb();
                setOpponentAtbValue(0);
                prepareOpponentAttack();
                break;
        }
    }

    /**
     * Updates the characterHp of CharacterEnum
     *
     * @param forWho     the person affected
     * @param damageDone the characterHp to add/subtract
     */
    public void lifePhysUpdateSimple(Player forWho, int damageDone) {

        if (forWho == Player.CHARACTER) //Attack from player
        {
            damageDoneToCharacter = damageDone;
            incLImit(damageDoneToCharacter);
            guiScreenChaos(damageDone * getDamageMultiplierOpp(), Player.OPPONENT);
            for (int m = 0; m < damageDoneToCharacter; m++) {
                if (characterHp >= 0) {
                    characterHp -= getDamageMultiplierOpp();
                }
            }
            daNum = ((getCharacterHp() / getCharacterMaximumHp()) * 100); //perc characterHp x characterHp bar length
            lifePlain = Math.round(daNum); // round off
            lifeTotalPlain = Math.round(getCharacterHp()); // for text
            characterHpAsPercent = Math.round(lifePlain);
        }

        if (forWho == Player.OPPONENT || forWho == Player.BOSS) //Attack from CPU pponent 1
        {
            damageDoneToOpponent = damageDone;
            incLImit(damageDoneToOpponent);
            guiScreenChaos(damageDone * getDamageMultiplierChar(), Player.CHARACTER);
            for (int m = 0; m < damageDoneToOpponent; m++) {
                if (opponentHp >= 0) {
                    opponentHp -= getDamageMultiplierOpp();
                }
            }
            daNum2 = ((getOpponentHp() / getOpponentMaximumHp()) * 100); //perc characterHp x characterHp bar length
            lifePlain2 = Math.round(daNum2); // round off
            lifeTotalPlain2 = Math.round(getOpponentHp()); // for text
            opponentHpAsPercent = Math.round(lifePlain2);
        }
    }

    /**
     * Get the break status
     *
     * @return break status
     */
    public int getBreak() {
        return limitBreak;
    }

    public void setBreak(int change) {
        limitBreak += change;
    }

    protected abstract void guiScreenChaos(float damageAmount, Player who);

    protected abstract void furySound();

    protected abstract void hurtSoundChar();

    protected abstract void hurtSoundOpp();

    protected abstract void attackSoundOpp();

    public AnimationDirection getAnimationDirection() {
        return animationDirection;
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

    public int getAnimationLoops() {
        return animationLoops;
    }

    public long getDelay() {
        return delay;
    }

    public int getForeGroundPositionX() {
        return foreGroundPositionX;
    }

    public void setForeGroundPositionX(int foreGroundPositionX) {
        this.foreGroundPositionX = foreGroundPositionX;
    }

    public int getForeGroundPositionY() {
        return foreGroundPositionY;
    }

    public void setForeGroundPositionY(int foreGroundPositionY) {
        this.foreGroundPositionY = foreGroundPositionY;
    }

    public int getForeGroundXIncrement() {
        return foreGroundXIncrement;
    }

    public int getForeGroundYIncrement() {
        return foreGroundYIncrement;
    }


    public int getCharacterQueuedAttacks() {
        return characterAttacks.size();
    }

    public int getDone() {
        return done;
    }

    public CharacterEnum[] getCharNames() {
        return charNames;
    }

    public int getAmbSpeed1() {
        return ambSpeed1;
    }

    public int getAmbSpeed2() {
        return ambSpeed2;
    }

    public int getParticlesLayer1PositionX() {
        return particlesLayer1PositionX;
    }

    public void setParticlesLayer1PositionX(int particlesLayer1PositionX) {
        this.particlesLayer1PositionX = particlesLayer1PositionX;
    }

    public int getParticlesLayer2PositionX() {
        return particlesLayer2PositionX;
    }

    public void setParticlesLayer2PositionX(int particlesLayer2PositionX) {
        this.particlesLayer2PositionX = particlesLayer2PositionX;
    }

    public int getParticlesLayer1PositionY() {
        return particlesLayer1PositionY;
    }

    public void setParticlesLayer1PositionY(int particlesLayer1PositionY) {
        this.particlesLayer1PositionY = particlesLayer1PositionY;
    }

    public int getParticlesLayer2PositionY() {
        return particlesLayer2PositionY;
    }

    public void setParticlesLayer2PositionY(int particlesLayer2PositionY) {
        this.particlesLayer2PositionY = particlesLayer2PositionY;
    }

    /**
     * Draws battle message at bottom of screen
     *
     * @param message - what to display
     */
    public void showBattleMessage(String message) {
        storyText(message);
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
                triggerFury(Player.CHARACTER);
                break;
            case SECONDARY:
                onBackCancel();
                break;
        }
    }

    protected void cancelMatch() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Dude!?");
        alert.setContentText("Are you sure you wanna quit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (isPaused()) {
                onTogglePause();
            }
            terminateGameplay();
            RenderStageSelect.getInstance().setStageSelected(false);
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE) {
                ScndGenLegends.getInstance().loadMode(ModeEnum.MAIN_MENU);
            } else {
                ScndGenLegends.getInstance().loadMode(ModeEnum.CHAR_SELECT_SCREEN);
            }
            RenderCharacterSelection.getInstance().primaryNotice("Canceled Match");
        }
    }

    public void isCharacterAttacking(boolean value) {
        isCharacterAttacking = value;
    }

    /**
     * Animate attacks in graphics context
     *
     * @param thischar - active characterEnum
     */
    public void revertToDefaultSprites(Player thischar) {
        if (thischar == Player.CHARACTER || thischar == Player.OPPONENT) {
            //sprites back to normal poses
            setSprites(Player.CHARACTER, 9, 11);
            setSprites(Player.OPPONENT, 9, 11);
        }
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
            array = Characters.getInstance().getOpponent().getAiProfile1();
        } //when doing isWithinRange, all attacks + 2 buffs
        else if (getOpponentHp() / getOpponentMaximumHp() >= 0.75 && getOpponentHp() / getOpponentMaximumHp() < 1.00) {
            array = Characters.getInstance().getOpponent().getAiProfile2();
        } //when doing isWithinRange, 4 attacks + 2 buffs
        else if (getOpponentHp() / getOpponentMaximumHp() >= 0.50 && getOpponentHp() / getOpponentMaximumHp() < 0.75) {
            if (getBreak() == 1000 && limitRunning) {
                triggerFury(Player.OPPONENT);
                array = new int[]{};
            } else {
                array = Characters.getInstance().getOpponent().getAiProfile3();
            }
        } //when doing isWithinRange, 4 buffs + 2 moves
        else if (getOpponentHp() / getOpponentMaximumHp() >= 0.25 && getOpponentHp() / getOpponentMaximumHp() < 0.50) {
            if (getBreak() == 1000 && limitRunning) {
                triggerFury(Player.OPPONENT);
                array = new int[]{};
            } else {
                array = Characters.getInstance().getOpponent().getAiProfile4();
            }
        } //first fury, when doing isWithinRange, 4 buffs + 2 moves
        else {
            if (getBreak() == 1000 && limitRunning) {
                triggerFury(Player.OPPONENT);
                array = new int[]{};
            } else {
                array = Characters.getInstance().getOpponent().getAiProfile5();
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
    public void setAttackSpritesAndTrigger(int attack, Player source, Player destination, GamePlay gamePlay, Character defaultOpponent) {
        String attackIdentifier = "";
        if (attack > 8) {
            destination = Player.SELF;
        }
        switch (attack) {
            case 0:
                gamePlay.setSprites(source, 9, 11);
                gamePlay.setSprites(destination, 9, 11);
                gamePlay.showBattleMessage("");
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
        triggerAttack(gamePlay, attackIdentifier, defaultOpponent, destination);
    }

    /**
     * call specific attack
     */
    public void setAttackSprites(GamePlay gamePlay, Player source, Player destination, int attack) {
        gamePlay.isCharacterAttacking(source == Player.CHARACTER);
        if (destination == Player.SELF) {
            gamePlay.setSprites(source, 10, 11); //USE ITEM
        } else {
            //status moves use 10 (pose sprite)
            gamePlay.setSprites(source, attack > 9 ? 10 : attack, 11); //attack
            gamePlay.setSprites(destination, 0, 11); //defend
        }
    }

    /**
     * ATTAAAAAAACK!!!!!!!
     */
    public void triggerAttack(GamePlay gamePlay, String attackIdentifier, Character opponent, Player destination) {
        opponent.attack(attackIdentifier, destination, gamePlay);
    }

    //////////////////////////////////
    //////////////////////////////////
    //////////////////////////////////
    //////////////////////////////////

    public float getCharacterAtbValue() {
        return characterAtbValue;
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
        GameState.getInstance().getLogin().setPlayTime(playTimeCounter);
        Achievement.getInstance().scan(this);
        //if not playStory scene, increment char usage
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE == false) {
            GameState.getInstance().getLogin().setCharacterUsage(RenderCharacterSelection.getInstance().getCharName());
        }
        if (hasWon()) {
            showWinLabel();
        } else {
            showLoseLabel();
        }
        RenderStageSelect.getInstance().newInstance();
        RenderCharacterSelection.getInstance().newInstance();
        drawAchievements();
    }

    protected abstract void closeAudio();

    protected abstract void showWinLabel();

    protected abstract void showLoseLabel();

    private void recordPlayTime() {
        matchDuration = 0;
        playTimeCounter = GameState.getInstance().getLogin().getPlayTime();
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
            ScndGenLegends.getInstance().loadMode(ModeEnum.CHAR_SELECT_SCREEN);
        } else {
            ScndGenLegends.getInstance().loadMode(ModeEnum.MAIN_MENU);
        }
    }

    protected void updatePlayerProfile() {
        //logic profile operations
        incrementWinsOrLosses();
        GameState.getInstance().getLogin().setPoints(Achievement.getInstance().getNewUserPoints());
        //save profile
        GameState.getInstance().saveConfigFile();
    }

    private void incrementWinsOrLosses() {
        if (newMatch) {
            newMatch = false;
            GameState.getInstance().getLogin().setNumberOfMatches(GameState.getInstance().getLogin().getNumberOfMatches() + 1);
            if (getCharacterHp() < getOpponentHp()) {
                GameState.getInstance().getLogin().setLosses(GameState.getInstance().getLogin().getLosses() + 1);
                GameState.getInstance().getLogin().setConsecutiveWins(0);
            } else {
                GameState.getInstance().getLogin().setWins(GameState.getInstance().getLogin().getWins() + 1);
                GameState.getInstance().getLogin().setConsecutiveWins(GameState.getInstance().getLogin().getConsecutiveWins() + 1);
            }
        }
    }

    /**
     * Cancel the game mid fight
     */
    public void terminateGameplay() {
        gameOver = true;
        RenderCharacterSelection.getInstance().newInstance();
        RenderStageSelect.getInstance().newInstance();
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
        io.github.subiyacryolite.enginev1.Overlay.getInstance().secondaryNotice(RenderStageSelect.getInstance().getAmbientMusicMetaData()[RenderStageSelect.getInstance().getAmbientMusicIndex()]);
    }

    public void playMusicNow() {
        try {

            io.github.subiyacryolite.enginev1.Overlay.getInstance().primaryNotice(Characters.getInstance().getOpponent().getBraggingRights(RenderCharacterSelection.getInstance().getSelectedCharIndex()));
        } catch (Exception e) {
            System.out.println("Dude, something went wrong " + e.getMessage());
        }
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
}
