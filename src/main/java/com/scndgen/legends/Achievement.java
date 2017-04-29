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
package com.scndgen.legends;

import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.controller.StoryMode;
import com.scndgen.legends.enums.AchievementCategories;
import com.scndgen.legends.enums.Achievements;
import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.state.GameState;
import com.scndgen.legends.threads.GameInstance;
import io.github.subiyacryolite.enginev1.JenesisOverlay;

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
    private final boolean[] isAchievementLocked= new boolean[Achievements.values().length];
    private int currentPoints;
    private int bonus;

    private Achievement() {
        instance = this;
        setAchievementBinding(Achievements.UPPER_HAND, Language.getInstance().get(61), Language.getInstance().get(72));
        setAchievementBinding(Achievements.BEAT_THE_ODDS, Language.getInstance().get(62), Language.getInstance().get(73));
        setAchievementBinding(Achievements.OWNAGE, Language.getInstance().get(63), Language.getInstance().get(74));
        setAchievementBinding(Achievements.HEARTLESS, Language.getInstance().get(64), Language.getInstance().get(75));
        setAchievementBinding(Achievements.MEANIE, Language.getInstance().get(65), Language.getInstance().get(76));
        setAchievementBinding(Achievements.RAGE, Language.getInstance().get(66), Language.getInstance().get(77));
        setAchievementBinding(Achievements.WINNER, Language.getInstance().get(67), Language.getInstance().get(78));
        setAchievementBinding(Achievements.BUZZ_KILL, Language.getInstance().get(68), Language.getInstance().get(79));
        setAchievementBinding(Achievements.CLOSE_CALL, Language.getInstance().get(69), Language.getInstance().get(80));
        setAchievementBinding(Achievements.ON_A_ROLL, Language.getInstance().get(70), Language.getInstance().get(81));
        setAchievementBinding(Achievements.HALF_WAY_THROUGH, Language.getInstance().get(71), Language.getInstance().get(82));
        newInstance();
    }

    private void setAchievementBinding(final Achievements achievement, final String name, final String description) {
        achievementName.put(achievement, name);
        achievementDescription.put(achievement, description);
    }

    public static synchronized Achievement getInstance() {
        if (instance == null)
            instance = new Achievement();
        return instance;
    }

    /**
     * Get the amount of Achievements triggered
     *
     * @return
     */
    public int getAcievementsTriggered() {
        return name.size();
    }

    /**
     * Get achievement details
     *
     * @param index
     * @return details
     */
    public String[] getName(int index) {
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
    public void scan() {
        Achievements achievement;
        if (Characters.getInstance().getCharMinLife() <= 79 && RenderGameplay.getInstance().getCharacterHpAsPercent() >= 82 && isAchievementLocked[Achievements.UPPER_HAND.id()]) {
            achievement = Achievements.UPPER_HAND;
            name.add(achievementName.get(achievement));
            JenesisOverlay.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            GameState.getInstance().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (Characters.getInstance().getCharMinLife() <= 30 && RenderGameplay.getInstance().getCharacterHpAsPercent() >= 50 && isAchievementLocked[Achievements.BEAT_THE_ODDS.id()]) {
            achievement = Achievements.BEAT_THE_ODDS;
            name.add(achievementName.get(achievement));
            JenesisOverlay.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            GameState.getInstance().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (((RenderGameplay.getInstance().getCharacterHpAsPercent() - RenderGameplay.getInstance().getOpponentHpAsPercent()) >= 50) && isAchievementLocked[Achievements.OWNAGE.id()]) {
            achievement = Achievements.OWNAGE;
            name.add(achievementName.get(achievement));
            JenesisOverlay.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            GameState.getInstance().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
            isAchievementLocked[Achievements.MEANIE.id()] = false;
            isAchievementLocked[Achievements.HEARTLESS.id()] = false;
        }
        if (((RenderGameplay.getInstance().getCharacterHpAsPercent() - RenderGameplay.getInstance().getOpponentHpAsPercent()) >= 40) && isAchievementLocked[Achievements.HEARTLESS.id()]) {
            achievement = Achievements.HEARTLESS;
            name.add(achievementName.get(achievement));
            JenesisOverlay.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            GameState.getInstance().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (((RenderGameplay.getInstance().getCharacterHpAsPercent() - RenderGameplay.getInstance().getOpponentHpAsPercent()) >= 30) && isAchievementLocked[Achievements.MEANIE.id()]) {
            achievement = Achievements.MEANIE;
            name.add(achievementName.get(achievement));
            JenesisOverlay.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            GameState.getInstance().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (RenderGameplay.getInstance().getAttackType(CharacterState.CHARACTER).equalsIgnoreCase("fury") && GameInstance.getInstance().gameOver && RenderGameplay.getInstance().hasWon() && isAchievementLocked[Achievements.RAGE.id()]) {
            achievement = Achievements.RAGE;
            name.add(achievementName.get(achievement));
            JenesisOverlay.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            GameState.getInstance().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (RenderGameplay.getInstance().hasWon() && GameInstance.getInstance().gameOver) {
            achievement = Achievements.WINNER;
            name.add(achievementName.get(achievement));
            JenesisOverlay.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            GameState.getInstance().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (RenderGameplay.getInstance().getAttackType(CharacterState.OPPONENT).equalsIgnoreCase("fury") && RenderGameplay.getInstance().hasWon() && GameInstance.getInstance().gameOver && isAchievementLocked[Achievements.BUZZ_KILL.id()]) {
            achievement = Achievements.BUZZ_KILL;
            name.add(achievementName.get(achievement));
            JenesisOverlay.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            GameState.getInstance().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (RenderGameplay.getInstance().hasWon() && GameInstance.getInstance().gameOver && (RenderGameplay.getInstance().getCharacterHpAsPercent() - RenderGameplay.getInstance().getOpponentHpAsPercent() <= 30) && isAchievementLocked[Achievements.CLOSE_CALL.id()]) {
            achievement = Achievements.CLOSE_CALL;
            name.add(achievementName.get(achievement));
            JenesisOverlay.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            GameState.getInstance().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (RenderGameplay.getInstance().hasWon() && GameInstance.getInstance().gameOver && GameState.getInstance().getLogin().getConsecutiveWins() >= 5 && isAchievementLocked[Achievements.ON_A_ROLL.id()]) {
            achievement = Achievements.ON_A_ROLL;
            name.add(achievementName.get(achievement));
            JenesisOverlay.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            GameState.getInstance().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (RenderGameplay.getInstance().hasWon() && StoryMode.getInstance().stat.equalsIgnoreCase("half way") && GameInstance.getInstance().gameOver && isAchievementLocked[Achievements.HALF_WAY_THROUGH.id()]) {
            achievement = Achievements.HALF_WAY_THROUGH;
            name.add(achievementName.get(achievement));
            JenesisOverlay.getInstance().primaryNotice(Language.getInstance().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            GameState.getInstance().getLogin().incrementAchievement(achievement);
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
        currentPoints = GameState.getInstance().getLogin().getPoints();
        for (int i = 0; i < Achievements.values().length; i++) {
            isAchievementLocked[i] = true;
        }
        bonus = ((GameState.DIFFICULTY_BASE - GameState.getInstance().getLogin().getDifficultyDynamic()) / GameState.DIFFICULTY_SCALE) * 5;
        currentPoints += 20 + bonus;
    }

    public String achievementName(Achievements achievement) {
        return achievementName.get(achievement);
    }

    public String achievementDescription(Achievements achievement) {
        return achievementDescription.get(achievement);
    }
}
