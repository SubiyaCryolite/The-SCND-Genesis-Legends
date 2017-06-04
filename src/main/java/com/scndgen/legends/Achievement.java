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
package com.scndgen.legends;

import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.enums.*;
import com.scndgen.legends.mode.GamePlay;
import com.scndgen.legends.mode.StoryMode;
import com.scndgen.legends.state.State;
import io.github.subiyacryolite.enginev1.Overlay;

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
        setBinding(Achievements.UPPER_HAND, Language.get().get(61), Language.get().get(72));
        setBinding(Achievements.BEAT_THE_ODDS, Language.get().get(62), Language.get().get(73));
        setBinding(Achievements.OWNAGE, Language.get().get(63), Language.get().get(74));
        setBinding(Achievements.HEARTLESS, Language.get().get(64), Language.get().get(75));
        setBinding(Achievements.MEANIE, Language.get().get(65), Language.get().get(76));
        setBinding(Achievements.RAGE, Language.get().get(66), Language.get().get(77));
        setBinding(Achievements.WINNER, Language.get().get(67), Language.get().get(78));
        setBinding(Achievements.BUZZ_KILL, Language.get().get(68), Language.get().get(79));
        setBinding(Achievements.CLOSE_CALL, Language.get().get(69), Language.get().get(80));
        setBinding(Achievements.ON_A_ROLL, Language.get().get(70), Language.get().get(81));
        setBinding(Achievements.HALF_WAY_THROUGH, Language.get().get(71), Language.get().get(82));
        newInstance();
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
        Achievements achievement;
        if (Characters.get().getCharMinLife() <= 79 && gamePlay.getCharacterHpAsPercent() >= 82 && isAchievementLocked[Achievements.UPPER_HAND.id()]) {
            achievement = Achievements.UPPER_HAND;
            name.add(achievementName.get(achievement));
            Overlay.get().primaryNotice(Language.get().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            State.get().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (Characters.get().getCharMinLife() <= 30 && gamePlay.getCharacterHpAsPercent() >= 50 && isAchievementLocked[Achievements.BEAT_THE_ODDS.id()]) {
            achievement = Achievements.BEAT_THE_ODDS;
            name.add(achievementName.get(achievement));
            Overlay.get().primaryNotice(Language.get().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            State.get().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (((gamePlay.getCharacterHpAsPercent() - gamePlay.getOpponentHpAsPercent()) >= 50) && isAchievementLocked[Achievements.OWNAGE.id()]) {
            achievement = Achievements.OWNAGE;
            name.add(achievementName.get(achievement));
            Overlay.get().primaryNotice(Language.get().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            State.get().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
            isAchievementLocked[Achievements.MEANIE.id()] = false;
            isAchievementLocked[Achievements.HEARTLESS.id()] = false;
        }
        if (((gamePlay.getCharacterHpAsPercent() - gamePlay.getOpponentHpAsPercent()) >= 40) && isAchievementLocked[Achievements.HEARTLESS.id()]) {
            achievement = Achievements.HEARTLESS;
            name.add(achievementName.get(achievement));
            Overlay.get().primaryNotice(Language.get().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            State.get().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (((gamePlay.getCharacterHpAsPercent() - gamePlay.getOpponentHpAsPercent()) >= 30) && isAchievementLocked[Achievements.MEANIE.id()]) {
            achievement = Achievements.MEANIE;
            name.add(achievementName.get(achievement));
            Overlay.get().primaryNotice(Language.get().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            State.get().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (gamePlay.getAttackType(Player.CHARACTER) == AttackType.FURY && gamePlay.isGameOver() && gamePlay.hasWon() && isAchievementLocked[Achievements.RAGE.id()]) {
            achievement = Achievements.RAGE;
            name.add(achievementName.get(achievement));
            Overlay.get().primaryNotice(Language.get().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            State.get().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (gamePlay.hasWon() && gamePlay.isGameOver()) {
            achievement = Achievements.WINNER;
            name.add(achievementName.get(achievement));
            Overlay.get().primaryNotice(Language.get().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            State.get().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (gamePlay.getAttackType(Player.OPPONENT) == AttackType.FURY && gamePlay.hasWon() && gamePlay.isGameOver() && isAchievementLocked[Achievements.BUZZ_KILL.id()]) {
            achievement = Achievements.BUZZ_KILL;
            name.add(achievementName.get(achievement));
            Overlay.get().primaryNotice(Language.get().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            State.get().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (gamePlay.hasWon() && gamePlay.isGameOver() && (gamePlay.getCharacterHpAsPercent() - gamePlay.getOpponentHpAsPercent() <= 30) && isAchievementLocked[Achievements.CLOSE_CALL.id()]) {
            achievement = Achievements.CLOSE_CALL;
            name.add(achievementName.get(achievement));
            Overlay.get().primaryNotice(Language.get().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            State.get().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (gamePlay.hasWon() && gamePlay.isGameOver() && State.get().getLogin().getConsecutiveWins() >= 5 && isAchievementLocked[Achievements.ON_A_ROLL.id()]) {
            achievement = Achievements.ON_A_ROLL;
            name.add(achievementName.get(achievement));
            Overlay.get().primaryNotice(Language.get().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            State.get().getLogin().incrementAchievement(achievement);
            isAchievementLocked[achievement.id()] = false;
        }
        if (gamePlay.hasWon() && StoryMode.get().storyProgress == StoryProgress.HALFWAY && gamePlay.isGameOver() && isAchievementLocked[Achievements.HALF_WAY_THROUGH.id()]) {
            achievement = Achievements.HALF_WAY_THROUGH;
            name.add(achievementName.get(achievement));
            Overlay.get().primaryNotice(Language.get().get(83) + ": " + achievementName.get(achievement));
            descriptions.add(achievementDescription.get(achievement));
            categories.add(achievement.achievementCategory());
            points.add(achievement.achievementCategory().points() + bonus);
            currentPoints += achievement.achievementCategory().points() + bonus;
            State.get().getLogin().incrementAchievement(achievement);
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
        currentPoints = State.get().getLogin().getPoints();
        for (int i = 0; i < Achievements.values().length; i++) {
            isAchievementLocked[i] = true;
        }
        bonus = ((State.DIFFICULTY_BASE - State.get().getLogin().getDifficultyDynamic()) / State.DIFFICULTY_SCALE) * 5;
        currentPoints += 20 + bonus;
    }

    public String achievementName(Achievements achievement) {
        return achievementName.get(achievement);
    }

    public String achievementDescription(Achievements achievement) {
        return achievementDescription.get(achievement);
    }
}
