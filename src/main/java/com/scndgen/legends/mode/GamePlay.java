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
import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.controller.StoryMode;
import com.scndgen.legends.enums.*;
import com.scndgen.legends.network.NetworkClient;
import com.scndgen.legends.network.NetworkServer;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGamePlay;
import com.scndgen.legends.render.RenderStageSelect;
import com.scndgen.legends.state.GameState;
import com.scndgen.legends.threads.ClashSystem;
import com.scndgen.legends.threads.ClashingOpponent;
import com.scndgen.legends.windows.JenesisPanel;
import io.github.subiyacryolite.enginev1.AudioPlayback;
import io.github.subiyacryolite.enginev1.Mode;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class draws and manipulates all sprites, images and effects used in the game
 *
 * @Author: Ifunga Ndana
 * @Class: GamePlay
 */
public abstract class GamePlay extends Mode {
    protected final String[] physical = new String[]{"", "", "", ""}, celestia = new String[]{"", "", "", ""}, item = new String[]{"", "", "", ""};
    protected final AudioPlayback furySound, damageSound;
    protected String activePerson; // person who performed an attack, getName shall show in battle info status area
    protected float characterHpAsPercent = 100, opponentHpAsPercent = 100;
    protected boolean triggerCharacterAttack;
    protected boolean triggerOpponentAttack;
    protected int done = 0; // if gameover
    protected LinkedList<String> characterAttacks = new LinkedList<>();
    protected LinkedList<Integer> opponentAttacks = new LinkedList<>();
    protected boolean threadsNotRunningYet = true, playATBFile = false;
    protected StringBuilder StatusText = new StringBuilder();
    protected int startDrawing = 0, menuBarY;
    protected Color currentColor = Color.RED;
    protected float angRot = 20;
    protected boolean safeToSelect;
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
    protected boolean clasherRunning = false;
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
    protected int shakingChar = 0033, lifeBarShakeIterations = 3, lifeBarShakeInnerIterations = 4;
    protected int x2 = 560, comX = 380, comY = 100;
    protected int xLocal = 470;
    protected int y2 = 435;
    protected int statIndexOpp, statIndexChar, statusEffectCharacterYCoord, statusEffectOpponentYCoord, uiShakeEffectOffsetCharacter = 1, uiShakeEffectOffsetOpponent = 1, basicY = 0;
    protected boolean shaky1 = true;
    protected ClashingOpponent oppAttack = null;
    protected int animTime = 400, itemX = 0, itemY = 0;
    protected boolean runNew = true, effectChar = false;
    protected int bgX = 0;
    protected int numberOfStoryPix, lbx2 = 500;
    protected int lby2 = 420;
    protected String characterAttackType = "normal", opponentAttackType = "normal", statusChar = "", statusOpp = "";
    protected int charMeleeSpriteStatus = 9, oppMeleeSpriteStatus = 9, charCelestiaSpriteStatus = 11, oppCelestiaSpriteStatus = 11;
    protected float statusEffectCharacterOpacity, statusEffectOpponentOpacity;
    protected int furyBarY = 0;
    protected int[] fontSizes = {LoginScreen.bigTxtSize, LoginScreen.normalTxtSize, LoginScreen.normalTxtSize, LoginScreen.normalTxtSize};
    protected int attackInt = 0;
    protected String attackStr;
    protected boolean lagFactor = true;
    protected float currentXShear = 0, currentYShear = 0;
    protected boolean isFree = true, isFree2 = true;
    protected CharacterState characterState;
    protected String sysNot = "";
    protected float sysNotOpac = 0, sysNotOpacInc = (float) 0.1;
    protected String achievementName = "", achievementDescription = "", achievementClass = "", achievementPoints = "";
    protected int activeAttack = 0;
    protected NetworkServer server;
    protected NetworkClient client;
    protected int InfoBarYPose, spacer = 27, randSoundIntChar, randSoundIntOpp, randSoundIntOppHurt, randSoundIntCharHurt, YOffset = 15;
    protected int x = 2;
    protected int oppBarYOffset, attackMenuXPos, attackMenuTextXPos, attackMenuTextYPos, y = 0;
    protected float opacityTxt = 10, opacityPic = 0.0f;
    protected boolean limitRunning = true, animCharFree = true;
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
    protected AudioPlayback sound, ambientMusic, hurtChar, hurtOpp, attackChar, attackOpp;
    protected boolean isCharacterAttacking;
    protected int columnIndex;
    protected int rowIndex;
    private int currentCharacterQueLoop, currentOpponentQueLoop;
    private int lastCharacterQueLoop, lastOpponentQueLoop;
    private int characterUiLoop, opponentUiLoop;
    private long characterQueDelta, opponentQueDelta;
    private long opponentAiTimeout, opponentAiDelta;


    protected GamePlay() {
        furySound = new AudioPlayback(AudioConstants.furyAttck(), AudioType.SOUND, false);
        damageSound = new AudioPlayback(AudioConstants.playerAttack(), AudioType.SOUND, false);
    }

    /**
     * Set stat index
     *
     * @param dex
     */
    public void setStatIndex(int dex) {
        statIndex = dex;
    }

    public void setStatusPic(CharacterState who) {

        if (who == CharacterState.CHARACTER) {
            statusEffectCharacterOpacity = 1.0f;
            statusEffectCharacterYCoord = 0;
            statIndexChar = statIndex;
        }
        if (who == CharacterState.OPPONENT) {
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
    @SuppressWarnings("SleepWhileHoldingLock")
    public void drawAchievements() {
        try {
            int howMany = Achievement.getInstance().getAcievementsTriggered();
            do {
                for (int u = 0; u < howMany; u++) {
                    String[] achievementInfo = Achievement.getInstance().getName(u);
                    achievementName = achievementInfo[0]; //getName
                    achievementDescription = achievementInfo[1]; //desc
                    achievementClass = achievementInfo[2]; //class
                    achievementPoints = achievementInfo[3]; //points
                    System.out.println("Triggered " + achievementName + "\n" + achievementDescription + "\n" + achievementClass + "\n" + achievementPoints);

                    try {
                        Thread.sleep(2300);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } while (isRunning);
        } catch (Exception e) {
            //nothin
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
     * @param physicalS
     * @param celestiaS
     * @param itemS
     */
    public void setCharacterAttackArrays(String[] physicalS, String[] celestiaS, String[] itemS) {
        System.arraycopy(physicalS, 0, physical, 0, physicalS.length);
        System.arraycopy(celestiaS, 0, celestia, 0, celestiaS.length);
        System.arraycopy(itemS, 0, item, 0, itemS.length);
    }

    /**
     * Generates strings used to execute moves
     *
     * @param input move
     */
    public String genStr(int input) {
        String thisTxt = "";
        if (input < 10) {
            thisTxt = "0" + activeAttack;
        } else {
            thisTxt = "" + activeAttack;
        }
        return thisTxt;
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
    public String getAttackType(CharacterState who) {
        String result = "";
        if (who == CharacterState.CHARACTER) {
            result = characterAttackType;
        } else if (who == CharacterState.OPPONENT) {
            result = opponentAttackType;
        }

        return result;
    }

    /**
     * set hurtChar type, normal or fury
     */
    public void setAttackType(String type, CharacterState who) {
        if (who == CharacterState.CHARACTER) {
            characterAttackType = type;
        }
        if (who == CharacterState.OPPONENT) {
            opponentAttackType = type;
        }
    }

    public void clash(int dude, CharacterState homie) {
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
            JenesisPanel.getInstance().sendToServer("clashing^T&T^&T&^");
        } else if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
            JenesisPanel.getInstance().sendToClient("clashing^T&T^&T&^");
        }
        if (getBreak() == 1000 && safeToSelect && clasherRunning == false) {
            new ClashSystem(dude, homie);
            clasherRunning = true;
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER || ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE) {
                oppAttack = new ClashingOpponent();
                System.out.println("clash ai");
            }
        }
    }

    public void opponetClashing() {
        ClashSystem.getInstance().oppClashing();
    }

    public void sendToClient(String message) {
        JenesisPanel.getInstance().sendToClient(message);
        //protected JenesisPanel.getInstance().NetworkClient client;
    }

    public void sendToServer(String message) {
        JenesisPanel.getInstance().sendToServer(message);
    }

    public String getFavChar(int here) {
        return charNames[here].name();
    }

    public boolean shakeCharacterLifeBar(int loop) {
        int computedPosition = -1;//so that we start from zero
        // for (int iteration = 0; iteration < lifeBarShakeIterations; iteration++) // shakes opponents LifeBar in a cool way as isWithinRange as Black n White flashy Anime effect
        {
            for (int i = 0; i < lifeBarShakeInnerIterations; i++) {
                computedPosition++;
                if (loop == computedPosition) {
                    uiShakeEffectOffsetCharacter = uiShakeEffectOffsetCharacter + 1;
                    return true;
                }
            }
            for (int i = 0; i < lifeBarShakeInnerIterations; i++) {
                computedPosition++;
                if (loop == computedPosition) {
                    uiShakeEffectOffsetCharacter = uiShakeEffectOffsetCharacter - 1;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean shakeOpponentLifeBar(int loop) {
        int computedPosition = -1;//so that we start from zero
        for (int h = 0; h < lifeBarShakeIterations; h++) // shakes opponents LifeBar in a cool way as isWithinRange as Black n White flashy Anime effect
        {
            for (int i = 0; i < lifeBarShakeInnerIterations; i++) {
                computedPosition++;
                if (loop == computedPosition) {
                    uiShakeEffectOffsetOpponent = uiShakeEffectOffsetOpponent + 1;
                    return true;
                }
            }
            for (int i = 0; i < lifeBarShakeInnerIterations; i++) {
                computedPosition++;
                if (loop == computedPosition) {
                    uiShakeEffectOffsetOpponent = uiShakeEffectOffsetOpponent - 1;
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
    public void setSprites(CharacterState characterState, int oneA, int Magic) {
        if (characterState == CharacterState.CHARACTER) {
            charMeleeSpriteStatus = oneA;
            charCelestiaSpriteStatus = Magic;
        }
        if (characterState == CharacterState.OPPONENT) {
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
                setSprites(CharacterState.CHARACTER, 9, 11);
                setSprites(CharacterState.OPPONENT, 9, 11);
                //broadcast hurtChar on net
                attackStr = "" + characterAttacks.get(0) + characterAttacks.get(1) + characterAttacks.get(2) + characterAttacks.get(3) + " attack";
                System.out.println(attackStr);
                client.sendData(attackStr);
                //attack on local
                disableSelection();
                prepareCharacterAttack();
            } else if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                //clear active combos
                setSprites(CharacterState.CHARACTER, 9, 11);
                setSprites(CharacterState.OPPONENT, 9, 11);
                //broadcast hurtChar on net
                attackStr = "" + characterAttacks.get(0) + characterAttacks.get(1) + characterAttacks.get(2) + characterAttacks.get(3) + " attack";
                System.out.println(attackStr);
                server.sendData(attackStr);
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
    public int timeLimit, count2;
    public boolean storySequence;
    private boolean characterAtb = true, opponentAtb = true;
    public boolean isRunning = false;
    public String timeStr;//, scene;
    public int time1 = 10, time2 = 10, time3 = 10;
    public AudioPlayback loseMusic, winMusic;
    private boolean newMatch;
    private float characterAtbValue;
    private int limitChar;
    private float opponentAtbValue;
    private int limitOpp;
    private float secondCount = 0.0f;
    private int speedFactor = 30; //equal to the fps division
    private int matchDuration, playTimeCounter;

    public void update(long delta) {
        super.update(delta);
        if (gameOver == false) {
            boolean singlePlayerOrStoryMode = ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER || ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE;
            if (singlePlayerOrStoryMode && !triggerOpponentAttack) {
                if (singlePlayerOrStoryMode && isOpponentAtbFull() && (delta - opponentAiDelta) > MS16) {
                    opponentAiDelta = delta;
                    if (opponentAiTimeout < GameState.getInstance().getLogin().getDifficultyDynamic()) {
                        opponentAiTimeout += 16;
                    } else {
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
            if (triggerOpponentAttack) {
                if (lastOpponentQueLoop == -1) {
                    pauseOpponentAtb();
                    setOpponentAtbValue(0);
                }
                if (lastOpponentQueLoop != currentOpponentQueLoop && !opponentAttacks.isEmpty()) {
                    opponentQueDelta = delta;
                    lastOpponentQueLoop = currentOpponentQueLoop;
                    setAttackSpritesAndTrigger(opponentAttacks.pop(), CharacterState.OPPONENT, CharacterState.CHARACTER, this, Characters.getInstance().getCharacter());//add mode reference here
                }
                if ((delta - opponentQueDelta) > MS33) {
                    opponentQueDelta = delta;
                    if (shakeCharacterLifeBar(opponentUiLoop)) {
                        opponentUiLoop++;
                    } else {
                        currentOpponentQueLoop++;
                        opponentUiLoop = 0;
                        if (opponentAttacks.isEmpty()) {
                            revertToDefaultSprites(CharacterState.OPPONENT);
                            resumeOpponentAtb();
                            triggerOpponentAttack = false;
                        }
                    }
                }
            }
            if (triggerCharacterAttack) {
                if (lastCharacterQueLoop == -1) {
                    pauseCharacterAtb();
                    setCharacterAtbValue(0);
                }
                if (lastCharacterQueLoop != currentCharacterQueLoop && !characterAttacks.isEmpty()) {
                    characterQueDelta = delta;
                    lastCharacterQueLoop = currentCharacterQueLoop;
                    setAttackSpritesAndTrigger(Integer.parseInt(characterAttacks.pop()), CharacterState.CHARACTER, CharacterState.OPPONENT, this, Characters.getInstance().getOpponent());//add mode reference here
                }
                if ((delta - characterQueDelta) > MS33) {
                    characterQueDelta = delta;
                    if (shakeOpponentLifeBar(characterUiLoop)) {
                        characterUiLoop++;
                    } else {
                        currentCharacterQueLoop++;
                        characterUiLoop = 0;
                        if (characterAttacks.isEmpty()) {
                            revertToDefaultSprites(CharacterState.CHARACTER);
                            resumeCharacterAtb();
                            triggerCharacterAttack = false;
                        }
                    }
                }
            }
            if ((delta - timerDelta) > MS33) {
                timerDelta = delta;
                /////////////////////
                updateMatchStatus();
                Achievement.getInstance().scan(this);
                if (characterAtbValue <= maxAtb && characterAtb) {
                    characterAtbValue += Characters.getInstance().getCharRecoverySpeed();
                }
                if (opponentAtbValue <= maxAtb && opponentAtb && ScndGenLegends.getInstance().getSubMode() != SubMode.STORY_MODE) {
                    opponentAtbValue += Characters.getInstance().getOppRecoverySpeed();
                }
                if (timeLimit <= 180 && storySequence == false) {
                    if (secondCount < 1000) //continue till we make a second
                    {
                        secondCount += speedFactor;
                    } else {
                        try {
                            if (timeLimit < 999 && timeLimit > 0) {
                                timeLimit = timeLimit - 1;
                                timeStr = "" + timeLimit + "";
                                secondCount = 0.0f;
                                if (timeStr.length() == 1) {
                                    time1 = 10;
                                    time2 = 10;
                                    time3 = Integer.parseInt("" + timeStr.charAt(0));
                                }
                                if (timeStr.length() == 2) {
                                    time1 = 10;
                                    time2 = Integer.parseInt("" + timeStr.charAt(0));
                                    time3 = Integer.parseInt("" + timeStr.charAt(1));
                                }
                                if (timeStr.length() == 3) {
                                    time1 = Integer.parseInt("" + timeStr.charAt(0));
                                    time2 = Integer.parseInt("" + timeStr.charAt(1));
                                    time3 = Integer.parseInt("" + timeStr.charAt(2));
                                }
                            }
                        } catch (Exception nfe) {
                            nfe.printStackTrace(System.err);
                        }
                    }
                }
            }
        }
    }

    public void newInstance() {
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
        /////////////////////////
        /////////////////////////
        /////////////////////////
        Achievement.getInstance().newInstance();
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE) {
            storySequence = true;
            timeLimit = StoryMode.getInstance().time;
        } //if LAN, client uses hosts timeLimit preset
        else if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
            timeLimit = JenesisPanel.getInstance().hostTime;
        } else {
            timeLimit = GameState.getInstance().getLogin().getTimeLimit();
        }
        recordPlayTime();
        characterAtbValue = 00;
        opponentAtbValue = 00;
        maxAtb = 290;
        gameOver = false;
        newMatch = true;
        winMusic = new AudioPlayback(AudioConstants.winSound(), AudioType.MUSIC, false);
        loseMusic = new AudioPlayback(AudioConstants.loseSound(), AudioType.MUSIC, false);
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE == false) {
            playBGMusic();
            musNotice();
            io.github.subiyacryolite.enginev1.Overlay.getInstance().primaryNotice(Characters.getInstance().getOpponent().getBraggingRights(RenderCharacterSelectionScreen.getInstance().getSelectedCharIndex()));
        }
        count2 = 0;
    }

    protected abstract void playBGMusic();

    /**
     * Gets the damage multiplier
     *
     * @param who - which characterEnum
     * @return damage multiplier
     */
    public int getDamageDealt(CharacterState who) {
        if (who == CharacterState.CHARACTER) {
            thisInt = damageC;
        }
        if (who == CharacterState.OPPONENT) {
            thisInt = damageO;
        }
        return thisInt;
    }

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
        if (gameOver == false) {
            if (opponentHp < 0 || characterHp < 0 || (timeLimit <= 0 && GameState.getInstance().getLogin().isTimeLimited())) {
                if (opponentHp / opponentMaximumHp > characterHp / characterMaximumHp || opponentHp / opponentMaximumHp < characterHp / characterMaximumHp) {
                    gameOver();
                }
            }
            //save characterHp at gameover
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
        Characters.getInstance().getCharacter().setDamageMultiplier(Characters.getInstance().getDamageMultiplier(CharacterState.CHARACTER));
        Characters.getInstance().getOpponent().setDamageMultiplier(Characters.getInstance().getDamageMultiplier(CharacterState.OPPONENT));
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
            int moi = Integer.parseInt(characterAttacks.removeLast());
            Characters.getInstance().alterPoints2(moi);
            System.out.println("UNQUEUED " + moi);
        }
    }

    /**
     * logic CharacterState 1 characterHp
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
    public void alterDamageCounter(CharacterState per, int thisMuch) {
        if (per == CharacterState.CHARACTER && Characters.getInstance().getOpponent().getDamageMultiplier() > 0 && Characters.getInstance().getOpponent().getDamageMultiplier() < 20) {
            Characters.getInstance().getOpponent().setDamageMultiplier(Characters.getInstance().getOpponent().getDamageMultiplier() + thisMuch);
        }

        if (per == CharacterState.OPPONENT && Characters.getInstance().getCharacter().getDamageMultiplier() > 0 && Characters.getInstance().getCharacter().getDamageMultiplier() < 20) {
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

    public void triggerFury(CharacterState who) {
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
    public void limitBreak(CharacterState who) {
        characterState = who;
        new Thread() {

            @Override
            public void run() {
                if (getBreak() == 1000) {
                    //&& getCharacterAtbValue()>289
                    //runs on local
                    if (characterState == CharacterState.CHARACTER && limitRunning && getCharacterAtbValue() > 289) {
                        limitRunning = false;
                        if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
                            JenesisPanel.getInstance().sendToServer("limt_Break_Oxodia_Ownz");
                        } else if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                            JenesisPanel.getInstance().sendToClient("limt_Break_Oxodia_Ownz");
                        }
                        setAttackType("fury", CharacterState.CHARACTER);
                        pauseCharacterAtb();
                        setCharacterAtbValue(0);
                        for (int i = 1; i < 9; i++) {
                            //stop attacking when game over
                            if (gameOver == false) {
                                furySound();
                                hurtSoundOpp();
                                setSprites(CharacterState.CHARACTER, i, 11);
                                setSprites(CharacterState.OPPONENT, 0, 11);
                                shakeOpponentLifeBar(0);
                                comboPicArrayPosOpp = i;
                                furyComboOpacity = 1.0f;
                                lifePhysUpdateSimple(CharacterState.OPPONENT, 100, "");
                            }
                        }
                        comboPicArrayPosOpp = 8;
                        resumeCharacterAtb();
                        setSprites(CharacterState.CHARACTER, 9, 11);
                        setSprites(CharacterState.OPPONENT, 9, 11);
                        limitRunning = true;
                        resetBreak();
                        setAttackType("normal", CharacterState.CHARACTER);
                    } else if (characterState == CharacterState.OPPONENT && limitRunning && getOpponentAtbValue() > 289) {
                        setAttackType("fury", CharacterState.OPPONENT);
                        limitRunning = false;
                        for (int i = 1; i < 9; i++) {
                            if (gameOver == false) {
                                isCharacterAttacking = true;
                                furySound();
                                hurtSoundChar();
                                setOpponentAtbValue(0);
                                setSprites(CharacterState.OPPONENT, i, 11);
                                setSprites(CharacterState.CHARACTER, 0, 11);
                                shakeCharacterLifeBar(0);
                                comboPicArrayPosOpp = i;
                                lifePhysUpdateSimple(CharacterState.CHARACTER, 100, "");
                            }
                        }
                        isCharacterAttacking = false;
                        comboPicArrayPosOpp = 8;
                        setSprites(CharacterState.OPPONENT, 9, 11);
                        setSprites(CharacterState.CHARACTER, 9, 11);
                        limitRunning = true;
                        resetBreak();
                        setAttackType("normal", CharacterState.OPPONENT);
                    }
                }
            }
        }.start();
    }

    /**
     * Updates the characterHp of CharacterEnum
     *
     * @param forWho     - the person affected
     * @param damageDone - the characterHp to add/subtract
     * @param attacker   - who inflicted damage
     */
    public void lifePhysUpdateSimple(CharacterState forWho, int damageDone, String attacker) {

        if (forWho == CharacterState.CHARACTER) //Attack from player
        {
            damageDoneToCharacter = damageDone;
            activePerson = attacker;
            incLImit(damageDoneToCharacter);
            guiScreenChaos(damageDone * getDamageMultiplierOpp(), CharacterState.OPPONENT);
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

        if (forWho == CharacterState.OPPONENT || forWho == CharacterState.BOSS) //Attack from CPU pponent 1
        {
            damageDoneToOpponent = damageDone;
            activePerson = attacker;
            incLImit(damageDoneToOpponent);
            guiScreenChaos(damageDone * getDamageMultiplierChar(), CharacterState.CHARACTER);
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

    protected abstract void guiScreenChaos(float damageAmount, CharacterState who);

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

    public boolean getClasherRunning() {
        return clasherRunning;
    }

    public void setClasherRunning(boolean value) {
        clasherRunning = value;
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
     * @param writeThis - what to display
     */
    public void showBattleMessage(String writeThis) {
        storyText(writeThis);
    }

    /**
     * Flashy text at bottom of screen
     *
     * @param thisMessage
     */
    public void storyText(String thisMessage) {
        opacityTxt = 0.0f;
        battleInformation = new StringBuilder(thisMessage);
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY:
                onAccept();
                break;
            case MIDDLE:
                triggerFury(CharacterState.CHARACTER);
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
            RenderCharacterSelectionScreen.getInstance().primaryNotice("Canceled Match");
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
    public void revertToDefaultSprites(CharacterState thischar) {
        if (thischar == CharacterState.CHARACTER || thischar == CharacterState.OPPONENT) {
            //sprites back to normal poses
            setSprites(CharacterState.CHARACTER, 9, 11);
            setSprites(CharacterState.OPPONENT, 9, 11);
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
                triggerFury(CharacterState.OPPONENT);
                array = new int[]{};
            } else {
                array = Characters.getInstance().getOpponent().getAiProfile3();
            }
        } //when doing isWithinRange, 4 buffs + 2 moves
        else if (getOpponentHp() / getOpponentMaximumHp() >= 0.25 && getOpponentHp() / getOpponentMaximumHp() < 0.50) {
            if (getBreak() == 1000 && limitRunning) {
                triggerFury(CharacterState.OPPONENT);
                array = new int[]{};
            } else {
                array = Characters.getInstance().getOpponent().getAiProfile4();
            }
        } //first fury, when doing isWithinRange, 4 buffs + 2 moves
        else {
            if (getBreak() == 1000 && limitRunning) {
                triggerFury(CharacterState.OPPONENT);
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
    public void setAttackSpritesAndTrigger(int attack, CharacterState source, CharacterState destination, GamePlay gamePlay, Character defaultOpponent) {
        String attackIdentifier = "";
        if (attack > 8) {
            destination = CharacterState.SELF;
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
    public void setAttackSprites(GamePlay gamePlay, CharacterState source, CharacterState destination, int attack) {
        gamePlay.isCharacterAttacking(source == CharacterState.CHARACTER);
        if (destination == CharacterState.SELF) {
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
    public void triggerAttack(GamePlay gamePlay, String attackIdentifier, Character opponent, CharacterState destination) {
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
        //if not story scene, increment char usage
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE == false) {
            GameState.getInstance().getLogin().setCharacterUsage(RenderCharacterSelectionScreen.getInstance().getCharName());
        }
        if (hasWon()) {
            showWinLabel();
            winMusic.play();
        } else {
            showLoseLabel();
            loseMusic.play();
        }
        RenderStageSelect.getInstance().newInstance();
        RenderCharacterSelectionScreen.getInstance().newInstance();
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
    public void closingThread(boolean toCharacterSelect) {
        //logic profile operations
        incrementWinsOrLosses();
        GameState.getInstance().getLogin().setPoints(Achievement.getInstance().getNewUserPoints());
        //save profile
        GameState.getInstance().saveConfigFile();
        io.github.subiyacryolite.enginev1.Overlay.getInstance().primaryNotice("Saved File");
        if (toCharacterSelect) {
            ScndGenLegends.getInstance().loadMode(ModeEnum.MAIN_MENU);
        } else {
            ScndGenLegends.getInstance().loadMode(ModeEnum.CHAR_SELECT_SCREEN);
        }
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
        RenderCharacterSelectionScreen.getInstance().newInstance();
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
            playBGMusic();
            io.github.subiyacryolite.enginev1.Overlay.getInstance().primaryNotice(Characters.getInstance().getOpponent().getBraggingRights(RenderCharacterSelectionScreen.getInstance().getSelectedCharIndex()));
        } catch (Exception e) {
            System.out.println("Dude, somin went wrong" + e.getMessage());
        }
    }

    public boolean isGameOver()
    {
        return gameOver;
    }
}
