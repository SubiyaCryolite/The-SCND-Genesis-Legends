package com.scndgen.legends.network;

import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.constants.NetworkConstants;
import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.enums.Player;
import com.scndgen.legends.enums.Stage;
import com.scndgen.legends.render.RenderCharacterSelection;
import com.scndgen.legends.render.RenderGamePlay;
import com.scndgen.legends.render.RenderStageSelect;
import io.github.subiyacryolite.enginev1.FxDialogs;

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
            if (line.endsWith(NetworkConstants.ATTACK_POSTFIX)) {
                String[] attackArray = line.split(":");
                List<String> attackList = new LinkedList<>();
                for (int i = 0; i < attackArray.length - 1; i++) {
                    if (!attackArray[i].isEmpty())
                        attackList.add(attackArray[i]);
                }
                RenderGamePlay.get().opponentAttack(attackList);
            } else if (line.startsWith(HOST_TIME_CONSTANT)) {
                NetworkManager.get().hostTimeLimit = Integer.parseInt(line.replace(HOST_TIME_CONSTANT, ""));
                System.out.println("aquired time is " + NetworkManager.get().hostTimeLimit);
            } else {
                switch (line) {
                    case SEL_SUBIYA:
                        RenderCharacterSelection.get().selSubiya(Player.OPPONENT);
                        return;
                    case SEL_LYNX:
                        RenderCharacterSelection.get().selLynx(Player.OPPONENT);
                        return;
                    case SEL_ALEX:
                        RenderCharacterSelection.get().selAisha(Player.OPPONENT);
                        return;
                    case SEL_ADE:
                        RenderCharacterSelection.get().selAde(Player.OPPONENT);
                        return;
                    case SEL_RAVAGE:
                        RenderCharacterSelection.get().selRav(Player.OPPONENT);
                        return;
                    case SEL_JOHN:
                        RenderCharacterSelection.get().selJon(Player.OPPONENT);
                        return;
                    case SEL_ADAM:
                        RenderCharacterSelection.get().selAdam(Player.OPPONENT);
                        return;
                    case SEL_NOVA_ADAM:
                        RenderCharacterSelection.get().selNOVAAdam(Player.OPPONENT);
                        return;
                    case SEL_AZARIA:
                        RenderCharacterSelection.get().selAza(Player.OPPONENT);
                        return;
                    case SEL_SORROWE:
                        RenderCharacterSelection.get().selSorr(Player.OPPONENT);
                        return;
                    case SEL_THING:
                        RenderCharacterSelection.get().selThing(Player.OPPONENT);
                        return;
                    case STAGE_IBEX_HILL:
                        RenderStageSelect.get().selectStage(Stage.IBEX_HILL);
                        return;
                    case STAGE_CHELSTON_CITY_DOCKS:
                        RenderStageSelect.get().selectStage(Stage.CHELSTON_CITY_DOCKS);
                        return;
                    case STAGE_DESERT_RUINS:
                        RenderStageSelect.get().selectStage(Stage.DESERT_RUINS);
                        return;
                    case STAGE_CHELSTON_CITY_STREETS:
                        RenderStageSelect.get().selectStage(Stage.CHELSTON_CITY_STREETS);
                        return;
                    case STAGE_IBEX_HILL_NIGHT:
                        RenderStageSelect.get().selectStage(Stage.IBEX_HILL_NIGHT);
                        return;
                    case STAGE_SCORCHED_RUINS:
                        RenderStageSelect.get().selectStage(Stage.SCORCHED_RUINS);
                        return;
                    case STAGE_FROZEN_WILDERNESS:
                        RenderStageSelect.get().selectStage(Stage.FROZEN_WILDERNESS);
                        return;
                    case STAGE_DISTANT_ISLE:
                        RenderStageSelect.get().selectStage(Stage.DISTANT_ISLE);
                        return;
                    case STAGE_HIDDEN_CAVE:
                        RenderStageSelect.get().selectStage(Stage.HIDDEN_CAVE);
                        return;
                    case STAGE_HIDDEN_CAVE_NIGHT:
                        RenderStageSelect.get().selectStage(Stage.HIDDEN_CAVE_NIGHT);
                        return;
                    case STAGE_AFRICAN_VILLAGE:
                        RenderStageSelect.get().selectStage(Stage.AFRICAN_VILLAGE);
                        return;
                    case STAGE_APOCALYPTO:
                        RenderStageSelect.get().selectStage(Stage.APOCALYPTO);
                        return;
                    case STAGE_DISTANT_ISLE_NIGHT:
                        RenderStageSelect.get().selectStage(Stage.DISTANT_ISLE_NIGHT);
                        return;
                    case STAGE_RANDOM:
                        RenderStageSelect.get().selectStage(Stage.RANDOM);
                        return;
                    case STAGE_DESERT_RUINS_NIGHT:
                        RenderStageSelect.get().selectStage(Stage.DESERT_RUINS_NIGHT);
                        return;
                    case STAGE_SCORCHED_RUINS_NIGHT:
                        RenderStageSelect.get().selectStage(Stage.SCORCHED_RUINS_NIGHT);
                        return;
                    case TO_STAGE_SELECT:
                        ScndGenLegends.get().loadMode(ModeEnum.STAGE_SELECT_SCREEN);
                        break;
                    case GAME_START:
                        RenderStageSelect.get().start();
                        break;
                    case FURY_ATTACK:
                        RenderGamePlay.get().triggerFury(Player.OPPONENT);
                        return;
                    case CONNECT_TO_HOST:
                        if (NetworkManager.get().isServer()) {
                            NetworkManager.get().promptServer();
                        } else {
                            NetworkManager.get().setConnectedToPartner(true);
                        }
                        break;
                    case DISCONNECT_FROM_HOST:
                        FxDialogs.message("Ouchies", "HARSH!", "The opponent doesnt want to fight you -_-");
                        NetworkManager.get().close();
                        break;
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
            ex.printStackTrace();
            sendData("lastMess");
        }
    }

    public abstract void sendData(String mess);

    protected long keepAlive(long frames) {
        if (frames % 60 == 0)
            sendData("1");//keep stream alive
        frames++;
        return frames;
    }
}
