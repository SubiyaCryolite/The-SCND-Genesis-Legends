package com.scndgen.legends;

import com.scndgen.legends.enums.CharacterEnum;
import io.github.subiyacryolite.jds.JdsEntity;
import io.github.subiyacryolite.jds.annotations.JdsEntityAnnotation;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by ifung on 22/04/2017.
 */
@JdsEntityAnnotation(entityName = "Game Saves", entityId = 2)
public class GameSave extends JdsEntity {
    private final SimpleStringProperty userName = new SimpleStringProperty("");
    private final SimpleStringProperty usrCode = new SimpleStringProperty("");
    private final SimpleIntegerProperty points = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty playTime = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty numberOfMatches = new SimpleIntegerProperty(0);
    private final SimpleStringProperty achievementName = new SimpleStringProperty("");
    private final SimpleStringProperty achievementDescription = new SimpleStringProperty("");
    private final SimpleStringProperty achievementClass = new SimpleStringProperty("");
    private final SimpleStringProperty achievementPoints = new SimpleStringProperty("");
    private final SimpleStringProperty ach0 = new SimpleStringProperty("");
    private final SimpleStringProperty ach1 = new SimpleStringProperty("");
    private final SimpleStringProperty ach2 = new SimpleStringProperty("");
    private final SimpleStringProperty ach3 = new SimpleStringProperty("");
    private final SimpleStringProperty ach4 = new SimpleStringProperty("");
    private final SimpleStringProperty ach5 = new SimpleStringProperty("");
    private final SimpleStringProperty ach6 = new SimpleStringProperty("");
    private final SimpleStringProperty ach7 = new SimpleStringProperty("");
    private final SimpleStringProperty ach8 = new SimpleStringProperty("");
    private final SimpleStringProperty ach9 = new SimpleStringProperty("");
    private final SimpleStringProperty ach10 = new SimpleStringProperty("");
    private final SimpleStringProperty ach11 = new SimpleStringProperty("");
    private final SimpleIntegerProperty wins = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty losses = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty frames = new SimpleIntegerProperty(0);
    private final SimpleBooleanProperty soundStatus = new SimpleBooleanProperty(true);
    private final SimpleIntegerProperty difficulty = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty lastStoryScene = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty timeLimit = new SimpleIntegerProperty(0);
    private final SimpleStringProperty graffix = new SimpleStringProperty("");
    private final SimpleStringProperty upToDate = new SimpleStringProperty("");
    private final SimpleStringProperty autoUpdate = new SimpleStringProperty("");
    private final SimpleStringProperty musicFiles = new SimpleStringProperty("");
    private final SimpleIntegerProperty char0 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char1 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char2 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char3 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char4 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char5 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char6 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char7 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char8 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char9 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char10 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char11 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty comicEffectOccurence = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty gameRating = new SimpleIntegerProperty(0);
    private final SimpleStringProperty country = new SimpleStringProperty("");
    private final SimpleBooleanProperty usingController = new SimpleBooleanProperty(true);
    private final SimpleIntegerProperty currentLanguage = new SimpleIntegerProperty(0);
    private final SimpleBooleanProperty leftHanded = new SimpleBooleanProperty(false);
    private final SimpleIntegerProperty consequtiveWins = new SimpleIntegerProperty(0);

    public void incrementAchievement(int index) {
        switch (index) {
            case 0:
                setAch0(getAch0() + 1);
                break;
            case 1:
                setAch1(getAch1() + 1);
                break;
            case 2:
                setAch2(getAch2() + 1);
                break;
            case 3:
                setAch3(getAch3() + 1);
                break;
            case 4:
                setAch4(getAch4() + 1);
                break;
            case 5:
                setAch5(getAch5() + 1);
                break;
            case 6:
                setAch6(getAch6() + 1);
                break;
            case 7:
                setAch7(getAch7() + 1);
                break;
            case 8:
                setAch8(getAch8() + 1);
                break;
            case 9:
                setAch9(getAch9() + 1);
                break;
            case 10:
                setAch10(getAch10() + 1);
                break;
            case 11:
                setAch11(getAch11() + 1);
                break;
        }
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
        return userName.get();
    }

    public void setUserName(final String userName) {
        this.userName.set(userName);
    }

    public String getUsrCode() {
        return usrCode.get();
    }

    public void setUsrCode(final String usrCode) {
        this.usrCode.set(usrCode);
    }

    public int getPoints() {
        return points.get();
    }

    public void setPoints(final int points) {
        this.points.set(points);
    }

    public int getPlayTime() {
        return playTime.get();
    }

    public void setPlayTime(final int playTime) {
        this.playTime.set(playTime);
    }

    public int getNumberOfMatches() {
        return numberOfMatches.get();
    }

    public void setNumberOfMatches(final int numberOfMatches) {
        this.numberOfMatches.set(numberOfMatches);
    }

    public String getAchievementName() {
        return achievementName.get();
    }

    public void setAchievementName(final String achievementName) {
        this.achievementName.set(achievementName);
    }

    public String getAchievementDescription() {
        return achievementDescription.get();
    }

    public void setAchievementDescription(final String achievementDescription) {
        this.achievementDescription.set(achievementDescription);
    }

    public String getAchievementClass() {
        return achievementClass.get();
    }

    public void setAchievementClass(final String achievementClass) {
        this.achievementClass.set(achievementClass);
    }

    public String getAchievementPoints() {
        return achievementPoints.get();
    }

    public void setAchievementPoints(final String achievementPoints) {
        this.achievementPoints.set(achievementPoints);
    }

    public String getAch0() {
        return ach0.get();
    }

    public void setAch0(final String ach0) {
        this.ach0.set(ach0);
    }

    public String getAch1() {
        return ach1.get();
    }

    public void setAch1(final String ach1) {
        this.ach1.set(ach1);
    }

    public String getAch2() {
        return ach2.get();
    }

    public void setAch2(final String ach2) {
        this.ach2.set(ach2);
    }

    public String getAch3() {
        return ach3.get();
    }

    public void setAch3(final String ach3) {
        this.ach3.set(ach3);
    }

    public String getAch4() {
        return ach4.get();
    }

    public void setAch4(final String ach4) {
        this.ach4.set(ach4);
    }

    public String getAch5() {
        return ach5.get();
    }

    public void setAch5(final String ach5) {
        this.ach5.set(ach5);
    }

    public String getAch6() {
        return ach6.get();
    }

    public void setAch6(final String ach6) {
        this.ach6.set(ach6);
    }

    public String getAch7() {
        return ach7.get();
    }

    public void setAch7(final String ach7) {
        this.ach7.set(ach7);
    }

    public String getAch8() {
        return ach8.get();
    }

    public void setAch8(final String ach8) {
        this.ach8.set(ach8);
    }

    public String getAch9() {
        return ach9.get();
    }

    public void setAch9(final String ach9) {
        this.ach9.set(ach9);
    }

    public String getAch10() {
        return ach10.get();
    }

    public void setAch10(final String ach10) {
        this.ach10.set(ach10);
    }

    public String getAch11() {
        return ach11.get();
    }

    public void setAch11(final String ach11) {
        this.ach11.set(ach11);
    }

    public int getWins() {
        return wins.get();
    }

    public void setWins(final int wins) {
        this.wins.set(wins);
    }

    public int getLosses() {
        return losses.get();
    }

    public void setLosses(final int losses) {
        this.losses.set(losses);
    }

    public int getFrames() {
        return frames.get();
    }

    public void setFrames(int frames) {
        this.frames.set(frames);
    }

    public SimpleIntegerProperty framesProperty() {
        return frames;
    }

    public boolean getSoundStatus() {
        return soundStatus.get();
    }

    public void setSoundStatus(final boolean soundStatus) {
        this.soundStatus.set(soundStatus);
    }

    public int getDifficulty() {
        return difficulty.get();
    }

    public void setDifficulty(final int difficulty) {
        this.difficulty.set(difficulty);
    }

    public int getLastStoryScene() {
        return lastStoryScene.get();
    }

    public void setLastStoryScene(final int lastStoryScene) {
        this.lastStoryScene.set(lastStoryScene);
    }

    public int getTimeLimit() {
        return timeLimit.get();
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit.set(timeLimit);
    }

    public SimpleIntegerProperty timeLimitProperty() {
        return timeLimit;
    }

    public String getGraffix() {
        return graffix.get();
    }

    public void setGraffix(final String graffix) {
        this.graffix.set(graffix);
    }

    public String getUpToDate() {
        return upToDate.get();
    }

    public void setUpToDate(final String upToDate) {
        this.upToDate.set(upToDate);
    }

    public String getAutoUpdate() {
        return autoUpdate.get();
    }

    public void setAutoUpdate(final String autoUpdate) {
        this.autoUpdate.set(autoUpdate);
    }

    public String getMusicFiles() {
        return musicFiles.get();
    }

    public void setMusicFiles(final String musicFiles) {
        this.musicFiles.set(musicFiles);
    }

    public int getChar0() {
        return char0.get();
    }

    public void setChar0(final int char0) {
        this.char0.set(char0);
    }

    public int getChar1() {
        return char1.get();
    }

    public void setChar1(final int char1) {
        this.char1.set(char1);
    }

    public int getChar2() {
        return char2.get();
    }

    public void setChar2(final int char2) {
        this.char2.set(char2);
    }

    public int getChar3() {
        return char3.get();
    }

    public void setChar3(final int char3) {
        this.char3.set(char3);
    }

    public int getChar4() {
        return char4.get();
    }

    public void setChar4(final int char4) {
        this.char4.set(char4);
    }

    public int getChar5() {
        return char5.get();
    }

    public void setChar5(final int char5) {
        this.char5.set(char5);
    }

    public int getChar6() {
        return char6.get();
    }

    public void setChar6(final int char6) {
        this.char6.set(char6);
    }

    public int getChar7() {
        return char7.get();
    }

    public void setChar7(final int char7) {
        this.char7.set(char7);
    }

    public int getChar8() {
        return char8.get();
    }

    public void setChar8(final int char8) {
        this.char8.set(char8);
    }

    public int getChar9() {
        return char9.get();
    }

    public void setChar9(final int char9) {
        this.char9.set(char9);
    }

    public int getChar10() {
        return char10.get();
    }

    public void setChar10(final int char10) {
        this.char10.set(char10);
    }

    public int getChar11() {
        return char11.get();
    }

    public void setChar11(final int char11) {
        this.char11.set(char11);
    }

    public int getComicEffectOccurence() {
        return comicEffectOccurence.get();
    }

    public void setComicEffectOccurence(final int comicEffectOccurence) {
        this.comicEffectOccurence.set(comicEffectOccurence);
    }

    public int getGameRating() {
        return gameRating.get();
    }

    public void setGameRating(final int gameRating) {
        this.gameRating.set(gameRating);
    }

    public String getCountry() {
        return country.get();
    }

    public void setCountry(final String country) {
        this.country.set(country);
    }

    public boolean getUsingController() {
        return usingController.get();
    }

    public void setUsingController(final boolean usingController) {
        this.usingController.set(usingController);
    }

    public int getCurrentLanguage() {
        return currentLanguage.get();
    }

    public void setCurrentLanguage(final int currentLanguage) {
        this.currentLanguage.set(currentLanguage);
    }

    public boolean isLeftHanded() {
        return leftHanded.get();
    }

    public void setLeftHanded(final boolean leftHanded) {
        this.leftHanded.set(leftHanded);
    }

    public void setConsequtiveWins(int consequtiveWins) {
        this.consequtiveWins.set(consequtiveWins);
    }

    public int getConsequtiveWins() {
        return this.consequtiveWins.get();
    }
}
