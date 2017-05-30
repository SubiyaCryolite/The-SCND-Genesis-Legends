package com.scndgen.legends.constants;

/**
 * Created by ifunga on 30/05/2017.
 */
public class NetworkConstants {
    public static final String SEL_RAILA = "SEL_RAILA";
    public static final String CONNECT_TO_HOST = "0000";
    public static final String DISCONNECT_FROM_HOST = "0001";
    public static final String FURY_ATTACK = "0002";
    public static final String ATTACK_POSTFIX = "0003";
    public static final String SEL_SUBIYA = "SEL_SUBIYA";
    public static final String SEL_LYNX = "SEL_LYNX";
    public static final String SEL_ALEX = "SEL_ALEX";
    public static final String SEL_ADE = "SEL_ADE";
    public static final String SEL_RAVAGE = "SEL_RAVAGE";
    public static final String SEL_JOHN = "SEL_JOHN";
    public static final String SEL_ADAM = "SEL_ADAM";
    public static final String SEL_NOVA_ADAM = "SEL_NOVA_ADAM";
    public static final String SEL_AZARIA = "SEL_AZARIA";
    public static final String SEL_SORROWE = "SEL_SORROWE";
    public static final String SEL_THING = "SEL_THING";
    public static final String STAGE_IBEX_HILL = "STAGE_IBEX_HILL";
    public static final String STAGE_CHELSTON_CITY_DOCKS = "STAGE_CHELSTON_CITY_DOCKS";
    public static final String STAGE_DESERT_RUINS = "STAGE_DESERT_RUINS";
    public static final String STAGE_CHELSTON_CITY_STREETS = "STAGE_CHELSTON_CITY_STREETS";
    public static final String STAGE_IBEX_HILL_NIGHT = "STAGE_IBEX_HILL_NIGHT";
    public static final String STAGE_SCORCHED_RUINS = "STAGE_SCORCHED_RUINS";
    public static final String STAGE_FROZEN_WILDERNESS = "STAGE_FROZEN_WILDERNESS";
    public static final String STAGE_DISTANT_ISLE = "STAGE_DISTANT_ISLE";
    public static final String STAGE_HIDDEN_CAVE = "STAGE_HIDDEN_CAVE";
    public static final String STAGE_HIDDEN_CAVE_NIGHT = "STAGE_HIDDEN_CAVE_NIGHT";
    public static final String STAGE_AFRICAN_VILLAGE = "STAGE_AFRICAN_VILLAGE";
    public static final String STAGE_APOCALYPTO = "STAGE_APOCALYPTO";
    public static final String STAGE_DISTANT_ISLE_NIGHT = "STAGE_DISTANT_ISLE_NIGHT";
    public static final String STAGE_RANDOM = "STAGE_RANDOM";
    public static final String STAGE_DESERT_RUINS_NIGHT = "STAGE_DESERT_RUINS_NIGHT";
    public static final String STAGE_SCORCHED_RUINS_NIGHT = "STAGE_SCORCHED_RUINS_NIGHT";
    public static final String INDICATE_STAGE_SELECTED = "INDICATE_STAGE_SELECTED";
    public static final String GAME_START="GAME_START";

    public static String connectToHost(int timeOut) {
        return String.format("%s_%s", CONNECT_TO_HOST, timeOut);
    }


}
