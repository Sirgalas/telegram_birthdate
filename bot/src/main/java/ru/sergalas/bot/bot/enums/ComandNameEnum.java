package ru.sergalas.bot.bot.enums;

public enum ComandNameEnum {
    START("START"),
    PARTICIPANT_SAVE("PARTICIPANT_SAVE"),
    PERIODICITY_SAVE("PERIODICITY_SAVE"),
    AUTH("AUTH"),
    ABOUT("ABOUT");

    private String name;

    ComandNameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
