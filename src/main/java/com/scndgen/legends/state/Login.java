/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="https://www.scndgen.com">https://www.scndgen.com</a>]))).

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
 along with The SCND Genesis: Legends. If not, see <<a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>>.

 **************************************************************************/
package com.scndgen.legends.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scndgen.legends.Language;
import com.scndgen.legends.enums.Achievements;
import com.scndgen.legends.enums.CharacterEnum;

import java.util.UUID;

import static com.scndgen.legends.constants.GeneralConstants.INFINITE_TIME;

/**
 * Created by ifunga on 22/04/2017.
 */
public class Login {
    private final int diff0 = 0,
            diff1 = 1000,
            diff2 = 2500,
            diff3 = 3500,
            diff4 = 4500,
            diff5 = 6000;
    private final int[] difficultyArray = {diff0, diff1, diff2, diff3, diff4, diff5};
    //
    private String id;
    private String userName = "";
    private int points = 0;
    private int playTime = 0;
    private int numberOfMatches = 0;
    private int ach0 = 0;
    private int ach1 = 0;
    private int ach2 = 0;
    private int ach3 = 0;
    private int ach4 = 0;
    private int ach5 = 0;
    private int ach6 = 0;
    private int ach7 = 0;
    private int ach8 = 0;
    private int ach9 = 0;
    private int ach10 = 0;
    private int ach11 = 0;
    private int wins = 0;
    private int losses = 0;
    private int frames = 0;
    @JsonProperty("audioOn")
    private boolean audioOn = true;
    private int difficulty = diff3;
    private int difficultyDynamic = diff3;
    private int highestStoryScene = 0;
    private int timeLimit = 90;
    private String graphicsSetting = "";
    private int letterboxR = 0;
    private int letterboxG = 0;
    private int letterboxB = 0;
    private int char0 = 0;
    private int char1 = 0;
    private int char2 = 0;
    private int char3 = 0;
    private int char4 = 0;
    private int char5 = 0;
    private int char6 = 0;
    private int char7 = 0;
    private int char8 = 0;
    private int char9 = 0;
    private int char10 = 0;
    private int char11 = 0;
    private int comicEffectOccurence = 0;
    private int gameRating = 0;
    private boolean usingController = true;
    private int currentLanguage = 0;
    private int consecutiveWins = 0;
    private int musicVolume = 100;
    private int voiceVolume = 100;
    private int soundVolume = 100;
    private String txtSpeed = "Normal";

    public Login() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
    }

    public Login(String userName) {
        this();
        setUserName(userName);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Compatibility alias for callers that previously used JdsEntity.getEntityGuid().
     */
    public String getEntityGuid() {
        return getId();
    }

    public void incrementAchievement(Achievements achievement) {
        switch (achievement) {
            case UPPER_HAND:
                setAch0(getAch0() + 1);
                break;
            case BEAT_THE_ODDS:
                setAch1(getAch1() + 1);
                break;
            case OWNAGE:
                setAch2(getAch2() + 1);
                break;
            case HEARTLESS:
                setAch3(getAch3() + 1);
                break;
            case MEANIE:
                setAch4(getAch4() + 1);
                break;
            case RAGE:
                setAch5(getAch5() + 1);
                break;
            case WINNER:
                setAch6(getAch6() + 1);
                break;
            case BUZZ_KILL:
                setAch7(getAch7() + 1);
                break;
            case CLOSE_CALL:
                setAch8(getAch8() + 1);
                break;
            case ON_A_ROLL:
                setAch9(getAch9() + 1);
                break;
            case HALF_WAY_THROUGH:
                setAch10(getAch10() + 1);
                break;
            case Ach12:
                setAch11(getAch11() + 1);
                break;
        }
    }

    public int getAchievementTriggers(Achievements achievement) {
        switch (achievement) {
            case UPPER_HAND:
                return getAch0();
            case BEAT_THE_ODDS:
                return getAch1();
            case OWNAGE:
                return getAch2();
            case HEARTLESS:
                return getAch3();
            case MEANIE:
                return getAch4();
            case RAGE:
                return getAch5();
            case WINNER:
                return getAch6();
            case BUZZ_KILL:
                return getAch7();
            case CLOSE_CALL:
                return getAch8();
            case ON_A_ROLL:
                return getAch9();
            case HALF_WAY_THROUGH:
                return getAch10();
            case Ach12:
                return getAch11();
        }
        return 0;
    }

    public int getNumberOfTimesAchivementTriggered() {
        int count = 0;
        for (Achievements achievement : Achievements.values()) {
            count += getAchievementTriggers(achievement);
        }
        return count;
    }

    public int getUnlockedAch() {
        int counter = 0;
        for (Achievements achievement : Achievements.values()) {
            if (getAchievementTriggers(achievement) > 0) {
                counter = counter + 1;
            }
        }
        return counter;
    }

    public int getCharacterUsage(CharacterEnum characterEnum) {
        switch (characterEnum) {
            case SUBIYA:
                return getChar0();
            case RAILA:
                return getChar1();
            case LYNX:
                return getChar2();
            case AISHA:
                return getChar3();
            case ADE:
                return getChar4();
            case RAVAGE:
                return getChar5();
            case JONAH:
                return getChar6();
            case ADAM:
                return getChar7();
            case NOVA_ADAM:
                return getChar8();
            case AZARIA:
                return getChar9();
            case SORROWE:
                return getChar10();
            case THING:
                return getChar11();
            default:
                return 0;
        }
    }

    public void setCharacterUsage(CharacterEnum characterEnum) {
        switch (characterEnum) {
            case SUBIYA:
                setChar0(getChar0() + 1);
                break;
            case RAILA:
                setChar1(getChar1() + 1);
                break;
            case LYNX:
                setChar2(getChar2() + 1);
                break;
            case AISHA:
                setChar3(getChar3() + 1);
                break;
            case ADE:
                setChar4(getChar4() + 1);
                break;
            case RAVAGE:
                setChar5(getChar5() + 1);
                break;
            case JONAH:
                setChar6(getChar6() + 1);
                break;
            case ADAM:
                setChar7(getChar7() + 1);
                break;
            case NOVA_ADAM:
                setChar8(getChar8() + 1);
                break;
            case AZARIA:
                setChar9(getChar9() + 1);
                break;
            case SORROWE:
                setChar10(getChar10() + 1);
                break;
            case THING:
                setChar11(getChar11() + 1);
                break;
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(final int points) {
        this.points = points;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(final int playTime) {
        this.playTime = playTime;
    }

    public int getNumberOfMatches() {
        return numberOfMatches;
    }

    public void setNumberOfMatches(final int numberOfMatches) {
        this.numberOfMatches = numberOfMatches;
    }

    public int getAch0() {
        return ach0;
    }

    public void setAch0(final int ach0) {
        this.ach0 = ach0;
    }

    public int getAch1() {
        return ach1;
    }

    public void setAch1(final int ach1) {
        this.ach1 = ach1;
    }

    public int getAch2() {
        return ach2;
    }

    public void setAch2(final int ach2) {
        this.ach2 = ach2;
    }

    public int getAch3() {
        return ach3;
    }

    public void setAch3(final int ach3) {
        this.ach3 = ach3;
    }

    public int getAch4() {
        return ach4;
    }

    public void setAch4(final int ach4) {
        this.ach4 = ach4;
    }

    public int getAch5() {
        return ach5;
    }

    public void setAch5(final int ach5) {
        this.ach5 = ach5;
    }

    public int getAch6() {
        return ach6;
    }

    public void setAch6(final int ach6) {
        this.ach6 = ach6;
    }

    public int getAch7() {
        return ach7;
    }

    public void setAch7(final int ach7) {
        this.ach7 = ach7;
    }

    public int getAch8() {
        return ach8;
    }

    public void setAch8(final int ach8) {
        this.ach8 = ach8;
    }

    public int getAch9() {
        return ach9;
    }

    public void setAch9(final int ach9) {
        this.ach9 = ach9;
    }

    public int getAch10() {
        return ach10;
    }

    public void setAch10(final int ach10) {
        this.ach10 = ach10;
    }

    public int getAch11() {
        return ach11;
    }

    public void setAch11(final int ach11) {
        this.ach11 = ach11;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(final int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(final int losses) {
        this.losses = losses;
    }

    public int getFrames() {
        return frames;
    }

    public void setFrames(int frames) {
        this.frames = frames;
    }

    public boolean isAudioOn() {
        return audioOn;
    }

    public void setAudioOn(final boolean audioOn) {
        this.audioOn = audioOn;
    }

    /** @deprecated use {@link #setAudioOn(boolean)} */
    @JsonIgnore
    public void setIsAudioOn(final boolean audioOn) {
        this.audioOn = audioOn;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(final int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficultyDynamic() {
        return difficultyDynamic;
    }

    public void setDifficultyDynamic(final int difficultyDynamic) {
        this.difficultyDynamic = difficultyDynamic;
    }

    public int getHighestStoryScene() {
        return highestStoryScene;
    }

    public void setHighestStoryScene(final int lastStoryScene) {
        this.highestStoryScene = lastStoryScene;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getTimeLimitString() {
        switch (getTimeLimit()) {
            case INFINITE_TIME:
                return Language.get().get(424);
            case 180:
                return "180";
            case 150:
                return "150";
            case 120:
                return "120";
            case 90:
                return "90";
            case 60:
                return "60";
            case 45:
                return "45";
            case 30:
                return "30";
        }
        return "90";
    }

    public String getGraphicsSetting() {
        return graphicsSetting;
    }

    public void setGraphicsSetting(final String graphicsSetting) {
        this.graphicsSetting = graphicsSetting;
    }

    public int getLetterboxR() {
        return letterboxR;
    }

    public void setLetterboxR(int letterboxR) {
        this.letterboxR = clampByte(letterboxR);
    }

    public int getLetterboxG() {
        return letterboxG;
    }

    public void setLetterboxG(int letterboxG) {
        this.letterboxG = clampByte(letterboxG);
    }

    public int getLetterboxB() {
        return letterboxB;
    }

    public void setLetterboxB(int letterboxB) {
        this.letterboxB = clampByte(letterboxB);
    }

    private static int clampByte(int value) {
        return Math.max(0, Math.min(255, value));
    }

    public int getChar0() {
        return char0;
    }

    public void setChar0(final int char0) {
        this.char0 = char0;
    }

    public int getChar1() {
        return char1;
    }

    public void setChar1(final int char1) {
        this.char1 = char1;
    }

    public int getChar2() {
        return char2;
    }

    public void setChar2(final int char2) {
        this.char2 = char2;
    }

    public int getChar3() {
        return char3;
    }

    public void setChar3(final int char3) {
        this.char3 = char3;
    }

    public int getChar4() {
        return char4;
    }

    public void setChar4(final int char4) {
        this.char4 = char4;
    }

    public int getChar5() {
        return char5;
    }

    public void setChar5(final int char5) {
        this.char5 = char5;
    }

    public int getChar6() {
        return char6;
    }

    public void setChar6(final int char6) {
        this.char6 = char6;
    }

    public int getChar7() {
        return char7;
    }

    public void setChar7(final int char7) {
        this.char7 = char7;
    }

    public int getChar8() {
        return char8;
    }

    public void setChar8(final int char8) {
        this.char8 = char8;
    }

    public int getChar9() {
        return char9;
    }

    public void setChar9(final int char9) {
        this.char9 = char9;
    }

    public int getChar10() {
        return char10;
    }

    public void setChar10(final int char10) {
        this.char10 = char10;
    }

    public int getChar11() {
        return char11;
    }

    public void setChar11(final int char11) {
        this.char11 = char11;
    }

    public int getComicEffectOccurence() {
        return comicEffectOccurence;
    }

    public void setComicEffectOccurence(final int comicEffectOccurence) {
        this.comicEffectOccurence = comicEffectOccurence;
    }

    public int getGameRating() {
        return gameRating;
    }

    public void setGameRating(final int gameRating) {
        this.gameRating = gameRating;
    }

    public boolean getUsingController() {
        return usingController;
    }

    public void setUsingController(final boolean usingController) {
        this.usingController = usingController;
    }

    public int getCurrentLanguage() {
        return currentLanguage;
    }

    public void setCurrentLanguage(final int currentLanguage) {
        this.currentLanguage = currentLanguage;
    }

    public void setConsecutiveWins(int consecutiveWins) {
        this.consecutiveWins = consecutiveWins;
    }

    public int getConsecutiveWins() {
        return this.consecutiveWins;
    }

    /**
     * Sorts difficulty
     *
     * @return difficulty array index
     */
    public int resolveDifficultyInt() {
        var login = State.get().getLogin();
        if (login.getDifficulty() == diff0)
            return 0;
        if (login.getDifficulty() == diff1)
            return 1;
        if (login.getDifficulty() == diff2)
            return 2;
        if (login.getDifficulty() == diff3)
            return 3;
        if (login.getDifficulty() == diff4)
            return 4;
        if (login.getDifficulty() == diff5)
            return 5;
        return -1;
    }

    public String resolveDifficulty() {
        switch (resolveDifficultyInt()) {
            case 0:
                return Language.get().get(26);
            case 1:
                return Language.get().get(27);
            case 2:
                return Language.get().get(28);
            case 3:
                return Language.get().get(29);
            case 4:
                return Language.get().get(30);
            case 5:
                return Language.get().get(31);
        }
        return Language.get().get(26);
    }

    public void setTextSpeed(String dex) {
        txtSpeed = dex;
    }

    public String getTextSpeed() {
        return txtSpeed;
    }

    public int getTextSpeedInt() {
        switch (txtSpeed) {
            case "Insane":
                return 50;
            case "Fast":
                return 100;
            case "Normal":
                return 200;
            case "Slow":
                return 250;
        }
        return 200;
    }

    public int getDifficultyConstant(int dex) {
        return difficultyArray[dex];
    }

    public int mostPopularChar() {
        int highest = 0;
        for (CharacterEnum characterEnum : CharacterEnum.values()) {
            if (getCharacterUsage(characterEnum) > highest) {
                highest = getCharacterUsage(characterEnum);
            }
        }
        return highest;
    }

    public CharacterEnum mostPopularCharEnum() {
        int h = 0;
        CharacterEnum highest = CharacterEnum.ADAM;
        for (CharacterEnum characterEnum : CharacterEnum.values()) {
            if (getCharacterUsage(characterEnum) > h) {
                h = getCharacterUsage(characterEnum);
                highest = characterEnum;
            }
        }
        return highest;
    }

    public int mostPopularCharPercentage() {
        float ans;
        float count = 0;
        for (CharacterEnum characterEnum : CharacterEnum.values()) {
            count += getCharacterUsage(characterEnum);
        }
        ans = (mostPopularChar() / count) * 100;
        return Math.round(ans);
    }

    public int userAwesomeness() {
        int total = 0;
        int returnThis;
        try {
            for (Achievements achievement : Achievements.values())
                total += (getAchievementTriggers(achievement) * achievement.achievementCategory().points());
            System.out.println("Style points: " + total);
            returnThis = total / getUnlockedAch();
        } catch (Exception e) {
            System.out.println("new user, awesomeness is newbie");
            returnThis = 0;
        }
        return returnThis;
    }

    public boolean isTimeLimited() {
        return getTimeLimit() != INFINITE_TIME;
    }

    public int getMusicVolume() {
        return musicVolume;
    }

    public int getVoiceVolume() {
        return voiceVolume;
    }

    public int getSoundVolume() {
        return soundVolume;
    }

    public void setMusicVolume(int value) {
        musicVolume = value;
    }

    public void setVoiceVolume(int value) {
        voiceVolume = value;
    }

    public void setSoundVolume(int value) {
        soundVolume = value;
    }

    public String toString() {
        return getUserName();
    }
}
