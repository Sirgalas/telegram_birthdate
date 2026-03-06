package ru.sergalas.bot.bot.enums;

public enum ButtonTextEnum {
    AUTH("Авторизация"),
    START("/start"),
    ABOUT("О боте"),
    PARTICIPANT("Записать участника"),
    PERIODICITY("Записать периодическое сообщение");

    private String text;

    ButtonTextEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
