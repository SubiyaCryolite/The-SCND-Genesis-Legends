package com.scndgen.legends.enums;

/**
 * Created by ifunga on 14/04/2017.
 */
public enum CharacterEnum {
    SUBIYA("Subiya", "Subiya", 0),
    RAILA("Raila", "Raila", 1),
    LYNX("Lynx", "Lynx", 2),
    AISHA("Aisha", "Aisha", 3),
    ADE("Ade", "Ade", 4),
    RAVAGE("Ravage", "Ravage", 5),
    JONAH("Jonah", "Jonah", 6),
    ADAM("Adam", "Adam", 7),
    NOVA_ADAM("NOVA Adam", "NovaAdam", 8),
    AZARIA("Azaria", "Azaria", 9),
    SORROWE("Sorrowe", "Sorrowe", 10),
    THING("Thing", "The Thing", 11);

    private final String character;
    private final String dataFile;
    private final int index;

    CharacterEnum(String name, String dataFile, int index) {
        this.character = name;
        this.dataFile = dataFile;
        this.index = index;
    }

    public String toString() {
        return character;
    }

    public String data() {
        return dataFile;
    }

    public int index() {
        return index;
    }
}
