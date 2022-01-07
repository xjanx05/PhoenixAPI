package de.codingphoenix.phoenixapi.other;

public enum JSONIcons {
    PLANE("✈"),
    HOURGLASS("⌛"),
    CHECK("✔"),
    CROSS("✖"),
    HEARD("❤"),
    FLAG("⚑"),
    SMILE_NICE("☺"),
    SMILE_SAD("☹"),
    LETTER("✉"),
    COFFEE_CUP("☕"),
    WARNING("⚠"),
    SKULL("☠"),
    SNOWFLAKE("❄"),
    SUN_1("☀"),
    SUN_2("☼"),
    CLOUD("☁"),
    MOON("☽"),
    DICE_1("⚀"),
    DICE_2("⚁"),
    DICE_3("⚂"),
    DICE_4("⚃"),
    DICE_5("⚄"),
    DICE_6("⚅"),
    WOMAN("♀"),
    MAN("♂"),
    SNOWMAN("☃"),
    LIGHTNING("⚡"),
    PEN_DIAGONAL("✎"),
    PEN_HORIZONTAL("✏"),
    DOUBLE_EXCLAMATION_MARK("‼"),
    DISABLED("♿"),
    NOTE_ONE("♪"),
    NOTE_DOUBLE("♫"),
    PHONE("☎"),
    INFINITY("∞"),
    STAR("☆"),
    CHECKBOX("☐"),
    CHECKBOX_CHECK("☑"),
    CHECKBOX_CROSS("☒"),
    RHOMBUS("⬧"),
    JESUS("✝"),
    SYMBOL("۞"),
    ARROW_LEFT("«"),
    ARROW_RIGHT("»");

    final String icon;

    JSONIcons(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }
}
