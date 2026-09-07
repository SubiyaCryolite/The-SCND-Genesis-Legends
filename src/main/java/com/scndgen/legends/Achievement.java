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
package com.scndgen.legends;

import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.enums.*;
import com.scndgen.legends.mode.GamePlay;
import com.scndgen.legends.mode.StoryMode;
import com.scndgen.legends.state.State;
import io.github.subiyacryolite.enginev2.Overlay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ndana
 */
public class Achievement {

    private static Achievement instance;
    private final List<String> name = new ArrayList<>();
    private final List<String> descriptions = new ArrayList<>();
    private final List<AchievementCategories> categories = new ArrayList<>();
    private final List<Integer> points = new ArrayList<>();
    private final HashMap<Achievements, String> achievementName = new HashMap<>();
    private final HashMap<Achievements, String> achievementDescription = new HashMap<>();
    private final boolean[] isAchievementLocked = new boolean[Achievements.values().length];
    private int currentPoints;
    private int bonus;

    private Achievement() {
        instance = this;
        bindNames();
        Language.get().addLocaleListener(this::bindNames);
        newInstance();
    }

    private void bindNames() {
        var language = Language.get();
        setBinding(Achievements.UPPER_HAND, language.get(LangKey.ACH_UPPER_HAND), language.get(LangKey.ACH_UPPER_HAND_DESC));
        setBinding(Achievements.BEAT_THE_ODDS, language.get(LangKey.ACH_BEAT_THE_ODDS), language.get(LangKey.ACH_BEAT_THE_ODDS_DESC));
        setBinding(Achievements.OWNAGE, language.get(LangKey.ACH_OWNAGE), language.get(LangKey.ACH_OWNAGE_DESC));
        setBinding(Achievements.HEARTLESS, language.get(LangKey.ACH_HEARTLESS), language.get(LangKey.ACH_HEARTLESS_DESC));
        setBinding(Achievements.MEANIE, language.get(LangKey.ACH_MEANIE), language.get(LangKey.ACH_MEANIE_DESC));
        setBinding(Achievements.RAGE, language.get(LangKey.ACH_RAGE), language.get(LangKey.ACH_RAGE_DESC));
        setBinding(Achievements.WINNER, language.get(LangKey.ACH_WINNER), language.get(LangKey.ACH_WINNER_DESC));
        setBinding(Achievements.BUZZ_KILL, language.get(LangKey.ACH_BUZZ_KILL), language.get(LangKey.ACH_BUZZ_KILL_DESC));
        setBinding(Achievements.CLOSE_CALL, language.get(LangKey.ACH_CLOSE_CALL), language.get(LangKey.ACH_CLOSE_CALL_DESC));
        setBinding(Achievements.ON_A_ROLL, language.get(LangKey.ACH_ON_A_ROLL), language.get(LangKey.ACH_ON_A_ROLL_DESC));
        setBinding(Achievements.HALF_WAY_THROUGH, language.get(LangKey.ACH_HALF_WAY), language.get(LangKey.ACH_HALF_WAY_DESC));
    }

    private void setBinding(final Achievements achievement, final String name, final String description) {
        achievementName.put(achievement, name);
        achievementDescription.put(achievement, description);
    }

    public static synchronized Achievement get() {
        if (instance == null)
            instance = new Achievement();
        return instance;
    }

    /**
     * Get the amount of Achievements triggered
     *
     * @return
     */
    public int getNumberOfAchievements() {
        return name.size();
    }

    /**
     * Get achievement details
     *
     * @param index
     * @return details
     */
    public String[] getInfo(int index) {
        String[] results = new String[4];
        results[0] = "" + name.get(index);
        results[1] = "" + descriptions.get(index);
        results[2] = "" + categories.get(index);
        results[3] = "" + points.get(index);
        return results;
    }

    public void clearAll() {
        name.clear();
        descriptions.clear();
        categories.clear();
        points.clear();
    }

    /**
     * Scan for conditions
     */
    public void scan(GamePlay gamePlay) {
        var characters = Characters.get();
        var overlay = Overlay.get();
        var language = Language.get();
        var login = State.get().getLogin();

        Achievements achievement;
        if (characters.getCharMinLife() <= 79 && gamePlay.getCharacterHpAsPercent() >= 82 && isAchievementLocked[Achievements.UPPER_HAND.id()]) {
            achievement = Achievements.UPPER_HAND;
            name.add(achievementName.get(achievement));
            overlay.primaryNotice(language.get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            login.incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (characters.getCharMinLife() <= 30 && gamePlay.getCharacterHpAsPercent() >= 50 && isAchievementLocked[Achievements.BEAT_THE_ODDS.id()]) {
            achievement = Achievements.BEAT_THE_ODDS;
            name.add(achievementName.get(achievement));
            overlay.primaryNotice(language.get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            login.incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (((gamePlay.getCharacterHpAsPercent() - gamePlay.getOpponentHpAsPercent()) >= 50) && isAchievementLocked[Achievements.OWNAGE.id()]) {
            achievement = Achievements.OWNAGE;
            name.add(achievementName.get(achievement));
            overlay.primaryNotice(language.get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            login.incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
            isAchievementLocked[Achievements.MEANIE.id()] = false;
            isAchievementLocked[Achievements.HEARTLESS.id()] = false;
        }
        if (((gamePlay.getCharacterHpAsPercent() - gamePlay.getOpponentHpAsPercent()) >= 40) && isAchievementLocked[Achievements.HEARTLESS.id()]) {
            achievement = Achievements.HEARTLESS;
            name.add(achievementName.get(achievement));
            overlay.primaryNotice(language.get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            login.incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (((gamePlay.getCharacterHpAsPercent() - gamePlay.getOpponentHpAsPercent()) >= 30) && isAchievementLocked[Achievements.MEANIE.id()]) {
            achievement = Achievements.MEANIE;
            name.add(achievementName.get(achievement));
            overlay.primaryNotice(language.get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            login.incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (gamePlay.getAttackType(PlayerType.PLAYER1) == AttackType.FURY && gamePlay.isGameOver() && gamePlay.hasWon() && isAchievementLocked[Achievements.RAGE.id()]) {
            achievement = Achievements.RAGE;
            name.add(achievementName.get(achievement));
            overlay.primaryNotice(language.get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            login.incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (gamePlay.hasWon() && gamePlay.isGameOver()) {
            achievement = Achievements.WINNER;
            name.add(achievementName.get(achievement));
            overlay.primaryNotice(language.get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            login.incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (gamePlay.getAttackType(PlayerType.PLAYER2) == AttackType.FURY && gamePlay.hasWon() && gamePlay.isGameOver() && isAchievementLocked[Achievements.BUZZ_KILL.id()]) {
            achievement = Achievements.BUZZ_KILL;
            name.add(achievementName.get(achievement));
            overlay.primaryNotice(language.get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            login.incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (gamePlay.hasWon() && gamePlay.isGameOver() && (gamePlay.getCharacterHpAsPercent() - gamePlay.getOpponentHpAsPercent() <= 30) && isAchievementLocked[Achievements.CLOSE_CALL.id()]) {
            achievement = Achievements.CLOSE_CALL;
            name.add(achievementName.get(achievement));
            overlay.primaryNotice(language.get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            login.incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (gamePlay.hasWon() && gamePlay.isGameOver() && login.getConsecutiveWins() >= 5 && isAchievementLocked[Achievements.ON_A_ROLL.id()]) {
            achievement = Achievements.ON_A_ROLL;
            name.add(achievementName.get(achievement));
            overlay.primaryNotice(language.get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            login.incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (gamePlay.hasWon() && StoryMode.get().storyProgress == StoryProgress.HALFWAY && gamePlay.isGameOver() && isAchievementLocked[Achievements.HALF_WAY_THROUGH.id()]) {
            achievement = Achievements.HALF_WAY_THROUGH;
            name.add(achievementName.get(achievement));
            overlay.primaryNotice(language.get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            login.incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
    }

    /**
     * Get the users new score
     *
     * @return score
     */
    public int getNewUserPoints() {
        return currentPoints;
    }

    /**
     * New instance
     */
    public void newInstance() {
        name.clear();
        descriptions.clear();
        categories.clear();
        points.clear();
        var login = State.get().getLogin();
        currentPoints = login.getPoints();
        for (int i = 0; i < Achievements.values().length; i++) {
            isAchievementLocked[i] = true;
        }
        bonus = ((State.DIFFICULTY_BASE - login.getDifficultyDynamic()) / State.DIFFICULTY_SCALE) * 5;
        currentPoints += 20 + bonus;
    }

    public String achievementName(Achievements achievement) {
        return achievementName.get(achievement);
    }

    public String achievementDescription(Achievements achievement) {
        return achievementDescription.get(achievement);
    }
}
