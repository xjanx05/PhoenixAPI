package de.codingphoenix.phoenixapi.mc.languageAPI;

public enum Language {
    GERMAN("de_DE"),
    ENGLISH("en_EN"),
    FRANCE("fr_FR"),
    SPANISH("es_ES"),
    CHINESE("ch_CH");

    String languageName;

    Language(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageName() {
        return languageName;
    }
}
