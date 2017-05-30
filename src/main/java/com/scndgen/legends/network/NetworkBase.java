package com.scndgen.legends.network;

import com.scndgen.legends.constants.NetworkConstants;
import com.scndgen.legends.enums.Player;
import com.scndgen.legends.enums.Stage;
import com.scndgen.legends.render.RenderCharacterSelection;
import com.scndgen.legends.render.RenderGamePlay;
import com.scndgen.legends.render.RenderStageSelect;
import io.github.subiyacryolite.enginev1.Overlay;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

import static com.scndgen.legends.constants.NetworkConstants.*;

/**
 * Created by ifunga on 30/05/2017.
 */
public abstract class NetworkBase {
    /**
     * Read incoming data stream
     */
    public void readMessage(String line) {
        try {
            switch (line) {
                case SEL_SUBIYA:
                    RenderCharacterSelection.getInstance().selSubiya(Player.OPPONENT);
                    return;
                case SEL_LYNX:
                    RenderCharacterSelection.getInstance().selLynx(Player.OPPONENT);
                    return;
                case SEL_ALEX:
                    RenderCharacterSelection.getInstance().selAisha(Player.OPPONENT);
                    return;
                case SEL_ADE:
                    RenderCharacterSelection.getInstance().selAde(Player.OPPONENT);
                    return;
                case SEL_RAVAGE:
                    RenderCharacterSelection.getInstance().selRav(Player.OPPONENT);
                    return;
                case SEL_JOHN:
                    RenderCharacterSelection.getInstance().selJon(Player.OPPONENT);
                    return;
                case SEL_ADAM:
                    RenderCharacterSelection.getInstance().selAdam(Player.OPPONENT);
                    return;
                case SEL_NOVA_ADAM:
                    RenderCharacterSelection.getInstance().selNOVAAdam(Player.OPPONENT);
                    return;
                case SEL_AZARIA:
                    RenderCharacterSelection.getInstance().selAza(Player.OPPONENT);
                    return;
                case SEL_SORROWE:
                    RenderCharacterSelection.getInstance().selSorr(Player.OPPONENT);
                    return;
                case SEL_THING:
                    RenderCharacterSelection.getInstance().selThing(Player.OPPONENT);
                    return;
                case STAGE_IBEX_HILL:
                    RenderStageSelect.getInstance().selectStage(Stage.IBEX_HILL);
                    return;
                case STAGE_CHELSTON_CITY_DOCKS:
                    RenderStageSelect.getInstance().selectStage(Stage.CHELSTON_CITY_DOCKS);
                    return;
                case STAGE_DESERT_RUINS:
                    RenderStageSelect.getInstance().selectStage(Stage.DESERT_RUINS);
                    return;
                case STAGE_CHELSTON_CITY_STREETS:
                    RenderStageSelect.getInstance().selectStage(Stage.CHELSTON_CITY_STREETS);
                    return;
                case STAGE_IBEX_HILL_NIGHT:
                    RenderStageSelect.getInstance().selectStage(Stage.IBEX_HILL_NIGHT);
                    return;
                case STAGE_SCORCHED_RUINS:
                    RenderStageSelect.getInstance().selectStage(Stage.SCORCHED_RUINS);
                    return;
                case STAGE_FROZEN_WILDERNESS:
                    RenderStageSelect.getInstance().selectStage(Stage.FROZEN_WILDERNESS);
                    return;
                case STAGE_DISTANT_ISLE:
                    RenderStageSelect.getInstance().selectStage(Stage.DISTANT_ISLE);
                    return;
                case STAGE_HIDDEN_CAVE:
                    RenderStageSelect.getInstance().selectStage(Stage.HIDDEN_CAVE);
                    return;
                case STAGE_HIDDEN_CAVE_NIGHT:
                    RenderStageSelect.getInstance().selectStage(Stage.HIDDEN_CAVE_NIGHT);
                    return;
                case STAGE_AFRICAN_VILLAGE:
                    RenderStageSelect.getInstance().selectStage(Stage.AFRICAN_VILLAGE);
                    return;
                case STAGE_APOCALYPTO:
                    RenderStageSelect.getInstance().selectStage(Stage.APOCALYPTO);
                    return;
                case STAGE_DISTANT_ISLE_NIGHT:
                    RenderStageSelect.getInstance().selectStage(Stage.DISTANT_ISLE_NIGHT);
                    return;
                case STAGE_RANDOM:
                    RenderStageSelect.getInstance().selectStage(Stage.RANDOM);
                    return;
                case STAGE_DESERT_RUINS_NIGHT:
                    RenderStageSelect.getInstance().selectStage(Stage.DESERT_RUINS_NIGHT);
                    return;
                case STAGE_SCORCHED_RUINS_NIGHT:
                    RenderStageSelect.getInstance().selectStage(Stage.SCORCHED_RUINS_NIGHT);
                    return;
                case INDICATE_STAGE_SELECTED:
                    RenderStageSelect.getInstance().setStageSelected(true);
                    break;
                case GAME_START:
                    RenderStageSelect.getInstance().start();
                    break;
                case FURY_ATTACK:
                    RenderGamePlay.getInstance().triggerFury(Player.OPPONENT);
                    return;
            }
            if (line.endsWith(NetworkConstants.ATTACK_POSTFIX)) {
                String[] attackArray = line.split(":");
                List<String> attackList = new LinkedList<>();
                for (int i = 0; i < attackArray.length - 1; i++) {
                    if (!attackArray[i].isEmpty())
                        attackList.add(attackArray[i]);
                }
                RenderGamePlay.getInstance().opponentAttack(attackList);
            } else if (line.endsWith(" xc_97_mb")) {
                Overlay.getInstance().primaryNotice(line.replaceAll(" xc_97_mb", ""));
            } //CharacterEnum
            else if (line.startsWith("as1wds2_")) {
                NetworkManager.getInstance().hostTime = Integer.parseInt(line.substring(8));
                System.out.println("aquired time is " + NetworkManager.getInstance().hostTime);
            } //stages
            else if (line.contains("getLost")) ;
            {
                JOptionPane.showMessageDialog(null, "HARSH!, The opponent doesnt want to fight you -_-", "Ouchies", JOptionPane.ERROR_MESSAGE);
                NetworkManager.getInstance().closePipes();
            }
        } catch (Exception ex) {
            System.err.println(ex);
            ex.printStackTrace();
            sendData("lastMess");
        }
    }

    public abstract void sendData(String mess);
}
